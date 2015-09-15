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
public class RptParticipacionPojo implements Serializable{
    
    private String alumno;
    private String curso;
    private String totalClases;
    private String clasesRecibidas;
    private String participacion;

    public RptParticipacionPojo() {
    }

    public RptParticipacionPojo(String alumno, String curso, String totalClases, String clasesRecibidas, String participacion) {
        this.alumno = alumno;
        this.curso = curso;
        this.totalClases = totalClases;
        this.clasesRecibidas = clasesRecibidas;
        this.participacion = participacion;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTotalClases() {
        return totalClases;
    }

    public void setTotalClases(String totalClases) {
        this.totalClases = totalClases;
    }

    public String getClasesRecibidas() {
        return clasesRecibidas;
    }

    public void setClasesRecibidas(String clasesRecibidas) {
        this.clasesRecibidas = clasesRecibidas;
    }

    public String getParticipacion() {
        return participacion;
    }

    public void setParticipacion(String participacion) {
        this.participacion = participacion;
    }
    
}
