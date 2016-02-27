/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.util.Base64;
import sv.com.mined.sieni.SieniAlumnRolFacadeRemote;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniAnioEscolarFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniDocentRolFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniTemaDudaFacadeRemote;
import sv.com.mined.sieni.form.LoginForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniDocentRol;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniTemaDuda;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.DateUtils;
import utils.EmailValidator;
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
    private SieniAnioEscolarFacadeRemote sieniAnioEscolarFacadeRemote;
    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;
    @EJB
    private SieniAlumnRolFacadeRemote sieniAlumnRolFacadeRemote;
    @EJB
    private SieniDocentRolFacadeRemote sieniDocentRolFacadeRemote;

    @EJB
    private SieniTemaDudaFacadeRemote sieniConsultaFacadeRemote;

    public void onIdle() {
        logout();
        new siteUrls().redirect("/login.xhtml");
    }

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
                    this.setPassUsr(passEncriptado);
                    if (docente != null) {
                        if (docente.getDcEstado() != null && docente.getDcEstado().equals('A')) {
                            this.setDias(new DateUtils().getDiasAntesVencimientoContra(docente.getDcFechaContrasenia(), this.getVencimientoContra()));
                            this.setPedirContrasenia(true);
                            this.setLogeado(true);
                            this.setTipoUsuario("D");
                            List<SieniDocentRol> r = sieniDocentRolFacadeRemote.findRoles(docente.getIdDocente());
                            this.setTipoRol(r.get(0).getFRolDoc() + "");
                            this.setIdUsuario(docente.getIdDocente());
                            this.setNombreCompleto(docente.getNombreCompleto());
                            this.setDocente(docente);
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.getUsuario());
                            this.getsU().redirect("/faces/index.xhtml");
                            this.setAnioEscolarActivo(sieniAnioEscolarFacadeRemote.findActivo());
                        } else {
                            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario inactivo", this.getUsuario());
                        }
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales no válidas", this.getUsuario());
                    }
                } else {
                    if (alumno.getAlEstado() != null && alumno.getAlEstado().equals('A')) {
                        this.setDias(new DateUtils().getDiasAntesVencimientoContra(alumno.getAlFechaContrasenia(), this.getVencimientoContra()));
                        this.setPassUsr(passEncriptado);
                        this.setPedirContrasenia(true);
                        this.setLogeado(true);
                        this.setTipoUsuario("A");
                        this.setTipoRol(sieniAlumnRolFacadeRemote.findRolesAlumno(alumno.getIdAlumno()).get(0).getFRol() + "");
                        this.setIdUsuario(alumno.getIdAlumno());
                        this.setNombreCompleto(alumno.getNombreCompleto());
                        this.setAlumno(alumno);
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.getUsuario());
                        this.getsU().redirect("/");
                        this.setAnioEscolarActivo(sieniAnioEscolarFacadeRemote.findActivo());
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

    public void registrarTransaccion(String accion, String tabla, Long regAfectado) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), accion, tabla, this.getIdUsuario(), this.getTipoUsuario().charAt(0), req.getRemoteAddr(), regAfectado));
    }

    public void registrarCambioPass() {
        SieniTemaDuda duda = new SieniTemaDuda();

        EmailValidator ev = new EmailValidator();
        if (ev.validate(this.getEmail())) {
            SieniAlumno alumno = sieniAlumnoFacadeRemote.findByNombreCompleto(this.getNombreCompleto());
            SieniDocente docente = sieniDocenteFacadeRemote.findByNombreCompleto(this.getNombreCompleto());
            List<SieniDocentRol> docentes = sieniDocentRolFacadeRemote.findAdmins();
            if (alumno != null) {
                for (SieniDocentRol dc : docentes) {
                    duda = new SieniTemaDuda();
                    duda.setIdDocente(dc.getIdDocente());
                    duda.setIdAlumno(alumno.getIdAlumno());
                    duda.setTdConsulta("Necesito reestablecer contraseña: " + this.getNombreCompleto() + ", usuario: " + this.getUsuario());
                    duda.setTdTema("Contraseña olvidada");
                    duda.setTdTipo('C');
                    duda.setTdFecha(new Date());
                    duda.setTdEstado('A');
                    duda.setTdTipoUsr("A");
                    sieniConsultaFacadeRemote.create(duda);
                }
                new ValidationPojo().printMsj("Su solicitud ha sido enviada al administrador del sistema", FacesMessage.SEVERITY_INFO);
            } else if (docente != null) {
                for (SieniDocentRol dc : docentes) {
                    duda = new SieniTemaDuda();
                    duda.setIdDocente(dc.getIdDocente());
                    duda.setIdAlumno(docente.getIdDocente());
                    duda.setTdConsulta("Necesito reestablecer contraseña: " + this.getNombreCompleto() + ", usuario: " + this.getUsuario() + ", correo electrónico: " + this.getEmail());
                    duda.setTdTema("Contraseña olvidada");
                    duda.setTdTipo('C');
                    duda.setTdFecha(new Date());
                    duda.setTdEstado('A');
                    duda.setTdTipoUsr("D");
                    sieniConsultaFacadeRemote.create(duda);
                }
            } else {
                new ValidationPojo().printMsj("El nombre no corresponde a ningun usuario", FacesMessage.SEVERITY_ERROR);
            }
        } else {
            new ValidationPojo().printMsj("Correo Electrónico no valido", FacesMessage.SEVERITY_ERROR);
        }
    }
}
