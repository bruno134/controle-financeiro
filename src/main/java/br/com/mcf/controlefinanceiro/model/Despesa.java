package br.com.mcf.controlefinanceiro.model;

import br.com.mcf.controlefinanceiro.entity.DespesaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class Despesa {

     private Integer id;
     private LocalDate data;
     private Double valor;
     private String descricao;
     private String categoria;
     private String origem;
     private String instrumento;

     public Despesa(LocalDate data, Double valor, String descricao, String categoria, String origem, String instrumento) {
          this.data = data;
          this.valor = valor;
          this.descricao = descricao;
          this.categoria = categoria;
          this.origem = origem;
          this.instrumento = instrumento;
     }

     public DespesaEntity toEntity(){
          return new DespesaEntity(
                  this.data,
                  this.valor,
                  this.descricao,
                  this.categoria,
                  this.origem,
                  this.instrumento);
     }
}
