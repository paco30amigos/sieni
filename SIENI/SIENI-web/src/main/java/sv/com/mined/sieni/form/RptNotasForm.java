/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.Date;
import java.util.List;
import org.primefaces.model.StreamedContent;
import sv.com.mined.sieni.controller.ReportesController;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniSeccion;
import sv.com.mined.sieni.pojos.rpt.RptNotasPojo;

/**
 *
 * @author francisco_medina
 */
public class RptNotasForm extends ReportesController {

    private List<RptNotasPojo> listaDatos;
    private StreamedContent reporte;
    private Integer totalTransacciones;
    private String formatoRpt;
    private Date desde;
    private Date hasta;
    private SieniGrado grado;
    private SieniSeccion seccion;
    private String anio;
    private List<SieniGrado> gradosList;
    private List<SieniSeccion> seccionesList;
    private Integer tipoRpt;

    public StreamedContent getReporte() {
        return reporte;
    }

    public void setReporte(StreamedContent reporte) {
        this.reporte = reporte;
    }

    public Integer getTotalTransacciones() {
        return totalTransacciones;
    }

    public void setTotalTransacciones(Integer totalTransacciones) {
        this.totalTransacciones = totalTransacciones;
    }

    public String getFormatoRpt() {
        return formatoRpt;
    }

    public void setFormatoRpt(String formatoRpt) {
        this.formatoRpt = formatoRpt;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public List<SieniGrado> getGradosList() {
        return gradosList;
    }

    public void setGradosList(List<SieniGrado> gradosList) {
        this.gradosList = gradosList;
    }

    public List<SieniSeccion> getSeccionesList() {
        return seccionesList;
    }

    public void setSeccionesList(List<SieniSeccion> seccionesList) {
        this.seccionesList = seccionesList;
    }

    public List<RptNotasPojo> getMatriculaList() {
        return listaDatos;
    }

    public void setMatriculaList(List<RptNotasPojo> matriculaList) {
        this.listaDatos = matriculaList;
    }

    public SieniGrado getGrado() {
        return grado;
    }

    public void setGrado(SieniGrado grado) {
        this.grado = grado;
    }

    public SieniSeccion getSeccion() {
        return seccion;
    }

    public void setSeccion(SieniSeccion seccion) {
        this.seccion = seccion;
    }

    public List<RptNotasPojo> getListaDatos() {
        return listaDatos;
    }

    public void setListaDatos(List<RptNotasPojo> listaDatos) {
        this.listaDatos = listaDatos;
    }

    public Integer getTipoRpt() {
        return tipoRpt;
    }

    public void setTipoRpt(Integer tipoRpt) {
        this.tipoRpt = tipoRpt;
    }

}
