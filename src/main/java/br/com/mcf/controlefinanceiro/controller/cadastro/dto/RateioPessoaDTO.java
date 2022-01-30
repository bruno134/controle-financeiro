package br.com.mcf.controlefinanceiro.controller.cadastro.dto;

import br.com.mcf.controlefinanceiro.model.rateio.RateioPessoa;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RateioPessoaDTO {

    @JsonProperty("mesCompetenciaRateio")
    private Integer mesCompetenciaRateio;
    @JsonProperty("anoCompetenciaRateio")
    private Integer anoCompetenciaRateio;
    @JsonProperty("valorRateio")
    private String valorRateio;
    @JsonProperty("valorSalario")
    private String valorSalario;
    @JsonProperty("nomePessoaRateio")
    private String pessoaRateio;

    public RateioPessoa toObject() {

        Double valor = 0d;
        Double valorDoSalario = 0d;

        try{
            valor = Double.valueOf(valorRateio);
        }catch (NumberFormatException e){
            System.out.println("Erro ao converter valor para numérico, setando valor = 0"); //TODO arrumar forma de avisar (WARN)?
        }

        try{
            valorDoSalario = Double.valueOf(this.valorSalario);
        }catch (NumberFormatException e){
            System.out.println("Erro ao converter valor para numérico, setando valor = 0"); //TODO arrumar forma de avisar (WARN)?
        }

        return new RateioPessoa(this.mesCompetenciaRateio,
                                this.anoCompetenciaRateio,
                                valor,
                                valorDoSalario,
                                this.pessoaRateio);
    }
}
