package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.service.transacao.DespesaService;
import br.com.mcf.controlefinanceiro.util.ConstantFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.util.Pair.toMap;

@Service
public class ControleDespesaService {

    private DespesaService despesaService;

    public ControleDespesaService(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

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

    public List <Map.Entry<Integer, Double>> retornaTotalDespesaAno(List<Despesa> despesas){

        final var despesasMesMap = despesas.stream().collect(
                Collectors.groupingBy(
                        despesa -> despesa.getDataCompetencia().getMonthValue(),
                        Collectors.summingDouble(Despesa::getValor)
                )
        );

        return despesasMesMap.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Double>comparingByKey()).collect(Collectors.toList());
    }


}
