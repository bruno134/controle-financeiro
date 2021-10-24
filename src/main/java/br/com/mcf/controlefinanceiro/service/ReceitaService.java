package br.com.mcf.controlefinanceiro.service;


import br.com.mcf.controlefinanceiro.exceptions.DespesaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.exceptions.TransacaoNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Receita;
import br.com.mcf.controlefinanceiro.model.TipoTransacao;
import br.com.mcf.controlefinanceiro.repository.DespesaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService{

    private final TransacaoService<Receita> service;
    private final DespesaRepository repository;

    public ReceitaService(DespesaRepository repository) {
        this.repository = repository;
        this.service = new TransacaoService<>(this.repository, Receita.class);
    }

    public Receita inserir(Receita receita){

        Receita receitaInserida = null;

        try {
          receitaInserida = service.inserir(receita);
        }catch (NoSuchMethodException | InvocationTargetException |
                InstantiationException | IllegalAccessException e){
            e.printStackTrace();
        }

        return receitaInserida;
    }

    @Transactional
    public Optional<Receita> alterar(Receita receita) throws DespesaNaoEncontradaException {
        return service.alterar(receita);
    }

    @Transactional
    public void apagar(Receita receita) throws TransacaoNaoEncontradaException {
        service.apagar(receita);
    }

    public Optional<Receita> buscarPorId(Long id) throws TransacaoNaoEncontradaException {

        Optional<Receita> receitaEncontrada = Optional.empty();

        try {
            receitaEncontrada = service.buscarPorID(id);
        } catch (TransacaoNaoEncontradaException e) {
            e.printStackTrace();
        }

        return receitaEncontrada;

    }

    public List<Receita> buscarPorParametros(int mes, int ano){
        List<Receita> receitaList = new ArrayList<>();

        try {
            receitaList = service.buscarPorParametros(mes,ano, TipoTransacao.RECEITA);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  receitaList;
    }

    public List<Receita> buscarTodas(){
        List<Receita> receitaList = new ArrayList<>();

        try {
            receitaList = service.buscarTodas(TipoTransacao.RECEITA);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  receitaList;
    }



}
