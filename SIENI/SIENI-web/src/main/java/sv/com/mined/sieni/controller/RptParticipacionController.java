/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
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
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.form.RptParticipacionForm;
import sv.com.mined.sieni.model.SieniBitacora;
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
    
    @PostConstruct
    public void init() {
        this.setFormatoRpt("PDF");
        this.setAnio("2,015");
        fill();
    }
    
    private void fill() {
        RptParticipacionPojo elem = new RptParticipacionPojo();


    }

    public void generarReporte() {
        fill();
        String path = "resources/reportes/rtpParticipacion.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("anio", this.getAnio());
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        try {
            RptParticipacionController.generateReport(path, "rtpParticipacion" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
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
}
