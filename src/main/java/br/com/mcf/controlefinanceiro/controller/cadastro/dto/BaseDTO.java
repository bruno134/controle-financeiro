package br.com.mcf.controlefinanceiro.controller.cadastro.dto;

import br.com.mcf.controlefinanceiro.model.BaseDominio;
import br.com.mcf.controlefinanceiro.model.Despesa;

public interface BaseDTO {
    BaseDominio toObject();
}