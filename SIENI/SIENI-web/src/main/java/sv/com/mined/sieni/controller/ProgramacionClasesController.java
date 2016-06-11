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
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniClaseFacadeRemote;
import sv.com.mined.sieni.SieniCursoFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.form.ProgramacionClasesForm;
import sv.com.mined.sieni.model.SieniClase;
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

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);
    }

    @PostConstruct
    public void init() {

        this.setHorarios(new ArrayList<Combo>());
        this.getHorarios().add(new Combo("L", "Lunes", null));
        this.getHorarios().add(new Combo("M", "Martes", null));
        this.getHorarios().add(new Combo("X", "Miercoles", null));
        this.getHorarios().add(new Combo("J", "Jueves", null));
        this.getHorarios().add(new Combo("V", "Viernes", null));
        this.getHorarios().add(new Combo("S", "Sabado", null));
        this.getHorarios().add(new Combo("D", "Domingo", null));
        this.setNuevo(new SieniClase());
        this.setModifica(new SieniClase());
        fill();
    }

    private void fill() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        //fill para alumnos
        if (loginBean.getTipoRol().equals("0")) {
            this.setClasesList(sieniClaseFacadeRemote.findClaseByAlumno(loginBean.getAlumno().getIdAlumno()));
//            this.setClasesList(sieniClaseFacadeRemote.findClaseByAlumno(loginBean.getAlumno().getIdAlumno()));
        } else {
            this.setClasesList(sieniClaseFacadeRemote.findAllNoInactivos());
//            this.setClasesList(sieniClaseFacadeRemote.findByDocente(loginBean.getDocente().getIdDocente()));
        }
    }

    public void cancelaModifica(SieniClase modifica) {
        modifica = sieniClaseFacadeRemote.find(modifica.getIdClase());
        this.setIndexMenu(0);
    }

    public void nuevoD() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        this.setCursosList(sieniCursoFacadeRemote.findActivos());
//        this.setCursosList(sieniCursoFacadeRemote.findByDocente(loginBean.getDocente().getIdDocente()));
        this.setHorarioSelected(new ArrayList<String>());
        this.setIndexMenu(1);
    }

    public synchronized void guardar() {
        try {
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
            DateUtils du = new DateUtils();
            if ('O' == this.getNuevo().getClTipo()) {
                if (sieniClaseFacadeRemote.findByHorarioExiste(horario, this.getNuevo().getClHora())) {
                    this.getNuevo().setClHorario(horario);
                    if (validarNuevo(this.getNuevo())) {//valida el guardado
                        this.setNuevo(sieniClaseFacadeRemote.createAndReturn(this.getNuevo()));
                        registrarEnBitacora("Crear", "Programacion de clases", this.getNuevo().getIdClase());
                        new ValidationPojo().printMsj("Programación Creada Exitosamente", FacesMessage.SEVERITY_INFO);
                        this.setNuevo(new SieniClase());
                        this.setHorarioSelected(new ArrayList<String>());
                        fill();
                    }
                } else {
                    new ValidationPojo().printMsj("Ya existe una clase en vivo en ese horario", FacesMessage.SEVERITY_ERROR);
                }
            } else {
                this.getNuevo().setClHorario(horario);
                if (validarNuevo(this.getNuevo())) {//valida el guardado
                    this.setNuevo(sieniClaseFacadeRemote.createAndReturn(this.getNuevo()));
                    registrarEnBitacora("Crear", "Programacion de clases", this.getNuevo().getIdClase());
                    new ValidationPojo().printMsj("Programación Creada Exitosamente", FacesMessage.SEVERITY_INFO);
                    this.setNuevo(new SieniClase());
                    this.setHorarioSelected(new ArrayList<String>());
                    fill();
                }
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
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
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
//        validaciones.add(new ValidationPojo(this.getCursosList() == null, "Debe tener un curso asignado", FacesMessage.SEVERITY_ERROR));
        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniClase modificado) {
//        this.setDocentesModificaList(sieniDocenteFacadeRemote.findDocentesActivos());
        DateUtils du = new DateUtils();
        this.setHorarioSelected(du.getAllNamesOfWeekList(modificado.getClHorario()));
        this.setCursosModificaList(sieniCursoFacadeRemote.findByTipoCurso("Digital"));
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

    public synchronized void guardarModifica() {
        try {
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
            DateUtils du = new DateUtils();
            boolean repetido;
//            if ('O' == this.getModifica().getClTipo()) {
//                if (sieniClaseFacadeRemote.findByHorarioExiste(horario, this.getModifica().getClHora())) {
                    this.getModifica().setClHorario(horario);
                    if (validarModifica(this.getModifica())) {//valida el guardado
                        sieniClaseFacadeRemote.edit(this.getModifica());
                        registrarEnBitacora("Modificar", "Programacion de clases", this.getModifica().getIdClase());
                        new ValidationPojo().printMsj("Programación Creada Exitosamente", FacesMessage.SEVERITY_INFO);
                        fill();
//                    }
//                } else {
//                    FacesMessage msg = new FacesMessage("Ya existe una programacion en ese horario");
//                    FacesContext.getCurrentInstance().addMessage(null, msg);
//                }
            } else {
                this.getModifica().setClHorario(horario);
                if (validarModifica(this.getModifica())) {//valida el guardado
                    sieniClaseFacadeRemote.edit(this.getModifica());
                    registrarEnBitacora("Modificar", "Programacion de clases", this.getModifica().getIdClase());
                    FacesMessage msg = new FacesMessage("Programación modificada Exitosamente");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    fill();
                }
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
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
        boolean ban = false;
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
//        validaciones.add(new ValidationPojo(this.getCursosList() == null, "Debe tener un curso asignado", FacesMessage.SEVERITY_ERROR));
        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public synchronized void eliminarArchivo() {
        try {
            registrarEnBitacora("Eliminar", "Programacion de clases", this.getEliminar().getIdClase());
            this.getEliminar().setClEstado('I');
            sieniClaseFacadeRemote.edit(this.getEliminar());
            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
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
