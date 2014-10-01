/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marcosanta.service.impl;

import com.azteca.model.CatReporteXls;
import com.azteca.model.ReporteGeneralGranulado;
import com.azteca.persistence.entities.ComscoreConsumo;
import com.marcosanta.service.ComscoreService;
import com.marcosanta.service.ConsultasBDService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Santa
 */
public class ComscoreServiceImpl implements  ComscoreService,Serializable{
    
    @Autowired
    ConsultasBDService consultasBDService;

    @Override
    public ReporteGeneralGranulado reporteGlobalComscore(Date fechaInicial, Date fechaFinal,String tipo,String sitio,String fabrica) {
        List<CatReporteXls> listaReporte= new ArrayList<>();
        ChartSeries chartStorage = new ChartSeries();
        ChartSeries chartConsume = new ChartSeries();
        CartesianChartModel graficaStorage = new CartesianChartModel();
        CartesianChartModel graficaConsume = new CartesianChartModel();
        Map<Object, Number> mapaSerieTamanio = new HashMap<>();
        Map<Object, Number> mapaSerieConsume = new HashMap<>();
        List<ComscoreConsumo> listaConsumo= new ArrayList<>();
        if (fechaFinal != null && fechaInicial != null) {
            if (tipo.equals("global")) {
                listaConsumo = consultasBDService.findByFechaCorte(fechaInicial, fechaFinal);
            } else if (tipo.equals("sitio")) {
                listaConsumo = consultasBDService.findComscoreConsumoBySitioAndFechaCorte(sitio, fechaInicial, fechaFinal);
            } else if (tipo.equals("fabrica")) {
                listaConsumo = consultasBDService.findComscoreConsumoBySitioAndFabricaAndFechaCorte(sitio, fabrica, fechaInicial, fechaFinal);
            }
        } else {
            if (tipo.equals("global")) {
                listaConsumo = consultasBDService.findAllComscoreConsumo();
            }
            if (tipo.equals("sitio")) {
                listaConsumo = consultasBDService.findComscoreConsumoBySitio(sitio);
            }
            if (tipo.equals("fabrica")) {
                listaConsumo = consultasBDService.findByFabricaAndSitio(sitio,fabrica);
            }
        }
        String nombre="";
            for (ComscoreConsumo cc : listaConsumo) {
                
                if (tipo.equals("global")) {
                    nombre=cc.getSitio();
                }
                if (tipo.equals("sitio")) {
                    nombre=cc.getFabrica();
                }
                if (tipo.equals("fabrica")) {
                    nombre=cc.getNombre();
                }
                
                if (listaReporte.contains(new CatReporteXls(nombre))) {
                    CatReporteXls reportDummy = listaReporte.get(listaReporte.indexOf(new CatReporteXls(nombre)));
                    reportDummy.setMinVistos(reportDummy.getMinVistos().add(new BigDecimal(cc.getTrafico())));
                    reportDummy.setTamanio(reportDummy.getTamanio().add(new BigDecimal(cc.getTamanio())));
                    mapaSerieConsume.put(nombre, mapaSerieConsume.get(nombre).doubleValue()+cc.getTrafico());
                    mapaSerieTamanio.put(nombre, mapaSerieTamanio.get(nombre).doubleValue()+cc.getTamanio());
                } else {
                    
                    listaReporte.add(new CatReporteXls(nombre, new BigDecimal(cc.getTrafico()), new BigDecimal(cc.getTamanio())));
                    mapaSerieConsume.put(nombre, cc.getTrafico());
                    mapaSerieTamanio.put(nombre, cc.getTamanio());
                }
            }
        chartStorage.setData(mapaSerieTamanio);
        chartConsume.setData(mapaSerieConsume);
        graficaStorage.addSeries(chartStorage);
        graficaConsume.addSeries(chartConsume);
        ReporteGeneralGranulado repGenGra = new ReporteGeneralGranulado(listaReporte, graficaStorage, graficaConsume, null);
        return repGenGra;
    }

//    @Override
//    public ReporteGeneralGranulado reporteSitioComscore(Date fechaInicial, Date fechaFinal, String sitioSeleccionado) {
//        List<CatReporteXls> listaReporte= new ArrayList<>();
//        ChartSeries chartStorage = new ChartSeries();
//        ChartSeries chartConsume = new ChartSeries();
//        CartesianChartModel graficaStorage = new CartesianChartModel();
//        CartesianChartModel graficaConsume = new CartesianChartModel();
//        Map<Object, Number> mapaSerieTamanio = new HashMap<>();
//        Map<Object, Number> mapaSerieConsume = new HashMap<>();
//        List<ComscoreConsumo> listaConsumo;
//        if (fechaFinal != null && fechaInicial != null){
//            listaConsumo =consultasBDService.findComscoreConsumoBySitioAndFechaCorte(sitioSeleccionado,fechaInicial, fechaFinal);
//        }else{
//            listaConsumo= consultasBDService.findAllComscoreConsumo();
//        }
//            for (ComscoreConsumo cc : listaConsumo) {
//                if (listaReporte.contains(new CatReporteXls(cc.getSitio()))) {
//                    CatReporteXls reportDummy = listaReporte.get(listaReporte.indexOf(new CatReporteXls(cc.getSitio())));
//                    reportDummy.setMinVistos(reportDummy.getMinVistos().add(new BigDecimal(cc.getTrafico())));
//                    reportDummy.setTamanio(reportDummy.getTamanio().add(new BigDecimal(cc.getTamanio())));
//                    mapaSerieConsume.put(cc.getSitio(), mapaSerieConsume.get(cc.getSitio()).doubleValue()+cc.getTrafico());
//                    mapaSerieTamanio.put(cc.getSitio(), mapaSerieTamanio.get(cc.getSitio()).doubleValue()+cc.getTamanio());
//                } else {
//                    listaReporte.add(new CatReporteXls(cc.getSitio(), new BigDecimal(cc.getTrafico()), new BigDecimal(cc.getTamanio())));
//                    mapaSerieConsume.put(cc.getSitio(), cc.getTrafico());
//                    mapaSerieTamanio.put(cc.getSitio(), cc.getTamanio());
//                }
//            }
//        chartStorage.setData(mapaSerieTamanio);
//        chartConsume.setData(mapaSerieConsume);
//        graficaStorage.addSeries(chartStorage);
//        graficaConsume.addSeries(chartConsume);
//
//        ReporteGeneralGranulado repGenGra = new ReporteGeneralGranulado(listaReporte, graficaStorage, graficaConsume, null);
//        return repGenGra;
//    }
//
//    @Override
//    public ReporteGeneralGranulado reporteFabricaComscore(Date fechaInicial, Date fechaFinal, String fabricaSeleccionado) {
//        ReporteGeneralGranulado rgg = new ReporteGeneralGranulado();
//        return rgg;
//    }
    
     @Override
    public CatReporteXls calculoTotalReporteGlobal(List<CatReporteXls> reporteGlobal) {
        CatReporteXls totalReporte;
        Long minVistos = 0L;
        Double tamanio = 0.0;

        for (CatReporteXls crx : reporteGlobal) {
            minVistos += crx.getMinVistos().longValue();
            tamanio += crx.getTamanio().doubleValue();
        }
        totalReporte = new CatReporteXls(new BigDecimal(minVistos), new BigDecimal(tamanio), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0));
        return totalReporte;
    }
    
}
