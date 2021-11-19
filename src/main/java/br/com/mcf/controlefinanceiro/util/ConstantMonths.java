package br.com.mcf.controlefinanceiro.util;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class ConstantMonths {
    public static final Map<Integer, String> months =
            Map.ofEntries(
                            entry(1,"Janeiro"),
                            entry(2,"Fevereiro"),
                            entry(3,"Março"),
                            entry(4,"Abril"),
                            entry(5,"Maio"),
                            entry(6,"Junho"),
                            entry(7,"Julho"),
                            entry(8,"Agosto"),
                            entry(9,"Setembro"),
                            entry(10,"Outubro"),
                            entry(11,"Novembro"),
                            entry(12,"Dezembro"));
}
