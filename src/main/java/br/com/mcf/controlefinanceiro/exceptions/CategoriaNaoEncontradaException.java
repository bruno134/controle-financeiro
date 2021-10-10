package br.com.mcf.controlefinanceiro.exceptions;

import lombok.Getter;

public class CategoriaNaoEncontradaException extends Exception {
    @Getter
    private final String message;

     public CategoriaNaoEncontradaException(String naoEncontradoMsg) {
         this.message = naoEncontradoMsg;
     }
}
