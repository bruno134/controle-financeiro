package br.com.mcf.controlefinanceiro.service.transacao;

import br.com.fluentvalidator.context.Error;
import br.com.mcf.controlefinanceiro.model.exceptions.TransactionBusinessException;
import br.com.mcf.controlefinanceiro.model.transacao.Despesa;
import br.com.mcf.controlefinanceiro.model.transacao.Transacao;
import br.com.mcf.controlefinanceiro.util.ConstantFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportArquivoService {

    public static final String NAO_CATEGORIZADO = "Não categorizado";
    public static final String COMPARTILHADA = "COMPARTILHADA";


    public List<Transacao> importaDespesaDoExcel(InputStream inputFile, String tipo
                         , Integer mes, Integer ano) throws IOException, TransactionBusinessException {


        if(mes>12)
            throw new TransactionBusinessException(Error.create("mes",
                                                       "Mes informado inválido",
                                                          "99",
                                                                mes));

        Workbook wb = new HSSFWorkbook(inputFile);
        Sheet sheet = wb.getSheetAt(0);

        int i=0;
        boolean grava = false;

        List<Transacao> listaDespesa = new ArrayList<>();

        for(Row row: sheet){
            List<String> linha = new ArrayList<>();
            for(Cell cell: row){
                switch (cell.getCellType()) {
                    case STRING:
                        linha.add(cell.getRichStringCellValue() + "");
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            linha.add(cell.getDateCellValue() + "");
                        } else {
                            linha.add(cell.getNumericCellValue() + "");
                        }
                        break;
                    case BOOLEAN:
                        linha.add(cell.getBooleanCellValue() + "");
                        break;
                    case FORMULA:
                        linha.add(cell.getCellFormula() + "");
                        break;
                    default:
                        linha.add(" ");
                }
            }

            if(("data".equals(linha.get(0))) || (grava && isDate(linha.get(0))) ) {
                if(grava) {
                    listaDespesa.add(new Despesa(LocalDate.parse(linha.get(0), ConstantFormat.format)
                            ,Double.parseDouble(linha.get(3))
                            ,linha.get(1)
                            ,NAO_CATEGORIZADO
                            ,COMPARTILHADA
                            ,tipo
                    , LocalDate.of(ano,mes,1)));
                    i++;
                }
                grava=true;
            }else{
                grava=false;
            }
        }

        return listaDespesa;
    }

    //TODO Jogar para um UTILS
    public boolean isDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            format.setLenient(false);
            format.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

}
