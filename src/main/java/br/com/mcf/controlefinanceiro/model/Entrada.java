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
public class Entrada {

    private Integer id;
    private LocalDate data;
    private Double valor;
    private String descricao;
    private String categoria;
    private String instrumento;
    private String tipoRateio;

}
