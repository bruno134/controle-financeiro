package br.com.mcf.controlefinanceiro.controller.dash.dto;

import br.com.mcf.controlefinanceiro.model.DespesaPessoaConsolidada;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListaDespesaPorDonoDTO {
    @JsonProperty("itens")
    private List<DespesaPorDonoDTO> listaDespesaPorDono;
    @JsonProperty("descricaoDespesaCompartilhada")
    private String descricaoDespesaCompartilhada;
    @JsonProperty("valorTotalDespesaCompartilhada")
    private String valorTotalDespesaCompartilhada;


    public ListaDespesaPorDonoDTO(Map<String, DespesaPessoaConsolidada> listaDespesaPorDono) {

        this.listaDespesaPorDono = new ArrayList<>();
        listaDespesaPorDono.forEach(this::incluiValorLista);

    }

    private void incluiValorLista(String chave, DespesaPessoaConsolidada valor) {
        this.listaDespesaPorDono.add(new DespesaPorDonoDTO(chave,
                                    String.valueOf(valor.getValorTotal()),
                                    String.valueOf(valor.getValorTotalIndividual()),
                                    String.valueOf(valor.getGetValorTotalCompartilhado()),
                                    String.valueOf(valor.getValorTaxa())));
    }
}
