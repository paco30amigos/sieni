/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos;

import sv.com.mined.sieni.model.SieniAlumnRol;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniDocentRol;
import sv.com.mined.sieni.model.SieniDocente;

/**
 *
 * @author Laptop
 */
public class UsuariosPojo {

    private Long idUsuario;
    private String usuario;
    private String tipoUsuario;
    private String tipoPermiso;
    private String nombre;
    private String pass0;
    private String pass1;
    private String pass2;
    private String codTipoUsuario;
    private long codTipoPermiso;
    private String codNombre;
    private String estado;
    private Character codEstado;
    private SieniAlumno alumno;
    private SieniDocente docente;
    private SieniAlumnRol alumnoRol;
    private SieniDocentRol docenteRol;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass0() {
        return pass0;
    }

    public void setPass0(String pass0) {
        this.pass0 = pass0;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }

    public String getCodTipoUsuario() {
        return codTipoUsuario;
    }

    public void setCodTipoUsuario(String codTipoUsuario) {
        this.codTipoUsuario = codTipoUsuario;
    }

    public long getCodTipoPermiso() {
        return codTipoPermiso;
    }

    public void setCodTipoPermiso(long codTipoPermiso) {
        this.codTipoPermiso = codTipoPermiso;
    }

    public String getCodNombre() {
        return codNombre;
    }

    public void setCodNombre(String codNombre) {
        this.codNombre = codNombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Character getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(Character codEstado) {
        this.codEstado = codEstado;
    }

    public SieniAlumno getAlumno() {
        return alumno;
    }

    public void setAlumno(SieniAlumno alumno) {
        this.alumno = alumno;
    }

    public SieniDocente getDocente() {
        return docente;
    }

    public void setDocente(SieniDocente docente) {
        this.docente = docente;
    }

    public SieniAlumnRol getAlumnoRol() {
        return alumnoRol;
    }

    public void setAlumnoRol(SieniAlumnRol alumnoRol) {
        this.alumnoRol = alumnoRol;
    }

    public SieniDocentRol getDocenteRol() {
        return docenteRol;
    }

    public void setDocenteRol(SieniDocentRol docenteRol) {
        this.docenteRol = docenteRol;
    }

}
