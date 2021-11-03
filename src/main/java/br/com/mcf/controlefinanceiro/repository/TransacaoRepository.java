package br.com.mcf.controlefinanceiro.repository;

import br.com.mcf.controlefinanceiro.entity.TransacaoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<TransacaoEntity,Long> {

    Slice<TransacaoEntity> findAllByDataCompetenciaBetweenAndTipoTransacaoOrderByDataCompetenciaAsc(
                                            LocalDate DataInicio,
                                            LocalDate DataFim,
                                            String tipoTransacao, Pageable pageable);

    List<TransacaoEntity> findAllByDataCompetenciaBetweenAndTipoTransacaoOrderByDataCompetenciaAsc(
            LocalDate DataInicio,
            LocalDate DataFim,
            String tipoTransacao);


    List<TransacaoEntity> findAllByTipoTransacaoOrderByDataCompetenciaAsc(String tipoTransacao);
    void deleteByIdAndTipoTransacao(Long Id, String tipoTransacao);


    Optional<TransacaoEntity> findByIdAndTipoTransacao(long longValue, String tipoTransacao);
}
