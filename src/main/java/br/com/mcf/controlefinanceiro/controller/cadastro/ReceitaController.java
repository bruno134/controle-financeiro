package br.com.mcf.controlefinanceiro.controller.cadastro;

import br.com.mcf.controlefinanceiro.controller.cadastro.dto.ReceitaDTO;
import br.com.mcf.controlefinanceiro.controller.cadastro.validator.ConsultaDespesaValidator;
import br.com.mcf.controlefinanceiro.controller.cadastro.validator.InsereDespesaValidator;
import br.com.mcf.controlefinanceiro.model.exceptions.ReceitaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.transacao.Receita;
import br.com.mcf.controlefinanceiro.service.transacao.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("receita")
public class ReceitaController {

    final ReceitaService service;

    @Autowired
    InsereDespesaValidator validator;
    @Autowired
    ConsultaDespesaValidator consultaValidator;

    public ReceitaController(ReceitaService service) {
        this.service = service;
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity buscaReceita(@PathVariable Long id) {

        try {
            final Optional<Receita> receitaEncontrada = service.buscarPorId(id);
            return ResponseEntity.ok(new ReceitaDTO(receitaEncontrada.get()));
        } catch (Exception e) {
                e.printStackTrace();
        } catch (ReceitaNaoEncontradaException e) {
                return ResponseEntity.notFound().build();
        }

        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/inserir")
    public ResponseEntity<Object> insereNovaReceita(@RequestBody ReceitaDTO receitaDTO) {
        //TODO validador de receita

            try {
                Receita receita = receitaDTO.toObject();
                service.inserir(receita);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }

    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity alterarReceita(@PathVariable("id") Integer id, @RequestBody ReceitaDTO receitaDTO) {

        //TODO incluir validador receita

            try {
                receitaDTO.setId(id);
                final Optional<Receita> receitaAlterada = service.alterar(receitaDTO.toObject());

                if (receitaAlterada.isPresent()) {
                    final ReceitaDTO despesaRespostaDTO = new ReceitaDTO(receitaAlterada.get());
                    return ResponseEntity.ok(despesaRespostaDTO);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (ReceitaNaoEncontradaException e) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagarReceita(@PathVariable("id") Integer id) {

        try {
            Receita receitaParaApagar = new Receita();
            receitaParaApagar.setId(id);
            service.apagar(receitaParaApagar);
            return ResponseEntity.ok().build();
        } catch (Exception  e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        } catch (ReceitaNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }

    }
}