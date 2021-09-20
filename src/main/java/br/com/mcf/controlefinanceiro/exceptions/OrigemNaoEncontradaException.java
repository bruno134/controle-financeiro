package br.com.mcf.controlefinanceiro.exceptions;

import lombok.Getter;

public class OrigemNaoEncontradaException extends Throwable {
    @Getter
    private final String mensagem;

    public OrigemNaoEncontradaException(String origemNaoEncontrada) {
        this.mensagem = origemNaoEncontrada;
    }
}
