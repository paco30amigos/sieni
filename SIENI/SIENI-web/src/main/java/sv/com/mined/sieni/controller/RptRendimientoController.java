/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniEvaluacionFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniNotaFacadeRemote;
import sv.com.mined.sieni.SieniSeccionFacadeRemote;
import sv.com.mined.sieni.form.RptRendimientoForm;
import sv.com.mined.sieni.model.SieniEvaluacion;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniNota;
import sv.com.mined.sieni.model.SieniSeccion;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
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
    @EJB
    private SieniNotaFacadeRemote sieniNotaFacadeRemote;

    @PostConstruct
    public void init() {
        this.setTipoRpt(0);
        this.setAnio("2,015");
        this.setDesde(new Date());
        this.setHasta(new Date());
        this.setGradosList(sieniGradoFacadeRemote.findAllNoInactivos());
//        this.setMateriaList(sieniMateriaFacadeRemote.findAllNoInactivas());
//        this.setSeccionesList(sieniSeccionFacadeRemote.findAll());
        if (this.getGradosList() != null && !this.getGradosList().isEmpty()) {
            Long idGrado = (Long) this.getGradosList().get(0).getIdGrado();
            SieniGrado cod = new SieniGrado();
            for (SieniGrado actual : this.getGradosList()) {
                if (actual.getIdGrado().equals(idGrado)) {
                    cod = actual;
                    break;
                }
            }
            this.setMateriaList(sieniMateriaFacadeRemote.findMateriasActivasByGrado(idGrado));
            this.setSeccionesList(cod.getSieniSeccionList());
        } else {
            this.setMateriaList(new ArrayList<SieniMateria>());
        }
        //fill();
    }

    public void fill() {
        RptRendimientoPojo elem = new RptRendimientoPojo();
        if (this.getIdMateria() == null) {
            new ValidationPojo().printMsj("No se ha seleccionado una materia valida", FacesMessage.SEVERITY_ERROR);
        } else {
            List<SieniEvaluacion> tipoEvaluacion = sieniEvaluacionFacadeRemote.findByMateria(this.getIdMateria());
            this.setListDatos(new ArrayList<RptRendimientoPojo>());
            if (!tipoEvaluacion.isEmpty()) {
                for (SieniEvaluacion tipoActual : tipoEvaluacion) {
                    List<SieniNota> notas = sieniNotaFacadeRemote.findByGradoSecMatRpt(this.getDesde(), this.getHasta(), this.getIdGrado(), this.getIdSeccion(), this.getIdMateria(), tipoActual.getIdEvaluacion());

                    double totalNotas = notas.size();
                    Integer totalAlumnos = notas.size();
                    float aprobados = 0;
                    float reprobados = 0;
                    double totalAprobados = 0;
                    double totalReprobados = 0;
                    float suma = 0;
                    double promedio = 0;

                    for (SieniNota actual : notas) {
                        if (actual.getNtCalificacion() >= 6.00) {
                            aprobados++;
                        } else {
                            reprobados++;
                        }
                        suma += actual.getNtCalificacion();
                    }

                    totalAprobados = (double) ((aprobados * 100) / totalNotas);
                    totalReprobados = (double) ((reprobados * 100) / totalNotas);
                    BigDecimal aprob = new BigDecimal(totalAprobados);
                    BigDecimal reprob = new BigDecimal(totalReprobados);
                    aprob = aprob.setScale(2, RoundingMode.HALF_UP);
                    reprob = reprob.setScale(2, RoundingMode.HALF_UP);
                    promedio = suma / notas.size();
                    BigDecimal prom = new BigDecimal(promedio);
                    prom = prom.setScale(2, RoundingMode.HALF_UP);

                    SieniGrado grado = sieniGradoFacadeRemote.findByIdGrado(this.getIdGrado());
                    SieniSeccion seccion = sieniSeccionFacadeRemote.findByIdSeccion(this.getIdSeccion());
                    SieniMateria materia = sieniMateriaFacadeRemote.findByIdMateria(this.getIdMateria());
                    if (totalAlumnos == 0) {
                        elem = new RptRendimientoPojo(grado.getGrNombre(), seccion.getScDescripcion(), materia.getMaNombre(), String.valueOf(totalAlumnos), tipoActual.getEvTipo(), "No Definido", "No Definido", "No Definido", tipoActual.getEvNombre());
                    } else {
                        elem = new RptRendimientoPojo(grado.getGrNombre(), seccion.getScDescripcion(), materia.getMaNombre(), String.valueOf(totalAlumnos), tipoActual.getEvTipo(), aprob.toString() + " %", reprob.toString() + " %", prom.toString(), tipoActual.getEvNombre());
                    }
                    this.getListDatos().add(elem);
                }
            }

            //List<SieniEvaluacion> evaluaciones = sieniEvaluacionFacadeRemote.findbyRendimientoRpt(this.getDesde(), this.getHasta(), this.getGrado(), this.getSeccion(), this.getMateria());
            this.setTotalTransacciones(Long.parseLong(this.getListDatos().size() + ""));
        }
    }

    public void generarReporte() {
//        fill();
        String path = "resources/reportes/rtpRendimiento.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("anio", this.getAnio());
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        parameterMap.put("desde", new FormatUtils().getFormatedDate(this.getDesde()));
        parameterMap.put("hasta", new FormatUtils().getFormatedDate(this.getHasta()));

        SieniGrado gradoSelec = null;
        for (SieniGrado grado : this.getGradosList()) {
            if (grado.getIdGrado().equals(this.getIdGrado())) {
                gradoSelec = grado;
                break;
            }
        }
        if (gradoSelec != null) {
            parameterMap.put("grado", gradoSelec.getGrNombre());
        } else {
            parameterMap.put("grado", "Grado no seleccionado");
        }
        SieniSeccion seccSelec = null;
        for (SieniSeccion actual : this.getSeccionesList()) {
            if (actual.getIdSeccion().equals(this.getIdSeccion())) {
                seccSelec = actual;
                break;
            }
        }
        if (seccSelec != null) {
            parameterMap.put("seccion", seccSelec.getScDescripcion());
        } else {
            parameterMap.put("seccion", "Seccion no seleccionada");
        }

        SieniMateria materia = null;
        for (SieniMateria actual : this.getMateriaList()) {
            if (actual.getIdMateria().equals(this.getIdMateria())) {
                materia = actual;
                break;
            }
        }
        if (materia != null) {
            parameterMap.put("materia", materia.getMaNombre());
        } else {
            parameterMap.put("materia", "Materia no seleccionada");
        }

        try {
            RptRendimientoController.generateReport(path, "rtpRendimiento" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
            registrarEnBitacora("Reporte", "Rendimiento", 0L);
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
        this.setMateriaList(sieniMateriaFacadeRemote.findMateriasActivasByGrado(idGrado));
        this.setSeccionesList(cod.getSieniSeccionList());
    }
}
