package br.com.mcf.controlefinanceiro.model;

import br.com.mcf.controlefinanceiro.controller.dto.DespesaDTO;
import br.com.mcf.controlefinanceiro.entity.DespesaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Despesa {

     private Integer id;
     private LocalDate data;
     private BigDecimal valor;
     private String descricao;
     private String classificacao;
     private String origem;
     private String tipo;

     public Despesa(LocalDate data, BigDecimal valor, String descricao, String classificacao, String origem, String tipo) {
          this.data = data;
          this.valor = valor;
          this.descricao = descricao;
          this.classificacao = classificacao;
          this.origem = origem;
          this.tipo = tipo;
     }

     public DespesaEntity toEntity(){
          return new DespesaEntity(
                  this.data,
                  this.valor,
                  this.descricao,
                  this.classificacao,
                  this.origem,
                  this.tipo);
     }

     public static List toList(List<DespesaEntity> listaDespesa){
          final List<Despesa> list = new ArrayList<>();
          listaDespesa.forEach(itemDespesa -> {

               Despesa despesa = new Despesa(itemDespesa.getId().intValue(),
                       itemDespesa.getData(),
                       itemDespesa.getValor(),
                       itemDespesa.getDescricao(),
                       itemDespesa.getClassificacao(),
                       itemDespesa.getTipo(),
                       itemDespesa.getOrigem());
               list.add(despesa);
          });
          return list;
     }
}
