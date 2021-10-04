package br.com.mcf.controlefinanceiro.controller;

import br.com.mcf.controlefinanceiro.exceptions.DespesaValidatorException;
import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.service.CadastroDespesaService;
import br.com.mcf.controlefinanceiro.service.ControleDespesaService;
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
    private ControleDespesaService serviceControle;
    @Autowired
    private CadastroDespesaService service;

    @Test
    void deveRetonarValoresDespesasSomados(){
        service.insere(LocalDate.now(), Double.valueOf(150.5d), "teste1", "teste2", "teste3", "teste4");
        service.insere(LocalDate.now(), Double.valueOf(150.5d), "teste1", "teste2", "teste3", "teste4");
        List<Despesa> listaDespesa = service.buscarTodasDespesas();
        Double totalSomado = serviceControle.retornaTotalDespesa(listaDespesa);
        assertEquals(301d,totalSomado);
    }

}
