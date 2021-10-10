package br.com.mcf.controlefinanceiro.controller.cadastro.dto;

import br.com.mcf.controlefinanceiro.model.Tipo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.mcf.controlefinanceiro.util.ConstantFormat.format;

public class TipoDTO extends BaseDominioDTO implements BaseDTO {

    @JsonCreator
    public TipoDTO(@JsonProperty("nome") String nome) {
        super(nome);
    }

    public TipoDTO(Integer id, String nome, String data) {
        super(id, nome, data);
    }

    public TipoDTO(Tipo baseDominio) {
        super(baseDominio);
    }

    public static List<TipoDTO> listaDto(List<Tipo> lista) {

        List<TipoDTO> listaDto = new ArrayList<>();

        lista.forEach(item -> listaDto.add(
            new TipoDTO(item.getId().intValue(),
                    item.getNome(),
                    String.valueOf(item.getDataCriacao()))
        ));

        return listaDto;
    }

    @Override
    public Tipo toObject() {
        LocalDate dataObjeto = null;
        if(this.getData()!=null&&"".equals(this.getData())){
            dataObjeto = LocalDate.parse(this.getData(),format);
        }
        return new Tipo((long) (this.getId() == null ? 0 : this.getId()),
                this.getNome()
                ,dataObjeto);
    }
}
