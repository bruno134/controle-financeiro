package br.com.mcf.controlefinanceiro.model;

import java.time.LocalDate;

public class Origem extends BaseDominio{
    public Origem(Long id, String nome, LocalDate dataCriacao) {
        super(id, nome, dataCriacao);
    }

    public Origem(String nomeDominio) {
        super(nomeDominio);
    }
}
