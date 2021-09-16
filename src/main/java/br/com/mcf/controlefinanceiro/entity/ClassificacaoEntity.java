package br.com.mcf.controlefinanceiro.entity;

import br.com.mcf.controlefinanceiro.model.Classificacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_classificacao")
@Getter
@Setter
@AllArgsConstructor
public class ClassificacaoEntity {
    //TODO deixar os campos no padrão qualificador/idenficador

    public ClassificacaoEntity() {
    }

    public ClassificacaoEntity(String nomeClassificacao, LocalDate dataCriacao) {
        this.nomeClassificacao = nomeClassificacao;
        this.dataCriacao = dataCriacao;
        this.ativo = true;
    }

    public ClassificacaoEntity(Classificacao classificacao) {
        this.nomeClassificacao = classificacao.getNome();
        this.dataCriacao = classificacao.getDataCriacao();
        this.ativo = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_classificacao", nullable = false)
    private Long id;

    @Column(name = "nm_classsificacao")
    private String nomeClassificacao;
    @Column(name = "dt_criacao")
    private LocalDate dataCriacao;
    @Column(name = "is_ativo")
    private boolean ativo;

    public Classificacao toObject(){
        return new Classificacao(
                this.id,
                this.nomeClassificacao,
                this.dataCriacao
        );
    }

    public static List<Classificacao> toList(List<ClassificacaoEntity> list){
        List<Classificacao> listaClassificacao = new ArrayList<>();
        list.forEach(entity -> listaClassificacao.add(new Classificacao(entity.id, entity.nomeClassificacao, entity.dataCriacao)));
        return listaClassificacao;
    }

}
