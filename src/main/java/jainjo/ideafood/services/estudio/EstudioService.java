/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jainjo.ideafood.services.estudio;

import jainjo.ideafood.dao.estudio.PreguntaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author alexis.suarez
 */
@Service
public class EstudioService {
    @Autowired
    public PreguntaDao preguntaDao;
}
