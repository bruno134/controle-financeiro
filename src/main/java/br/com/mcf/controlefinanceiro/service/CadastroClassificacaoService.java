package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.entity.ClassificacaoEntity;
import br.com.mcf.controlefinanceiro.exceptions.ClassificacaoNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Classificacao;
import br.com.mcf.controlefinanceiro.repository.ClassificacaoRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CadastroClassificacaoService {

    private ClassificacaoRepository repository;

    //TODO criar testes automatizados

    public CadastroClassificacaoService(ClassificacaoRepository repository){
        this.repository = repository;
    }

    public void cadastrarClassificao(String nome){

        Classificacao classificacao = new Classificacao(nome);

        try {
            repository.save(new ClassificacaoEntity(classificacao));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Classificacao> consultarTodasClassificacoes(){
        return ClassificacaoEntity.toList(repository.findAllByAtivo(true));
    }

    public void apagarClassificacao(Integer id) throws ClassificacaoNaoEncontradaException {
        if(id>0) {
            try {

                var classificacao = consultaClassificacao(id);

                if(classificacao.isPresent()){
                    classificacao.get().setAtivo(false);
                    alterarClassificacao(classificacao.get());
                }
            } catch (EmptyResultDataAccessException e) {
                throw new ClassificacaoNaoEncontradaException(ConstantMessages.CLASSIFICACAO_NAO_ENCONTRADA);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //TODO deveria ter um ELSE?
    }

    public Optional<Classificacao> alterarClassificacao(Classificacao classificacao) throws ClassificacaoNaoEncontradaException {

        Classificacao classificacaoAlterada = null;
        try {
            var record = repository.findById(classificacao.getId());
            if (record.isPresent()){

                var classificacaoEntity = record.get();

                //Incluindo novo nome da classificação
                classificacaoEntity.setNomeClassificacao(classificacao.getNome());
                //Alterando a data para a nova data de alteração
                classificacaoEntity.setDataCriacao(LocalDate.now());
                classificacaoEntity.setAtivo(classificacao.isAtivo());

                classificacaoAlterada = repository.saveAndFlush(classificacaoEntity).toObject();
            }else{
                throw new ClassificacaoNaoEncontradaException(ConstantMessages.CLASSIFICACAO_NAO_ENCONTRADA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(classificacaoAlterada);
    }

    private Optional<Classificacao> consultaClassificacao(Integer id){
        Classificacao classificacaoEncontrada = null;

        try {
            final Optional<ClassificacaoEntity> entity = repository.findById(id.longValue());
            if(entity.isPresent()){
                classificacaoEncontrada = entity.get().toObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(classificacaoEncontrada);
    }
}
