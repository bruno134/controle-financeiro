package br.com.mcf.controlefinanceiro.model;

import java.time.LocalDate;

public class Instrumento extends BaseDominio{
    public Instrumento(String nome) {
        super(nome);
    }

    public Instrumento(Long id, String nome, LocalDate dataCriacao) {
        super(id, nome, dataCriacao);
    }
}
