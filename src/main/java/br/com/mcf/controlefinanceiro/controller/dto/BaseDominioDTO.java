package br.com.mcf.controlefinanceiro.controller.dto;

import br.com.mcf.controlefinanceiro.model.BaseDominio;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static br.com.mcf.controlefinanceiro.util.ConstantFormat.format;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDominioDTO {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("data")
    private String data;

    public BaseDominioDTO(Integer id, String nome, String data) {
        this.id = id;
        this.nome = nome;
        this.data = data;
    }

    public BaseDominioDTO(BaseDominio baseDominio){
        this.id = baseDominio.getId().intValue();
        this.nome = baseDominio.getNome();
        this.data = baseDominio.getDataCriacao().format(format);
    }

    @JsonCreator
    public BaseDominioDTO(@JsonProperty("nome") String nome) {
        this.nome = nome;
    }


}
