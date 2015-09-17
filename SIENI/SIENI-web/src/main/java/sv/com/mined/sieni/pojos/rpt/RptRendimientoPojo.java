/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import java.io.Serializable;

/**
 *
 * @author Alejandro
 */
public class RptRendimientoPojo implements Serializable{
    
    private String grado;
    private String seccion;
    private String materia;
    private String alumnos;
    private String tipoEvaluacion;
    private String aprobados;
    private String reprobados;
    private String promedio;

    public RptRendimientoPojo() {
    }

    public RptRendimientoPojo(String grado, String seccion, String materia, String alumnos, String tipoEvaluacion, String aprobados, String reprobados, String promedio) {
        this.grado = grado;
        this.seccion = seccion;
        this.materia = materia;
        this.alumnos = alumnos;
        this.tipoEvaluacion = tipoEvaluacion;
        this.aprobados = aprobados;
        this.reprobados = reprobados;
        this.promedio = promedio;
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

    public String getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(String alumnos) {
        this.alumnos = alumnos;
    }

    public String getTipoEvaluacion() {
        return tipoEvaluacion;
    }

    public void setTipoEvaluacion(String tipoEvaluacion) {
        this.tipoEvaluacion = tipoEvaluacion;
    }

    public String getAprobados() {
        return aprobados;
    }

    public void setAprobados(String aprobados) {
        this.aprobados = aprobados;
    }

    public String getReprobados() {
        return reprobados;
    }

    public void setReprobados(String reprobados) {
        this.reprobados = reprobados;
    }

    public String getPromedio() {
        return promedio;
    }

    public void setPromedio(String promedio) {
        this.promedio = promedio;
    }

}
