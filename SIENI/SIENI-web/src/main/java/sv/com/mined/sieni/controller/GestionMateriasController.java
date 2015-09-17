/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniSeccionFacadeRemote;
import sv.com.mined.sieni.form.GestionMateriasForm;
import sv.com.mined.sieni.model.SieniMateria;

/**
 *
 * @author Alejandro
 */
@SessionScoped
@ManagedBean(name = "gestionMateriaController")
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

    }
}
