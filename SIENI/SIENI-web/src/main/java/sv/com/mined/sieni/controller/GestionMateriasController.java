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
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniSeccionFacadeRemote;
import sv.com.mined.sieni.form.GestionMateriasForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniMateria;
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
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setMateriaNuevo(new SieniMateria());
        this.setMateriaModifica(new SieniMateria());
        this.setMateriaList(new ArrayList<SieniMateria>());
        fill();
    }

    private void fill() {
        this.setMateriaList(sieniMateriaFacadeRemote.findAll());
    }

    public void guardar() {

        if (validarNuevo(this.getMateriaNuevo())) {
            String anioActual = new FormatUtils().getFormatedAnio(new Date());
            this.getMateriaNuevo().setMaEstado("D");
            sieniMateriaFacadeRemote.create(this.getMateriaNuevo());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Materia", this.getMateriaNuevo().getIdMateria(), 'D'));
            FacesMessage msg = new FacesMessage("Materia Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setIndexMenu(0);
        }
        this.setMateriaNuevo(new SieniMateria());
        fill();
    }
    
    public boolean validarNuevo(SieniMateria nuevo) {
        boolean ban = true;

        return ban;
    }
}
