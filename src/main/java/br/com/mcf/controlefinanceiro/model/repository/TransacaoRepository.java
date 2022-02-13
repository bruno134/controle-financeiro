package br.com.mcf.controlefinanceiro.model.repository;

import br.com.mcf.controlefinanceiro.model.entity.TransacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<TransacaoEntity,Long>, JpaSpecificationExecutor<TransacaoEntity> {

    Page<TransacaoEntity> findAllByDataCompetenciaBetweenAndTipoTransacaoOrderByDataCompetenciaDesc(
                                            LocalDate DataInicio,
                                            LocalDate DataFim,
                                            String tipoTransacao, Pageable pageable);
    
    List<TransacaoEntity> findAllByDataCompetenciaBetweenAndTipoTransacaoOrderByDataCompetenciaDesc(
            LocalDate DataInicio,
            LocalDate DataFim,
            String tipoTransacao);


    List<TransacaoEntity> findAllByTipoTransacaoOrderByDataCompetenciaAsc(String tipoTransacao);
    void deleteByIdAndTipoTransacao(Long Id, String tipoTransacao);


    Optional<TransacaoEntity> findByIdAndTipoTransacao(long longValue, String tipoTransacao);
}
