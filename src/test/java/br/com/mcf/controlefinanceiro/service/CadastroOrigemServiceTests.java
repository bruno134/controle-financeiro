package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.exceptions.DespesaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.model.Origem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CadastroOrigemServiceTests {
	private static final Logger LOG = LoggerFactory.getLogger(CadastroOrigemServiceTests.class);
	@Autowired
	private CadastroOrigemService service;

	@Test
	void deveRetornarListaVaziaQuandoSemElementosNoBD(){
		List<Origem> lista;
		lista = service.consultarTudo();
		assertNotNull(lista);
		assertEquals(0,lista.size());

	}


}
