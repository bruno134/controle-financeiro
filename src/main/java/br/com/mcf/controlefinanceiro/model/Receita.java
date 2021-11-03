package br.com.mcf.controlefinanceiro.model;

import br.com.mcf.controlefinanceiro.entity.TransacaoEntity;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Receita extends Transacao{

    public Receita(){
        super.setTipoTransacao(TipoTransacao.RECEITA);
    }

    public Receita(LocalDate data, Double valor, String descricao, String categoria, String tipoRateio,
                   String instrumento, LocalDate dataCompetencia) {
        super(data,
                valor,
                descricao,
                categoria,
                tipoRateio,
                instrumento,
                TipoTransacao.RECEITA,
                dataCompetencia);
    }
    public Receita(Integer id, LocalDate data, Double valor, String descricao, String categoria,
                   String tipoRateio, String instrumento, LocalDate dataCompetencia) {
        super(  id,
                data,
                valor,
                descricao,
                categoria,
                tipoRateio,
                instrumento,
                TipoTransacao.RECEITA,
                dataCompetencia);
    }

    public Receita(TransacaoEntity entity){
        super(  entity.getId().intValue(),
                entity.getData(),
                entity.getValor(),
                entity.getDescricao(),
                entity.getCategoria(),
                entity.getTipoRateio(),
                entity.getInstrumento(),
                TipoTransacao.RECEITA,
                entity.getDataCompetencia());
    }

}
