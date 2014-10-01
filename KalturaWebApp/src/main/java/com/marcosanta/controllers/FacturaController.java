/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marcosanta.controllers;

import com.azteca.persistence.entities.SistemaFacturaGral;
import com.azteca.persistence.entities.SistemaServicio;
import com.azteca.persistence.entities.SistemaTipoConsumo;
import com.azteca.persistence.entities.SistemaTipoServicio;
import com.azteca.persistence.entities.Sistemafactura;
import com.marcosanta.service.ConsultasBDService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Santa
 */
@Controller("facturaController")
@Scope("session")
public class FacturaController {
    
    @Autowired
    ConsultasBDService consultasBDService;
    
    private List<SistemaTipoConsumo> listaConsumos;
    private List<SistemaTipoServicio> listaServicios;
    private List<SistemaServicio> listaServicio;
    private List<Sistemafactura> listaFactura;
    private List<Sistemafactura> nuevaListaFactura;
    private List<SistemaFacturaGral> listaFacturas;
    private String nuevoConsumo;
    private String consumoSeleccionado;
    private String tipoServicioSeleccionado;
    private SistemaTipoServicio nuevotipoServicio;
    private SistemaServicio SistemaServicio; 
    private SistemaFacturaGral sistemaFacturaGral;
    private Date hoy;
    private boolean crearServicio;
    
    @PostConstruct
    public void init() {
        hoy= new Date();
        crearServicio=false;
        sistemaFacturaGral= new SistemaFacturaGral();
        nuevaListaFactura= new ArrayList<>();
        nuevotipoServicio= new SistemaTipoServicio();
        SistemaServicio= new SistemaServicio();
        listaConsumos= new ArrayList<>();
        listaServicios= new ArrayList<>();
        listaFacturas= consultasBDService.findAllSistemafacturaGral();
        listaConsumos= consultasBDService.findAllTipoConsumo();
        listaServicios= consultasBDService.findAllTipoServicio();
        listaServicio= consultasBDService.findAllSistemaServicio();
        listaFactura= consultasBDService.findAllSistemaFactura();
        nuevoConsumo="";
        consumoSeleccionado="";
        tipoServicioSeleccionado="";
    }
    
    public void agregarServicio(){
        System.out.println("entre");
        nuevaListaFactura.add(new Sistemafactura());
        System.out.println(nuevaListaFactura.size());
    }
    
    public void botonCrearNuevoServicio(){
        crearServicio=true;
    }
    
    public void botonVolverFactura(){
        crearServicio=false;
    }
    
    public void guardaConsumoNuevo(){
        if (!nuevoConsumo.replaceAll(" ", "").equals("")) {
            consultasBDService.saveSistemaConsumo(new SistemaTipoConsumo(nuevoConsumo));
            listaConsumos= consultasBDService.findAllTipoConsumo();
        }
    }
    
    public void guardaServicioNuevo(){
        if (!nuevotipoServicio.getNombre().replaceAll(" ", "").equals("")) {
            if(!consumoSeleccionado.replaceAll(" ", "").equals("")){
                nuevotipoServicio.setTipoConsumo(new SistemaTipoConsumo(consumoSeleccionado));
            }else{
                nuevotipoServicio.setTipoConsumo(null);
            }
            consultasBDService.saveSistemaTipoServicio(nuevotipoServicio);
            listaServicios= consultasBDService.findAllTipoServicio();
        }
        nuevotipoServicio= new SistemaTipoServicio();
        consumoSeleccionado="";
    }
    
    public void guardaNuevoServicio(){
        if(!this.tipoServicioSeleccionado.replaceAll(" ", "").equals("")){
            System.out.println("entre");
            this.SistemaServicio.setSistemaServicio(consultasBDService.findSistemaTipoServicioByNombre(tipoServicioSeleccionado));
            consultasBDService.saveSistemaServicio(this.SistemaServicio);
            listaServicio= consultasBDService.findAllSistemaServicio();
        }
    }

    public String getTipoServicioSeleccionado() {
        return tipoServicioSeleccionado;
    }

    public void setTipoServicioSeleccionado(String tipoServicioSeleccionado) {
        this.tipoServicioSeleccionado = tipoServicioSeleccionado;
    }

    public SistemaServicio getSistemaServicio() {
        return SistemaServicio;
    }

    public void setSistemaServicio(SistemaServicio SistemaServicio) {
        this.SistemaServicio = SistemaServicio;
    }
    
    public void borraConsumo(SistemaTipoConsumo sc){
        consultasBDService.deleteSistemaConsumo(sc);
        listaConsumos= consultasBDService.findAllTipoConsumo();
    }
    
    public void borraTipoServicio(SistemaTipoServicio sts){
        consultasBDService.deleteSistematipoServicio(sts);
        listaServicios= consultasBDService.findAllTipoServicio();
        listaConsumos=consultasBDService.findAllTipoConsumo();
    }
    
    public void borraServicio(SistemaServicio ss){
        consultasBDService.deleteSistemaServicio(ss);
        listaServicios= consultasBDService.findAllTipoServicio();
        listaServicio= consultasBDService.findAllSistemaServicio();
        listaConsumos=consultasBDService.findAllTipoConsumo();
    }
    
    public void borraFactura(Sistemafactura sf){
        consultasBDService.deleteSistemaFactura(sf);
        listaServicios= consultasBDService.findAllTipoServicio();
        listaServicio= consultasBDService.findAllSistemaServicio();
        listaConsumos=consultasBDService.findAllTipoConsumo();
        listaFactura= consultasBDService.findAllSistemaFactura();
    }
    
    public boolean renderedborrarConsumo(SistemaTipoConsumo tc){
        if(consultasBDService.findsistemaTipoServicioByTipoConsumo(tc).isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean renderedborrarServicio(SistemaServicio sc){
        if(consultasBDService.findSistemaFacturaBySistemaServicio(sc).isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean renderedborrarTipoServicio(SistemaTipoServicio tc){
        if(consultasBDService.findSistemaServicioBytipoServicio(tc).isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public List<SistemaTipoConsumo> getListaConsumos() {
        return listaConsumos;
    }

    public void setListaConsumos(List<SistemaTipoConsumo> listaConsumos) {
        this.listaConsumos = listaConsumos;
    } 

    public List<SistemaTipoServicio> getListaServicios() {
        return listaServicios;
    }

    public void setListaServicios(List<SistemaTipoServicio> listaServicios) {
        this.listaServicios = listaServicios;
    }

    public String getNuevoConsumo() {
        return nuevoConsumo;
    }

    public void setNuevoConsumo(String nuevoConsumo) {
        this.nuevoConsumo = nuevoConsumo;
    }

    public SistemaTipoServicio getNuevotipoServicio() {
        return nuevotipoServicio;
    }

    public void setNuevotipoServicio(SistemaTipoServicio nuevotipoServicio) {
        this.nuevotipoServicio = nuevotipoServicio;
    }

    public String getConsumoSeleccionado() {
        return consumoSeleccionado;
    }

    public void setConsumoSeleccionado(String consumoSeleccionado) {
        this.consumoSeleccionado = consumoSeleccionado;
    }

    public List<SistemaServicio> getListaServicio() {
        return listaServicio;
    }

    public void setListaServicio(List<SistemaServicio> listaServicio) {
        this.listaServicio = listaServicio;
    }

    public List<Sistemafactura> getListaFactura() {
        return listaFactura;
    }

    public void setListaFactura(List<Sistemafactura> listaFactura) {
        this.listaFactura = listaFactura;
    }

    public List<Sistemafactura> getNuevaListaFactura() {
        return nuevaListaFactura;
    }

    public void setNuevaListaFactura(List<Sistemafactura> nuevaListaFactura) {
        this.nuevaListaFactura = nuevaListaFactura;
    }

    public SistemaFacturaGral getSistemaFacturaGral() {
        return sistemaFacturaGral;
    }

    public void setSistemaFacturaGral(SistemaFacturaGral sistemaFacturaGral) {
        this.sistemaFacturaGral = sistemaFacturaGral;
    }

    public List<SistemaFacturaGral> getListaFacturas() {
        return listaFacturas;
    }

    public void setListaFacturas(List<SistemaFacturaGral> listaFacturas) {
        this.listaFacturas = listaFacturas;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    public boolean isCrearServicio() {
        return crearServicio;
    }

    public void setCrearServicio(boolean crearServicio) {
        this.crearServicio = crearServicio;
    }
    
    
}
