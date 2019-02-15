package jainjo.ideafood.services;

import jainjo.ideafood.model.TipoAlimento;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

public class TipoAlimentoFormatter implements Formatter<TipoAlimento> {

    public TipoAlimentoFormatter() {
        super();
    }
    
    @Override
    public String print(TipoAlimento object, Locale locale) {
        return Short.toString(object.getId());
    }

    @Override
    public TipoAlimento parse(String text, Locale locale) throws ParseException {
        return new TipoAlimento(Short.parseShort(text));
    }
    
}
