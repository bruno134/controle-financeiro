package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.model.Despesa;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashService {

    public List<Map<String, DoubleSummaryStatistics>> retornaDadosDespesaDash(List<Despesa> listaDespesa){

        List<Map<String, DoubleSummaryStatistics>> listaDadosDash = new ArrayList<>();

        listaDadosDash.add(retornaTotalDespesaPorCategoria(listaDespesa));
        listaDadosDash.add(retornaTotalDespesaPorTipoRateio(listaDespesa));
        listaDadosDash.add(retornaTotalDespesaPorInstrumento(listaDespesa));

        return listaDadosDash;
    }

    public Map<String, DoubleSummaryStatistics> retornaTotalDespesaPorCategoria(List<Despesa> listaDespesa) {

        return listaDespesa.stream().collect(
                Collectors.groupingBy(
                        Despesa::getCategoria
                        , Collectors.summarizingDouble(Despesa::getValor)
                )
        );
    }

    public Map<String, DoubleSummaryStatistics> retornaTotalDespesaPorTipoRateio(List<Despesa> despesas){
        return despesas.stream().collect(
                Collectors.groupingBy(
                        Despesa::getTipoRateio
                        , Collectors.summarizingDouble(Despesa::getValor)
                )
        );
    }

    public Map<String, DoubleSummaryStatistics> retornaTotalDespesaPorInstrumento(List<Despesa> despesas){
                return despesas.stream().collect(
                Collectors.groupingBy(
                        Despesa::getInstrumento
                        , Collectors.summarizingDouble(Despesa::getValor)
                )
        );
    }
}
