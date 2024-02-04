package br.com.mcf.controlefinanceiro.service.transacao.load;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.mcf.controlefinanceiro.model.transacao.Despesa;
import br.com.mcf.controlefinanceiro.model.transacao.Receita;
import br.com.mcf.controlefinanceiro.model.transacao.Transacao;

public class CarregaCCreditoCapitalOne implements CarregarArquivo {

    private FileInputStream arquivo;
    private static final String INSTRUMENTO = "Cartão de Crédito";
    private static final String UNDEFINED = "Undefined";
    private static final String COMPARTILHADA = "COMPARTILHADA";

    public CarregaCCreditoCapitalOne(FileInputStream arquivo) {
        this.arquivo = arquivo;
    }

    @Override
    public Map<String, List<? extends Transacao>> carregar() {
        List<Despesa> despesas = new ArrayList<>();
        List<Receita> receitas = new ArrayList<>();
        Map<String, List<? extends Transacao>> transacoesMap = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(arquivo))) {
            reader.lines().skip(1).forEach(line -> {
                Transacao transaction = parseLineToTransaction(line);
                if (transaction instanceof Despesa) {
                    despesas.add((Despesa) transaction);
                }
            });

            transacoesMap = Map.of("despesas", despesas, "receitas", receitas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transacoesMap;
    }

    public Transacao parseLineToTransaction(String line) {

        String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        if (fields.length < 6) {
            return null;
        }

        LocalDate dateValue = LocalDate.parse(fields[0]);      
        Double transactionValue = returnDoubleFromFields(fields);
        String description = fields[3].replace("\"", "");

        return new Despesa(dateValue,
                (transactionValue),
                description,
                UNDEFINED,
                COMPARTILHADA,
                INSTRUMENTO,
                dateValue);
    }

    private Double returnDoubleFromFields(String[] fields) {       

        String value = fields[5].replace("\"", "")
        .replace(",", "");

        if (value.isEmpty()) {
            return Double.valueOf(0.0);
        }

        try{
            return Double.parseDouble(value);
        }
        catch (NullPointerException | NumberFormatException e){
            return Double.valueOf(0.0);
        }
    }
 }
