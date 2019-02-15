package jainjo.ideafood.services;

import jainjo.ideafood.model.Alimento;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

public class AlimentoFormatter implements Formatter<Alimento> {

    @Autowired
    private IdeaService ideaService;
    
    public AlimentoFormatter() {
        super();
    }
    
    @Override
    public String print(Alimento object, Locale locale) {
        return object.getNombre();
    }

    @Override
    public Alimento parse(String text, Locale locale) throws ParseException {
        for(Alimento a : ideaService.todosLosAlimentos()) {
            if(a.getNombre().equals(text))
                return a;
        }
        return  new Alimento(text);
    }
    
}
