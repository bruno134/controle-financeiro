package br.com.mcf.controlefinanceiro.service.transacao;

import br.com.mcf.controlefinanceiro.model.transacao.Despesa;
import br.com.mcf.controlefinanceiro.model.transacao.Transacao;
import br.com.mcf.controlefinanceiro.service.transacao.DespesaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ControleDespesaService {

    private DespesaService despesaService;

    public ControleDespesaService(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    public List<Map<String, DoubleSummaryStatistics>> retornaDadosDespesaDash(List<Transacao> listaDespesa){

        List<Map<String, DoubleSummaryStatistics>> listaDadosDash = new ArrayList<>();

        listaDadosDash.add(retornaTotalDespesaPorCategoria(listaDespesa));
        listaDadosDash.add(retornaTotalDespesaPorTipoRateio(listaDespesa));
        listaDadosDash.add(retornaTotalDespesaPorInstrumento(listaDespesa));

        return listaDadosDash;
    }

    public Map<String, DoubleSummaryStatistics> retornaTotalDespesaPorCategoria(List<Transacao> listaDespesa) {

        return listaDespesa.stream().collect(
                Collectors.groupingBy(
                        Transacao::getCategoria
                        , Collectors.summarizingDouble(Transacao::getValor)
                )
        );
    }

    public Map<String, DoubleSummaryStatistics> retornaTotalDespesaPorTipoRateio(List<Transacao> despesas){
        return despesas.stream().collect(
                Collectors.groupingBy(
                        Transacao::getTipoRateio
                        , Collectors.summarizingDouble(Transacao::getValor)
                )
        );
    }

    public Map<String, DoubleSummaryStatistics> retornaTotalDespesaPorInstrumento(List<Transacao> despesas){

        return despesas.stream().collect(
                Collectors.groupingBy(
                        Transacao::getInstrumento
                        , Collectors.summarizingDouble(Transacao::getValor)
                )
        );
    }

    public List <Map.Entry<Integer, Double>> retornaTotalDespesaAno(List<Transacao> despesas){

        final var despesasMesMap = despesas.stream().collect(
                Collectors.groupingBy(
                        despesa -> despesa.getDataCompetencia().getMonthValue(),
                        Collectors.summingDouble(Transacao::getValor)
                )
        );

        return despesasMesMap.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Double>comparingByKey()).collect(Collectors.toList());
    }


}
