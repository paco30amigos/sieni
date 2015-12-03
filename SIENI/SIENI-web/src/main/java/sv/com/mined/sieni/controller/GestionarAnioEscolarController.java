/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.ArrayList;
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
import sv.com.mined.sieni.pojos.controller.ValidationPojo;

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

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);
    }

    @PostConstruct
    public void init() {
        this.setAnioEscolarNuevo(new SieniAnioEscolar());
        this.setAnioEscolarModifica(new SieniAnioEscolar());
        this.setAnioEscolarList(new ArrayList<SieniAnioEscolar>());
        this.getAnioEscolarNuevo().setAeEstado('A');
        fill();
    }

    private void fill() {
        this.setAnioEscolarList(sieniAnioEscolarFacadeRemote.findAllNoInactivos());
    }

    public synchronized void guardar() {
        try {
            if (validarNuevo(this.getAnioEscolarNuevo())) {//valida el guardado            
                this.setAnioEscolarNuevo(sieniAnioEscolarFacadeRemote.createAndReturn(this.getAnioEscolarNuevo()));
                registrarEnBitacora("Crear", "Anio Escolar", this.getAnioEscolarNuevo().getIdAnioEscolar());
                FacesMessage msg = new FacesMessage("Año escolar Creado Exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                this.setIndexMenu(0);
            }
            this.setAnioEscolarNuevo(new SieniAnioEscolar());
            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
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

    //metodos para modificacion de datos
    public void modificar(SieniAnioEscolar modificado) {
        this.setAnioEscolarModifica(modificado);
        this.setIndexMenu(2);
    }

    public void ver(SieniAnioEscolar modificado) {
        this.setAnioEscolarModifica(modificado);
        this.setIndexMenu(3);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniAnioEscolar eliminado) {
        this.setEliminar(eliminado);
    }

    public synchronized  void guardarModifica() {
        try {
            if (validarModifica(this.getAnioEscolarModifica())) {//valida el guardado
                sieniAnioEscolarFacadeRemote.edit(this.getAnioEscolarModifica());
                registrarEnBitacora("Modificar", "Anio Escolar", this.getAnioEscolarModifica().getIdAnioEscolar());
                FacesMessage msg = new FacesMessage("Año escolar Modificado Exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                resetModificaForm();
                this.setIndexMenu(0);
            }
            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void resetModificaForm() {
        this.setAnioEscolarModifica(new SieniAnioEscolar());
    }

    public boolean validarModifica(SieniAnioEscolar nuevo) {
        boolean ban = true;

        return ban;
    }

    public synchronized void eliminaraAnioEscolar() {
        try {
            registrarEnBitacora("Eliminar", "Anio Escolar", this.getEliminar().getIdAnioEscolar());
            this.getEliminar().setAeEstado('I');
            sieniAnioEscolarFacadeRemote.edit(this.getEliminar());
            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }
}
