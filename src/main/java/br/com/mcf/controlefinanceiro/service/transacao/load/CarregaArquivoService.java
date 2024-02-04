package br.com.mcf.controlefinanceiro.service.transacao.load;

import java.util.List;
import java.util.Map;

import br.com.mcf.controlefinanceiro.model.transacao.Transacao;


public class CarregaArquivoService {

    private final CarregarArquivo carregarArquivo;
    
    public CarregaArquivoService(CarregarArquivo carregarArquivo) {
        this.carregarArquivo = carregarArquivo;
    }

    public Map<String,List<? extends Transacao>> carregar() {
        return carregarArquivo.carregar();
    }

}
