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
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMatriculaFacadeRemote;
import sv.com.mined.sieni.SieniSeccionFacadeRemote;
import sv.com.mined.sieni.form.GestionMatriculaForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniMatricula;
import sv.com.mined.sieni.model.SieniSeccion;
import utils.FormatUtils;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "gestionMatriculaController")
public class GestionMatriculaController extends GestionMatriculaForm {

    @EJB
    private SieniMatriculaFacadeRemote sieniMatriculaFacadeRemote;
    @EJB
    private SieniGradoFacadeRemote sieniGradoFacadeRemote;
    @EJB
    private SieniSeccionFacadeRemote sieniSeccionFacadeRemote;
    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setMatriculaNuevo(new SieniMatricula());
        this.setMatriculaModifica(new SieniMatricula());
        this.setMatriculaList(new ArrayList<SieniMatricula>());
        fill();
    }

    private void fill() {
        this.setMatriculaList(sieniMatriculaFacadeRemote.findAllNoInactivos());//tenes q traer los q no sean I (eliminados)
        this.setAlumnosList(sieniAlumnoFacadeRemote.findAlumnosNoMatriculados());
        this.setAlumnosModificaList(sieniAlumnoFacadeRemote.findAlumnosNoMatriculados());
        //nuevo
        this.setGradosList(sieniGradoFacadeRemote.findAll());
        this.setSeccionesList(new ArrayList<SieniSeccion>());
        if (this.getGradosList() != null && !this.getGradosList().isEmpty()) {
            if (this.getGradosList().get(0).getSieniSeccionList() != null
                    && !this.getGradosList().get(0).getSieniSeccionList().isEmpty()) {
                this.setSeccionesList(this.getGradosList().get(0).getSieniSeccionList());
            }
        }
        //modifica
        this.setGradosModificaList(sieniGradoFacadeRemote.findAll());
        this.setSeccionesModificaList(new ArrayList<SieniSeccion>());
        if (this.getGradosModificaList() != null && !this.getGradosModificaList().isEmpty()) {
            if (this.getGradosModificaList().get(0).getSieniSeccionList() != null
                    && !this.getGradosModificaList().get(0).getSieniSeccionList().isEmpty()) {
                this.setSeccionesModificaList(this.getGradosModificaList().get(0).getSieniSeccionList());
            }
        }
    }

    public void guardar() {
        for (SieniAlumno actual : this.getAlumnosList()) {
            if (actual.getIdAlumno().equals(this.getIdAlumno())) {
                this.getMatriculaNuevo().setIdAlumno(actual);
                break;
            }
        }
        for (SieniGrado actual : this.getGradosList()) {
            if (actual.getIdGrado().equals(this.getIdGrado())) {
                this.getMatriculaNuevo().setIdGrado(actual);
                break;
            }
        }
        for (SieniSeccion actual : this.getSeccionesList()) {
            if (actual.getIdSeccion().equals(this.getIdSeccion())) {
                this.getMatriculaNuevo().setIdSeccion(actual);
                break;
            }
        }

        if (validarNuevo(this.getMatriculaNuevo())) {//valida el guardado
            String anioActual = new FormatUtils().getFormatedAnio(new Date());
            this.getMatriculaNuevo().setMtAnio(anioActual);
            this.getMatriculaNuevo().setMtEstado('D');
            this.getMatriculaNuevo().setMtFechaIngreso(new Date());
            sieniMatriculaFacadeRemote.create(this.getMatriculaNuevo());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Matricula", this.getMatriculaNuevo().getIdMatricula(), 'D'));
            FacesMessage msg = new FacesMessage("Matricula Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setIndexMenu(0);
        }
        this.setMatriculaNuevo(new SieniMatricula());
        fill();
    }

    public void quitarFormato(SieniMatricula actual) {

    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniMatricula nuevo) {
        boolean ban = true;

        return ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniMatricula modificado) {
        this.setMatriculaModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniMatricula eliminado) {
        this.setEliminar(eliminado);
    }

    public void guardarModifica() {
        for (SieniAlumno actual : this.getAlumnosModificaList()) {
            if (actual.getIdAlumno().equals(this.getIdAlumnoModifica())) {
                this.getMatriculaModifica().setIdAlumno(actual);
                break;
            }
        }
        for (SieniGrado actual : this.getGradosModificaList()) {
            if (actual.getIdGrado().equals(this.getIdGradoModifica())) {
                this.getMatriculaModifica().setIdGrado(actual);
                break;
            }
        }
        for (SieniSeccion actual : this.getSeccionesModificaList()) {
            if (actual.getIdSeccion().equals(this.getIdSeccionModifica())) {
                this.getMatriculaModifica().setIdSeccion(actual);
                break;
            }
        }
        if (validarModifica(this.getMatriculaModifica())) {//valida el guardado
            sieniMatriculaFacadeRemote.edit(this.getMatriculaModifica());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modifica", "Matricula", this.getMatriculaModifica().getIdMatricula(), 'D'));
            FacesMessage msg = new FacesMessage("Matricula Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            resetModificaForm();
            this.setIndexMenu(0);
        }
        fill();
    }

    public void resetModificaForm() {
        this.setMatriculaModifica(new SieniMatricula());
    }

    public boolean validarModifica(SieniMatricula nuevo) {
        boolean ban = true;

        return ban;
    }

    public void eliminarMatricula() {
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Matricula", this.getEliminar().getIdMatricula(), 'D'));
        this.getEliminar().setMtEstado('I');//I:eliminado,D:disponible,N:no disponible, (I eliminado logico)
        sieniMatriculaFacadeRemote.edit(this.getEliminar());
        fill();
    }

    public void getSeccionesGrado(ValueChangeEvent a) {
        Long idGrado = (Long) a.getNewValue();
        SieniGrado cod = new SieniGrado();
        for (SieniGrado actual : this.getGradosList()) {
            if (actual.getIdGrado().equals(idGrado)) {
                cod = actual;
                break;
            }
        }
        this.setSeccionesList(cod.getSieniSeccionList());
    }

    public void getSeccionesGradoModifica(ValueChangeEvent a) {
        Long idGrado = (Long) a.getNewValue();
        SieniGrado cod = new SieniGrado();
        for (SieniGrado actual : this.getGradosModificaList()) {
            if (actual.getIdGrado().equals(idGrado)) {
                cod = actual;
                break;
            }
        }
        this.setSeccionesModificaList(cod.getSieniSeccionList());
    }

    public void ver(SieniMatricula modificado) {
        this.setMatriculaModifica(modificado);
        this.setIndexMenu(3);
    }
    
        public void getCarnetAlumno(ValueChangeEvent a) {
        Integer cod = new Integer(a.getNewValue().toString());
        this.setAlumno(sieniAlumnoFacadeRemote.findAlumnoById(cod));
//        if (this.getMateriasList() != null && !this.getMateriasList().isEmpty()) {
//            this.setIdMateria(this.getMateriasList().get(0));
//            this.setEvaluacionesList(this.getIdMateria().getSieniEvaluacionList());
//        } else {
//            this.setEvaluacionesList(new ArrayList<SieniEvaluacion>());
//        }
    }
}
