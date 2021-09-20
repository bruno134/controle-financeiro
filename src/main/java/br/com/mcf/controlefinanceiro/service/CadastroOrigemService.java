package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.entity.OrigemEntity;
import br.com.mcf.controlefinanceiro.exceptions.OrigemNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Origem;
import br.com.mcf.controlefinanceiro.repository.OrigemRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CadastroOrigemService{

    private OrigemRepository repository;

    public CadastroOrigemService(OrigemRepository repository) {
        this.repository = repository;
    }

    public void inserir(String nomeDominio) {
        Origem origem = new Origem(nomeDominio);

        try {
            repository.save(new OrigemEntity(origem));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void apagar(Integer id) throws OrigemNaoEncontradaException {
        if(id>0){
            try{
                var origem = consultar(id);
                if(origem.isPresent()){
                    origem.get().setAtivo(false);
                    alterar(origem.get());
                }

            }catch (EmptyResultDataAccessException e){
                throw new OrigemNaoEncontradaException(ConstantMessages.ORIGEM_NAO_ENCONTRADA);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public Optional<Origem> consultar(Integer id) {
        Origem origemEncontrada = null;

        try {
            final Optional<OrigemEntity> entity = repository.findById(id.longValue());
            if(entity.isPresent()){
                origemEncontrada = entity.get().toObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(origemEncontrada);
    }


    public Optional<Origem> alterar(Origem dominio) throws OrigemNaoEncontradaException {

        Origem origemAlterada;
        Optional<OrigemEntity> record = Optional.empty();

        try{
            record = repository.findByIdAndAtivo(dominio.getId(),true);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(record.isPresent()){
          var recordEntity = record.get();
          recordEntity.setNomeOrigem(dominio.getNome());
          recordEntity.setDataCriacao(LocalDate.now());
          recordEntity.setAtivo(dominio.isAtivo());
          origemAlterada = repository.saveAndFlush(recordEntity).toObject();
        }else{
            throw new OrigemNaoEncontradaException(ConstantMessages.ORIGEM_NAO_ENCONTRADA);
        }

        return Optional.ofNullable(origemAlterada);
    }

    public List<Origem> consultarTudo() {
        List<OrigemEntity> list = repository.findAllByAtivo(true);
        return OrigemEntity.toList(list);
    }
}
