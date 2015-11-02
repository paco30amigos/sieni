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
import org.springframework.validation.ValidationUtils;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.form.LoginForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
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
            if ((this.getUsuario() != null && this.getUsuario().length() > 30)
                    || this.getPassword() != null && this.getPassword().length() > 30) {
                new ValidationPojo().printMsj("Credenciales no válidas ", FacesMessage.SEVERITY_ERROR);
            } else {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                String passEncriptado = Arrays.toString(Base64.encodeToByte((digest.digest(this.getPassword().getBytes("UTF-8"))), false));
                SieniAlumno alumno = sieniAlumnoFacadeRemote.findAlumnoUsuario(this.getUsuario(), passEncriptado);
                if (alumno == null) {
                    SieniDocente docente = sieniDocenteFacadeRemote.findDocenteUsuario(this.getUsuario(), passEncriptado);
                    if (docente != null) {
                        if (docente.getDcEstado() != null && docente.getDcEstado().equals('A')) {
                            this.setLogeado(true);
                            this.setTipoUsuario("D");
                            this.setTipoRol(docente.getSieniDocentRolList().get(0).getFRolDoc() + "");
                            this.setIdUsuario(docente.getIdDocente());
                            this.setNombreCompleto(docente.getNombreCompleto());
                            this.setDocente(docente);
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.getUsuario());
                            this.getsU().redirect("/faces/index.xhtml");
                        } else {
                            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario inactivo", this.getUsuario());
                        }
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales no válidas", this.getUsuario());
                    }
                } else {
                    if (alumno.getAlEstado() != null && alumno.getAlEstado().equals('A')) {
                        this.setLogeado(true);
                        this.setTipoUsuario("A");
                        this.setTipoRol(alumno.getSieniAlumnRolList().get(0).getFRol() + "");
                        this.setIdUsuario(alumno.getIdAlumno());
                        this.setNombreCompleto(alumno.getNombreCompleto());
                        this.setAlumno(alumno);
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.getUsuario());
                        this.getsU().redirect("/");
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario inactivo", this.getUsuario());
                    }
                }
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

    public void cambiarPass() {
        this.getsU().redirect("/faces/password.xhtml");
    }

    public boolean mostrarA(String permitidos) {
        boolean ban = false;
        String[] usuario = permitidos.split(",");
        for (String actual : usuario) {
            if (this.getTipoRol().equals(actual)) {
                ban = true;
                break;
            }
        }
        return ban;
    }
}
