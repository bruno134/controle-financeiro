package br.com.mcf.controlefinanceiro.repository;

import br.com.mcf.controlefinanceiro.entity.DespesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DespesaRepository extends JpaRepository<DespesaEntity,Long> {

    List<DespesaEntity> findAllByDataBetweenAndTipoTransacaoOrderByDataAsc(
                                            LocalDate DataInicio,
                                            LocalDate DataFim,
                                            String tipoTransacao);

    List<DespesaEntity> findAllByTipoTransacaoOrderByDataAsc(String tipoTransacao);
    void deleteByIdAndTipoTransacao(Long Id, String tipoTransacao);


    Optional<DespesaEntity> findByIdAndTipoTransacao(long longValue, String tipoTransacao);
}
