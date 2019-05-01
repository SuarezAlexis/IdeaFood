/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jainjo.ideafood.model.estudio;

/**
 *
 * @author alexis.suarez
 */
public class Pregunta {
    private int id;
    private String pregunta;
    private String incisoA;
    private String incisoB;
    private String incisoC;
    private String incisoD;
    private char respuesta;
    private String materia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getIncisoA() {
        return incisoA;
    }

    public void setIncisoA(String incisoA) {
        this.incisoA = incisoA;
    }

    public String getIncisoB() {
        return incisoB;
    }

    public void setIncisoB(String incisoB) {
        this.incisoB = incisoB;
    }

    public String getIncisoC() {
        return incisoC;
    }

    public void setIncisoC(String incisoC) {
        this.incisoC = incisoC;
    }

    public String getIncisoD() {
        return incisoD;
    }

    public void setIncisoD(String incisoD) {
        this.incisoD = incisoD;
    }

    public char getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(char respuesta) {
        this.respuesta = respuesta;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
    
    
}
