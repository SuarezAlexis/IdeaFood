package jainjo.ideafood.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Idea {
    private long id;
    private Alimento alimento;
    private Usuario usuario;
    private short porciones;
    private double costo;
    private short tiempo;
    private Date fechaCreacion;         
    private List<Foto> fotos;
    private List<Voto> votos;
    private String receta;
    private List<IngredienteItem> ingredientes;
    private PuntoVenta puntoVenta;

    public Idea() {
        this.alimento = new Alimento();
        this.usuario = new Usuario(); 
        this.fotos = new ArrayList<Foto>();
        this.ingredientes = new ArrayList<IngredienteItem>();
        this.puntoVenta = new PuntoVenta();
    }
    
    public Alimento getAlimento() {
        return alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }

    public PuntoVenta getPuntoVenta() {
        return puntoVenta;
    }

    public void setPuntoVenta(PuntoVenta puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public short getPorciones() {
        return porciones;
    }

    public void setPorciones(short porciones) {
        this.porciones = porciones;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public short getTiempo() {
        return tiempo;
    }

    public void setTiempo(short tiempo) {
        this.tiempo = tiempo;
    }

    public List<IngredienteItem> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteItem> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

        
    
}
