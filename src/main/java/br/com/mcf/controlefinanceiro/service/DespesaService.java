package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.exceptions.DespesaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.exceptions.TransacaoNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.model.TipoTransacao;
import br.com.mcf.controlefinanceiro.repository.TransacaoRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DespesaService{


    private final TransacaoService<Despesa> service;

    public DespesaService(TransacaoRepository repository) {
        service = new TransacaoService<>(repository, Despesa.class);
    }

    @Transactional
    public Despesa inserir(Despesa despesa){
       
        Despesa despesaInserida = null;
        
       try {
           despesaInserida = service.inserir(despesa);
       }catch (NoSuchMethodException | InvocationTargetException | 
               InstantiationException | IllegalAccessException e){
           e.printStackTrace();
       }

        return despesaInserida;
    }

    @Transactional
    public Optional<Despesa> alterar(Despesa despesa) throws DespesaNaoEncontradaException {
        try {
            return service.alterar(despesa);
        } catch (TransacaoNaoEncontradaException e) {
            e.printStackTrace();
            throw new DespesaNaoEncontradaException(ConstantMessages.DESPESA_NAO_ENCONTRADA);
        }
    }

    @Transactional
    public void apagar(Despesa despesa) throws DespesaNaoEncontradaException {
        try{
            service.apagar(despesa);
        }catch (TransacaoNaoEncontradaException e){
            throw new DespesaNaoEncontradaException(ConstantMessages.DESPESA_NAO_ENCONTRADA);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Optional<Despesa> buscarPorID(Long id) throws DespesaNaoEncontradaException {
        try {
            return service.buscarPorID(id);
        }catch (TransacaoNaoEncontradaException e){
            throw new DespesaNaoEncontradaException(ConstantMessages.DESPESA_NAO_ENCONTRADA);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  Optional.empty();
    }

    public List<Despesa> buscarTodas(){
        return service.buscarTodas(TipoTransacao.DESPESA);
    }

    public List<Despesa> inserirEmLista(List<Despesa> despesaList){

        List<Despesa> listaDespesaIncluida = new ArrayList<>();

        if(despesaList!=null)
            despesaList.forEach(d -> listaDespesaIncluida.add(inserir(d)));

        return listaDespesaIncluida;
    }

    public List<Despesa> buscarPorParametros(int mes, int ano){
        return service.buscarPorParametros(mes,ano, TipoTransacao.DESPESA);
    }

}
