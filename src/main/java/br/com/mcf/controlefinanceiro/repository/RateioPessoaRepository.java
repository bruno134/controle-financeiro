package br.com.mcf.controlefinanceiro.repository;

import br.com.mcf.controlefinanceiro.entity.RateioPessoaEntity;
import br.com.mcf.controlefinanceiro.entity.embedded.RateioPessoaEmbeddedKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RateioPessoaRepository extends JpaRepository<RateioPessoaEntity, RateioPessoaEmbeddedKey> {

    @Override
    Optional<RateioPessoaEntity> findById(RateioPessoaEmbeddedKey rateioPessoaEmbeddedKey);

    List<RateioPessoaEntity> findByIdAnoCompetenciaAndIdMesCompetencia(Integer anoCompetencia, Integer mesCompetencia);

}