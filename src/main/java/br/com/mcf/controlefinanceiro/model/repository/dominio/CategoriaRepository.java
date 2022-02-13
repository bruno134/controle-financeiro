package br.com.mcf.controlefinanceiro.model.repository.dominio;

import br.com.mcf.controlefinanceiro.model.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    List<CategoriaEntity> findAllByAtivo(boolean ativo);
    List<CategoriaEntity> findAllByAtivoAndTipoTransacao(boolean ativo, String tipoTransacao);
    Optional<CategoriaEntity> findByIdAndAtivo(Long id, boolean ativo);
}
