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
import sv.com.mined.sieni.pojos.rpt.RptRendimientoPojo;

/**
 *
 * @author Alejandro
 */
public class RptRendimientoForm extends ReportesController {

    private StreamedContent reporte;
    private Long totalTransacciones;
    private String formatoRpt;
    private Date desde;
    private Date hasta;
    private String grado;
    private String seccion;
    private String materia;
    private List<RptRendimientoPojo> listDatos;
    private Integer tipoRpt;

    public StreamedContent getReporte() {
        return reporte;
    }

    public void setReporte(StreamedContent reporte) {
        this.reporte = reporte;
    }

    public Long getTotalTransacciones() {
        return totalTransacciones;
    }

    public void setTotalTransacciones(Long totalTransacciones) {
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

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public List<RptRendimientoPojo> getListDatos() {
        return listDatos;
    }

    public void setListDatos(List<RptRendimientoPojo> listDatos) {
        this.listDatos = listDatos;
    }

    public Integer getTipoRpt() {
        return tipoRpt;
    }

    public void setTipoRpt(Integer tipoRpt) {
        this.tipoRpt = tipoRpt;
    }
    
}
