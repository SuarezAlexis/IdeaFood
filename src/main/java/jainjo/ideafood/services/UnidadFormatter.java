package jainjo.ideafood.services;

import jainjo.ideafood.model.Unidad;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

public class UnidadFormatter implements Formatter<Unidad>{

    public UnidadFormatter() {
        super();
    }
    
    @Override
    public String print(Unidad object, Locale locale) {
        return object.getNombre();
    }

    @Override
    public Unidad parse(String text, Locale locale) throws ParseException {
        return new Unidad(text);
    }
    
}
