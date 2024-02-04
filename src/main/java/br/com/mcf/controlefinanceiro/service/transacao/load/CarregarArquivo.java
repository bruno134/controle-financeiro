package br.com.mcf.controlefinanceiro.service.transacao.load;

import java.util.List;
import java.util.Map;

import br.com.mcf.controlefinanceiro.model.transacao.Transacao;

public interface CarregarArquivo {

    Map<String,List<? extends Transacao>> carregar();
}
