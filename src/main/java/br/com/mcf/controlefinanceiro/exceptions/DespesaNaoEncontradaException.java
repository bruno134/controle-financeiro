package br.com.mcf.controlefinanceiro.exceptions;

import lombok.Getter;

public class DespesaNaoEncontradaException extends Exception {

    @Getter
    private String message;

    public DespesaNaoEncontradaException(String naoEncontradoMsg) {
        this.message = naoEncontradoMsg;
    }
}
