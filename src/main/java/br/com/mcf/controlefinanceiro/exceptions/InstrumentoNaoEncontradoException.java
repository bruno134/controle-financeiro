package br.com.mcf.controlefinanceiro.exceptions;

import lombok.Getter;

public class InstrumentoNaoEncontradoException extends Throwable {
    @Getter
    private final String mensagem;

    public InstrumentoNaoEncontradoException(String instrumentoNaoEncontrado) {
        this.mensagem = instrumentoNaoEncontrado;
    }
}
