package br.com.mcf.controlefinanceiro.entity;

import br.com.mcf.controlefinanceiro.model.Despesa;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="tb_despesas")
public class DespesaEntity {

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

    public DespesaEntity() {
    }

    public DespesaEntity(LocalDate data, Double valor, String descricao, String categoria, String tipoRateio, String instrumento) {
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.tipoRateio = tipoRateio;
        this.instrumento = instrumento;
    }

    public DespesaEntity(Despesa despesa){
        this.data = despesa.getData();
        this.valor = despesa.getValor();
        this.descricao = despesa.getDescricao();
        this.categoria = despesa.getCategoria();
        this.tipoRateio = despesa.getTipoRateio();
        this.instrumento = despesa.getInstrumento();
    }

    public Despesa toObject(){
        return  new Despesa(this.id.intValue(),
                this.data,
                this.valor,
                this.descricao,
                this.categoria,
                this.tipoRateio,
                this.instrumento);


    }

    public static List<Despesa> toList(List<DespesaEntity> list){
        final List<Despesa> listaDespesa = new ArrayList<>();

        list.forEach(itemDespesa -> {
            Despesa despesa = new Despesa(itemDespesa.getId().intValue(),
                    itemDespesa.getData(),
                    itemDespesa.getValor(),
                    itemDespesa.getDescricao(),
                    itemDespesa.getCategoria(),
                    itemDespesa.getTipoRateio(),
                    itemDespesa.getInstrumento());
            listaDespesa.add(despesa);
        });

        return listaDespesa;
    }

}
