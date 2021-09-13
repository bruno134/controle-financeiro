package br.com.mcf.controlefinanceiro.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ErroBaseDTO {

    @JsonProperty
    private Integer codigo;
    @JsonProperty
    private String mensagem;

    public ErroBaseDTO(Integer codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    public ErroBaseDTO(){

    }
}
