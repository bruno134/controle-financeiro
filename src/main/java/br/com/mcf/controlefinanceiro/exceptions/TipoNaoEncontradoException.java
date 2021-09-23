package br.com.mcf.controlefinanceiro.exceptions;

import lombok.Getter;

public class TipoNaoEncontradoException extends Throwable {
    @Getter
    private final String mensagem;

    public TipoNaoEncontradoException(String tipoNaoEncontrado) {
        this.mensagem = tipoNaoEncontrado;
    }
}
