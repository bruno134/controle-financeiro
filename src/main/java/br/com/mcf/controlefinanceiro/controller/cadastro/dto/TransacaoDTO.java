package br.com.mcf.controlefinanceiro.controller.cadastro.dto;

import br.com.mcf.controlefinanceiro.model.Transacao;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.mcf.controlefinanceiro.util.ConstantFormat.format;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class TransacaoDTO {


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
    @JsonProperty("tipoRateio")
    private String tipoRateio;
    @JsonProperty("instrumento")
    private String instrumento;

    public TransacaoDTO(){

    }

    public TransacaoDTO(Integer id, String data, String valor, String descricao, String categoria, String tipoRateio, String instrumento) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.tipoRateio = tipoRateio;
        this.instrumento = instrumento;
    }

    public TransacaoDTO(Integer id, LocalDate data, String valor, String descricao, String categoria, String tipoRateio, String instrumento) {
        this.id = id;
        setData(data);
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.tipoRateio = tipoRateio;
        this.instrumento = instrumento;
    }

    public TransacaoDTO(Transacao transacao){
        this.id = transacao.getId();
        this.data = transacao.getData().format(format);
        this.valor = String.valueOf(transacao.getValor());
        this.descricao = transacao.getDescricao();
        this.categoria = transacao.getCategoria();
        this.tipoRateio = transacao.getTipoRateio();
        this.instrumento = transacao.getInstrumento();
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setData(LocalDate data) {
        this.data = data.format(format);
    }

    public <T> T toObject(Class<T> clazz) {

        LocalDate dataTransacao = null;

        T objetoRetornado = null;

        try {
            final Constructor<T> declaredConstructor = clazz.getDeclaredConstructor(Integer.class, LocalDate.class, Double.class, String.class, String.class,String.class, String.class);

            if(!getData().equals("")) {
                dataTransacao = LocalDate.parse(getData(), format);
            }

            Double valor = 0d;

            try{
                valor = Double.valueOf(getValor());
            }catch (NumberFormatException e){
                System.out.println("Erro ao converter valor para numérico, setando valor = 0"); //TODO arrumar forma de avisar (WARN)?
            }

            objetoRetornado = (T) declaredConstructor.newInstance( getId(),
                                                            dataTransacao,
                                                            valor,
                                                            getDescricao(),
                                                            getCategoria(),
                                                            getTipoRateio(),
                                                            getInstrumento());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return objetoRetornado;
    }

    public static <T extends TransacaoDTO, T2 extends Transacao> List<T> listaDto(List<T2> lista, Class<T> clazz){

        final List<T> list = new ArrayList<>();

        try {
            final Constructor<T> declaredConstructor = clazz.getDeclaredConstructor(Integer.class,
                    LocalDate.class,
                    String.class,
                    String.class,
                    String.class,
                    String.class,
                    String.class);

            lista.forEach(item -> {
                T objeto = null;
                try {
                    objeto = declaredConstructor.newInstance(item.getId(),
                            item.getData(),
                            String.valueOf(item.getValor()),
                            item.getDescricao(),
                            item.getCategoria(),
                            item.getTipoRateio(),
                            item.getInstrumento());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                list.add(objeto);
            });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T extends TransacaoDTO, T2 extends Transacao> List<T2> listDtoToListObject(List<T> listaDto, Class<T2> clazz){

        final List<T2> listRetorno = new ArrayList<>();

        try {
            final Constructor<T2> declaredConstructor = clazz.getDeclaredConstructor(Integer.class, LocalDate.class, Double.class, String.class, String.class,String.class, String.class);
            listaDto.forEach(item -> {

                LocalDate dataDespesa = null;


                if(!item.getData().equals("")) {
                    dataDespesa = LocalDate.parse(item.getData(), format);
                }

                Double valorConvertido = null;

                try{
                    valorConvertido = Double.parseDouble(item.getValor());
                }catch (NumberFormatException e0){
                    throw new NumberFormatException("Não pode converter " + item.getValor() + "em double"); //TODO arrumar mensagem fixa
                }catch (NullPointerException e1){
                    valorConvertido = 0d;
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    listRetorno.add(declaredConstructor.newInstance(item.getId(),
                                                                    dataDespesa,
                                                                    valorConvertido,
                                                                    item.getDescricao(),
                                                                    item.getCategoria(),
                                                                    item.getTipoRateio(),
                                                                    item.getInstrumento())
                    );
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return listRetorno;
    }

}
