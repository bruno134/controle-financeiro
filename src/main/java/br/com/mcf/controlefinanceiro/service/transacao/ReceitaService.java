package br.com.mcf.controlefinanceiro.service.transacao;


import br.com.mcf.controlefinanceiro.model.exceptions.ReceitaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.exceptions.TransacaoNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.transacao.Despesa;
import br.com.mcf.controlefinanceiro.model.transacao.PaginaTransacao;
import br.com.mcf.controlefinanceiro.model.transacao.Receita;
import br.com.mcf.controlefinanceiro.model.dominio.TipoTransacao;
import br.com.mcf.controlefinanceiro.model.repository.TransacaoRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService{

    @Autowired
    private PeriodoMes periodoMes;
    private final TransacaoService<Receita> service;

    public ReceitaService(TransacaoRepository repository) {
        this.service = new TransacaoService<>(repository, Receita.class);
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
    public Optional<Receita> alterar(Receita receita) throws ReceitaNaoEncontradaException {
        try {
            return service.alterar(receita);
        } catch (TransacaoNaoEncontradaException e) {
            e.printStackTrace();
            throw new ReceitaNaoEncontradaException(ConstantMessages.RECEITA_NAO_ENCONTRADA);
        }
    }

    @Transactional
    public void apagar(Receita receita) throws ReceitaNaoEncontradaException {
        try {
            service.apagar(receita);
        } catch (TransacaoNaoEncontradaException e) {
            e.printStackTrace();
            throw new ReceitaNaoEncontradaException(ConstantMessages.RECEITA_NAO_ENCONTRADA);
        }
    }

    public Optional<Receita> buscarPorId(Long id) throws ReceitaNaoEncontradaException {

        Optional<Receita> receitaEncontrada = Optional.empty();

        try {
            receitaEncontrada = service.buscarPorID(id);
        } catch (TransacaoNaoEncontradaException e) {
            throw new ReceitaNaoEncontradaException(ConstantMessages.RECEITA_NAO_ENCONTRADA);

        }

        return receitaEncontrada;

    }

    public List<Despesa> buscarTodasPor(Integer ano){
        return service.buscarTodasPor(ano,TipoTransacao.RECEITA);
    }

    public PaginaTransacao buscarPorPeriodo(int mes, int ano, int pagina, Integer tamanhoPagina){
        PaginaTransacao receitaList = new PaginaTransacao();

        try {
            /**
             *  O dia inicial do mês é definido dentro da propriedade 'controle-financeiro.inicio-mes.dia' no application.properties
             *  Não defina a propriedade 'controle-financeiro.inicio-mes.dia', caso não deseje customizar o  periodo de competencia do mes.
             */

            final LocalDate dataInicial = periodoMes.getDataInicioMes(mes,ano);
            final LocalDate dataFinal = periodoMes.getDataFimMes(mes,ano);

            receitaList =  service.buscarPorPeriodo(dataInicial,dataFinal,TipoTransacao.RECEITA, pagina, tamanhoPagina);
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
