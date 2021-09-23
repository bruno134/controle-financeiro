package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.entity.TipoEntity;
import br.com.mcf.controlefinanceiro.exceptions.TipoNaoEncontradoException;
import br.com.mcf.controlefinanceiro.model.Tipo;
import br.com.mcf.controlefinanceiro.repository.TipoRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CadastroTipoService {

    private TipoRepository repository;

    public CadastroTipoService(TipoRepository repository) {

        this.repository = repository;

    }

    public void inserir(String nomeDominio) {
        Tipo tipo = new Tipo(nomeDominio);

        try {
            repository.save(new TipoEntity(tipo));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void apagar(Integer id) throws TipoNaoEncontradoException {
        if(id>0){
            try{
                var tipo = consultar(id);
                if(tipo.isPresent()){
                    tipo.get().setAtivo(false);
                    alterar(tipo.get());
                }else{
                    throw new TipoNaoEncontradoException(ConstantMessages.TIPO_NAO_ENCONTRADO);
                }

            }catch (EmptyResultDataAccessException e){
                throw new TipoNaoEncontradoException(ConstantMessages.TIPO_NAO_ENCONTRADO);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public Optional<Tipo> consultar(Integer id) {
        Tipo tipoEncontrado = null;

        try {
            final Optional<TipoEntity> entity = repository.findById((long)id);
            if(entity.isPresent()){
                tipoEncontrado = entity.get().toObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(tipoEncontrado);
    }


    public Optional<Tipo> alterar(Tipo dominio) throws TipoNaoEncontradoException {

        Tipo tipoAlterado;
        Optional<TipoEntity> record = Optional.empty();

        try{
            record = repository.findByIdAndAtivo(dominio.getId(),true);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(record.isPresent()){
          var recordEntity = record.get();
          recordEntity.setNomeTipo(dominio.getNome());
          recordEntity.setDataCriacao(LocalDate.now());
          recordEntity.setAtivo(dominio.isAtivo());
          tipoAlterado = repository.saveAndFlush(recordEntity).toObject();
        }else{
            throw new TipoNaoEncontradoException(ConstantMessages.TIPO_NAO_ENCONTRADO);
        }

        return Optional.ofNullable(tipoAlterado);
    }

    public List<Tipo> consultarTudo() {
        List<TipoEntity> list = repository.findAllByAtivo(true);
        return TipoEntity.toList(list);
    }
}
