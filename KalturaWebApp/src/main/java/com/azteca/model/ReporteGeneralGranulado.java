/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.azteca.model;

import java.util.ArrayList;
import java.util.List;
import org.primefaces.model.chart.CartesianChartModel;

/**
 *
 * @author Santa
 */
public class ReporteGeneralGranulado {
    private List<CatReporteXls> tabla;
    private List<CatReporteXlsEntry> tablagrafica;
    private List<CatReporteXls> tabla2;
    private CartesianChartModel graficaStorage;
    private CartesianChartModel graficaTiempoVisto;
    private CartesianChartModel graficaProductividad;
    
    public ReporteGeneralGranulado() {
        tabla= new ArrayList<>();
        tablagrafica= new ArrayList<>();
    }

    public ReporteGeneralGranulado(List<CatReporteXls> tabla) {
        this.tabla = tabla;
    }
    
    

    public ReporteGeneralGranulado(List<CatReporteXls> tabla, CartesianChartModel graficaStorage, CartesianChartModel graficaTiempoVisto) {
        this.tabla = tabla;
        this.graficaStorage = graficaStorage;
        this.graficaTiempoVisto = graficaTiempoVisto;
    }

    public ReporteGeneralGranulado(List<CatReporteXls> tabla, CartesianChartModel graficaStorage, CartesianChartModel graficaTiempoVisto, CartesianChartModel graficaProductividad) {
        this.tabla = tabla;
        this.graficaStorage = graficaStorage;
        this.graficaTiempoVisto = graficaTiempoVisto;
        this.graficaProductividad = graficaProductividad;
    }
    

    public List<CatReporteXlsEntry> getTablagrafica() {
        return tablagrafica;
    }

    public void setTablagrafica(List<CatReporteXlsEntry> tablagrafica) {
        this.tablagrafica = tablagrafica;
    }

    public List<CatReporteXls> getTabla2() {
        return tabla2;
    }

    public void setTabla2(List<CatReporteXls> tabla2) {
        this.tabla2 = tabla2;
    }

    
    
    public List<CatReporteXls> getTabla() {
        return tabla;
    }

    public void setTabla(List<CatReporteXls> tabla) {
        this.tabla = tabla;
    }

    public CartesianChartModel getGraficaStorage() {
        return graficaStorage;
    }

    public void setGraficaStorage(CartesianChartModel graficaStorage) {
        this.graficaStorage = graficaStorage;
    }

    public CartesianChartModel getGraficaTiempoVisto() {
        return graficaTiempoVisto;
    }

    public void setGraficaTiempoVisto(CartesianChartModel graficaTiempoVisto) {
        this.graficaTiempoVisto = graficaTiempoVisto;
    }

    public CartesianChartModel getGraficaProductividad() {
        return graficaProductividad;
    }

    public void setGraficaProductividad(CartesianChartModel graficaProductividad) {
        this.graficaProductividad = graficaProductividad;
    }
    
    
    
    
}
