package jainjo.ideafood.dao;

import jainjo.ideafood.model.PuntoVenta;
import java.util.List;


public interface PuntoVentaDao {
    public void insert(PuntoVenta pv);
    public void update(PuntoVenta pv);
    public void delete(int id);
    public PuntoVenta find(int id);
    public List<PuntoVenta> findAll();
}
