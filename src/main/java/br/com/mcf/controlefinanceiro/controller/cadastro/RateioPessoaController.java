package br.com.mcf.controlefinanceiro.controller.cadastro;

import br.com.mcf.controlefinanceiro.controller.cadastro.dto.RateioPessoaDTO;
import br.com.mcf.controlefinanceiro.entity.RateioPessoaEntity;
import br.com.mcf.controlefinanceiro.entity.embedded.RateioPessoaEmbeddedKey;
import br.com.mcf.controlefinanceiro.exceptions.RateioPessoaExistenteException;
import br.com.mcf.controlefinanceiro.exceptions.RateioPessoaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.RateioPessoa;
import br.com.mcf.controlefinanceiro.service.RateioPessoaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("rateio")
public class RateioPessoaController {

    private RateioPessoaService rateioPessoaService;

    public RateioPessoaController(RateioPessoaService rateioPessoaService){
        this.rateioPessoaService = rateioPessoaService;
    }

    @PostMapping("/inserir")
    public ResponseEntity inserir(@RequestBody RateioPessoaDTO rateioPessoaDTO){

        try{
            final var rateioPessoa = rateioPessoaService.inserir(rateioPessoaDTO.toObject());
            return ResponseEntity.status(HttpStatus.CREATED).body(rateioPessoa);
        }catch (Exception | RateioPessoaNaoEncontradaException e){
            return ResponseEntity.internalServerError().build();
        }
        catch (RateioPessoaExistenteException e){
            //TODO devolver mensagem de erro
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping("/consultar")
    public ResponseEntity buscar(@RequestParam("mesCompetenciaRateio") Integer mesCompetenciaRateio,
                                 @RequestParam("anoCompetenciaRateio") Integer anoCompetenciaRateio){

        try{

            RateioPessoa rateioPessoa = new RateioPessoa();
            rateioPessoa.setMesCompetenciaRateio(mesCompetenciaRateio);
            rateioPessoa.setAnoCompetenciaRateio(anoCompetenciaRateio);

            List<RateioPessoa> listaRateio = rateioPessoaService.consultarListaRateio(rateioPessoa);

            if(listaRateio.size()>0)
                return ResponseEntity.ok(listaRateio);
            else
                return ResponseEntity.notFound().build();

        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/alterar")
    public ResponseEntity alterarRateio(@RequestBody RateioPessoaDTO rateioPessoaDTO){

        try {
            final var rateioPessoaAlterada = rateioPessoaService.alterar(rateioPessoaDTO.toObject());
            return ResponseEntity.ok(rateioPessoaAlterada);
        } catch (RateioPessoaNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/apagar")
    ResponseEntity apagarRateio(@RequestBody RateioPessoaDTO rateioPessoaDTO){
        try {
            rateioPessoaService.apagar(rateioPessoaDTO.toObject());
             return ResponseEntity.ok().build();
        } catch (RateioPessoaNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
