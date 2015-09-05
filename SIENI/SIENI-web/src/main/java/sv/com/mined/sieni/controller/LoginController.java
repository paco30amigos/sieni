/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.util.Base64;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.form.LoginForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniDocente;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "loginController")
public class LoginController extends LoginForm {

    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;

    public void login(ActionEvent actionEvent) {
        FacesMessage msg = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String passEncriptado = Arrays.toString(Base64.encodeToByte((digest.digest(this.getPassword().getBytes("UTF-8"))), false));
            SieniAlumno alumno = sieniAlumnoFacadeRemote.findAlumnoUsuario(this.getUsuario(), passEncriptado);
            if (alumno == null) {
                SieniDocente docente = sieniDocenteFacadeRemote.findDocenteUsuario(this.getUsuario(), passEncriptado);
                if (docente != null) {
                    this.setLogeado(true);
                    this.setTipoUsuario("D");
                    this.setTipoRol(docente.getSieniDocentRolList().get(0).getFRolDoc() + "");
                    this.setIdUsuario(docente.getIdDocente());
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.getUsuario());
                    this.getsU().redirect("/faces/index.xhtml");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Credenciales no válidas", this.getUsuario());
                }
            } else {
                this.setLogeado(true);
                this.setTipoUsuario("A");
                this.setTipoRol(alumno.getSieniAlumnRolList().get(0).getFRol() + "");
                this.setIdUsuario(alumno.getIdAlumno());
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.getUsuario());
                this.getsU().redirect("/");
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error inesperado", this.getUsuario());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        this.getsU().redirect("/faces/login.xhtml");
        this.setLogeado(false);
    }
}