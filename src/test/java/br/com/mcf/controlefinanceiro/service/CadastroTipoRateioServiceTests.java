package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.model.TipoRateio;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CadastroTipoRateioServiceTests {
	private static final Logger LOG = LoggerFactory.getLogger(CadastroTipoRateioServiceTests.class);
	@Autowired
	private CadastroTipoRateioService service;

	@Test
	void deveRetornarListaVaziaQuandoSemElementosNoBD(){
		List<TipoRateio> lista;
		lista = service.consultarTudo();
		assertNotNull(lista);
		assertEquals(0,lista.size());

	}


}
