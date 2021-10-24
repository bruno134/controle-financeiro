package br.com.mcf.controlefinanceiro.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Categoria {

    private Long id;
    private String nome;
    private LocalDate dataCriacao;
    private boolean isAtivo;
    private TipoTransacao tipoTransacao;

    public Categoria(String nome) {
        this.nome = nome;
        this.dataCriacao = LocalDate.now();
        this.isAtivo = true;
    }

    public Categoria(String nome, TipoTransacao tipoTransacao) {
        this.nome = nome;
        this.tipoTransacao = tipoTransacao;
        this.dataCriacao = LocalDate.now();
        this.isAtivo = true;
    }

    public Categoria(Long id, String nome, LocalDate dataCriacao, String tipoTransacao) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.isAtivo = true;
        this.tipoTransacao = TipoTransacao.get(tipoTransacao);
    }

    public Categoria(Long id, String nome, LocalDate dataCriacao, TipoTransacao tipoTransacao) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.isAtivo = true;
        this.tipoTransacao = tipoTransacao;
    }
}
