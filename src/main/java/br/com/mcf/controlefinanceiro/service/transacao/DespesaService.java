package br.com.mcf.controlefinanceiro.service.transacao;

import br.com.mcf.controlefinanceiro.model.dominio.TipoTransacao;
import br.com.mcf.controlefinanceiro.model.entity.TransacaoEntity;
import br.com.mcf.controlefinanceiro.model.exceptions.TransacaoNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.repository.TransacaoRepository;
import br.com.mcf.controlefinanceiro.model.transacao.Despesa;
import br.com.mcf.controlefinanceiro.model.transacao.PaginaTransacao;
import br.com.mcf.controlefinanceiro.model.transacao.Transacao;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DespesaService implements TransacaoImplementator{

    @Autowired
    private PeriodoMes periodoMes;
    private final TransacaoRepository repository;
    private static final Integer tamanhoPadraoPagina = 50;

//    private final TransacaoService<Despesa> service;

    public DespesaService(TransacaoRepository repository) {
       this.repository = repository;
    }

    @Transactional
    @Override
    public Transacao inserir(Transacao transacao) {
        return new Despesa(repository.save(toEntity(transacao)));
    }

    @Transactional
    @Override
    public Optional<Transacao> alterar(Transacao transacao)  {

        final var transacaoEncontrada = repository.findById(Long.valueOf(transacao.getId()));

        if(transacaoEncontrada.isPresent()){

            transacaoEncontrada.get().setValor(transacao.getValor());
            transacaoEncontrada.get().setData(transacao.getData());
            transacaoEncontrada.get().setDescricao(transacao.getDescricao());
            transacaoEncontrada.get().setCategoria(transacao.getCategoria());
            transacaoEncontrada.get().setTipoRateio(transacao.getTipoRateio());
            transacaoEncontrada.get().setInstrumento(transacao.getInstrumento());
            transacaoEncontrada.get().setDataCompetencia(transacao.getDataCompetencia());

            return Optional.of(new Despesa(repository.saveAndFlush(transacaoEncontrada.get())));
        }
        else return Optional.empty();
    }


    @Override
    public Optional<Transacao> buscarPorID(Integer id)  {

        final var tipoTransacaoEncontrada = repository.findByIdAndTipoTransacao(id, TipoTransacao.DESPESA.getDescricao());

        return tipoTransacaoEncontrada.map(Despesa::new);

    }

    @Transactional
    @Override
    public void apagar(Transacao transacao) throws TransacaoNaoEncontradaException{

        final var transacaoEncontrada = repository.findById(Long.valueOf(transacao.getId()));

        if(transacaoEncontrada.isPresent()){
            repository.delete(transacaoEncontrada.get());
        } else
            throw new TransacaoNaoEncontradaException(ConstantMessages.TRANSACAO_NAO_ENCONTRADA + " para id:" + transacao.getId());
    }


    public PaginaTransacao buscarPorPeriodoPaginado(LocalDate dataInicio, LocalDate dataFim, Integer pagina, Integer tamanhoDaPagina) {

        PaginaTransacao paginaRetorno = new PaginaTransacao();
        Pageable page;
        Integer tamanhoPagina = tamanhoDaPagina>0?tamanhoDaPagina:tamanhoPadraoPagina;

        try{
            if(pagina>0){
                page = PageRequest.of(pagina-1, tamanhoPagina, Sort.by("id"));
                final var despesaPage = repository
                        .findAllByDataCompetenciaBetweenAndTipoTransacaoOrderByDataCompetenciaDesc(dataInicio, dataFim,
                                TipoTransacao.DESPESA.getDescricao(), page);
                if(despesaPage.hasContent()){
                    paginaRetorno.setTransacoes(toList(despesaPage.toList()));
                    paginaRetorno.setPaginaAnterior(despesaPage.hasPrevious() ? pagina - 1 : null);
                    paginaRetorno.setProximaPagina(despesaPage.hasNext() ? pagina + 1 : null);
                    paginaRetorno.setTotalPaginas(despesaPage.getTotalPages());

                }
            } else {
                paginaRetorno.setTransacoes(toList(repository.findAllByDataCompetenciaBetweenAndTipoTransacaoOrderByDataCompetenciaDesc(dataInicio, dataFim, TipoTransacao.DESPESA.getDescricao())));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return paginaRetorno;
    }

    @Override
    public List<Transacao> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return toList(repository
                 .findAllByDataCompetenciaBetweenAndTipoTransacaoOrderByDataCompetenciaDesc(
                         dataInicio, dataFim, TipoTransacao.DESPESA.getDescricao())
                 );
    }

    public List<Transacao> buscaPorMesAno(Integer mes, Integer ano) {

        final LocalDate dataInicial = periodoMes.getDataInicioMes(mes,ano);
        final LocalDate dataFinal = periodoMes.getDataFimMes(mes,ano);

        return buscarPorPeriodo(dataInicial,dataFinal);
    }

    public PaginaTransacao buscaPorMesAnoPaginado(Integer mes, Integer ano, Integer pagina, Integer tamanhoPagina) {

        final LocalDate dataInicial = periodoMes.getDataInicioMes(mes,ano);
        final LocalDate dataFinal = periodoMes.getDataFimMes(mes,ano);

        return buscarPorPeriodoPaginado(dataInicial,dataFinal, pagina,tamanhoPagina);
    }

    public List<Transacao> buscarPorAno(Integer ano){

        LocalDate dataInicio = LocalDate.of(ano,1,1);
        LocalDate dataFim = LocalDate.of(ano,12,31);

        return buscarPorPeriodo(dataInicio,dataFim);

    }

    private List<Transacao> toList(List<TransacaoEntity> entityList){
        List<Transacao> transacoes = new ArrayList<>();

        if(entityList!=null)
           entityList.forEach(t -> transacoes.add(new Despesa(t)));

        return transacoes;

    }

    private TransacaoEntity toEntity(Transacao transacao){
        return new TransacaoEntity(transacao.getData(),
                                   transacao.getValor(),
                                   transacao.getDescricao(),
                                   transacao.getCategoria(),
                                   transacao.getTipoRateio(),
                                   transacao.getInstrumento(),
                                   transacao.getTipoTransacao().getDescricao(),
                                   transacao.getDataCompetencia());
    }

    public List<Transacao> inserirEmLista(List<Despesa> listaDespesas) {

        List<Transacao> listaDespesaIncluida = new ArrayList<>();

        if(listaDespesas!=null)
            listaDespesas.forEach(d -> listaDespesaIncluida.add(inserir(d)));

        return listaDespesaIncluida;
    }

//
//    @Transactional
//    public Despesa inserir(Despesa despesa){
//
//        Despesa despesaInserida = null;
//
//       try {
//           despesaInserida = service.inserir(despesa);
//       }catch (NoSuchMethodException | InvocationTargetException |
//               InstantiationException | IllegalAccessException e){
//           e.printStackTrace();
//       }
//
//        return despesaInserida;
//    }
//
//    @Transactional
//    public Optional<Despesa> alterar(Despesa despesa) throws DespesaNaoEncontradaException {
//        try {
//            return service.alterar(despesa);
//        } catch (TransacaoNaoEncontradaException e) {
//            e.printStackTrace();
//            throw new DespesaNaoEncontradaException(ConstantMessages.DESPESA_NAO_ENCONTRADA);
//        }
//    }
//
//    @Transactional
//    public void apagar(Despesa despesa) throws DespesaNaoEncontradaException {
//        try{
//            service.apagar(despesa);
//        }catch (TransacaoNaoEncontradaException e){
//            throw new DespesaNaoEncontradaException(ConstantMessages.DESPESA_NAO_ENCONTRADA);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public Optional<Despesa> buscarPorID(Long id) throws DespesaNaoEncontradaException {
//        try {
//            return service.buscarPorID(id);
//        }catch (TransacaoNaoEncontradaException e){
//            throw new DespesaNaoEncontradaException(ConstantMessages.DESPESA_NAO_ENCONTRADA);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return  Optional.empty();
//    }
//
//    public List<Despesa> buscarTodas(){
//        return service.buscarTodas(TipoTransacao.DESPESA);
//    }
//
//    public List<Despesa> buscarTodasPor(Integer ano){
//                return service.buscarTodasPor(ano,TipoTransacao.DESPESA);
//    }
//
//    public List<Despesa> inserirEmLista(List<Despesa> despesaList){
//
//        List<Despesa> listaDespesaIncluida = new ArrayList<>();
//
//        if(despesaList!=null)
//            despesaList.forEach(d -> listaDespesaIncluida.add(inserir(d)));
//
//        return listaDespesaIncluida;
//    }
//
//    public ListaTransacao<Despesa> buscarPorPeriodo(int mes, int ano, int pagina, Integer tamanhoPagina){
//
//        /**
//         *  O dia inicial do mês é definido dentro da propriedade 'controle-financeiro.inicio-mes.dia' no application.properties
//         *  Não defina a propriedade 'controle-financeiro.inicio-mes.dia', caso não deseje customizar o  periodo de competencia do mes.
//         */
//
//        final LocalDate dataInicial = periodoMes.getDataInicioMes(mes,ano);
//        final LocalDate dataFinal = periodoMes.getDataFimMes(mes,ano);
//
//        return service.buscarPorPeriodo(dataInicial,dataFinal, TipoTransacao.DESPESA,pagina, tamanhoPagina);
//    }



}
