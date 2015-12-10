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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniPntosContrlFacadeRemote;
import sv.com.mined.sieni.converter.AlumnoConverter;
import sv.com.mined.sieni.form.RptParticipacionForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniPntosContrl;
import sv.com.mined.sieni.pojos.rpt.RptParticipacionPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author Alejandro
 */
@SessionScoped
@ManagedBean(name = "rptParticipacionController")
public class RptParticipacionController extends RptParticipacionForm {
    
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;
    
    @EJB
    private SieniPntosContrlFacadeRemote sieniPntosContrlFacadeRemote;
    
    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    
    @PostConstruct
    public void init() {
        this.setFormatoRpt("PDF");
        this.setAnio("2,015");
        fill();
    }
    
    public void fill() {
        RptParticipacionPojo elem = new RptParticipacionPojo();

        //List<SieniPntosContrl> puntos = sieniPntosContrlFacadeRemote.findAll();
        this.setListDatos(new ArrayList<RptParticipacionPojo>());
        
        List<SieniAlumno> alumnos = sieniPntosContrlFacadeRemote.findByAlumno();
        for(SieniAlumno actual : alumnos){
            List<SieniClase> clases = sieniPntosContrlFacadeRemote.findByClasesAlumnos(actual.getIdAlumno());
            for(SieniClase clase : clases){
                
                
            
                elem = new RptParticipacionPojo(actual.getAlNombreCompleto(), clase.getIdCurso().getCrNombre(), null, null, null);
                this.getListDatos().add(elem);
            }            
        }
        
        this.setTotalTransacciones(Long.parseLong(this.getListDatos().size() + ""));
    }

    public void generarReporte() {
        fill();
        String path = "resources/reportes/rtpParticipacion.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("anio", this.getAnio());
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        try {
            RptParticipacionController.generateReport(path, "rtpParticipacion" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
            registrarEnBitacora("Reporte", "Participacion", 0L);
        } catch (JRException ex) {
            Logger.getLogger("error 1").log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger("error 2").log(Level.SEVERE, null, ex);
        }
    }

    public void refresh() {
        String anio = this.getAnio().replaceAll(",", "");
        Integer anioInt = Integer.parseInt(anio);
        
        
    }
}
