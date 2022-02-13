package br.com.mcf.controlefinanceiro.model.transacao;

import br.com.mcf.controlefinanceiro.model.dominio.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

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
    /*
    Data competencia é a data em que a transaçao foi registrada; Será o valor utilizado para
        as buscas por data.
     */
    private LocalDate dataCompetencia;

    public Transacao(LocalDate data, Double valor, String descricao, String categoria, String tipoRateio,
                     String instrumento, TipoTransacao tipoTransacao, LocalDate dataCompetencia) {
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.tipoRateio = tipoRateio;
        this.instrumento = instrumento;
        this.tipoTransacao = tipoTransacao;
        this.dataCompetencia = dataCompetencia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.data, this.valor,
                                     this.descricao, this.categoria,
                                     this.tipoRateio, this.instrumento, this.tipoTransacao, this.dataCompetencia );

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;

        return data.equals(transacao.data) &&
                valor.equals(transacao.valor) &&
                descricao.equals(transacao.descricao) &&
                categoria.equals(transacao.categoria) &&
                tipoRateio.equals(transacao.tipoRateio) &&
                instrumento.equals(transacao.instrumento) &&
                tipoTransacao.equals(transacao.tipoTransacao) &&
                dataCompetencia.equals(transacao.dataCompetencia);

    }

    @Override
    public String toString() {
        return "Transacao {" +
                "id=" + id +
                ", data=" + data +
                ", valor=" + valor +
                ", descricao='" + descricao + '\'' +
                ", categoria='" + categoria + '\'' +
                ", tipoRateio='" + tipoRateio + '\'' +
                ", instrumento='" + instrumento + '\'' +
                ", tipoTransacao=" + tipoTransacao +
                ", dataCompetencia=" + dataCompetencia +
                '}';
    }
}
