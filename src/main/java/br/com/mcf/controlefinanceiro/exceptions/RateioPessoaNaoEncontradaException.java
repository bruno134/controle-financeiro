package br.com.mcf.controlefinanceiro.exceptions;

public class RateioPessoaNaoEncontradaException extends Throwable {

    private String message;

    public RateioPessoaNaoEncontradaException(String rateioNaoEncontrado) {
        this.message = rateioNaoEncontrado;
    }
}
