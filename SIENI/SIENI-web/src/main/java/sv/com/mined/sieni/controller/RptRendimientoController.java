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
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniEvaluacionFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniSeccionFacadeRemote;
import sv.com.mined.sieni.form.RptRendimientoForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniEvaluacion;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.pojos.rpt.RptRendimientoPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author Alejandro
 */
@SessionScoped
@ManagedBean(name = "rptRendimientoController")
public class RptRendimientoController extends RptRendimientoForm {
    
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote; 
    @EJB
    private SieniGradoFacadeRemote sieniGradoFacadeRemote;
    @EJB
    private SieniSeccionFacadeRemote sieniSeccionFacadeRemote;
    @EJB
    private SieniMateriaFacadeRemote sieniMateriaFacadeRemote;
    @EJB
    private SieniEvaluacionFacadeRemote sieniEvaluacionFacadeRemote;
    
    @PostConstruct
    public void init() {
        this.setFormatoRpt("PDF");
        this.setAnio("2,015");
        this.setGradosList(sieniGradoFacadeRemote.findAll());
        this.setMateriaList(sieniMateriaFacadeRemote.findAll());
        this.setSeccionesList(sieniSeccionFacadeRemote.findAll());
        //fill();
    }
    
    public void fill() {
        RptRendimientoPojo elem = new RptRendimientoPojo();

        
        
        List<SieniEvaluacion> evaluaciones = sieniEvaluacionFacadeRemote.findbyRendimientoRpt(this.getDesde(), this.getHasta(), this.getGrado(), this.getSeccion(), this.getMateria());
        this.setListDatos(new ArrayList<RptRendimientoPojo>());
        for (SieniEvaluacion actual : evaluaciones) {
            elem = new RptRendimientoPojo(this.getGrado(), this.getSeccion(), this.getMateria(), null, actual.getEvTipo(), null, null, null);
            this.getListDatos().add(elem);
        }
        
        this.setTotalTransacciones(Long.parseLong(this.getListDatos().size()+""));
    }

    public void generarReporte() {
        fill();
        String path = "resources/reportes/rtpRendimiento.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("anio", this.getAnio());
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        try {
            RptRendimientoController.generateReport(path, "rtpRendimiento" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Generar Reporte", "Docente", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0), req.getRemoteAddr()));
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
    
    public void getSeccionesGrado(ValueChangeEvent a) {
        Long idGrado = (Long) a.getNewValue();
        SieniGrado cod = new SieniGrado();
        for (SieniGrado actual : this.getGradosList()) {
            if (actual.getIdGrado().equals(idGrado)) {
                cod = actual;
                break;
            }
        }
        this.setSeccionesList(cod.getSieniSeccionList());
    }
}
