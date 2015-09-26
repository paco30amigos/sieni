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
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniSeccionFacadeRemote;
import sv.com.mined.sieni.form.GestionCursoForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniSeccion;

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

    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    @EJB
    private SieniGradoFacadeRemote sieniGradoFacadeRemote;
    @EJB
    private SieniSeccionFacadeRemote sieniSeccionFacadeRemote;
    @EJB
    private SieniMateriaFacadeRemote sieniMateriaFacadeRemote;

    @PostConstruct
    public void init() {
        this.setCursoNuevo(new SieniCurso());
        this.setCursoModifica(new SieniCurso());
        this.setCursoList(new ArrayList<SieniCurso>());
        fill();
    }

    private void fill() {
        this.setCursoList(sieniCursoFacadeRemote.findByEstado('A'));
        //nuevo
        this.setDocentesList(sieniDocenteFacadeRemote.findAll());
        this.setGradoList(sieniGradoFacadeRemote.findAll());
        this.setMateriaList(sieniMateriaFacadeRemote.findAll());
        this.setSeccionList(new ArrayList<SieniSeccion>());
        if (this.getGradoList() != null && !this.getGradoList().isEmpty()) {
            if (this.getGradoList().get(0).getSieniSeccionList() != null
                    && !this.getGradoList().get(0).getSieniSeccionList().isEmpty()) {
                this.setSeccionList(this.getGradoList().get(0).getSieniSeccionList());
            }
        }
        //modifica
        this.setDocentesModificaList(sieniDocenteFacadeRemote.findAll());
        this.setGradoModificaList(sieniGradoFacadeRemote.findAll());
        this.setMateriaModificaList(sieniMateriaFacadeRemote.findAll());
        this.setSeccionModificaList(new ArrayList<SieniSeccion>());
        if (this.getGradoModificaList() != null && !this.getGradoModificaList().isEmpty()) {
            if (this.getGradoModificaList().get(0).getSieniSeccionList() != null
                    && !this.getGradoModificaList().get(0).getSieniSeccionList().isEmpty()) {
                this.setSeccionModificaList(this.getGradoModificaList().get(0).getSieniSeccionList());
            }
        }
    }

    public void guardar() {
        for (SieniDocente actual : this.getDocentesList()) {
            if (actual.getIdDocente().equals(this.getIdDocente())) {
                this.getCursoNuevo().setIdDocente(actual);
                break;
            }
        }
        for (SieniGrado actual : this.getGradoList()) {
            if (actual.getIdGrado().equals(this.getIdGrado())) {
                this.getCursoNuevo().setIdGrado(actual);
                break;
            }
        }
        for (SieniSeccion actual : this.getSeccionList()) {
            if (actual.getIdSeccion().equals(this.getIdSeccion())) {
                this.getCursoNuevo().setIdSeccion(actual);
                break;
            }
        }
        for (SieniMateria actual : this.getMateriaList()) {
            if (actual.getIdMateria().equals(this.getIdMateria())) {
                this.getCursoNuevo().setIdMateria(actual);
                break;
            }
        }

        this.getCursoNuevo().setCrEstado('A');
        if (validarNuevo(this.getCursoNuevo())) {//valida el guardado
            
            SieniCurso existCurso=new SieniCurso();
            existCurso=sieniCursoFacadeRemote.finByDocGrSecMat(this.getCursoNuevo().getIdDocente().getIdDocente(), this.getCursoNuevo().getIdGrado().getIdGrado(), this.getCursoNuevo().getIdSeccion().getIdSeccion(), this.getCursoNuevo().getIdMateria().getIdMateria(),this.getCursoNuevo().getCrNombre());
            if(existCurso!=null)
            {
            FacesMessage msg = new FacesMessage("No se puede crear el curso, ya existe para esa materia");
            FacesContext.getCurrentInstance().addMessage(null, msg);
           
            }else{
            sieniCursoFacadeRemote.create(this.getCursoNuevo());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Curso", this.getCursoNuevo().getIdCurso(), 'D'));
            FacesMessage msg = new FacesMessage("Curso Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setIndexMenu(0);
            }
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
    
    public void ver(SieniCurso modificado) {
        this.setCursoModifica(modificado);
             this.setIndexMenu(3);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniCurso eliminado) {
        this.setEliminar(eliminado);
    }

    public void guardarModifica() {
        for (SieniDocente actual : this.getDocentesModificaList()) {
            if (actual.getIdDocente().equals(this.getIdDocenteModifica())) {
                this.getCursoModifica().setIdDocente(actual);
                break;
            }
        }
        for (SieniGrado actual : this.getGradoModificaList()) {
            if (actual.getIdGrado().equals(this.getIdGradoModifica())) {
                this.getCursoModifica().setIdGrado(actual);
                break;
            }
        }
        for (SieniSeccion actual : this.getSeccionModificaList()) {
            if (actual.getIdSeccion().equals(this.getIdSeccionModifica())) {
                this.getCursoModifica().setIdSeccion(actual);
                break;
            }
        }
        for (SieniMateria actual : this.getMateriaModificaList()) {
            if (actual.getIdMateria().equals(this.getIdMateriaModifica())) {
                this.getCursoModifica().setIdMateria(actual);
                break;
            }
        }
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
        this.getEliminar().setCrEstado('I');
        sieniCursoFacadeRemote.edit(this.getEliminar());
        fill();
    }

    public void getSeccionesGrado(ValueChangeEvent a) {
        Long idGrado = (Long) a.getNewValue();
        SieniGrado cod = new SieniGrado();
        for (SieniGrado actual : this.getGradoList()) {
            if (actual.getIdGrado().equals(idGrado)) {
                cod = actual;
                break;
            }
        }
        this.setSeccionList(cod.getSieniSeccionList());
    }

    public void getSeccionesGradoModifica(ValueChangeEvent a) {
        Long idGrado = (Long) a.getNewValue();
        SieniGrado cod = new SieniGrado();
        for (SieniGrado actual : this.getGradoModificaList()) {
            if (actual.getIdGrado().equals(idGrado)) {
                cod = actual;
                break;
            }
        }
        this.setSeccionModificaList(cod.getSieniSeccionList());
    }

}
