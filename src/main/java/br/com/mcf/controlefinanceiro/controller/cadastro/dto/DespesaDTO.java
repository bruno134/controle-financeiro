package br.com.mcf.controlefinanceiro.controller.cadastro.dto;

import br.com.mcf.controlefinanceiro.model.Despesa;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.mcf.controlefinanceiro.util.ConstantFormat.format;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DespesaDTO{

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("data")
    private String data;
    @JsonProperty("valor")
    private String valor;
    @JsonProperty("descricao")
    private String descricao;
    @JsonProperty("categoria")
    private String categoria;
    @JsonProperty("origem")
    private String origem;
    @JsonProperty("instrumento")
    private String instrumento;

    public DespesaDTO() {
    }

    public DespesaDTO(Integer id, String data, String valor, String descricao, String categoria, String origem, String instrumento) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.origem = origem;
        this.instrumento = instrumento;
    }

    public DespesaDTO(Integer id, LocalDate data, String valor, String descricao, String categoria, String origem, String instrumento) {
        this.id = id;
        setData(data);
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.origem = origem;
        this.instrumento = instrumento;
    }

    public DespesaDTO(Despesa despesa){
        this.id = despesa.getId();
        this.data = despesa.getData().format(format);
        this.valor = String.valueOf(despesa.getValor());
        this.descricao = despesa.getDescricao();
        this.categoria = despesa.getCategoria();
        this.origem = despesa.getOrigem();
        this.instrumento = despesa.getInstrumento();
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setData(LocalDate data) {
        this.data = data.format(format);
    }

    public Despesa toObject() {

        LocalDate dataDespesa = null;

        if(!getData().equals("")) {
            dataDespesa = LocalDate.parse(getData(), format);
        }

        return new Despesa( getId(),
                dataDespesa,
                Double.valueOf(getValor()),
                getDescricao(),
                getCategoria(),
                getOrigem(),
                getInstrumento());
    }

    public static List<DespesaDTO> listaDto(List<Despesa> listaDespesa){
        final List<DespesaDTO> list = new ArrayList<>();
        listaDespesa.forEach(despesa -> {

            DespesaDTO despesaDTO = new DespesaDTO(despesa.getId(),
                    despesa.getData(),
                    String.valueOf(despesa.getValor()),
                    despesa.getDescricao(),
                    despesa.getCategoria(),
                    despesa.getOrigem(),
                    despesa.getInstrumento());
            list.add(despesaDTO);
        });
        return list;
    }

    public static List<Despesa> listDtoToListDespesa(List<DespesaDTO> listaDto){
        final List<Despesa> listaDespesa = new ArrayList<>();

        listaDto.forEach(despesaDTO -> {

            LocalDate dataDespesa = null;

            if(!despesaDTO.getData().equals("")) {
                dataDespesa = LocalDate.parse(despesaDTO.getData(), format);
            }
            Double valorConvertido = null;

            try{
                valorConvertido = Double.parseDouble(despesaDTO.getValor());
            }catch (NumberFormatException e0){
                throw new NumberFormatException("Não pode converter " + despesaDTO.valor + "em double");
            }catch (NullPointerException e1){
                valorConvertido = 0d;
            }catch (Exception e){
                e.printStackTrace();
            }
            listaDespesa.add(
            new Despesa(despesaDTO.getId(),
                    dataDespesa,
                    valorConvertido,
                    despesaDTO.getDescricao(),
                    despesaDTO.getCategoria(),
                    despesaDTO.getOrigem(),
                    despesaDTO.getInstrumento())
            );
        });

        return listaDespesa;
    }
}
