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
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.form.GestionarAlumnosForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniBitacora;

/**
 *
 * @author Laptop
 */
@SessionScoped
@ManagedBean(name = "gestionarAlumnosController")
public class GestionarAlumnosController extends GestionarAlumnosForm {

    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setAlumnoNuevo(new SieniAlumno());
        this.setAlumnoModifica(new SieniAlumno());
        this.setAlumnosList(new ArrayList<SieniAlumno>());
        fill();
    }

    private void fill() {
        this.setAlumnosList(sieniAlumnoFacadeRemote.findAll());
    }

    public void guardar() {
//        Character tipoUsuario = ;//hay que extraer el del usuario logueado
        this.getAlumnoNuevo().setAlFoto(this.getFotoArchivo());
        quitarFormato(this.getAlumnoNuevo());//quita el formato de los campos
        if (validarNuevo(this.getAlumnoNuevo())) {//valida el guardado
            sieniAlumnoFacadeRemote.create(this.getAlumnoNuevo());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Alumno", this.getAlumnoNuevo().getIdAlumno(), 'D'));
            FacesMessage msg = new FacesMessage("Expediente Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setIndexMenu(0);
        }
        this.setAlumnoNuevo(new SieniAlumno());
        fill();
    }

    public void quitarFormato(SieniAlumno actual) {
        actual.setAlTelefonoEm1(actual.getAlTelefonoEm1().replaceAll("-", ""));
        actual.setAlTelefonoEm2(actual.getAlTelefonoEm2().replaceAll("-", ""));
    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniAlumno nuevo) {
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
    public void modificar(SieniAlumno modificado) {
        this.setFotoArchivoModifica(modificado.getAlFoto());
        this.setFotoUsableModifica(getImage(modificado.getAlFoto()));
        this.setAlumnoModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniAlumno eliminado) {
        this.setEliminar(eliminado);
    }

    public void getFotoNuevaModifica(FileUploadEvent event) {
        this.setFotoArchivoModifica(event.getFile().getContents());
        this.setFotoUsableModifica(getImage(event.getFile().getContents()));
    }

    public void guardarModifica() {
        this.getAlumnoModifica().setAlFoto(this.getFotoArchivoModifica());
        quitarFormato(this.getAlumnoModifica());//quita el formato de los campos
        if (validarModifica(this.getAlumnoModifica())) {//valida el guardado
            sieniAlumnoFacadeRemote.edit(this.getAlumnoModifica()); 
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modificar", "Alumno", this.getAlumnoModifica().getIdAlumno(), new Character('D')));
            FacesMessage msg = new FacesMessage("Expediente Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            resetModificaForm();
            this.setIndexMenu(0);
        }
        fill();
    }

    public void resetModificaForm() {
        this.setAlumnoModifica(new SieniAlumno());
        this.setFotoArchivoModifica(null);
        this.setFotoUsableModifica(null);
    }

    public boolean validarModifica(SieniAlumno nuevo) {
        boolean ban = true;

        return ban;
    }

    public void eliminarExpediente() {
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Alumno", this.getEliminar().getIdAlumno(), 'D'));
        sieniAlumnoFacadeRemote.remove(this.getEliminar());        
        fill();
    }
}
