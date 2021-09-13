import br.com.mcf.controlefinanceiro.util.ConstantFormat;

import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;

public class TestesDiversos {
    public static void main(String[] args) throws ParseException {

        LocalDate data = LocalDate.of(2021,10,1);

        System.out.println(data.lengthOfMonth());

    }
}
