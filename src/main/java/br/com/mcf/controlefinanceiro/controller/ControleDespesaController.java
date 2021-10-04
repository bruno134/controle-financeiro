package br.com.mcf.controlefinanceiro.controller;

import br.com.mcf.controlefinanceiro.controller.dto.DadosConsultaDespesaDTO;
import br.com.mcf.controlefinanceiro.controller.dto.DespesaConsolidadaDTO;
import br.com.mcf.controlefinanceiro.controller.validator.ConsultaDespesaValidator;
import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.service.CadastroDespesaService;
import br.com.mcf.controlefinanceiro.service.ControleDespesaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("controle")
public class ControleDespesaController {

    private ControleDespesaService controleDespesaService;
    private CadastroDespesaService cadastroDespesaService;
    private ConsultaDespesaValidator consultaValidator;

    public ControleDespesaController(ControleDespesaService controleDespesaService,
                                     CadastroDespesaService cadastroDespesaService,
                                     ConsultaDespesaValidator consultaValidator) {

        this.controleDespesaService = controleDespesaService;
        this.cadastroDespesaService = cadastroDespesaService;
        this.consultaValidator = consultaValidator;
    }

    @GetMapping("/consultar")
    public ResponseEntity consultaConsolidadoCategoria(@RequestParam(value = "mes", required = false, defaultValue = "0") String mes,
                                                       @RequestParam(value = "ano", required = false, defaultValue = "0") String ano){


        try {

            final var dadosConsultaDespesaDTO = new DadosConsultaDespesaDTO(ano, mes, null);
            final var validate = consultaValidator.validate(dadosConsultaDespesaDTO);

            if(validate.isValid()) {
                List<Despesa> despesas = cadastroDespesaService.buscaDespesaPorParametros(Integer.parseInt(mes), Integer.parseInt(ano));
                final var valoresConsolidados = controleDespesaService.retornaTotalDespesaPorCategoria(despesas);
                final var despesaConsolidadaDTO = new DespesaConsolidadaDTO();
                despesaConsolidadaDTO.setItens(valoresConsolidados);
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
