package br.com.mcf.controlefinanceiro.controller.cadastro.dto;

import br.com.mcf.controlefinanceiro.model.TipoRateio;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.mcf.controlefinanceiro.util.ConstantFormat.format;

public class TipoRateioDTO extends BaseDominioDTO implements BaseDTO {
    public TipoRateioDTO(Integer id, String nome, String data) {
        super(id, nome, data);
    }

    @JsonCreator
    public TipoRateioDTO(@JsonProperty("nome") String nome) {
        super(nome);
    }

    public TipoRateioDTO(TipoRateio tipoRateio) {
        super(tipoRateio);
    }

    @Override
    public TipoRateio toObject() {
        LocalDate dataObjeto = null;
        if(this.getData()!=null&&"".equals(this.getData())){
            dataObjeto = LocalDate.parse(this.getData(),format);
        }
        return new TipoRateio((long) (this.getId() == null ? 0 : this.getId()),
                                        this.getNome()
                                        ,dataObjeto);
    }

    public static List<TipoRateioDTO> listaDto(List<TipoRateio> lista) {
        List<TipoRateioDTO> listaDTO = new ArrayList<>();

        lista.forEach(item -> {
            var itemDTO =
                    new TipoRateioDTO(item.getId().intValue(),
                                  item.getNome(),
                            String.valueOf(item.getDataCriacao()));
            listaDTO.add(itemDTO);
        });

        return listaDTO;
    }
}
