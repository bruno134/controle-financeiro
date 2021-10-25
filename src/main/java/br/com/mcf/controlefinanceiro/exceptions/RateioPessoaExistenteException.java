package br.com.mcf.controlefinanceiro.exceptions;

public class RateioPessoaExistenteException extends Throwable {

    private String message;

    public RateioPessoaExistenteException(String rateioPessoaExistente) {
        this.message = rateioPessoaExistente;
    }
}
