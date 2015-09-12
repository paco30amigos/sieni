///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package sv.com.mined.sieni.form;
//
//import java.util.Date;
//import java.util.List;
//import org.primefaces.model.StreamedContent;
//import sv.com.mined.sieni.controller.ReportesController;
//import sv.com.mined.sieni.model.SieniMatricula;
//import sv.com.mined.sieni.pojos.rpt.RptMatriculasPojo;
//
///**
// *
// * @author francisco_medina
// */
//public class RptMatriculaForm extends ReportesController{
//
//    private List<SieniMatricula> matriculaList;
//    private StreamedContent reporte;
//    private Long totalTransacciones;
//    private String formatoRpt;
//    private Date desde;
//    private Date hasta;
//    private String anio;
//    private List<RptMatriculasPojo> listDatos;
//    private Integer tipoRpt;
//
//    public List<SieniMatricula> getMatriculaList() {
//        return matriculaList;
//    }
//
//    public void setMatriculaList(List<SieniMatricula> matriculaList) {
//        this.matriculaList = matriculaList;
//    }
//
//    public StreamedContent getReporte() {
//        return reporte;
//    }
//
//    public void setReporte(StreamedContent reporte) {
//        this.reporte = reporte;
//    }
//
//    public Long getTotalTransacciones() {
//        return totalTransacciones;
//    }
//
//    public void setTotalTransacciones(Long totalTransacciones) {
//        this.totalTransacciones = totalTransacciones;
//    }
//
//    public String getFormatoRpt() {
//        return formatoRpt;
//    }
//
//    public void setFormatoRpt(String formatoRpt) {
//        this.formatoRpt = formatoRpt;
//    }
//
//    public Date getDesde() {
//        return desde;
//    }
//
//    public void setDesde(Date desde) {
//        this.desde = desde;
//    }
//
//    public Date getHasta() {
//        return hasta;
//    }
//
//    public void setHasta(Date hasta) {
//        this.hasta = hasta;
//    }
//
//    public String getAnio() {
//        return anio;
//    }
//
//    public void setAnio(String anio) {
//        this.anio = anio;
//    }
//
//    public List<RptMatriculasPojo> getListDatos() {
//        return listDatos;
//    }
//
//    public void setListDatos(List<RptMatriculasPojo> listDatos) {
//        this.listDatos = listDatos;
//    }
//
//    public Integer getTipoRpt() {
//        return tipoRpt;
//    }
//
//    public void setTipoRpt(Integer tipoRpt) {
//        this.tipoRpt = tipoRpt;
//    }
//
//}
