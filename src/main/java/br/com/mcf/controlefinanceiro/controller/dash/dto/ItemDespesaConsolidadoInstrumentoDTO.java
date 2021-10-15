package br.com.mcf.controlefinanceiro.controller.dash.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDespesaConsolidadoInstrumentoDTO {

    @JsonProperty("descricao")
    private String descricaoItem;
    @JsonProperty("quantidade")
    private String quantidadeItem;
    @JsonProperty("soma")
    private String somaValoresItem;
    @JsonProperty("media")
    private String mediaValoresItem;
    @JsonProperty("valorMinimo")
    private String valorMinimoItem;
    @JsonProperty("valorMaximo")
    private String valorMaximoItem;

}
