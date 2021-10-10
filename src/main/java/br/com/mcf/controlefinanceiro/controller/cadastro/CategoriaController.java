package br.com.mcf.controlefinanceiro.controller.cadastro;

import br.com.mcf.controlefinanceiro.controller.cadastro.dto.CategoriaDTO;
import br.com.mcf.controlefinanceiro.exceptions.CategoriaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Categoria;
import br.com.mcf.controlefinanceiro.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("categoria")
public class CategoriaController {

    final CategoriaService service;

    public CategoriaController(CategoriaService service){
        this.service = service;
    }


    @GetMapping("/consultar")
    public ResponseEntity<List<CategoriaDTO>> buscaTodasClassificacoes(){

        List<Categoria> lista;

        try{
            //TODO Deve retornar 404 para lista vazia?
            lista = service.consultarTodasClassificacoes();
            return ResponseEntity.ok().body(CategoriaDTO.listaDto(lista));
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/inserir")
    public ResponseEntity cadastrarCategoria(@RequestBody CategoriaDTO categoriaDTO){

        try {

            service.cadastrarClassificao(categoriaDTO.getNome());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity alterarCategoria(@PathVariable("id") Integer id,
                                               @RequestBody CategoriaDTO categoriaDTO){

        try {
            categoriaDTO.setId(id);
            final Optional<Categoria> categoriaAlterada = service.alterarCategoria(categoriaDTO.toCategoria());

            if(categoriaAlterada.isPresent()) {
                final CategoriaDTO respostaDTO = new CategoriaDTO(categoriaAlterada.get());
                return ResponseEntity.ok(respostaDTO);
            }
        }catch (CategoriaNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagarCategoria(@PathVariable("id") Integer id) {

        try {
            service.apagarCategoria(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
