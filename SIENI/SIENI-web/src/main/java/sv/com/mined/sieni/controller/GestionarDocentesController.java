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
import org.primefaces.event.FileUploadEvent;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.form.GestionarDocentesForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniDocente;

/**
 *
 * @author Laptop
 */
@SessionScoped
@ManagedBean(name = "gestionarDocentesController")
public class GestionarDocentesController extends GestionarDocentesForm {

    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setDocenteNuevo(new SieniDocente());
        this.setDocenteModifica(new SieniDocente());
        this.setDocentesList(new ArrayList<SieniDocente>());
        fill();
    }

    private void fill() {
        this.setDocentesList(sieniDocenteFacadeRemote.findAll());
    }

    public void guardar() {
        this.getDocenteNuevo().setDcFoto(this.getFotoArchivo());
        quitarFormato(this.getDocenteNuevo());//quita el formato de los campos
        if (validarNuevo(this.getDocenteNuevo())) {//valida el guardado
            sieniDocenteFacadeRemote.create(this.getDocenteNuevo());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guarda", "Docente", this.getDocenteNuevo().getIdDocente(), new Character('D')));
            FacesMessage msg = new FacesMessage("Expediente Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setIndexMenu(0);
        }
        this.setDocenteNuevo(new SieniDocente());
        fill();
    }

    public void quitarFormato(SieniDocente actual) {
        actual.setDcTelefonoEm1(actual.getDcTelefonoEm1().replaceAll("-", ""));
        actual.setDcTelefonoEm2(actual.getDcTelefonoEm2().replaceAll("-", ""));
    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniDocente nuevo) {
        boolean ban = true;

        return ban;
    }

    public void cancelar() {
    }

    public void getFotoNueva(FileUploadEvent event) {
        this.setFotoArchivo(event.getFile().getContents());
        this.setFotoUsable(getImage(event.getFile().getContents()));
    }

    //metodos para modificacion de datos
    public void modificar(SieniDocente modificado) {
        this.setFotoArchivoModifica(modificado.getDcFoto());
        this.setFotoUsableModifica(getImage(modificado.getDcFoto()));
        this.setDocenteModifica(modificado);
        this.setIndexMenu(2);
    }
    
    public void ver(SieniDocente modificado) {
        this.setFotoArchivoModifica(modificado.getDcFoto());
        this.setFotoUsableModifica(getImage(modificado.getDcFoto()));
        this.setDocenteModifica(modificado);
        this.setIndexMenu(3);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniDocente eliminado) {
        this.setEliminar(eliminado);
    }

    public void getFotoNuevaModifica(FileUploadEvent event) {
        this.setFotoArchivoModifica(event.getFile().getContents());
        this.setFotoUsableModifica(getImage(event.getFile().getContents()));
    }

    public void guardarModifica() {
        this.getDocenteModifica().setDcFoto(this.getFotoArchivoModifica());
        quitarFormato(this.getDocenteModifica());//quita el formato de los campos
        if (validarModifica(this.getDocenteModifica())) {//valida el guardado
            sieniDocenteFacadeRemote.edit(this.getDocenteModifica());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modificar", "Docente", this.getDocenteModifica().getIdDocente(), new Character('D')));
            FacesMessage msg = new FacesMessage("Expediente Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            resetModificaForm();
            this.setIndexMenu(0);
        }
        fill();
    }

    public void resetModificaForm() {
        this.setDocenteModifica(new SieniDocente());
        this.setFotoArchivoModifica(null);
        this.setFotoUsableModifica(null);
    }

    public boolean validarModifica(SieniDocente nuevo) {
        boolean ban = true;

        return ban;
    }

    public void eliminarExpediente() {
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Docente", this.getEliminar().getIdDocente(), new Character('D')));
        sieniDocenteFacadeRemote.remove(this.getEliminar());
        fill();
    }
}
