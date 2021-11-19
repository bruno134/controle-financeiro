package br.com.mcf.controlefinanceiro.controller;


import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.service.ControleDespesaService;
import br.com.mcf.controlefinanceiro.service.transacao.DespesaService;
import br.com.mcf.controlefinanceiro.util.ConstantMonths;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


@SpringBootTest
public class CadastroDespesaControllerTest {

//    //TODO Arrumar testes
//
    @Autowired
    private ControleDespesaService serviceControle;
    @Autowired
    private DespesaService service;

    @Test
    void deveRetonarValoresDespesasSomados(){


        final var despesaList = service.buscarTodasPor(2021);

        System.out.println(serviceControle.retornaTotalDespesaAno(despesaList));

        System.out.println(ConstantMonths.months.get(3));

    }


}
