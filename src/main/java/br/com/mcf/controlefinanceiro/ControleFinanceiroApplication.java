package br.com.mcf.controlefinanceiro;

//import br.com.mcf.controlefinanceiro.config.WebConfig;
import br.com.mcf.controlefinanceiro.config.WebConfig;
import br.com.mcf.controlefinanceiro.controller.cadastro.CadastroDespesaController;
import br.com.mcf.controlefinanceiro.controller.dash.ControleDespesaController;
import br.com.mcf.controlefinanceiro.service.CadastroDespesaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses={CadastroDespesaController.class,
		                           ControleDespesaController.class,
		      					   CadastroDespesaService.class,
		  						   WebConfig.class })
public class ControleFinanceiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleFinanceiroApplication.class, args);
	}

}
