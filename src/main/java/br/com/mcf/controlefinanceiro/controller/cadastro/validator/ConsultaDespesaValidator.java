package br.com.mcf.controlefinanceiro.controller.cadastro.validator;

import br.com.fluentvalidator.AbstractValidator;
import br.com.mcf.controlefinanceiro.controller.cadastro.dto.DadosConsultaDespesaDTO;
import org.springframework.stereotype.Component;

import static br.com.fluentvalidator.predicate.StringPredicate.isNumber;
import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

@Component
public class ConsultaDespesaValidator extends AbstractValidator<DadosConsultaDespesaDTO> {
    @Override
    public void rules() {
        ruleFor(DadosConsultaDespesaDTO::getMes)

                /**
                 * Valida se entrada de mês é numérica
                 */

                .must(isNumber())
                .withFieldName("mes")
                .withMessage("Valor de mês deve ser numérico")
                .withCode("006");

                /**
                 *  Valida se entrada de mês está entre os valores de 1 a 12.
                 */

//                .must(lessThan("13"))
//                .withFieldName("mes")
//                .withMessage("Mês deve ser informado entre 1 e 12")
//                .withCode("007");

        /**
         * Valida se entrada de ano é valida.
         */
        ruleFor(DadosConsultaDespesaDTO::getAno)
                .must(isNumber())
                .withFieldName("ano")
                .withMessage("Valor de ano deve ser numérico")
                .withCode("008");



        ruleFor(DadosConsultaDespesaDTO::getIdentificador)
                .must(isNumber())
                .when(not(stringEmptyOrNull()))
                .withFieldName("identificador")
                .withMessage("Valor do identificador deve ser numérico")
                .withCode("009");

    }
}
