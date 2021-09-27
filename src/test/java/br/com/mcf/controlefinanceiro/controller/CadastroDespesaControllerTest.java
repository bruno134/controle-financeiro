package br.com.mcf.controlefinanceiro.controller;

import br.com.mcf.controlefinanceiro.exceptions.DespesaValidatorException;
import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.service.CadastroDespesaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CadastroDespesaControllerTest {

    @Autowired
    private CadastroDespesaService service;

    @Test
    void deveRetonarValoresDespesasSomados(){
        service.insere(LocalDate.now(), new BigDecimal("150"), "teste1", "teste2", "teste3", "teste4");
        service.insere(LocalDate.now(), new BigDecimal("150"), "teste1", "teste2", "teste3", "teste4");
        List<Despesa> listaDespesa = service.buscarTodasDespesas();
        BigDecimal totalSomado = service.retornaTotalDespesa(listaDespesa);
        assertEquals(new BigDecimal("300.00"),totalSomado);
    }

}
