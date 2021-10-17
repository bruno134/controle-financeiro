package br.com.mcf.controlefinanceiro.model;

import br.com.mcf.controlefinanceiro.entity.DespesaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class Despesa extends Transacao{



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

     public DespesaEntity toEntity(){
          return new DespesaEntity(
                  getData(),
                  getValor(),
                  getDescricao(),
                  getCategoria(),
                  getTipoRateio(),
                  getInstrumento(),
                  TipoTransacao.DESPESA.getDescricao());
     }
}
