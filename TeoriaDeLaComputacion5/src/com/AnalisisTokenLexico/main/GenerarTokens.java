package com.AnalisisTokenLexico.main;

import com.AnalisisTokenLexico.util.Archivo;

import java.util.ArrayList;

public class GenerarTokens {

    private static final String[] palabrasReservadas = {
            "abstract", "assert", "boolean", "break", "byte", "case", "catch",
            "char", "class", "const", "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float", "for", "goto", "if",
            "implements", "import", "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public", "return", "short",
            "static", "strictfp", "super", "switch", "sychronized", "this", "throw",
            "throws", "transient", "try", "void", "volatile", "while"
    };

    private static final char[] caracteresEspeciales = {
            '(', ')', '{', '}', '[', ']', '+', '-', '*', '/', '=', ';', '<', '>', '"', '\'', '.', '_'
    };

    private AutomataTokens automata;
    private ArrayList<String> lista;
    private Archivo a;
    private String linea, lexema, programa;
    private boolean error, key;
    private ArrayList<Token> tokens, errores;
    public String[] tkns;
    public Token[] t;

    public GenerarTokens(){
        a = new Archivo();
        automata = new AutomataTokens();
        lista = a.leerArchivo();
        tokens = new ArrayList();
        errores = new ArrayList();
        lexema = "";
        error = false;
        generar(lista);
        toArray(tokens);
        //imprimir(tokens);
    }

    public void generar(ArrayList ar){
        System.out.println("Selecciona el archivo de programa");
        for (String s : lista) {
            linea = s;
            for (int j = 0; j < linea.length(); j++) {
                if (!isSpace(linea.charAt(j)) && !isCaracterEspecial(linea.charAt(j))) {
                    switch (automata.getEstadoActual()) {
                        case "0":
                            if (isDigit(linea.charAt(j))) {
                                automata.cambiarEstadoActual(AutomataTokens.IS_DIGIT);
                                lexema += String.valueOf(linea.charAt(j));
                            } else {
                                automata.cambiarEstadoActual(AutomataTokens.OTHER);
                                lexema += String.valueOf(linea.charAt(j));
                            }
                            break;
                        case "1":
                            if (!isDigit(linea.charAt(j))) {
                                error = true;
                                automata.cambiarEstadoActual(AutomataTokens.OTHER);
                                lexema += String.valueOf(linea.charAt(j));
                            } else {
                                lexema += String.valueOf(linea.charAt(j));
                            }
                            break;
                        case "2":
                            lexema += String.valueOf(linea.charAt(j));
                            break;
                        case "3":
                            if (isAlpha(linea.charAt(j))) {
                                automata.cambiarEstadoActual(AutomataTokens.IS_ALPHA);
                                lexema += String.valueOf(linea.charAt(j));
                            } else if (isOther(linea.charAt(j))) {
                                automata.cambiarEstadoActual(AutomataTokens.OTHER);
                                lexema += String.valueOf(linea.charAt(j));
                            }
                            break;
                        case "4":
                            if (isAlpha(linea.charAt(j)) || isDigit(linea.charAt(j))) {
                                lexema += String.valueOf(linea.charAt(j));
                            } else if (isOther(linea.charAt(j))) {
                                verificarPalabraReservada();
                            }
                            break;
                        case "5":
                            generarToken(261);
                            automata.reiniciar();
                            lexema = "";
                            break;
                        case "6":
                            generarToken(262);
                            automata.reiniciar();
                            lexema = "";
                            break;
                    }
                } else if (isCaracterEspecial(linea.charAt(j))) {
                    if (lexema.length() != 0) {
                        if (!error)
                            verificarPalabraReservada();
                        else {
                            errores.add(new Token(0, lexema));
                            automata.reiniciar();
                            error = false;
                        }
                        lexema = String.valueOf(linea.charAt(j));
                        if(linea.length()>j+1){
                            String temp = lexema+String.valueOf(linea.charAt(j+1));
                            if(temp.equals(">=") || temp.equals("<=") ||
                                    temp.equals("==") || temp.equals("!=")){
                                lexema=temp;
                                j++;
                            }
                        }
                        generarToken(linea.charAt(j));
                        lexema = "";
                    } else {
                        lexema = String.valueOf(linea.charAt(j));
                        generarToken(linea.charAt(j));
                        lexema = "";
                    }
                } else if (isSpace(linea.charAt(j)) && !lexema.equals("")) {
                    if (lexema.length() > 1 && !error) {
                        verificarPalabraReservada();
                    } else if (error) {
                        errores.add(new Token(0, lexema));
                        lexema = "";
                        automata.reiniciar();
                    }
                }
            }
        }
        //tabla = new TablaTokens(tokens, errores, archivo);
        //System.out.println(errores);
        //imprimir(tokens);
        //return tokens;
    }

    public void toArray(ArrayList ar){
        t= new Token[tokens.size()];
        for (int j = 0; j < tokens.size(); j++) {
            t[j]=tokens.get(j);
            //if(t[j].getTipo()==262)
            //    t[j].setLexema("id");
            //if(t[j].getTipo()==261)
            //    t[j].setLexema("IntLiteral");
            //System.out.println("tipo: "+t[j].getTipo());
            //System.out.println("lexema: "+t[j].getLexema());
        }
    }

    private boolean isNum(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    private void verificarPalabraReservada() {
        key = false;
        for (String palabrasReservada : palabrasReservadas) {
            if (lexema.equals(palabrasReservada)) {
                generarToken(260);
                automata.reiniciar();
                lexema = "";
                key = true;
                break;
            }
        }
        if (!key) {
            if(isNum(lexema))
                generarToken(261);
            else
                generarToken(262);
            automata.reiniciar();
            lexema = "";
        }
    }

    private void generarToken(int tipo) {
        tokens.add(new Token(tipo, lexema));
    }

    private boolean isDigit(char caracter) {
        String aux = String.valueOf(caracter);
        return aux.matches("[0-9]");
    }

    private boolean isAlpha(char caracter) {
        String aux = String.valueOf(caracter);
        return aux.matches("[a-z]|[A-Z]");
    }

    private boolean isOther(char caracter) {
        String aux = String.valueOf(caracter);
        return aux.matches("[^a-zA-Z0-9]");
    }

    private boolean isSpace(char caracter) {
        String aux = String.valueOf(caracter);
        return aux.equals(" ") || aux.equals("\t");
    }

    private boolean isCaracterEspecial(char caracter) {
        boolean key = false;
        for (char caracteresEspeciale : caracteresEspeciales)
            if (caracter == caracteresEspeciale) {
                key = true;
                break;
            }
        return key;
    }

    public void imprimir(ArrayList todo){
        for (int i=0; i< todo.size(); i++) {
            System.out.println(todo.get(i).toString());
        }

    }



}
