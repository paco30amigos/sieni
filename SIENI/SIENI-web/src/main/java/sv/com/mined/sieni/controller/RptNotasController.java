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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniNotaFacadeRemote;
import sv.com.mined.sieni.form.RptNotasForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniNota;
import sv.com.mined.sieni.model.SieniSeccion;
import sv.com.mined.sieni.pojos.rpt.RptNotasPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "rptNotasController")
public class RptNotasController extends RptNotasForm {

    @EJB
    private SieniNotaFacadeRemote sieniNotasFacadeRemote;

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;
    @EJB
    private SieniGradoFacadeRemote sieniGradoFacadeRemote;
    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;

    @PostConstruct
    public void init() {
        this.setFormatoRpt("PDF");
        this.setDesde(new Date());
        this.setHasta(new Date());
        this.setTotalTransacciones(0);
        this.setTipoRpt(0);
        this.setListaDatos(new ArrayList<RptNotasPojo>());
        this.setGrado(new SieniGrado(0L));
        this.setSeccion(new SieniSeccion(0L));
        fill();
    }

    private void fill() {
        Date desde = this.getDesde();
        Date hasta = this.getHasta();
        this.setGradosList(sieniGradoFacadeRemote.findAllNoInactivos());
        this.setSeccionesList(new ArrayList<SieniSeccion>());
        if (this.getGradosList() != null && !this.getGradosList().isEmpty()) {
            if (this.getGradosList().get(0).getSieniSeccionList() != null
                    && !this.getGradosList().get(0).getSieniSeccionList().isEmpty()) {
                this.setSeccionesList(this.getGradosList().get(0).getSieniSeccionList());
            }
        }
        this.setListaDatos(new ArrayList<RptNotasPojo>());
        for (SieniNota nota : sieniNotasFacadeRemote.getNotasRpt(desde, hasta, this.getGrado().getIdGrado(), this.getSeccion().getIdSeccion())) {
            SieniAlumno alumno = sieniAlumnoFacadeRemote.findAlumnoById(nota.getIdAlumno());
            this.getListaDatos().add(new RptNotasPojo(alumno.getNombreCompleto(), nota.getIdEvaluacion().getIdMateria().getIdGrado().getGrNombre(), nota.getIdEvaluacion().getIdCurso().getIdSeccion().getScDescripcion(), nota.getIdEvaluacion().getIdMateria().getMaNombre(), nota.getIdEvaluacion().getEvNombre(), nota.getNtCalificacion().toString(), alumno.getAlCarnet(), nota.getIdEvaluacion().getEvTipo()));
        }
        this.setTotalTransacciones(this.getListaDatos().size());
    }

    public void generarReporte() {
//        fill();
        String txtGrado = "", txtSeccion = "";
        if (this.getGrado() != null && this.getGrado().getIdGrado() != null && !this.getGrado().getIdGrado().equals(0L)) {
            txtGrado = this.getGrado().getGrNombre();
        } else {
            txtGrado = "Todos";
        }

        if (this.getSeccion() != null && this.getSeccion().getIdSeccion() != null && !this.getSeccion().getIdSeccion().equals(0L)) {
            txtSeccion = this.getSeccion().getScDescripcion();
        } else {
            txtSeccion = "Todos";
        }
        String path = "resources/reportes/rtpNotas.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("desde", new FormatUtils().getFormatedDate(this.getDesde()));
        parameterMap.put("hasta", new FormatUtils().getFormatedDate(this.getHasta()));
        parameterMap.put("grado", txtGrado);
        parameterMap.put("seccion", txtSeccion);
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));

        try {
            RptAlumnosController.generateReport(path, "rtpNotas" + new Date().getTime(), this.getListaDatos(), parameterMap, this.getTipoRpt());
            registrarEnBitacora("Reporte", "Notas", 0L);
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
        for (SieniGrado actual : this.getGradosList()) {
            if (actual.getIdGrado().equals(idGrado)) {
                cod = actual;
                break;
            }
        }
        if (cod.getIdGrado() == null) {
            this.setGrado(new SieniGrado(0L));
            this.setSeccion(new SieniSeccion(0L));
            this.setSeccionesList(new ArrayList<SieniSeccion>());
        } else {
            this.setGrado(cod);
            this.setSeccionesList(cod.getSieniSeccionList());
            this.setSeccion(this.getSeccionesList() != null && !this.getSeccionesList().isEmpty() ? this.getSeccionesList().get(0) : null);
        }
    }

}
