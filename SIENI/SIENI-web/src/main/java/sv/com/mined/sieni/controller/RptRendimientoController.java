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
import sv.com.mined.sieni.SieniNotaFacadeRemote;
import sv.com.mined.sieni.SieniSeccionFacadeRemote;
import sv.com.mined.sieni.form.RptRendimientoForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniEvaluacion;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniNota;
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
        this.setGradosList(sieniGradoFacadeRemote.findAll());
        this.setMateriaList(sieniMateriaFacadeRemote.findAll());
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
            this.setSeccionesList(cod.getSieniSeccionList());
        }
        //fill();
    }

    public void fill() {
        RptRendimientoPojo elem = new RptRendimientoPojo();

        List<SieniNota> notas = sieniNotaFacadeRemote.findByGradoSecMatRpt(this.getDesde(), this.getHasta(), this.getIdGrado(), this.getIdSeccion(), this.getIdMateria());
        this.setListDatos(new ArrayList<RptRendimientoPojo>());

        double totalNotas = notas.size();
        float aprobados = 0;
        float reprobados = 0;
        float totalAprobados = 0;
        float totalReprobados = 0;
        float promedio = 0;
        for (SieniNota actual : notas) {
            if (actual.getNtCalificacion() >= 6.00) {
                aprobados++;
            } else {
                reprobados++;
            }
        }
        totalAprobados = (float) ((aprobados * 100) / totalNotas);
        totalReprobados = (float) ((reprobados * 100) / totalNotas);
        promedio = (totalAprobados + totalReprobados) / 2;

        for (SieniNota actual : notas) {
            elem = new RptRendimientoPojo(actual.getIdEvaluacion().getIdCurso().getIdGrado().getGrNombre(), actual.getIdEvaluacion().getIdCurso().getIdSeccion().getScDescripcion(), actual.getIdEvaluacion().getIdMateria().getMaNombre(), null, actual.getIdEvaluacion().getEvTipo().toString(), Float.toString(totalAprobados) + " %", Float.toString(totalReprobados) + " %", Float.toString(promedio));
            this.getListDatos().add(elem);
        }

        //List<SieniEvaluacion> evaluaciones = sieniEvaluacionFacadeRemote.findbyRendimientoRpt(this.getDesde(), this.getHasta(), this.getGrado(), this.getSeccion(), this.getMateria());
        this.setTotalTransacciones(Long.parseLong(this.getListDatos().size() + ""));
    }

    public void generarReporte() {
        fill();
        String path = "resources/reportes/rtpRendimiento.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("anio", this.getAnio());
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        parameterMap.put("desde", new FormatUtils().getFormatedDate(this.getDesde()));
        parameterMap.put("hasta", new FormatUtils().getFormatedDate(this.getHasta()));
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
