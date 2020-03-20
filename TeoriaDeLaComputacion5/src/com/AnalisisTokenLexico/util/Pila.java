package com.AnalisisTokenLexico.util;

public class Pila {

    public Nodo tope;

    public Pila(){
        tope = null;
    }

    public boolean isEmpty(){
        return tope==null;
    }

    public void push(Nodo n){
        if(isEmpty())
            tope = n;
        else{
            n.sig=tope;
            tope=n;
        }
    }

    public Nodo pop(){
        Nodo temp = tope;
        if (isEmpty())
            System.out.println("No hay elementos para sacar");
        else
            tope=tope.sig;
        return temp;
    }

    public String top(){
        return tope.dato;
    }

    public void recorrer(){
        Nodo temp=tope;
        while(temp!=null){
            System.out.println(temp.dato);
            temp=temp.sig;
        }
    }
}
