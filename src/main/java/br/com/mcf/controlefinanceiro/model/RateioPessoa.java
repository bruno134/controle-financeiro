package br.com.mcf.controlefinanceiro.model;

import br.com.mcf.controlefinanceiro.entity.RateioPessoaEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RateioPessoa {

    private Integer mesCompetenciaRateio;
    private Integer anoCompetenciaRateio;
    private Double valorRateio;
    private Double valorSalario;
    private String pessoaRateio;

    public RateioPessoa(RateioPessoaEntity entity){
        this.mesCompetenciaRateio = entity.getId().getMesCompetencia();
        this.anoCompetenciaRateio = entity.getId().getAnoCompetencia();
        this.valorRateio = entity.getValorRateio();
        this.pessoaRateio = entity.getId().getPessoaRateio();
        this.valorSalario = entity.getValorSalario();
    }

    public RateioPessoa(Integer mesCompetenciaRateio, Integer anoCompetenciaRateio, Double valor, Double valorSalario, String pessoaRateio) {
        this.mesCompetenciaRateio = mesCompetenciaRateio;
        this.anoCompetenciaRateio = anoCompetenciaRateio;
        this.valorRateio = valor;
        this.pessoaRateio = pessoaRateio;
        this.valorSalario = valorSalario;
    }

    public RateioPessoa() {

    }

    public RateioPessoa(Integer anoCompetenciaRateio, Integer mesCompetenciaRateio) {
        this.mesCompetenciaRateio = mesCompetenciaRateio;
        this.anoCompetenciaRateio = anoCompetenciaRateio;
    }

    public RateioPessoaEntity toEntity(){
        return new RateioPessoaEntity(this.mesCompetenciaRateio, this.anoCompetenciaRateio,this.valorRateio, this.valorSalario, this.pessoaRateio);
    }

    public static List<RateioPessoa> toList(List<RateioPessoaEntity> list){

        List<RateioPessoa> rateioPessoaList = new ArrayList<>();

        list.forEach(entity -> rateioPessoaList.add(new RateioPessoa(entity)));

        return rateioPessoaList;
    }



}
