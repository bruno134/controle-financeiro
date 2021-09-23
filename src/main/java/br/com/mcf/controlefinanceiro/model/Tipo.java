package br.com.mcf.controlefinanceiro.model;

import java.time.LocalDate;

public class Tipo extends BaseDominio{
    public Tipo(String nome) {
        super(nome);
    }

    public Tipo(Long id, String nome, LocalDate dataCriacao) {
        super(id, nome, dataCriacao);
    }
}
