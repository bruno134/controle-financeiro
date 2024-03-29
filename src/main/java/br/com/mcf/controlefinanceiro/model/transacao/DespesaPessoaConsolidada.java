package br.com.mcf.controlefinanceiro.model.transacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DespesaPessoaConsolidada {
    private Double valorTotal;
    private Double valorTotalIndividual;
    private Double getValorTotalCompartilhado;
    private Double valorTaxa;
    private Double valorSalario;
}
