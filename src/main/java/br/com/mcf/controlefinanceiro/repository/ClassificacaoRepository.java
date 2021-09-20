package br.com.mcf.controlefinanceiro.repository;

import br.com.mcf.controlefinanceiro.entity.ClassificacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassificacaoRepository extends JpaRepository<ClassificacaoEntity, Long> {
    List<ClassificacaoEntity> findAllByAtivo(boolean ativo);
    Optional<ClassificacaoEntity> findByIdAndAtivo(Long id, boolean ativo);
}
