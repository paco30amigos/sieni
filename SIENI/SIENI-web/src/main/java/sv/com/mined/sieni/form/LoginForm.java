/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniDocente;

/**
 *
 * @author francisco_medina
 */
public class LoginForm {

    private String usuario;
    private String password;
    private boolean logeado;
    private SieniDocente docente;
    private SieniAlumno alumno;
    private String tipoUsuario;
    private String tipoRol;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogeado() {
        return logeado;
    }

    public void setLogeado(boolean logeado) {
        this.logeado = logeado;
    }

    public SieniDocente getDocente() {
        return docente;
    }

    public void setDocente(SieniDocente docente) {
        this.docente = docente;
    }

    public SieniAlumno getAlumno() {
        return alumno;
    }

    public void setAlumno(SieniAlumno alumno) {
        this.alumno = alumno;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoRol() {
        return tipoRol;
    }

    public void setTipoRol(String tipoRol) {
        this.tipoRol = tipoRol;
    }

}
