package br.com.mcf.controlefinanceiro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Transacao {

    private Integer id;
    private LocalDate data;
    private Double valor;
    private String descricao;
    private String categoria;
    private String tipoRateio;
    private String instrumento;
    private TipoTransacao tipoTransacao;

    public Transacao(LocalDate data, Double valor, String descricao, String categoria, String tipoRateio, String instrumento, TipoTransacao tipoTransacao) {
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.tipoRateio = tipoRateio;
        this.instrumento = instrumento;
        this.tipoTransacao = tipoTransacao;
    }

}
