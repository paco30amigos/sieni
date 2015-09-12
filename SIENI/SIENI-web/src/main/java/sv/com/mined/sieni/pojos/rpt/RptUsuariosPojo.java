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
    private String tipo;
    private String activo;

    public RptUsuariosPojo() {
    }

    public RptUsuariosPojo(SieniAlumno alumnoEntity, SieniDocente docenteEntity, String usuario, String nombre, String tipo, String activo) {
        this.alumnoEntity = alumnoEntity;
        this.docenteEntity = docenteEntity;
        this.usuario = usuario;
        this.nombre = nombre;
        this.tipo = tipo;
        this.activo = activo;
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
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
    
    
}
