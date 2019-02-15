package jainjo.ideafood.dao;

import jainjo.ideafood.model.Foto;

public interface FotoDao {
    public void insert(Foto foto);
    public void update(Foto foto);
    public void delete(long id);
    public Foto find(long id);
}
