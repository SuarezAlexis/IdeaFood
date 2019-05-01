/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jainjo.ideafood.dao.estudio;

import jainjo.ideafood.model.estudio.Pregunta;
import java.util.List;

/**
 *
 * @author alexis.suarez
 */
public interface PreguntaDao {
    public void insert(Pregunta pregunta);
    public void delete(int id);
    public void update(Pregunta pregunta);
    public Pregunta find(int id);
    public List<Pregunta> findAll();
}
