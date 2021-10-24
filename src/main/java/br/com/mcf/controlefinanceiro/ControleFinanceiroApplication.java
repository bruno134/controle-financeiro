package br.com.mcf.controlefinanceiro;

//import br.com.mcf.controlefinanceiro.config.WebConfig;
import br.com.mcf.controlefinanceiro.config.WebConfig;
import br.com.mcf.controlefinanceiro.controller.cadastro.DespesaController;
import br.com.mcf.controlefinanceiro.controller.dash.ControleDespesaController;
import br.com.mcf.controlefinanceiro.service.TransacaoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses={DespesaController.class,
		                           ControleDespesaController.class,
		      					   TransacaoService.class,
		  						   WebConfig.class })
public class ControleFinanceiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleFinanceiroApplication.class, args);
	}

}
