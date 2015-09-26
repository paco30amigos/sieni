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
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniClaseFacadeRemote;
import sv.com.mined.sieni.SieniCursoFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.form.ProgramacionClasesForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.pojos.controller.Combo;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.DateUtils;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "programacionClasesController")
public class ProgramacionClasesController extends ProgramacionClasesForm {

    @EJB
    private SieniClaseFacadeRemote sieniClaseFacadeRemote;
    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    @EJB
    private SieniCursoFacadeRemote sieniCursoFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {

        this.setHorarios(new ArrayList<Combo>());
        this.getHorarios().add(new Combo("L", "Lunes", null));
        this.getHorarios().add(new Combo("M", "Martes", null));
        this.getHorarios().add(new Combo("X", "Miercoles", null));
        this.getHorarios().add(new Combo("J", "Jueves", null));
        this.getHorarios().add(new Combo("V", "Viernes", null));
        this.setNuevo(new SieniClase());
        this.setModifica(new SieniClase());
        fill();
    }

    private void fill() {
        this.setClasesList(sieniClaseFacadeRemote.findAllNoInactivos());
        this.setDocentesList(sieniDocenteFacadeRemote.findDocentesActivos());
        this.setDocentesModificaList(sieniDocenteFacadeRemote.findDocentesActivos());
        this.setCursosList(sieniCursoFacadeRemote.findByEstado('A'));
    }

    public void guardar() {
        boolean inicio = true;
        String horario = "";
        for (String actual : this.getHorarioSelected()) {
            if (inicio) {
                horario += actual;
                inicio = false;
            } else {
                horario += "," + actual;
            }
        }
        this.getNuevo().setClHorario(horario);
        if (validarNuevo(this.getNuevo())) {//valida el guardado
            sieniClaseFacadeRemote.create(this.getNuevo());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Archivo", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
            FacesMessage msg = new FacesMessage("Programación Creada Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setNuevo(new SieniClase());
            this.setHorarioSelected(new ArrayList<String>());
            fill();
        }
    }

    public void guardarCopia() {
//        CopiaArchivos cpa = new CopiaArchivos();
//        SieniClase arch = this.getArchivoNuevo();
//        this.setArchivoNuevo(cpa.updateDataToResource(arch));
//        System.gc();
    }

    public void actualizarCopia() {
//        CopiaArchivos cpa = new CopiaArchivos();
//        SieniClase arch = this.getArchivoModifica();
//        this.setArchivoModifica(cpa.updateDataToResource(arch));
//        System.gc();
    }

    public void quitarFormato(SieniClase actual) {

    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniClase nuevo) {
        boolean ban = false;
//        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
//        validaciones.add(new ValidationPojo(this.getArchivoUsable() == null, "Debe subir un archivo", FacesMessage.SEVERITY_WARN));
//        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniClase modificado) {
        DateUtils du = new DateUtils();
        this.setHorarioSelected(du.getAllNamesOfWeekList(modificado.getClHorario()));
        this.setModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniClase eliminado) {
        this.setEliminar(eliminado);
        
    }

    //metodos para modificacion de datos
    public void mostrar(SieniClase ver) {
        this.setModifica(ver);
        this.setIndexMenu(3);
    }
    
    public void ver(SieniClase modificado) {
        this.setModifica(modificado);
             this.setIndexMenu(3);
    }

    public void guardarModifica() {

        boolean inicio = true;
        String horario = "";
        for (String actual : this.getHorarioSelected()) {
            if (inicio) {
                horario += actual;
                inicio = false;
            } else {
                horario += "," + actual;
            }
        }
        this.getNuevo().setClHorario(horario);
        if (validarModifica(this.getModifica())) {//valida el guardado
            sieniClaseFacadeRemote.edit(this.getModifica());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modificar", "Programacion Clase", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
            FacesMessage msg = new FacesMessage("Programación modificada Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
//            this.setNuevo(new SieniClase());
            fill();
        }
    }

    public void resetModificaForm() {
//        this.setArchivoModifica(new SieniClase());
    }

    public void resetNuevoForm() {
//        this.setArchivoNuevo(new SieniClase());
//        this.setArchivoUsable(null);

    }

    public boolean validarModifica(SieniClase nuevo) {
        boolean ban = true;
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        return ban;
    }

    public void eliminarArchivo() {
sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Clase", this.getEliminar().getIdClase(), 'D'));
        this.getEliminar().setClEstado('I');
        sieniClaseFacadeRemote.edit(this.getEliminar());
        fill();
    }

    public void getFormatosSubidaNuevo(ValueChangeEvent a) {
//        String cod = a.getNewValue().toString();
//        this.setFormatoArchivo(getFormatoArchivoByCod(cod));
//        this.setTamanioArchivo(getTamanioMaxArchivoByCod(cod));
    }

    public void getArchivoNuevo(FileUploadEvent event) {
//        this.setArchivoUsable(event.getFile().getContents());
//        this.setArchivoData(getArchivo(event.getFile().getContents()));
    }

    public void getArchivoModifica(FileUploadEvent event) {
//        this.setArchivoUsableModifica(event.getFile().getContents());
//        this.setArchivoModificaData(getArchivo(event.getFile().getContents()));
    }

}
