package br.com.mcf.controlefinanceiro.controller.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DespesaConsolidadaDTO {
    @JsonProperty("itens")
    private List<ItemDespesaConsolidadoDTO> itens;


    public void setItens(Map<String, DoubleSummaryStatistics> itens) {

        this.itens = new ArrayList<>();
        itens.forEach( (mapKey, mapItem) -> {
            this.itens.add(new ItemDespesaConsolidadoDTO(mapKey, String.valueOf(mapItem.getCount())
                                                    ,String.valueOf(mapItem.getSum())
                                                    ,String.valueOf(mapItem.getAverage())
                                                    ,String.valueOf(mapItem.getMin())
                                                    ,String.valueOf(mapItem.getMax()))
            );
        });
    }
}
