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
import javax.servlet.http.HttpServletRequest;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniCatMateriaFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniSeccionFacadeRemote;
import sv.com.mined.sieni.form.GestionMateriasForm;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;

/**
 *
 * @author Alejandro
 */
@SessionScoped
@ManagedBean(name = "gestionMateriasController")
public class GestionMateriasController extends GestionMateriasForm {

    @EJB
    private SieniMateriaFacadeRemote sieniMateriaFacadeRemote;
    @EJB
    private SieniGradoFacadeRemote sieniGradoFacadeRemote;
    @EJB
    private SieniSeccionFacadeRemote sieniSeccionFacadeRemote;
    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    @EJB
    private SieniCatMateriaFacadeRemote sieniCatMateriaFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);
    }

    @PostConstruct
    public void init() {
        this.setMateriaNuevo(new SieniMateria());
        this.setMateriaModifica(new SieniMateria());
        this.setMateriaList(new ArrayList<SieniMateria>());
        fill();
    }

    public void fill() {
        this.setMateriaList(sieniMateriaFacadeRemote.findAllNoInactivas());
        this.setDocentesList(sieniDocenteFacadeRemote.findDocentesActivos());
    }

    public void initNuevo() {
        this.setCatmateriaList(sieniCatMateriaFacadeRemote.findAllActivos());
        this.setGradoList(sieniGradoFacadeRemote.findAll());
        this.setIndexMenu(1);
    }

    public synchronized void guardar() {
        String matSelected = this.getMateriaNuevo().getMaNombre();
        Integer gradoSelected = this.getMateriaNuevo().getIdGrado().getGrNumero();
        String turnoSelected = this.getMateriaNuevo().getMaTurno();
        Character estado = this.getMateriaNuevo().getMaEstado();
        try {
            if (validarNuevo(this.getMateriaNuevo())) {
                if (validarMateria(matSelected, gradoSelected, turnoSelected, estado)) {
                    this.setMateriaNuevo(sieniMateriaFacadeRemote.createAndReturn(this.getMateriaNuevo()));
                    registrarEnBitacora("Crear", "Materia", this.getMateriaNuevo().getIdMateria());
                    this.setMateriaNuevo(new SieniMateria());
                    new ValidationPojo().printMsj("Materia Creada Exitosamente", FacesMessage.SEVERITY_INFO);
                    this.getMateriaList().add(this.getMateriaNuevo());
                    this.setMateriaNuevo(new SieniMateria());
                } else {
                    new ValidationPojo().printMsj("La materia seleccionada ya existe para ese grado y seccion", FacesMessage.SEVERITY_ERROR);
                }
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public boolean validarNuevo(SieniMateria nuevo) {
        boolean ban = true;

        return ban;
    }

    public void modificar(SieniMateria modificado) {
        this.setCatmateriaList(sieniCatMateriaFacadeRemote.findAllActivos());
        this.setGradoList(sieniGradoFacadeRemote.findAll());
        this.setMateriaModifica(modificado);
        this.setIndexMenu(2);
    }

    public synchronized void guardarModifica() {
        String matSelected = this.getMateriaModifica().getMaNombre();
        Integer gradoSelected = this.getMateriaModifica().getIdGrado().getGrNumero();
        String turnoSelected = this.getMateriaModifica().getMaTurno();
        Character estado = this.getMateriaModifica().getMaEstado();
        try {
            if (validarModifica(this.getMateriaModifica())) {//valida el guardado            
                if (validarMateria(matSelected, gradoSelected, turnoSelected, estado)) {
                    sieniMateriaFacadeRemote.edit(this.getMateriaModifica());
                    registrarEnBitacora("Modificar", "Materia", this.getMateriaModifica().getIdMateria());
                    new ValidationPojo().printMsj("Archivo Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
                } else {
                    new ValidationPojo().printMsj("La materia seleccionada ya existe para ese grado y seccion", FacesMessage.SEVERITY_ERROR);
                }
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public boolean validarModifica(SieniMateria nuevo) {
        boolean ban = true;
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        return ban;
    }

    public void eliminar(SieniMateria eliminado) {
        this.setEliminar(eliminado);
    }

    public synchronized void eliminarArchivo() {
        try {
            registrarEnBitacora("Eliminar", "Materia", this.getMateriaNuevo().getIdMateria());
            this.getEliminar().setMaEstado(new Character('I'));
            sieniMateriaFacadeRemote.edit(this.getEliminar());
            this.getMateriaList().remove(this.getEliminar());
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void ver(SieniMateria modificado) {
        this.setMateriaModifica(modificado);
        this.setIndexMenu(3);
    }

    public boolean validarMateria(String materia, Integer grado, String turno, Character estado) {
        List<SieniMateria> mat = sieniMateriaFacadeRemote.findByMaNombre(materia);
        if (mat != null) {
            for (SieniMateria actual : mat) {
//                if ((actual.getIdGrado().getGrNumero().equals(grado)) && (actual.getMaTurno().equals(turno)) && actual.getMaEstado()=='I') {
                if ((actual.getIdGrado().getGrNumero().equals(grado)) && (actual.getMaTurno().equals(turno)) && actual.getMaEstado().equals(estado)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void refresh() {
        fill();
    }
}
