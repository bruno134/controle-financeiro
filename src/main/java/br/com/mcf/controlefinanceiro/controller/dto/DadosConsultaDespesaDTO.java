package br.com.mcf.controlefinanceiro.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosConsultaDespesaDTO {

    private String ano;
    private String mes;
    private String identificador;

}
