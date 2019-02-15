package jainjo.ideafood.model;

import java.util.ArrayList;
import java.util.List;

public class Alimento {
    private int id;
    private String nombre;
    private String descripcion;
    private List<TipoAlimento> tipos;
    
    public Alimento() {        
    }
    
    public Alimento(int id) {
        this.id = id;
    }
    
    public Alimento(String nombre) {
        this.nombre = nombre;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public List<TipoAlimento> getTipos() {
        return tipos;
    }
    
    public void setTipos(List<TipoAlimento> tipos) {
        this.tipos = tipos;
    }
    
}
