/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import java.io.Serializable;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniGrado;

/**
 *
 * @author Laptop
 */
public class RptMatriculasPojo implements Serializable {

    private String matricula;
    private String anio;
    private String carnet;
    private String alumno;
    private String grado;
    private String seccion;

    public RptMatriculasPojo() {
    }

    public RptMatriculasPojo(String matricula, String anio, String carnet, String alumno, String grado, String seccion) {
        this.matricula = matricula;
        this.anio = anio;        
        this.carnet = carnet;
        this.alumno = alumno;
        this.grado = grado;
        this.seccion = seccion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
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

}
