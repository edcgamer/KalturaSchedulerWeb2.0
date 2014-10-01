/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosanta.service;

import com.azteca.persistence.entities.ComscoreConsumo;
import com.azteca.persistence.entities.ComscoreFabrica;
import com.azteca.persistence.entities.ComscoreSitio;
import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.KalturaFabrica;
import com.azteca.persistence.entities.KalturaPrograma;
import com.azteca.persistence.entities.KalturaUnidad;
import com.azteca.persistence.entities.SistemaFacturaGral;
import com.azteca.persistence.entities.SistemaReporte;
import com.azteca.persistence.entities.SistemaServicio;
import com.azteca.persistence.entities.SistemaSubReporte;
import com.azteca.persistence.entities.SistemaTipoConsumo;
import com.azteca.persistence.entities.SistemaTipoServicio;
import com.azteca.persistence.entities.Sistemafactura;
import com.azteca.persistence.entities.Usuario;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Santa
 */
public interface ConsultasBDService {
    
    public Date obtenerMaxDateActualizadoComscore();
    
    public Date obtenerMaxDateActualizado();
    
    public List<ComscoreConsumo> findComscoreConsumoBySitio(String sitio);
    
    public List<ComscoreConsumo> findComscoreConsumoBySitioAndFechaCorte(String sitio,Date fechaInicio,Date fechaFinal);
    
    public List<ComscoreConsumo> findComscoreConsumoBySitioAndFabrica(String sitio,String fabrica);
    
    public List<ComscoreConsumo> findComscoreConsumoBySitioAndFabricaAndFechaCorte(String sitio,String fabrica,Date fechaInicio,Date fechaFinal);
    
    public List<ComscoreConsumo> findAllComscoreConsumo();
    
    public List<ComscoreConsumo> findByFechaCorte(Date fechaInicio,Date fechaFinal);
    
    public List<ComscoreConsumo> findByFechaCorteAndFabricaAndSitio( String unidad, String fabrica,  Date fechaInicio,Date fechaFinal);
    
    public List<ComscoreConsumo> findByFabricaAndSitio( String unidad, String fabrica);
    
    public List<ComscoreSitio>findAllComscoreSitio();
    
    public List<ComscoreSitio>findComscoreSitioByNombre(String nombre);
    
    public List<ComscoreFabrica>findComscoreFabricaBySitioAndNombre(ComscoreSitio cs,String nombre);
    
    public List<KalturaUnidad> findLikeUnidad(String unidad);
    
    public List<KalturaFabrica> findLikeFabrica(String fabrica);
    
    public List<KalturaPrograma> findLikePrograma(String programa);
    
    public List<Object[]> findReporteNoVistoGloblalConCreacion(Date fechaInicialCrea,Date fechaFinalCrea,String unidad,Date fechaInicial,Date fechaFinal);

    public List<Object[]> findReporteNoVistofabricaConCreacion(Date fechaInicialCrea,Date fechaFinalCrea,String unidad,Date fechaInicial,Date fechaFinal,String fabrica);
    
    public List<Object[]> findReporteNoVistoProgramaConCreacion(Date fechaInicialCrea,Date fechaFinalCrea,String unidad,Date fechaInicial,Date fechaFinal,String fabrica,String programa);
    
    public List<Object[]> findReporteNoVistoGloblal(String unidad,Date fechaInicial,Date fechaFinal);

    public List<Object[]> findReporteNoVistofabrica(String unidad,Date fechaInicial,Date fechaFinal,String fabrica);
    
    public List<Object[]> findReporteNoVistoPrograma(String unidad,Date fechaInicial,Date fechaFinal,String fabrica,String programa);
    
    public List<SistemaReporte> findReportByNivelPrograma(String unidad, String fabrica, String programa);
    
    public List<SistemaReporte> findReportByNivelPrograma2(String unidad, String fabrica, String programa);

    public List<SistemaReporte> findReportByFechaCorteNivelPrograma(Date fechaInicio, Date fechaFinal, String unidad, String fabrica, String programa);

    public List<SistemaReporte> findReportByFechaCorteNivelPrograma2(Date fechaInicio, Date fechaFinal, String unidad, String fabrica, String programa);

    public List<SistemaSubReporte> findReportNivelUnidad(String tipo, String cuenta,int novistos);

    public List<SistemaSubReporte> findReportNivelFabrica(String tipo, String cuenta, String unidad,int novistos);

    public List<SistemaSubReporte> findReportNivelPrograma(String tipo, String cuenta, String fabrica,int novistos);

    public List<SistemaSubReporte> findSubReporteByFechasAndTipo(Date fechaInicio, Date fechaFin, String tipo,int novistos);

    public List<SistemaSubReporte> findReportByFechaCorteNivelUnidad(Date fechaInicio, Date fechaFin, String tipo, String cuenta,int novistos);

    public List<SistemaSubReporte> findReportByFechaCorteNivelFabrica(Date fechaInicio, Date fechaFin, String tipo, String cuenta, String unidad,int novistos);

    public List<SistemaSubReporte> findSubReportByFechaCorteNivelPrograma(Date fechaInicio, Date fechaFin, String tipo, String cuenta, String fabrica,int novistos);

    public List<SistemaSubReporte> findSubReporteByTipo(String tipo,int novistos);

    public List<KalturaCuenta> findCuentaByNombre(String nombre);

    public List<KalturaUnidad> findUnidadByNombre(String nombre);

    public List<KalturaFabrica> findFabricaByNombre(String nombre);
    
    public List<KalturaFabrica> findFabricaByNombreAndUnidad(String nombre,KalturaUnidad unidad);
    
    public List<KalturaPrograma> findProgramaByNombreAndFabrica(String nombre, KalturaFabrica fabrica);

    public List<KalturaPrograma> findProgramaByNombre(String nombre);

    public List<KalturaCuenta> findAllCuenta();

    public Usuario findUsuarioByUsername(String Username);

    public void saveUser(Usuario usuario);

    public List<SistemaReporte> findCorteByFecha(Date ini, Date fin);
    
    public List<SistemaSubReporte> findCorteByFechaSubReporte(Date ini, Date fin);

    public List<SistemaSubReporte> findCorteB();

    public List<SistemaTipoConsumo> findAllTipoConsumo();

    public List<SistemaTipoServicio> findAllTipoServicio();

    public void saveSistemaConsumo(SistemaTipoConsumo stp);

    public void deleteSistemaConsumo(SistemaTipoConsumo stp);

    public void saveSistemaTipoServicio(SistemaTipoServicio sts);

    public void deleteSistematipoServicio(SistemaTipoServicio sts);

    public List<SistemaServicio> findAllSistemaServicio();

    public void saveSistemaServicio(SistemaServicio sc);
    
    public SistemaTipoServicio findSistemaTipoServicioByNombre(String nombre);
    
    public void deleteSistemaServicio(SistemaServicio ss);
   
    public SistemaTipoConsumo findSistemaTipoConsumoByName(String name);
    
    public List<SistemaTipoServicio> findsistemaTipoServicioByTipoConsumo(SistemaTipoConsumo stc);
    
    public List<SistemaServicio> findSistemaServicioBytipoServicio(SistemaTipoServicio sts);
    
    public List<Sistemafactura> findAllSistemaFactura();
    
    public List<Sistemafactura> findSistemaFacturaBySistemaServicio(SistemaServicio ss);
    
    public void deleteSistemaFactura(Sistemafactura sf);
    
    public List<SistemaFacturaGral> findAllSistemafacturaGral();
}
