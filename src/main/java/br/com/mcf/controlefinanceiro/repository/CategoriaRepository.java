package br.com.mcf.controlefinanceiro.repository;

import br.com.mcf.controlefinanceiro.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    List<CategoriaEntity> findAllByAtivo(boolean ativo);
    Optional<CategoriaEntity> findByIdAndAtivo(Long id, boolean ativo);
}
