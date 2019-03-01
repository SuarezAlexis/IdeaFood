package jainjo.ideafood.controllers;

import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import jainjo.ideafood.dto.*;
import jainjo.ideafood.model.*;
import jainjo.ideafood.services.IdeaService;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IdeaFoodController {
    /**************************** Dependencias ****************************/
    @Autowired
    private IdeaService ideaService;
    
    @Autowired
    private AuthenticationManager authManager;
    
    @Autowired
    private RememberMeServices rememberMeServices;
    
    @Autowired
    private CookieClearingLogoutHandler cookieClearingLogoutHandler;
    
    /***************************** Constantes *****************************/
    
    private final int RECENTS_PAGE_SIZE = 2;
    private final String APP_PATH = "\\IdeaFood\\src\\main\\webapp";
    private final String FOOD_IMG_RELATIVE_PATH = "\\resources\\img\\";
    private final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
        //.initialRetryDelayMillis(10)
        //.retryMaxAttempts(5)
        //.totalRetryPeriodMillis(15000)
        .build());
    private final String GOOGLE_STORAGE_BUCKET_NAME = "digitalmenudev-180918.appspot.com";
    private static final int BUFFER_SIZE = 2 * 1024 * 1024;
    
    /************************** Model Attributes **************************/
    @ModelAttribute("tiposAlimento")
    public List<TipoAlimento> tiposAlimento() {
        return ideaService.tiposAlimento();
    }
    
    @ModelAttribute("ingredientes")
    public List<Ingrediente> ingredientes() {
        return ideaService.todosLosIngredientes();
    }
    
    @ModelAttribute("idea")
    public Idea nuevaIdea() {
        Idea idea = new Idea();
        Usuario usuario = new Usuario();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            usuario.setUserName(((User)auth.getPrincipal()).getUsername());
            idea.setUsuario(usuario);
        }
        return idea;
    }
    
    @ModelAttribute("unidades")
    public List<Unidad> unidades() {
        return Unidad.todasLasUnidades();
    }
    
    @ModelAttribute("alimentos")
    public List<Alimento> alimentos() {
        return ideaService.todosLosAlimentos();
    }
     
    @ModelAttribute("sec")
    public Authentication authorize() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    
    /************************** Métodos Mapeados **************************/
    @RequestMapping({"/","/home","/index"})
    public String homePage(@RequestParam(value = "pag",required = false, defaultValue = "1") int page, Model model, HttpServletRequest request) {
        List<Idea> ideas = ideaService.getSugerencias(page, RECENTS_PAGE_SIZE);
        model.addAttribute("sugerencias",ideas);
        return "home";
    }
    
    @RequestMapping("Nueva")
    public String nuevaIdea(@RequestParam("picName") Optional<String> picRelativePath, final Idea idea, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "home";
        }
        if(picRelativePath.isPresent()){
            Foto foto = new Foto();
            foto.setUsuario(idea.getUsuario());
            foto.setUrl(picRelativePath.get().substring(20));
            foto.setIdea(idea);
            idea.getFotos().add(foto);
        }
        ideaService.insertIdea(idea);
        
        model.clear();
        return "redirect:/home";
    }
    
    @PostMapping("FotoUpload")
    public ResponseEntity<String> guardaFoto(String imageData) throws FileNotFoundException, IOException {
        if(imageData.length() > 0)
        {
            Calendar calendar = Calendar.getInstance();
            String relativePath = "food\\" + calendar.get(Calendar.YEAR) + "\\" + (calendar.get(Calendar.MONTH) + 1) + "\\" + calendar.get(Calendar.DAY_OF_MONTH);;
            String extension = imageData.substring(11,imageData.indexOf(";"));
            String fileName = UUID.randomUUID() + "." + extension;
            imageData = imageData.replace("data:image/"+ extension + ";base64,","");
            
            if(SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                relativePath = relativePath.replaceAll("\\\\","/");
                GcsFileOptions instance = GcsFileOptions.getDefaultInstance();
                GcsFilename objectName = new GcsFilename(GOOGLE_STORAGE_BUCKET_NAME, relativePath + "/" + fileName);
                try {
                    GcsOutputChannel outputChannel = gcsService.createOrReplace(objectName, instance);
                             
                    try (   OutputStream out = Channels.newOutputStream(outputChannel); InputStream in = new ByteArrayInputStream(imageData.getBytes()) ) {
                        byte[] buffer = new byte[BUFFER_SIZE];
                        int bytesRead = in.read(buffer);
                        while (bytesRead != -1) {
                            out.write(buffer, 0, bytesRead);
                            bytesRead = in.read(buffer);
                        }
                    } 
                } catch(Exception e) {
                    return ResponseEntity.ok("{ \"success\": false, \"message\": \"Problema con repositorio de imágenes.\" }");
                }  
            } else {
                String basePath = "C:\\Users\\alexis.suarez\\Documents\\NetBeansProjects" + APP_PATH;
                relativePath = FOOD_IMG_RELATIVE_PATH + relativePath;
                File file = new File(basePath + relativePath);
                if(!file.exists())
                    file.mkdirs();
                file = new File(basePath + relativePath + "\\" + fileName);
                file.createNewFile();
                try (FileOutputStream fos = new FileOutputStream(file,false)) {
                    fos.write(Base64.getDecoder().decode(imageData.getBytes()));
                }
            }
            return ResponseEntity.ok("{ \"success\": true, \"message\" : \"El archivo se subió correctamente.\", \"fileRelativePath\": \"" + relativePath.replace("\\","/") + "/" + fileName + "\" }");
        }
        else
        {
            return ResponseEntity.ok("{ \"success\": false, \"message\": \"Archivo vacio.\" }");
        }
    }
    
    @GetMapping("RequestRecientes")
    public String requestIdeas(@RequestParam(value="pag", required = true) int page, Model model) {
        List<Idea> ideas = ideaService.getRecientes(page,RECENTS_PAGE_SIZE);
        model.addAttribute("recientes", ideas);
        model.addAttribute("pag", ideas.size() < RECENTS_PAGE_SIZE? 0 : page+1);
        return "lastest :: recentIdeas";
    }
    
    @RequestMapping(value = "Recientes", method = RequestMethod.GET)
    public String lastestIdeas(@RequestParam(value = "pag",required = false, defaultValue = "1") int page, Model model, HttpServletRequest request) {
        List<Idea> ideas = ideaService.getRecientes(page, RECENTS_PAGE_SIZE);
        model.addAttribute("recientes",ideas);
        model.addAttribute("pag",2);
        return "lastest";
    }
    
    @RequestMapping(value = "/Ingresar", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("usuario") IngresoDto ingresoDto, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        if(!result.hasErrors()) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(ingresoDto.getUsernameOrEmail(), ingresoDto.getPassword());
            authToken.setDetails(new WebAuthenticationDetails(request));
            try {
                Authentication authentication = authManager.authenticate(authToken);
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);
                HttpSession session = request.getSession(true);
                session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
                if(ingresoDto.getRecordar())
                    rememberMeServices.loginSuccess(request, response, authentication);
            } catch (Exception e) {
                result.addError(new ObjectError("usuario","El usuario o contraseña son incorrectos."));
                return new ModelAndView("logIn","usuario",ingresoDto);
            }
        } else {
            result.reject("El usuario o contraseña son incorrectos.");
            return new ModelAndView("logIn","usuario",ingresoDto);
        }
        ModelAndView mv = new ModelAndView("redirect:/home");
        mv.getModelMap().addAttribute("idea",nuevaIdea());
        return mv;
    }
    
    @RequestMapping(value = "/Ingresar", method = RequestMethod.GET)
    public String login(Model model) {
        IngresoDto ingresoDto = new IngresoDto();
        model.addAttribute("usuario",ingresoDto);
        return "logIn";
    }
    
    @RequestMapping(value = "/Registrar", method = RequestMethod.GET)
    public String pantallaRegistro(Model model) {
        RegistroDto registroDto = new RegistroDto();
        model.addAttribute("usuario", registroDto);
        return "registrar";
    }
    
    @RequestMapping(value = "/Registrar", method = RequestMethod.POST)
    public ModelAndView registrar(@ModelAttribute("usuario") @Valid RegistroDto registroDto, BindingResult result, WebRequest request, Errors errors) {
        Usuario usuario = new Usuario();
        if(!result.hasErrors()) {
            usuario = ideaService.insertUsuario(registroDto);
        }
        if(usuario == null) {
            result.rejectValue("", "El nombre de usuario o correo electrónico ya se encuentran registrados.");
        }
        if(result.hasErrors()) {
            return new ModelAndView("registrar","usuario",registroDto);
        }
        else {
            return new ModelAndView("home");
        }
    }
    
    @RequestMapping(value = "/Salir")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            cookieClearingLogoutHandler.logout(request, response, auth);
            request.logout();
        }
        return "redirect:/home";
    }
    
    @RequestMapping(value= "/Bricks")
    public String bricks() {
        return "bricks";
    }
}
