/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosanta.service.impl;

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
import com.azteca.persistence.repository.ComscoreConsumoRepository;
import com.azteca.persistence.repository.ComscoreFabricaRepository;
import com.azteca.persistence.repository.ComscoreSitioRepository;
import com.azteca.persistence.repository.KalturaCuentaRepository;
import com.azteca.persistence.repository.KalturaFabricaRepository;
import com.azteca.persistence.repository.KalturaProgramaRepository;
import com.azteca.persistence.repository.KalturaUnidadRepository;
import com.azteca.persistence.repository.SistemaFacturaGralRepository;
import com.azteca.persistence.repository.SistemaFacturaRepository;
import com.azteca.persistence.repository.SistemaReporteRepository;
import com.azteca.persistence.repository.SistemaServicioRepository;
import com.azteca.persistence.repository.SistemaSubReporteRepository;
import com.azteca.persistence.repository.SistemaTipoConsumoRepository;
import com.azteca.persistence.repository.SistemaTipoServicioRepository;
import com.azteca.persistence.repository.SistemaUsuarioRepository;
import com.marcosanta.service.ConsultasBDService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Santa
 */
public class ConsultasBDServiceImpl implements ConsultasBDService, Serializable {

    @Autowired
    KalturaCuentaRepository kalturaCuentaRepository;

    @Autowired
    KalturaUnidadRepository kalturaUnidadRepository;

    @Autowired
    KalturaFabricaRepository kalturaFabricaRepository;

    @Autowired
    KalturaProgramaRepository kalturaProgramaRepository;

    @Autowired
    SistemaSubReporteRepository sistemaSubReporteRepository;

    @Autowired
    SistemaReporteRepository sistemaReporteRepository;

    @Autowired
    SistemaUsuarioRepository sistemaUsuarioRepository;

    @Autowired
    SistemaTipoConsumoRepository sistemaTipoConsumoRepository;

    @Autowired
    SistemaTipoServicioRepository sistemaTipoServicioRepository;

    @Autowired
    SistemaServicioRepository sistemaServicioRepository;

    @Autowired
    SistemaFacturaRepository sistemaFacturaRepository;
    
    @Autowired
    SistemaFacturaGralRepository sistemaFacturaGralRepository;
    
    @Autowired
    ComscoreSitioRepository comscoreSitioRepository;
    
    @Autowired
    ComscoreFabricaRepository comscoreFabricaRepository;

    @Autowired
    ComscoreConsumoRepository comscoreConsumoRepository;
    
    
    
    @Override
    public List<Object[]> findReporteNoVistoGloblalConCreacion(Date fechaInicialCrea, Date fechaFinalCrea, String unidad, Date fechaInicial, Date fechaFinal) {
        return sistemaReporteRepository.findReporeNovistosGlobalConCreacion(unidad, fechaInicial, fechaFinal,fechaInicialCrea,fechaFinalCrea);
    }

    @Override
    public List<Object[]> findReporteNoVistofabricaConCreacion(Date fechaInicialCrea, Date fechaFinalCrea, String unidad, Date fechaInicial, Date fechaFinal, String fabrica) {
        return sistemaReporteRepository.findReporeNovistosfabricaConCreacion(unidad, fechaInicial, fechaFinal, fabrica,fechaInicialCrea,fechaFinalCrea);
    }

    @Override
    public List<Object[]> findReporteNoVistoProgramaConCreacion(Date fechaInicialCrea, Date fechaFinalCrea, String unidad, Date fechaInicial, Date fechaFinal, String fabrica, String programa) {
        return sistemaReporteRepository.findReporeNovistosProgramaConCreacion(unidad, fechaInicial, fechaFinal, fabrica,programa,fechaInicialCrea,fechaFinalCrea);
    }
    
    @Override
    public List<Object[]> findReporteNoVistoGloblal(String unidad,Date fechaInicial,Date fechaFinal){
        return sistemaReporteRepository.findReporeNovistosGlobal(unidad, fechaInicial, fechaFinal);
    }
    
    @Override
    public List<SistemaServicio> findAllSistemaServicio() {
        return sistemaServicioRepository.findAll();
    }

    @Override
    public void saveSistemaServicio(SistemaServicio sc) {
        sistemaServicioRepository.save(sc);
    }

    @Override
    public List<SistemaTipoConsumo> findAllTipoConsumo() {
        return sistemaTipoConsumoRepository.findAll();
    }

    @Override
    public List<SistemaTipoServicio> findAllTipoServicio() {
        return sistemaTipoServicioRepository.findAll();
    }

    @Override
    public void saveUser(Usuario usuario) {
        sistemaUsuarioRepository.save(usuario);
    }

    @Override
    public Usuario findUsuarioByUsername(String Username) {
        return sistemaUsuarioRepository.findByUsername(Username);
    }

    @Override
    public List<SistemaReporte> findReportByFechaCorteNivelPrograma(Date fechaInicio, Date fechaFinal, String unidad, String fabrica, String programa) {
        return sistemaReporteRepository.findReportByFechaCorteNivelPrograma(fechaInicio, fechaFinal, unidad, fabrica, programa);
    }
    
    public List<SistemaReporte> findReportByFechaCorteNivelPrograma2(Date fechaInicio, Date fechaFinal, String unidad, String fabrica, String programa) {
        return sistemaReporteRepository.findReportByFechaCorteNivelPrograma2(fechaInicio, fechaFinal, unidad, fabrica, programa);
    }

    @Override
    public List<SistemaSubReporte> findSubReporteByFechasAndTipo(Date fechaInicio, Date fechaFin, String tipo,int novistos) {
        return sistemaSubReporteRepository.findReportByFechaCorte(fechaInicio, fechaFin, tipo,novistos);
    }

    @Override
    public List<SistemaSubReporte> findSubReporteByTipo(String tipo,int novistos) {
        return sistemaSubReporteRepository.findByTipoAndNoVistos(tipo,novistos);
    }

    @Override
    public List<KalturaCuenta> findCuentaByNombre(String nombre) {
        return kalturaCuentaRepository.findByNombre(nombre);
    }

    @Override
    public List<KalturaCuenta> findAllCuenta() {
        return kalturaCuentaRepository.findAll();
    }

    @Override
    public List<KalturaUnidad> findUnidadByNombre(String nombre) {
        return kalturaUnidadRepository.findByNombre(nombre);
    }

    @Override
    public List<KalturaFabrica> findFabricaByNombre(String nombre) {
        return kalturaFabricaRepository.findByNombre(nombre);
    }

    @Override
    public List<KalturaPrograma> findProgramaByNombre(String nombre) {
        return kalturaProgramaRepository.findByNombre(nombre);
    }

    @Override
    public List<SistemaSubReporte> findReportByFechaCorteNivelUnidad(Date fechaInicio, Date fechaFin, String tipo, String cuenta,int novistos) {
        return sistemaSubReporteRepository.findReportByFechaCorteNivelUnidad(fechaInicio, fechaFin, tipo, cuenta,novistos);
    }

    @Override
    public List<SistemaSubReporte> findReportByFechaCorteNivelFabrica(Date fechaInicio, Date fechaFin, String tipo, String cuenta, String unidad,int novistos) {
        return sistemaSubReporteRepository.findReportByFechaCorteNivelfabrica(fechaInicio, fechaFin, tipo, cuenta, unidad,novistos);
    }

    @Override
    public List<SistemaSubReporte> findSubReportByFechaCorteNivelPrograma(Date fechaInicio, Date fechaFin, String tipo, String cuenta, String fabrica,int novistos) {
        return sistemaSubReporteRepository.findReportByFechaCorteNivelPrograma(fechaInicio, fechaFin, tipo, cuenta, fabrica,novistos);
    }

    @Override
    public List<SistemaSubReporte> findReportNivelUnidad(String tipo, String cuenta,int novistos) {
        return sistemaSubReporteRepository.findReportNivelUnidad(tipo, cuenta,novistos);
    }

    @Override
    public List<SistemaSubReporte> findReportNivelFabrica(String tipo, String cuenta, String unidad,int novistos) {
        return sistemaSubReporteRepository.findReportNivelFabrica(tipo, cuenta, unidad,novistos);
    }

    @Override
    public List<SistemaSubReporte> findReportNivelPrograma(String tipo, String cuenta, String fabrica,int novistos) {
        return sistemaSubReporteRepository.findReportNivelPrograma(tipo, cuenta, fabrica,novistos);
    }

    @Override
    public List<SistemaReporte> findReportByNivelPrograma(String unidad, String fabrica, String programa) {
        return sistemaReporteRepository.findReportByNivelPrograma(unidad, fabrica, programa);
    }
    
    @Override
    public List<SistemaReporte> findReportByNivelPrograma2(String unidad, String fabrica, String programa) {
        return sistemaReporteRepository.findReportByNivelPrograma2(unidad, fabrica, programa);
    }

    @Override
    public List<SistemaReporte> findCorteByFecha(Date ini, Date fin) {
        return sistemaReporteRepository.findCortesByFecha(ini, fin);
    }

    @Override
    public List<SistemaSubReporte> findCorteB() {
        return sistemaReporteRepository.findCortes();
    }

    @Override
    public void saveSistemaConsumo(SistemaTipoConsumo stp) {
        sistemaTipoConsumoRepository.save(stp);
    }

    @Override
    public void deleteSistemaConsumo(SistemaTipoConsumo stp) {
        sistemaTipoConsumoRepository.delete(stp);
    }

    @Override
    public void saveSistemaTipoServicio(SistemaTipoServicio sts) {
        sistemaTipoServicioRepository.save(sts);

    }

    @Override
    public void deleteSistematipoServicio(SistemaTipoServicio sts) {
        sistemaTipoServicioRepository.delete(sts);
    }

    @Override
    public SistemaTipoServicio findSistemaTipoServicioByNombre(String nombre) {
        return sistemaTipoServicioRepository.findByNombre(nombre);
    }

    @Override
    public void deleteSistemaServicio(SistemaServicio ss) {
        sistemaServicioRepository.delete(ss.getId());
    }

    @Override
    public SistemaTipoConsumo findSistemaTipoConsumoByName(String name) {
        return sistemaTipoConsumoRepository.findByNombre(name);
    }

    @Override
    public List<SistemaTipoServicio> findsistemaTipoServicioByTipoConsumo(SistemaTipoConsumo stc) {
        return sistemaTipoServicioRepository.findByTipoConsumo(stc);
    }

    @Override
    public List<SistemaServicio> findSistemaServicioBytipoServicio(SistemaTipoServicio sts) {
        return sistemaServicioRepository.findBySistemaServicio(sts);
    }

    @Override
    public List<Sistemafactura> findAllSistemaFactura() {
        return sistemaFacturaRepository.findAll();
    }

    @Override
    public List<Sistemafactura> findSistemaFacturaBySistemaServicio(SistemaServicio ss) {
        return sistemaFacturaRepository.findBySistemaServicio(ss);
    }

    @Override
    public void deleteSistemaFactura(Sistemafactura sf) {
        sistemaFacturaRepository.delete(sf);
    }

    @Override
    public List<SistemaFacturaGral> findAllSistemafacturaGral() {
        return sistemaFacturaGralRepository.findAll();
    }

    @Override
    public List<SistemaSubReporte> findCorteByFechaSubReporte(Date ini, Date fin) {
        return sistemaSubReporteRepository.findCortesByFecha(ini, fin);
    }

    @Override
    public List<Object[]> findReporteNoVistofabrica(String unidad, Date fechaInicial, Date fechaFinal, String fabrica) {
        return sistemaReporteRepository.findReporeNovistosfabrica(unidad, fechaInicial, fechaFinal, fabrica);
    }

    @Override
    public List<Object[]> findReporteNoVistoPrograma(String unidad, Date fechaInicial, Date fechaFinal, String fabrica, String programa) {
        return sistemaReporteRepository.findReporeNovistosPrograma(unidad, fechaInicial, fechaFinal, fabrica,programa);
    }

    @Override
    public List<KalturaFabrica> findFabricaByNombreAndUnidad(String nombre, KalturaUnidad unidad) {
        return kalturaFabricaRepository.findByNombreAndKalturaUnidad(nombre, unidad);
    }
    
     @Override
    public List<KalturaPrograma> findProgramaByNombreAndFabrica(String nombre, KalturaFabrica fabrica) {
        return kalturaProgramaRepository.findByNombreAndKalturaFabrica(nombre, fabrica);
    }

    @Override
    public List<KalturaUnidad> findLikeUnidad(String unidad) {
        return kalturaUnidadRepository.findLikeUnidad(unidad);
    }

    @Override
    public List<KalturaFabrica> findLikeFabrica(String fabrica) {
        return kalturaFabricaRepository.findLikeFabrica(fabrica);
    }

    @Override
    public List<KalturaPrograma> findLikePrograma(String programa) {
        return kalturaProgramaRepository.findLikePrograma(programa);
    }

    @Override
    public List<ComscoreSitio> findAllComscoreSitio() {
       return comscoreSitioRepository.findAll();
    }

    @Override
    public List<ComscoreFabrica> findComscoreFabricaBySitioAndNombre(ComscoreSitio cs,String nombre) {
        return comscoreFabricaRepository.findByNombreAndComscoreSitio(nombre,cs);
    }
    
    public List<ComscoreSitio>findComscoreSitioByNombre(String nombre){
        return comscoreSitioRepository.findByNombre(nombre);
    }

    @Override
    public List<ComscoreConsumo> findByFechaCorteAndFabricaAndSitio(String unidad, String fabrica, Date fechaInicio, Date fechaFinal) {
        return comscoreConsumoRepository.findByFechaCorteAndFabricaAndSitio(unidad, fabrica, fechaInicio, fechaFinal);
    }

    @Override
    public List<ComscoreConsumo> findByFabricaAndSitio(String unidad, String fabrica) {
        return comscoreConsumoRepository.findByFabricaAndSitio(unidad, fabrica);
    }

    @Override
    public List<ComscoreConsumo> findByFechaCorte(Date fechaInicio, Date fechaFinal) {
        return  comscoreConsumoRepository.findByFechaCorteA(fechaInicio, fechaFinal);
    }

    @Override
    public List<ComscoreConsumo> findAllComscoreConsumo() {
        return comscoreConsumoRepository.findAll();
    }

    @Override
    public List<ComscoreConsumo> findComscoreConsumoBySitio(String sitio) {
        return  comscoreConsumoRepository.findBySitio(sitio);
    }

    @Override
    public List<ComscoreConsumo> findComscoreConsumoBySitioAndFechaCorte(String sitio, Date fechaInicio, Date fechaFinal) {
        return comscoreConsumoRepository.findByFechaCorteAndSitio(sitio, fechaInicio, fechaFinal);
    }

    @Override
    public List<ComscoreConsumo> findComscoreConsumoBySitioAndFabrica(String sitio, String fabrica) {
        return comscoreConsumoRepository.findByFabricaAndSitio(sitio, fabrica);
    }

    @Override
    public List<ComscoreConsumo> findComscoreConsumoBySitioAndFabricaAndFechaCorte(String sitio, String fabrica, Date fechaInicio, Date fechaFinal) {
        return comscoreConsumoRepository.findByFechaCorteAndFabricaAndSitio(sitio, fabrica, fechaInicio, fechaFinal);
    }

    @Override
    public Date obtenerMaxDateActualizado() {
        return sistemaSubReporteRepository.ultimaFecha();
    }

    @Override
    public Date obtenerMaxDateActualizadoComscore() {
        return comscoreConsumoRepository.ultimaFecha();
    }
    

}
