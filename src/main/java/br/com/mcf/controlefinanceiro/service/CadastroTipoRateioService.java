package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.entity.TipoRateioEntity;
import br.com.mcf.controlefinanceiro.exceptions.TipoRateioNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.TipoRateio;
import br.com.mcf.controlefinanceiro.repository.TipoRateioRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CadastroTipoRateioService {

    private final TipoRateioRepository repository;

    public CadastroTipoRateioService(TipoRateioRepository repository) {
        this.repository = repository;
    }

    public void inserir(String nomeDominio) {
        TipoRateio tipoRateio = new TipoRateio(nomeDominio);

        try {
            repository.save(new TipoRateioEntity(tipoRateio));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void apagar(Integer id) throws TipoRateioNaoEncontradaException {
        if(id>0){
            try{
                var tipoRateio = consultar(id);
                if(tipoRateio.isPresent()){
                    tipoRateio.get().setAtivo(false);
                    alterar(tipoRateio.get());
                }

            }catch (EmptyResultDataAccessException e){
                throw new TipoRateioNaoEncontradaException(ConstantMessages.TIPO_RATEIO_NAO_ENCONTRADO);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public Optional<TipoRateio> consultar(Integer id) {
        TipoRateio tipoRateioEncontrada = null;

        try {
            final Optional<TipoRateioEntity> entity = repository.findById(id.longValue());
            if(entity.isPresent()){
                tipoRateioEncontrada = entity.get().toObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(tipoRateioEncontrada);
    }


    public Optional<TipoRateio> alterar(TipoRateio dominio) throws TipoRateioNaoEncontradaException {

        TipoRateio tipoRateioAlterada;
        Optional<TipoRateioEntity> record = Optional.empty();

        try{
            record = repository.findByIdAndAtivo(dominio.getId(),true);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(record.isPresent()){
          var recordEntity = record.get();
          recordEntity.setNomeTipoRateio(dominio.getNome());
          recordEntity.setDataCriacao(LocalDate.now());
          recordEntity.setAtivo(dominio.isAtivo());
          tipoRateioAlterada = repository.saveAndFlush(recordEntity).toObject();
        }else{
            throw new TipoRateioNaoEncontradaException(ConstantMessages.TIPO_RATEIO_NAO_ENCONTRADO);
        }

        return Optional.ofNullable(tipoRateioAlterada);
    }

    public List<TipoRateio> consultarTudo() {
        List<TipoRateioEntity> list = repository.findAllByAtivo(true);
        return TipoRateioEntity.toList(list);
    }
}
