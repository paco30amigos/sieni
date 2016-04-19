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
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniNotaFacadeRemote;
import sv.com.mined.sieni.SieniNotasModHistFacadeRemote;
import sv.com.mined.sieni.form.RptNotasModificadasForm;
import sv.com.mined.sieni.model.SieniNota;
import sv.com.mined.sieni.model.SieniNotasModHist;
import sv.com.mined.sieni.pojos.rpt.RptNotasModificadasPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "rptNotasModificadasController")
public class RptNotasModificadasController extends RptNotasModificadasForm {

    @EJB
    private SieniNotasModHistFacadeRemote sieniNotasModHistFacadeRemote;

    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;

    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    @EJB
    private SieniNotaFacadeRemote sieniNotaFacadeRemote;

    @PostConstruct
    public void init() {
        this.setTipoRpt(0);
        FormatUtils fu =new FormatUtils();
        this.setDesde(fu.getFormatDate(fu.getFormatedDate(new Date())));
        this.setHasta(fu.getFormatDate(fu.getFormatedDate(new Date())));
        //fill();
    }

    public void fill() {
        RptNotasModificadasPojo elem;
        List<SieniNotasModHist> bitacora = sieniNotasModHistFacadeRemote.findByFecha(this.getDesde(), this.getHasta());
        this.setListDatos(new ArrayList<RptNotasModificadasPojo>());
        for (SieniNotasModHist actual : bitacora) {
            String docente = sieniDocenteFacadeRemote.findByDocenteId(actual.getNtDocente()).getNombreCompleto();
            SieniNota nota = sieniNotaFacadeRemote.findById(actual.getIdNotas());
            String alumno = sieniAlumnoFacadeRemote.findAlumnoById(nota.getIdAlumno()).getNombreCompleto();
            String evaluacion = nota.getIdEvaluacion().getEvNombre();
            elem = new RptNotasModificadasPojo(actual.getNtTipoModificacion(), nota.getNtCalificacion().toString(), new FormatUtils().getFormatedDate(actual.getNtFechaMod()), new FormatUtils().getFormatedTime(actual.getNtFechaMod()), docente, alumno, evaluacion, actual.getNtCalificacion().toString());
            this.getListDatos().add(elem);
        }
        this.setTotalTransacciones(Long.parseLong(this.getListDatos().size() + ""));
    }

    public void generarReporte() {
        String path = "resources/reportes/rtpNotasModificadas.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        parameterMap.put("desde", new FormatUtils().getFormatedDate(this.getDesde()));
        parameterMap.put("hasta", new FormatUtils().getFormatedDate(this.getHasta()));

        try {
            RptNotasModificadasController.generateReport(path, "rtpNotasModificadas" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
        } catch (JRException ex) {
            Logger.getLogger("error 1").log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger("error 2").log(Level.SEVERE, null, ex);
        }
    }

    public void refresh() {
        this.setBitacoraList(sieniNotasModHistFacadeRemote.findByFecha(this.getDesde(), this.getHasta()));
    }

}
