package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.exceptions.DespesaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Despesa;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CadastroDespesaServiceTests {
	private static final Logger LOG = LoggerFactory.getLogger(CadastroDespesaServiceTests.class);
	@Autowired
	private CadastroDespesaService service;

	@Test
	void cadastrarDespesaComSucessoPorParametro() {

		service.apagarTodasDespesas();
		final LocalDate data = LocalDate.now();
		final BigDecimal valor = BigDecimal.TEN;
		final String descricao = "Compra na loja Americanas";
		final String classificacao = "Compras";
		final String origem = "Cartão de Crédito";
		final String tipo = "Compartilhada";

		service.cadastrarDespesa(data,
								 valor,
								 descricao,
								 classificacao,
								 origem,
								 tipo);

		final List<Despesa> list = service.consultasTodasDespesas();
		assertEquals(1,list.size());
	}

	@Test
	void cadastrarDespesaComSucessoPorObjeto() {

		service.apagarTodasDespesas();
		final LocalDate data = LocalDate.now();
		final BigDecimal valor = BigDecimal.TEN;
		final String descricao = "Compra na loja Americanas";
		final String classificacao = "Compras";
		final String origem = "Cartão de Crédito";
		final String tipo = "Compartilhada";

		final Despesa despesa = new Despesa(1,
				data,
				valor,
				descricao,
				classificacao,
				origem,
				tipo);
		service.apagarTodasDespesas();
		service.cadastrarDespesa(despesa);

		final List<Despesa> list = service.consultasTodasDespesas();
		assertEquals(1,list.size());

	}

	@Test
	void deveApagarTodosElementosDaLista(){
		inicializaListDeDespesa();
		service.apagarTodasDespesas();
		final List<Despesa> list = service.consultasTodasDespesas();
		assertEquals(0,list.size());
	}

	@Test
	void consultaDeveRetornarListaPreenchidaComCincoElementos(){
		inicializaListDeDespesa();
		final List<Despesa> despesas = service.consultasTodasDespesas();
		assertEquals(5,despesas.size());
	}

	@Test
	void despesaASerApagadaNaoPodeSerMaiorQueUm(){
		inicializaListDeDespesa();
		try {
			service.apagarDespesa(2);
		} catch (DespesaNaoEncontradaException e) {
			e.printStackTrace();
		}
		final List<Despesa> todasDespesas = service.consultasTodasDespesas();
		assertEquals(4,todasDespesas.size());
	}

	@Test
	void despesaAlteradaComSucesso(){
		inicializaListDeDespesa();

		final LocalDate data = LocalDate.now();
		final BigDecimal valor = new BigDecimal(4);
		final String descricao = "Compra na Coop";
		final String classificacao = "SuperMercado";
		final String origem = "Conta Corrente";
		final String tipo = "Particular";

		final Despesa despesa = new Despesa(1,
				data,
				valor,
				descricao,
				classificacao,
				origem,
				tipo);

		try {
			Despesa despesaAlterada = service.alterarDespesa(despesa);
			assertEquals(new BigDecimal(4), despesaAlterada.getValor());
			assertEquals("Compra na Coop", despesaAlterada.getDescricao());
			assertEquals("SuperMercado", despesaAlterada.getClassificacao());
			assertEquals("Conta Corrente", despesaAlterada.getOrigem());
			assertEquals("Particular", despesaAlterada.getTipo());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void inicializaListDeDespesa(){
		service.apagarTodasDespesas();
		for (int i = 0; i < 5; i++) {
			Despesa despesa = new Despesa(i,
										  LocalDate.now(),
					 						new BigDecimal(i*12),
											"Descrição de Compra " + String.valueOf(i),
											"Compras",
											"Cartão de Crédito",
											"Compartilhada"
										   );
			service.cadastrarDespesa(despesa);
		}
	}

	//TODO Criar teste de Despesa nao encontrada
}
