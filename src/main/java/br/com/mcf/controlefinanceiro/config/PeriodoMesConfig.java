package br.com.mcf.controlefinanceiro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class PeriodoMesConfig {

    @Value("${controle-financeiro.inicio-mes.dia:#{null}}")
    private Integer diaInicioMes;


    public Integer getDiaInicioMes() {
        return diaInicioMes==null?1:diaInicioMes;
    }
}
