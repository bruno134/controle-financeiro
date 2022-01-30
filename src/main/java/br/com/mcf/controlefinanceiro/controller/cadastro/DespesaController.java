package br.com.mcf.controlefinanceiro.controller.cadastro;

import br.com.mcf.controlefinanceiro.controller.cadastro.dto.DadosConsultaDespesaDTO;
import br.com.mcf.controlefinanceiro.controller.cadastro.dto.DespesaDTO;
import br.com.mcf.controlefinanceiro.controller.cadastro.dto.ListaDespesaDTO;
import br.com.mcf.controlefinanceiro.controller.cadastro.validator.ConsultaDespesaValidator;
import br.com.mcf.controlefinanceiro.controller.cadastro.validator.InsereDespesaValidator;
import br.com.mcf.controlefinanceiro.controller.dto.ErrorsDTO;
import br.com.mcf.controlefinanceiro.model.exceptions.DespesaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.exceptions.TransacaoNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.transacao.Despesa;
import br.com.mcf.controlefinanceiro.model.transacao.PaginaTransacao;
import br.com.mcf.controlefinanceiro.model.transacao.Transacao;
import br.com.mcf.controlefinanceiro.service.transacao.ImportArquivoService;
import br.com.mcf.controlefinanceiro.service.transacao.DespesaService;
import org.apache.regexp.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("despesa")
public class DespesaController {

    final DespesaService service;
    final ImportArquivoService arquivoService;

    @Autowired
    InsereDespesaValidator validator;
    @Autowired
    ConsultaDespesaValidator consultaValidator;

    public DespesaController(DespesaService service, ImportArquivoService arquivoService) {
        this.service = service;
        this.arquivoService = arquivoService;
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity buscaDespesa(@PathVariable Integer id) {



        try {
            final Optional<Transacao> despesaEncontrada = service.buscarPorID(id);

            if (despesaEncontrada.isPresent()){
                return ResponseEntity.ok(new DespesaDTO((Despesa) despesaEncontrada.get()));
            } else
                return ResponseEntity.notFound().build();
        }
            catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/consultar")
    public ResponseEntity buscaDespesaPorMes(@RequestParam(value = "mes", required = false, defaultValue = "0") String mes,
                                             @RequestParam(value = "ano", required = false, defaultValue = "0") String ano,
                                             @RequestParam(value = "pagina", required = false, defaultValue = "0") String pagina,
                                             @RequestParam(value = "tamanhoPagina", required = false, defaultValue = "0") String tamanhoPagina) {

        try {
            PaginaTransacao despesas;
            final var dadosConsultaDespesaDTO = new DadosConsultaDespesaDTO(ano, mes, pagina, tamanhoPagina);
            final var validate = consultaValidator.validate(dadosConsultaDespesaDTO);

            if (validate.isValid()) {
                despesas = service.buscaPorMesAnoPaginado(Integer.parseInt(mes), Integer.parseInt(ano), Integer.parseInt(pagina), Integer.parseInt(tamanhoPagina));
                if(!despesas.getTransacoes().isEmpty())
                    return ResponseEntity.ok().body(new ListaDespesaDTO(despesas));
                else
                    return  ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().body(validate.getErrors());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/inserir")
    public ResponseEntity insereNovaDespesa(@RequestBody DespesaDTO despesaDTO) {
        var validate = validator.validate(despesaDTO);

        if (validate.isValid()) {
            try {
                service.inserir(despesaDTO.toObject());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        } else {
            return ResponseEntity.unprocessableEntity().body(new ErrorsDTO(validate.getErrors()));
        }
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity alterarDespesa(@PathVariable("id") Integer id, @RequestBody DespesaDTO despesaDTO) {

        //TODO incluir validaçao de ID numerico
        var validate = validator.validate(despesaDTO);

        if (validate.isValid()) {
            try {
                despesaDTO.setId(id);
                final Optional<Transacao> despesaAlterada = service.alterar(despesaDTO.toObject());

                if (despesaAlterada.isPresent()) {
                    //TODO tirar o cast
                    final DespesaDTO despesaRespostaDTO = new DespesaDTO((Despesa) despesaAlterada.get());
                    return ResponseEntity.ok(despesaRespostaDTO);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        } else {
            return ResponseEntity.unprocessableEntity().body(validate.getErrors());
        }
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagarDespesa(@PathVariable("id") Integer id) {

        try {
            Despesa despesaParaApagar = new Despesa();
            despesaParaApagar.setId(id);
            service.apagar(despesaParaApagar);
            return ResponseEntity.ok().build();
        }catch (TransacaoNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
//TODO arrumar o importar

//    @PostMapping("/import")
//    public ResponseEntity importaExcel (@RequestParam("file") MultipartFile dataFile,
//                                        @RequestParam("instrumento") String instrumento,
//                                        @RequestParam("mes") Integer mes,
//                                        @RequestParam("ano") Integer ano) {
//
//
//
//
//        //TODO Testar/tratar se planilha excel vier zerada, fora do formato, em xlsx
//        try{
//            List<DespesaDTO> dtoList = DespesaDTO.dtoList(
//                    arquivoService.importaDespesaDoExcel(dataFile.getInputStream(), instrumento, mes,ano)
//            );
//
//            return ResponseEntity.ok(dtoList);
//
//        }catch (Exception | TransactionBusinessException e){
//            return ResponseEntity.internalServerError().build();
//        }
//    }

    @PostMapping("/inserir/lista")
    public ResponseEntity inserirDespesaEmLote(@RequestBody List<DespesaDTO> listaDeDespesas){

        try {
            //TODO colocar validação da entrada

            service.inserirEmLista(DespesaDTO.listDtoToListObject(listaDeDespesas));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }



    }
}