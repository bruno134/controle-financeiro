package br.com.mcf.controlefinanceiro.exceptions;

import lombok.Getter;

public class TransacaoNaoEncontradaException extends Throwable {

    @Getter
    private final String message;

    public TransacaoNaoEncontradaException(String message) {
        this.message = message;
    }
}
