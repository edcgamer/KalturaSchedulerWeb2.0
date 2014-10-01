/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.azteca.model;

import java.io.Serializable;

/**
 *
 * @author Santa
 */
public class CalculosTotales {
    String totalEntrys;
    String totalTamanio;
    String totalVisto;
    String totalTamanioUnitario;
    String totalVistoUnitario;
    String nombre;

    public CalculosTotales(String totalEntrys, String totalTamanio, String totalVisto, String totalTamanioUnitario, String totalVistoUnitario,String nombre) {
        this.totalEntrys = totalEntrys;
        this.totalTamanio = totalTamanio;
        this.totalVisto = totalVisto;
        this.totalTamanioUnitario = totalTamanioUnitario;
        this.totalVistoUnitario = totalVistoUnitario;
        this.nombre= nombre;
    }
    
    

    public CalculosTotales() {
    }

    public String getTotalEntrys() {
        return totalEntrys;
    }

    public void setTotalEntrys(String totalEntrys) {
        this.totalEntrys = totalEntrys;
    }

    public String getTotalTamanio() {
        return totalTamanio;
    }

    public void setTotalTamanio(String totalTamanio) {
        this.totalTamanio = totalTamanio;
    }

    public String getTotalVisto() {
        return totalVisto;
    }

    public void setTotalVisto(String totalVisto) {
        this.totalVisto = totalVisto;
    }

    public String getTotalTamanioUnitario() {
        return totalTamanioUnitario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTotalTamanioUnitario(String totalTamanioUnitario) {
        this.totalTamanioUnitario = totalTamanioUnitario;
    }

    public String getTotalVistoUnitario() {
        return totalVistoUnitario;
    }

    public void setTotalVistoUnitario(String totalVistoUnitario) {
        this.totalVistoUnitario = totalVistoUnitario;
    }
    
    
}
