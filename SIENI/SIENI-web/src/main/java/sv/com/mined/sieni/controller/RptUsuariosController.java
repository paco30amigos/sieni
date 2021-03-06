/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniAlumnRolFacadeRemote;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniDocentRolFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.form.RptUsuariosForm;
import sv.com.mined.sieni.model.SieniAlumnRol;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniDocentRol;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.pojos.rpt.RptUsuariosPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author Marlon Alexander Palacios Díaz
 */
@SessionScoped
@ManagedBean(name = "rptUsuariosController")
public class RptUsuariosController extends RptUsuariosForm {

    @EJB
    SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;

    @EJB
    SieniAlumnRolFacadeRemote sieniAlumnoRolFacadeRemote;
    
    @EJB
    SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    
    @EJB
    SieniDocentRolFacadeRemote sieniDocenteRolFacadeRemote;

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setTipoUser(1);
        this.setEstadoUser(1);
        this.setTotalUsuarios("0");
        this.setTipoRpt(0);
        this.setListDatos(new ArrayList<RptUsuariosPojo>());
        fill();
    }

    public void fill() {
        RptUsuariosPojo elem = new RptUsuariosPojo();
        this.setListDatos(new ArrayList<RptUsuariosPojo>());
        List<SieniDocentRol> docentes = new ArrayList<SieniDocentRol>();
        List<SieniAlumnRol> alumnos = new ArrayList<SieniAlumnRol>();
        switch (this.getTipoUser()) {
            case 0://TODOS
                docentes = sieniDocenteRolFacadeRemote.findUsuariosRpt(this.getEstadoUser());
                alumnos = sieniAlumnoRolFacadeRemote.findUsuariosRpt(this.getEstadoUser());
                break;
            case 1: //DOCENTES
                docentes = sieniDocenteRolFacadeRemote.findUsuariosRpt(this.getEstadoUser());
                break;
            case 2: //ALUMNOS
                alumnos = sieniAlumnoRolFacadeRemote.findUsuariosRpt(this.getEstadoUser());
                break;
        }
        for (SieniDocentRol actual : docentes) {
            SieniDocente d = sieniDocenteFacadeRemote.findByDocenteId(actual.getIdDocente());
            elem = new RptUsuariosPojo(null, actual, d.getDcUsuario(), d.getNombreCompleto(), 1, actual.getSdrFechaIngreso(), actual.getSdrEstado());
            this.getListDatos().add(elem);
        }
        for (SieniAlumnRol actual : alumnos) {
            SieniAlumno a = sieniAlumnoFacadeRemote.findAlumnoById(actual.getIdAlumno());
            elem = new RptUsuariosPojo(actual, null, a.getAlUsuario(), a.getNombreCompleto(), 2, actual.getSarFechaIngreso(), actual.getSarEstado());
            this.getListDatos().add(elem);
        }
        this.setTotalUsuarios("" + this.getListDatos().size());

    }

    public void generarReporte() {
        fill();
        String path = "resources/reportes/rtpUsuarios.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        switch (this.getTipoUser()) {
            case 0: //DOCENTES
                parameterMap.put("tipoUsuario", "TODOS");
                break;
            case 1: //DOCENTES
                parameterMap.put("tipoUsuario", "DOCENTE");
                break;
            case 2: //ALUMNOS
                parameterMap.put("tipoUsuario", "ALUMNO");
                break;
        }
        switch (this.getEstadoUser()) {
            case 0: //DOCENTES
                parameterMap.put("estadoUsuario", "TODOS");
                break;
            case 1: //DOCENTES
                parameterMap.put("estadoUsuario", "ACTIVO");
                break;
            case 2: //ALUMNOS
                parameterMap.put("estadoUsuario", "INACTIVO");
                break;
        }
        try {
            RptUsuariosController.generateReport(path, "rtpUsuarios" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
            registrarEnBitacora("Reporte", "Usuarios", 0L);
        } catch (JRException ex) {
            Logger.getLogger("error 1").log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger("error 2").log(Level.SEVERE, null, ex);
        }
    }

    public void refresh() {
        fill();
    }

}
