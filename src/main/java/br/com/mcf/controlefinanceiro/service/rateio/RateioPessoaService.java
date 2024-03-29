package br.com.mcf.controlefinanceiro.service.rateio;

import br.com.fluentvalidator.context.Error;
import br.com.mcf.controlefinanceiro.model.entity.RateioPessoaEntity;
import br.com.mcf.controlefinanceiro.model.entity.embedded.RateioPessoaEmbeddedKey;
import br.com.mcf.controlefinanceiro.model.exceptions.RateioPessoaBusinessException;
import br.com.mcf.controlefinanceiro.model.exceptions.RateioPessoaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.transacao.DespesaPessoaConsolidada;
import br.com.mcf.controlefinanceiro.model.rateio.RateioPessoa;
import br.com.mcf.controlefinanceiro.model.repository.RateioPessoaRepository;
import br.com.mcf.controlefinanceiro.service.dominio.CadastroTipoRateioService;
import br.com.mcf.controlefinanceiro.service.transacao.ControleDespesaService;
import br.com.mcf.controlefinanceiro.service.transacao.DespesaService;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class RateioPessoaService {

    private static final String statusCompartilhada = "COMPARTILHADA";
    private final RateioPessoaRepository repository;
    private final DespesaService despesaService;
    private final CadastroTipoRateioService tipoRateioService;
    private final ControleDespesaService dash;

    public RateioPessoaService(RateioPessoaRepository repository, DespesaService despesaService, CadastroTipoRateioService tipoRateioService, ControleDespesaService dash) {
        this.repository = repository;
        this.despesaService = despesaService;
        this.tipoRateioService = tipoRateioService;
        this.dash = dash;
    }

    public RateioPessoa inserir(RateioPessoa rateioPessoa) throws RateioPessoaBusinessException, RateioPessoaNaoEncontradaException {

        Optional<RateioPessoa> rateioPessoaEncontrada;

        var totalSomaRateio = buscarListaRateio(rateioPessoa).stream().collect(Collectors.summingDouble(RateioPessoa::getValorRateio));

        if (totalSomaRateio + rateioPessoa.getValorRateio() > 1) {
            var error = Error.create("valorRateio", ConstantMessages.VALOR_RATEIO_EXCEDE_100, "99", rateioPessoa.getValorRateio());
            throw new RateioPessoaBusinessException(error);
        }
        rateioPessoaEncontrada = buscarRateioPessoa(rateioPessoa);

        if (rateioPessoaEncontrada.isEmpty()) {
            try {
                return new RateioPessoa(repository.save(rateioPessoa.toEntity()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            var error = Error.create("nomePessoaRateio", ConstantMessages.RATEIO_PESSOA_EXISTENTE, "99", rateioPessoa.getPessoaRateio());
            throw new RateioPessoaBusinessException(error);
        }
        return null;
    }


    public Optional<RateioPessoa> alterar(RateioPessoa rateioPessoa) throws RateioPessoaNaoEncontradaException {
        final var rateioPessoaList = buscarListaRateio(rateioPessoa);

        if (!rateioPessoaList.isEmpty()) {

            //Retirando o item que não será atualizado com a diferença do rateio de entrada
            rateioPessoaList.removeIf(rp -> rp.getPessoaRateio().equals(rateioPessoa.getPessoaRateio()));

            alteraDemaisRateios(rateioPessoaList, rateioPessoa.getValorRateio());
            return Optional.of(new RateioPessoa(repository.save(rateioPessoa.toEntity())));
        } else {
            throw new RateioPessoaNaoEncontradaException(ConstantMessages.RATEIO_NAO_ENCONTRADO);
        }
    }

    private void alteraDemaisRateios(List<RateioPessoa> rateioPessoaList,Double valorRateioReferencia) {

        DecimalFormat df = new DecimalFormat("##0.00###");
        df.setRoundingMode(RoundingMode.HALF_UP);

        if(!rateioPessoaList.isEmpty()){
            Double finalValorAlterar =  (1-valorRateioReferencia)/rateioPessoaList.size();
            final var valorFormatado = df.format(finalValorAlterar).replace(",",".");

            rateioPessoaList.forEach(rp -> {

                rp.setValorRateio(Double.valueOf(valorFormatado));
                repository.save(rp.toEntity());
            });
        }
    }

    public Optional<RateioPessoa> consultar(RateioPessoa rateioPessoa) throws RateioPessoaNaoEncontradaException {
        try {
            final var rateioPessoaEncontrada = buscarRateioPessoa(rateioPessoa);

            if (rateioPessoaEncontrada.isPresent()) {
                return rateioPessoaEncontrada;
            } else {
                throw new RateioPessoaNaoEncontradaException(ConstantMessages.RATEIO_NAO_ENCONTRADO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<RateioPessoa> consultarListaRateio(RateioPessoa rateioPessoa) {

        List<RateioPessoa> rateioPessoaList = new ArrayList<>();

        try {
            rateioPessoaList = buscarListaRateio(rateioPessoa);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rateioPessoaList;
    }

    public void apagar(RateioPessoa rateioPessoa) throws RateioPessoaNaoEncontradaException {
        try {
            final var rateioPessoaEncontrada = consultar(rateioPessoa);
            if (rateioPessoaEncontrada.isPresent()) {
                repository.delete(rateioPessoa.toEntity());
            } else {
                throw new RateioPessoaNaoEncontradaException(ConstantMessages.RATEIO_NAO_ENCONTRADO);
            }
        } catch (RateioPessoaNaoEncontradaException e) {
            e.printStackTrace();
        }
    }

    public Map<String, DespesaPessoaConsolidada> calculaRateio(Integer mes, Integer ano) {


        final RateioPessoa rateioPessoa = new RateioPessoa(ano, mes);
        final Map<String, DespesaPessoaConsolidada> despesasAPagar = new HashMap<>();


        var sumarizadoPorTipoRateio = dash.retornaTotalDespesaPorTipoRateio(
                despesaService.buscaPorMesAnoPaginado(mes, ano, 1,0).getTransacoes()
        );

        if (!sumarizadoPorTipoRateio.isEmpty()) {

            //TODO da pra fazer melhor? Sem repetir o try catch?
            final Double valorCompatilhado;

            if (sumarizadoPorTipoRateio.containsKey(statusCompartilhada)) {
                valorCompatilhado = sumarizadoPorTipoRateio.get(statusCompartilhada).getSum();
            } else {
                valorCompatilhado = 0d;
            }

            var valoresRateioPorPessoa = consultarListaRateio(rateioPessoa);

            if (valoresRateioPorPessoa.isEmpty()) {
                cadastraRateioPadrao(mes, ano);
                valoresRateioPorPessoa = consultarListaRateio(rateioPessoa);
            }


            valoresRateioPorPessoa.forEach(rp -> {
                Double valorSoma;
                try {
                    valorSoma = sumarizadoPorTipoRateio.get(rp.getPessoaRateio().toUpperCase()).getSum();
                } catch (NullPointerException e) {
                    valorSoma = 0d;
                }
                final var valorTotal = (valorCompatilhado * rp.getValorRateio()) + valorSoma;
                final var valorCompartilhado = (valorCompatilhado * rp.getValorRateio());
                final var valorTotalIndividual = valorSoma;

                despesasAPagar.put(rp.getPessoaRateio().toUpperCase(), new DespesaPessoaConsolidada(valorTotal, valorTotalIndividual, valorCompartilhado, rp.getValorRateio(),rp.getValorSalario()));
            });
        }
        return despesasAPagar;
    }

    public Map<String,Double> retornaValorTotalCompartilhado(Map<String, DespesaPessoaConsolidada> valoresRateioPorPessoa){

      AtomicReference<Double> valorTotalCompartilhado = new AtomicReference<>(0d);

      Map<String,Double> retornoValorCompartilhado = new HashMap<>();
      valoresRateioPorPessoa.forEach( (chave,valor) -> valorTotalCompartilhado
                                                        .updateAndGet(v -> v + valor.getGetValorTotalCompartilhado()));

      retornoValorCompartilhado.put(statusCompartilhada,valorTotalCompartilhado.get());

      return retornoValorCompartilhado;

    }

    private void cadastraRateioPadrao(Integer mes, Integer ano) {

        final var tipoRateios = tipoRateioService.consultarTudo();


        tipoRateios.forEach(tipoRateio -> {
            if (!tipoRateio.getNome().equals(statusCompartilhada)) {
                RateioPessoa rateioPessoa = new RateioPessoa(mes, ano, 0.5, 0D,tipoRateio.getNome());
                try {
                    inserir(rateioPessoa);
                } catch (RateioPessoaBusinessException | RateioPessoaNaoEncontradaException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Optional<RateioPessoa> buscarRateioPessoa(RateioPessoa rateioPessoa) {
        Optional<RateioPessoaEntity> rateioPessoaEncontrada;
        try {
            rateioPessoaEncontrada = repository.findById(new RateioPessoaEmbeddedKey(rateioPessoa.getMesCompetenciaRateio(), rateioPessoa.getAnoCompetenciaRateio(), rateioPessoa.getPessoaRateio()));
            return rateioPessoaEncontrada.map(RateioPessoa::new);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private List<RateioPessoa> buscarListaRateio(RateioPessoa rateioPessoa) {
        List<RateioPessoaEntity> entityList;

        try {
            entityList = repository.findByIdAnoCompetenciaAndIdMesCompetencia(rateioPessoa.getAnoCompetenciaRateio(), rateioPessoa.getMesCompetenciaRateio());
            return RateioPessoa.toList(entityList);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


}
