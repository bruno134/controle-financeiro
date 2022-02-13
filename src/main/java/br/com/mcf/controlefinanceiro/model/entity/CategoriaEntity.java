package br.com.mcf.controlefinanceiro.model.entity;

import br.com.mcf.controlefinanceiro.model.dominio.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_categoria")
@Getter
@Setter
@AllArgsConstructor
public class CategoriaEntity {
    //TODO deixar os campos no padrão qualificador/idenficador
    //TODO tirar campo ativo e realmente apagar os registros???
    public CategoriaEntity() {
    }

    public CategoriaEntity(String nomeCategoria, LocalDate dataCriacao) {
        this.nomeCategoria = nomeCategoria;
        this.dataCriacao = dataCriacao;
        this.ativo = true;
    }

    public CategoriaEntity(Categoria categoria) {
        this.nomeCategoria = categoria.getNome();
        this.dataCriacao = categoria.getDataCriacao();
        this.ativo = true;
        this.tipoTransacao = categoria.getTipoTransacao().getDescricao();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    private Long id;

    @Column(name = "nm_categoria")
    private String nomeCategoria;
    @Column(name = "dt_criacao")
    private LocalDate dataCriacao;
    @Column(name = "is_ativo")
    private boolean ativo;
    @Column(name = "tp_transacao")
    private String tipoTransacao;

//TODO sera aqui o melhor lugar desse metodo?
    public Categoria toObject(){
        return new Categoria(
                this.id,
                this.nomeCategoria,
                this.dataCriacao,
                this.tipoTransacao
        );
    }

    public static List<Categoria> toList(List<CategoriaEntity> list){
        List<Categoria> listaCategoria = new ArrayList<>();
        list.forEach(entity -> listaCategoria.add(new Categoria(entity.id, entity.nomeCategoria, entity.dataCriacao, entity.getTipoTransacao())));
        return listaCategoria;
    }

}
