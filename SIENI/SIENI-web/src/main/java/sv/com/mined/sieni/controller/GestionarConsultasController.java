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
import org.primefaces.event.FileUploadEvent;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniTemaDudaFacadeRemote;
import sv.com.mined.sieni.form.GestionarConsultasForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniDocente;
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
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setConsultaNueva(new SieniTemaDuda());
        this.setConsultaModifica(new SieniTemaDuda());
        this.setConsultasList(new ArrayList<SieniTemaDuda>());
        fill();
    }

    
    private void fill() {
        this.setConsultasList(sieniConsultaFacadeRemote.findConsultasActivas());
        this.setDocentesList(sieniDocenteFacadeRemote.findDocentesActivos());
    }

        

    public void refresh() {
        fill();
    }


    //metodos para modificacion de datos
    public void modificar(SieniTemaDuda modificado) {
        this.setConsultaModifica(modificado);
        if(modificado.getIdDocente() != null){
            this.setIdDocenteModifica(modificado.getIdDocente().getIdDocente());
        }else{
            this.setIdDocenteModifica(null);
        }
        this.setIndexMenu(2);
    }

    public void ver(SieniTemaDuda modificado) {
        this.setConsultaModifica(modificado);
        this.setIndexMenu(3);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniTemaDuda eliminado) {
        this.setEliminar(eliminado);
    }


    
    public void guardar() {
//        Character tipoUsuario = ;//hay que extraer el del usuario logueado
        for (SieniDocente actual : this.getDocentesList()) {
            if (actual.getIdDocente().equals(this.getIdDocente())) {
                this.getConsultaNueva().setIdDocente(actual);
                break;
            }
        }
        if (validarNuevo(this.getConsultaNueva())) {//valida el guardado
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            this.getConsultaNueva().setTdEstado('A');
            this.getConsultaNueva().setIdAlumno(loginBean.getAlumno());
            this.getConsultaNueva().setTdTipo('C');
            this.getConsultaNueva().setTdFecha(new Date());
            sieniConsultaFacadeRemote.create(this.getConsultaNueva());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Consulta", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
            this.setConsultaNueva(new SieniTemaDuda());
            FacesMessage msg = new FacesMessage("Consulta Enviada Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            fill();
            
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
    
    public void guardarModifica() {
        for (SieniDocente actual : this.getDocentesList()) {
            if (actual.getIdDocente().equals(this.getIdDocenteModifica())) {
                this.getConsultaModifica().setIdDocente(actual);
                break;
            }
        }
        if (validarModifica(this.getConsultaModifica())) {//valida el guardado
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            
            sieniConsultaFacadeRemote.edit(this.getConsultaModifica());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modificar", "Consulta", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
            FacesMessage msg = new FacesMessage("Consulta Modificada Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            fill();
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

    
    public void eliminarConsulta() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Consulta", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
        this.getEliminar().setTdEstado(new Character('I'));
        sieniConsultaFacadeRemote.edit(this.getEliminar());
        fill();
    }
}
