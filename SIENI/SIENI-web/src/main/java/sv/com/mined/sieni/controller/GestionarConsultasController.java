/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniResolDudaFacadeRemote;
import sv.com.mined.sieni.SieniTemaDudaFacadeRemote;
import sv.com.mined.sieni.form.GestionarConsultasForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniResolDuda;
import sv.com.mined.sieni.model.SieniTemaDuda;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.DateUtils;
import utils.EmailValidator;
import utils.FormatUtils;

/**
 *
 * @author INFORMATICA
 */
@SessionScoped
@ManagedBean(name = "gestionarConsultasController")
public class GestionarConsultasController extends GestionarConsultasForm {

    @EJB
    private SieniTemaDudaFacadeRemote sieniConsultaFacadeRemote;
    @EJB
    private SieniResolDudaFacadeRemote sieniResolDudaFacadeRemote;
    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);
    }

    @PostConstruct
    public void init() {
        this.setConsultaNueva(new SieniTemaDuda());
        this.setConsultaModifica(new SieniTemaDuda());
        this.setConsultasList(new ArrayList<SieniTemaDuda>());
        fill();
    }

    private List<SieniTemaDuda> setDocente(List<SieniTemaDuda> matriculas) {
        List<SieniTemaDuda> ret = new ArrayList<>();
        for (SieniTemaDuda actual : matriculas) {
            ret.add(setInfoDocente(actual));
        }
        return ret;
    }

    public SieniTemaDuda setInfoDocente(SieniTemaDuda matActual) {
        matActual.setDocente(sieniDocenteFacadeRemote.findByDocenteId(matActual.getIdDocente()));
        return matActual;
    }

    private List<SieniTemaDuda> setAlumno(List<SieniTemaDuda> matriculas) {
        List<SieniTemaDuda> ret = new ArrayList<>();
        for (SieniTemaDuda actual : matriculas) {
            ret.add(setInfoDocente(actual));
        }
        return ret;
    }

    public SieniTemaDuda setInfoAlumno(SieniTemaDuda matActual) {
        matActual.setAlumno(sieniAlumnoFacadeRemote.findAlumnoById(matActual.getIdAlumno()));
        return matActual;
    }

    private void fill() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");

        if (loginBean.getDocente() != null) {
            this.setConsultasList(sieniConsultaFacadeRemote.findConsultasActivasByDocente(loginBean.getDocente()));
            for (SieniTemaDuda cta : this.getConsultasList()) {
                if (cta.getTdTipoUsr().equals("D")) {
                    SieniDocente dct = sieniDocenteFacadeRemote.findByDocenteId(cta.getIdAlumno());
                    cta.setNombreAl(dct.getNombreCompleto());
                } else {
                    SieniAlumno alumno = sieniAlumnoFacadeRemote.findAlumnoById(cta.getIdAlumno());
                    cta.setNombreAl(alumno.getNombreCompleto());
                }
                SieniDocente dct = sieniDocenteFacadeRemote.findByDocenteId(cta.getIdDocente());
                cta.setNombreDc(dct.getNombreCompleto());
            }
        } else if (loginBean.getAlumno() != null) {
            this.setConsultasList(sieniConsultaFacadeRemote.findConsultasActivasByAlumno(loginBean.getAlumno()));
            for (SieniTemaDuda cta : this.getConsultasList()) {
                if (cta.getTdTipoUsr().equals("D")) {
                    SieniDocente dct = sieniDocenteFacadeRemote.findByDocenteId(cta.getIdAlumno());
                    cta.setNombreAl(dct.getNombreCompleto());
                } else {
                    SieniAlumno alumno = sieniAlumnoFacadeRemote.findAlumnoById(cta.getIdAlumno());
                    cta.setNombreAl(alumno.getNombreCompleto());
                }
                SieniDocente dct = sieniDocenteFacadeRemote.findByDocenteId(cta.getIdDocente());
                cta.setNombreDc(dct.getNombreCompleto());
            }
        } else {
            this.setConsultasList(new ArrayList<SieniTemaDuda>());
        }

        this.setDocentesList(sieniDocenteFacadeRemote.findDocentesActivos());
    }

    public void refresh() {
        fill();
    }

    //metodos para modificacion de datos
    public void modificar(SieniTemaDuda modificado) {
        this.setConsultaModifica(modificado);
        if (modificado.getTdTipoUsr().equals("D")) {
            this.setIdDocenteModifica(modificado.getIdDocente());
        } else {
            this.setIdDocenteModifica(null);
        }
        this.setIndexMenu(2);
    }

    public void ver(SieniTemaDuda modificado) {
        this.setConsultaModifica(modificado);
        if (this.getConsultaModifica().getTdTipoUsr().equals("D")) {
            this.getConsultaModifica().setDocente(sieniDocenteFacadeRemote.findByDocenteId(this.getConsultaModifica().getIdDocente()));
        } else {
            this.getConsultaModifica().setAlumno(sieniAlumnoFacadeRemote.findAlumnoById(this.getConsultaModifica().getIdAlumno()));
        }
        this.setRespuesta(new SieniResolDuda());
        fillRespuestasConsulta();
        this.setIndexMenu(3);
    }

    private void fillRespuestasConsulta() {
        if (this.getConsultaModifica() != null) {
            this.getConsultaModifica().setSieniResolDudaList(sieniResolDudaFacadeRemote.findByConsulta(this.getConsultaModifica()));
            if (this.getConsultaModifica().getSieniResolDudaList() != null) {
                for (SieniResolDuda r : this.getConsultaModifica().getSieniResolDudaList()) {
                    if (r.getRdTipoUsr().equals("D")) {
                        r.setDocente(sieniDocenteFacadeRemote.findByDocenteId(r.getIdDocente()));
                    } else {
                        r.setAlumno(sieniAlumnoFacadeRemote.findAlumnoById(r.getIdAlumno()));
                    }
                }
            }
        }
    }

    //metodos para modificacion de datos
    public void eliminar(SieniTemaDuda eliminado) {
        this.setEliminar(eliminado);
    }

    public synchronized void guardar() {
        try {
//        Character tipoUsuario = ;//hay que extraer el del usuario logueado
            for (SieniDocente actual : this.getDocentesList()) {
                if (actual.getIdDocente().equals(this.getIdDocente())) {
                    this.getConsultaNueva().setIdDocente(actual.getIdDocente());
                    break;
                }
            }
            if (validarNuevo(this.getConsultaNueva())) {//valida el guardado
                HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
                this.getConsultaNueva().setTdEstado('A');
                if (loginBean.getAlumno() != null) {
                    this.getConsultaNueva().setIdAlumno(loginBean.getAlumno().getIdAlumno());
                } else {
                    this.getConsultaNueva().setIdAlumno(loginBean.getDocente().getIdDocente());
                }
                this.getConsultaNueva().setTdTipo('C');
                this.getConsultaNueva().setTdFecha(new Date());
                this.getConsultaNueva().setTdTipoUsr(loginBean.getTipoUsuario());
                this.setConsultaNueva(sieniConsultaFacadeRemote.createAndReturn(this.getConsultaNueva()));
                registrarEnBitacora("Crear", "Consulta", this.getConsultaNueva().getIdTemaDuda());

                this.setConsultaNueva(new SieniTemaDuda());
                FacesMessage msg = new FacesMessage("Consulta Enviada Exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                fill();

                FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
                NotificacionesController notifyBean = (NotificacionesController) context.getApplication().getELResolver().getValue(context.getELContext(), null, "notificacionesController");
                notifyBean.notificarPUSH("Nueva Consulta");
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public boolean validarNuevo(SieniTemaDuda nuevo) {
        boolean valido = true;
        DateUtils du = new DateUtils();
        FormatUtils fu = new FormatUtils();
        EmailValidator ev = new EmailValidator();
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public synchronized void guardarModifica() {
        try {
            for (SieniDocente actual : this.getDocentesList()) {
                if (actual.getIdDocente().equals(this.getIdDocenteModifica())) {
                    this.getConsultaModifica().setIdDocente(actual.getIdDocente());
                    break;
                }
            }
            if (validarModifica(this.getConsultaModifica())) {//valida el guardado
                sieniConsultaFacadeRemote.edit(this.getConsultaModifica());
                registrarEnBitacora("Modificar", "Consulta", this.getConsultaModifica().getIdTemaDuda());
                FacesMessage msg = new FacesMessage("Consulta Modificada Exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                fill();
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public boolean validarModifica(SieniTemaDuda nuevo) {
        boolean valido = true;
        DateUtils du = new DateUtils();
        FormatUtils fu = new FormatUtils();
        EmailValidator ev = new EmailValidator();
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        //alumno ya registrado
        boolean cambio = true;
        SieniTemaDuda alOriginal = sieniConsultaFacadeRemote.find(this.getConsultaModifica().getIdTemaDuda());
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public void resetModificaForm() {
        this.setConsultaModifica(new SieniTemaDuda());
        this.setIdDocenteModifica(null);
    }

    public boolean diferencia(String original, String modificado) {
        boolean ret = true;
        if (modificado != null && original != null) {
            if (!modificado.equals(original)) {
                ret = false;
            }
        } else {
            if (!((modificado == null && original != null) || modificado != null && original == null)) {
                ret = false;
            }
        }
        return ret;
    }

    public synchronized void eliminarConsulta() {
        try {
            registrarEnBitacora("Eliminar", "Consulta", this.getEliminar().getIdTemaDuda());
            this.getEliminar().setTdEstado(new Character('I'));
            sieniConsultaFacadeRemote.edit(this.getEliminar());
            fill();
            new ValidationPojo().printMsj("Registro eliminado exitosamente", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public synchronized void publicar() {
        try {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            this.getRespuesta().setIdTemaDuda(this.getConsultaModifica());
            this.getRespuesta().setRdFecha(new Date());
            this.getRespuesta().setRdTipoUsr(loginBean.getTipoUsuario());
            if (loginBean.getAlumno() != null) {
                this.getRespuesta().setIdAlumno(loginBean.getAlumno().getIdAlumno());
            }
            if (loginBean.getDocente() != null) {
                this.getRespuesta().setIdDocente(loginBean.getDocente().getIdDocente());
            }

            this.setRespuesta(sieniResolDudaFacadeRemote.createAndReturn(this.getRespuesta()));
            registrarEnBitacora("Crear", "Responder Consulta", this.getRespuesta().getIdResolDuda());
            this.setRespuesta(new SieniResolDuda());
            FacesMessage msg = new FacesMessage("Respuesta enviada Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            fillRespuestasConsulta();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

}
