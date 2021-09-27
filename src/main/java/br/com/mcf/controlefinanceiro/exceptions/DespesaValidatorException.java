package br.com.mcf.controlefinanceiro.exceptions;

import lombok.Getter;

import java.util.Collection;
import br.com.fluentvalidator.context.Error;


public class DespesaValidatorException extends Exception {
    @Getter
    private final Collection<Error> errors;

     public DespesaValidatorException(Collection<Error> errors) {
         this.errors = errors;
     }
}
