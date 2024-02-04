package br.com.mcf.controlefinanceiro.service.dominio;

import br.com.mcf.controlefinanceiro.model.entity.CategoriaEntity;
import br.com.mcf.controlefinanceiro.model.exceptions.CategoriaNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.dominio.Categoria;
import br.com.mcf.controlefinanceiro.model.dominio.TipoTransacao;
import br.com.mcf.controlefinanceiro.model.repository.dominio.CategoriaRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

   //create Log of L4J
   private static final Logger LOG = LoggerFactory.getLogger(CategoriaService.class);
    private final CategoriaRepository repository;

    //TODO criar testes automatizados

    public CategoriaService(CategoriaRepository repository){
        this.repository = repository;
    }

    public void cadastrarClassificao(String nome){

        Categoria categoria = new Categoria(nome);

        if(categoria.getTipoTransacao()==null) {
            LOG.warn("Type of category not informed, setting DESPESA as default");
            categoria.setTipoTransacao(TipoTransacao.DESPESA);
        }


        try {
            repository.save(new CategoriaEntity(categoria));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cadastrarClassificao(String nome, TipoTransacao tipoTransacao){

        Categoria categoria = new Categoria(nome,tipoTransacao);

        try {
            repository.save(new CategoriaEntity(categoria));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Categoria> consultarTodasClassificacoesAtivas(){
        return CategoriaEntity.toList(repository.findAllByAtivo(true));
    }

    public List<Categoria> consultarTodasClassificacoes(){
        return CategoriaEntity.toList(repository.findAll());
    }

    public List<Categoria> consultarTodasClassificacoesPorTipoTransacao(TipoTransacao tipoTransacao){
        return CategoriaEntity.toList(repository.findAllByAtivoAndTipoTransacao(true, tipoTransacao.getDescricao()));
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

    public Categoria consultarCategoriaPorNome(String categoryToFind) {
        return null;
    }
}
