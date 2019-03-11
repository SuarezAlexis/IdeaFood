package jainjo.ideafood.model;

import java.util.Date;
import java.util.List;

public class Usuario {
    private String userName;
    private String nombre;
    private String password;
    private String email;
    private String foto;
    private String passwordResetToken;

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public Date getPasswordResetExpiration() {
        return passwordResetExpiration;
    }

    public void setPasswordResetExpiration(Date passwordResetExpiration) {
        this.passwordResetExpiration = passwordResetExpiration;
    }
    private Date passwordResetExpiration;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    private List<String> permisos;
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<String> permisos) {
        this.permisos = permisos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
