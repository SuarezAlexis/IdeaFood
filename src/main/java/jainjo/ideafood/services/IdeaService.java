package jainjo.ideafood.services;

import jainjo.ideafood.dto.RegistroDto;
import jainjo.ideafood.dao.*;
import jainjo.ideafood.model.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IdeaService {
    
    @Autowired
    private IngredienteDao ingredienteDao;
    @Autowired
    private TipoAlimentoDao tipoAlimentoDao;
    @Autowired
    private AlimentoDao alimentoDao;
    @Autowired
    private IdeaDao ideaDao;
    @Autowired 
    private UsuarioDao usuarioDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
        
    public List<TipoAlimento> tiposAlimento() {
        return tipoAlimentoDao.findAll();
    }

    public List<Ingrediente> todosLosIngredientes() {
        return ingredienteDao.findAll();
    }

    public List<Alimento> todosLosAlimentos() {
        return alimentoDao.findAll();
    }
    
    public Alimento insertAlimento(Alimento alimento) {
        alimentoDao.insert(alimento);
        return alimento;
    }
    
    public Idea insertIdea(Idea idea) {
        if( idea.getAlimento().getId() == 0) {
            insertAlimento(idea.getAlimento());
        }
        ideaDao.insert(idea);
        return idea;
    }
    
    public List<Idea> getRecientes(int page, int pageSize) {
        return ideaDao.findRecent(page, pageSize);
    }
    
    public List<Idea> getSugerencias(int page, int pageSize) {
        return ideaDao.findSuggestions(page, pageSize);
    }
    
    public Usuario insertUsuario(RegistroDto registro) {
        Usuario usuario = new Usuario();
        usuario.setNombre(registro.getNombre());
        usuario.setEmail(registro.getEmail());
        usuario.setPassword(passwordEncoder.encode(registro.getPassword()));
        usuario.setUserName(registro.getUserName());
        List<String> permisos = new ArrayList<String>();
        permisos.add("usuario");
        usuario.setPermisos(permisos);
        try {
            usuarioDao.insert(usuario);
        } catch(DuplicateKeyException dke) {
            return null;
        }
        return usuario;
    }
    
    public void updateScore(String userName, int score) {
        Usuario usuario = new Usuario();
        usuario.setUserName(userName);
        usuario.setScore(score);
        try {
            usuarioDao.update(usuario);
        } catch(Exception e) {
            
        }
    }
    
    public int getScore(String userName) {
        return usuarioDao.find(userName).getScore();
    }
    
    public List<Usuario> getHighScores() {
        return usuarioDao.findHighScores(3);
    }
    
    public Usuario getUsuario(String userName) {
        return usuarioDao.find(userName);
    }

    public Usuario updateUsuario(RegistroDto registro) {
        Usuario usuario = new Usuario();
        if(registro.getNombre() != null && !registro.getNombre().isEmpty())
            usuario.setNombre(registro.getNombre());
        if(registro.getEmail() != null && !registro.getEmail().isEmpty())
            usuario.setEmail(registro.getEmail());
        if(registro.getPassword() != null && !registro.getPassword().isEmpty())
            usuario.setPassword(passwordEncoder.encode(registro.getPassword()));
        usuario.setUserName(registro.getUserName());
        usuario.setFoto(registro.getFoto());
        try {
            usuarioDao.update(usuario);
        } catch(Exception e) {
            return null;
        }
        return usuario;
    }
}
