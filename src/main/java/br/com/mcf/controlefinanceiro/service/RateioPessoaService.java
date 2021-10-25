package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.entity.RateioPessoaEntity;
import br.com.mcf.controlefinanceiro.entity.embedded.RateioPessoaEmbeddedKey;
import br.com.mcf.controlefinanceiro.exceptions.RateioPessoaExistenteException;
import br.com.mcf.controlefinanceiro.exceptions.RateioPessoaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.RateioPessoa;
import br.com.mcf.controlefinanceiro.repository.RateioPessoaRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RateioPessoaService {

    RateioPessoaRepository repository;

    public RateioPessoaService(RateioPessoaRepository repository){ this.repository = repository; }

    public RateioPessoa inserir(RateioPessoa rateioPessoa) throws RateioPessoaExistenteException, RateioPessoaNaoEncontradaException {

        Optional<RateioPessoa> rateioPessoaEncontrada;
        rateioPessoaEncontrada = buscar(rateioPessoa);

        if(rateioPessoaEncontrada.isEmpty()){
            try {
                return new RateioPessoa(repository.save(rateioPessoa.toEntity()));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            throw new RateioPessoaExistenteException(ConstantMessages.RATEIO_PESSOA_EXISTENTE);
        }
        return null;
    }

    public Optional<RateioPessoa> alterar(RateioPessoa rateioPessoa) throws RateioPessoaNaoEncontradaException {

        try {
            final var rateioPessoaEncontrada = consultar(rateioPessoa);
            if(rateioPessoaEncontrada.isPresent()){
                return Optional.of(new RateioPessoa(repository.save(rateioPessoa.toEntity())));
            }else{
                throw new RateioPessoaNaoEncontradaException(ConstantMessages.RATEIO_NAO_ENCONTRADO);
            }
        } catch (RateioPessoaNaoEncontradaException e) {
            throw e;
        }
    }

    public Optional<RateioPessoa> consultar(RateioPessoa rateioPessoa) throws RateioPessoaNaoEncontradaException {
        try{
            final var rateioPessoaEncontrada = buscar(rateioPessoa);

            if(rateioPessoaEncontrada.isPresent()){
                return rateioPessoaEncontrada;
            }else {
                throw new RateioPessoaNaoEncontradaException(ConstantMessages.RATEIO_NAO_ENCONTRADO);
            }
        }catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<RateioPessoa> consultarListaRateio(RateioPessoa rateioPessoa) {

        List<RateioPessoa> rateioPessoaList = new ArrayList<>();

        try{
            rateioPessoaList = buscarLista(rateioPessoa);

        }catch (Exception e){
            e.printStackTrace();
        }

        return rateioPessoaList;
    }

    public void apagar(RateioPessoa rateioPessoa) throws RateioPessoaNaoEncontradaException {
        try {
            final var rateioPessoaEncontrada = consultar(rateioPessoa);
            if(rateioPessoaEncontrada.isPresent()){
                repository.delete(rateioPessoa.toEntity());
            }else{
                throw new RateioPessoaNaoEncontradaException(ConstantMessages.RATEIO_NAO_ENCONTRADO);
            }
        } catch (RateioPessoaNaoEncontradaException e) {
            e.printStackTrace();
        }
    }

    private Optional<RateioPessoa> buscar(RateioPessoa rateioPessoa){
        Optional<RateioPessoaEntity> rateioPessoaEncontrada = Optional.empty();
        try{
            rateioPessoaEncontrada = repository.findById(new RateioPessoaEmbeddedKey(rateioPessoa.getMesCompetenciaRateio(), rateioPessoa.getAnoCompetenciaRateio(), rateioPessoa.getPessoaRateio()));
            return rateioPessoaEncontrada.map(RateioPessoa::new);

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    private List<RateioPessoa> buscarLista(RateioPessoa rateioPessoa){
        List<RateioPessoaEntity> entityList;

        try{
            entityList = repository.findByIdAnoCompetenciaAndIdMesCompetencia(rateioPessoa.getAnoCompetenciaRateio(),rateioPessoa.getMesCompetenciaRateio());
            return RateioPessoa.toList(entityList);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

}
