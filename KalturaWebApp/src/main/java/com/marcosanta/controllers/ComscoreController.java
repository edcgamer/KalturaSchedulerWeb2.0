/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marcosanta.controllers;

import com.azteca.model.CatReporteXls;
import com.azteca.model.ReporteGeneralGranulado;
import com.azteca.persistence.entities.ComscoreConsumo;
import com.azteca.persistence.entities.ComscoreFabrica;
import com.azteca.persistence.entities.ComscoreSitio;
import com.marcosanta.service.ComscoreService;
import com.marcosanta.service.ConsultasBDService;
import com.marcosanta.service.impl.ComscoreServiceImpl;
import com.marcosanta.util.FacesUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Santa
 */
@Controller("comscoreController")
@Scope("session")
public class ComscoreController {
    
    private List<ComscoreSitio> listaSitios;
    private List<ComscoreFabrica> listaFabricas;
    private String sitioSeleccionado;
    private String FabricaSeleccionada;
    private List<ComscoreConsumo> listaConsumo;
    private Date fechaInicio;
    private Date fechaFinal;
    private Date fechaInicioView;
    private Date fechaFinalView;
    private Date hoy;
    private ReporteGeneralGranulado reporteGranuladoGlobal;
    private ReporteGeneralGranulado reporteGranuladositio;
    private ReporteGeneralGranulado reporteGranuladoGFabrica;
    private CatReporteXls reporteGlobalTotal;
    private CatReporteXls reporteSitioTotal;
    private CatReporteXls reporteFabricaTotal;
    private boolean graficaActiva;
    private String ultimaFechaConsumida;
    
    @Autowired
    ConsultasBDService consultasBDService;
    
    @Autowired
    ComscoreService comscoreService;
    
    @PostConstruct
    public void init() {
        ultimaFechaConsumida=consultasBDService.obtenerMaxDateActualizadoComscore().toString();
        graficaActiva=false;
        hoy= new Date();
        fechaFinal= null;
        fechaInicio= null;
        sitioSeleccionado="";
        FabricaSeleccionada="";
        listaSitios= consultasBDService.findAllComscoreSitio();
        listaFabricas= new ArrayList<>();
        generaReporteGlobal();
    }
    
    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
        for (Row row : sheet) {
            for (Cell cell : row) {
                cell.setCellValue(cell.getStringCellValue().toUpperCase());
                cell.setCellStyle(style);
            }
        }
    }
    
    public void generaReporteGlobal(){
        reporteGranuladoGlobal= comscoreService.reporteGlobalComscore(fechaInicio, fechaFinal,"global","","");
        reporteGlobalTotal= comscoreService.calculoTotalReporteGlobal(reporteGranuladoGlobal.getTabla());
    }
    
    public void generaReporteSitio(){
        reporteGranuladositio= comscoreService.reporteGlobalComscore(fechaInicio, fechaFinal,"sitio",sitioSeleccionado,"");
        reporteSitioTotal= comscoreService.calculoTotalReporteGlobal(reporteGranuladositio.getTabla());
    }
    
    public void generaReporteFabrica(){
        System.out.println("entreee");
        reporteGranuladoGFabrica= comscoreService.reporteGlobalComscore( fechaInicio, fechaFinal,"fabrica",sitioSeleccionado,FabricaSeleccionada);
        reporteFabricaTotal= comscoreService.calculoTotalReporteGlobal(reporteGranuladoGFabrica.getTabla());
    }
    
    public void botonFiltrosAction() {
        fechaInicio=fechaInicioView;
        fechaFinal=fechaFinalView;
        generaReporteGlobal();
        if(!sitioSeleccionado.equals("")){
            generaReporteSitio();
        }
        if(!FabricaSeleccionada.equals("")){
            generaReporteFabrica();
        }
    }

    public boolean isGraficaActiva() {
        return graficaActiva;
    }

    public void setGraficaActiva(boolean graficaActiva) {
        this.graficaActiva = graficaActiva;
    }
    
    
    
    public void activaGrafica(){
        graficaActiva=true;
    }
    
    public void  activaTablas(){
        graficaActiva=false;
    }
    
    public void verificaFechaMenor() {
        if (fechaInicioView != null&&fechaFinalView!=null) {
            if (fechaInicioView.getTime() > fechaFinalView.getTime()) {
                FacesUtils.addWarningMessage("inicioAlert", "Fecha incorrecta(Fecha inicio deberia ser menor ala final)");
            }
        }
    }
    
    public void verificaFechaMayor() {
        if (fechaFinalView != null&&fechaInicioView!=null) {
            if (fechaFinalView.getTime() < fechaInicioView.getTime()) {
                FacesUtils.addWarningMessage("finalAlert", "Fecha incorrecta(Fecha final deberia ser mayor ala inicial)");
            }
        }
    }
    
    public void seleccionSitio() {
        if (!sitioSeleccionado.equals("")) {
            listaFabricas = consultasBDService.findComscoreSitioByNombre(sitioSeleccionado).get(0).getListaComscoreFabricas();
            generaReporteSitio();
        }
    }

    public void seleccionFabrica() {
        if (!FabricaSeleccionada.equals("")) {
            generaReporteFabrica();
        }
    }

    public List<ComscoreSitio> getListaSitios() {
        return listaSitios;
    }

    public void setListaSitios(List<ComscoreSitio> listaSitios) {
        this.listaSitios = listaSitios;
    }

    public List<ComscoreFabrica> getListaFabricas() {
        return listaFabricas;
    }

    public void setListaFabricas(List<ComscoreFabrica> listaFabricas) {
        this.listaFabricas = listaFabricas;
    }

    public String getSitioSeleccionado() {
        return sitioSeleccionado;
    }

    public void setSitioSeleccionado(String sitioSeleccionado) {
        this.sitioSeleccionado = sitioSeleccionado;
    }

    public String getFabricaSeleccionada() {
        return FabricaSeleccionada;
    }

    public void setFabricaSeleccionada(String FabricaSeleccionada) {
        this.FabricaSeleccionada = FabricaSeleccionada;
    }

    public List<ComscoreConsumo> getListaConsumo() {
        return listaConsumo;
    }

    public void setListaConsumo(List<ComscoreConsumo> listaConsumo) {
        this.listaConsumo = listaConsumo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public ReporteGeneralGranulado getReporteGranuladoGlobal() {
        return reporteGranuladoGlobal;
    }

    public void setReporteGranuladoGlobal(ReporteGeneralGranulado reporteGranuladoGlobal) {
        this.reporteGranuladoGlobal = reporteGranuladoGlobal;
    }

    public ReporteGeneralGranulado getReporteGranuladositio() {
        return reporteGranuladositio;
    }

    public void setReporteGranuladositio(ReporteGeneralGranulado reporteGranuladositio) {
        this.reporteGranuladositio = reporteGranuladositio;
    }

    public ReporteGeneralGranulado getReporteGranuladoGFabrica() {
        return reporteGranuladoGFabrica;
    }

    public void setReporteGranuladoGFabrica(ReporteGeneralGranulado reporteGranuladoGFabrica) {
        this.reporteGranuladoGFabrica = reporteGranuladoGFabrica;
    }

    public Date getFechaInicioView() {
        return fechaInicioView;
    }

    public void setFechaInicioView(Date fechaInicioView) {
        this.fechaInicioView = fechaInicioView;
    }

    public Date getFechaFinalView() {
        return fechaFinalView;
    }

    public void setFechaFinalView(Date fechaFinalView) {
        this.fechaFinalView = fechaFinalView;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    public CatReporteXls getReporteGlobalTotal() {
        return reporteGlobalTotal;
    }

    public void setReporteGlobalTotal(CatReporteXls reporteGlobalTotal) {
        this.reporteGlobalTotal = reporteGlobalTotal;
    }

    public CatReporteXls getReporteSitioTotal() {
        return reporteSitioTotal;
    }

    public void setReporteSitioTotal(CatReporteXls reporteSitioTotal) {
        this.reporteSitioTotal = reporteSitioTotal;
    }

    public CatReporteXls getReporteFabricaTotal() {
        return reporteFabricaTotal;
    }

    public void setReporteFabricaTotal(CatReporteXls reporteFabricaTotal) {
        this.reporteFabricaTotal = reporteFabricaTotal;
    }

    public String getUltimaFechaConsumida() {
        return ultimaFechaConsumida;
    }

    public void setUltimaFechaConsumida(String ultimaFechaConsumida) {
        this.ultimaFechaConsumida = ultimaFechaConsumida;
    }
    
    
    
    
}
