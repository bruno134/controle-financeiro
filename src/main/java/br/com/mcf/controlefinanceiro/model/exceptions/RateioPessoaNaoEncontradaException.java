package br.com.mcf.controlefinanceiro.model.exceptions;

public class RateioPessoaNaoEncontradaException extends Throwable {

    private final String message;

    public RateioPessoaNaoEncontradaException(String rateioNaoEncontrado) {
        this.message = rateioNaoEncontrado;
    }
}
