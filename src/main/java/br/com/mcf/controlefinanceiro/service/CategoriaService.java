package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.entity.CategoriaEntity;
import br.com.mcf.controlefinanceiro.exceptions.CategoriaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.Categoria;
import br.com.mcf.controlefinanceiro.repository.CategoriaRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    //TODO criar testes automatizados

    public CategoriaService(CategoriaRepository repository){
        this.repository = repository;
    }

    public void cadastrarClassificao(String nome){

        Categoria categoria = new Categoria(nome);

        try {
            repository.save(new CategoriaEntity(categoria));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Categoria> consultarTodasClassificacoes(){
        return CategoriaEntity.toList(repository.findAllByAtivo(true));
    }

    public void apagarCategoria(Integer id) throws CategoriaNaoEncontradaException {
        if(id>0) {
            try {

                var categoria = consultaCategoria(id);

                if(categoria.isPresent()){
                    categoria.get().setAtivo(false);
                    alterarCategoria(categoria.get());
                }
            } catch (EmptyResultDataAccessException e) {
                throw new CategoriaNaoEncontradaException(ConstantMessages.CATEGORIA_NAO_ENCONTRADA);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //TODO deveria ter um ELSE?
    }

    public Optional<Categoria> alterarCategoria(Categoria categoria) throws CategoriaNaoEncontradaException {

        Categoria categoriaAlterada;
        Optional<CategoriaEntity> record = Optional.empty();
        try {
            record = repository.findByIdAndAtivo(categoria.getId(),true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (record.isPresent()){
            var categoriaEntity = record.get();
            categoriaEntity.setNomeCategoria(categoria.getNome());
            //Alterando a data para a nova data de alteração
            categoriaEntity.setDataCriacao(LocalDate.now());
            categoriaEntity.setAtivo(categoria.isAtivo());
            categoriaAlterada = repository.saveAndFlush(categoriaEntity).toObject();
        }else{
            throw new CategoriaNaoEncontradaException(ConstantMessages.CATEGORIA_NAO_ENCONTRADA);
        }

        return Optional.ofNullable(categoriaAlterada);
    }

    private Optional<Categoria> consultaCategoria(Integer id){
        Categoria categoriaEncontrada = null;

        try {
            final Optional<CategoriaEntity> entity = repository.findById(id.longValue());
            if(entity.isPresent()){
                categoriaEncontrada = entity.get().toObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(categoriaEncontrada);
    }
}
