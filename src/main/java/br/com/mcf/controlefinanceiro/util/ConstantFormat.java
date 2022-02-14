package br.com.mcf.controlefinanceiro.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ConstantFormat {
    static public final Locale BRAZIL = new Locale ("pt", "BR");
    static public final DecimalFormatSymbols DECIMAL_FORMAT_SYMBOL_BR = new DecimalFormatSymbols (BRAZIL);
    static public final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static public final DateTimeFormatter dbDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");



}
