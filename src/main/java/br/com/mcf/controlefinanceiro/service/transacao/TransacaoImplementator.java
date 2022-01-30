package br.com.mcf.controlefinanceiro.service.transacao;

import br.com.mcf.controlefinanceiro.model.exceptions.TransacaoNaoEncontradaException;
import br.com.mcf.controlefinanceiro.model.transacao.Transacao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransacaoImplementator {

    Transacao inserir (Transacao transacao);
    Optional<Transacao> alterar (Transacao transacao);
    void apagar(Transacao transacao) throws TransacaoNaoEncontradaException;
    Optional<Transacao> buscarPorID(Integer id);
    List<Transacao> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim);

}
