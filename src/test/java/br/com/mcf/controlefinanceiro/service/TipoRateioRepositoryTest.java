package br.com.mcf.controlefinanceiro.service;

import br.com.mcf.controlefinanceiro.model.entity.TipoRateioEntity;
import br.com.mcf.controlefinanceiro.model.repository.dominio.TipoRateioRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TipoRateioRepositoryTest implements TipoRateioRepository {

    List<TipoRateioEntity> entityList;

    public TipoRateioRepositoryTest() {
        this.entityList = new ArrayList<>();
    }

    public TipoRateioRepositoryTest(List<TipoRateioEntity> entityList) {
        this.entityList = entityList;
    }

    public void setInitialSet(List<TipoRateioEntity> entityList){
        this.entityList = entityList;
    }

    @Override
    public List<TipoRateioEntity> findAllByAtivo(boolean ativo) {

        return entityList.stream().filter(TipoRateioEntity::isAtivo).collect(Collectors.toList());
    }

    @Override
    public Optional<TipoRateioEntity> findByIdAndAtivo(Long id, boolean ativo) {
        return entityList.stream()
                .filter(tr -> tr.isAtivo()==ativo)
                .filter(tr -> tr.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<TipoRateioEntity> findAll() {
        return entityList;
    }

    @Override
    public List<TipoRateioEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<TipoRateioEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<TipoRateioEntity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return entityList.size();
    }

    @Override
    public void deleteById(@NotNull Long aLong) {
        entityList.removeIf(tr ->tr.getId().equals(aLong));
    }

    @Override
    public void delete(TipoRateioEntity entity) {
        entityList.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends TipoRateioEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends TipoRateioEntity> S save(S entity) {
        entity.setId(nextId());
        entityList.add(entity);
        return entity;
    }

    private Long nextId() {
        final var tamanhoLista = entityList.size();

        if (tamanhoLista>0)
            return entityList.get(tamanhoLista-1).getId()+1;
        else
            return 1L;
    }

    @Override
    public <S extends TipoRateioEntity> List<S> saveAll(Iterable<S> entities) {
     return null;
    }

    @Override
    public Optional<TipoRateioEntity> findById(Long aLong) {
        return entityList.stream()
                .filter(tr -> tr.getId().equals(aLong))
                .findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends TipoRateioEntity> S saveAndFlush(S entity) {
        return save(entity);
    }

    @Override
    public <S extends TipoRateioEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<TipoRateioEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public TipoRateioEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public TipoRateioEntity getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends TipoRateioEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends TipoRateioEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends TipoRateioEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends TipoRateioEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TipoRateioEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TipoRateioEntity> boolean exists(Example<S> example) {
        return false;
    }
}
