package br.com.mcf.controlefinanceiro.entity;

import br.com.mcf.controlefinanceiro.model.Classificacao;
import br.com.mcf.controlefinanceiro.model.Origem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_origem")
@Getter
@Setter
@AllArgsConstructor
public class OrigemEntity {
    //TODO deixar os campos no padrão qualificador/idenficador

    public OrigemEntity() {
    }

    public OrigemEntity(String nome, LocalDate dataCriacao) {
        this.nomeOrigem = nome;
        this.dataCriacao = dataCriacao;
        this.ativo = true;
    }

    public OrigemEntity(Origem origem) {
        this.nomeOrigem = origem.getNome();
        this.dataCriacao = origem.getDataCriacao();
        this.ativo = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_origem", nullable = false)
    private Long id;

    @Column(name = "nm_origem")
    private String nomeOrigem;
    @Column(name = "dt_criacao")
    private LocalDate dataCriacao;
    @Column(name = "is_ativo")
    private boolean ativo;

    public Origem toObject(){
        return new Origem(
                this.id,
                this.nomeOrigem,
                this.dataCriacao
        );
    }

    public static List<Origem> toList(List<OrigemEntity> list){
        List<Origem> origemLista = new ArrayList<>();
        if(list!=null)
            list.forEach(entity -> origemLista.add(new Origem(entity.id, entity.nomeOrigem, entity.dataCriacao)));
        return origemLista;
    }

}
