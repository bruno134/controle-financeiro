package br.com.mcf.controlefinanceiro.controller.dash;

import br.com.mcf.controlefinanceiro.controller.cadastro.dto.DadosConsultaDespesaDTO;
import br.com.mcf.controlefinanceiro.controller.cadastro.validator.ConsultaDespesaValidator;
import br.com.mcf.controlefinanceiro.controller.dash.dto.DashDTO;
import br.com.mcf.controlefinanceiro.controller.dash.dto.DespesaMesDTO;
import br.com.mcf.controlefinanceiro.controller.dash.dto.ListaDespesaPorDonoDTO;
import br.com.mcf.controlefinanceiro.service.transacao.ControleDespesaService;
import br.com.mcf.controlefinanceiro.service.rateio.RateioPessoaService;
import br.com.mcf.controlefinanceiro.service.transacao.DespesaService;
import br.com.mcf.controlefinanceiro.util.ConstantMonths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("dash")
public class ControleDespesaController {

    public static final String COMPARTILHADA = "COMPARTILHADA";
    private final ControleDespesaService controleDespesaService;
    private final DespesaService despesaService;
    private final ConsultaDespesaValidator consultaValidator;
    private final RateioPessoaService rateioPessoaService;

    public ControleDespesaController(ControleDespesaService controleDespesaService,
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
                                            @RequestParam(value = "pagina", required = false, defaultValue = "1") String pagina) {


        try {

            final var dadosConsultaDespesaDTO = new DadosConsultaDespesaDTO(ano, mes);
            final var validate = consultaValidator.validate(dadosConsultaDespesaDTO);

            if(validate.isValid()) {
                var despesas = despesaService.buscaPorMesAnoPaginado(Integer.parseInt(mes), Integer.parseInt(ano), Integer.parseInt(pagina),0);
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

        final var rateio = rateioPessoaService.calculaRateio(mes, ano);
        final var totalCompartilhado = rateioPessoaService.retornaValorTotalCompartilhado(rateio);

        ListaDespesaPorDonoDTO dto = new ListaDespesaPorDonoDTO(rateio);

        dto.setValorTotalDespesaCompartilhada("0.0");
        dto.setDescricaoDespesaCompartilhada(COMPARTILHADA);
        if(!totalCompartilhado.isEmpty())
            dto.setValorTotalDespesaCompartilhada(totalCompartilhado.get(COMPARTILHADA).toString());


        return  ResponseEntity.ok(dto);
    }

    @GetMapping("totalconsolidadodespesa")
    public ResponseEntity consultaDespesaConsolidadaPorMes(@RequestParam Integer ano){

        final List<DespesaMesDTO> despesas = new ArrayList<>();
        final var despesaList = despesaService.buscarPorAno(ano);
        final var totalDespesaAno = controleDespesaService.retornaTotalDespesaAno(despesaList);

        totalDespesaAno.forEach(item -> despesas.add(
                                                      new DespesaMesDTO(
                                                                        ConstantMonths.months.get(item.getKey()),
                                                                        String.valueOf(item.getValue())
                                                                        )
                                                    )
                                );

        return ResponseEntity.ok(despesas);
    }

}
