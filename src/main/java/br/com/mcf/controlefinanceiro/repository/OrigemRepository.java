package br.com.mcf.controlefinanceiro.repository;

import br.com.mcf.controlefinanceiro.entity.OrigemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrigemRepository extends JpaRepository<OrigemEntity, Long> {
    List<OrigemEntity> findAllByAtivo(boolean ativo);
    Optional<OrigemEntity> findByIdAndAtivo(Long id, boolean ativo);
}
