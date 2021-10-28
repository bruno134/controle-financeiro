package br.com.mcf.controlefinanceiro.controller.dash.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DespesaPorDonoDTO {

    @JsonProperty("nomeDono")
    private String nomeDono;
    @JsonProperty("valorTotal")
    private String valorTotal;
    @JsonProperty("valorTotalIndividual")
    private String valorTotalIndividual;
    @JsonProperty("valorTotalCompartilhado")
    private String valorTotalCompartilhado;
    @JsonProperty("valorTaxa")
    private String valorTaxa;

    public DespesaPorDonoDTO(String nomeDono, String valorTotal, String valorTotalIndividual, String valorTotalCompartilhado, String valorTaxa) {
        this.nomeDono = nomeDono;
        this.valorTotal = valorTotal;
        this.valorTotalIndividual = valorTotalIndividual;
        this.valorTotalCompartilhado = valorTotalCompartilhado;
        this.valorTaxa = valorTaxa;
    }
}
