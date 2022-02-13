package br.com.mcf.controlefinanceiro.controller.cadastro.dto;

import br.com.mcf.controlefinanceiro.model.transacao.Despesa;
import br.com.mcf.controlefinanceiro.model.transacao.Transacao;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;

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

    public DespesaDTO(Integer id, LocalDate data, String valor, String descricao, String categoria, String tipoRateio, String instrumento, LocalDate dataCompetencia) {
        super(id,data,valor,descricao,categoria,tipoRateio,instrumento,dataCompetencia);
    }

    public DespesaDTO(Integer id, String data, String valor, String descricao, String categoria, String tipoRateio, String instrumento) {
        super(id,data,valor,descricao,categoria,tipoRateio,instrumento);
    }

    public DespesaDTO(String data, String valor, String descricao, String categoria, String tipoRateio, String instrumento) {
        super(null,data,valor,descricao,categoria,tipoRateio,instrumento);
    }

    public Despesa toObject() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return super.toObject(Despesa.class);
    }

    public static List<DespesaDTO> dtoList(List<Transacao> list){

        return TransacaoDTO.listaDto(list,DespesaDTO.class);
    }

    public static List<Despesa> listDtoToListObject(List<DespesaDTO> list){
        return TransacaoDTO.listDtoToListObject(list, Despesa.class);
    }
}
