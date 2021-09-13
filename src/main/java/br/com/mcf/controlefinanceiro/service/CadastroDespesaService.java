package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.exceptions.DespesaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Despesa;
import br.com.mcf.controlefinanceiro.repository.DespesaRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CadastroDespesaService {

    private List<Despesa> despesas;
    private DespesaRepository repository;

    public CadastroDespesaService(DespesaRepository despesaRepository){
        this.despesas   = new ArrayList<>();
        this.repository = despesaRepository;
    }

    public void cadastrarDespesa(LocalDate dataDespesa,
                                 @NotNull BigDecimal valorDespesa,
                                 @NotNull String descricaoDespesa,
                                 String classificacaoDespesa,
                                 String origemDespesa,
                                 String tipoDespesa){

        Despesa despesa = new Despesa(dataDespesa==null? LocalDate.now():dataDespesa,
                                      valorDespesa,
                                      descricaoDespesa,
                                      classificacaoDespesa,
                                      origemDespesa,
                                      tipoDespesa);

        try {
            repository.save(despesa.toEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        despesas.add(despesa);
    }

    public void cadastrarDespesa(Despesa despesa){
        cadastrarDespesa(despesa.getData(),
                despesa.getValor(),
                despesa.getDescricao(),
                despesa.getClassificacao(),
                despesa.getOrigem(),
                despesa.getTipo());
    }

    public List<Despesa> consultasTodasDespesas(){
        return Despesa.toList(repository.findAll());
    }

    public Optional<Despesa> consultaDespesa(Integer idDespesa){

        final List<Despesa> lista = despesas.stream()
                .filter(despesa -> despesa.getId().equals(idDespesa))
                .collect(Collectors.toList());
        if (lista.size()>0)
            return Optional.of(lista.get(0));

        return Optional.ofNullable(null);
    }

    public void apagarDespesa(Integer id) throws DespesaNaoEncontradaException {
        final Optional<Despesa> despesa = this.consultaDespesa(id);
        if(despesa.isPresent())
            this.despesas.remove(despesa.get());
        else{
            throw new DespesaNaoEncontradaException(ConstantMessages.NAO_ENCONTRADO_MSG);
        }
    }

    public void apagarTodasDespesas(){
        this.despesas.clear();
    }

    private Integer proximoId(){
        return this.despesas.size() + 1;
    }


    public Despesa alterarDespesa(Despesa despesa) throws DespesaNaoEncontradaException {
        apagarDespesa(despesa.getId());
        this.despesas.add(despesa);
        return despesa;
    }
}
