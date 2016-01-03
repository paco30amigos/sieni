/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.io.IOException;
import java.io.Serializable;
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
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniCatPuntosFacadeRemote;
import sv.com.mined.sieni.SieniClaseFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniPntosContrlFacadeRemote;
import sv.com.mined.sieni.form.RptEstadisticoAvanceForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniCatPuntos;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniSeccion;
import sv.com.mined.sieni.pojos.rpt.RptEstadisticoAvancePojo;
import utils.DateUtils;
import utils.FormatUtils;

@SessionScoped
@ManagedBean(name = "rptEstadisticoAvanceController")
public class RptEstadisticoAvanceController extends RptEstadisticoAvanceForm implements Serializable {

    @EJB
    private SieniGradoFacadeRemote sieniGradoFacadeRemote;
    @EJB
    private SieniPntosContrlFacadeRemote sieniPntosContrlFacadeRemote;
    @EJB
    private SieniMateriaFacadeRemote sieniMateriaFacadeRemote;
    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    @EJB
    private SieniCatPuntosFacadeRemote sieniCatPuntosFacadeRemote;
    @EJB
    private SieniClaseFacadeRemote sieniClaseFacadeRemote;

    @PostConstruct
    public void init() {
        this.setIdgrado(0);
        this.setIdseccion(0);
        this.setIdalumno(0L);
        this.setIdmateria(0);
        this.setGrado(new SieniGrado());
        this.setSeccion(new SieniSeccion());
        this.setMateria(new SieniMateria());
        this.setAlumno(new SieniAlumno());
        this.setTotalAlumnos("0");
        this.setTipoRpt(0);
        this.setDesde(new Date());
        this.setHasta(new Date());
        this.setListDatos(new ArrayList<RptEstadisticoAvancePojo>());
        this.setListGrados(sieniGradoFacadeRemote.findAllNoInactivos());
        if (this.getListGrados() != null && !this.getListGrados().isEmpty()) {
            if (this.getListGrados().get(0).getSieniSeccionList() != null
                    && !this.getListGrados().get(0).getSieniSeccionList().isEmpty()) {
                initSeccion();
//                this.setGrado(this.getListGrados().get(0));
//                this.setMateria(this.getListMaterias().get(0));
//                String anio = "2015";
//                if (getDesde() != null) {
//                    anio = new FormatUtils().getFormatedAnio(getDesde());
//                } else if (getHasta() != null) {
//                    anio = new FormatUtils().getFormatedAnio(getDesde());
//                }
                this.setListAlumnos(sieniAlumnoFacadeRemote.findAlumnosGradoSeccionAnio(this.getSeccion().getIdGrado().getIdGrado(), this.getSeccion().getIdSeccion()));
            } else {
                this.setListAlumnos(new ArrayList<SieniAlumno>());
            }
        }
//        fill();
    }

    public void initSeccion() {
        if (this.getListGrados() != null && !this.getListGrados().isEmpty()) {
            Long idGrado = (Long) this.getListGrados().get(0).getIdGrado();
            SieniGrado cod = new SieniGrado();
            for (SieniGrado actual : this.getListGrados()) {
                if (actual.getIdGrado().equals(idGrado)) {
                    cod = actual;
                    break;
                }
            }
            this.setListMaterias(sieniMateriaFacadeRemote.findMateriasActivasByGrado(idGrado));
            this.setListSecciones(cod.getSieniSeccionList());
            this.setSeccion(this.getListSecciones().get(0));
        } else {
            this.setListMaterias(new ArrayList<SieniMateria>());
        }
    }

    public void fill() {
        RptEstadisticoAvancePojo elem;
        this.setListDatos(new ArrayList<RptEstadisticoAvancePojo>());
        List<SieniClase> rptAvance;
//        rptAvance = sieniClaseFacadeRemote.findRptAvance(this.getAlumno().getIdAlumno());
        rptAvance = sieniClaseFacadeRemote.findClaseByAlumno(this.getAlumno().getIdAlumno());
        for (SieniClase actual : rptAvance) {
            Integer totalPuntos, puntosAl;
            puntosAl = sieniPntosContrlFacadeRemote.findByCountClase(actual.getIdClase(), this.getAlumno().getIdAlumno());
//                        if (numPuntos > 0) {
//                            System.out.println("aja");
//                        }
            SieniCatPuntos ptos = sieniCatPuntosFacadeRemote.findByClase(actual.getIdClase());
            totalPuntos = ptos.getCpNumPuntos();
            elem = new RptEstadisticoAvancePojo(actual, null, actual.getIdCurso().getIdMateria().getMaNombre(), actual.getClTema(), actual.getTipo(), totalPuntos, puntosAl);
            this.getListDatos().add(elem);
        }
        this.setTotalAlumnos("" + this.getListDatos().size());

    }

    public void generarReporte() {
//        fill();
        String path = "resources/reportes/rtpAvance.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        if (this.getGrado() != null) {
            parameterMap.put("grado", this.getGrado().getGrNombre());
        }
        if (this.getSeccion() != null) {
            parameterMap.put("seccion", this.getSeccion().getScDescripcion());
        }
        if (this.getMateria() != null) {
            parameterMap.put("materia", this.getMateria().getMaNombre());
        }
        if (this.getAlumno() != null) {
            parameterMap.put("alumno", this.getAlumno().getAlCarnet());
        }

        try {
            RptUsuariosController.generateReport(path, "rptAvance" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
            registrarEnBitacora("Reporte", "Estadistico de avance", 0L);
        } catch (JRException ex) {
            Logger.getLogger("error 1").log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger("error 2").log(Level.SEVERE, null, ex);
        }
    }

    public void refresh() {
        init();
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
        this.setSeccion(new SieniSeccion());
        this.setListSecciones(cod.getSieniSeccionList());
        if (!this.getListSecciones().isEmpty()) {
            this.setSeccion(this.getListSecciones().get(0));
            if (this.getSeccion() != null) {
                this.setListAlumnos(sieniAlumnoFacadeRemote.findAlumnosGradoSeccionAnio(this.getSeccion().getIdGrado().getIdGrado(), this.getSeccion().getIdSeccion()));
                this.setAlumno(this.getListAlumnos().get(0));
            }
        } else {
            this.setListAlumnos(new ArrayList<SieniAlumno>());
        }
    }

    public void getAlumnosSecciones(ValueChangeEvent a) {
        Long idSeccion = (Long) a.getNewValue();
        SieniSeccion selec = null;
        for (SieniSeccion actual : this.getListSecciones()) {
            if (actual.getIdSeccion().equals(idSeccion)) {
                selec = actual;
                this.setSeccion(selec);
                break;
            }
        }
        if (selec != null) {
            this.setListAlumnos(sieniAlumnoFacadeRemote.findAlumnosGradoSeccionAnio(selec.getIdGrado().getIdGrado(), selec.getIdSeccion()));
            this.setAlumno(this.getListAlumnos().get(0));
        }
    }
}
