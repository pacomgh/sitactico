package com.AnalisisTokenLexico.util;

import com.AnalisisTokenLexico.main.GenerarTokens;

import java.util.ArrayList;

public class Stacks {

    private ArrayList lista, nt, prod, term;
    private GenerarTokens gt;
    public String [] noTerm, termi, arreProd, tkns, der;
    private Archivo ar;

    public Stacks() {
        nt = new ArrayList();
        prod = new ArrayList();
        term = new ArrayList();
        ar = new Archivo();
        //gt = new GenerarTokens();
        System.out.println("Selecciona el archivo para sepaar en estructuras");
        lista = ar.leerArchivo();
        separar(lista);
        terminales();
        toArreglo();
    }

    public void terminales(){
        String der="";
        ArrayList ar = new ArrayList();
        for(int i = 0; i<prod.size(); i++){
            for(int j = 0; j<nt.size(); j++)
                der = prod.get(i).toString().replace(nt.get(j).toString(), "");

            ar = split(' ', der);
            for (int h = 0; h<ar.size(); h++)
                if (ar == null || !term.contains(ar.get(h)))
                    term.add(ar.get(h).toString());
        }
        for (int k = 0; k<nt.size();k++)
            term.remove(nt.get(k).toString().trim());

        term.remove("");
        term.remove("vacio");
    }

    public void separar(ArrayList al){
        //System.out.println("Selecciona el archivo para separar en estructuras");
        String punto = ".";
        String guion = "->";
        String cad="";
        int start, end;
        for (int i = 0; i<al.size();i++){
            cad = al.get(i).toString();
            start = cad.indexOf(punto);
            end = cad.lastIndexOf(guion);
            if(nt==null)
                nt.add(cad.substring(start+1,end).trim());
            else if(!nt.contains(cad.substring(start+1,end).trim()))
                nt.add(cad.substring(start+1,end).trim());
            if(prod==null)
                prod.add(cad.substring(end+2,cad.length()).trim());
            else if(!prod.contains(cad.substring(end+2,cad.length()).trim()))
                prod.add(cad.substring(end+2,cad.length()).trim());
        }
    }

    public ArrayList split(char delimeter, String line){
        String word = "";
        ArrayList wordsArr = new ArrayList();
        ArrayList palabras;
        int k = 0;
        for(int i = 0; i <line.length(); i++){
            if(line.charAt(i) != delimeter){
                word+= line.charAt(i);
            }else{
                wordsArr.add(word);
                word = "";
                k++;
            }
        }
        wordsArr.add(word);
        palabras=new ArrayList();
        for(int j = 0; j <wordsArr.size(); j++){
            palabras.add(wordsArr.get(j));
        }
        return palabras;
    }

    public void toArreglo() {
        noTerm = new String[nt.size()];
        termi = new String[term.size()];
        arreProd = new String[lista.size()];
        der = new String[prod.size()];
        //tkns = new String[tt.size()];

        for (int i = 0; i<term.size();i++)
            termi[i]=term.get(i).toString();
        for (int j = 0; j<nt.size(); j++)
            noTerm[j]=nt.get(j).toString();
        for (int m = 0; m<lista.size(); m++)
            arreProd[m]=lista.get(m).toString();
        for (int n = 0; n<prod.size(); n++)
            der[n]=prod.get(n).toString();
        //for's para impresiones
        //for (int k = 0; k<noTerm.length;k++)
        //   System.out.println("no terminal: "+noTerm[k]);

        //for (int g = 0; g<termi.length; g++)
        //    System.out.println("terminal: "+termi[g]);

        //for (int n = 0; n<arreProd.length; n++)
        //    System.out.println("producciones: "+arreProd[n]);

        //for (int n = 0; n<tkns.length; n++) {
        //    System.out.println("producciones: " + tkns[n]);
        //}

    }
}
