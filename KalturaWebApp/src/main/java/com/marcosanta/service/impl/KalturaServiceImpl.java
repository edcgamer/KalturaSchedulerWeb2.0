/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosanta.service.impl;

import com.azteca.model.CatReporteXls;
import com.azteca.model.CatReporteXlsEntry;
import com.azteca.model.ReporteGeneralGranulado;
import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.KalturaFabrica;
import com.azteca.persistence.entities.KalturaPrograma;
import com.azteca.persistence.entities.KalturaUnidad;
import com.azteca.persistence.entities.SistemaReporte;
import com.azteca.persistence.entities.SistemaSubReporte;
import com.marcosanta.service.ConsultasBDService;
import com.marcosanta.service.KalturaService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
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
public class KalturaServiceImpl implements KalturaService, Serializable {

    @Autowired
    ConsultasBDService consultasBDService;

    @Override
    public ReporteGeneralGranulado calculoReporteGlobal(Date fechaInicio, Date fechaFin, String valorUnitarioTam, String valorUnitarioMIN, String tipoReporte, String cuenta, String unidad, String fabrica, int novistos) {
        ChartSeries chartStorage = new ChartSeries();
        ChartSeries chartTiempoVisto = new ChartSeries();
        ChartSeries chartProductividad = new ChartSeries();
        CartesianChartModel graficaStorage = new CartesianChartModel();
        CartesianChartModel graficaTiempoVisto = new CartesianChartModel();
        CartesianChartModel graficaProductividad = new CartesianChartModel();
        Map<Object, Number> mapaSerieTamanio = new HashMap<>();
        Map<Object, Number> mapaSerieProductividad = new HashMap<>();
        Map<Object, Number> mapaSerieTiempoVisto = new HashMap<>();
        List<CatReporteXls> reporteGlobal = new ArrayList<>();
        List<SistemaSubReporte> listaTemportalSubReporteGlobal = new ArrayList<>();
        if (fechaInicio != null && fechaFin != null) {
            if (cuenta != null && unidad == null && fabrica == null) {
                listaTemportalSubReporteGlobal = consultasBDService.findReportByFechaCorteNivelUnidad(fechaInicio, fechaFin, tipoReporte, cuenta, novistos);
            } else if (cuenta != null && unidad != null) {
                listaTemportalSubReporteGlobal = consultasBDService.findReportByFechaCorteNivelFabrica(fechaInicio, fechaFin, tipoReporte, cuenta, unidad, novistos);
            } else if (cuenta != null && fabrica != null) {
                listaTemportalSubReporteGlobal = consultasBDService.findSubReportByFechaCorteNivelPrograma(fechaInicio, fechaFin, tipoReporte, cuenta, fabrica, novistos);
            } else {
                listaTemportalSubReporteGlobal = consultasBDService.findSubReporteByFechasAndTipo(fechaInicio, fechaFin, tipoReporte, novistos);
            }
        } else {
            if (cuenta != null && unidad == null && fabrica == null) {
                listaTemportalSubReporteGlobal = consultasBDService.findReportNivelUnidad(tipoReporte, cuenta, novistos);
            } else if (cuenta != null && unidad != null) {
                listaTemportalSubReporteGlobal = consultasBDService.findReportNivelFabrica(tipoReporte, cuenta, unidad, novistos);
            } else if (cuenta != null && fabrica != null) {
                listaTemportalSubReporteGlobal = consultasBDService.findReportNivelPrograma(tipoReporte, cuenta, fabrica, novistos);
            } else {
                listaTemportalSubReporteGlobal = consultasBDService.findSubReporteByTipo(tipoReporte, novistos);
            }
        }
        if (!listaTemportalSubReporteGlobal.isEmpty()) {
            for (SistemaSubReporte ssr : listaTemportalSubReporteGlobal) {
                if (reporteGlobal.contains(new CatReporteXls(ssr.getNombre()))) {
                    CatReporteXls caRepTmp = reporteGlobal.get(reporteGlobal.indexOf(new CatReporteXls(ssr.getNombre())));
                    caRepTmp.setMinVistos(caRepTmp.getMinVistos().add(new BigDecimal(ssr.getTiempoVisto())));
                    caRepTmp.setDuration(caRepTmp.getDuration().add(new BigDecimal(ssr.getDuracion())));
                    caRepTmp.setTamanio(caRepTmp.getTamanio().add(new BigDecimal(ssr.getTamanio())));
                    caRepTmp.setTamUni(caRepTmp.getTamUni().add(new BigDecimal((ssr.getTamanio() * Double.parseDouble(valorUnitarioTam)))));
                    caRepTmp.setMinVisUni(caRepTmp.getMinVisUni().add(new BigDecimal((ssr.getTiempoVisto() * Double.parseDouble(valorUnitarioMIN)))));
                    if(caRepTmp.getTotalEntrys().longValue()<ssr.getTotalEntrys()){
                        caRepTmp.setTotalEntrys(new BigDecimal(ssr.getTotalEntrys()));
                    }
                    mapaSerieTamanio.put(ssr.getNombre(), mapaSerieTamanio.get(ssr.getNombre()).doubleValue() + ssr.getTamanio());
                    mapaSerieTiempoVisto.put(ssr.getNombre(), mapaSerieTiempoVisto.get(ssr.getNombre()).longValue() + ssr.getTiempoVisto());
                } else {
                    reporteGlobal.add(new CatReporteXls(ssr.getNombre(), new BigDecimal(ssr.getTiempoVisto()),
                            new BigDecimal(ssr.getTamanio()), new BigDecimal(ssr.getDuracion()), ssr.getFechaCorte(), new BigDecimal(ssr.getTiempoVisto() * Double.parseDouble(valorUnitarioMIN)),
                            new BigDecimal(ssr.getTamanio() * Double.parseDouble(valorUnitarioTam)), new BigDecimal(ssr.getTotalEntrys())));
                    mapaSerieTamanio.put(ssr.getNombre(), ssr.getTamanio());
                    mapaSerieTiempoVisto.put(ssr.getNombre(), ssr.getTiempoVisto());
                }
            }
        }
        int cortes = 1;
        mapaSerieTamanio = new HashMap<>();
        if (fechaInicio != null && fechaFin != null) {
            cortes = consultasBDService.findCorteByFechaSubReporte(fechaInicio, fechaFin).size();
        } else {
            cortes = consultasBDService.findCorteB().size();
        }

        for (CatReporteXls crxls : reporteGlobal) {
            crxls.setTamanio(crxls.getTamanio().divide(new BigDecimal(cortes), MathContext.DECIMAL128));
            crxls.setTamUni(crxls.getTamUni().divide(new BigDecimal(cortes), MathContext.DECIMAL128));
            mapaSerieTamanio.put(crxls.getNombre(), crxls.getTamanio());
            BigDecimal prod;

            if (crxls.getTamUni().compareTo(BigDecimal.ZERO) == 0) {
                prod = new BigDecimal(-1);
            } else {
                prod = crxls.getMinVisUni().subtract(crxls.getTamUni());
                prod = prod.divide(crxls.getTamUni(), MathContext.DECIMAL128);
            }
            mapaSerieProductividad.put(crxls.getNombre(), prod);
        }
        chartProductividad.setData(mapaSerieProductividad);
        chartStorage.setData(mapaSerieTamanio);
        chartTiempoVisto.setData(mapaSerieTiempoVisto);
        graficaProductividad.addSeries(chartProductividad);
        graficaStorage.addSeries(chartStorage);
        graficaTiempoVisto.addSeries(chartTiempoVisto);
        ReporteGeneralGranulado repGenGra = new ReporteGeneralGranulado(reporteGlobal, graficaStorage, graficaTiempoVisto, graficaProductividad);
        return repGenGra;
    }

    @Override
    public CatReporteXls calculoTotalReporteGlobal(List<CatReporteXls> reporteGlobal, String minVistosUnit, String tamanioUnit) {
        CatReporteXls totalReporte;
        Long minVistos = 0L;
        Double tamanio = 0.0;
        Long duration = 0L;
        Long totalEntrys = 0L;
        for (CatReporteXls crx : reporteGlobal) {
            minVistos += crx.getMinVistos().longValue();
            tamanio += crx.getTamanio().doubleValue();
            duration += crx.getDuration().longValue();
            totalEntrys += crx.getTotalEntrys().longValue();
        }
        totalReporte = new CatReporteXls(new BigDecimal(minVistos), new BigDecimal(tamanio), new BigDecimal(duration), new BigDecimal(minVistos * Double.parseDouble(minVistosUnit)), new BigDecimal(tamanio * Double.parseDouble(tamanioUnit)), new BigDecimal(totalEntrys));
        return totalReporte;
    }

    @Override
    public ReporteGeneralGranulado reporteEntrysGranulado(Date fechaInicio, Date fechaFin, String valorUnitarioTam, String valorUnitarioMIN, String tipoReporte, String cuenta, String unidad, String fabrica, String programa, int novistos) {
        System.out.println(programa+" "+fabrica+" "+unidad+" "+cuenta+" ");
        ChartSeries chartStorage = new ChartSeries();
        ChartSeries chartTiempoVisto = new ChartSeries();
        ChartSeries chartProductividad = new ChartSeries();
        CartesianChartModel graficaStorage = new CartesianChartModel();
        CartesianChartModel graficaTiempoVisto = new CartesianChartModel();
        CartesianChartModel graficaProductividad = new CartesianChartModel();
        Map<Object, Number> mapaSerieTamanio = new HashMap<>();
        Map<Object, Number> mapaSerieTiempoVisto = new HashMap<>();
        Map<Object, Number> mapaSerieProductividad = new HashMap<>();
        List<SistemaReporte> listaTemporalReporte = new ArrayList<>();
        if (fechaInicio != null && fechaFin != null) {
            if (novistos == 0) {
                listaTemporalReporte = this.consultasBDService.findReportByFechaCorteNivelPrograma(fechaInicio,
                        fechaFin, unidad, fabrica, programa);
            } else {
                listaTemporalReporte = this.consultasBDService.findReportByFechaCorteNivelPrograma2(fechaInicio,
                        fechaFin, unidad, fabrica, programa);
            }
        } else {
            if (novistos == 0) {
                listaTemporalReporte = this.consultasBDService.findReportByNivelPrograma(unidad,
                        fabrica, programa);
            } else {
                listaTemporalReporte = this.consultasBDService.findReportByNivelPrograma2(unidad,
                        fabrica, programa);
            }
        }
        List<CatReporteXls> reporteTemporal = new ArrayList<>();
        List<CatReporteXlsEntry> reporteTemporalGrafica = new ArrayList<>();
        for (SistemaReporte sr : listaTemporalReporte) {
            if (reporteTemporal.contains(new CatReporteXls(sr.getEntryId()))) {
                CatReporteXls caRepTmp2 = reporteTemporal.get(reporteTemporal.indexOf(new CatReporteXls(sr.getEntryId())));
                caRepTmp2.setMinVistos(caRepTmp2.getMinVistos().add(new BigDecimal(sr.getTiempoVisto())));
                caRepTmp2.setTamanio((caRepTmp2.getTamanio().add(new BigDecimal((sr.getTamanio().doubleValue() / 1028) / 1028))));
                caRepTmp2.setTamUni(caRepTmp2.getTamUni().add(new BigDecimal(((sr.getTamanio().doubleValue() / 1028) / 1028) * Double.parseDouble(valorUnitarioTam))));
                caRepTmp2.setMinVisUni(caRepTmp2.getMinVisUni().add(new BigDecimal((sr.getTiempoVisto() * Double.parseDouble(valorUnitarioMIN)))));
//                 caRepTmp2.setTotalEntrys(1L+caRepTmp2.getTotalEntrys());
//                 System.out.println(caRepTmp2.getTotalEntrys());
//                 System.out.println(caRepTmp2.getTamanio()+" "+caRepTmp2.getNombre());

            } else {
                reporteTemporal.add(new CatReporteXls(sr.getNombre(), sr.getEntryId(), sr.getNombreFabrica(),
                        sr.getNombrePrograma(), cuenta, sr.getNombre(), sr.getNombreUnidad(),
                        new BigDecimal(((sr.getTamanio().doubleValue() / 1028) / 1028)), new BigDecimal(sr.getDuracion()), new BigDecimal(sr.getTiempoVisto()), sr.getFechaCorte(), sr.getFechaCreacion(),
                        new BigDecimal(sr.getTiempoVisto() * Double.parseDouble(valorUnitarioMIN)), new BigDecimal(((sr.getTamanio().doubleValue() / 1028) / 1028) * Double.parseDouble(valorUnitarioTam)), new BigDecimal(1L)));
            }
            if (reporteTemporalGrafica.contains(new CatReporteXlsEntry(sr.getFechaCorte()))) {
                CatReporteXlsEntry caRepTmp = reporteTemporalGrafica.get(reporteTemporalGrafica.indexOf(new CatReporteXlsEntry(sr.getFechaCorte())));
                caRepTmp.setTotalEntrys(caRepTmp.getTotalEntrys() + 1L);
                caRepTmp.setTamanio(((sr.getTamanio().doubleValue() / 1028) / 1028) + caRepTmp.getTamanio());
                caRepTmp.setMinVistos(sr.getTiempoVisto() + caRepTmp.getMinVistos());
                caRepTmp.setMinVisUni((sr.getTiempoVisto() * Double.parseDouble(valorUnitarioMIN)) + caRepTmp.getMinVisUni());
                caRepTmp.setTamUni((sr.getTamanio() * Double.parseDouble(valorUnitarioTam)) + caRepTmp.getTamUni());
                mapaSerieTamanio.put(sr.getFechaCorte(), caRepTmp.getTamanio());
                mapaSerieTiempoVisto.put(sr.getFechaCorte(), caRepTmp.getMinVistos());
            } else {
                reporteTemporalGrafica.add(new CatReporteXlsEntry(sr.getEntryId(),
                        sr.getNombreUnidad(), sr.getNombreFabrica(), sr.getNombrePrograma(),
                        cuenta, sr.getNombre(), sr.getTiempoVisto(), ((sr.getTamanio().doubleValue() / 1028) / 1028), Long.parseLong(sr.getDuracion() + ""),
                        sr.getFechaCorte(), sr.getFechaCreacion(), sr.getTiempoVisto() * Double.parseDouble(valorUnitarioMIN), sr.getTamanio() * Double.parseDouble(valorUnitarioTam), 1L));

                mapaSerieTamanio.put(sr.getFechaCorte(), ((sr.getTamanio().doubleValue() / 1028) / 1028));
                mapaSerieTiempoVisto.put(sr.getFechaCorte(), sr.getTiempoVisto());
            }

        }
        int cortes = 1;
//        mapaSerieTamanio = new HashMap<>();
        if (fechaInicio != null && fechaFin != null) {
            cortes = consultasBDService.findCorteByFecha(fechaInicio, fechaFin).size();
        } else {
            cortes = consultasBDService.findCorteB().size();
        }

        for (CatReporteXlsEntry crtsxx : reporteTemporalGrafica) {
            BigDecimal prod;
            if (crtsxx.getTamUni() == 0) {
                prod = new BigDecimal(-1);
            } else {
                prod = new BigDecimal(crtsxx.getMinVisUni() - crtsxx.getTamUni());
                prod = prod.divide(new BigDecimal(crtsxx.getTamUni()), MathContext.DECIMAL128);
            }
            mapaSerieProductividad.put(crtsxx.getFechaCorte(), prod);
        }

        for (CatReporteXls crxls : reporteTemporal) {
            crxls.setTamanio(crxls.getTamanio().divide(new BigDecimal(cortes), MathContext.DECIMAL128));
            crxls.setTamUni(crxls.getTamUni().divide(new BigDecimal(cortes), MathContext.DECIMAL128));

        }
//        for(CatReporteXlsEntry crxe:reporteTemporalGrafica){
//            mapaSerieTamanio.put(crxe., cortes)
//        }
        chartProductividad.setData(mapaSerieProductividad);
        chartStorage.setData(mapaSerieTamanio);
        chartTiempoVisto.setData(mapaSerieTiempoVisto);
        graficaProductividad.addSeries(chartProductividad);
        graficaStorage.addSeries(chartStorage);
        graficaTiempoVisto.addSeries(chartTiempoVisto);
        ReporteGeneralGranulado repGenGra = new ReporteGeneralGranulado(reporteTemporal, graficaStorage, graficaTiempoVisto, graficaProductividad);
        return repGenGra;
    }

    @Override
    public ReporteGeneralGranulado topTen(Date fechaInicio, Date fechaFin, String valorUnitarioTam, String valorUnitarioTiempoVisto, int novistos) {
        BigDecimal total = new BigDecimal(0);
        ReporteGeneralGranulado rpg = new ReporteGeneralGranulado();
        List<SistemaSubReporte> ssr;
        List<CatReporteXls> ssrCompleta = new ArrayList<>();
        CatReporteXls top[] = new CatReporteXls[11];
        for (int i = 0; i < 11; i++) {
            top[i] = new CatReporteXls();
        }
        for (KalturaCuenta kc : consultasBDService.findAllCuenta()) {
            for (KalturaUnidad ku : kc.getListaUnidad()) {
                for (KalturaFabrica kf : ku.getListaFabricas()) {
                    if (fechaInicio != null && fechaFin != null) {
                        ssr = consultasBDService.findSubReportByFechaCorteNivelPrograma(fechaInicio, fechaFin, "programa", kc.getNombre(), kf.getNombre(), novistos);
                    } else {
                        ssr = consultasBDService.findReportNivelPrograma("programa", kc.getNombre(), kf.getNombre(), novistos);
                    }
                    for (SistemaSubReporte subReporte : ssr) {
                        total = total.add(new BigDecimal(subReporte.getTiempoVisto()));
                        if (ssrCompleta.contains(new CatReporteXls(subReporte.getNombre()))) {
                            CatReporteXls caRepTmp = ssrCompleta.get(ssrCompleta.indexOf(new CatReporteXls(subReporte.getNombre())));
                            if (caRepTmp.getNombreFabrica().equals(kf.getNombre()) && caRepTmp.getNombreUnidad().equals(ku.getNombre())) {
                                caRepTmp.setMinVistos(caRepTmp.getMinVistos().add(new BigDecimal(subReporte.getTiempoVisto())));
                                caRepTmp.setMinVisUni(caRepTmp.getMinVisUni().add(new BigDecimal((subReporte.getTiempoVisto() * Double.parseDouble(valorUnitarioTiempoVisto)))));
                            } else {
                                ssrCompleta.add(new CatReporteXls(subReporte.getNombre(), new BigDecimal(subReporte.getTiempoVisto()), new BigDecimal(subReporte.getTiempoVisto() * Double.parseDouble(valorUnitarioTiempoVisto)), subReporte.getUnidad(), subReporte.getFabrica()));
                            }
                        } else {
                            ssrCompleta.add(new CatReporteXls(subReporte.getNombre(), new BigDecimal(subReporte.getTiempoVisto()), new BigDecimal(subReporte.getTiempoVisto() * Double.parseDouble(valorUnitarioTiempoVisto)), subReporte.getUnidad(), subReporte.getFabrica()));
                        }
                    }
                }
            }
        }

        for (CatReporteXls crx : ssrCompleta) {
            for (int i = 0; i < 10; i++) {
                if (crx.getMinVistos().compareTo(top[i].getMinVistos()) == 1) {
                    if (i < 9 && top[i + 1].getMinVistos().compareTo(crx.getMinVistos()) == -1) {
                    } else {
                        top[i] = crx;
                        break;
                    }
                }
            }
        }
        List<CatReporteXls> ssrCompletas = new ArrayList<>();
        for (int i = 9; i >= 0; i--) {
            top[i].setPorciento(top[i].getMinVistos().multiply(new BigDecimal(100).divide(total, MathContext.DECIMAL128)));
            ssrCompletas.add(top[i]);
            System.out.println(top[i].getNombre() + " " + top[i].getMinVistos() + " " + top[i].getMinVisUni() + " " + top[i].getPorciento());
        }
        rpg.setTabla(ssrCompletas);
        return rpg;
    }

    @Override
    public ReporteGeneralGranulado calculoReporteGlobalNoVistos(Date fechaInicioCrea,Date fechaFinCrea,Date fechaInicio, Date fechaFin, String valorUnitarioTam, String valorUnitarioMIN, String tipoReporte, String cuenta, String unidad, String fabrica, String programa) {
        List<CatReporteXls> reporteGlobal = new ArrayList<>();
        List<CatReporteXls> reporteEntrys = new ArrayList<>();
        for (KalturaCuenta kc : consultasBDService.findAllCuenta()) {
            int totalentrys = 1;
            System.out.println("-------");
            for (KalturaUnidad ku : kc.getListaUnidad()) {
                List<Object[]> objs = new ArrayList<Object[]>();
                if (fechaInicioCrea != null && fechaFinCrea != null) {
                    objs = consultasBDService.findReporteNoVistoGloblalConCreacion(fechaInicioCrea, fechaFinCrea, ku.getNombre(), fechaInicio, fechaFin);
                } else {
                    objs = consultasBDService.findReporteNoVistoGloblal(ku.getNombre(), fechaInicio, fechaFin);
                }
                System.out.println(objs.size());
                totalentrys = GeneraReporte(reporteGlobal,reporteEntrys,objs, totalentrys,kc.getNombre(),valorUnitarioMIN,valorUnitarioTam,kc.getPartner());
                
            }
        }
        ReporteGeneralGranulado rep = new ReporteGeneralGranulado(reporteGlobal, new CartesianChartModel(), new CartesianChartModel(), new CartesianChartModel());
        rep.setTabla2(reporteEntrys);
        System.out.println(reporteEntrys.size()+" -----");
        return rep;
    }
    
    @Override
    public ReporteGeneralGranulado calculoReporteCuentaNoVistos(Date fechaInicioCrea,Date fechaFinCrea,Date fechaInicio, Date fechaFin, String valorUnitarioTam, String valorUnitarioMIN, String tipoReporte, String cuenta, String unidad, String fabrica, String programa,KalturaCuenta kcc) {
        List<CatReporteXls> reporteGlobal = new ArrayList<>();
        List<CatReporteXls> reporteEntrys = new ArrayList<>();
        for (KalturaCuenta kc : consultasBDService.findCuentaByNombre(cuenta)) {
            for (KalturaUnidad ku : kc.getListaUnidad()) {
                int totalentrys = 1;
                System.out.println("---****----");
                List<Object[]> objs = new ArrayList<Object[]>();
                if (fechaInicioCrea != null && fechaFinCrea != null) {
                    objs = consultasBDService.findReporteNoVistoGloblalConCreacion(fechaInicioCrea, fechaFinCrea, ku.getNombre(), fechaInicio, fechaFin);
                } else {
                objs=consultasBDService.findReporteNoVistoGloblal(ku.getNombre(), fechaInicio, fechaFin);
                }
//                 GeneraReporte(reporteGlobal,objs, totalentrys,ku.getNombre(),valorUnitarioMIN,valorUnitarioTam);
                System.out.println(objs.size()); 
                GeneraReporte(reporteGlobal,reporteEntrys,objs, totalentrys,ku.getNombre(),valorUnitarioMIN,valorUnitarioTam,kc.getPartner());
            } 
        }
        ReporteGeneralGranulado rep = new ReporteGeneralGranulado(reporteGlobal, new CartesianChartModel(), new CartesianChartModel(), new CartesianChartModel());
        rep.setTabla2(reporteEntrys);
        return rep;
    }
    
    @Override
    public ReporteGeneralGranulado calculoReporteUnidadVistos(Date fechaInicioCrea,Date fechaFinCrea,Date fechaInicio, Date fechaFin, String valorUnitarioTam, String valorUnitarioMIN, String tipoReporte, String cuenta, String unidad, String fabrica, String programa,KalturaCuenta kc) {
        List<CatReporteXls> reporteGlobal = new ArrayList<>();
        List<CatReporteXls> reporteEntrys = new ArrayList<>();
        for (KalturaUnidad ku : consultasBDService.findUnidadByNombre(unidad)) {
            for (KalturaFabrica kf : ku.getListaFabricas()) {
                int totalentrys = 1;
                System.out.println("---****----");
                List<Object[]> objs = new ArrayList<Object[]>();
                if (fechaInicioCrea != null && fechaFinCrea != null) {
                    objs = consultasBDService.findReporteNoVistofabricaConCreacion(fechaInicioCrea, fechaFinCrea, ku.getNombre(), fechaInicio, fechaFin,kf.getNombre());
                } else {
                objs = consultasBDService.findReporteNoVistofabrica(ku.getNombre(), fechaInicio, fechaFin,kf.getNombre());
                }
                System.out.println(objs.size());
                GeneraReporte(reporteGlobal,reporteEntrys, objs, totalentrys, kf.getNombre(), valorUnitarioMIN, valorUnitarioTam,kc.getPartner());
            }
        }
        ReporteGeneralGranulado rep = new ReporteGeneralGranulado(reporteGlobal, new CartesianChartModel(), new CartesianChartModel(), new CartesianChartModel());
        rep.setTabla2(reporteEntrys);
        return rep;
    }
    

    @Override
    public ReporteGeneralGranulado calculoReportefabricaoVistos(Date fechaInicioCrea,Date fechaFinCrea,Date fechaInicio, Date fechaFin, String valorUnitarioTam, String valorUnitarioMIN, String tipoReporte, String cuenta, String unidad, String fabrica, String programa,KalturaCuenta kc) {
        List<CatReporteXls> reporteGlobal = new ArrayList<>();
        List<CatReporteXls> reporteEntrys = new ArrayList<>();
        for (KalturaFabrica kf : consultasBDService.findFabricaByNombreAndUnidad(fabrica, consultasBDService.findUnidadByNombre(unidad).get(0))) {
            for (KalturaPrograma kp : kf.getListaProgramas()) {
                int totalentrys = 1;
                System.out.println("---****----");
                List<Object[]> objs = new ArrayList<Object[]>();
                if (fechaInicioCrea != null && fechaFinCrea != null) {
                    objs = consultasBDService.findReporteNoVistoProgramaConCreacion(fechaInicioCrea, fechaFinCrea, unidad, fechaInicio, fechaFin, kf.getNombre(), kp.getNombre());
                } else {
                    objs = consultasBDService.findReporteNoVistoPrograma(unidad, fechaInicio, fechaFin, kf.getNombre(), kp.getNombre());
                }
                System.out.println(objs.size());
                GeneraReporte(reporteGlobal,reporteEntrys, objs, totalentrys, kp.getNombre(), valorUnitarioMIN, valorUnitarioTam,kc.getPartner());
            }
        }
        
    ReporteGeneralGranulado rep = new ReporteGeneralGranulado(reporteGlobal, new CartesianChartModel(), new CartesianChartModel(), new CartesianChartModel());
    rep.setTabla2(reporteEntrys);
        return rep;
    }

    @Override
    public ReporteGeneralGranulado calculoReporteProgramaNoVistos(Date fechaInicioCrea,Date fechaFinCrea,Date fechaInicio, Date fechaFin, String valorUnitarioTam, String valorUnitarioMIN, String tipoReporte, String cuenta, String unidad, String fabrica, String programa,KalturaCuenta kc) {
        List<CatReporteXls> reporteGlobal = new ArrayList<>();
        List<Object[]> objs= new ArrayList<Object[]>(); 
                if(fechaInicioCrea!=null &&fechaFinCrea!=null){
                    objs= consultasBDService.findReporteNoVistoProgramaConCreacion(fechaInicioCrea,fechaFinCrea,unidad, fechaInicio, fechaFin, fabrica, programa);
                }else{
                objs= consultasBDService.findReporteNoVistoPrograma(unidad, fechaInicio, fechaFin, fabrica, programa);
                }
                System.out.println(objs.size());
                GeneraReporte2(reporteGlobal, objs, 0, "", valorUnitarioMIN, valorUnitarioTam,cuenta);
     

        ReporteGeneralGranulado rep = new ReporteGeneralGranulado(reporteGlobal, new CartesianChartModel(), new CartesianChartModel(), new CartesianChartModel());

        return rep;
    }
    
    private int GeneraReporte(List<CatReporteXls> reporteGlobal,List<CatReporteXls> reporteEntrys,List<Object[]> objs,int totalentrys,String nombre,String valorUnitarioMIN,String valorUnitarioTam,String partnerId){
        for(Object[] obj:objs){
                    SistemaReporte sr= new SistemaReporte((String)obj[0], (int)obj[1], (long)obj[2]);
                    sr.setDuracion(0L);
                    sr.setFechaCorte(new Date());
                    BigDecimal tamanioDeci= new BigDecimal(sr.getTamanio()).divide(new BigDecimal(1024),  MathContext.DECIMAL128).divide(new BigDecimal(1024),  MathContext.DECIMAL128);
                    BigDecimal taDeci= new BigDecimal(sr.getTamanio()).divide(new BigDecimal(1024),  MathContext.DECIMAL128).divide(new BigDecimal(1024),  MathContext.DECIMAL128);
                    if (reporteGlobal.contains(new CatReporteXls(nombre))) {
                        reporteEntrys.add(new CatReporteXls(sr.getEntryId(), partnerId));
                        CatReporteXls caRepTmp = reporteGlobal.get(reporteGlobal.indexOf(new CatReporteXls(nombre)));
                        caRepTmp.setMinVistos(caRepTmp.getMinVistos().add(new BigDecimal(sr.getTiempoVisto())));
                        caRepTmp.setDuration(caRepTmp.getDuration().add(new BigDecimal(sr.getDuracion())));
                        caRepTmp.setTamanio(caRepTmp.getTamanio().add(tamanioDeci));
                        caRepTmp.setTotalEntrys(new BigDecimal(totalentrys));
                        caRepTmp.setTamUni(caRepTmp.getTamUni().add(tamanioDeci.multiply(new BigDecimal(Double.parseDouble(valorUnitarioTam)))));
                        caRepTmp.setMinVisUni(caRepTmp.getMinVisUni().add(new BigDecimal(sr.getTiempoVisto())));
                    } else {
                        reporteEntrys.add(new CatReporteXls(sr.getEntryId(), partnerId));
                        reporteGlobal.add(new CatReporteXls(nombre, new BigDecimal(sr.getTiempoVisto()),
                                tamanioDeci, new BigDecimal(sr.getDuracion()), sr.getFechaCorte(), new BigDecimal(sr.getTiempoVisto() * Double.parseDouble(valorUnitarioMIN)),
                                new BigDecimal(tamanioDeci.floatValue() * Double.parseDouble(valorUnitarioTam)), new BigDecimal(totalentrys)));

                    }
                    totalentrys++;
                }
        return totalentrys;
    }
    
    private int GeneraReporte2(List<CatReporteXls> reporteGlobal,List<Object[]> objs,int totalentrys,String nombre,String valorUnitarioMIN,String valorUnitarioTam,String cuenta){
        for(Object[] obj:objs){
                    SistemaReporte sr= new SistemaReporte((String)obj[0], (Integer)obj[1], (long)obj[2],(long)obj[3],(String)obj[4],(String)obj[5],(String)obj[6],(Date)obj[7]);
                    sr.setNombre(sr.getEntryId());
                    sr.setDuracion(0L);
                    sr.setFechaCorte(new Date());
                    BigDecimal tamanioDeci= new BigDecimal(sr.getTamanio()).divide(new BigDecimal(1024),  MathContext.DECIMAL128).divide(new BigDecimal(1024),  MathContext.DECIMAL128);
                    BigDecimal taDeci= new BigDecimal(sr.getTamanio()).divide(new BigDecimal(1024),  MathContext.DECIMAL128).divide(new BigDecimal(1024),  MathContext.DECIMAL128);
                    if (reporteGlobal.contains(new CatReporteXls(sr.getNombre()))) {
                        CatReporteXls caRepTmp = reporteGlobal.get(reporteGlobal.indexOf(new CatReporteXls(sr.getNombre())));
                        caRepTmp.setMinVistos(caRepTmp.getMinVistos().add(new BigDecimal(sr.getTiempoVisto())));
                        caRepTmp.setDuration(caRepTmp.getDuration().add(new BigDecimal(sr.getDuracion())));
                        caRepTmp.setTamanio(caRepTmp.getTamanio().add(tamanioDeci));
                        caRepTmp.setTotalEntrys(new BigDecimal(totalentrys));
                        caRepTmp.setTamUni(caRepTmp.getTamUni().add(tamanioDeci.multiply(new BigDecimal(Double.parseDouble(valorUnitarioTam)))));
                        caRepTmp.setMinVisUni(caRepTmp.getMinVisUni().add(new BigDecimal(sr.getTiempoVisto())));
                    } else {
                        reporteGlobal.add(new CatReporteXls(sr.getNombre(),sr.getNombre(),sr.getNombreFabrica(),sr.getNombrePrograma(),cuenta,sr.getNombre(),sr.getNombreUnidad(),tamanioDeci,new BigDecimal(sr.getDuracion()), new BigDecimal(sr.getTiempoVisto())
                                 ,sr.getFechaCorte(),sr.getFechaCreacion(),new BigDecimal(sr.getTiempoVisto() * Double.parseDouble(valorUnitarioMIN)),
                                new BigDecimal(tamanioDeci.floatValue() * Double.parseDouble(valorUnitarioTam)), new BigDecimal(totalentrys)));
                        
                    }
                    totalentrys++;
                }
        return totalentrys;
    }

    @Override
    public ReporteGeneralGranulado FindCategoryByType(String category) {
        category="%"+category+"%";
        System.out.println(category+"------");
        List<CatReporteXls> reporteGlobal = new ArrayList<>();
        for(KalturaUnidad ku:consultasBDService.findLikeUnidad(category)){
            reporteGlobal.add(new CatReporteXls("Unidad", ku.getNombre(), "", "", ku.getCuenta().getNombre(), ""));
        }
        for(KalturaFabrica kf:consultasBDService.findLikeFabrica(category)){
            reporteGlobal.add(new CatReporteXls("Fabrica", kf.getNombre(), "", "", kf.getKalturaUnidad().getCuenta().getNombre(), kf.getKalturaUnidad().getNombre()));
        }
        for(KalturaPrograma kp:consultasBDService.findLikePrograma(category)){
            reporteGlobal.add(new CatReporteXls("Programa", kp.getNombre(), kp.getKalturaFabrica().getNombre(), "", kp.getKalturaFabrica().getKalturaUnidad().getCuenta().getNombre(), kp.getKalturaFabrica().getKalturaUnidad().getNombre()));
        }

        System.out.println(reporteGlobal +" "+reporteGlobal.size());
         ReporteGeneralGranulado reporteGeneralGranulado=new ReporteGeneralGranulado(reporteGlobal);
         return reporteGeneralGranulado;
    }

    

    
}
