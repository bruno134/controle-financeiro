package br.com.mcf.controlefinanceiro.model.repository.dominio;

import br.com.mcf.controlefinanceiro.model.entity.TipoRateioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoRateioRepository extends JpaRepository<TipoRateioEntity, Long> {
    List<TipoRateioEntity> findAllByAtivo(boolean ativo);
    Optional<TipoRateioEntity> findByIdAndAtivo(Long id, boolean ativo);
}
