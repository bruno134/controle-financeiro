package br.com.mcf.controlefinanceiro.controller;

import br.com.fluentvalidator.Validator;
import br.com.fluentvalidator.context.ValidationResult;
import br.com.mcf.controlefinanceiro.controller.dto.DespesaDTO;
import br.com.mcf.controlefinanceiro.controller.validator.InsereDespesaValidator;
import br.com.mcf.controlefinanceiro.exceptions.DespesaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.service.CadastroDespesaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("despesa")
public class CadastroDespesaController {

    final CadastroDespesaService service;
    final Validator<DespesaDTO> validator;

    public CadastroDespesaController(CadastroDespesaService service,
                                     Validator<DespesaDTO> validator){
        this.service = service;
        this.validator = validator;
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity buscaDespesa(@PathVariable Integer id){
        final Optional<Despesa> despesaEncontrada = service.buscaDespesaPorID(id);

        try {
            if(despesaEncontrada.isPresent())
                return ResponseEntity.ok(new DespesaDTO(despesaEncontrada.get()));
            else
                return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/consultar")
    public ResponseEntity<List<DespesaDTO>> buscaDespesaPorMes(@RequestParam(value = "mes", required = false, defaultValue = "0") int mes,
                                                               @RequestParam(value = "ano", required = false, defaultValue = "0")int ano){

        List<Despesa> despesas;

        try{
            despesas = service.buscaDespesaPorParametros(mes,ano);
            return ResponseEntity.ok().body(DespesaDTO.listaDto(despesas));
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/inserir")
    public ResponseEntity insereNovaDespesa(@RequestBody DespesaDTO despesaDTO){
        var validate = validator.validate(despesaDTO);

        if(validate.isValid()){
            try {
                service.insere(despesaDTO.toObject());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        }else{
            return ResponseEntity.unprocessableEntity().body(validate.getErrors());
        }
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity alterarDespesa(@PathVariable("id") Integer id,@RequestBody DespesaDTO despesaDTO){

        var validate = validator.validate(despesaDTO);

        if(validate.isValid()){
            try {
                despesaDTO.setId(id);
                final Optional<Despesa> despesaAlterada = service.alterarDespesa(despesaDTO.toObject());

                if(despesaAlterada.isPresent()) {
                    final DespesaDTO despesaRespostaDTO = new DespesaDTO(despesaAlterada.get());
                    return ResponseEntity.ok(despesaRespostaDTO);
                }else{
                    return ResponseEntity.notFound().build();
                }
            }catch (DespesaNaoEncontradaException e){
                return ResponseEntity.notFound().build();
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        }else{
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
}
