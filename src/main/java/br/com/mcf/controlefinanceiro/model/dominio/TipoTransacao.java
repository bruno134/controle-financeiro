package br.com.mcf.controlefinanceiro.model.dominio;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public enum TipoTransacao {
    DESPESA("DESPESA"),
    RECEITA("RECEITA");

    private final String descricao;

    TipoTransacao(String descricao) {
        this.descricao = descricao;
    }

    public static TipoTransacao get(String descricao){
        final var tipoTransacaos = Arrays.stream(
                TipoTransacao.values()).filter(
                        tipoTransacao -> descricao.equals(tipoTransacao.getDescricao()))
                .collect(Collectors.toList());

        if(tipoTransacaos.size()>0)
            return tipoTransacaos.get(0);
        else {
            return null;
        }
    }
}
