package br.com.mcf.controlefinanceiro.controller.cadastro.dto;

import br.com.mcf.controlefinanceiro.model.Receita;

import java.util.List;

public class ReceitaDTO extends TransacaoDTO{


    public ReceitaDTO(Receita receita){
        super(receita);
    }

    public Receita toObject() {
        return super.toObject(Receita.class);
    }

    public static List<ReceitaDTO> dtoList(List<Receita> list){
        return TransacaoDTO.listaDto(list,ReceitaDTO.class);
    }

    public static List<Receita> listDtoToListObject(List<ReceitaDTO> list){
        return TransacaoDTO.listDtoToListObject(list, Receita.class);
    }

}
