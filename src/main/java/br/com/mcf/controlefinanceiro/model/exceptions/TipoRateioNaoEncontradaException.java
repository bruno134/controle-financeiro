package br.com.mcf.controlefinanceiro.model.exceptions;

import lombok.Getter;

public class TipoRateioNaoEncontradaException extends Throwable {
    @Getter
    private final String mensagem;

    public TipoRateioNaoEncontradaException(String tipoRateioMensagem) {
        this.mensagem = tipoRateioMensagem;
    }
}
