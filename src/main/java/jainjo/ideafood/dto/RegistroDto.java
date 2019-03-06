package jainjo.ideafood.dto;

import jainjo.ideafood.model.Usuario;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import jainjo.ideafood.services.ValidEmail;
import jainjo.ideafood.services.PasswordMatches;

@PasswordMatches
public class RegistroDto {
    @NotNull
    @NotEmpty
    private String nombre;
    
    @NotNull
    @NotEmpty
    private String userName;
    
    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    private String password;
    
    @NotNull
    @NotEmpty
    private String confirmaPassword;
    
    public RegistroDto() {
        
    }
    
    public RegistroDto(Usuario u) {
        this.userName = u.getUserName();
        this.email = u.getEmail();
        this.nombre = u.getNombre();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getConfirmaPassword() {
        return confirmaPassword;
    }

    public void setConfirmaPassword(String confirmaPassword) {
        this.confirmaPassword = confirmaPassword;
    }
}
