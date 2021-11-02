package br.com.mcf.controlefinanceiro.controller.dash;

import br.com.mcf.controlefinanceiro.controller.cadastro.dto.DadosConsultaDespesaDTO;
import br.com.mcf.controlefinanceiro.controller.cadastro.validator.ConsultaDespesaValidator;
import br.com.mcf.controlefinanceiro.controller.dash.dto.DashDTO;
import br.com.mcf.controlefinanceiro.controller.dash.dto.ListaDespesaPorDonoDTO;
import br.com.mcf.controlefinanceiro.service.transacao.DespesaService;
import br.com.mcf.controlefinanceiro.service.DashService;
import br.com.mcf.controlefinanceiro.service.RateioPessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dash")
public class ControleDespesaController {

    private final DashService controleDespesaService;
    private final DespesaService despesaService;
    private final ConsultaDespesaValidator consultaValidator;
    private final RateioPessoaService rateioPessoaService;

    public ControleDespesaController(DashService controleDespesaService,
                                     DespesaService despesaService,
                                     ConsultaDespesaValidator consultaValidator,
                                     RateioPessoaService rateioPessoaService) {

        this.controleDespesaService = controleDespesaService;
        this.despesaService = despesaService;
        this.consultaValidator = consultaValidator;
        this.rateioPessoaService = rateioPessoaService;
    }

    @GetMapping("/consultar")
    public ResponseEntity consultaDadosDash(@RequestParam(value = "mes", required = false, defaultValue = "0") String mes,
                                            @RequestParam(value = "ano", required = false, defaultValue = "0") String ano,
                                            @RequestParam(value = "pagina", required = false, defaultValue = "0") String pagina) {


        try {

            final var dadosConsultaDespesaDTO = new DadosConsultaDespesaDTO(ano, mes, null);
            final var validate = consultaValidator.validate(dadosConsultaDespesaDTO);

            if(validate.isValid()) {
                var despesas = despesaService.buscarPorPeriodo(Integer.parseInt(mes), Integer.parseInt(ano), Integer.parseInt(pagina));
                final var valoresConsolidados = controleDespesaService.retornaDadosDespesaDash(despesas.getTransacoes());
                final var despesaConsolidadaDTO = new DashDTO();
                despesaConsolidadaDTO.setItensPorCategoria(valoresConsolidados.get(0));
                despesaConsolidadaDTO.setItensPorInstrumentos(valoresConsolidados.get(2));
                return ResponseEntity.ok().body(despesaConsolidadaDTO);
            }else{
                return ResponseEntity.badRequest().body(validate.getErrors());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();

    }

    @GetMapping("/calculo")
    public ResponseEntity consultaDespesasAPagar(@RequestParam Integer mes, @RequestParam Integer ano){

        ListaDespesaPorDonoDTO dto = new ListaDespesaPorDonoDTO(rateioPessoaService.calculaRateio(mes,ano));
        return  ResponseEntity.ok(dto);
    }


}
