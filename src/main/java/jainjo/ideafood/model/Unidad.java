package jainjo.ideafood.model;

import java.util.ArrayList;
import java.util.List;

public class Unidad {
    private String abr;
    private String nombre;

    public Unidad(String abr, String nombre) {
        this.abr = abr;
        this.nombre = nombre;
    }
    
    public Unidad(String nombre) {
        for(Unidad u : todasLasUnidades()) {
            if(u.getAbr().equals(nombre) || u.getNombre().equals(nombre)) {
                this.setAbr(u.getAbr());
                this.setNombre(u.getNombre());
            }
        }
    }
    
    public static List<Unidad> todasLasUnidades() {
        return new ArrayList<Unidad>() {
            {
                add(new Unidad("pz.","pieza"));
                add(new Unidad("g.","gramo"));
                add(new Unidad("kg.","kilogramo"));
                add(new Unidad("ml.","mililitro"));
                add(new Unidad("l.","litro"));
                add(new Unidad("oz.","onza"));
                add(new Unidad("tz.","taza"));
                add(new Unidad("cda.","cucharada"));
                add(new Unidad("paq.","paquete"));
                add(new Unidad("caja","caja"));
            }
        };        
    }
    
    public String getAbr() {
        return abr;
    }

    public void setAbr(String abr) {
        this.abr = abr;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
