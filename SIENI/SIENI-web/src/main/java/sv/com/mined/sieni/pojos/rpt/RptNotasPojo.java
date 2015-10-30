/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import java.io.Serializable;

/**
 *
 * @author Laptop
 */
public class RptNotasPojo implements Serializable {

    private String alumno;
    private String grado;
    private String seccion;
    private String materia;
    private String evaluacion;
    private String nota;
    private String carnet;
    private String tipoEvaluacion;

    public RptNotasPojo() {
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
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

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getTipoEvaluacion() {
        return tipoEvaluacion;
    }

    public void setTipoEvaluacion(String tipoEvaluacion) {
        this.tipoEvaluacion = tipoEvaluacion;
    }

    public RptNotasPojo(String alumno, String grado, String seccion, String materia, String evaluacion, String nota, String carnet, String tipoEvaluacion) {
        this.alumno = alumno;
        this.grado = grado;
        this.seccion = seccion;
        this.materia = materia;
        this.evaluacion = evaluacion;
        this.nota = nota;
        this.carnet = carnet;
        this.tipoEvaluacion = tipoEvaluacion;
    }
}
