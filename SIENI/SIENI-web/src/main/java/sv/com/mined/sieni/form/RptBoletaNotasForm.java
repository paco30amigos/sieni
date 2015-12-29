/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.primefaces.model.StreamedContent;
import sv.com.mined.sieni.controller.ReportesController;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniSeccion;
import sv.com.mined.sieni.pojos.rpt.RptBoletaNotasPojo;

/**
 *
 * @author francisco_medina
 */
public class RptBoletaNotasForm extends ReportesController {

    private List<RptBoletaNotasPojo> listaDatos;
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
    private Date trimestre1;
    private Date trimestre2;
    private Date trimestre3;
    private Date trimestre4;
    private BigDecimal total;
    private SieniAlumno alumno;
    private List<SieniAlumno> alumnos;

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

    public List<RptBoletaNotasPojo> getMatriculaList() {
        return listaDatos;
    }

    public void setMatriculaList(List<RptBoletaNotasPojo> matriculaList) {
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

    public List<RptBoletaNotasPojo> getListaDatos() {
        return listaDatos;
    }

    public void setListaDatos(List<RptBoletaNotasPojo> listaDatos) {
        this.listaDatos = listaDatos;
    }

    public Integer getTipoRpt() {
        return tipoRpt;
    }

    public void setTipoRpt(Integer tipoRpt) {
        this.tipoRpt = tipoRpt;
    }

    public Date getTrimestre1() {
        return trimestre1;
    }

    public void setTrimestre1(Date trimestre1) {
        this.trimestre1 = trimestre1;
    }

    public Date getTrimestre2() {
        return trimestre2;
    }

    public void setTrimestre2(Date trimestre2) {
        this.trimestre2 = trimestre2;
    }

    public Date getTrimestre3() {
        return trimestre3;
    }

    public void setTrimestre3(Date trimestre3) {
        this.trimestre3 = trimestre3;
    }

    public Date getTrimestre4() {
        return trimestre4;
    }

    public void setTrimestre4(Date trimestre4) {
        this.trimestre4 = trimestre4;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public SieniAlumno getAlumno() {
        return alumno;
    }

    public void setAlumno(SieniAlumno alumno) {
        this.alumno = alumno;
    }

    public List<SieniAlumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<SieniAlumno> alumnos) {
        this.alumnos = alumnos;
    }

}
