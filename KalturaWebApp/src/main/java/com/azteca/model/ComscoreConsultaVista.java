/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.azteca.model;

import java.math.BigDecimal;

/**
 *
 * @author Santa
 */
public class ComscoreConsultaVista {
    private String nombre;
    private String sitio;
    private String fabrica;
    private double tamanio;
    private double trafico;
    private BigDecimal fabricaDeci;
    private BigDecimal tamanioDeci;
    private BigDecimal traficoDeci;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getFabrica() {
        return fabrica;
    }

    public void setFabrica(String fabrica) {
        this.fabrica = fabrica;
    }

    public double getTamanio() {
        return tamanio;
    }

    public void setTamanio(double tamanio) {
        this.tamanio = tamanio;
    }

    public double getTrafico() {
        return trafico;
    }

    public void setTrafico(double trafico) {
        this.trafico = trafico;
    }

    public BigDecimal getFabricaDeci() {
        return fabricaDeci;
    }

    public void setFabricaDeci(BigDecimal fabricaDeci) {
        this.fabricaDeci = fabricaDeci;
    }

    public BigDecimal getTamanioDeci() {
        return tamanioDeci;
    }

    public void setTamanioDeci(BigDecimal tamanioDeci) {
        this.tamanioDeci = tamanioDeci;
    }

    public BigDecimal getTraficoDeci() {
        return traficoDeci;
    }

    public void setTraficoDeci(BigDecimal traficoDeci) {
        this.traficoDeci = traficoDeci;
    }

    public ComscoreConsultaVista() {
    }

    public ComscoreConsultaVista(String nombre, String sitio, String fabrica, double tamanio, double trafico, BigDecimal fabricaDeci, BigDecimal tamanioDeci, BigDecimal traficoDeci) {
        this.nombre = nombre;
        this.sitio = sitio;
        this.fabrica = fabrica;
        this.tamanio = tamanio;
        this.trafico = trafico;
        this.fabricaDeci = fabricaDeci;
        this.tamanioDeci = tamanioDeci;
        this.traficoDeci = traficoDeci;
    }
    
    
    
}
