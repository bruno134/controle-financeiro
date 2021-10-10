package br.com.mcf.controlefinanceiro.controller.dash.dto;


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
public class DashDTO {
    @JsonProperty("itens")
    private List<ItemDespesaConsolidadoCategoriaDTO> itensPorCategoria;
    @JsonProperty("itensTipo")
    private List<ItemDespesaConsolidadoTipoDTO> itensPorTipo;


    public void setItensPorCategoria(Map<String, DoubleSummaryStatistics> itensPorCategoria) {

        this.itensPorCategoria = new ArrayList<>();
        itensPorCategoria.forEach( (mapKey, mapItem) -> {
            this.itensPorCategoria.add(new ItemDespesaConsolidadoCategoriaDTO(mapKey, String.valueOf(mapItem.getCount())
                                                    ,String.valueOf(mapItem.getSum())
                                                    ,String.valueOf(mapItem.getAverage())
                                                    ,String.valueOf(mapItem.getMin())
                                                    ,String.valueOf(mapItem.getMax()))
            );
        });
    }

    public void setItensPorTipo(Map<String, DoubleSummaryStatistics> itensPorTipo){
        this.itensPorTipo = new ArrayList<>();
        itensPorTipo.forEach(((mapKey, mapItem)  -> {
            this.itensPorTipo.add(new ItemDespesaConsolidadoTipoDTO(mapKey, String.valueOf(mapItem.getCount())
                    ,String.valueOf(mapItem.getSum())
                    ,String.valueOf(mapItem.getAverage())
                    ,String.valueOf(mapItem.getMin())
                    ,String.valueOf(mapItem.getMax()))
            );
        }));
    }
}
