package br.com.mcf.controlefinanceiro.controller.cadastro.dto;

import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.model.Receita;
import br.com.mcf.controlefinanceiro.model.TipoTransacao;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.mcf.controlefinanceiro.util.ConstantFormat.format;

@Getter
@Setter
public class DespesaDTO extends TransacaoDTO{

    public DespesaDTO (){

    }

    public DespesaDTO(Despesa despesa){
        super(despesa);
    }

    public DespesaDTO(Integer id, LocalDate data, String valor, String descricao, String categoria, String tipoRateio, String instrumento) {
        super(id,data,valor,descricao,categoria,tipoRateio,instrumento);
    }
    public DespesaDTO(Integer id, String data, String valor, String descricao, String categoria, String tipoRateio, String instrumento) {
        super(id,data,valor,descricao,categoria,tipoRateio,instrumento);
    }

    public DespesaDTO(String data, String valor, String descricao, String categoria, String tipoRateio, String instrumento) {
        super(null,data,valor,descricao,categoria,tipoRateio,instrumento);
    }

    public Despesa toObject() {
        return super.toObject(Despesa.class);
    }

    public static List<DespesaDTO> dtoList(List<Despesa> list){

        return TransacaoDTO.listaDto(list,DespesaDTO.class);
    }

    public static List<Despesa> listDtoToListObject(List<DespesaDTO> list){
        return TransacaoDTO.listDtoToListObject(list, Despesa.class);
    }
}
