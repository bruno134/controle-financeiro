package br.com.mcf.controlefinanceiro.controller.cadastro;

import br.com.mcf.controlefinanceiro.controller.cadastro.dto.TipoDTO;
import br.com.mcf.controlefinanceiro.exceptions.TipoNaoEncontradoException;
import br.com.mcf.controlefinanceiro.model.Tipo;
import br.com.mcf.controlefinanceiro.service.CadastroTipoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("tipo")
public class CadastroTipoController {

    final CadastroTipoService tipoService;

    public CadastroTipoController(CadastroTipoService tipoService){
        this.tipoService = tipoService;
    }


    @GetMapping("/consultar")
    public ResponseEntity buscar(){
        List<Tipo> lista;
        try{

            lista = tipoService.consultarTudo();
            if(lista.size()>0)
                return ResponseEntity.ok().body(TipoDTO.listaDto(lista));
            else
                return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/inserir")
    public ResponseEntity inserir(@RequestBody TipoDTO tipoDTO){

        try {

            tipoService.inserir(tipoDTO.getNome());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity alterar(@PathVariable("id") Integer id,
                                               @RequestBody TipoDTO tipoDTO){
        try {
            tipoDTO.setId(id);
            var tipoAlterada = tipoService.alterar(tipoDTO.toObject());
            if(tipoAlterada.isPresent()){
                var dto = new TipoDTO(tipoAlterada.get());
                return ResponseEntity.ok(dto);
            }
        }catch (TipoNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagar(@PathVariable("id") Integer id) {

        try {
            tipoService.apagar(id);
            return ResponseEntity.ok().build();
        }catch (TipoNaoEncontradoException e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
