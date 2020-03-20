package com.AnalisisTokenLexico.util;

public class Matriz {

    //private Stacks s = new Stacks();
    private int [][] matrizPred = {
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,3,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,4,0,0,5,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,6,0,0,0,0,0,7,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,10,0,0,0,0,8,9,0,0,0,0,0,0},
            {0,0,0,11,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,12,13,14,15,16,17},
    };

    public int buscaIndice(int f, int c) {
        int numero;
        return (numero = matrizPred[f][c]);
    }
    /*public void imprimirMatriz(){
        for(int i=0; i<s.noTerm.length;i++) {
            for (int j = 0; j < s.termi.length; j++)
                System.out.print("| " + matrizPred[i][j]);
            System.out.println();
        }
    }*/
}
