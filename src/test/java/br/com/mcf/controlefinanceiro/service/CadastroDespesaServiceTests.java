package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.model.dominio.TipoTransacao;
import br.com.mcf.controlefinanceiro.model.repository.specification.QueryOperator;
import br.com.mcf.controlefinanceiro.model.repository.specification.SearchCriteria;
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


//        criterios.add(new SearchCriteria("data",QueryOperator.GREATER_OR_EQUAL_THAN,"2021-12-01"));
//        criterios.add(new SearchCriteria("data",QueryOperator.LESS_OR_EQUAL_THAN,"2022-02-01"));
//          criterios.add(new SearchCriteria("categoria",QueryOperator.EQUAL,"JOGOS"));
//        criterios.add(new SearchCriteria("tipoRateio",QueryOperator.EQUAL, "BRUNO"));

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
    public void buscaComParametrosdeData(){
        List<SearchCriteria> criterios = new ArrayList<>();
        criterios.add(new SearchCriteria("data",QueryOperator.GREATER_OR_EQUAL_THAN,"2021-12-19"));
        criterios.add(new SearchCriteria("data",QueryOperator.LESS_OR_EQUAL_THAN,"2021-12-19"));

        final var busca = despesaService.buscarDespesaPorParametros
                (criterios, 1, 50);

        final var transacaoList = busca.getTransacoes();

        assertEquals(4,transacaoList.size());
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("data",is(LocalDate.of(2021,12,19)))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("data",not(is(LocalDate.of(2021,12,22))))));
       transacaoList.forEach(System.out::println);

    }

    @Test
    public void buscaComParametrosdeDataMaiorQueDezembro(){
        List<SearchCriteria> criterios = new ArrayList<>();
        criterios.add(new SearchCriteria("data",QueryOperator.GREATER_OR_EQUAL_THAN,"2021-12-01"));


        final var busca = despesaService.buscarDespesaPorParametros
                (criterios, 1, 50);

        final var transacaoList = busca.getTransacoes();
        assertThat(transacaoList, not(IsEmptyCollection.empty()));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("data",greaterThanOrEqualTo(LocalDate.of(2021,12,1)))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("data",not(lessThan(LocalDate.of(2021,12,1))))));
    }

    @Test
    public void buscaComVariosParametros(){
        List<SearchCriteria> criterios = new ArrayList<>();
        criterios.add(new SearchCriteria("data",QueryOperator.GREATER_OR_EQUAL_THAN,"2021-12-01"));
        criterios.add(new SearchCriteria("data",QueryOperator.LESS_OR_EQUAL_THAN,"2022-02-01"));
        criterios.add(new SearchCriteria("categoria",QueryOperator.EQUAL,"JOGOS"));
        criterios.add(new SearchCriteria("instrumento",QueryOperator.EQUAL, "CARTAO CREDITO"));
        criterios.add(new SearchCriteria("tipoRateio",QueryOperator.EQUAL, "COMPARTILHADA"));


        final var busca = despesaService.buscarDespesaPorParametros
                (criterios, 1, 50);

        final var transacaoList = busca.getTransacoes();

        assertThat(transacaoList, not(IsEmptyCollection.empty()));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("data",greaterThanOrEqualTo(LocalDate.of(2021,12,1)))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("data",not(lessThan(LocalDate.of(2021,12,1))))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("categoria", is("JOGOS"))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("instrumento", is("CARTAO CREDITO"))));
        assertThat(transacaoList,containsInRelativeOrder(hasProperty("tipoRateio", is("COMPARTILHADA"))));
    }
}