/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

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
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniPntosContrlFacadeRemote;
import sv.com.mined.sieni.SieniSeccionFacadeRemote;
import sv.com.mined.sieni.form.RptEstadisticoAvanceForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniCatPuntos;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniPntosContrl;
import sv.com.mined.sieni.model.SieniSeccion;
import sv.com.mined.sieni.pojos.rpt.RptEstadisticoAvancePojo;
import utils.DateUtils;
import utils.FormatUtils;



public class RptEstadisticoAvanceController extends RptEstadisticoAvanceForm  {
    
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote; 
    @EJB
    private SieniGradoFacadeRemote sieniGradoFacadeRemote;
    @EJB
    private SieniSeccionFacadeRemote sieniSeccionFacadeRemote;
    @EJB
    private SieniMateriaFacadeRemote sieniMateriaFacadeRemote;
    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    @EJB
    private SieniPntosContrlFacadeRemote sieniPntosContrlFacadeRemote;
    
    
    
    @PostConstruct
    public void init() {
        this.setTotalAlumnos("0");
        this.setTipoRpt(0);
        this.setListDatos(new ArrayList<RptEstadisticoAvancePojo>());
        this.setListGrados(sieniGradoFacadeRemote.findAll());
        this.setListSecciones(sieniSeccionFacadeRemote.findAll());
        this.setListMaterias(sieniMateriaFacadeRemote.findMateriasActivas());
        this.setListAlumnos(sieniAlumnoFacadeRemote.findAlumnoActivos());
        fill();
    }

    
    
    public void fill() {
        RptEstadisticoAvancePojo elem = new RptEstadisticoAvancePojo();
        List<SieniPntosContrl> rptAvance = new ArrayList<SieniPntosContrl>();
        this.setGrado(null);
        this.setSeccion(null);
        this.setMateria(null);
        this.setAlumno(null);
        
        this.setListDatos(new ArrayList<RptEstadisticoAvancePojo>());
        
        for (SieniGrado actual : this.getListGrados()) {
            if(actual.getIdGrado().intValue() == this.getIdgrado()){
                this.setGrado(actual);
            }
        }
        
        for (SieniSeccion actual : this.getListSecciones()) {
            if(actual.getIdSeccion().intValue() == this.getIdseccion()){
                this.setSeccion(actual);
            }
        }
        
        for (SieniMateria actual : this.getListMaterias()) {
            if(actual.getIdMateria().intValue() == this.getIdmateria()){
                this.setMateria(actual);
            }
        }
        
        for (SieniAlumno actual : this.getListAlumnos()) {
            if(actual.getIdAlumno().intValue() == this.getIdalumno()){
                this.setAlumno(actual);
            }
        }
        
        rptAvance = sieniPntosContrlFacadeRemote.findAll();
        for (SieniPntosContrl actual : rptAvance) {
             elem = new RptEstadisticoAvancePojo(actual.getIdClase(),actual.getIdAlumno(),actual.getIdClase().getIdCurso().getIdGrado().getGrNombre(),actual.getIdClase().getIdCurso().getIdSeccion().getScDescripcion(),actual.getIdClase().getIdCurso().getIdMateria().getMaCodigo(),actual.getIdAlumno().getAlCarnet(),actual.getIdClase().getTipo());
            this.getListDatos().add(elem);
        }
        this.setTotalAlumnos("" + this.getListDatos().size());
        
        
    }

    public void generarReporte() {
        fill();
        String path = "resources/reportes/rtpEstadisticoAvance.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        if(this.getGrado() != null){
            parameterMap.put("grado", this.getGrado().getGrNombre());
        }
        if(this.getSeccion()!= null){
            parameterMap.put("grado", this.getSeccion().getScDescripcion());
        }
        if(this.getMateria()!= null){
            parameterMap.put("grado", this.getMateria().getMaCodigo());
        }
        if(this.getAlumno()!= null){
            parameterMap.put("grado", this.getAlumno().getAlCarnet());
        }
        
        try {
            RptUsuariosController.generateReport(path, "rptEstadisticoAvance" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Generar Reporte", "Estadistico Avance", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0), req.getRemoteAddr()));
        } catch (JRException ex) {
            Logger.getLogger("error 1").log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger("error 2").log(Level.SEVERE, null, ex);
        }
    }

    public void refresh() {
        fill();
    }
    
    
    public void getSeccionesGrado(ValueChangeEvent a) {
        Long idGrado = (Long) a.getNewValue();
        SieniGrado cod = new SieniGrado();
        for (SieniGrado actual : this.getListGrados()) {
            if (actual.getIdGrado().equals(idGrado)) {
                cod = actual;
                break;
            }
        }
        this.setListSecciones(cod.getSieniSeccionList());
    }
}
