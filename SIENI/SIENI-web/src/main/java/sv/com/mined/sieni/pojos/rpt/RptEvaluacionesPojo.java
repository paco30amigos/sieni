/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniEvaluacion;

/**
 *
 * @author ever
 */
public class RptEvaluacionesPojo {

    private SieniEvaluacion evaluacionEntity;
    private String docente;
    private String gradoResponsable;
    private String nombreEvaluacion;
    private String ponderacion;
    private String fechaInicio;
    private String fechaCierre;

    public RptEvaluacionesPojo() {
    }

   

    public RptEvaluacionesPojo(SieniEvaluacion evaluacionEntity, String docente, String gradoResponsable, String nombreEvaluacion, String ponderacion, String fechaInicio, String fechaCierre) {
        this.evaluacionEntity = evaluacionEntity;
        this.docente = docente;
        this.gradoResponsable = gradoResponsable;
        this.nombreEvaluacion = nombreEvaluacion;
        this.ponderacion = ponderacion;
        this.fechaInicio = fechaInicio;
        this.fechaCierre = fechaCierre;
    }

    
    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    
    public String getGradoResponsable() {
        return gradoResponsable;
    }

    public void setGradoResponsable(String gradoResponsable) {
        this.gradoResponsable = gradoResponsable;
    }

    public SieniEvaluacion getEvaluacionEntity() {
        return evaluacionEntity;
    }

    public void setEvaluacionEntity(SieniEvaluacion evaluacionEntity) {
        this.evaluacionEntity = evaluacionEntity;
    }

    public String getNombreEvaluacion() {
        return nombreEvaluacion;
    }

    public void setNombreEvaluacion(String nombreEvaluacion) {
        this.nombreEvaluacion = nombreEvaluacion;
    }

    public String getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(String ponderacion) {
        this.ponderacion = ponderacion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

}
