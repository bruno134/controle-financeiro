package br.com.mcf.controlefinanceiro.service;

public class Qqteste {

    public static void main(String[] args) {
        Double sb = 8000.0;
        Double sp = 16000.0;
        Double vc = 6200.0;
        Double tb = 0.5;
        Double tp = 1 - tb;




        var v1 = (sb-(vc*tb));
        var v2 = (sp-(vc*tp));
        var v3 = v2-v1;


        //(sb-(vc*tb)) = 5600
        // sb - 5600 = (vc*tb)
        // 11000-5600 = 13800 * tb
        // tb = (11000 - 5600)/13800

        System.out.println("v1 "+ v1);
        System.out.println("v2 "+v2);
        System.out.println("v3 "+v3);
        System.out.println("Valor Bruno " + (v1+(v3/2)));
        System.out.println("Valor Pri " + (v2-(v3/2)));
        System.out.println("Bruno ==> "+ (sb-(v1+(v3/2))) + "==> " +(sb-(v1+(v3/2)))/vc);
        System.out.println("Pri ==> "+ (sp-(v2-(v3/2))) + "==> " + (sp-(v2-(v3/2)))/vc);
        System.out.println(((sb-(v1+(v3/2)))/vc) + (sp-(v2-(v3/2)))/vc);
    //(sp-(v2-(v3/2)));
    }
}
