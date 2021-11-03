package br.com.mcf.controlefinanceiro.service.transacao;

import br.com.mcf.controlefinanceiro.entity.TransacaoEntity;
import br.com.mcf.controlefinanceiro.exceptions.TransacaoNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.ListaTransacao;
import br.com.mcf.controlefinanceiro.model.TipoTransacao;
import br.com.mcf.controlefinanceiro.model.Transacao;
import br.com.mcf.controlefinanceiro.repository.TransacaoRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransacaoService<T extends Transacao> {

    private final TransacaoRepository repository;
    private Class<T> clazz;

    public TransacaoService(TransacaoRepository transacaoRepository, Class<T> clazz){
        this.repository = transacaoRepository;
        this.clazz = clazz;
    }

    public <T extends Transacao> T inserir(T t) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        T transacao = null;
        try{
            final var save = repository.save(new TransacaoEntity(t));
            final Constructor<T> declaredConstructor = (Constructor<T>) clazz.getDeclaredConstructor(TransacaoEntity.class);
            final Transacao instance = declaredConstructor.newInstance(save);
            transacao = (T) instance;

        }catch (Exception e){
            e.printStackTrace();
        }
        return transacao;

    }


    public <T extends Transacao> Optional<T> alterar(T t) throws TransacaoNaoEncontradaException {

        T transacaoSalva = null;
        Optional<TransacaoEntity> transacaoEncontrada;

        try{
          transacaoEncontrada = repository.findByIdAndTipoTransacao(t.getId().longValue(), t.getTipoTransacao().getDescricao());

          if(transacaoEncontrada.isPresent()){
              transacaoEncontrada.get().setValor(t.getValor());
              transacaoEncontrada.get().setData(t.getData());
              transacaoEncontrada.get().setDescricao(t.getDescricao());
              transacaoEncontrada.get().setCategoria(t.getCategoria());
              transacaoEncontrada.get().setTipoRateio(t.getTipoRateio());
              transacaoEncontrada.get().setInstrumento(t.getInstrumento());
              transacaoEncontrada.get().setDataCompetencia(t.getDataCompetencia());

              transacaoSalva = (T) this.clazz.getDeclaredConstructor(TransacaoEntity.class)
                                     .newInstance(repository.saveAndFlush(transacaoEncontrada.get()));
          }else{
              throw new TransacaoNaoEncontradaException(ConstantMessages.TRANSACAO_NAO_ENCONTRADA);
          }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(transacaoSalva);
    }


    public <T extends Transacao> void apagar(T t) throws TransacaoNaoEncontradaException {
        if(t!=null && t.getId()>0){
            try {
                repository.deleteByIdAndTipoTransacao(t.getId().longValue(),t.getTipoTransacao().getDescricao());
            }catch (EmptyResultDataAccessException e) {
                throw new TransacaoNaoEncontradaException(ConstantMessages.TRANSACAO_NAO_ENCONTRADA);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public <T extends Transacao> Optional<T> buscarPorID(Long id) throws TransacaoNaoEncontradaException {

        T transacaoEncontrada = null;

        try {
            final Optional<TransacaoEntity> transacaoEntity = repository.findById(id);

            if(transacaoEntity.isPresent()){
                transacaoEncontrada = (T) this.clazz.getDeclaredConstructor(TransacaoEntity.class)
                                            .newInstance(transacaoEntity.get());
            } else
                throw new TransacaoNaoEncontradaException(ConstantMessages.TRANSACAO_NAO_ENCONTRADA);
        }catch (Exception e){
            e.printStackTrace();
        }

        return Optional.ofNullable(transacaoEncontrada);
    }

    public <T extends Transacao> List<T> buscarTodas(TipoTransacao tipoTransacao){
        return toList(repository.findAllByTipoTransacaoOrderByDataCompetenciaAsc(tipoTransacao.getDescricao()));
    }

    public <T extends Transacao> ListaTransacao<T> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim, TipoTransacao tipoTransacao, Integer pagina) {
       return buscaPorRangeDeDatas(dataInicio,dataFim, tipoTransacao, pagina);
    }

    public <T extends Transacao> List<T> buscarPorPeriodo(TipoTransacao tipoTransacao) {
       return buscarTodas(tipoTransacao);

    }


    private <T extends Transacao> List<T> toList(List<TransacaoEntity> entityList){
        List<T> list = new ArrayList<>();

        entityList.forEach(item-> {
            try {
                list.add((T) this.clazz.getDeclaredConstructor(TransacaoEntity.class).newInstance(item));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
        return list;
    }


    private <T extends Transacao> ListaTransacao<T> buscaPorRangeDeDatas(LocalDate dataInicio, LocalDate dataFim, TipoTransacao tipoTransacao, Integer paginaInformada){


        ListaTransacao<T> lista = new ListaTransacao<>();
        Pageable page;


        try{
            if(paginaInformada>0) {
                page = PageRequest.of(paginaInformada-1, 10, Sort.by("id"));
                final var despesaSlice = repository.findAllByDataCompetenciaBetweenAndTipoTransacaoOrderByDataCompetenciaAsc(dataInicio, dataFim, tipoTransacao.getDescricao(), page);
                if(despesaSlice.hasContent())
                    lista.setTransacoes(toList(despesaSlice.toList()));
                    lista.setPaginaAnterior(despesaSlice.hasPrevious()?paginaInformada-1:null);
                    lista.setProximaPAgina(despesaSlice.hasNext()?paginaInformada+1:null);
            }
            else {
                lista.setTransacoes(toList(repository.findAllByDataCompetenciaBetweenAndTipoTransacaoOrderByDataCompetenciaAsc(dataInicio, dataFim, tipoTransacao.getDescricao())));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return lista;
    }
}
