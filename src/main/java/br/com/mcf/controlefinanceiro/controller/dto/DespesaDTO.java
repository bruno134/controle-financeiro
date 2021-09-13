package br.com.mcf.controlefinanceiro.controller.dto;

import br.com.mcf.controlefinanceiro.model.Despesa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DespesaDTO implements BaseDespesaDTO{

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("data")
    private String data;
    @JsonProperty("valor")
    private String valor;
    @JsonProperty("descricao")
    private String descricao;
    @JsonProperty("classificacao")
    private String classificacao;
    @JsonProperty("origem")
    private String origem;
    @JsonProperty("tipo")
    private String tipo;

    @JsonIgnore
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DespesaDTO() {
    }

    public DespesaDTO(Integer id, String data, String valor, String descricao, String classificacao, String origem, String tipo) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.classificacao = classificacao;
        this.origem = origem;
        this.tipo = tipo;
    }

    public DespesaDTO(Integer id, LocalDate data, String valor, String descricao, String classificacao, String origem, String tipo) {
        this.id = id;
        setData(data);
        this.valor = valor;
        this.descricao = descricao;
        this.classificacao = classificacao;
        this.origem = origem;
        this.tipo = tipo;
    }

    public DespesaDTO(Despesa despesa){
        this.id = despesa.getId();
        this.data = despesa.getData().format(format);
        this.valor = String.valueOf(despesa.getValor());
        this.descricao = despesa.getDescricao();
        this.classificacao = despesa.getClassificacao();
        this.origem = despesa.getOrigem();
        this.tipo = despesa.getTipo();
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setData(LocalDate data) {
        this.data = data.format(format);
    }

    @Override
    public Despesa toDespesa() {

        LocalDate dataDespesa = null;

        if(!getData().equals("")) {
            dataDespesa = LocalDate.parse(getData(), format);
        }

        return new Despesa( getId(),
                dataDespesa,
                new BigDecimal(getValor()),
                getDescricao(),
                getClassificacao(),
                getOrigem(),
                getTipo());
    }

    public static List<DespesaDTO> listaDto(List<Despesa> listaDespesa){
        final List<DespesaDTO> list = new ArrayList<>();
        listaDespesa.forEach(despesa -> {

            DespesaDTO despesaDTO = new DespesaDTO(despesa.getId(),
                    despesa.getData(),
                    String.valueOf(despesa.getValor()),
                    despesa.getDescricao(),
                    despesa.getClassificacao(),
                    despesa.getTipo(),
                    despesa.getOrigem());
            list.add(despesaDTO);
        });
        return list;
    }
}
