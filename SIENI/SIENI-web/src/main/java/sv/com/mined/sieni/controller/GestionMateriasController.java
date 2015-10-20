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
import javax.servlet.http.HttpServletRequest;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniCatMateriaFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniMatriculaFacadeRemote;
import sv.com.mined.sieni.SieniSeccionFacadeRemote;
import sv.com.mined.sieni.form.GestionMateriasForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniCatMateria;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.FormatUtils;

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

    @PostConstruct
    public void init() {
        this.setMateriaNuevo(new SieniMateria());
        this.setMateriaModifica(new SieniMateria());
        this.setMateriaList(new ArrayList<SieniMateria>());
        fill();
    }

    public void fill() {
        this.setCatmateriaList(sieniCatMateriaFacadeRemote.findAll());
        this.setMateriaList(sieniMateriaFacadeRemote.findMateriasActivas());
        this.setDocentesList(sieniDocenteFacadeRemote.findDocentesActivos());
        this.setGradoList(sieniGradoFacadeRemote.findAll());
    }

    public void guardar() {

        if (validarNuevo(this.getMateriaNuevo())) {            
            sieniMateriaFacadeRemote.create(this.getMateriaNuevo());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Materia", this.getMateriaNuevo().getIdMateria(), loginBean.getTipoUsuario().charAt(0), req.getRemoteAddr()));
            this.setMateriaNuevo(new SieniMateria());
            FacesMessage msg = new FacesMessage("Materia Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            fill();
        }
    }

    public boolean validarNuevo(SieniMateria nuevo) {
        boolean ban = true;

        return ban;
    }

    public void modificar(SieniMateria modificado) {
        this.setMateriaModifica(modificado);
        this.setIndexMenu(2);
    }

    public void guardarModifica() {

        if (validarModifica(this.getMateriaModifica())) {//valida el guardado            
            sieniMateriaFacadeRemote.edit(this.getMateriaModifica());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modifica", "Archivo", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0), req.getRemoteAddr()));
            FacesMessage msg = new FacesMessage("Archivo Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            fill();
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
    
    public void eliminarArchivo() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Materia", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0), req.getRemoteAddr()));
        this.getEliminar().setMaEstado(new Character('I'));
        sieniMateriaFacadeRemote.edit(this.getEliminar());
        fill();
    }
    
    public void ver(SieniMateria modificado) {
        this.setMateriaModifica(modificado);
        this.setIndexMenu(3);
    }
}
