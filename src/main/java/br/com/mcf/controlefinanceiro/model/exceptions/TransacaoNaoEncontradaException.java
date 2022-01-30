package br.com.mcf.controlefinanceiro.model.exceptions;

import lombok.Getter;

public class TransacaoNaoEncontradaException extends Throwable {

    @Getter
    private final String message;

    public TransacaoNaoEncontradaException(String message) {
        this.message = message;
    }
}
