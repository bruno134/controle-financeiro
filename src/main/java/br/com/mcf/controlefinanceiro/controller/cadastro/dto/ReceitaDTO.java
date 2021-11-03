package br.com.mcf.controlefinanceiro.controller.cadastro.dto;

import br.com.mcf.controlefinanceiro.model.Receita;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;

import static br.com.mcf.controlefinanceiro.util.ConstantFormat.format;

@Getter
@Setter
public class ReceitaDTO extends TransacaoDTO{

    public ReceitaDTO(){
    }

    public ReceitaDTO(Receita receita){

        super(receita);
    }

    public ReceitaDTO(Integer id, LocalDate data, String valor, String descricao, String categoria, String tipoRateio, String instrumento) {
        super(id,data,valor,descricao,categoria,tipoRateio,instrumento);
    }
    public ReceitaDTO(Integer id, String data, String valor, String descricao, String categoria, String tipoRateio, String instrumento) {
        super(id,data,valor,descricao,categoria,tipoRateio,instrumento);
    }

    public ReceitaDTO(String data, String valor, String descricao, String categoria, String tipoRateio, String instrumento) {
        super(null,data,valor,descricao,categoria,tipoRateio,instrumento);
    }

    public Receita toObject() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return super.toObject(Receita.class);
    }

    public static List<ReceitaDTO> dtoList(List<Receita> list){

        return TransacaoDTO.listaDto(list,ReceitaDTO.class);
    }

    public static List<Receita> listDtoToListObject(List<ReceitaDTO> list){
        return TransacaoDTO.listDtoToListObject(list, Receita.class);
    }

}
