package jainjo.ideafood.dto;

import jainjo.ideafood.services.ValidEmail;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class RecuperaDto {
    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
