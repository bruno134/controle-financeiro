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

     public Despesa(LocalDate data, Double valor, String descricao, String categoria, String tipoRateio, String instrumento) {
           super(data,
                 valor,
                 descricao,
                 categoria,
                 tipoRateio,
                 instrumento,
                 TipoTransacao.DESPESA);
     }
     public Despesa(Integer id, LocalDate data, Double valor, String descricao, String categoria, String tipoRateio, String instrumento) {
          super(  id,
                  data,
                  valor,
                  descricao,
                  categoria,
                  tipoRateio,
                  instrumento,
                  TipoTransacao.DESPESA);
     }

   public Despesa(TransacaoEntity entity){
       super(  entity.getId().intValue(),
               entity.getData(),
               entity.getValor(),
               entity.getDescricao(),
               entity.getCategoria(),
               entity.getTipoRateio(),
               entity.getInstrumento(),
               TipoTransacao.DESPESA);
   }
}
