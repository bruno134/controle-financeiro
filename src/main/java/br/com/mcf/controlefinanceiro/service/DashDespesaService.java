package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.model.Despesa;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashDespesaService {

    public List<Map<String, DoubleSummaryStatistics>> retornaDadosDespesaDash(List<Despesa> listaDespesa){

        List<Map<String, DoubleSummaryStatistics>> listaDadosDash = new ArrayList<>();

        listaDadosDash.add(retornaTotalDespesaPorCategoria(listaDespesa));
        listaDadosDash.add(retornaTotalDespesaPorOrigem(listaDespesa));
        listaDadosDash.add(retornaTotalDespesaPorTipo(listaDespesa));

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

    public Map<String, DoubleSummaryStatistics> retornaTotalDespesaPorOrigem(List<Despesa> despesas){
        return despesas.stream().collect(
                Collectors.groupingBy(
                        Despesa::getOrigem
                        , Collectors.summarizingDouble(Despesa::getValor)
                )
        );
    }

    public Map<String, DoubleSummaryStatistics> retornaTotalDespesaPorTipo(List<Despesa> despesas){
                return despesas.stream().collect(
                Collectors.groupingBy(
                        Despesa::getTipo
                        , Collectors.summarizingDouble(Despesa::getValor)
                )
        );
    }
}
