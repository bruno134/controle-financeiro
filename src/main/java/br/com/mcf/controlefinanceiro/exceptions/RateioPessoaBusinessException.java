package br.com.mcf.controlefinanceiro.exceptions;

import br.com.fluentvalidator.context.Error;
import lombok.Getter;

@Getter
public class RateioPessoaBusinessException extends Throwable {

    private final String message;
    private final Error error;

    public RateioPessoaBusinessException(Error error) {
        this.message = error.getMessage();
        this.error = error;

    }
}
