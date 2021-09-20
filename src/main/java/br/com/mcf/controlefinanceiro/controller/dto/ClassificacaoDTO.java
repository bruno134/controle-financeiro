package br.com.mcf.controlefinanceiro.controller.dto;

import br.com.mcf.controlefinanceiro.model.Classificacao;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.mcf.controlefinanceiro.util.ConstantFormat.format;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassificacaoDTO {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("data")
    private String data;

    public ClassificacaoDTO(Integer id, String nome, String data) {
        this.id = id;
        this.nome = nome;
        this.data = data;
    }

    @JsonCreator
    public ClassificacaoDTO( @JsonProperty("nome") String nome) {
        this.nome = nome;
    }

    public ClassificacaoDTO(Classificacao classificacao){
        this.id = classificacao.getId().intValue();
        this.nome = classificacao.getNome();
        this.data = classificacao.getDataCriacao().format(format);
    }

    public static List<ClassificacaoDTO> listaDto(List<Classificacao> lista) {
        List<ClassificacaoDTO> listaDTO = new ArrayList<>();

        lista.forEach(classificacao -> {
            var classificacaoDTO =
                    new ClassificacaoDTO(classificacao.getId().intValue(),
                                         classificacao.getNome(),
                                         String.valueOf(classificacao.getDataCriacao()));
            listaDTO.add(classificacaoDTO);
        });

        return listaDTO;
    }

    public Classificacao toClassificacao() {
        LocalDate dataCriacao = null;

        if(this.data!=null && !this.data.equals("")) {
            dataCriacao = LocalDate.parse(getData(), format);
        }
        return new Classificacao(Long.valueOf(this.id),this.nome, dataCriacao);
    }
}
