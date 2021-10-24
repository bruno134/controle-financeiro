package br.com.mcf.controlefinanceiro.exceptions;

import lombok.Getter;

public class ReceitaNaoEncontradaException extends Throwable{

    @Getter
    private final String message;

    public ReceitaNaoEncontradaException(String message) {
        this.message = message;
    }
}
