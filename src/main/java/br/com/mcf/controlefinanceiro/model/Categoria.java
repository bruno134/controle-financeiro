package br.com.mcf.controlefinanceiro.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Categoria extends BaseDominio{


    private TipoTransacao tipoTransacao;

    public Categoria(String nome) {
       super(nome);
    }

    public Categoria(String nome, TipoTransacao tipoTransacao) {
      super(nome);
      this.tipoTransacao = tipoTransacao;
    }

    public Categoria(Long id, String nome, LocalDate dataCriacao, String tipoTransacao) {
        super(id,nome,dataCriacao);
        this.tipoTransacao = TipoTransacao.get(tipoTransacao);
    }

    public Categoria(Long id, String nome, LocalDate dataCriacao, TipoTransacao tipoTransacao) {
        super(id,nome,dataCriacao);
        this.tipoTransacao = tipoTransacao;
    }
}
