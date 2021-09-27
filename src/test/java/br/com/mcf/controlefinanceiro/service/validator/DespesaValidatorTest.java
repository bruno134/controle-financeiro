package br.com.mcf.controlefinanceiro.service.validator;

import br.com.fluentvalidator.Validator;
import br.com.mcf.controlefinanceiro.controller.dto.DespesaDTO;
import br.com.mcf.controlefinanceiro.controller.validator.InsereDespesaValidator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DespesaValidatorTest {



    @Test
    void deveRetornarDatanoFormatoBrasil(){

        DespesaDTO dto = new DespesaDTO(0,"09/09/2021", "", "", "", "", "");
        Validator<DespesaDTO> vdto = new InsereDespesaValidator();

        var result = vdto.validate(dto);

        result.getErrors().stream().forEach(error -> {
            System.out.println(error.getMessage());
            System.out.println(error.getAttemptedValue());
        });


    }

    @Test
    void naoDevePermitirDataVazia(){
        DespesaDTO dto = new DespesaDTO(0,"", "", "", "", "", "");
        Validator<DespesaDTO> vdto = new InsereDespesaValidator();

        var result = vdto.validate(dto);

        result.getErrors().stream().forEach(error -> System.out.println(error.getMessage()));
    }

    @Test
    void valorDespesaDeveSerNumerico(){
        DespesaDTO dto = new DespesaDTO(0,"29/09/2021", "2.00", "", "", "", "");
        Validator<DespesaDTO> vdto = new InsereDespesaValidator();
        var result = vdto.validate(dto);
        assertTrue(result.isValid());
        assertThat(result.getErrors().size(), equalTo(0));
        }

    @Test
    void deveFalharQuandoValorNaoENumerico(){
        DespesaDTO dto = new DespesaDTO(0,"29/09/2021", "", "", "", "", "");
        Validator<DespesaDTO> vdto = new InsereDespesaValidator();
        var result = vdto.validate(dto);
        assertFalse(result.isValid());
        assertThat(result.getErrors().size(), greaterThan(0));
    }




}
