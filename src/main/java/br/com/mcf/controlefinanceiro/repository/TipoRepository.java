package br.com.mcf.controlefinanceiro.repository;

import br.com.mcf.controlefinanceiro.entity.OrigemEntity;
import br.com.mcf.controlefinanceiro.entity.TipoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoRepository extends JpaRepository<TipoEntity, Long> {
    List<TipoEntity> findAllByAtivo(boolean ativo);
    Optional<TipoEntity> findByIdAndAtivo(Long id, boolean ativo);
}