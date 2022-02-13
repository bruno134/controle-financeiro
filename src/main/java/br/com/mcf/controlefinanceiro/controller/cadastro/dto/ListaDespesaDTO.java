package br.com.mcf.controlefinanceiro.controller.cadastro.dto;

import br.com.mcf.controlefinanceiro.model.transacao.Despesa;
import br.com.mcf.controlefinanceiro.model.transacao.PaginaTransacao;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListaDespesaDTO {

    @JsonProperty("paginaAnterior")
    public Integer paginaAnterior;
    @JsonProperty("proximaPagina")
    public Integer proximaPagina;
    @JsonProperty("totalPaginas")
    public Integer totalPaginas;
    @JsonProperty("despesas")
    public List<DespesaDTO> despesas;


    public ListaDespesaDTO(){
        this.despesas = new ArrayList<>();
    }

    public ListaDespesaDTO(PaginaTransacao despesas) {
        this.despesas = DespesaDTO.dtoList(despesas.getTransacoes());
        this.paginaAnterior = despesas.getPaginaAnterior();
        this.proximaPagina = despesas.getProximaPagina();
        this.totalPaginas = despesas.getTotalPaginas();

    }
}
