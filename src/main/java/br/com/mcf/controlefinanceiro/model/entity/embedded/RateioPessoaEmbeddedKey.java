package br.com.mcf.controlefinanceiro.model.entity.embedded;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class RateioPessoaEmbeddedKey implements Serializable {

    @Column(name = "num_mes_competencia")
    Integer mesCompetencia;
    @Column(name = "num_ano_competencia")
    Integer anoCompetencia;
    @Column(name = "nm_pessoa_rateio")
    String pessoaRateio;


    public RateioPessoaEmbeddedKey(){}

    public RateioPessoaEmbeddedKey(Integer mes, Integer ano, String pessoaRateio) {
        this.mesCompetencia = mes;
        this.anoCompetencia = ano;
        this.pessoaRateio = pessoaRateio;
    }
}
