/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import java.io.Serializable;

/**
 *
 * @author alejandro.gonzalez
 */
public class RptNotasModificadasPojo implements Serializable {

    private String accion;
    private String nota;
    private String notaAnterior;
    private String fecha;
    private String hora;
    private String docente;
    private String alumno;
    private String evaluacion;

    public RptNotasModificadasPojo() {
    }

    public RptNotasModificadasPojo(String accion, String nota, String fecha, String hora, String docente, String alumno, String evaluacion, String notaAnterior) {
        this.accion = accion;
        this.nota = nota;
        this.fecha = fecha;
        this.hora = hora;
        this.docente = docente;
        this.alumno = alumno;
        this.evaluacion = evaluacion;
        this.notaAnterior = notaAnterior;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getNotaAnterior() {
        return notaAnterior;
    }

    public void setNotaAnterior(String notaAnterior) {
        this.notaAnterior = notaAnterior;
    }

}
