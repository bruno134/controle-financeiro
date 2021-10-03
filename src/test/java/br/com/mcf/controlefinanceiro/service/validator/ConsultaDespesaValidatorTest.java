package br.com.mcf.controlefinanceiro.service.validator;

import br.com.fluentvalidator.context.ValidationResult;
import br.com.mcf.controlefinanceiro.controller.dto.DadosConsultaDespesaDTO;
import br.com.mcf.controlefinanceiro.controller.validator.ConsultaDespesaValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ConsultaDespesaValidatorTest {

    @Autowired
    private ConsultaDespesaValidator validator;
    private final DadosConsultaDespesaDTO valoresConsulta = new DadosConsultaDespesaDTO("2020", "9", "1");

    @Test
    void deveValidarSeValorMesENumerico(){

        final var result = validator.validate(valoresConsulta);

        assertTrue(result.isValid());
        assertThat(result.getErrors().size(), equalTo(0));

    }


    @Test
    void deveValidarSeValorAnoENumerico(){
        final var result = validator.validate(valoresConsulta);
        assertTrue(result.isValid());
        assertThat(result.getErrors().size(), equalTo(0));

    }

    @Test
    void deveValidarSeValorIdentificadorENumerico(){

        final var result = validator.validate(valoresConsulta);
        assertTrue(result.isValid());
        assertThat(result.getErrors().size(), equalTo(0));

    }

    @Test
    void naoDeveValidarIdentificadorQuandoValorForBranco(){

        final DadosConsultaDespesaDTO valoresConsulta = new DadosConsultaDespesaDTO("2020", "9", "");
        final var result = validator.validate(valoresConsulta);
        assertTrue(result.isValid());
        assertThat(result.getErrors().size(), equalTo(0));

    }

    @Test
    void naoDeveValidarIdentificadorQuandoValorForNulo(){

        final DadosConsultaDespesaDTO valoresConsulta = new DadosConsultaDespesaDTO("2020", "9", null);
        final var result = validator.validate(valoresConsulta);
        assertTrue(result.isValid());
        assertThat(result.getErrors().size(), equalTo(0));

    }

}
