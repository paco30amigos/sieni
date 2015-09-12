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
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniArchivoFacadeRemote;
import sv.com.mined.sieni.form.GestionArchivoMultimediaForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniArchivo;
import utils.CopiaArchivos;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "gestionArchivoMultimediaController")
public class GestionArchivoMultimediaController extends GestionArchivoMultimediaForm {

    @EJB
    private SieniArchivoFacadeRemote sieniArchivoFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setArchivoNuevo(new SieniArchivo());
        this.setArchivoModifica(new SieniArchivo());
        this.setArchivoList(new ArrayList<SieniArchivo>());
        this.setFormatoArchivo(this.getFormatosAudio());
        this.setFormatoArchivoModifica(this.getFormatosAudio());
        fill();
    }

    private void fill() {
        this.setArchivoList(sieniArchivoFacadeRemote.findAllNoInactivos());
    }

    public void guardar() {
        this.getArchivoNuevo().setArArchivo(this.getArchivoUsable());
        if (validarNuevo(this.getArchivoNuevo())) {//valida el guardado
            guardarCopia();
            sieniArchivoFacadeRemote.create(this.getArchivoNuevo());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Archivo", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
            FacesMessage msg = new FacesMessage("Archivo Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setIndexMenu(0);
        }
        this.setArchivoNuevo(new SieniArchivo());
        fill();
    }

    public void guardarCopia() {
        CopiaArchivos cpa = new CopiaArchivos();
        SieniArchivo arch = this.getArchivoNuevo();
        this.setArchivoNuevo(cpa.updateDataToResource(arch));
        System.gc();
    }

    public void actualizarCopia() {
        CopiaArchivos cpa = new CopiaArchivos();
        SieniArchivo arch = this.getArchivoModifica();
        this.setArchivoModifica(cpa.updateDataToResource(arch));
        System.gc();
    }

    public void quitarFormato(SieniArchivo actual) {

    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniArchivo nuevo) {
        boolean ban = true;

        return ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniArchivo modificado) {
        this.setFormatoArchivoModifica(getFormatoArchivoByCod(modificado.getArTipo() + ""));
        this.setArchivoModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniArchivo eliminado) {
        this.setEliminar(eliminado);
    }

    //metodos para modificacion de datos
    public void mostrar(SieniArchivo ver) {
//        byte[] archivo = sieniArchivoFacadeRemote.getArchivoLazy(ver.getIdArchivo());
//        ver.setArArchivo(archivo);
        CopiaArchivos ca = new CopiaArchivos();
        ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
        if (ca.copyDataToResource(ver)) {
            this.setVer(ver);
            this.setIndexMenu(3);
        } else {
            //error
        }
    }

    public void guardarModifica() {

        if (validarModifica(this.getArchivoModifica())) {//valida el guardado
            if (this.getArchivoUsableModifica() != null) {
                this.getArchivoModifica().setArArchivo(this.getArchivoUsableModifica());
                actualizarCopia();
            }
            sieniArchivoFacadeRemote.edit(this.getArchivoModifica());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modifica", "Archivo", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
            FacesMessage msg = new FacesMessage("Archivo Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            resetModificaForm();
            this.setIndexMenu(0);
        }
        fill();
    }

    public void resetModificaForm() {
        this.setArchivoModifica(new SieniArchivo());
    }

    public void resetNuevoForm() {
        this.setArchivoNuevo(new SieniArchivo());
        this.setArchivoUsable(null);

    }

    public boolean validarModifica(SieniArchivo nuevo) {
        boolean ban = true;

        return ban;
    }

    public void eliminarArchivo() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Archivo", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
        this.getEliminar().setArEstado("I");
        sieniArchivoFacadeRemote.edit(this.getEliminar());
        fill();
    }

    public void getFormatosSubidaNuevo(ValueChangeEvent a) {
        String cod = a.getNewValue().toString();
        this.setFormatoArchivo(getFormatoArchivoByCod(cod));
    }

    private String getFormatoArchivoByCod(String cod) {
        String ret = "";
        switch (cod) {
            case "A":
                ret = this.getFormatosAudio();
                break;
            case "V":
                ret = this.getFormatosVideo();
                break;
            case "I":
                ret = this.getFormatosImagen();
                break;
        }
        return ret;
    }

    public void getFormatosSubidaModifica(ValueChangeEvent a) {
        String cod = a.getNewValue().toString();
        switch (cod) {
            case "A":
                this.setFormatoArchivoModifica(this.getFormatosAudio());
                break;
            case "V":
                this.setFormatoArchivoModifica(this.getFormatosVideo());
                break;
            case "I":
                this.setFormatoArchivoModifica(this.getFormatosImagen());
                break;
        }
    }

    public void getArchivoNuevo(FileUploadEvent event) {
        this.setArchivoUsable(event.getFile().getContents());
        this.setArchivoData(getArchivo(event.getFile().getContents()));
    }

    public void getArchivoModifica(FileUploadEvent event) {
        this.setArchivoUsableModifica(event.getFile().getContents());
        this.setArchivoModificaData(getArchivo(event.getFile().getContents()));
    }

}
