package br.com.mcf.controlefinanceiro.service.transacao.load;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.mcf.controlefinanceiro.model.transacao.Despesa;
import br.com.mcf.controlefinanceiro.model.transacao.Receita;
import br.com.mcf.controlefinanceiro.model.transacao.Transacao;

public class CarregaCCreditoBofa implements CarregarArquivo{

    private FileInputStream arquivo;

    private static final String INSTRUMENTO = "Cartão de Crédito - Bofa";
    private static final String UNDEFINED = "Undefined";
    private static final String COMPARTILHADA = "COMPARTILHADA";

    public CarregaCCreditoBofa(FileInputStream arquivo) {
        this.arquivo = arquivo;
    }


    @Override
    public Map<String, List<? extends Transacao>> carregar() {
        List<Despesa> despesas = new ArrayList<>();
        List<Receita> receitas = new ArrayList<>();
        Map<String,List<? extends Transacao>> transacoesMap = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(arquivo))) {
            reader.lines().skip(1).forEach(line ->{
                Transacao transaction = parseLineToTransaction(line);                
                if(transaction instanceof Despesa){                
                    despesas.add((Despesa)transaction);
                }else{  
                    receitas.add((Receita)transaction);
                }
            });

            transacoesMap = Map.of("despesas", despesas, "receitas", receitas);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transacoesMap;
    }

    public Transacao parseLineToTransaction(String line){
        String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        String date = fields[0].replace("\"", "");
        LocalDate dateValue = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String value = fields[4].replace("\"", "")
                                .replace(",", "");
        Double transactionValue = Double.parseDouble(value);
        String description = fields[2].replace("\"", "");

            if(transactionValue < 0){
                 return new Despesa(dateValue, 
                                    transactionValue*-1, 
                                    description,
                                    UNDEFINED,
                                    COMPARTILHADA,
                                    INSTRUMENTO,
                                    dateValue);
            }else{
                 return new Receita(dateValue, 
                                    transactionValue, 
                                    description,
                                    UNDEFINED,
                                    COMPARTILHADA,
                                    INSTRUMENTO,
                                    dateValue);
            }
        }  

}
