package com.AnalisisTokenLexico.main;

import com.AnalisisTokenLexico.util.Archivo;
import com.AnalisisTokenLexico.util.Matriz;
import com.AnalisisTokenLexico.util.Sintactico;
import com.AnalisisTokenLexico.util.Stacks;

public class Main {

    private GenerarTokens gt;
    private Stacks s;
    private Archivo a;
    private Matriz m;
    private Sintactico sin;

    public Main() {
        //gt.generar();
        //a = new Archivo();
        //s = new Stacks();
        //m = new Matriz();
        //gt = new GenerarTokens();
        sin = new Sintactico();
    }

    public static void main(String[] args) {
        new Main();

    }
}