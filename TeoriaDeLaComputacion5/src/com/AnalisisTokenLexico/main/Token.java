package com.AnalisisTokenLexico.main;

public class Token {
    private int tipo;
    private String lexema;

    public Token(int tipo, String lexema){
        this.tipo = tipo;
        this.lexema = lexema;
    }


    public int getTipo() {
        return tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema){ this.lexema=lexema; }

    @Override
    public String toString(){
        return tipo +"\t\t"+lexema;
    }
}
