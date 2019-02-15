package jainjo.ideafood.model;

public class Voto {
    private Usuario usuario;
    private Idea idea;

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public short getAtencion() {
        return atencion;
    }

    public void setAtencion(short atencion) {
        this.atencion = atencion;
    }
    private short sabor;
    private short balance;
    private short ligero;
    private short facilidad;
    private short higiene;
    private short atencion;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public short getSabor() {
        return sabor;
    }

    public void setSabor(short sabor) {
        this.sabor = sabor;
    }

    public short getBalance() {
        return balance;
    }

    public void setBalance(short balance) {
        this.balance = balance;
    }

    public short getLigero() {
        return ligero;
    }

    public void setLigero(short ligero) {
        this.ligero = ligero;
    }

    public short getFacilidad() {
        return facilidad;
    }

    public void setFacilidad(short facilidad) {
        this.facilidad = facilidad;
    }

    public short getHigiene() {
        return higiene;
    }

    public void setHigiene(short higiene) {
        this.higiene = higiene;
    }
    
    
       
}
