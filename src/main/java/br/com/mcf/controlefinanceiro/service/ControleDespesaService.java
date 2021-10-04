package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.model.Despesa;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ControleDespesaService {

    public Double retornaTotalDespesa(List<Despesa> listaDespesa){
        Double totalDespesa = 0d;
        for (Despesa despesa : listaDespesa)
            totalDespesa = totalDespesa + despesa.getValor();

        return totalDespesa;
    }

    public Map<String, DoubleSummaryStatistics>  retornaTotalDespesaPorCategoria(List<Despesa> despesas) {

        return despesas.stream().collect(
                Collectors.groupingBy(
                        Despesa::getClassificacao
                       // ,Collectors.summingDouble(Despesa::getValor)
                        , Collectors.summarizingDouble(Despesa::getValor)
                )
        );
    }

    public Map<String, Double> retornaTotalDespesaPorOrigem(List<Despesa> despesas){
        return despesas.stream().collect(
                Collectors.groupingBy(
                        Despesa::getOrigem
                        ,Collectors.summingDouble(Despesa::getValor)
                )
        );
    }

    public Map<String, Double> retornaTotalDespesaPorTipo(List<Despesa> despesas){
                return despesas.stream().collect(
                Collectors.groupingBy(
                        Despesa::getTipo
                        ,Collectors.summingDouble(Despesa::getValor)
                )
        );
    }
}
