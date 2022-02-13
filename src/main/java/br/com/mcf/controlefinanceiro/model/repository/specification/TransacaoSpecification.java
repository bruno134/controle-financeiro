package br.com.mcf.controlefinanceiro.model.repository.specification;

import br.com.mcf.controlefinanceiro.model.entity.TransacaoEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

public class TransacaoSpecification implements Specification<TransacaoEntity> {

    private final SearchCriteria criteria;

    public TransacaoSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (@NotNull Root<TransacaoEntity> root,
             @NotNull CriteriaQuery<?> query,
             @NotNull CriteriaBuilder builder) {


        switch (criteria.getOperation()){

            case EQUAL:
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            case LESS_OR_EQUAL_THAN:
                return getPredicateLessThanOrEqual(root, builder);
            case LESS_THAN:
                return getPredicateLessThan(root, builder);
            case GREATER_OR_EQUAL_THAN:
                return getPredicateGreaterThanOrEqual(root,builder);
            case GREATER_THAN:
                return getPredicateGreaterThan(root,builder);
            default:
                return null;
        }

    }

    private Predicate getPredicateGreaterThanOrEqual(@NotNull Root<TransacaoEntity> root, @NotNull CriteriaBuilder builder) {
        if(root.get(criteria.getKey()).getJavaType() == LocalDate.class){
            final var parsedDate = LocalDate.parse(criteria.getValue().toString());
            return builder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()),parsedDate);
        } else
        if(root.get(criteria.getKey()).getJavaType() == Double.class){
            final var parsedDate = Double.parseDouble(criteria.getValue());
            return builder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()),parsedDate);
        }
        else
        return builder.greaterThanOrEqualTo(
                root.get(criteria.getKey()), criteria.getValue());
    }

    private Predicate getPredicateGreaterThan(@NotNull Root<TransacaoEntity> root, @NotNull CriteriaBuilder builder) {
        if(root.get(criteria.getKey()).getJavaType() == LocalDate.class){
            final var parsedDate = LocalDate.parse(criteria.getValue().toString());
            return builder.greaterThan(
                    root.get(criteria.getKey()),parsedDate);
        } else
        if(root.get(criteria.getKey()).getJavaType() == Double.class){
            final var parsedDate = Double.parseDouble(criteria.getValue());
            return builder.greaterThan(
                    root.get(criteria.getKey()),parsedDate);
        }
        else
            return builder.greaterThan(
                    root.get(criteria.getKey()), criteria.getValue());
    }

    private Predicate getPredicateLessThan(@NotNull Root<TransacaoEntity> root, @NotNull CriteriaBuilder builder) {
        if(root.get(criteria.getKey()).getJavaType() == LocalDate.class){
            final var parsedDate = LocalDate.parse(criteria.getValue().toString());
            return builder.lessThan(
                    root.get(criteria.getKey()),parsedDate);
        }else
        if(root.get(criteria.getKey()).getJavaType() == Double.class){
            final var parsedDate = Double.parseDouble(criteria.getValue());
            return builder.lessThan(
                    root.get(criteria.getKey()),parsedDate);
        }
        else
            return  builder.lessThan(
                    root.get(criteria.getKey()),LocalDate.parse(criteria.getValue().toString()));
    }

    private Predicate getPredicateLessThanOrEqual(@NotNull Root<TransacaoEntity> root, @NotNull CriteriaBuilder builder) {
        if(root.get(criteria.getKey()).getJavaType() == LocalDate.class){
            final var parsedDate = LocalDate.parse(criteria.getValue().toString());
            return builder.lessThanOrEqualTo(
                    root.get(criteria.getKey()),parsedDate);
        }else
        if(root.get(criteria.getKey()).getJavaType() == Double.class){
            final var parsedDate = Double.parseDouble(criteria.getValue());
            return builder.lessThanOrEqualTo(
                    root.get(criteria.getKey()),parsedDate);
        }
        else
            return  builder.lessThanOrEqualTo(
                    root.get(criteria.getKey()),LocalDate.parse(criteria.getValue().toString()));
    }
}
