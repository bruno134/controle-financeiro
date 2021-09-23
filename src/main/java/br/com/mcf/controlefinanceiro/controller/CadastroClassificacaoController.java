package br.com.mcf.controlefinanceiro.controller;

import br.com.mcf.controlefinanceiro.controller.dto.ClassificacaoDTO;
import br.com.mcf.controlefinanceiro.controller.dto.DespesaDTO;
import br.com.mcf.controlefinanceiro.exceptions.ClassificacaoNaoEncontradaException;
import br.com.mcf.controlefinanceiro.exceptions.DespesaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Classificacao;
import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.service.CadastroClassificacaoService;
import br.com.mcf.controlefinanceiro.service.CadastroDespesaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("classificacao")
public class CadastroClassificacaoController {

    final CadastroClassificacaoService service;

    public CadastroClassificacaoController(CadastroClassificacaoService service){
        this.service = service;
    }


    @GetMapping("/consultar")
    public ResponseEntity<List<ClassificacaoDTO>> buscaTodasClassificacoes(){

        List<Classificacao> lista;

        try{
            //TODO Deve retornar 404 para lista vazia?
            lista = service.consultarTodasClassificacoes();
            return ResponseEntity.ok().body(ClassificacaoDTO.listaDto(lista));
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/inserir")
    public ResponseEntity cadastrarClassificacao(@RequestBody ClassificacaoDTO classificacaoDTO){

        try {

            service.cadastrarClassificao(classificacaoDTO.getNome());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity alterarClassificacao(@PathVariable("id") Integer id,
                                               @RequestBody ClassificacaoDTO classificacaoDTO){

        try {
            classificacaoDTO.setId(id);
            final Optional<Classificacao> classificacaoAlterada = service.alterarClassificacao(classificacaoDTO.toClassificacao());

            if(classificacaoAlterada.isPresent()) {
                final ClassificacaoDTO respostaDTO = new ClassificacaoDTO(classificacaoAlterada.get());
                return ResponseEntity.ok(respostaDTO);
            }
        }catch (ClassificacaoNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagarClassificacao(@PathVariable("id") Integer id) {

        try {
            service.apagarClassificacao(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
