package br.com.mcf.controlefinanceiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import br.com.mcf.controlefinanceiro.config.PeriodoMesConfig;
import br.com.mcf.controlefinanceiro.controller.cadastro.DespesaController;
import br.com.mcf.controlefinanceiro.controller.dash.ControleDespesaController;
import br.com.mcf.controlefinanceiro.service.dominio.CadastroInstrumentoService;
import br.com.mcf.controlefinanceiro.service.rateio.RateioPessoaService;
import br.com.mcf.controlefinanceiro.service.transacao.DespesaService;
import br.com.mcf.controlefinanceiro.service.transacao.PeriodoMes;
import br.com.mcf.controlefinanceiro.service.transacao.load.CarregaArquivoService;

@SpringBootApplication
@ComponentScan(basePackageClasses={ DespesaController.class,
		                            ControleDespesaController.class,
		      					    DespesaService.class,
									RateioPessoaService.class,
									PeriodoMes.class,
									CadastroInstrumentoService.class,
								    PeriodoMesConfig.class,
									CarregaArquivoService.class
})
public class ControleFinanceiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleFinanceiroApplication.class, args);
	}

}
