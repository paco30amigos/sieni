/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import java.io.Serializable;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniDocente;
/**
 *
 * @author PD08004
 */
public class RptUsuariosPojo  implements Serializable {
    private SieniAlumno alumnoEntity;
    private SieniDocente docenteEntity;
    private String usuario;
    private String nombre;
    private Integer tipoUser;
    private String tipo;
    private Character estado;
    private String activo;

    public RptUsuariosPojo() {
    }

    public RptUsuariosPojo(SieniAlumno alumnoEntity, SieniDocente docenteEntity, String usuario, String nombre, Integer tipoUser, Character estado) {
        this.alumnoEntity = alumnoEntity;
        this.docenteEntity = docenteEntity;
        this.usuario = usuario;
        this.nombre = nombre;
        this.tipoUser = tipoUser;
        this.estado = estado;
    }

    public SieniAlumno getAlumnoEntity() {
        return alumnoEntity;
    }

    public void setAlumnoEntity(SieniAlumno alumnoEntity) {
        this.alumnoEntity = alumnoEntity;
    }

    public SieniDocente getDocenteEntity() {
        return docenteEntity;
    }

    public void setDocenteEntity(SieniDocente docenteEntity) {
        this.docenteEntity = docenteEntity;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        switch(this.tipoUser){
            case 1: //DOCENTES
                tipo = "DOCENTE";
                break;
            case 2: //ALUMNOS
                tipo = "ALUMNO";
                break;
        }
        return tipo;
    }


    public String getActivo() {
        switch(this.estado){
            case 'A': //DOCENTES
                activo = "ACTIVO";
                break;
            case 'I': //ALUMNOS
                activo = "INACTIVO";
                break;
        }
        return activo;
    }

    
}
