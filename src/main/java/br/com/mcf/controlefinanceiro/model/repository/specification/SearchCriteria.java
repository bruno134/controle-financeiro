package br.com.mcf.controlefinanceiro.model.repository.specification;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteria {

    private String key;
    private QueryOperator operation;
    private String value;

    public SearchCriteria(String key, QueryOperator operation, String value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
