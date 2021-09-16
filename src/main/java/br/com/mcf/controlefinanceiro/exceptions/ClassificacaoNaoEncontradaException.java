package br.com.mcf.controlefinanceiro.exceptions;

import lombok.Getter;

public class ClassificacaoNaoEncontradaException extends Exception {
    @Getter
    private String message;

     public ClassificacaoNaoEncontradaException(String naoEncontradoMsg) {
         this.message = naoEncontradoMsg;
     }
}
