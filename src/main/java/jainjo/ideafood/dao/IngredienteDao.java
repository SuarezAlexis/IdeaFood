package jainjo.ideafood.dao;

import jainjo.ideafood.model.Ingrediente;
import java.util.List;

public interface IngredienteDao {
    public void insert(Ingrediente ingrediente);
    public void update(Ingrediente ingrediente);
    public void delete(long id);
    public Ingrediente find(short id);
    public Ingrediente find(String nombre);
    public List<Ingrediente> findAll();
}
