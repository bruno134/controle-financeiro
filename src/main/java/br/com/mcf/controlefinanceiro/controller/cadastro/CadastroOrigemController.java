package br.com.mcf.controlefinanceiro.controller.cadastro;

import br.com.mcf.controlefinanceiro.controller.cadastro.dto.OrigemDTO;
import br.com.mcf.controlefinanceiro.exceptions.OrigemNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Origem;
import br.com.mcf.controlefinanceiro.service.CadastroOrigemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("origem")
public class CadastroOrigemController {

    final CadastroOrigemService origemService;

    public CadastroOrigemController(CadastroOrigemService origemService){
        this.origemService = origemService;
    }


    @GetMapping("/consultar")
    public ResponseEntity buscar(){
        List<Origem> lista;
        try{

            lista = origemService.consultarTudo();
            if(lista.size()>0)
                return ResponseEntity.ok().body(OrigemDTO.listaDto(lista));
            else
                return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/inserir")
    public ResponseEntity inserir(@RequestBody OrigemDTO origemDTO){

        try {

            origemService.inserir(origemDTO.getNome());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity alterar(@PathVariable("id") Integer id,
                                               @RequestBody OrigemDTO origemDTO){
        try {
            origemDTO.setId(id);
            var origemAlterada = origemService.alterar(origemDTO.toObject());
            if(origemAlterada.isPresent()){
                var dto = new OrigemDTO(origemAlterada.get());
                return ResponseEntity.ok(dto);
            }
        }catch (OrigemNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagar(@PathVariable("id") Integer id) {

        try {
            origemService.apagar(id);
            return ResponseEntity.ok().build();
        }catch (OrigemNaoEncontradaException e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
