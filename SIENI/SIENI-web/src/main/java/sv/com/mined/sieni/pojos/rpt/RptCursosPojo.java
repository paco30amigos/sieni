/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import sv.com.mined.sieni.model.SieniCurso;

/**
 *
 * @author ever
 */
public class RptCursosPojo {
     private SieniCurso cursoEntity;
    private String nombre;
    private String docente;
    private String grado;
    private String seccion;
    private String Materia;
    private String capacidad;
    
    public RptCursosPojo(){}

    public RptCursosPojo(SieniCurso cursoEntity, String nombre, String docente, String grado, String seccion, String Materia, String capacidad) {
        this.cursoEntity = cursoEntity;
        this.nombre = nombre;
        this.docente = docente;
        this.grado = grado;
        this.seccion = seccion;
        this.Materia = Materia;
        this.capacidad = capacidad;
    }

    public SieniCurso getCursoEntity() {
        return cursoEntity;
    }

    public void setCursoEntity(SieniCurso cursoEntity) {
        this.cursoEntity = cursoEntity;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
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
        return Materia;
    }

    public void setMateria(String Materia) {
        this.Materia = Materia;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }
    
    
    
}
