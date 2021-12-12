package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.config.PeriodoMesConfig;
import br.com.mcf.controlefinanceiro.exceptions.DespesaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.exceptions.RateioPessoaBusinessException;
import br.com.mcf.controlefinanceiro.exceptions.RateioPessoaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.model.RateioPessoa;
import br.com.mcf.controlefinanceiro.service.transacao.DespesaService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class CadastroDespesaServiceTests {
	private static final Logger LOG = LoggerFactory.getLogger(CadastroDespesaServiceTests.class);
	@Autowired
	private DespesaService service;

	@Autowired
	private RateioPessoaService rateioService;

	@Autowired
	private PeriodoMesConfig mesConfig;

	@Test
	public void testa(){

//		var despesaList = service.buscarPorPeriodo(11,2021,-1);
//		despesaList.getTransacoes().forEach(despesa -> System.out.println(despesa.toString()));

//		Integer page = 0;
//		while (despesaList.size()>0) {
//			despesaList.forEach(despesa -> System.out.println(despesa.toString()));
//			System.out.println("------------------------");
//			despesaList = service.buscarTodas(++page);
//		}
	}

	@Test
	public void testaCalculo(){

		RateioPessoa r1 = new RateioPessoa(10,2021,0.7,0D,"BRUNO");
		RateioPessoa r2 = new RateioPessoa(10,2021,0.3,0D,"PRI");

		Despesa d1 = new Despesa(1,LocalDate.now(),93d, "", "", "BRUNO", "",LocalDate.now());
		Despesa d2 = new Despesa(1,LocalDate.now(),150d, "", "", "COMPARTILHADA", "",LocalDate.now());
		Despesa d3 = new Despesa(1,LocalDate.now(),41d, "", "", "PRI", "",LocalDate.now());
		Despesa d4 = new Despesa(1,LocalDate.now(),160d, "", "", "COMPARTILHADA", "",LocalDate.now());

		service.inserir(d1);
		service.inserir(d2);
		service.inserir(d3);
		service.inserir(d4);

		try {
			rateioService.inserir(r1);
			rateioService.inserir(r2);
		} catch (RateioPessoaBusinessException | RateioPessoaNaoEncontradaException e) {
			e.printStackTrace();
		}

		rateioService.calculaRateio(10,2021);

	}

	@Test
	public void testar() throws DespesaNaoEncontradaException {

		Despesa despesa = new Despesa(1,LocalDate.now(),100d, "", "", "", "",LocalDate.now());

		service.inserir(despesa);

		service.buscarTodas().forEach(d -> System.out.println("Result ==> " + d.getValor()));


	}

//	@Test
//	void cadastrarDespesaComSucessoPorParametro() {
//
//		service.apagarTodasDespesas();
//		final LocalDate data = LocalDate.now();
//		final Double valor = Double.valueOf(10d);
//		final String descricao = "Compra na loja Americanas";
//		final String categoria = "Compras";
//		final String origem = "Cartão de Crédito";
//		final String tipo = "Compartilhada";
//
//
//			service.insere(data,
//					valor,
//					descricao,
//					categoria,
//					origem,
//					tipo);
//
//		final List<Despesa> list = service.buscarTodasDespesas();
//		assertEquals(1,list.size());
//	}
//
//	@Test
//	void cadastrarDespesaComSucessoPorObjeto() {
//
//		service.apagarTodasDespesas();
//		final LocalDate data = LocalDate.now();
//		final Double valor = Double.valueOf(10d);
//		final String descricao = "Compra na loja Americanas";
//		final String categoria = "Compras";
//		final String origem = "Cartão de Crédito";
//		final String tipo = "Compartilhada";
//
//		final Despesa despesa = new Despesa(1,
//				data,
//				valor,
//				descricao,
//				categoria,
//				origem,
//				tipo);
//		service.apagarTodasDespesas();
//
//		service.insere(despesa);
//
//		final List<Despesa> list = service.buscarTodasDespesas();
//		assertEquals(1,list.size());
//
//	}
//
//	@Test
//	void deveApagarTodosElementosDaLista(){
//		inicializaListDeDespesa();
//		service.apagarTodasDespesas();
//		final List<Despesa> list = service.buscarTodasDespesas();
//		assertEquals(0,list.size());
//	}
//
//	@Test
//	void consultaDeveRetornarListaPreenchidaComCincoElementos(){
//		inicializaListDeDespesa();
//		final List<Despesa> despesas = service.buscarTodasDespesas();
//		assertEquals(5,despesas.size());
//	}
//
//	@Test
//	void despesaPorIdDeveSerApagada(){
//		try {
//			inicializaListDeDespesa();
//
//			final List<Despesa> todasDespesas = service.buscarTodasDespesas();
//			service.apagarDespesa(todasDespesas.get(0).getId());
//			final Optional<Despesa> optionalDespesa = service.buscaDespesaPorID(todasDespesas.get(0).getId());
//			assertTrue(optionalDespesa.isEmpty());
//
//		} catch (DespesaNaoEncontradaException e) {
//			fail();
//		}
//	}
//
//	@Test
//	void despesaNaoDeveSerApagadaQuandoNaoInformada(){
//		try {
//			inicializaListDeDespesa();
//
//			final List<Despesa> todasDespesas = service.buscarTodasDespesas();
//			service.apagarDespesa(todasDespesas.get(0).getId());
//			for (int i = 1; i < todasDespesas.size(); i++) {
//				Optional<Despesa> optionalDespesa = service.buscaDespesaPorID(todasDespesas.get(1).getId());
//				assertFalse(optionalDespesa.isEmpty());
//			}
//		} catch (DespesaNaoEncontradaException e) {
//			fail();
//		}
//	}
//
//	@Test
//	void despesaAlteradaComSucesso(){
//		inicializaListDeDespesa();
//
//		final LocalDate data = LocalDate.now();
//		final Double valor = Double.valueOf(4d);
//		final String descricao = "Compra na Coop";
//		final String categoria = "SuperMercado";
//		final String tipoRateio = "Conta Corrente";
//		final String instrumento = "Particular";
//
//		final Despesa despesa = new Despesa(1,
//				data,
//				valor,
//				descricao,
//				categoria,
//				tipoRateio,
//				instrumento);
//
//		try {
//			Optional<Despesa> despesaAlterada = service.alterarDespesa(despesa);
//			if(despesaAlterada.isPresent()) {
//				assertEquals(Double.valueOf(4d), despesaAlterada.get().getValor());
//				assertEquals("Compra na Coop", despesaAlterada.get().getDescricao());
//				assertEquals("SuperMercado", despesaAlterada.get().getCategoria());
//				assertEquals("Conta Corrente", despesaAlterada.get().getTipoRateio());
//				assertEquals("Particular", despesaAlterada.get().getInstrumento());
//			}else{
//				Assertions.fail();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	void buscaDespesaPorMes(){
//		//TODO montar o teste
//		inicializaListDeDespesa();
//		final List<Despesa> despesas = service.buscaDespesaPorParametros(9, 2021);
//
//
//		despesas.forEach(despesa -> System.out.println(despesa.getData()));
//
//	}
//
//	private void inicializaListDeDespesa(){
//		service.apagarTodasDespesas();
//		for (int i = 0; i < 5; i++) {
//			Despesa despesa = new Despesa(i,
////										  LocalDate.now(),
//					LocalDate.of(2021,7+i,11),
//					 						Double.valueOf(i*12),
//											"Descrição de Compra " + i,
//											"Compras",
//											"Cartão de Crédito",
//											"Compartilhada");
//
//			service.insere(despesa);
//		}
//	}
//
//	//TODO Criar teste de Despesa nao encontrada
}
