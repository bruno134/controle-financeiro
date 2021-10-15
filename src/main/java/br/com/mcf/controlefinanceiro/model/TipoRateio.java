package br.com.mcf.controlefinanceiro.model;

import java.time.LocalDate;

public class TipoRateio extends BaseDominio{
    public TipoRateio(Long id, String nome, LocalDate dataCriacao) {
        super(id, nome, dataCriacao);
    }

    public TipoRateio(String nomeDominio) {
        super(nomeDominio);
    }
}
