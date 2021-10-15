package br.com.mcf.controlefinanceiro.controller.dash;

import br.com.mcf.controlefinanceiro.controller.cadastro.dto.DadosConsultaDespesaDTO;
import br.com.mcf.controlefinanceiro.controller.dash.dto.DashDTO;
import br.com.mcf.controlefinanceiro.controller.cadastro.validator.ConsultaDespesaValidator;
import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.service.CadastroDespesaService;
import br.com.mcf.controlefinanceiro.service.DashDespesaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dash")
public class ControleDespesaController {

    private final DashDespesaService controleDespesaService;
    private final CadastroDespesaService cadastroDespesaService;
    private final ConsultaDespesaValidator consultaValidator;

    public ControleDespesaController(DashDespesaService controleDespesaService,
                                     CadastroDespesaService cadastroDespesaService,
                                     ConsultaDespesaValidator consultaValidator) {

        this.controleDespesaService = controleDespesaService;
        this.cadastroDespesaService = cadastroDespesaService;
        this.consultaValidator = consultaValidator;
    }

    @GetMapping("/consultar")
    public ResponseEntity consultaDadosDash(@RequestParam(value = "mes", required = false, defaultValue = "0") String mes,
                                            @RequestParam(value = "ano", required = false, defaultValue = "0") String ano){


        try {

            final var dadosConsultaDespesaDTO = new DadosConsultaDespesaDTO(ano, mes, null);
            final var validate = consultaValidator.validate(dadosConsultaDespesaDTO);

            if(validate.isValid()) {
                List<Despesa> despesas = cadastroDespesaService.buscaDespesaPorParametros(Integer.parseInt(mes), Integer.parseInt(ano));
                final var valoresConsolidados = controleDespesaService.retornaDadosDespesaDash(despesas);
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


}
