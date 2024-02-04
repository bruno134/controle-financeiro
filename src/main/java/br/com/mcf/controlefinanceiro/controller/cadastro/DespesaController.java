package br.com.mcf.controlefinanceiro.controller.cadastro;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.mcf.controlefinanceiro.controller.cadastro.dto.DadosConsultaDespesaDTO;
import br.com.mcf.controlefinanceiro.controller.cadastro.dto.DespesaDTO;
import br.com.mcf.controlefinanceiro.controller.cadastro.dto.ListaDespesaDTO;
import br.com.mcf.controlefinanceiro.controller.cadastro.validator.ConsultaDespesaValidator;
import br.com.mcf.controlefinanceiro.controller.cadastro.validator.InsereDespesaValidator;
import br.com.mcf.controlefinanceiro.controller.dto.ErrorsDTO;
import br.com.mcf.controlefinanceiro.model.exceptions.TransacaoNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.exceptions.TransactionBusinessException;
import br.com.mcf.controlefinanceiro.model.repository.specification.QueryOperator;
import br.com.mcf.controlefinanceiro.model.repository.specification.SearchCriteria;
import br.com.mcf.controlefinanceiro.model.transacao.Despesa;
import br.com.mcf.controlefinanceiro.model.transacao.PaginaTransacao;
import br.com.mcf.controlefinanceiro.model.transacao.Transacao;
import br.com.mcf.controlefinanceiro.service.transacao.DespesaService;
import br.com.mcf.controlefinanceiro.service.transacao.ImportArquivoService;
import br.com.mcf.controlefinanceiro.service.transacao.PeriodoMes;
import br.com.mcf.controlefinanceiro.service.transacao.load.CarregaArquivoBoFa;
import br.com.mcf.controlefinanceiro.service.transacao.load.CarregaArquivoService;
import br.com.mcf.controlefinanceiro.service.transacao.load.CarregaCCreditoBofa;
import br.com.mcf.controlefinanceiro.service.transacao.load.CarregaCCreditoCapitalOne;
import br.com.mcf.controlefinanceiro.service.transacao.load.CarregarArquivo;

@RestController
@RequestMapping("despesa")
public class DespesaController {

    final DespesaService service;
    final ImportArquivoService arquivoService;

    @Autowired
    InsereDespesaValidator validator;
    @Autowired
    ConsultaDespesaValidator consultaValidator;
    @Autowired
    PeriodoMes periodoMes;

    CarregaArquivoService carregaArquivoService;

    public DespesaController(DespesaService service, ImportArquivoService arquivoService) {
        this.service = service;
        this.arquivoService = arquivoService;
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity buscaDespesa(@PathVariable Integer id) {

        try {
            final Optional<Transacao> despesaEncontrada = service.buscarPorID(id);

            if (despesaEncontrada.isPresent()) {
                return ResponseEntity.ok(new DespesaDTO((Despesa) despesaEncontrada.get()));
            } else
                return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/consultar")
    public ResponseEntity buscaDespesaPorMes(@RequestParam Map<String, String> allParams) {

        try {

            String ano = allParams.remove("ano");
            String mes = allParams.remove("mes");
            String pagina = allParams.remove("pagina");
            String tamanhoPagina = allParams.remove("tamanhoPagina");

            PaginaTransacao despesas;
            final var dadosConsultaDespesaDTO = new DadosConsultaDespesaDTO(ano, mes, pagina, tamanhoPagina);
            final var validate = consultaValidator.validate(dadosConsultaDespesaDTO);

            if (validate.isValid()) {

                final LocalDate dataInicial = periodoMes.getDataInicioMes(Integer.parseInt(mes), Integer.parseInt(ano));
                final LocalDate dataFinal = periodoMes.getDataFimMes(Integer.parseInt(mes), Integer.parseInt(ano));

                List<SearchCriteria> criterios = new ArrayList<>();
                criterios.add(new SearchCriteria("dataCompetencia", QueryOperator.GREATER_OR_EQUAL_THAN,
                        String.valueOf(dataInicial)));
                criterios.add(new SearchCriteria("dataCompetencia", QueryOperator.LESS_OR_EQUAL_THAN,
                        String.valueOf(dataFinal)));
                allParams.forEach((k, v) -> criterios.add(new SearchCriteria(k, QueryOperator.EQUAL, v)));

                despesas = service.buscarDespesaPorParametros(criterios, Integer.parseInt(pagina),
                        Integer.parseInt(tamanhoPagina));

                if (!despesas.getTransacoes().isEmpty())
                    return ResponseEntity.ok().body(new ListaDespesaDTO(despesas));
                else
                    return ResponseEntity.notFound().build();
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

        // TODO incluir validaçao de ID numerico
        var validate = validator.validate(despesaDTO);

        if (validate.isValid()) {
            try {
                despesaDTO.setId(id);
                final Optional<Transacao> despesaAlterada = service.alterar(despesaDTO.toObject());

                if (despesaAlterada.isPresent()) {
                    // TODO tirar o cast
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
        } catch (TransacaoNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
    // TODO arrumar o importar

    @PostMapping("/import")
    public ResponseEntity importaExcel (@RequestParam("file") MultipartFile dataFile,
                                        @RequestParam("instrumento") String instrumento,
                                        @RequestParam("mes") Integer mes,
                                        @RequestParam("ano") Integer ano) {

        //TODO Testar/tratar se planilha excel vier zerada, fora do formato, em xlsx
        try{
            ListaDespesaDTO dtoList = new ListaDespesaDTO();
            List< Transacao> transacaoList = new ArrayList<>();
            //Extrai a lista de transações em separado para poder depois somar o total.

            CarregarArquivo carregaArquivo = null;
            
            switch (instrumento){
                case "Conta Corrente":
                   carregaArquivo = new CarregaArquivoBoFa((FileInputStream) dataFile.getInputStream());
                    break;
                case "Cartão de Crédito - Bofa":
                    carregaArquivo = new CarregaCCreditoBofa((FileInputStream) dataFile.getInputStream());
                    break;
                case "Cartão de Crédito":
                    carregaArquivo = new CarregaCCreditoCapitalOne((FileInputStream) dataFile.getInputStream());
                    break;
                default:
                    break;
            }


           if(carregaArquivo !=null){            
            carregaArquivoService = new CarregaArquivoService(carregaArquivo);
            transacaoList = (List<Transacao>) carregaArquivoService.carregar().get("despesas");
           }else{
            transacaoList = arquivoService.importaDespesaDoExcel(dataFile.getInputStream(), instrumento, mes, ano);
           } 

            
            final var listaDeDespesas = DespesaDTO.dtoList(transacaoList);

            dtoList.setDespesas(listaDeDespesas);
            dtoList.setTotalQuantidadeDeItens(listaDeDespesas.size());
            dtoList.setTotalSomaDosItens(service.somaTotal(transacaoList));
            return ResponseEntity.ok(dtoList);

        }catch (Exception  | TransactionBusinessException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/inserir/lista")
    public ResponseEntity inserirDespesaEmLote(@RequestBody List<DespesaDTO> listaDeDespesas) {

        try {
            // TODO colocar validação da entrada

            service.inserirEmLista(DespesaDTO.listDtoToListObject(listaDeDespesas));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}