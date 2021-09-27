package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.entity.DespesaEntity;
import br.com.mcf.controlefinanceiro.exceptions.DespesaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.repository.DespesaRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import com.sun.istack.NotNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CadastroDespesaService {

    private DespesaRepository repository;

    public CadastroDespesaService(DespesaRepository despesaRepository){
        this.repository = despesaRepository;
    }

    public void insere(LocalDate dataDespesa,
                       @NotNull BigDecimal valorDespesa,
                       @NotNull String descricaoDespesa,
                       String classificacaoDespesa,
                       String origemDespesa,
                       String tipoDespesa) {

        Despesa despesa = new Despesa(dataDespesa==null? LocalDate.now():dataDespesa,
                                      valorDespesa,
                                      descricaoDespesa,
                                      classificacaoDespesa,
                                      origemDespesa,
                                      tipoDespesa);
            try {
                repository.save(new DespesaEntity(despesa));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void insere(Despesa despesa) {
        insere(despesa.getData(),
                despesa.getValor(),
                despesa.getDescricao(),
                despesa.getClassificacao(),
                despesa.getOrigem(),
                despesa.getTipo());
    }

    public List<Despesa> buscarTodasDespesas(){
        return DespesaEntity.toList(repository.findAll());
    }

    public Optional<Despesa> buscaDespesaPorID(Integer idDespesa){
        Despesa despesaEncontrada = null;

        try {
            final Optional<DespesaEntity> despesaEntity = repository.findById(idDespesa.longValue());
            if(despesaEntity.isPresent()){
                despesaEncontrada = despesaEntity.get().toObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(despesaEncontrada);
    }

    public void apagarDespesa(Integer id) throws DespesaNaoEncontradaException {
        if(id>0) {
            try {
                repository.deleteById(id.longValue());
            } catch (EmptyResultDataAccessException e) {
                throw new DespesaNaoEncontradaException(ConstantMessages.DESPESA_NAO_ENCONTRADA);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //TODO deveria ter um ELSE?
    }

    public void apagarTodasDespesas(){
        try {
            repository.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<Despesa> alterarDespesa(Despesa despesa) throws DespesaNaoEncontradaException {

        Despesa despesaSalva;
        Optional<DespesaEntity> despesaEncontrada = Optional.empty();

        try {
            despesaEncontrada = repository.findById(despesa.getId().longValue());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (despesaEncontrada.isPresent()){

            //setando os atributos
            despesaEncontrada.get().setValor(despesa.getValor());
            despesaEncontrada.get().setData(despesa.getData());
            despesaEncontrada.get().setDescricao(despesa.getDescricao());
            despesaEncontrada.get().setClassificacao(despesa.getClassificacao());
            despesaEncontrada.get().setOrigem(despesa.getOrigem());
            despesaEncontrada.get().setTipo(despesa.getTipo());

            despesaSalva = repository.saveAndFlush(despesaEncontrada.get()).toObject();

        }else{
            throw new DespesaNaoEncontradaException(ConstantMessages.DESPESA_NAO_ENCONTRADA);
        }

        return Optional.ofNullable(despesaSalva);
    }

    private List<Despesa> buscaDespesaPorMesEAno(int mes, int ano){

        final LocalDate dataInicial = LocalDate.of(ano,mes,1);
        final LocalDate dataFinal = LocalDate.of(ano,mes,dataInicial.lengthOfMonth());

        List<Despesa> listaResultado = new ArrayList<>();

        try {
            listaResultado = DespesaEntity.toList(
                    repository.findAllByDataBetweenOrderByDataAsc(dataInicial,dataFinal)
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaResultado;

    }

    public List<Despesa> buscaDespesaPorParametros(int mes, int ano){
        if(mes>0 && ano>0){
            return buscaDespesaPorMesEAno(mes,ano);
        }else
        {
            return buscarTodasDespesas();
        }
    }

    public BigDecimal retornaTotalDespesa(List<Despesa> listaDespesa){
        BigDecimal totalDespesa = BigDecimal.ZERO;
        for (Despesa despesa : listaDespesa)
            totalDespesa = totalDespesa.add(despesa.getValor());

        return totalDespesa;
    }

}
