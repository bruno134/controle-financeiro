package br.com.mcf.controlefinanceiro.service.dominio;

import br.com.mcf.controlefinanceiro.model.entity.InstrumentoEntity;
import br.com.mcf.controlefinanceiro.model.exceptions.InstrumentoNaoEncontradoException;
import br.com.mcf.controlefinanceiro.model.dominio.Instrumento;
import br.com.mcf.controlefinanceiro.model.repository.dominio.InstrumentoRepository;
import br.com.mcf.controlefinanceiro.util.ConstantMessages;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CadastroInstrumentoService {

    private final InstrumentoRepository repository;

    public CadastroInstrumentoService(InstrumentoRepository repository) {

        this.repository = repository;

    }

    public void inserir(String nomeDominio) {
        Instrumento instrumento = new Instrumento(nomeDominio);

        try {
            repository.save(new InstrumentoEntity(instrumento));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void apagar(Integer id) throws InstrumentoNaoEncontradoException {
        if(id>0){
            try{
                var instrumento = consultar(id);
                if(instrumento.isPresent()){
                    instrumento.get().setAtivo(false);
                    alterar(instrumento.get());
                }else{
                    throw new InstrumentoNaoEncontradoException(ConstantMessages.INSTRUMENTO_NAO_ENCONTRADO);
                }

            }catch (EmptyResultDataAccessException e){
                throw new InstrumentoNaoEncontradoException(ConstantMessages.INSTRUMENTO_NAO_ENCONTRADO);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public Optional<Instrumento> consultar(Integer id) {
        Instrumento instrumentoEncontrado = null;

        try {
            final Optional<InstrumentoEntity> entity = repository.findById((long)id);
            if(entity.isPresent()){
                instrumentoEncontrado = entity.get().toObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(instrumentoEncontrado);
    }


    public Optional<Instrumento> alterar(Instrumento dominio) throws InstrumentoNaoEncontradoException {

        Instrumento instrumentoAlterado;
        Optional<InstrumentoEntity> record = Optional.empty();

        try{
            record = repository.findByIdAndAtivo(dominio.getId(),true);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(record.isPresent()){
          var recordEntity = record.get();
          recordEntity.setNomeInstrumento(dominio.getNome());
          recordEntity.setDataCriacao(LocalDate.now());
          recordEntity.setAtivo(dominio.isAtivo());
          instrumentoAlterado = repository.saveAndFlush(recordEntity).toObject();
        }else{
            throw new InstrumentoNaoEncontradoException(ConstantMessages.INSTRUMENTO_NAO_ENCONTRADO);
        }

        return Optional.ofNullable(instrumentoAlterado);
    }

    public List<Instrumento> consultarTudo() {
        List<InstrumentoEntity> list = repository.findAllByAtivo(true);
        return InstrumentoEntity.toList(list);
    }
}
