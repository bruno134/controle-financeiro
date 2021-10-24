package br.com.mcf.controlefinanceiro.entity;

import br.com.mcf.controlefinanceiro.model.Transacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="tb_transacao")
public class TransacaoEntity {

    //TODO deixar os campos no padrão qualificador/idenficador
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "data", nullable = false)
    private LocalDate data;
    @Column(name = "valor", nullable = false)
    private Double valor;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "tp_rateio")
    private String tipoRateio;
    @Column(name = "instrumento")
    private String instrumento;
    @Column(name = "tp_transacao")
    private String tipoTransacao;

    public TransacaoEntity(LocalDate data, Double valor, String descricao, String categoria, String tipoRateio, String instrumento, String tipoTransacao) {
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.tipoRateio = tipoRateio;
        this.instrumento = instrumento;
        this.tipoTransacao = tipoTransacao;
    }

    public TransacaoEntity(Transacao transacao){
        this.data = transacao.getData();
        this.valor = transacao.getValor();
        this.descricao = transacao.getDescricao();
        this.categoria = transacao.getCategoria();
        this.tipoRateio = transacao.getTipoRateio();
        this.instrumento = transacao.getInstrumento();
        this.tipoTransacao = transacao.getTipoTransacao().getDescricao();
    }

}
