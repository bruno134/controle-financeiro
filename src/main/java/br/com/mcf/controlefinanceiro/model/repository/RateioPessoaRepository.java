package br.com.mcf.controlefinanceiro.model.repository;

import br.com.mcf.controlefinanceiro.model.entity.RateioPessoaEntity;
import br.com.mcf.controlefinanceiro.model.entity.embedded.RateioPessoaEmbeddedKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RateioPessoaRepository extends JpaRepository<RateioPessoaEntity, RateioPessoaEmbeddedKey> {

    @Override
    Optional<RateioPessoaEntity> findById(RateioPessoaEmbeddedKey rateioPessoaEmbeddedKey);

    List<RateioPessoaEntity> findByIdAnoCompetenciaAndIdMesCompetencia(Integer anoCompetencia, Integer mesCompetencia);

}