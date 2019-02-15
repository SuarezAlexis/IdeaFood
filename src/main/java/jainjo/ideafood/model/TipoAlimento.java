package jainjo.ideafood.model;

public class TipoAlimento {
    private short id;
    private String tipo; 
    private String descripcion;

    public TipoAlimento() {
        
    }
    
    public TipoAlimento(short id) {
        this.id = id;
    }
    
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
