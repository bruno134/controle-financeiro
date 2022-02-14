package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.model.dominio.TipoTransacao;
import br.com.mcf.controlefinanceiro.model.repository.specification.QueryOperator;
import br.com.mcf.controlefinanceiro.model.repository.specification.SearchCriteria;
import br.com.mcf.controlefinanceiro.model.transacao.Despesa;
import br.com.mcf.controlefinanceiro.service.transacao.DespesaService;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CadastroDespesaServiceTests {

    @Autowired
    private DespesaService despesaService;

    @Test
    public void buscaPorParametrosRetornaSomenteCategoriaJogos(){

        List<SearchCriteria> criterios = new ArrayList<>();
        criterios.add(new SearchCriteria("categoria", QueryOperator.EQUAL,"JOGOS"));

        final var busca = despesaService.buscarDespesaPorParametros
                (criterios, 1, 50);

        final var transacaoList = busca.getTransacoes();
        assertThat(transacaoList, not(IsEmptyCollection.empty()));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("categoria", is("JOGOS"))));
    }

    @Test
    public void buscaPorParametrosRetornaSomenteRateioCompartilhada(){

        List<SearchCriteria> criterios = new ArrayList<>();

        criterios.add(new SearchCriteria("tipoRateio",QueryOperator.EQUAL, "COMPARTILHADA"));

        final var busca = despesaService.buscarDespesaPorParametros
                (criterios, 1, 50);

        final var transacaoList = busca.getTransacoes();
        assertThat(transacaoList, not(IsEmptyCollection.empty()));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("tipoRateio", is("COMPARTILHADA"))));

    }

    @Test
    public void buscaPorParametrosRetornaSomenteInstrumentoDebito(){

        List<SearchCriteria> criterios = new ArrayList<>();
        criterios.add(new SearchCriteria("instrumento",QueryOperator.EQUAL,"DEBITO"));

        final var busca = despesaService.buscarDespesaPorParametros
                (criterios, 1, 50);

        final var transacaoList = busca.getTransacoes();
        assertThat(transacaoList, not(IsEmptyCollection.empty()));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("instrumento", is("DEBITO"))));
    }

    @Test
    public void buscaSemParametrosTrazDespesas(){
        List<SearchCriteria> criterios = new ArrayList<>();
        final var busca = despesaService.buscarDespesaPorParametros
                (criterios, 1, 50);

        final var transacaoList = busca.getTransacoes();
        assertThat(transacaoList, not(IsEmptyCollection.empty()));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("tipoTransacao", is(TipoTransacao.DESPESA))));

    }


    @Test
    public void buscaComParametrosdedataCompetencia(){
        List<SearchCriteria> criterios = new ArrayList<>();
        criterios.add(new SearchCriteria("dataCompetencia",QueryOperator.GREATER_OR_EQUAL_THAN,"2021-12-01"));
        criterios.add(new SearchCriteria("dataCompetencia",QueryOperator.LESS_OR_EQUAL_THAN,"2021-12-31"));

        final var busca = despesaService.buscarDespesaPorParametros
                (criterios, 1, 50);

        final var transacaoList = busca.getTransacoes();


        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",is(greaterThanOrEqualTo(LocalDate.of(2021,12,1))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",is(lessThanOrEqualTo(LocalDate.of(2021,12,31))))));
        transacaoList.forEach(System.out::println);

    }

    @Test
    public void buscaComParametrosdeDataMaiorQueDezembro(){
        List<SearchCriteria> criterios = new ArrayList<>();
        criterios.add(new SearchCriteria("dataCompetencia",QueryOperator.GREATER_OR_EQUAL_THAN,"2021-12-01"));


        final var busca = despesaService.buscarDespesaPorParametros
                (criterios, 1, 50);

        final var transacaoList = busca.getTransacoes();
        assertThat(transacaoList, not(IsEmptyCollection.empty()));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",greaterThanOrEqualTo(LocalDate.of(2021,12,1)))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",not(lessThan(LocalDate.of(2021,12,1))))));
    }

    @Test
    public void buscaComVariosParametros(){
        List<SearchCriteria> criterios = new ArrayList<>();
        criterios.add(new SearchCriteria("dataCompetencia",QueryOperator.GREATER_OR_EQUAL_THAN,"2021-12-01"));
        criterios.add(new SearchCriteria("dataCompetencia",QueryOperator.LESS_OR_EQUAL_THAN,"2022-02-01"));
        criterios.add(new SearchCriteria("categoria",QueryOperator.EQUAL,"JOGOS"));
        criterios.add(new SearchCriteria("instrumento",QueryOperator.EQUAL, "CARTAO CREDITO"));
        criterios.add(new SearchCriteria("tipoRateio",QueryOperator.EQUAL, "COMPARTILHADA"));


        final var busca = despesaService.buscarDespesaPorParametros
                (criterios, 1, 50);

        final var transacaoList = busca.getTransacoes();

        assertThat(transacaoList, not(IsEmptyCollection.empty()));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",greaterThanOrEqualTo(LocalDate.of(2021,12,1)))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",not(lessThan(LocalDate.of(2021,12,1))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("categoria", is("JOGOS"))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("instrumento", is("CARTAO CREDITO"))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("tipoRateio", is("COMPARTILHADA"))));
    }

    @Test
    public void buscaDeveTrazersomentePeriodoInformado(){

        LocalDate dataInicial = LocalDate.of(2021,12,1);
        LocalDate dataFinal   = LocalDate.of(2021,12,31);

        final var busca = despesaService.buscarPorPeriodoPaginado(dataInicial,dataFinal,1,0);

        final var transacaoList = busca.getTransacoes();

        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                is(greaterThanOrEqualTo(LocalDate.of(2021,12,1))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                not(is(greaterThan(LocalDate.of(2021,12,31)))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                is(lessThanOrEqualTo(LocalDate.of(2021,12,31))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                not(is(lessThan(LocalDate.of(2021,12,1)))))));


    }

    @Test
    public void buscaPorAnoDeveTrazersomenteAnoDe2022(){

        final var transacaoList = despesaService.buscarPorAno(2022);

        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                is(greaterThanOrEqualTo(LocalDate.of(2022,1,1))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                is(lessThanOrEqualTo(LocalDate.of(2022,12,31))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                not(is(greaterThan(LocalDate.of(2022,12,31)))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                not(is(lessThan(LocalDate.of(2022,1,1)))))));
    }

    @Test
    public void buscaPorMesEAnoDeveTrazerSomenteMesDeDezembroComPaginaDeDezRegistros(){
        final var transacaoList = despesaService.buscaPorMesAnoPaginado(12,2021,1,10).getTransacoes();

        assertEquals(transacaoList.size(),10);
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                is(greaterThanOrEqualTo(LocalDate.of(2021,12,1))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                is(lessThanOrEqualTo(LocalDate.of(2021,12,31))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                not(is(greaterThan(LocalDate.of(2021,12,31)))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                not(is(lessThan(LocalDate.of(2021,12,1)))))));

    }

    @Test
    public void buscaPorMesEAnoDeveTrazerSomenteMesDeDezembro(){
        final var transacaoList = despesaService.buscaPorMesAno(12,2021);

        assertThat(transacaoList.size(),is(greaterThan(10)));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                is(greaterThanOrEqualTo(LocalDate.of(2021,12,1))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                is(lessThanOrEqualTo(LocalDate.of(2021,12,31))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                not(is(greaterThan(LocalDate.of(2021,12,31)))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                not(is(lessThan(LocalDate.of(2021,12,1)))))));

    }

    @Test
    public void buscaPorPeriodoDeveTrazerNovembroEDezembro(){
        final var transacaoList = despesaService.buscarPorPeriodo(
                 LocalDate.of(2021,11,1)
                ,LocalDate.of(2021,12,31));

        assertThat(transacaoList.size(),is(greaterThan(10)));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                is(greaterThanOrEqualTo(LocalDate.of(2021,11,1))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                is(lessThanOrEqualTo(LocalDate.of(2021,12,31))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                not(is(greaterThan(LocalDate.of(2021,12,31)))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                not(is(lessThan(LocalDate.of(2021,11,1)))))));

    }

    @Test
    public void buscaPorPeriodoDeveTrazerNovembroEDezembroPaginadoComMaximoDezRegistros(){
        final var transacaoList = despesaService.buscarPorPeriodoPaginado(
                LocalDate.of(2021,11,1)
                ,LocalDate.of(2021,12,31),1,10).getTransacoes();

        assertThat(transacaoList.size(),is(10));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                is(greaterThanOrEqualTo(LocalDate.of(2021,11,1))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                is(lessThanOrEqualTo(LocalDate.of(2021,12,31))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                not(is(greaterThan(LocalDate.of(2021,12,31)))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("dataCompetencia",
                not(is(lessThan(LocalDate.of(2021,11,1)))))));

    }

    @Test
    public void deveRetornarSomaDosValoresDaLista(){

        List<Despesa> listaDespesa = new ArrayList<>();
        listaDespesa.add(new Despesa(LocalDate.now(),50d,"teste1","cat1","COMPARTILHADA","PIX",LocalDate.now()));
        listaDespesa.add(new Despesa(LocalDate.now(),50d,"teste2","cat1","COMPARTILHADA","PIX",LocalDate.now()));

        final var totalDaSoma = despesaService.somaTotal(listaDespesa);

        assertEquals(100,totalDaSoma);

    }

    @Test
    public void deveRetornarZeroSeListaForNula(){
        List<Despesa> listaDespesa = null;
        final var totalDaSoma = despesaService.somaTotal(listaDespesa);
        assertEquals(0,totalDaSoma);
    }

    @Test
    public void deveRetornarZeroSeListaForVazia(){
        List<Despesa> listaDespesa = new ArrayList<>();
        final var totalDaSoma = despesaService.somaTotal(listaDespesa);
        assertEquals(0,totalDaSoma);
    }



}