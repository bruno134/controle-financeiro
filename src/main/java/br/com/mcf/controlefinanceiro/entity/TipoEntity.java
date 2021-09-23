package br.com.mcf.controlefinanceiro.entity;

import br.com.mcf.controlefinanceiro.model.Origem;
import br.com.mcf.controlefinanceiro.model.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_tipo")
@Getter
@Setter
@AllArgsConstructor
public class TipoEntity {
    //TODO deixar os campos no padrão qualificador/idenficador

    public TipoEntity() {
    }

    public TipoEntity(String nome, LocalDate dataCriacao) {
        this.nomeTipo = nome;
        this.dataCriacao = dataCriacao;
        this.ativo = true;
    }

    public TipoEntity(Tipo tipo) {
        this.nomeTipo = tipo.getNome();
        this.dataCriacao = tipo.getDataCriacao();
        this.ativo = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo", nullable = false)
    private Long id;

    @Column(name = "nm_tipo")
    private String nomeTipo;
    @Column(name = "dt_criacao")
    private LocalDate dataCriacao;
    @Column(name = "is_ativo")
    private boolean ativo;

    public Tipo toObject(){
        return new Tipo(
                this.id,
                this.nomeTipo,
                this.dataCriacao
        );
    }

    public static List<Tipo> toList(List<TipoEntity> list){
        List<Tipo> listaDeTipos = new ArrayList<>();
        if(list!=null)
            list.forEach(entity -> listaDeTipos.add(new Tipo(entity.id, entity.nomeTipo, entity.dataCriacao)));
        return listaDeTipos;
    }

}
