/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sv.com.mined.sieni.SieniAccionFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniComponenteFacadeRemote;
import sv.com.mined.sieni.SieniEventoFacadeRemote;
import sv.com.mined.sieni.SieniSuperComponFacadeRemote;
import sv.com.mined.sieni.SieniTipoSuperComponFacadeRemote;
import sv.com.mined.sieni.form.GestionComponentesInteractivosForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniSuperCompon;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "gestionVideoClaseController")
public class GestionVideoClaseController extends GestionComponentesInteractivosForm {

    @EJB
    private SieniComponenteFacadeRemote sieniComponenteFacadeRemote;
    @EJB
    private SieniAccionFacadeRemote sieniAccionFacadeRemote;
    @EJB
    private SieniEventoFacadeRemote sieniEventoFacadeRemote;
    @EJB
    private SieniSuperComponFacadeRemote sieniSuperComponFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;
    @EJB
    private SieniTipoSuperComponFacadeRemote sieniTipoSuperComponFacadeRemote;

    @PostConstruct
    public void init() {
        fill();
    }

    private void fill() {
        this.setDatosList(sieniSuperComponFacadeRemote.findAll());
    }

    public void guardar() {
        if (validarNuevo(this.getNuevo())) {//valida el guardado
            //guardar componente
            //guardar interaccion
            sieniSuperComponFacadeRemote.create(this.getNuevo());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Archivo", this.getNuevo().getIdSuperCompon(), 'D'));
            FacesMessage msg = new FacesMessage("Archivo Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setIndexMenu(0);
        }
        this.setNuevo(new SieniSuperCompon());
        fill();
    }

    public void quitarFormato(SieniSuperCompon actual) {

    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniSuperCompon nuevo) {
        boolean ban = true;

        return ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniSuperCompon modificado) {
        this.setModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniSuperCompon eliminado) {
        this.setEliminar(eliminado);
    }

    //metodos para modificacion de datos
    public void mostrar(SieniSuperCompon ver) {
        this.setVer(ver);
        this.setIndexMenu(3);
    }

    public void guardarModifica() {
        if (validarModifica(this.getModifica())) {//valida el guardado
            sieniSuperComponFacadeRemote.edit(this.getModifica());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modifica", "Archivo", this.getModifica().getIdSuperCompon(), 'D'));
            FacesMessage msg = new FacesMessage("Archivo Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            resetModificaForm();
            this.setIndexMenu(0);
        }
        fill();
    }

    public void resetModificaForm() {
        this.setModifica(new SieniSuperCompon());
    }

    public boolean validarModifica(SieniSuperCompon nuevo) {
        boolean ban = true;

        return ban;
    }

    public void eliminarArchivo() {
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Archivo", this.getEliminar().getIdSuperCompon(), 'D'));
        sieniSuperComponFacadeRemote.remove(this.getEliminar());
        fill();
    }
}
