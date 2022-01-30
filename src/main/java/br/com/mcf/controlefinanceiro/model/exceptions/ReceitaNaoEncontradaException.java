package br.com.mcf.controlefinanceiro.model.exceptions;

import lombok.Getter;

public class ReceitaNaoEncontradaException extends Throwable{

    @Getter
    private final String message;

    public ReceitaNaoEncontradaException(String message) {
        this.message = message;
    }
}
