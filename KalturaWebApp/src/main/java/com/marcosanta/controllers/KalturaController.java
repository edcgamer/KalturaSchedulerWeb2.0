/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosanta.controllers;

import com.azteca.model.CatReporteXls;
import com.azteca.model.ReporteGeneralGranulado;
import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.KalturaEntry;
import com.azteca.persistence.entities.KalturaFabrica;
import com.azteca.persistence.entities.KalturaPrograma;
import com.azteca.persistence.entities.KalturaUnidad;
import com.azteca.persistence.entities.SistemaSubReporte;
import com.azteca.persistence.entities.Usuario;
import com.marcosanta.service.ConsultasBDService;
import com.marcosanta.service.KalturaService;
import com.marcosanta.util.FacesUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.model.chart.CartesianChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Santa
 */
@Controller("kalturaController")
@Scope("session")
public class KalturaController implements Serializable {

    @Autowired
    KalturaService kalturaService;

    @Autowired
    ConsultasBDService consultasBDService;

    private List<KalturaCuenta> listaCuentas;
    private List<KalturaUnidad> listaUnidades;
    private List<KalturaFabrica> listaFabricas;
    private List<KalturaPrograma> listaProgramas;
    private List<KalturaEntry> listaEntry;
    private CatReporteXls reporteGlobalTotal;
    private CatReporteXls reporteCuentaTotal;
    private CatReporteXls reporteUnidadTotal;
    private CatReporteXls reporteFabricaTotal;
    private CatReporteXls reporteProgramaTotal;
    private String unidadSeleccionada;
    private String cuentaSeleccionada;
    private String fabricaSeleccionada;
    private String programaSeleccionado;
    private ReporteGeneralGranulado repGenGraGlobal;
    private ReporteGeneralGranulado repGenGraCuenta;
    private ReporteGeneralGranulado repGenGraFabrica;
    private ReporteGeneralGranulado repGenGraUnidad;
    private ReporteGeneralGranulado repGenGraPrograma;
    private ReporteGeneralGranulado reporteTop;
    private boolean mostrarTop;
    private String valorUnitarioTam = "";
    private String valorUnitarioMIN = "";
    private String valorUnitarioTamView = "";
    private String valorUnitarioMINView = "";
    private Date fechaInicio;
    private Date fechafin;
    private Date fechaInicioView;
    private Date fechafinView;
    private Date fechaInicioCrea;
    private Date fechafinCrea;
    private Date fechaInicioViewCrea;
    private Date fechafinViewCrea;
    private Date hoy;
    private boolean graficaActiva;
    private String contrasena;
    private boolean mostrarNoVistos;
    private boolean mostrarNoVistosView;
    private boolean mostrarRangoCreacion;
    private boolean busquedaActiva;
    private int tipoVideos;
    private KalturaCuenta kalturaCuenta;
    private String busquedaCategoria;
    private ReporteGeneralGranulado reporteBusquedaCategorias;
    private String ultimaFechaConsumida;

    @PostConstruct
    public void init() {
        ultimaFechaConsumida=consultasBDService.obtenerMaxDateActualizado().toString();
        busquedaActiva=false;
        busquedaCategoria="";
        mostrarRangoCreacion=false;
        tipoVideos=0;
        this.reporteTop= new ReporteGeneralGranulado();
        mostrarTop=false;
        this.hoy = new Date();
        mostrarNoVistos=false;
        mostrarNoVistosView=false;
        this.valorUnitarioMIN = "0.0015";
        this.valorUnitarioTam = "0.17";
        this.valorUnitarioMINView = "0.0015";
        this.valorUnitarioTamView = "0.17";
        fechaInicio = null;
        fechafin = null;
        fechaInicioView = null;
        fechafinView = null;
        fechaInicioCrea = null;
        fechafinCrea = null;
        fechaInicioViewCrea = null;
        fechafinViewCrea = null;
        graficaActiva = false;
        fechaInicio = null;
        fechafin = null;
        unidadSeleccionada = "";
        fabricaSeleccionada = "";
        cuentaSeleccionada = "";
        programaSeleccionado = "";
        listaCuentas = consultasBDService.findAllCuenta();
        listaUnidades = new ArrayList<>();
        listaFabricas = new ArrayList<>();
        listaProgramas = new ArrayList<>();
        listaEntry = new ArrayList<>();
        repGenGraGlobal = new ReporteGeneralGranulado();
        repGenGraCuenta = new ReporteGeneralGranulado();
        repGenGraUnidad = new ReporteGeneralGranulado();
        repGenGraFabrica = new ReporteGeneralGranulado();
        repGenGraPrograma = new ReporteGeneralGranulado();
        reporteBusquedaCategorias= new ReporteGeneralGranulado();
        generaReporteGlobal(0);
    }
    
    public void mostrarRangoDeCreacion(){
        mostrarRangoCreacion=mostrarNoVistosView;
    }
    
    public void generaTop(){
        this.reporteTop=kalturaService.topTen(fechaInicio, fechafin, valorUnitarioTam, valorUnitarioMIN,0);
        mostrarTop=true;
    }
    
    public void buscaCategorias(){
        reporteBusquedaCategorias=kalturaService.FindCategoryByType(busquedaCategoria.toUpperCase());
    }

    public void verificaFechaMenor() {
        if (fechaInicioView != null&&fechafinView!=null) {
            if (fechaInicioView.getTime() > fechafinView.getTime()) {
                FacesUtils.addWarningMessage("inicioAlert", "Fecha incorrecta(Fecha inicio deberia ser menor ala final)");
            }
        }
    }
    
    public void verificaFechaMenorCrea() {
        if (fechaInicioViewCrea != null&&fechafinViewCrea!=null) {
            if (fechaInicioViewCrea.getTime() > fechafinViewCrea.getTime()) {
                FacesUtils.addWarningMessage("inicioAlertCrea", "Fecha incorrecta(Fecha inicio deberia ser menor ala final)");
            }
        }
    }

    public void verificaFechaMayor() {
        if (fechafinView != null&&fechaInicioView!=null) {
            if (fechafinView.getTime() < fechaInicioView.getTime()) {
                FacesUtils.addWarningMessage("finalAlert", "Fecha incorrecta(Fecha final deberia ser mayor ala inicial)");
            }
        }
    }
    
     public void verificaFechaMayorCrea() {
        if (fechafinViewCrea != null&&fechaInicioViewCrea!=null) {
            if (fechafinViewCrea.getTime() < fechaInicioViewCrea.getTime()) {
                FacesUtils.addWarningMessage("finalAlertCrea", "Fecha incorrecta(Fecha final deberia ser mayor ala inicial)");
            }
        }
    }

    public void botonFiltrosAction() {
        fechaInicioCrea= fechaInicioViewCrea;
        fechafinCrea=fechafinViewCrea;
        System.out.println(fechaInicioCrea);
        System.out.println(fechafinCrea);
        mostrarTop=false;
        fechaInicio=fechaInicioView;
        fechafin=fechafinView;
        mostrarNoVistos= mostrarNoVistosView;
        valorUnitarioMIN=valorUnitarioMINView;
        valorUnitarioTam=valorUnitarioTamView;
        if(mostrarNoVistos)
            tipoVideos=1;
        else{
            tipoVideos=0;
        }
        generaReporteGlobal(tipoVideos);
        if (!programaSeleccionado.equals("")) {
            seleccionPrograma(true,tipoVideos);
        }
        if (!unidadSeleccionada.equals("")) {
            seleccionUnidad(true,tipoVideos);
        }
        if (!fabricaSeleccionada.equals("")) {
            seleccionFabrica(true,tipoVideos);
        }
        if (!cuentaSeleccionada.equals("")) {
            seleccionCuenta(true,tipoVideos);
        }
    }

    public void activaGrafica() {
        graficaActiva = true;
        busquedaActiva=false;
    }
    
    public void activaBusqueda(){
        busquedaActiva=true;
    }

    public void activaTablas() {
        busquedaActiva=false;
        graficaActiva = false;
    }

    public void seleccionUnidad(boolean filtrosAplicados,int mostrarVideos) {
        if (!filtrosAplicados) {
            this.repGenGraFabrica = new ReporteGeneralGranulado();
            this.repGenGraPrograma = new ReporteGeneralGranulado();
            this.reporteFabricaTotal = new CatReporteXls();
            this.reporteProgramaTotal = new CatReporteXls();
            this.fabricaSeleccionada = "";
            this.programaSeleccionado = "";
            this.listaFabricas = new ArrayList<>();
            this.listaProgramas = new ArrayList<>();
        }
        if (!unidadSeleccionada.equals("")) {
            listaFabricas = consultasBDService.findUnidadByNombre(unidadSeleccionada).get(0).getListaFabricas();
            if (mostrarNoVistos) {
                if (fechaInicio != null && fechafin != null) {
                    this.repGenGraUnidad = kalturaService.calculoReporteUnidadVistos(fechaInicioCrea,fechafinCrea,fechaInicio, fechafin, valorUnitarioTam, valorUnitarioMIN, "fabrica", cuentaSeleccionada, unidadSeleccionada, null, null,kalturaCuenta);
                }else{
                    this.repGenGraUnidad = new ReporteGeneralGranulado(new ArrayList<CatReporteXls>(), new CartesianChartModel(),  new CartesianChartModel(),  new CartesianChartModel());
                }
            } else {
                this.repGenGraUnidad = kalturaService.calculoReporteGlobal(this.fechaInicio, this.fechafin, this.valorUnitarioTam, this.valorUnitarioMIN, "fabrica", this.cuentaSeleccionada, this.unidadSeleccionada, null, mostrarVideos);
            }
            this.reporteUnidadTotal = kalturaService.calculoTotalReporteGlobal(this.repGenGraUnidad.getTabla(), valorUnitarioMIN, valorUnitarioTam);
        }
    }

    public void generaReporteGlobal(int mostrarVideos) {
        if (mostrarNoVistos) {
            if (fechaInicio != null && fechafin != null) {
                this.repGenGraGlobal = kalturaService.calculoReporteGlobalNoVistos(fechaInicioCrea,fechafinCrea,this.fechaInicio, this.fechafin, this.valorUnitarioTam, this.valorUnitarioMIN, "cuenta", null, null, null, null);
            }else{
                    this.repGenGraGlobal = new ReporteGeneralGranulado(new ArrayList<CatReporteXls>(), new CartesianChartModel(),  new CartesianChartModel(),  new CartesianChartModel());
            }
        } else {
            this.repGenGraGlobal = kalturaService.calculoReporteGlobal(this.fechaInicio, this.fechafin, this.valorUnitarioTam, this.valorUnitarioMIN, "cuenta", null, null, null, mostrarVideos);
        }
        reporteGlobalTotal = kalturaService.calculoTotalReporteGlobal(this.repGenGraGlobal.getTabla(), valorUnitarioMIN, valorUnitarioTam);
    }

    public void seleccionCuenta(boolean filtrosAplicados,int mostrarVideos) {
        kalturaCuenta= consultasBDService.findCuentaByNombre(cuentaSeleccionada).get(0);
        if (!filtrosAplicados) {
            this.repGenGraFabrica = new ReporteGeneralGranulado();
            this.repGenGraUnidad = new ReporteGeneralGranulado();
            this.repGenGraPrograma = new ReporteGeneralGranulado();
            this.reporteUnidadTotal = new CatReporteXls();
            this.reporteFabricaTotal = new CatReporteXls();
            this.reporteProgramaTotal = new CatReporteXls();
            this.fabricaSeleccionada = "";
            this.programaSeleccionado = "";
            this.unidadSeleccionada = "";
            this.listaUnidades = new ArrayList<>();
            this.listaFabricas = new ArrayList<>();
            this.listaProgramas = new ArrayList<>();
        }
        if (!cuentaSeleccionada.equals("")) {
            listaUnidades = consultasBDService.findCuentaByNombre(cuentaSeleccionada).get(0).getListaUnidad();
            if (mostrarNoVistos) {
                if (fechaInicio != null && fechafin != null) {
                    this.repGenGraCuenta = kalturaService.calculoReporteCuentaNoVistos(fechaInicioCrea,fechafinCrea,fechaInicio, fechafin, valorUnitarioTam, valorUnitarioMIN, "fabrica", cuentaSeleccionada, null, null, null,kalturaCuenta);
                }else{
                    this.repGenGraCuenta = new ReporteGeneralGranulado(new ArrayList<CatReporteXls>(), new CartesianChartModel(),  new CartesianChartModel(),  new CartesianChartModel());
                }
            } else {
                this.repGenGraCuenta = kalturaService.calculoReporteGlobal(this.fechaInicio, this.fechafin, this.valorUnitarioTam, this.valorUnitarioMIN, "unidad", this.cuentaSeleccionada, null, null, mostrarVideos);
            } 
            this.reporteCuentaTotal = kalturaService.calculoTotalReporteGlobal(this.repGenGraCuenta.getTabla(), valorUnitarioMIN, valorUnitarioTam);
        }

    }

    public void seleccionFabrica(boolean filtrosActivado,int mostrarVideos) {
        if (!filtrosActivado) {
            this.repGenGraPrograma = new ReporteGeneralGranulado();
            this.reporteProgramaTotal = new CatReporteXls();
            this.programaSeleccionado = "";
            this.listaProgramas = new ArrayList<>();
        }
        if (!fabricaSeleccionada.equals("")) {
            listaProgramas = consultasBDService.findFabricaByNombre(fabricaSeleccionada).get(0).getListaProgramas();
            if (mostrarNoVistos) {
                if (fechaInicio != null && fechafin != null) {
                    this.repGenGraFabrica = kalturaService.calculoReportefabricaoVistos(fechaInicioCrea,fechafinCrea,fechaInicio, fechafin, valorUnitarioTam, valorUnitarioMIN, "fabrica", cuentaSeleccionada, unidadSeleccionada, fabricaSeleccionada, null,kalturaCuenta);
                }else{
                    this.repGenGraFabrica = new ReporteGeneralGranulado(new ArrayList<CatReporteXls>(), new CartesianChartModel(),  new CartesianChartModel(),  new CartesianChartModel());
                }
            } else {
                this.repGenGraFabrica = kalturaService.calculoReporteGlobal(this.fechaInicio, this.fechafin, this.valorUnitarioTam, this.valorUnitarioMIN, "programa", this.cuentaSeleccionada, null, this.fabricaSeleccionada, mostrarVideos);
            }
            this.reporteFabricaTotal = kalturaService.calculoTotalReporteGlobal(this.repGenGraFabrica.getTabla(), valorUnitarioMIN, valorUnitarioTam);
        }
    }
//

    public void seleccionPrograma(boolean filtrosActivado,int mostrarVideos) {
        this.repGenGraPrograma = new ReporteGeneralGranulado();
        if (mostrarNoVistos) {
            if (fechaInicio != null && fechafin != null) {
                this.repGenGraPrograma = kalturaService.calculoReporteProgramaNoVistos(fechaInicioCrea,fechafinCrea,fechaInicio, fechafin, valorUnitarioTam, valorUnitarioMIN, "programa", cuentaSeleccionada, unidadSeleccionada, fabricaSeleccionada, programaSeleccionado,kalturaCuenta);
            } else {
                this.repGenGraPrograma = new ReporteGeneralGranulado(new ArrayList<CatReporteXls>(), new CartesianChartModel(), new CartesianChartModel(), new CartesianChartModel());
            }
        } else {
            repGenGraPrograma = kalturaService.reporteEntrysGranulado(fechaInicio,
                    fechafin, valorUnitarioTam, valorUnitarioMIN, "",
                    cuentaSeleccionada, unidadSeleccionada, fabricaSeleccionada, programaSeleccionado, mostrarVideos);
        }
        this.reporteProgramaTotal = kalturaService.calculoTotalReporteGlobal(repGenGraPrograma.getTabla(), valorUnitarioMIN, valorUnitarioTam);
        this.reporteProgramaTotal.setTotalEntrys(new BigDecimal(repGenGraPrograma.getTabla().size()));
    }
    
    public void cambiaContrasena(String username){
        Usuario usuarioMod = this.consultasBDService.findUsuarioByUsername(username);
        String crypt = DigestUtils.sha256Hex(contrasena);
        usuarioMod.setPw(crypt);
        this.consultasBDService.saveUser(usuarioMod);
    }

    

    public String replaceAcentos(String cad) {
        return cad;
    }
//    
//    public String formatoNumero(Double numero){
//        return  DecimalFormat("#.##").format(numero) + "";
//    }
//

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
    


    public boolean renderedTabReporteUnidad() {
        if (repGenGraUnidad.getTabla().size() > 0 && !unidadSeleccionada.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean renderedTabReporteCuenta() {
        if (repGenGraCuenta.getTabla().size() > 0 && !cuentaSeleccionada.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean renderedTabReporteGlobal() {
        if (this.repGenGraGlobal.getTabla().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean renderedTabReporteFabrica() {
        if (repGenGraFabrica.getTabla().size() > 0 && !fabricaSeleccionada.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean renderedTabReportePrograma() {
        if (repGenGraPrograma.getTabla().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public CatReporteXls getReporteUnidadTotal() {
        return reporteUnidadTotal;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setReporteUnidadTotal(CatReporteXls reporteUnidadTotal) {
        this.reporteUnidadTotal = reporteUnidadTotal;
    }

    public CatReporteXls getReporteFabricaTotal() {
        return reporteFabricaTotal;
    }

    public void setReporteFabricaTotal(CatReporteXls reporteFabricaTotal) {
        this.reporteFabricaTotal = reporteFabricaTotal;
    }

    public CatReporteXls getReporteProgramaTotal() {
        return reporteProgramaTotal;
    }

    public void setReporteProgramaTotal(CatReporteXls reporteProgramaTotal) {
        this.reporteProgramaTotal = reporteProgramaTotal;
    }

    public ReporteGeneralGranulado getRepGenGraFabrica() {
        return repGenGraFabrica;
    }

    public void setRepGenGraFabrica(ReporteGeneralGranulado repGenGraFabrica) {
        this.repGenGraFabrica = repGenGraFabrica;
    }

    public ReporteGeneralGranulado getRepGenGraUnidad() {
        return repGenGraUnidad;
    }

    public void setRepGenGraUnidad(ReporteGeneralGranulado repGenGraUnidad) {
        this.repGenGraUnidad = repGenGraUnidad;
    }

    public ReporteGeneralGranulado getRepGenGraPrograma() {
        return repGenGraPrograma;
    }

    public void setRepGenGraPrograma(ReporteGeneralGranulado repGenGraPrograma) {
        this.repGenGraPrograma = repGenGraPrograma;
    }

    public String getValorUnitarioTam() {
        return valorUnitarioTam;
    }

    public void setValorUnitarioTam(String valorUnitarioTam) {
        this.valorUnitarioTam = valorUnitarioTam;
    }

    public String getValorUnitarioMIN() {
        return valorUnitarioMIN;
    }

    public void setValorUnitarioMIN(String valorUnitarioMIN) {
        this.valorUnitarioMIN = valorUnitarioMIN;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    public boolean isGraficaActiva() {
        return graficaActiva;
    }
//

    public void setGraficaActiva(boolean graficaActiva) {
        this.graficaActiva = graficaActiva;
    }

    public List<KalturaCuenta> getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(List<KalturaCuenta> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    public List<KalturaUnidad> getListaUnidades() {
        return listaUnidades;
    }

    public void setListaUnidades(List<KalturaUnidad> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public List<KalturaFabrica> getListaFabricas() {
        return listaFabricas;
    }

    public void setListaFabricas(List<KalturaFabrica> listaFabricas) {
        this.listaFabricas = listaFabricas;
    }

    public List<KalturaPrograma> getListaProgramas() {
        return listaProgramas;
    }

    public void setListaProgramas(List<KalturaPrograma> listaProgramas) {
        this.listaProgramas = listaProgramas;
    }

    public List<KalturaEntry> getListaEntry() {
        return listaEntry;
    }

    public void setListaEntry(List<KalturaEntry> listaEntry) {
        this.listaEntry = listaEntry;
    }

    public String getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    public void setUnidadSeleccionada(String unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

    public String getCuentaSeleccionada() {
        return cuentaSeleccionada;
    }

    public void setCuentaSeleccionada(String cuentaSeleccionada) {
        this.cuentaSeleccionada = cuentaSeleccionada;
    }

    public String getFabricaSeleccionada() {
        return fabricaSeleccionada;
    }

    public void setFabricaSeleccionada(String fabricaSeleccionada) {
        this.fabricaSeleccionada = fabricaSeleccionada;
    }

    public String getProgramaSeleccionado() {
        return programaSeleccionado;
    }

    public void setProgramaSeleccionado(String programaSeleccionado) {
        this.programaSeleccionado = programaSeleccionado;
    }

    public CatReporteXls getReporteGlobalTotal() {
        return reporteGlobalTotal;
    }

    public void setReporteGlobalTotal(CatReporteXls reporteGlobalTotal) {
        this.reporteGlobalTotal = reporteGlobalTotal;
    }

    public ReporteGeneralGranulado getRepGenGraGlobal() {
        return repGenGraGlobal;
    }

    public void setRepGenGraGlobal(ReporteGeneralGranulado repGenGraGlobal) {
        this.repGenGraGlobal = repGenGraGlobal;
    }

    public CatReporteXls getReporteCuentaTotal() {
        return reporteCuentaTotal;
    }

    public void setReporteCuentaTotal(CatReporteXls reporteCuentaTotal) {
        this.reporteCuentaTotal = reporteCuentaTotal;
    }

    public ReporteGeneralGranulado getRepGenGraCuenta() {
        return repGenGraCuenta;
    }

    public void setRepGenGraCuenta(ReporteGeneralGranulado repGenGraCuenta) {
        this.repGenGraCuenta = repGenGraCuenta;
    }

    public ReporteGeneralGranulado getReporteTop() {
        return reporteTop;
    }

    public void setReporteTop(ReporteGeneralGranulado reporteTop) {
        this.reporteTop = reporteTop;
    }

    public boolean isMostrarTop() {
        return mostrarTop;
    }

    public void setMostrarTop(boolean mostrarTop) {
        this.mostrarTop = mostrarTop;
    }

    public boolean isMostrarNoVistos() {
        return mostrarNoVistos;
    }

    public void setMostrarNoVistos(boolean mostrarNoVistos) {
        this.mostrarNoVistos = mostrarNoVistos;
    }

    public int getTipoVideos() {
        return tipoVideos;
    }

    public void setTipoVideos(int tipoVideos) {
        this.tipoVideos = tipoVideos;
    }

    public String getValorUnitarioTamView() {
        return valorUnitarioTamView;
    }

    public void setValorUnitarioTamView(String valorUnitarioTamView) {
        this.valorUnitarioTamView = valorUnitarioTamView;
    }

    public String getValorUnitarioMINView() {
        return valorUnitarioMINView;
    }

    public void setValorUnitarioMINView(String valorUnitarioMINView) {
        this.valorUnitarioMINView = valorUnitarioMINView;
    }

    public Date getFechaInicioView() {
        return fechaInicioView;
    }

    public void setFechaInicioView(Date fechaInicioView) {
        this.fechaInicioView = fechaInicioView;
    }

    public Date getFechafinView() {
        return fechafinView;
    }

    public void setFechafinView(Date fechafinView) {
        this.fechafinView = fechafinView;
    }

    public boolean isMostrarNoVistosView() {
        return mostrarNoVistosView;
    }

    public void setMostrarNoVistosView(boolean mostrarNoVistosView) {
        this.mostrarNoVistosView = mostrarNoVistosView;
    }

    public boolean isMostrarRangoCreacion() {
        return mostrarRangoCreacion;
    }

    public void setMostrarRangoCreacion(boolean mostrarRangoCreacion) {
        this.mostrarRangoCreacion = mostrarRangoCreacion;
    }

    public Date getFechaInicioCrea() {
        return fechaInicioCrea;
    }

    public void setFechaInicioCrea(Date fechaInicioCrea) {
        this.fechaInicioCrea = fechaInicioCrea;
    }

    public Date getFechafinCrea() {
        return fechafinCrea;
    }

    public void setFechafinCrea(Date fechafinCrea) {
        this.fechafinCrea = fechafinCrea;
    }

    public Date getFechaInicioViewCrea() {
        return fechaInicioViewCrea;
    }

    public void setFechaInicioViewCrea(Date fechaInicioViewCrea) {
        this.fechaInicioViewCrea = fechaInicioViewCrea;
    }

    public Date getFechafinViewCrea() {
        return fechafinViewCrea;
    }

    public void setFechafinViewCrea(Date fechafinViewCrea) {
        this.fechafinViewCrea = fechafinViewCrea;
    }

    public String getBusquedaCategoria() {
        return busquedaCategoria;
    }

    public void setBusquedaCategoria(String busquedaCategoria) {
        this.busquedaCategoria = busquedaCategoria;
    }

    public ReporteGeneralGranulado getReporteBusquedaCategorias() {
        return reporteBusquedaCategorias;
    }

    public void setReporteBusquedaCategorias(ReporteGeneralGranulado reporteBusquedaCategorias) {
        this.reporteBusquedaCategorias = reporteBusquedaCategorias;
    }

    public boolean isBusquedaActiva() {
        return busquedaActiva;
    }

    public void setBusquedaActiva(boolean busquedaActiva) {
        this.busquedaActiva = busquedaActiva;
    }

    public String getUltimaFechaConsumida() {
        return ultimaFechaConsumida;
    }

    public void setUltimaFechaConsumida(String ultimaFechaConsumida) {
        this.ultimaFechaConsumida = ultimaFechaConsumida;
    }
    
    
    
}
