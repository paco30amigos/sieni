/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.form.RptUsuariosForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniBitacora;
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
    SieniDocenteFacadeRemote sieniDocenteFacadeRemote;

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setTipoUser(1);
        this.setTotalUsuarios("0");
        this.setTipoRpt(0);
        this.setListDatos(new ArrayList<RptUsuariosPojo>());
        fill();
    }

    public void fill() {
        RptUsuariosPojo elem = new RptUsuariosPojo();
        this.setListDatos(new ArrayList<RptUsuariosPojo>());
        switch(this.getTipoUser()){
            case 1: //DOCENTES
                List<SieniDocente> docentes = sieniDocenteFacadeRemote.findAll();
                for (SieniDocente actual : docentes) {
                     elem = new RptUsuariosPojo(null, actual, actual.getDcUsuario(),actual.getNombreCompleto(),"DOCENTE","SI");
                    this.getListDatos().add(elem);
                }
                break;
            case 2: //ALUMNOS
                List<SieniAlumno> alumnos = sieniAlumnoFacadeRemote.findAlumnoActivos();
                for (SieniAlumno actual : alumnos) {
                     elem = new RptUsuariosPojo(actual, null, actual.getAlUsuario(),actual.getNombreCompleto(),"ALUMNO","SI");
                    this.getListDatos().add(elem);
                }
                break;
        }
        this.setTotalUsuarios("" + this.getListDatos().size());
        
        
    }

    public void generarReporte() {
        fill();
        String path = "resources/reportes/rtpUsuarios.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        try {
            RptAlumnosController.generateReport(path, "rtpUsuarios" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Generar Reporte", "Usuarios", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
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
