package br.com.mcf.controlefinanceiro.repository;

import br.com.mcf.controlefinanceiro.entity.DespesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends JpaRepository<DespesaEntity,Long> {
}
