package com.AnalisisTokenLexico.util;

import com.AnalisisTokenLexico.main.GenerarTokens;

import java.util.ArrayList;

public class Sintactico {

    Stacks s;
    Pila p;
    GenerarTokens gt;
    Matriz predict;

    public Sintactico(){
        s = new Stacks();
        p = new Pila();
        gt = new GenerarTokens();
        predict = new Matriz();
        //inicial();
        //p.push(new Nodo("program"));
        driver();
        //System.out.println("indice row"+getColumn(";"));;
        //System.out.println(inNoTerminals("sentencias"));
        //String inv = invertir("Anita lava la tina");
        //System.out.println(inv);
    }

    public String inicial(){
        String in="";
        int control;

        for(int i=0; i < s.noTerm.length; i++){
            control = 0;
            //System.out.println("temp: "+s.noTerm[i]);
            for(int j=0; j<s.der.length; j++){
                //System.out.println("comparar: "+s.der[j]);
                if( s.der[j].contains(s.noTerm[i]) )
                    control++;
            }
            //System.out.println("control "+control);
            if(control==0)
                in=s.noTerm[i];
            //System.out.println("inicial "+in);
        }
        //System.out.println(in);
        return in;
    }

    public void driver(){
        String x="";//string para la parte alta de la pila
        String a="";//string para guardar los tokens de entrada
        String temp="", reemplazo="";
        int tipo;
        //int fila, columna;
        //begin
        p.push(new Nodo(inicial()));
        //for que recorre sobre los tokens del programa leido
        for (int i = 0; i < gt.t.length; i++) {
            //se le asigna el tope de la pila
            x=p.top();
            //System.out.println(x);
            //se le asigna el token del programa leido
            //c=gt.t[i];
            //while mientras la pila no sea vacia
            a = gt.t[i].getLexema();
            //System.out.println("c "+c);
            while(!p.isEmpty()){
                //a = Character.toString(a.charAt(a.length()-1));
                System.out.println("x "+x);
                System.out.println("a "+a);
                System.out.println("row "+getRow(x));
                System.out.println("colum "+getColumn(a));
                //System.out.println("a "+a);
                //verificar si x esta contenido en los terminales
                if(inNoTerminals(x)){
                    temp="";
                    //System.out.println("indice de la matriz "+predict.buscaIndice(getRow(x),getColumn(a)));
                    //obtener el numero dentro de la matriz en la posicion numerica
                    //en la que esta contenida x(fila, no terminal) y a(columna, termi)
                    System.out.println("row "+getRow(x));
                    System.out.println("colum "+getColumn(a));
                    //System.out.println("a "+a);
                    if(predict.buscaIndice(getRow(x),getColumn(a))!=0){
                        //ver como meter el replace with x prediction
                        reemplazo=s.der[predict.buscaIndice(getRow(x),getColumn(a))-1];
                        //hacer pop
                        p.pop();
                        //ciclo para recorrer la derivacion inversamente
                        //reemplazo contiene el lado derecho de las producciones
                        //reverseString lee de atras hacia adelante una cadena
                        temp=reverseString(reemplazo);
                        //al final no lee otro espacio, se hace el push del ultimo contenido
                        //invertir invierte la palabra( dac = cad )
                        p.push(new Nodo(invertir(temp)));
                        x=p.top();
                        System.out.println("nuevos nodos");
                        p.recorrer();
                    }else {
                        System.out.println("Error sintactico");
                        System.exit(0);
                    }
                }else if(x.equals(a)) {
                    p.pop();
                    if (!p.isEmpty()) {
                        x = p.top();
                        //a=gt.t[i+1].getLexema();
                        tipo=gt.t[i+1].getTipo();
                        if(tipo ==260 || tipo == 261 || tipo == 262)
                            a=check(tipo);
                        else
                            a=gt.t[i+1].getLexema();
                    }
                }else{
                    //System.out.println("a del error "+a);
                    System.out.println("Error de sintaxis");
                    p.recorrer();
                    System.exit(0);
                }
            }
        }
    }

    public String check(int n){
        String a="";
        if (n==261)
            a = "IntLiteral";
        if (n==262)
            a = "id";
        if (n==260)
            a = "if";
        return a;
    }

    public String reverseString(String reemplazo){
        String temp="";
        for (int j = reemplazo.length()-1; j >= 0; j--) {
            //cuandoe lee algo diferente a ' ' lo guarda en la cadena
            if(reemplazo.charAt(j)!=' '){
                temp+=reemplazo.charAt(j);
                //System.out.println("true if "+temp);
            }//si lee un espacio, invierte la cadena y hace un push a la pila
            //limpiamos temp
            else{
                //System.out.println("false if "+temp);
                if(!temp.equals("vacio")){
                    p.push(new Nodo(invertir(temp)));
                    temp="";
                }
            }
        }return temp;
    }

    public int getRow(String f){
        int fila=0;
        //System.out.println("no term lenght "+s.noTerm.length);
        for(int i=0; i<s.noTerm.length;i++){

            if(f.equals(s.noTerm[i])){
                fila=i;
                i=s.noTerm.length;
            }
        }
        return fila;
    }

    public int getColumn(String c){
        int col=0;
        for(int i=0; i<s.termi.length;i++){
            if(c.equals(s.termi[i])){
                col=i;
                i=s.termi.length;
            }
        }
        return col;
    }

    public boolean inNoTerminals(String cad){
        boolean siEsta=false;
        int control=0;
        for (int i=0; i<s.noTerm.length; i++){
            //System.out.println("no term "+s.noTerm[i]);
            if(s.noTerm[i].equals(cad)){
                control++;
            }
            if(control==1) siEsta=true;
            else siEsta=false;
        }
        return siEsta;
    }

    public String invertir(String cad){
        String invertida="";
        for (int x=cad.length()-1;x>=0;x--)
            invertida = invertida + cad.charAt(x);
        return invertida;
    }

}
