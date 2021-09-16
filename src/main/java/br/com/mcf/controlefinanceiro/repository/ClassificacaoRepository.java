package br.com.mcf.controlefinanceiro.repository;

import br.com.mcf.controlefinanceiro.entity.ClassificacaoEntity;
import br.com.mcf.controlefinanceiro.model.Classificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassificacaoRepository extends JpaRepository<ClassificacaoEntity, Long> {
    List<ClassificacaoEntity> findAllByAtivo(boolean ativo);
}
