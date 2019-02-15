package jainjo.ideafood.dao;

import jainjo.ideafood.model.Alimento;
import java.util.List;

public interface AlimentoDao {
    public void insert(Alimento alimento);
    public void update(Alimento alimento);
    public void delete(int id);
    public Alimento find(int id);
    public Alimento find(String nombre);
    public List<Alimento> findAll();
}
