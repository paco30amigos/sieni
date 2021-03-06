/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniAnioEscolar;
import sv.com.mined.sieni.model.SieniDocente;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class LoginForm {

    private siteUrls sU = new siteUrls();
    private Long idUsuario;
    private String usuario;
    private String password;
    private boolean logeado;
    private SieniDocente docente;
    private SieniAlumno alumno;
    private String tipoUsuario;
    private String tipoRol;
    private String nombreCompleto;
    private String email;
    private SieniAnioEscolar anioEscolarActivo;
    private Integer dias;
    final private Integer vencimientoContra = 90;
    private boolean pedirContrasenia;
    private String passUsr;

    public String getTipoUsuario(String cod) {
        String ret = "";
        switch (cod) {
            case "0":
                ret = "Alumno";
                break;
            case "1":
                ret = "Docente";
                break;
            case "2":
                ret = "Administrador";
                break;
            case "3":
                ret = "Subdirector";
                break;
            case "4":
                ret = "Director";
                break;
        }
        return ret;
    }

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

    public siteUrls getsU() {
        return sU;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public SieniAnioEscolar getAnioEscolarActivo() {
        return anioEscolarActivo;
    }

    public void setAnioEscolarActivo(SieniAnioEscolar anioEscolarActivo) {
        this.anioEscolarActivo = anioEscolarActivo;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Integer getVencimientoContra() {
        return vencimientoContra;
    }

    public boolean isPedirContrasenia() {
        return pedirContrasenia;
    }

    public void setPedirContrasenia(boolean pedirContrasenia) {
        this.pedirContrasenia = pedirContrasenia;
    }

    public String getPassUsr() {
        return passUsr;
    }

    public void setPassUsr(String passUsr) {
        this.passUsr = passUsr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
