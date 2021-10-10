package br.com.mcf.controlefinanceiro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DespesaConsolidada {
    private String categoria;
    private BigDecimal valorTotal;
    private Integer quantidade;
}
