package br.com.mcf.controlefinanceiro.model.entity;

import br.com.mcf.controlefinanceiro.model.dominio.TipoRateio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_tipo_rateio")
@Getter
@Setter
@AllArgsConstructor
public class TipoRateioEntity {
    //TODO deixar os campos no padrão qualificador/idenficador

    public TipoRateioEntity() {
    }

    public TipoRateioEntity(String nome, LocalDate dataCriacao) {
        this.nomeTipoRateio = nome;
        this.dataCriacao = dataCriacao;
        this.ativo = true;
    }

    public TipoRateioEntity(TipoRateio tipoRateio) {
        this.nomeTipoRateio = tipoRateio.getNome();
        this.dataCriacao = tipoRateio.getDataCriacao();
        this.ativo = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_rateio", nullable = false)
    private Long id;

    @Column(name = "nm_tipo_rateio")
    private String nomeTipoRateio;
    @Column(name = "dt_criacao")
    private LocalDate dataCriacao;
    @Column(name = "is_ativo")
    private boolean ativo;

    public TipoRateio toObject(){
        return new TipoRateio(
                this.id,
                this.nomeTipoRateio,
                this.dataCriacao
        );
    }

    public static List<TipoRateio> toList(List<TipoRateioEntity> list){
        List<TipoRateio> tipoRateioLista = new ArrayList<>();
        if(list!=null)
            list.forEach(entity -> tipoRateioLista.add(new TipoRateio(entity.id, entity.nomeTipoRateio, entity.dataCriacao)));
        return tipoRateioLista;
    }

}
