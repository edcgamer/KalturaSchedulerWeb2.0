/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.azteca.model;

/**
 *
 * @author Santa
 */
public class Dato {
    private String nombre;
    private Number valor;

    public String getNombre() {
        return nombre;
    }

    public Dato(String nombre, Number valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Number getValor() {
        return valor;
    }

    public void setValor(Number valor) {
        this.valor = valor;
    }

    public Dato() {

    }
    
    
}
