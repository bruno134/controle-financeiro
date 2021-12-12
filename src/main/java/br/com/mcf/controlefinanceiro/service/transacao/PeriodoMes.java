package br.com.mcf.controlefinanceiro.service.transacao;

import br.com.mcf.controlefinanceiro.config.PeriodoMesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PeriodoMes {

    @Autowired
    private PeriodoMesConfig mesConfig;

    public LocalDate getDataInicioMes(Integer mes, Integer ano){

        Integer diaInicioMes = mesConfig.getDiaInicioMes();
        Integer mesInicio = mes -1;
        Integer AnoInicio = ano;

        if(mesInicio==0){
            mesInicio = 12;
            AnoInicio = ano -1;
        }

        return LocalDate.of(AnoInicio,mesInicio,diaInicioMes);

    }

    public LocalDate getDataFimMes(Integer mes, Integer ano){
        Integer diaInicioMes = mesConfig.getDiaInicioMes();
        return LocalDate.of(ano,mes,(diaInicioMes-1));
    }
}
