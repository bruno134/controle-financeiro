package br.com.mcf.controlefinanceiro.controller.dash.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DespesaMesDTO {

    private String mesDespesa;
    private String  valorTotalMes;

    public DespesaMesDTO(String mesDespesa, String valorTotalMes) {
        this.mesDespesa = mesDespesa;
        this.valorTotalMes = valorTotalMes;
    }
}
