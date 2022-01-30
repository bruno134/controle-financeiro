package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.service.transacao.ControleDespesaService;
import br.com.mcf.controlefinanceiro.service.transacao.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ControleDespesaServiceTest {

    @Autowired
    private TransacaoService cadastroService;

    @Autowired
    private ControleDespesaService controleService;

//    @Test
//    void deveSomarDespesasPorCategoria(){
//
//        inicializaListDeDespesa();
//        final var despesas = cadastroService.buscarTodasDespesas();
//
//        final var grouped = controleService.retornaTotalDespesaPorCategoria(despesas);
//
//        assertEquals(5, grouped.size());
//        assertEquals(120, grouped.get("Supermercado"));
//        assertEquals(240, grouped.get("Farmacia"));
//        assertEquals(480, grouped.get("Lazer"));
//        assertEquals(360, grouped.get("Educação"));
//        assertEquals(600, grouped.get("Viagem"));
//
//    }
//
//    private void inicializaListDeDespesa(){
//        cadastroService.apagarTodasDespesas();
//        List<String> listaCategoria = Arrays.asList("Supermercado", "Farmacia", "Educação","Lazer", "Viagem");
//       for (int d=0;d<listaCategoria.size();d++) {
//           for (int i = 0; i < 5; i++) {
//               Despesa despesa = new Despesa(i,
////										  LocalDate.now(),
//                       LocalDate.of(2021, 7 + i, 11),
//                       Double.valueOf(i * 12 * (d+1)),
//                       "Descrição de " + listaCategoria.get(d) + " "+ i,
//                       listaCategoria.get(d),
//                       "Cartão de Crédito",
//                       "Compartilhada");
//
//               cadastroService.insere(despesa);
//           }
//       }
//    }

}
