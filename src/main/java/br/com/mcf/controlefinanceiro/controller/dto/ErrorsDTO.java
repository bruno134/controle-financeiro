package br.com.mcf.controlefinanceiro.controller.dto;

import br.com.fluentvalidator.context.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsDTO { private Collection<Error> errors; }
