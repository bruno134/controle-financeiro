package br.com.mcf.controlefinanceiro.service.transacao;

import br.com.mcf.controlefinanceiro.model.dominio.TipoTransacao;
import br.com.mcf.controlefinanceiro.model.entity.TransacaoEntity;
import br.com.mcf.controlefinanceiro.model.exceptions.TransacaoNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.repository.TransacaoRepository;
import br.com.mcf.controlefinanceiro.model.repository.specification.QueryOperator;
import br.com.mcf.controlefinanceiro.model.repository.specification.SearchCriteria;
import br.com.mcf.controlefinanceiro.model.repository.specification.TransacaoSpecification;
import br.com.mcf.controlefinanceiro.model.transacao.Despesa;
import br.com.mcf.controlefinanceiro.model.transacao.PaginaTransacao;
import br.com.mcf.controlefinanceiro.model.transacao.Transacao;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static br.com.mcf.controlefinanceiro.util.ConstantFormat.dbDateFormat;

@Service
public class DespesaService implements TransacaoImplementator{

    @Autowired
    private PeriodoMes periodoMes;
    private final TransacaoRepository repository;
    private static final Integer tamanhoPadraoPagina = 99999;

    public DespesaService(TransacaoRepository repository) {
       this.repository = repository;
    }

    @Transactional
    @Override
    public Transacao inserir(Transacao transacao) {
        return new Despesa(repository.save(toEntity(transacao)));
    }

    public void inserirEmLista(List<Despesa> listaDespesas) {
        if(listaDespesas!=null)
            listaDespesas.forEach(this::inserir);

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

    @Transactional
    @Override
    public void apagar(Transacao transacao) throws TransacaoNaoEncontradaException{

        final var transacaoEncontrada = repository.findById(Long.valueOf(transacao.getId()));

        if(transacaoEncontrada.isPresent()){
            repository.delete(transacaoEncontrada.get());
        } else
            throw new TransacaoNaoEncontradaException(ConstantMessages.TRANSACAO_NAO_ENCONTRADA + " para id:" + transacao.getId());
    }

    @Override
    public Optional<Transacao> buscarPorID(Integer id)  {

        final var tipoTransacaoEncontrada = repository.findByIdAndTipoTransacao(id, TipoTransacao.DESPESA.getDescricao());

        return tipoTransacaoEncontrada.map(Despesa::new);

    }

    public PaginaTransacao buscarPorPeriodoPaginado(LocalDate dataInicio, LocalDate dataFim, Integer pagina, Integer tamanhoDaPagina) {
        return buscarDespesaPorParametros(getSearchCriteriaDoPeriodo(dataInicio,dataFim),pagina,tamanhoDaPagina);
    }

    @Override
    public List<Transacao> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return buscarDespesaPorParametros(getSearchCriteriaDoPeriodo(dataInicio,dataFim),1,0).getTransacoes();
    }

    public List<Transacao> buscaPorMesAno(Integer mes, Integer ano) {
        return buscarDespesaPorParametros(getSearchCriteriaMesEAno(mes, ano),1,0).getTransacoes();
    }

    public PaginaTransacao buscaPorMesAnoPaginado(Integer mes, Integer ano, Integer pagina, Integer tamanhoPagina) {
        return buscarDespesaPorParametros(getSearchCriteriaMesEAno(mes, ano),pagina,tamanhoPagina);
    }

    public List<Transacao> buscarPorAno(Integer ano){
        return buscarDespesaPorParametros(getSearchCriteriaAno(ano),1,0).getTransacoes();
    }

    public PaginaTransacao buscarDespesaPorParametros(List<SearchCriteria> criterios, Integer pagina, Integer tamanhoPagina){

        PaginaTransacao paginaRetorno = new PaginaTransacao();
        Integer tamanhoDaPagina = tamanhoPagina>0?tamanhoPagina:tamanhoPadraoPagina;
        Pageable page = PageRequest.of(pagina-1, tamanhoDaPagina, Sort.by("id"));
        criterios.add(new SearchCriteria("tipoTransacao", QueryOperator.EQUAL, TipoTransacao.DESPESA.getDescricao()));
        Specification<TransacaoEntity> finalSpecification = getTransacaoEntitySpecification(criterios);

        final var result = repository.findAll(finalSpecification, page);

        if(result.hasContent()){
            paginaRetorno.setTransacoes(toList(result.toList()));
            paginaRetorno.setPaginaAnterior(result.hasPrevious() ? pagina - 1 : null);
            paginaRetorno.setProximaPagina(result.hasNext() ? pagina + 1 : null);
            paginaRetorno.setTotalPaginas(result.getTotalPages());
            paginaRetorno.setTotalSomaDeItens(somaTotal(paginaRetorno.getTransacoes()));
        }

        return paginaRetorno;

    }

    public Double somaTotal(List<? extends Transacao> lista){

        Double somaTotalDaLista = 0d;
        if(lista!=null) {
            for (Transacao transacao : lista) {
                somaTotalDaLista += transacao.getValor();
            }
        }
        BigDecimal valorFinal = new BigDecimal(somaTotalDaLista);

        return valorFinal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private Specification<TransacaoEntity> getTransacaoEntitySpecification(List<SearchCriteria> criterios) {

        if (criterios.size()<=0) return null;

        Specification<TransacaoEntity> finalSpecification = Specification.where(new TransacaoSpecification(criterios.get(0)));
        criterios.remove(0);

        for (var sc: criterios) {
            finalSpecification = finalSpecification.and(new TransacaoSpecification(sc));
        }
        return finalSpecification;
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

    @NotNull
    private List<SearchCriteria> getSearchCriteriaAno(Integer ano) {
        return getSearchCriteriaMesEAno(0, ano);
    }

    @NotNull
    private List<SearchCriteria> getSearchCriteriaMesEAno(Integer mes, Integer ano) {

        final LocalDate dataInicio;
        final LocalDate dataFim;
        List<SearchCriteria> criteriaList = new ArrayList<>();

        if(ano !=null && ano>0) {
            if (mes!=null && mes>0) {
                dataInicio = periodoMes.getDataInicioMes(mes, ano);
                dataFim = periodoMes.getDataFimMes(mes, ano);
            } else {
                dataInicio = LocalDate.of(ano, 1, 1);
                dataFim = LocalDate.of(ano, 12, 31);
            }
            criteriaList.add(new SearchCriteria("dataCompetencia", QueryOperator.GREATER_OR_EQUAL_THAN,dataInicio.format(dbDateFormat)));
            criteriaList.add(new SearchCriteria("dataCompetencia",QueryOperator.LESS_OR_EQUAL_THAN,dataFim.format(dbDateFormat)));
        }

        return criteriaList;
    }

    @NotNull
    private List<SearchCriteria> getSearchCriteriaDoPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        List<SearchCriteria> criteriaList = new ArrayList<>();
        if(dataInicio!=null && dataFim!=null) {
            criteriaList.add(new SearchCriteria("dataCompetencia", QueryOperator.GREATER_OR_EQUAL_THAN, dataInicio.format(dbDateFormat)));
            criteriaList.add(new SearchCriteria("dataCompetencia", QueryOperator.LESS_OR_EQUAL_THAN, dataFim.format(dbDateFormat)));
        }
        return criteriaList;
    }

}
