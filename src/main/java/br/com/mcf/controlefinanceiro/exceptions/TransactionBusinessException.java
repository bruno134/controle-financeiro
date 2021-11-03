package br.com.mcf.controlefinanceiro.exceptions;

import br.com.fluentvalidator.context.Error;

public class TransactionBusinessException extends Throwable {

    private final String message;
    private final Error error;


    public TransactionBusinessException(Error error) {
        this.error = error;
        this.message = error.getMessage();
    }
}
