package br.com.mcf.controlefinanceiro.repository;

import br.com.mcf.controlefinanceiro.entity.TransacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<TransacaoEntity,Long> {

    List<TransacaoEntity> findAllByDataBetweenAndTipoTransacaoOrderByDataAsc(
                                            LocalDate DataInicio,
                                            LocalDate DataFim,
                                            String tipoTransacao);

    List<TransacaoEntity> findAllByTipoTransacaoOrderByDataAsc(String tipoTransacao);
    void deleteByIdAndTipoTransacao(Long Id, String tipoTransacao);


    Optional<TransacaoEntity> findByIdAndTipoTransacao(long longValue, String tipoTransacao);
}
