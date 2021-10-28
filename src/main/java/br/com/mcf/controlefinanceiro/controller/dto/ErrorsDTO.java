package br.com.mcf.controlefinanceiro.controller.dto;

import br.com.fluentvalidator.context.Error;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorsDTO {
    private Collection<Error> errors;

    public ErrorsDTO() {
        this.errors = new ArrayList<>();
    }
}