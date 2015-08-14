/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.security.MessageDigest;
import java.util.Base64;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.form.LoginForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniDocente;

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
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String passEncriptado = Base64.getEncoder().encodeToString((digest.digest(this.getPassword().getBytes("UTF-8"))));
            SieniAlumno alumno = sieniAlumnoFacadeRemote.findAlumnoUsuario(this.getUsuario(), passEncriptado);
            if (alumno == null) {
                SieniDocente docente = sieniDocenteFacadeRemote.findDocenteUsuario(this.getUsuario(), passEncriptado);
                if (docente != null) {
                    this.setLogeado(true);
                    this.setTipoUsuario("D");
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.getUsuario());
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                            "Credenciales no válidas");
                }
            } else {
                this.setLogeado(true);
                this.setTipoUsuario("A");
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.getUsuario());
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
        this.setLogeado(false);
    }
}
