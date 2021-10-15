package br.com.mcf.controlefinanceiro.controller.cadastro;

import br.com.fluentvalidator.Validator;
import br.com.fluentvalidator.context.ValidationResult;
import br.com.mcf.controlefinanceiro.controller.cadastro.dto.DadosConsultaDespesaDTO;
import br.com.mcf.controlefinanceiro.controller.cadastro.dto.DespesaDTO;
import br.com.mcf.controlefinanceiro.controller.cadastro.validator.ConsultaDespesaValidator;
import br.com.mcf.controlefinanceiro.controller.cadastro.validator.InsereDespesaValidator;
import br.com.mcf.controlefinanceiro.controller.dto.ErrorsDTO;
import br.com.mcf.controlefinanceiro.exceptions.DespesaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.service.CadastroDespesaService;
import br.com.mcf.controlefinanceiro.service.CadastroPorArquivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("despesa")
public class CadastroDespesaController {

    final CadastroDespesaService service;
    final CadastroPorArquivoService arquivoService;

    @Autowired
    InsereDespesaValidator validator;
    @Autowired
    ConsultaDespesaValidator consultaValidator;

    public CadastroDespesaController(CadastroDespesaService service, CadastroPorArquivoService arquivoService) {
        this.service = service;
        this.arquivoService = arquivoService;
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity buscaDespesa(@PathVariable Integer id) {
        final Optional<Despesa> despesaEncontrada = service.buscaDespesaPorID(id);

        try {
            if (despesaEncontrada.isPresent())
                return ResponseEntity.ok(new DespesaDTO(despesaEncontrada.get()));
            else
                return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/consultar")
    public ResponseEntity buscaDespesaPorMes(@RequestParam(value = "mes", required = false, defaultValue = "0") String mes,
                                             @RequestParam(value = "ano", required = false, defaultValue = "0") String ano) {

        try {
            List<Despesa> despesas;
            final var dadosConsultaDespesaDTO = new DadosConsultaDespesaDTO(ano, mes, null);
            final var validate = consultaValidator.validate(dadosConsultaDespesaDTO);

            if (validate.isValid()) {
                despesas = service.buscaDespesaPorParametros(Integer.parseInt(mes), Integer.parseInt(ano));
                return ResponseEntity.ok().body(DespesaDTO.listaDto(despesas));
            } else {
                return ResponseEntity.badRequest().body(validate.getErrors());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/inserir")
    public ResponseEntity insereNovaDespesa(@RequestBody DespesaDTO despesaDTO) {
        var validate = validator.validate(despesaDTO);

        if (validate.isValid()) {
            try {
                service.insere(despesaDTO.toObject());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        } else {
            return ResponseEntity.unprocessableEntity().body(new ErrorsDTO(validate.getErrors()));
        }
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity alterarDespesa(@PathVariable("id") Integer id, @RequestBody DespesaDTO despesaDTO) {

        //TODO incluir validaçao de ID numerico
        var validate = validator.validate(despesaDTO);

        if (validate.isValid()) {
            try {
                despesaDTO.setId(id);
                final Optional<Despesa> despesaAlterada = service.alterarDespesa(despesaDTO.toObject());

                if (despesaAlterada.isPresent()) {
                    final DespesaDTO despesaRespostaDTO = new DespesaDTO(despesaAlterada.get());
                    return ResponseEntity.ok(despesaRespostaDTO);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (DespesaNaoEncontradaException e) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        } else {
            return ResponseEntity.unprocessableEntity().body(validate.getErrors());
        }
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagarDespesa(@PathVariable("id") Integer id) {

        try {
            service.apagarDespesa(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/import")
    public ResponseEntity importaExcel (@RequestParam("file") MultipartFile dataFile,
                                        @RequestParam("instrumento") String instrumento) {




        //TODO Testar/tratar se planilha excel vier zerada, fora do formato, em xlsx
        try{
            List<DespesaDTO> dtoList = DespesaDTO.listaDto(
                    arquivoService.importaDespesaDoExcel(dataFile.getInputStream(), instrumento)
            );

            return ResponseEntity.ok(dtoList);

        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/inserir/lista")
    public ResponseEntity inserirDespesaEmLote(@RequestBody List<DespesaDTO> listaDeDespesas){

        System.out.println("me chamaram ==> " + listaDeDespesas);
        try {
            //TODO colocar validação da entrada

            service.insereEmLista(DespesaDTO.listDtoToListDespesa(listaDeDespesas));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }



    }
}