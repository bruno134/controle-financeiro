package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.entity.TransacaoEntity;
import br.com.mcf.controlefinanceiro.exceptions.DespesaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.exceptions.TransacaoNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.TipoTransacao;
import br.com.mcf.controlefinanceiro.model.Transacao;
import br.com.mcf.controlefinanceiro.repository.DespesaRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.springframework.dao.EmptyResultDataAccessException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransacaoService<T extends Transacao> {

    private final DespesaRepository repository;
    private final TipoTransacao despesaEnum = TipoTransacao.DESPESA;
    private Class<T> clazz;

    public TransacaoService(DespesaRepository despesaRepository, Class<T> clazz){
        this.repository = despesaRepository;
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


    public <T extends Transacao> Optional<T> alterar(T t) throws DespesaNaoEncontradaException {

        T transacaoSalva = null;
        Optional<TransacaoEntity> transacaoEncontrada = Optional.empty();

        try{
          transacaoEncontrada = repository.findByIdAndTipoTransacao(t.getId().longValue(), t.getTipoTransacao().getDescricao());

          if(transacaoEncontrada.isPresent()){
              transacaoEncontrada.get().setValor(t.getValor());
              transacaoEncontrada.get().setData(t.getData());
              transacaoEncontrada.get().setDescricao(t.getDescricao());
              transacaoEncontrada.get().setCategoria(t.getCategoria());
              transacaoEncontrada.get().setTipoRateio(t.getTipoRateio());
              transacaoEncontrada.get().setInstrumento(t.getInstrumento());

              transacaoSalva = (T) this.clazz.getDeclaredConstructor(TransacaoEntity.class)
                                     .newInstance(repository.saveAndFlush(transacaoEncontrada.get()));
          }else{
              throw new DespesaNaoEncontradaException(ConstantMessages.DESPESA_NAO_ENCONTRADA);
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
            final Optional<TransacaoEntity> despesaEntity = repository.findById(id);

            if(despesaEntity.isPresent()){
                transacaoEncontrada = (T) this.clazz.getDeclaredConstructor(TransacaoEntity.class)
                                            .newInstance(despesaEntity.get());
            } else
                throw new TransacaoNaoEncontradaException(ConstantMessages.TRANSACAO_NAO_ENCONTRADA);
        }catch (Exception e){
            e.printStackTrace();
        }

        return Optional.ofNullable(transacaoEncontrada);
    }

    public <T extends Transacao> List<T> buscarTodas(TipoTransacao tipoTransacao){
        return toList(repository.findAllByTipoTransacaoOrderByDataAsc(tipoTransacao.getDescricao()));
    }

    public <T extends Transacao> List<T>  buscarPorParametros(int mes, int ano, TipoTransacao tipoTransacao) {
        if(mes>0 && ano>0){
            return buscaPorMesEAno(mes,ano, tipoTransacao);
        }else
        {
            return buscarTodas(tipoTransacao);
        }
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


    private <T extends Transacao> List<T> buscaPorMesEAno(int mes, int ano, TipoTransacao tipoTransacao){

        final LocalDate dataInicial = LocalDate.of(ano,mes,1);
        final LocalDate dataFinal = LocalDate.of(ano,mes,dataInicial.lengthOfMonth());

        List<T> list = new ArrayList<>();

        try{
           list = toList(
                   repository.findAllByDataBetweenAndTipoTransacaoOrderByDataAsc(dataInicial,dataFinal,tipoTransacao.getDescricao())
           );
        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

}
