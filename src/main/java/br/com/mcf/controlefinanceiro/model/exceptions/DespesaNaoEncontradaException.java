package br.com.mcf.controlefinanceiro.model.exceptions;

import lombok.Getter;

public class DespesaNaoEncontradaException extends Exception {

    @Getter
    private final String message;

    public DespesaNaoEncontradaException(String naoEncontradoMsg) {
        this.message = naoEncontradoMsg;
    }
}
