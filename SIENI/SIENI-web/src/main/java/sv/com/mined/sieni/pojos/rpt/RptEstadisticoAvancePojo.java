/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import java.io.Serializable;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniSeccion;

/**
 *
 * @author INFORMATICA
 */
public class RptEstadisticoAvancePojo implements Serializable {
    
    private SieniClase claseEntity;
    private SieniAlumno alumnoEntity;
    
    private String grado;
    private String seccion;
    private String materia;
    private String alumno;
    private String tipoevaluacion;
    private float porcentaje;
    private float promedio;
    private float varianza;

    public RptEstadisticoAvancePojo() {
    }

    public RptEstadisticoAvancePojo(SieniClase claseEntity, SieniAlumno alumnoEntity, String grado, String seccion, String materia, String alumno, String tipoevaluacion) {
        this.claseEntity = claseEntity;
        this.alumnoEntity = alumnoEntity;
        this.grado = grado;
        this.seccion = seccion;
        this.materia = materia;
        this.alumno = alumno;
        this.tipoevaluacion = tipoevaluacion;
    }

    public SieniClase getClaseEntity() {
        return claseEntity;
    }

    public SieniAlumno getAlumnoEntity() {
        return alumnoEntity;
    }

    public String getGrado() {
        return grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public String getMateria() {
        return materia;
    }

    public String getAlumno() {
        return alumno;
    }

    public String getTipoevaluacion() {
        return tipoevaluacion;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public float getPromedio() {
        return promedio;
    }

    public float getVarianza() {
        return varianza;
    }

    

    
}
