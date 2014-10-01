/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marcosanta.service;

import com.azteca.model.CatReporteXls;
import com.azteca.model.ReporteGeneralGranulado;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Santa
 */
public interface  ComscoreService {
    public ReporteGeneralGranulado reporteGlobalComscore(Date fechaInicial, Date fechaFinal,String tipo,String sitio,String fabrica);
//    public ReporteGeneralGranulado reporteSitioComscore(Date fechaInicial,Date fechaFinal,String sitioSeleccionado);
//    public ReporteGeneralGranulado reporteFabricaComscore(Date fechaInicial,Date fechaFinal,String fabricaSeleccionado);
     public CatReporteXls calculoTotalReporteGlobal(List<CatReporteXls> reporteGlobal);
}
