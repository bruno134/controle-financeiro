package br.com.mcf.controlefinanceiro.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name="despesas")
public class DespesaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "data", nullable = false)
    private LocalDate data;
    @Column(name = "valor", nullable = false)
    private BigDecimal valor;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "classificacao")
    private String classificacao;
    @Column(name = "origem")
    private String origem;
    @Column(name = "tipo")
    private String tipo;

    public DespesaEntity() {
    }

    public DespesaEntity(LocalDate data, BigDecimal valor, String descricao, String classificacao, String origem, String tipo) {
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.classificacao = classificacao;
        this.origem = origem;
        this.tipo = tipo;
    }


}
