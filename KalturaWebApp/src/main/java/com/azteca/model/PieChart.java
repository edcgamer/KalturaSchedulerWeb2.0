/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Santa
 */
public class PieChart {

    private String nombre;
    private List<Dato> datos;

    public void addDato(Dato dato) {
        datos.add(dato);
    }
    
    public void ordenarMayores(){
        if(datos.size()>=10){
            int[] arreglo= new int[datos.size()];
            int pos=0;
            for(Dato d:datos){
                arreglo[pos]=d.getValor().intValue();
                pos++;
            }
            Arrays.sort(arreglo);
        }
    }

    public void limpiaDatos() {
        datos = new ArrayList<>();
    }

    public PieChart(String nombre, List<Dato> datos) {
        this.nombre = nombre;
        this.datos = datos;
    }

    public PieChart() {
        datos = new ArrayList();
    }

    public PieChart(String nombre) {
        this.nombre = nombre;
        datos = new ArrayList();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Dato> getDatos() {
        return datos;
    }

    public void setDatos(List<Dato> datos) {
        this.datos = datos;
    }

}
