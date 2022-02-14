package br.com.mcf.controlefinanceiro.model.transacao;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PaginaTransacao {

    private List<Transacao> transacoes;
    private Integer paginaAnterior;
    private Integer proximaPagina;
    private Integer totalPaginas;
    private Integer totalQuantidadeDeItens;
    private Double totalSomaDeItens;

    public PaginaTransacao(){
        this.transacoes = new ArrayList<>();
        this.paginaAnterior = 0;
        this.proximaPagina = 0;
        this.totalPaginas = 0;
        this.totalSomaDeItens = 0d;
        this.totalQuantidadeDeItens = 0;
    }

    public Integer getTotalQuantidadeDeItens() {
        totalQuantidadeDeItens = this.transacoes.size();
        return totalQuantidadeDeItens;
    }
}
