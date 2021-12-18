package br.com.mcf.controlefinanceiro.controller.cadastro.dto;

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
    private String pagina;
    private String tamanhoPagina;

    public DadosConsultaDespesaDTO(String ano, String mes, String pagina, String tamanhoPagina) {
        this.ano = ano;
        this.mes = mes;
        this.pagina = pagina;
        this.tamanhoPagina = tamanhoPagina;
    }

    public DadosConsultaDespesaDTO(String ano, String mes) {
        this.ano = ano;
        this.mes = mes;
    }

    public DadosConsultaDespesaDTO(String ano, String mes, String identificador) {
        this.ano = ano;
        this.mes = mes;
        this.identificador = identificador;
    }
}
