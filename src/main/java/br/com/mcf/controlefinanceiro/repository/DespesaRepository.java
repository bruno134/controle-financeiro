package br.com.mcf.controlefinanceiro.repository;

import br.com.mcf.controlefinanceiro.entity.DespesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DespesaRepository extends JpaRepository<DespesaEntity,Long> {

    List<DespesaEntity> findAllByDataBetweenOrderByDataAsc(
                                            LocalDate DataInicio,
                                            LocalDate DataFim);



}
