package br.com.mcf.controlefinanceiro.model;

import lombok.AllArgsConstructor;
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

    public Categoria(String nome) {
        this.nome = nome;
        this.dataCriacao = LocalDate.now();
        this.isAtivo = true;
    }

    public Categoria(String nome, LocalDate dataCriacao) {
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.isAtivo = true;
    }

    public Categoria(Long id, String nome, LocalDate dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.isAtivo = true;
    }
}
