/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marcosanta.service;

import com.azteca.model.CatReporteXls;
import com.azteca.model.ReporteGeneralGranulado;
import com.azteca.persistence.entities.KalturaCuenta;
import java.util.Date;
import java.util.List;



/**
 *
 * @author Santa
 */
public interface KalturaService {
    public ReporteGeneralGranulado calculoReporteGlobal(Date fechaInicio,Date fechaFin,String valorUnitarioTam,String valorUnitarioMIN,String tipoReporte,String cuenta,String unidad,String fabrica,int novistos);
    public CatReporteXls calculoTotalReporteGlobal(List<CatReporteXls> reporteGlobal,String minVistosUnit,String tamanioUnit);
    public ReporteGeneralGranulado reporteEntrysGranulado(Date fechaInicio,Date fechaFin,String valorUnitarioTam,String valorUnitarioMIN,String tipoReporte,String cuenta,String unidad,String fabrica,String programa,int novistos);
    public ReporteGeneralGranulado topTen(Date fechaInicio,Date fechaFin,String valorUnitarioTam,String valorUnitarioTiempoVisto,int novistos);
    public ReporteGeneralGranulado calculoReporteGlobalNoVistos(Date fechaInicioCrea,Date fechaFinCrea,Date fechaInicio,Date fechaFin,String valorUnitarioTam,String valorUnitarioMIN,String tipoReporte,String cuenta,String unidad,String fabrica,String programa);
    public ReporteGeneralGranulado calculoReporteCuentaNoVistos(Date fechaInicioCrea,Date fechaFinCrea,Date fechaInicio,Date fechaFin,String valorUnitarioTam,String valorUnitarioMIN,String tipoReporte,String cuenta,String unidad,String fabrica,String programa,KalturaCuenta kc);
    public ReporteGeneralGranulado calculoReporteUnidadVistos(Date fechaInicioCrea,Date fechaFinCrea,Date fechaInicio,Date fechaFin,String valorUnitarioTam,String valorUnitarioMIN,String tipoReporte,String cuenta,String unidad,String fabrica,String programa,KalturaCuenta kc);
    public ReporteGeneralGranulado calculoReportefabricaoVistos(Date fechaInicioCrea,Date fechaFinCrea,Date fechaInicio,Date fechaFin,String valorUnitarioTam,String valorUnitarioMIN,String tipoReporte,String cuenta,String unidad,String fabrica,String programa,KalturaCuenta kc);
    public ReporteGeneralGranulado calculoReporteProgramaNoVistos(Date fechaInicioCrea,Date fechaFinCrea,Date fechaInicio,Date fechaFin,String valorUnitarioTam,String valorUnitarioMIN,String tipoReporte,String cuenta,String unidad,String fabrica,String programa,KalturaCuenta kc);
    public ReporteGeneralGranulado FindCategoryByType(String category);
}
