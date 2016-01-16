/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import sv.com.mined.sieni.SieniArchivoFacadeRemote;
import sv.com.mined.sieni.form.GestionArchivoMultimediaForm;
import sv.com.mined.sieni.model.SieniArchivo;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.CopiaArchivos;
import utils.TamanioUtils;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "gestionArchivoMultimediaController")
public class GestionArchivoMultimediaController extends GestionArchivoMultimediaForm {

    @EJB
    private SieniArchivoFacadeRemote sieniArchivoFacadeRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);

    }

    @PostConstruct
    public void init() {
        this.setArchivoNuevo(new SieniArchivo());
        this.setArchivoModifica(new SieniArchivo());
        this.setArchivoList(new ArrayList<SieniArchivo>());
        this.setFormatoArchivo(this.getFormatosAudio());
        this.setTamanioArchivo(getTamanioMaxArchivoByCod("A"));//tamanio para audio
        this.setFormatoArchivoModifica(this.getFormatosAudio());
        fill();
    }

    private void fill() {
        this.setArchivoList(sieniArchivoFacadeRemote.findAllNoInactivos());
    }

    public void cancelaModifica(SieniArchivo modifica) {
        modifica = sieniArchivoFacadeRemote.find(modifica.getIdArchivo());
        this.setIndexMenu(0);
    }

    public synchronized void guardar() {
        try {
            this.getArchivoNuevo().setArArchivo(this.getArchivoUsable());
            if (validarNuevo(this.getArchivoNuevo())) {//valida el guardado
                guardarCopia();
                this.setArchivoNuevo(sieniArchivoFacadeRemote.createAndReturn(this.getArchivoNuevo()));
                registrarEnBitacora("Guardar", "Archivo", this.getArchivoNuevo().getIdArchivo());
                new ValidationPojo().printMsj("Archivo Creado Exitosamente", FacesMessage.SEVERITY_INFO);
                //agrega el nuevo archivo a la lista de la tabla actual para no hacer el fill
                this.getArchivoList().add(this.getArchivoNuevo());
                //limpia los datos para un registro nuevo
                this.setArchivoNuevo(new SieniArchivo());
                this.setArchivoUsable(null);
                this.setFormatoArchivo(this.getFormatosAudio());
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
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
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        validaciones.add(new ValidationPojo(sieniArchivoFacadeRemote.findByNombre(nuevo.getArNombre()) != null, "El nombre del archivo ya existe", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(this.getArchivoUsable() == null, "Debe subir un archivo", FacesMessage.SEVERITY_ERROR));
        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniArchivo modificado) {
        this.setFormatoArchivoModifica(getFormatoArchivoByCod(modificado.getArTipo() + ""));
        this.setTamanioArchivo(getTamanioMaxArchivoByCod(modificado.getArTipo() + ""));
        this.setArchivoModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniArchivo eliminado) {
        this.setEliminar(eliminado);
    }

    //metodos para modificacion de datos
    public void mostrar(SieniArchivo ver) {
        CopiaArchivos ca = new CopiaArchivos();
        ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
        if (ca.copyDataToResource(ver)) {
            this.setVer(ver);
            this.setIndexMenu(3);
        } else {
            new ValidationPojo().printMsj("Ocurrió un error, no se puede mostrar el archivo multimedia", FacesMessage.SEVERITY_ERROR);
        }
    }

    public synchronized void guardarModifica() {
        try {
            if (validarModifica(this.getArchivoModifica())) {//valida el guardado
                if (this.getArchivoUsableModifica() != null) {
                    this.getArchivoModifica().setArArchivo(this.getArchivoUsableModifica());
                    actualizarCopia();
                }
                sieniArchivoFacadeRemote.edit(this.getArchivoModifica());
                registrarEnBitacora("Modificar", "Archivo", this.getArchivoModifica().getIdArchivo());
                new ValidationPojo().printMsj("Archivo Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void resetModificaForm() {
        this.setArchivoModifica(new SieniArchivo());
    }

    public void resetNuevoForm() {
        this.setArchivoNuevo(new SieniArchivo());
        this.setArchivoUsable(null);

    }

    public boolean validarModifica(SieniArchivo nuevo) {
        boolean ban;
        List<ValidationPojo> validaciones = new ArrayList<>();
        SieniArchivo archivoBD = sieniArchivoFacadeRemote.find(nuevo.getIdArchivo());
        if (!archivoBD.getArNombre().equals(nuevo.getArNombre())) {
            validaciones.add(new ValidationPojo(sieniArchivoFacadeRemote.findByNombre(nuevo.getArNombre()) != null, "El nombre del archivo ya existe", FacesMessage.SEVERITY_ERROR));
        }
        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public synchronized void eliminarArchivo() {
        try {
            registrarEnBitacora("Eliminar", "Archivo", this.getEliminar().getIdArchivo());
            CopiaArchivos ca = new CopiaArchivos();
            ca.deleteDataToResource(this.getEliminar());
            this.getEliminar().setArEstado("I");
            sieniArchivoFacadeRemote.edit(this.getEliminar());
            //elimina el archivo de la lista de datos para no volver a hacer el fill
            this.getArchivoList().remove(this.getEliminar());
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void getFormatosSubidaNuevo(ValueChangeEvent a) {
        String cod = a.getNewValue().toString();
        this.setFormatoArchivo(getFormatoArchivoByCod(cod));
        this.setTamanioArchivo(getTamanioMaxArchivoByCod(cod));
    }

    private String getTamanioMaxArchivoByCod(String cod) {
        String ret = "";
        TamanioUtils tu = new TamanioUtils();

        switch (cod) {
            case "A":
                ret = tu.getAudioMax() + "";
                break;
            case "V":
                ret = tu.getVideoMax() + "";
                break;
            case "I":
                ret = tu.getImagenMax() + "";
                break;
        }
        return ret;
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
        new ValidationPojo().printMsj("Archivo subido correctamente", FacesMessage.SEVERITY_INFO);
    }

    public void getArchivoModifica(FileUploadEvent event) {
        this.setArchivoUsableModifica(event.getFile().getContents());
        this.setArchivoModificaData(getArchivo(event.getFile().getContents()));
        new ValidationPojo().printMsj("Archivo subido correctamente", FacesMessage.SEVERITY_INFO);
    }

}
