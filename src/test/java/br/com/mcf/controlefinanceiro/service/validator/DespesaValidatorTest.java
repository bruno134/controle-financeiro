package br.com.mcf.controlefinanceiro.service.validator;

import br.com.fluentvalidator.Validator;
import br.com.mcf.controlefinanceiro.controller.cadastro.dto.DespesaDTO;
import br.com.mcf.controlefinanceiro.controller.cadastro.validator.InsereDespesaValidator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DespesaValidatorTest {



    @Test
    void deveRetornarComoDataValida(){

        DespesaDTO dto = new DespesaDTO(0,"13/08/2021", "4", "teste", "", "", "");

        Validator<DespesaDTO> vdto = new InsereDespesaValidator();

        var result = vdto.validate(dto);

        assertTrue(result.isValid());
        assertThat(result.getErrors().size(), equalTo(0));

    }

    @Test
    void naoDevePermitirDataVazia(){
        DespesaDTO dto = new DespesaDTO(0,"", "", "", "", "", "");
        Validator<DespesaDTO> vdto = new InsereDespesaValidator();

        var result = vdto.validate(dto);

        assertFalse(result.isValid());
        assertThat(result.getErrors().size(), greaterThan(0));
        assertThat(result.getErrors(), hasItem(hasProperty("field", equalTo("data"))));
        assertThat(result.getErrors(), hasItem(hasProperty("attemptedValue", equalTo(dto.getData()))));
        assertThat(result.getErrors(), hasItem(hasProperty("message", containsString("Data valida deve ser informada"))));

    }

    @Test
    void devePermitirValorDespesaNumericoQuandoInformadoInteiro(){
        DespesaDTO dto = new DespesaDTO(0,"29/09/2021", "2.00", "teste1", "", "", "");
        Validator<DespesaDTO> vdto = new InsereDespesaValidator();
        var result = vdto.validate(dto);

        assertTrue(result.isValid());
        assertThat(result.getErrors().size(), equalTo(0));
        }

    @Test
    void devePermitirValorDespesaNumericoQuandoInformadoDecimal(){
        DespesaDTO dto = new DespesaDTO(0,"29/09/2021", "2.13", "teste1", "", "", "");
        Validator<DespesaDTO> vdto = new InsereDespesaValidator();
        var result = vdto.validate(dto);
        assertTrue(result.isValid());
        assertThat(result.getErrors().size(), equalTo(0));
    }

    @Test
    void devePermitirValorDespesaNumericoQuandoInformadoZero(){
        DespesaDTO dto = new DespesaDTO(0,"29/09/2021", "0", "teste1", "", "", "");
        Validator<DespesaDTO> vdto = new InsereDespesaValidator();
        var result = vdto.validate(dto);
        assertTrue(result.isValid());
        assertThat(result.getErrors().size(), equalTo(0));
    }

    @Test
    void devePermitirValorDespesaNumericoQuandoInformadoNegativo(){
        DespesaDTO dto = new DespesaDTO(0,"29/09/2021", "-2.13", "teste1", "", "", "");
        Validator<DespesaDTO> vdto = new InsereDespesaValidator();
        var result = vdto.validate(dto);
        assertTrue(result.isValid());
        assertThat(result.getErrors().size(), equalTo(0));
    }

    @Test
    void deveInformarErroQuandoSeparadorDecimalInformadoComVirgula(){
        DespesaDTO dto = new DespesaDTO(0,"29/09/2021", "-2,13", "teste1", "", "", "");
        Validator<DespesaDTO> vdto = new InsereDespesaValidator();
        var result = vdto.validate(dto);

        assertFalse(result.isValid());
        assertThat(result.getErrors().size(), greaterThan(0));
        assertThat(result.getErrors(), hasItem(hasProperty("field", equalTo("valor"))));
        assertThat(result.getErrors(), hasItem(hasProperty("attemptedValue", equalTo(dto.getValor()))));
        assertThat(result.getErrors(), hasItem(hasProperty("message", containsString("Usar ponto '.' como separador decimal"))));
    }

    @Test
    void deveFalharQuandoValorNaoENumericoVazio(){
        DespesaDTO dto = new DespesaDTO(0,"29/09/2021", "", "teste1", "", "", "");
        Validator<DespesaDTO> vdto = new InsereDespesaValidator();
        var result = vdto.validate(dto);
        assertFalse(result.isValid());
        assertThat(result.getErrors().size(), greaterThan(0));
    }

    @Test
    void deveFalharQuandoValorNaoENumericoComLetras(){
        DespesaDTO dto = new DespesaDTO(0,"29/09/2021", "$10.90", "teste1", "", "", "");
        Validator<DespesaDTO> vdto = new InsereDespesaValidator();
        var result = vdto.validate(dto);
        assertFalse(result.isValid());
        assertThat(result.getErrors().size(), greaterThan(0));
    }




}
