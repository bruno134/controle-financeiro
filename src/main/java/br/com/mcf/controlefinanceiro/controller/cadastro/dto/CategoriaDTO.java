package br.com.mcf.controlefinanceiro.controller.cadastro.dto;

import br.com.mcf.controlefinanceiro.model.Categoria;
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
public class CategoriaDTO {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("data")
    private String data;

    public CategoriaDTO(Integer id, String nome, String data) {
        this.id = id;
        this.nome = nome;
        this.data = data;
    }

    @JsonCreator
    public CategoriaDTO(@JsonProperty("nome") String nome) {
        this.nome = nome;
    }

    public CategoriaDTO(Categoria categoria){
        this.id = categoria.getId().intValue();
        this.nome = categoria.getNome();
        this.data = categoria.getDataCriacao().format(format);
    }

    public static List<CategoriaDTO> listaDto(List<Categoria> lista) {
        List<CategoriaDTO> listaDTO = new ArrayList<>();

        lista.forEach(categoria -> {
            var categoriaDTO =
                    new CategoriaDTO(categoria.getId().intValue(),
                                         categoria.getNome(),
                                         String.valueOf(categoria.getDataCriacao()));
            listaDTO.add(categoriaDTO);
        });

        return listaDTO;
    }

    public Categoria toCategoria() {
        LocalDate dataCriacao = null;

        if(this.data!=null && !this.data.equals("")) {
            dataCriacao = LocalDate.parse(getData(), format);
        }
        return new Categoria(Long.valueOf(this.id),this.nome, dataCriacao);
    }
}
