package br.com.mcf.controlefinanceiro.controller.cadastro;

import br.com.mcf.controlefinanceiro.controller.cadastro.dto.TipoRateioDTO;
import br.com.mcf.controlefinanceiro.exceptions.TipoRateioNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.TipoRateio;
import br.com.mcf.controlefinanceiro.service.CadastroTipoRateioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("tipo_rateio")
public class CadastroTipoRateioController {

    final CadastroTipoRateioService tipoRateioService;

    public CadastroTipoRateioController(CadastroTipoRateioService tipoRateioService){
        this.tipoRateioService = tipoRateioService;
    }


    @GetMapping("/consultar")
    public ResponseEntity buscar(){
        List<TipoRateio> lista;
        try{

            lista = tipoRateioService.consultarTudo();
            if(lista.size()>0)
                return ResponseEntity.ok().body(TipoRateioDTO.listaDto(lista));
            else
                return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/inserir")
    public ResponseEntity inserir(@RequestBody TipoRateioDTO tipoRateioDTO){

        try {

            tipoRateioService.inserir(tipoRateioDTO.getNome());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity alterar(@PathVariable("id") Integer id,
                                               @RequestBody TipoRateioDTO tipoRateioDTO){
        try {
            tipoRateioDTO.setId(id);
            var tipoRateioAlterado = tipoRateioService.alterar(tipoRateioDTO.toObject());
            if(tipoRateioAlterado.isPresent()){
                var dto = new TipoRateioDTO(tipoRateioAlterado.get());
                return ResponseEntity.ok(dto);
            }
        }catch (TipoRateioNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagar(@PathVariable("id") Integer id) {

        try {
            tipoRateioService.apagar(id);
            return ResponseEntity.ok().build();
        }catch (TipoRateioNaoEncontradaException e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
