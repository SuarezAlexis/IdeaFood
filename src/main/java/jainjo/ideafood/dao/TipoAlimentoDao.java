package jainjo.ideafood.dao;

import jainjo.ideafood.model.TipoAlimento;
import java.util.List;

public interface TipoAlimentoDao {
    public void insert(TipoAlimento tipoAlimento);
    public void update(TipoAlimento tipoAlimento);
    public void delete(short id);
    public TipoAlimento find(short id);
    public TipoAlimento find(String tipo);
    public List<TipoAlimento> find(int alimentoId);
    public List<TipoAlimento> findAll();
}
