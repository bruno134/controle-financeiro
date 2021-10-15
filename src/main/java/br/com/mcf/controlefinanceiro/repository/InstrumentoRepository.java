package br.com.mcf.controlefinanceiro.repository;

import br.com.mcf.controlefinanceiro.entity.InstrumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstrumentoRepository extends JpaRepository<InstrumentoEntity, Long> {
    List<InstrumentoEntity> findAllByAtivo(boolean ativo);
    Optional<InstrumentoEntity> findByIdAndAtivo(Long id, boolean ativo);
}