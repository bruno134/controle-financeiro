package br.com.mcf.controlefinanceiro.controller.validator;

import br.com.fluentvalidator.AbstractValidator;
import br.com.mcf.controlefinanceiro.controller.dto.DespesaDTO;

import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.*;

public class InsereDespesaValidator extends AbstractValidator <DespesaDTO> {


//TODO Definir os códigos de erro


    @Override
    public void rules() {
        ruleFor(DespesaDTO::getData)
                .must(not(stringEmptyOrNull()))
                .withMessage("Uma data deve ser informada")
//                .must(isDate("dd/MM/yyyy"))
//                .withMessage("Data deve ser informada no formato 'dd/mm/aaaa'")
                .withFieldName("data")
                .withCode("001");

        ruleFor(DespesaDTO::getValor)
                .must(isNumber().and(not(stringEmptyOrNull())))
                .withMessage("Valor informado inválido")
                .withFieldName("valor")
                .withCode("002")

                .must(not(stringContains(",")))
                .withMessage("Usar ponto '.' como separador decimal")
                .withFieldName("valor")
                .withCode("003")

                .must(not(stringEmptyOrNull()))
                .withMessage("Valor deve ser informado")
                .withFieldName("valor")
                .withCode("004");

        ruleFor(DespesaDTO::getDescricao)
                .must(not(stringEmptyOrNull()))
                .withMessage("Obrigatório informar descrição da despesa")
                .withFieldName("descricao")
                .withCode("005");

    }
}
