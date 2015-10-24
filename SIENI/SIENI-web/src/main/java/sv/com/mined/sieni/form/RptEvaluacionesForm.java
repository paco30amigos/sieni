
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.Date;
import java.util.List;
import sv.com.mined.sieni.controller.ReportesController;
import sv.com.mined.sieni.pojos.rpt.RptDocentesPojo;
import sv.com.mined.sieni.pojos.rpt.RptEvaluacionesPojo;

/**
 *
 * @author ever
 */
public class RptEvaluacionesForm extends ReportesController{
    
    private Long idGrado;
    private Long idSeccion;
    private String grado;
    private String seccion;
    private List<RptEvaluacionesPojo> listDatos;
     private Integer tipoRpt;
     private Date desde;
     private Date hasta;

   

   

    public Long getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(Long idGrado) {
        this.idGrado = idGrado;
    }

    public Long getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Long idSeccion) {
        this.idSeccion = idSeccion;
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

    public List<RptEvaluacionesPojo> getListDatos() {
        return listDatos;
    }

    public void setListDatos(List<RptEvaluacionesPojo> listDatos) {
        this.listDatos = listDatos;
    }

    public Integer getTipoRpt() {
        return tipoRpt;
    }

    public void setTipoRpt(Integer tipoRpt) {
        this.tipoRpt = tipoRpt;
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
     
     
    
}
