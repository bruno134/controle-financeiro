package br.com.mcf.controlefinanceiro.controller.cadastro;

import br.com.mcf.controlefinanceiro.controller.cadastro.dto.InstrumentoDTO;
import br.com.mcf.controlefinanceiro.model.exceptions.InstrumentoNaoEncontradoException;
import br.com.mcf.controlefinanceiro.model.dominio.Instrumento;
import br.com.mcf.controlefinanceiro.service.dominio.CadastroInstrumentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("instrumento")
public class CadastroInstrumentoController {

    final CadastroInstrumentoService instrumentoService;

    public CadastroInstrumentoController(CadastroInstrumentoService instrumentoService){
        this.instrumentoService = instrumentoService;
    }


    @GetMapping("/consultar")
    public ResponseEntity buscar(){
        List<Instrumento> lista;
        try{

            lista = instrumentoService.consultarTudo();
            if(lista.size()>0)
                return ResponseEntity.ok().body(InstrumentoDTO.listaDto(lista));
            else
                return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/inserir")
    public ResponseEntity inserir(@RequestBody InstrumentoDTO instrumentoDTO){

        try {

            instrumentoService.inserir(instrumentoDTO.getNome());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity alterar(@PathVariable("id") Integer id,
                                               @RequestBody InstrumentoDTO instrumentoDTO){
        try {
            instrumentoDTO.setId(id);
            var tipoAlterada = instrumentoService.alterar(instrumentoDTO.toObject());
            if(tipoAlterada.isPresent()){
                var dto = new InstrumentoDTO(tipoAlterada.get());
                return ResponseEntity.ok(dto);
            }
        }catch (InstrumentoNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagar(@PathVariable("id") Integer id) {

        try {
            instrumentoService.apagar(id);
            return ResponseEntity.ok().build();
        }catch (InstrumentoNaoEncontradoException e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
