package jainjo.ideafood.model;

public class IngredienteItem {
    private Ingrediente ingrediente;
    private float cantidad;
    private Unidad unidad;

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }
    
    @Override
    public String toString() {
        return cantidad + " " + unidad.getNombre() + "s " + "de " + ingrediente.getNombre();
    }
}
