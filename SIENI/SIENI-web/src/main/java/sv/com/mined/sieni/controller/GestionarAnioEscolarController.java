/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import sv.com.mined.sieni.SieniAnioEscolarFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.form.GestionarAnioEscolarForm;
import sv.com.mined.sieni.model.SieniAnioEscolar;
import sv.com.mined.sieni.model.SieniBitacora;

/**
 *
 * @author Laptop
 */
@SessionScoped
@ManagedBean(name = "gestionarAnioEscolarController")
public class GestionarAnioEscolarController extends GestionarAnioEscolarForm {

    @EJB
    private SieniAnioEscolarFacadeRemote sieniAnioEscolarFacadeRemote;

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setAnioEscolarNuevo(new SieniAnioEscolar());
        this.setAnioEscolarModifica(new SieniAnioEscolar());
        this.setAnioEscolarList(new ArrayList<SieniAnioEscolar>());
        fill();
    }

    private void fill() {
        this.setAnioEscolarList(sieniAnioEscolarFacadeRemote.findAll());
    }

    public void guardar() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        if (validarNuevo(this.getAnioEscolarNuevo())) {//valida el guardado
            sieniAnioEscolarFacadeRemote.create(this.getAnioEscolarNuevo());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Año Escolar", this.getAnioEscolarNuevo().getIdAnioEscolar(), loginBean.getTipoUsuario().charAt(0), req.getRemoteAddr()));
            FacesMessage msg = new FacesMessage("Año escolar Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setIndexMenu(0);
        }
        this.setAnioEscolarNuevo(new SieniAnioEscolar());
        fill();
    }

    public void quitarFormato(SieniAnioEscolar actual) {

    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniAnioEscolar nuevo) {
        boolean ban = true;

        return ban;
    }

    public void cancelar() {
    }

    public String getEstadoCurso(String estado) {
        String ret = "";
        switch (estado) {
            case "0":
                ret = "Inactivo";
                break;
            case "1":
                ret = "Activo";
                break;
            default:
                ret = "Inactivo";
                break;
        }
        return ret;
    }

    //metodos para modificacion de datos
    public void modificar(SieniAnioEscolar modificado) {
        this.setAnioEscolarModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniAnioEscolar eliminado) {
        this.setEliminar(eliminado);
    }

    public void guardarModifica() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        if (validarModifica(this.getAnioEscolarModifica())) {//valida el guardado
            sieniAnioEscolarFacadeRemote.edit(this.getAnioEscolarModifica());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modificar", "Año Escolar", this.getAnioEscolarModifica().getIdAnioEscolar(), loginBean.getTipoUsuario().charAt(0), req.getRemoteAddr()));
            FacesMessage msg = new FacesMessage("Año escolar Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            resetModificaForm();
            this.setIndexMenu(0);
        }
        fill();
    }

    public void resetModificaForm() {
        this.setAnioEscolarModifica(new SieniAnioEscolar());
    }

    public boolean validarModifica(SieniAnioEscolar nuevo) {
        boolean ban = true;

        return ban;
    }

    public void eliminaraAnioEscolar() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Año Escolar", this.getEliminar().getIdAnioEscolar(), loginBean.getTipoUsuario().charAt(0), req.getRemoteAddr()));
        sieniAnioEscolarFacadeRemote.remove(this.getEliminar());
        fill();
    }
}
