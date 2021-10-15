package br.com.mcf.controlefinanceiro.controller.cadastro.dto;

import br.com.mcf.controlefinanceiro.model.Instrumento;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.mcf.controlefinanceiro.util.ConstantFormat.format;

public class InstrumentoDTO extends BaseDominioDTO implements BaseDTO {

    @JsonCreator
    public InstrumentoDTO(@JsonProperty("nome") String nome) {
        super(nome);
    }

    public InstrumentoDTO(Integer id, String nome, String data) {
        super(id, nome, data);
    }

    public InstrumentoDTO(Instrumento baseDominio) {
        super(baseDominio);
    }

    public static List<InstrumentoDTO> listaDto(List<Instrumento> lista) {

        List<InstrumentoDTO> listaDto = new ArrayList<>();

        lista.forEach(item -> listaDto.add(
            new InstrumentoDTO(item.getId().intValue(),
                    item.getNome(),
                    String.valueOf(item.getDataCriacao()))
        ));

        return listaDto;
    }

    @Override
    public Instrumento toObject() {
        LocalDate dataObjeto = null;
        if(this.getData()!=null&&"".equals(this.getData())){
            dataObjeto = LocalDate.parse(this.getData(),format);
        }
        return new Instrumento((long) (this.getId() == null ? 0 : this.getId()),
                this.getNome()
                ,dataObjeto);
    }
}
