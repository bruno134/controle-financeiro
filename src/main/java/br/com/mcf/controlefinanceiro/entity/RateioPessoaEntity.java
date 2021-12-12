package br.com.mcf.controlefinanceiro.entity;

import br.com.mcf.controlefinanceiro.entity.embedded.RateioPessoaEmbeddedKey;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity()
@Table(name = "tb_rateio_pessoa")
public class RateioPessoaEntity {

    @EmbeddedId
    RateioPessoaEmbeddedKey id;
    @Column(name = "vl_rateio")
    Double valorRateio;
    @Column(name = "vl_salario")
    Double valorSalario;


    public RateioPessoaEntity(){}

    public RateioPessoaEntity(Integer mes, Integer ano, Double valorRateio, Double valorSalario, String pessoaRateio){
        this.id = new RateioPessoaEmbeddedKey(mes,ano,pessoaRateio);
        this.valorRateio = valorRateio;
        this.valorSalario = valorSalario;
    }


}
