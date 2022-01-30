package br.com.mcf.controlefinanceiro.model.entity;

import br.com.mcf.controlefinanceiro.model.dominio.Instrumento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_instrumento_transacao")
@Getter
@Setter
@AllArgsConstructor
public class InstrumentoEntity {
    //TODO deixar os campos no padrão qualificador/idenficador

    public InstrumentoEntity() {
    }

    public InstrumentoEntity(String nome, LocalDate dataCriacao) {
        this.nomeInstrumento = nome;
        this.dataCriacao = dataCriacao;
        this.ativo = true;
    }

    public InstrumentoEntity(Instrumento instrumento) {
        this.nomeInstrumento = instrumento.getNome();
        this.dataCriacao = instrumento.getDataCriacao();
        this.ativo = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instrumento", nullable = false)
    private Long id;

    @Column(name = "nm_instrumento")
    private String nomeInstrumento;
    @Column(name = "dt_criacao")
    private LocalDate dataCriacao;
    @Column(name = "is_ativo")
    private boolean ativo;

    public Instrumento toObject(){
        return new Instrumento(
                this.id,
                this.nomeInstrumento,
                this.dataCriacao
        );
    }

    public static List<Instrumento> toList(List<InstrumentoEntity> list){
        List<Instrumento> listaDeInstrumentos = new ArrayList<>();
        if(list!=null)
            list.forEach(entity -> listaDeInstrumentos.add(new Instrumento(entity.id, entity.nomeInstrumento, entity.dataCriacao)));
        return listaDeInstrumentos;
    }

}
