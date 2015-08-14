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
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniCursoFacadeRemote;
import sv.com.mined.sieni.form.GestionCursoForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.model.SieniGrado;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "gestionCursoController")
public class GestionCursoController extends GestionCursoForm {

    @EJB
    private SieniCursoFacadeRemote sieniCursoFacadeRemote;

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setCursoNuevo(new SieniCurso());
        this.setCursoModifica(new SieniCurso());
        this.setCursoList(new ArrayList<SieniCurso>());
        fill();
    }

    private void fill() {
        this.setCursoList(sieniCursoFacadeRemote.findAll());
    }

    public void guardar() {
        if (validarNuevo(this.getCursoNuevo())) {//valida el guardado
            sieniCursoFacadeRemote.create(this.getCursoNuevo());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Curso", this.getCursoNuevo().getIdCurso(), 'D'));
            FacesMessage msg = new FacesMessage("Curso Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setIndexMenu(0);
        }
        this.setCursoNuevo(new SieniCurso());
        fill();
    }

    public void quitarFormato(SieniCurso actual) {

    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniCurso nuevo) {
        boolean ban = true;

        return ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniCurso modificado) {
        this.setCursoModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniCurso eliminado) {
        this.setEliminar(eliminado);
    }

    public void guardarModifica() {
        if (validarModifica(this.getCursoModifica())) {//valida el guardado
            sieniCursoFacadeRemote.edit(this.getCursoModifica());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modificar", "Curso", this.getCursoModifica().getIdCurso(), 'D'));
            FacesMessage msg = new FacesMessage("Curso Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            resetModificaForm();
            this.setIndexMenu(0);
        }
        fill();
    }

    public void resetModificaForm() {
        this.setCursoModifica(new SieniCurso());
    }

    public boolean validarModifica(SieniCurso nuevo) {
        boolean ban = true;

        return ban;
    }

    public void eliminarCurso() {
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Curso", this.getEliminar().getIdCurso(), 'D'));
        sieniCursoFacadeRemote.remove(this.getEliminar());
        fill();
    }

    public void getSeccionesGrado(ValueChangeEvent a) {
        SieniGrado cod = (SieniGrado) a.getNewValue();
        this.setSeccionList(cod.getSieniSeccionList());
    }

    public void getSeccionesGradoModifica(ValueChangeEvent a) {
        SieniGrado cod = (SieniGrado) a.getNewValue();
        this.setSeccionModificaList(cod.getSieniSeccionList());
    }

}
