package br.com.mcf.controlefinanceiro.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListaTransacao <T> {

    private List<T> transacoes;
    private Integer paginaAnterior;
    private Integer proximaPagina;
    private Integer totalPaginas;

    public ListaTransacao(){
        this.transacoes = new ArrayList<>();
        this.paginaAnterior = 0;
        this.proximaPagina = 0;
        this.totalPaginas = 0;
    }
}