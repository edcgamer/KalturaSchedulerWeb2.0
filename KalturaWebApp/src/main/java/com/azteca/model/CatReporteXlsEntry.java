/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.azteca.model;

import java.text.DecimalFormat;
import java.util.Date;

/**
 *
 * @author Santa
 */
public class CatReporteXlsEntry {
    
    private String entryId;
    private String nombre;
    private String nombreFabrica;
    private String nombrePrograma;
    private String nombreCuenta;
    private String nombreUnidad;
    private String nombreVideo;
    private Long minVistos;
    private Long totalEntrys;
    private Double tamanio;
    private Long duration;
    private Date fechaCorte;
    private Date fechaCreacion;
    private Double minVisUni;
    private Double tamUni;
    
    @Override
    public boolean equals(Object o) {
        if(o!=null){
        CatReporteXlsEntry crx= (CatReporteXlsEntry)o;
        if(this.getFechaCorte().equals(crx.getFechaCorte())){
          return true;  
        }else{
            return false;
        }
        
        }else{
            return false;
        }
    }

    public CatReporteXlsEntry() {
    }

    public CatReporteXlsEntry(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public CatReporteXlsEntry(String entryId,String nombreUnidad, String nombreFabrica, String nombrePrograma, String nombreCuenta, String nombreVideo, Long minVistos, Double tamanio, Long duration, Date fechaCorte,Date fechaCreacion, Double minVisUni, Double tamUni,Long totalEntrys) {
        this.entryId = entryId;
        this.nombreFabrica = nombreFabrica;
        this.nombrePrograma = nombrePrograma;
        this.nombreCuenta = nombreCuenta;
        this.nombreVideo = nombreVideo;
        this.minVistos = minVistos;
        this.tamanio = tamanio;
        this.duration = duration;
        this.fechaCorte = fechaCorte;
        this.minVisUni = minVisUni;
        this.tamUni = tamUni;
        this.nombreUnidad= nombreUnidad;
        this.fechaCreacion=fechaCreacion;
        this.totalEntrys= totalEntrys;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }
    
    
    

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

    public Long getMinVistos() {
        return minVistos;
    }

    public void setMinVistos(Long minVistos) {
        this.minVistos = minVistos;
    }

    public Long getTotalEntrys() {
        return totalEntrys;
    }

    public void setTotalEntrys(Long totalEntrys) {
        this.totalEntrys = totalEntrys;
    }

    public Double getTamanio() {
        return Double.parseDouble(new DecimalFormat("#.##").format(tamanio));
    }

    public void setTamanio(Double tamanio) {
        this.tamanio = tamanio;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
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

    public Double getMinVisUni() {
        return minVisUni;
    }

    public void setMinVisUni(Double minVisUni) {
        this.minVisUni = minVisUni;
    }

    public Double getTamUni() {
        return Double.parseDouble(new DecimalFormat("#.##").format(tamUni));
    }

    public void setTamUni(Double tamUni) {
        this.tamUni = tamUni;
    }
    
    
    
}
