package br.com.mcf.controlefinanceiro.model;

import br.com.mcf.controlefinanceiro.entity.TransacaoEntity;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Receita extends Transacao{

    public Receita(){
        super.setTipoTransacao(TipoTransacao.RECEITA);
    }

    public Receita(LocalDate data, Double valor, String descricao, String categoria, String tipoRateio, String instrumento) {
        super(data,
                valor,
                descricao,
                categoria,
                tipoRateio,
                instrumento,
                TipoTransacao.RECEITA);
    }
    public Receita(Integer id, LocalDate data, Double valor, String descricao, String categoria, String tipoRateio, String instrumento) {
        super(  id,
                data,
                valor,
                descricao,
                categoria,
                tipoRateio,
                instrumento,
                TipoTransacao.RECEITA);
    }

    public Receita(TransacaoEntity entity){
        super(  entity.getId().intValue(),
                entity.getData(),
                entity.getValor(),
                entity.getDescricao(),
                entity.getCategoria(),
                entity.getTipoRateio(),
                entity.getInstrumento(),
                TipoTransacao.RECEITA);
    }

}
