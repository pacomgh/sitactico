package com.AnalisisTokenLexico.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class Archivo {

    private String archivo;

    public ArrayList leerArchivo() {
        JFileChooser chooser = new JFileChooser();
        ArrayList lista = new ArrayList();
        String line;
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            archivo = chooser.getSelectedFile().getAbsolutePath();
        }
        try {
            BufferedReader lector = new BufferedReader(new FileReader(chooser.getSelectedFile()));
            while ((line = lector.readLine()) != null) {
                lista.add(line);
            }
            lector.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public void imprimir(ArrayList todo) {
        for (int i = 0; i < todo.size(); i++) {
            System.out.println(todo.get(i).toString());
        }
    }
}
