package br.com.mcf.controlefinanceiro.controller.dto;

import br.com.mcf.controlefinanceiro.model.Origem;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.mcf.controlefinanceiro.util.ConstantFormat.format;

public class OrigemDTO extends BaseDominioDTO implements BaseDTO{
    public OrigemDTO(Integer id, String nome, String data) {
        super(id, nome, data);
    }

    @JsonCreator
    public OrigemDTO(@JsonProperty("nome") String nome) {
        super(nome);
    }

    public OrigemDTO(Origem origem) {
        super(origem);
    }

    @Override
    public Origem toObject() {
        LocalDate dataObjeto = null;
        if(this.getData()!=null&&"".equals(this.getData())){
            dataObjeto = LocalDate.parse(this.getData(),format);
        }
        return new Origem((long) (this.getId() == null ? 0 : this.getId()),
                                        this.getNome()
                                        ,dataObjeto);
    }

    public static List<OrigemDTO> listaDto(List<Origem> lista) {
        List<OrigemDTO> listaDTO = new ArrayList<>();

        lista.forEach(item -> {
            var itemDTO =
                    new OrigemDTO(item.getId().intValue(),
                                  item.getNome(),
                            String.valueOf(item.getDataCriacao()));
            listaDTO.add(itemDTO);
        });

        return listaDTO;
    }
}
