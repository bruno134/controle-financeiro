package br.com.mcf.controlefinanceiro.controller;

import br.com.mcf.controlefinanceiro.controller.dto.DespesaDTO;
import br.com.mcf.controlefinanceiro.exceptions.DespesaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.service.CadastroDespesaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("despesa")
public class CadastroDespesaController {

    final CadastroDespesaService service;

    public CadastroDespesaController(CadastroDespesaService service){
        this.service = service;
    }

    @GetMapping("/consulta/{id}")
    public ResponseEntity buscaDespesa(@PathVariable Integer id){
        final Optional<Despesa> despesaEncontrada = service.consultaDespesa(id);

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

    @GetMapping("/consultatodas")
    public ResponseEntity<List<DespesaDTO>> buscaTodasDespesas(){

        List<Despesa> despesas = new ArrayList<>();

        try{
            //TODO Deve retornar 404 para lista vazia?
            despesas = service.consultasTodasDespesas();
            return ResponseEntity.ok().body(DespesaDTO.listaDto(despesas));
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/novo")
    public ResponseEntity cadastrarDespesa(@RequestBody DespesaDTO despesaDTO){

        try {

            service.cadastrarDespesa(despesaDTO.toDespesa());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity alterarDespesa(@PathVariable("id") Integer id,@RequestBody DespesaDTO despesaDTO){

        try {
            despesaDTO.setId(id);
            final Despesa despesaAlterada = service.alterarDespesa(despesaDTO.toDespesa());
            final DespesaDTO despesaRespostaDTO = new DespesaDTO(despesaAlterada);
            return ResponseEntity.ok(despesaRespostaDTO);
        }catch (DespesaNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagarDespesa(@PathVariable("id") Integer id) {

        try {
            service.apagarDespesa(id);
            return ResponseEntity.ok().build();
        } catch (DespesaNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
