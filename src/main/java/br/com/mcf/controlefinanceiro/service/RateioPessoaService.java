package br.com.mcf.controlefinanceiro.service;

import br.com.fluentvalidator.context.Error;
import br.com.mcf.controlefinanceiro.entity.RateioPessoaEntity;
import br.com.mcf.controlefinanceiro.entity.embedded.RateioPessoaEmbeddedKey;
import br.com.mcf.controlefinanceiro.exceptions.RateioPessoaBusinessException;
import br.com.mcf.controlefinanceiro.exceptions.RateioPessoaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.DespesaPessoaConsolidada;
import br.com.mcf.controlefinanceiro.model.RateioPessoa;
import br.com.mcf.controlefinanceiro.repository.RateioPessoaRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RateioPessoaService {

    private final RateioPessoaRepository repository;
    private final DespesaService despesaService;

    public RateioPessoaService(RateioPessoaRepository repository, DespesaService despesaService){ this.repository = repository;
        this.despesaService = despesaService;
    }

    public RateioPessoa inserir(RateioPessoa rateioPessoa) throws RateioPessoaBusinessException, RateioPessoaNaoEncontradaException {

        Optional<RateioPessoa> rateioPessoaEncontrada;

        var totalSomaRateio = buscarListaRateio(rateioPessoa).stream().collect(Collectors.summingDouble(RateioPessoa::getValorRateio));

        if(totalSomaRateio+rateioPessoa.getValorRateio()>1) {
            var error = Error.create("valorRateio",ConstantMessages.VALOR_RATEIO_EXCEDE_100, "99", rateioPessoa.getValorRateio());
            throw new RateioPessoaBusinessException(error);
        }
        rateioPessoaEncontrada = buscarRateioPessoa(rateioPessoa);

        if(rateioPessoaEncontrada.isEmpty()){
            try {
                return new RateioPessoa(repository.save(rateioPessoa.toEntity()));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            var error = Error.create("nomePessoaRateio",ConstantMessages.RATEIO_PESSOA_EXISTENTE, "99", rateioPessoa.getPessoaRateio());
            throw new RateioPessoaBusinessException(error);
        }
        return null;
    }


    public Optional<RateioPessoa> alterar(RateioPessoa rateioPessoa) throws RateioPessoaNaoEncontradaException {

        final var rateioPessoaEncontrada = consultar(rateioPessoa);
        if(rateioPessoaEncontrada.isPresent()){
            return Optional.of(new RateioPessoa(repository.save(rateioPessoa.toEntity())));
        }else{
            throw new RateioPessoaNaoEncontradaException(ConstantMessages.RATEIO_NAO_ENCONTRADO);
        }
    }

    public Optional<RateioPessoa> consultar(RateioPessoa rateioPessoa) throws RateioPessoaNaoEncontradaException {
        try{
            final var rateioPessoaEncontrada = buscarRateioPessoa(rateioPessoa);

            if(rateioPessoaEncontrada.isPresent()){
                return rateioPessoaEncontrada;
            }else {
                throw new RateioPessoaNaoEncontradaException(ConstantMessages.RATEIO_NAO_ENCONTRADO);
            }
        }catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<RateioPessoa> consultarListaRateio(RateioPessoa rateioPessoa) {

        List<RateioPessoa> rateioPessoaList = new ArrayList<>();

        try{
            rateioPessoaList = buscarListaRateio(rateioPessoa);

        }catch (Exception e){
            e.printStackTrace();
        }

        return rateioPessoaList;
    }

    public void apagar(RateioPessoa rateioPessoa) throws RateioPessoaNaoEncontradaException {
        try {
            final var rateioPessoaEncontrada = consultar(rateioPessoa);
            if(rateioPessoaEncontrada.isPresent()){
                repository.delete(rateioPessoa.toEntity());
            }else{
                throw new RateioPessoaNaoEncontradaException(ConstantMessages.RATEIO_NAO_ENCONTRADO);
            }
        } catch (RateioPessoaNaoEncontradaException e) {
            e.printStackTrace();
        }
    }

    public Map<String, DespesaPessoaConsolidada> calculaRateio(Integer mes, Integer ano){


        final RateioPessoa rateioPessoa = new RateioPessoa(ano,mes);
        final DashService dash = new DashService();
        final Map<String, DespesaPessoaConsolidada> despesasAPagar  = new HashMap<>();



        final var despesasPorDono = dash.retornaTotalDespesaPorTipoRateio(
                                                                        despesaService.buscarPorParametros(mes, ano)
                                                                    );

        if(despesasPorDono.size()>0){

            //TODO da pra fazer melhor? Sem repetir o try catch?
            final Double valorCompatilhado;
            Double compartilhada;
            try {
                compartilhada = despesasPorDono.get("COMPARTILHADA").getSum();
            } catch (NullPointerException e) {
                compartilhada = 0d;
            }
            valorCompatilhado = compartilhada;

            final var valoresRateioPorPessoa = consultarListaRateio(rateioPessoa)
                    .stream().collect(Collectors
                            .toMap(RateioPessoa::getPessoaRateio, RateioPessoa::getValorRateio));

            valoresRateioPorPessoa.forEach((chave,valorRateio) -> {
               Double valorSoma;
                try {
                     valorSoma = despesasPorDono.get(chave.toUpperCase()).getSum();
                } catch (NullPointerException e) {
                    valorSoma = 0d;
                }
                final var valorTotal = (valorCompatilhado*valorRateio) + valorSoma;
                final var valorCompartilhado =  (valorCompatilhado*valorRateio);
                final var valorTotalIndividual = valorSoma;

                despesasAPagar.put(chave, new DespesaPessoaConsolidada(valorTotal, valorTotalIndividual, valorCompartilhado, valorRateio) );
            });
        }
        return  despesasAPagar;
    }

    private Optional<RateioPessoa> buscarRateioPessoa(RateioPessoa rateioPessoa){
        Optional<RateioPessoaEntity> rateioPessoaEncontrada;
        try{
            rateioPessoaEncontrada = repository.findById(new RateioPessoaEmbeddedKey(rateioPessoa.getMesCompetenciaRateio(), rateioPessoa.getAnoCompetenciaRateio(), rateioPessoa.getPessoaRateio()));
            return rateioPessoaEncontrada.map(RateioPessoa::new);

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    private List<RateioPessoa> buscarListaRateio(RateioPessoa rateioPessoa){
        List<RateioPessoaEntity> entityList;

        try{
            entityList = repository.findByIdAnoCompetenciaAndIdMesCompetencia(rateioPessoa.getAnoCompetenciaRateio(),rateioPessoa.getMesCompetenciaRateio());
            return RateioPessoa.toList(entityList);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
