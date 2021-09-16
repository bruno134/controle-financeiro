package br.com.mcf.controlefinanceiro.entity;

import br.com.mcf.controlefinanceiro.model.Despesa;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal valor;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "classificacao")
    private String classificacao;
    @Column(name = "origem")
    private String origem;
    @Column(name = "tipo")
    private String tipo;

    public DespesaEntity() {
    }

    public DespesaEntity(LocalDate data, BigDecimal valor, String descricao, String classificacao, String origem, String tipo) {
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.classificacao = classificacao;
        this.origem = origem;
        this.tipo = tipo;
    }

    public DespesaEntity(Despesa despesa){
        this.data = despesa.getData();
        this.valor = despesa.getValor();
        this.descricao = despesa.getDescricao();
        this.classificacao = despesa.getClassificacao();
        this.origem = despesa.getOrigem();
        this.tipo = despesa.getTipo();
    }

    public Despesa toObject(){
        return  new Despesa(this.id.intValue(),
                this.data,
                this.valor,
                this.descricao,
                this.classificacao,
                this.origem,
                this.tipo);


    }

    public static List<Despesa> toList(List<DespesaEntity> list){
        final List<Despesa> listaDespesa = new ArrayList<>();

        list.forEach(itemDespesa -> {
            Despesa despesa = new Despesa(itemDespesa.getId().intValue(),
                    itemDespesa.getData(),
                    itemDespesa.getValor(),
                    itemDespesa.getDescricao(),
                    itemDespesa.getClassificacao(),
                    itemDespesa.getTipo(),
                    itemDespesa.getOrigem());
            listaDespesa.add(despesa);
        });

        return listaDespesa;
    }

}
