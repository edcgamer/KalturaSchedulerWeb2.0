/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.azteca.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Santa
 */
public class CatReporteXls {

    private String entryId;
    private String nombre;
    private String nombreFabrica;
    private String nombrePrograma;
    private String nombreCuenta;
    private String nombreVideo;
    private String nombreUnidad;
    private BigDecimal minVistos;
    private BigDecimal totalEntrys;
    private BigDecimal tamanio;
    private BigDecimal duration;
    private BigDecimal porciento;
    private Date fechaCorte;
    private Date fechaCreacion;
    private BigDecimal minVisUni;
    private BigDecimal tamUni;
    private String minVisUniStr;
    private String porcientoStr;
    private String tamUniStr;
    private String minVistosStr;
    private String tamanioStr;
    private String durationStr;
    private String totalEntrysStr;
    private String partnerId;

    public CatReporteXls(String nombreCuenta) {
        this.nombre = nombreCuenta;
    }

    public CatReporteXls() {
        minVistos= new BigDecimal(0);
        minVisUni= new BigDecimal(0);
    }

    public CatReporteXls(String nombre, BigDecimal minVistos, BigDecimal tamanio) {
        this.nombre = nombre;
        this.minVistos = minVistos;
        this.tamanio = tamanio;
    }
    

    public CatReporteXls(String entryId, String nombre, String nombreFabrica, String nombrePrograma, String nombreCuenta, String nombreUnidad) {
        this.entryId = entryId;
        this.nombre = nombre;
        this.nombreFabrica = nombreFabrica;
        this.nombrePrograma = nombrePrograma;
        this.nombreCuenta = nombreCuenta;
        this.nombreUnidad = nombreUnidad;
    }
    
    

    public CatReporteXls(String nombre, BigDecimal minVistos, BigDecimal minVisUni,String NombreUnidad,String NombreFabrica) {
        this.nombre = nombre;
        this.minVistos = minVistos;
        this.minVisUni = minVisUni;
        this.nombreUnidad= NombreUnidad;
        this.nombreFabrica=NombreFabrica;
    }

    public CatReporteXls(String entryId, String partnerId) {
        this.entryId = entryId;
        this.partnerId = partnerId;
    }
    
    

    public String getPorcientoStr() {
        return  new DecimalFormat("###,###.##").format(porciento)+"%" ;
    }
    
    

    public String getMinVistosStr() {
        return  new DecimalFormat("###,###.##").format(minVistos) ;
    }

    public String getTamanioStr() {
        return  new DecimalFormat("###,###.##").format(tamanio) ;
    }

    public String getDurationStr() {
        return  new DecimalFormat("###,###.##").format(duration) ;
    }

    public String getTotalEntrysStr() {
        return  new DecimalFormat("###,###").format(totalEntrys) ;
    }
    
    

    public String getMinVisUniStr() {
        
        return  new DecimalFormat("###,###.##").format(minVisUni) ;
    }

    public void setMinVisUniStr(String minVisUniStr) {
        this.minVisUniStr = minVisUniStr;
    }

    public String getTamUniStr() {
        
        return new DecimalFormat("###,###.##").format(tamUni);
    }

    public void setTamUniStr(String tamUniStr) {
        this.tamUniStr = tamUniStr;
    }

    public BigDecimal getPorciento() {
        return porciento;
    }

    public void setPorciento(BigDecimal porciento) {
        this.porciento = porciento;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
    
    
    
    
    public CatReporteXls(BigDecimal minVistos, BigDecimal tamanio, BigDecimal duration, BigDecimal minVisUni, BigDecimal tamUni,BigDecimal totalEntrys) {
        this.minVistos = minVistos;
        this.tamanio = tamanio;
        this.duration = duration;
        this.minVisUni = minVisUni;
        this.tamUni = tamUni;
        this.totalEntrys=totalEntrys;
    }

    public CatReporteXls(String nombre, BigDecimal minVistos, BigDecimal tamanio, BigDecimal duration, Date fechaCorte, BigDecimal minVisUni, BigDecimal tamUni,BigDecimal totalEntrys) {
        this.nombre = nombre;
        this.minVistos = minVistos;
        this.tamanio = tamanio;
        this.duration = duration;
        this.fechaCorte = fechaCorte;
        this.minVisUni = minVisUni;
        this.tamUni = tamUni;
        this.totalEntrys=totalEntrys;
    }

    public CatReporteXls(String entryId, String nombre, String nombreFabrica, String nombrePrograma, String nombreCuenta, String nombreVideo, String nombreUnidad, BigDecimal tamanio, BigDecimal duration,BigDecimal tiempoVisto, Date fechaCorte, Date fechaCreacion, BigDecimal minVisUni,BigDecimal tamUni,BigDecimal totalEntrys) {
        this.entryId = entryId;
        this.nombre = nombre;
        this.nombreFabrica = nombreFabrica;
        this.nombrePrograma = nombrePrograma;
        this.nombreCuenta = nombreCuenta;
        this.nombreVideo = nombreVideo;
        this.nombreUnidad = nombreUnidad;
        this.tamanio = tamanio;
        this.duration = duration;
        this.fechaCorte = fechaCorte;
        this.fechaCreacion = fechaCreacion;
        this.minVisUni = minVisUni;
        this.tamUni=tamUni;
        this.minVistos= tiempoVisto;
        this.totalEntrys= totalEntrys;
    }
    
    

    @Override
    public boolean equals(Object o) {
        if(o!=null||this.nombre!=null
                ){
        CatReporteXls crx= (CatReporteXls)o;
        if(crx.getNombre()!=null){
        if(this.nombre.equals(crx.getNombre())){
          return true;  
        }else{
            return false;
        }
        }else{
            return  false;
        }
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.nombreCuenta);
        return hash;
    }

//    public double getTamUni() {
//        return Double.parseDouble(new DecimalFormat("#.####").format(tamUni));
//    }
    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreFabrica() {
        return nombreFabrica;
    }

    public void setNombreFabrica(String nombreFabrica) {
        this.nombreFabrica = nombreFabrica;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public String getNombreVideo() {
        return nombreVideo;
    }

    public void setNombreVideo(String nombreVideo) {
        this.nombreVideo = nombreVideo;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public BigDecimal getMinVistos() {
        return minVistos.setScale(2, RoundingMode.UP);
    }

    public void setMinVistos(BigDecimal minVistos) {
        this.minVistos = minVistos;
    }

    public BigDecimal getTotalEntrys() {
        return totalEntrys.setScale(2, RoundingMode.UP);
    }

    public void setTotalEntrys(BigDecimal totalEntrys) {
        this.totalEntrys = totalEntrys;
    }

    public BigDecimal getTamanio() {
        return tamanio.setScale(2, RoundingMode.UP);
    }

    public void setTamanio(BigDecimal tamanio) {
        this.tamanio = tamanio;
    }

    public BigDecimal getDuration() {
        return duration.setScale(2, RoundingMode.UP);
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getMinVisUni() {
        return minVisUni.setScale(2, RoundingMode.UP);
    }

    public void setMinVisUni(BigDecimal minVisUni) {
        this.minVisUni = minVisUni;
    }

    public BigDecimal getTamUni() {
        return tamUni.setScale(2, RoundingMode.UP);
    }

    public void setTamUni(BigDecimal tamUni) {
        this.tamUni = tamUni;
    }

    
    
    
}
