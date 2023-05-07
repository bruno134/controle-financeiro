package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.model.dominio.TipoRateio;
import br.com.mcf.controlefinanceiro.model.entity.TipoRateioEntity;
import br.com.mcf.controlefinanceiro.model.exceptions.TipoRateioNaoEncontradaException;
import br.com.mcf.controlefinanceiro.service.dominio.CadastroTipoRateioService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CadastroTipoRateioServiceTests {
	private static final Logger LOG = LoggerFactory.getLogger(CadastroTipoRateioServiceTests.class);
	private CadastroTipoRateioService service;

	@BeforeEach
	 void initializeService(){
		TipoRateioRepositoryTest repositoryTest = new TipoRateioRepositoryTest();
		service = new CadastroTipoRateioService(repositoryTest);
	}

	private List<TipoRateioEntity> getLista() {
		List<TipoRateioEntity> entityList = new ArrayList<>();

		for (int i = 0; i < 10 ; i++) {
			entityList.add(new TipoRateioEntity("tipo" + i, LocalDate.now()));
		}

		return  entityList;
	}

	@Test
	void deveRetornarListaVaziaQuandoSemElementosNoBD(){
		List<TipoRateio> lista;
		lista = service.consultarTudo();

		assertNotNull(lista);
		assertEquals(0,lista.size());

	}

	@Test
	void deveIncluirTipoRegistroNoDB() {

		service.inserir("tipo1");
		final var listaRateio = service.consultarTudo();

		assertNotNull(listaRateio);
		assertThat(listaRateio.size(), is(greaterThan(0)));
		assertThat(listaRateio,containsInRelativeOrder(hasProperty("nome",is(equalTo("tipo1")))));
	}

	@Test
	void deveApagarTipoRegistroNoDB() {
		service.inserir("tipo1");
		final var listaRateio = service.consultarTudo();
		assertNotNull(listaRateio);
		assertThat(listaRateio.size(), is(greaterThan(0)));
		assertThat(listaRateio,containsInRelativeOrder(hasProperty("nome",is(equalTo("tipo1")))));

		try {
			final var rateio = listaRateio.stream().findFirst();
			if(rateio.isPresent()){
				service.apagar(rateio.get().getId().intValue());
			}
		}catch (TipoRateioNaoEncontradaException e){
			e.printStackTrace();
		}

		final var novaListaRateio = service.consultarTudo();
		assertEquals(novaListaRateio.size(),0);
	}
}
