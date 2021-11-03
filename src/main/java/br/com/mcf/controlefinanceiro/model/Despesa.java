package br.com.mcf.controlefinanceiro.model;

import br.com.mcf.controlefinanceiro.entity.TransacaoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Despesa extends Transacao{

    public Despesa(){
        super.setTipoTransacao(TipoTransacao.DESPESA);
    }

     public Despesa(LocalDate data, Double valor, String descricao, String categoria,
                    String tipoRateio, String instrumento, LocalDate dataCompetencia) {
           super(data,
                 valor,
                 descricao,
                 categoria,
                 tipoRateio,
                 instrumento,
                 TipoTransacao.DESPESA,
                   dataCompetencia);
     }

     public Despesa(LocalDate data, Double valor, String descricao, String categoria,
                     String tipoRateio, String instrumento) {
        super(data,
                valor,
                descricao,
                categoria,
                tipoRateio,
                instrumento,
                TipoTransacao.DESPESA,
                data);
    }

    public Despesa(Integer id, LocalDate data, Double valor, String descricao, String categoria, String tipoRateio, String instrumento) {
        super(  id,
                data,
                valor,
                descricao,
                categoria,
                tipoRateio,
                instrumento,
                TipoTransacao.DESPESA,
                data);
    }


     public Despesa(Integer id, LocalDate data, Double valor, String descricao, String categoria, String tipoRateio, String instrumento, LocalDate dataCompetencia) {
          super(  id,
                  data,
                  valor,
                  descricao,
                  categoria,
                  tipoRateio,
                  instrumento,
                  TipoTransacao.DESPESA,
                  dataCompetencia);
     }

   public Despesa(TransacaoEntity entity){
       super(  entity.getId().intValue(),
               entity.getData(),
               entity.getValor(),
               entity.getDescricao(),
               entity.getCategoria(),
               entity.getTipoRateio(),
               entity.getInstrumento(),
               TipoTransacao.DESPESA,
               entity.getDataCompetencia());
   }

    @Override
    public String toString() {
        return String.format("Despesa=[%s, %s, %s, %s, %s, %s, %s, %s, %s]", super.getId(),
                                                                            super.getData(),
                                                                            super.getDataCompetencia(),
                                                                            super.getValor(),
                                                                            super.getDescricao(),
                                                                            super.getCategoria(),
                                                                            super.getTipoRateio(),
                                                                            super.getInstrumento(),
                                                                            super.getTipoTransacao());
    }
}
