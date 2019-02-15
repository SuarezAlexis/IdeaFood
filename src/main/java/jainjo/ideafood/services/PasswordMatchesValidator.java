package jainjo.ideafood.services;

import jainjo.ideafood.dto.RegistroDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object registroDto, ConstraintValidatorContext context) {
        RegistroDto registro = (RegistroDto) registroDto;
        return registro.getPassword().equals(registro.getConfirmaPassword());
    }
}
