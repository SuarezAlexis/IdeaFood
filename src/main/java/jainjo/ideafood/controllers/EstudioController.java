/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jainjo.ideafood.controllers;

import jainjo.ideafood.model.estudio.Busca;
import jainjo.ideafood.model.estudio.Pregunta;
import jainjo.ideafood.model.estudio.Solicitud;
import jainjo.ideafood.services.estudio.EstudioService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author alexis.suarez
 */
@Controller
public class EstudioController {
    private static List<Pregunta> preguntas;
    
    @Autowired
    private EstudioService estudioService;
    
    @ModelAttribute("materias")
    public List<String> materias() {
        List<String> materias = new ArrayList<String>();
        materias.add("Español");
        materias.add("Matemáticas");
        materias.add("Física");
        materias.add("Química");
        materias.add("Biología");
        materias.add("Historia Universal");
        materias.add("Historia de México");
        materias.add("Literatura");
        materias.add("Geografía");
        return materias;
    }
    
    @ModelAttribute("preguntas")
    public List<Pregunta> preguntas() {
        return preguntas;
    }
    
    @ModelAttribute("sec")
    public Authentication authorize() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    
    @GetMapping("Estudio")
    public String estudioHome(Model model) {
        model.addAttribute("pregunta",new Pregunta());
        model.addAttribute("busca", new Busca());
        model.addAttribute("solicitud", new Solicitud());
        return "estudio.html";
    }
    
    @PostMapping("Pregunta")
    public String pregunta(@ModelAttribute("pregunta") Pregunta pregunta) {
        estudioService.preguntaDao.insert(pregunta);
        this.preguntas = null;
        return "redirect:Estudio";
    }
    
    @PostMapping("Busca")
    public String busca(@ModelAttribute("busca")Busca busca, ModelMap model) {
        if(busca.getId() > 0) {
            preguntas = new ArrayList<>();
            preguntas.add(estudioService.preguntaDao.find(busca.getId()));
        }
        else {
            preguntas = estudioService.preguntaDao.find(busca.getMateria(),busca.getUnidad(),busca.getTexto());
        }
        model.clear();
        return "redirect:Estudio";
    }
    
    @PostMapping("Examen")
    public String examen(@ModelAttribute("solicitud")Solicitud solicitud, Model model) {
        return "examen.html";
    }
}
