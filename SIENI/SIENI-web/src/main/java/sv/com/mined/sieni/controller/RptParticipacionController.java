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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniCatPuntosFacadeRemote;
import sv.com.mined.sieni.SieniClaseFacadeRemote;
import sv.com.mined.sieni.SieniMatriculaFacadeRemote;
import sv.com.mined.sieni.SieniPntosContrlFacadeRemote;
import sv.com.mined.sieni.form.RptParticipacionForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniCatPuntos;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniCurso;
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
    private SieniCatPuntosFacadeRemote sieniCatPuntosFacadeRemote;

    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;

    @EJB
    private SieniClaseFacadeRemote sieniClaseFacadeRemote;

    @EJB
    private SieniMatriculaFacadeRemote sieniMatriculaFacadeRemote;

    @PostConstruct
    public void init() {
        this.setTipoRpt(0);
        this.setAnio("2,015");
        this.setDesde(new Date());
        this.setHasta(new Date());
        //fill();
    }

    public HashMap<Long, List<SieniClase>> agruparPorCurso(List<SieniClase> clases) {
        HashMap<Long, List<SieniClase>> ret = new HashMap<>();

        for (SieniClase actual : clases) {
            if (ret.containsKey(actual.getIdCurso().getIdCurso())) {
                //suma uno al conteo actual
                ret.get(actual.getIdCurso().getIdCurso()).add(actual);
            } else {
                ret.put(actual.getIdCurso().getIdCurso(), new ArrayList<SieniClase>());
                ret.get(actual.getIdCurso().getIdCurso()).add(actual);
            }
        }

        return ret;
    }

    public void fill() {
        RptParticipacionPojo elem = new RptParticipacionPojo();

        this.setListDatos(new ArrayList<RptParticipacionPojo>());
        List<SieniAlumno> alumnos = sieniMatriculaFacadeRemote.findAlumNoInactivos(this.getDesde(), this.getHasta());
        for (SieniAlumno alumnoActual : alumnos) {
            List<SieniClase> clases = sieniClaseFacadeRemote.findClaseByAlumno(alumnoActual.getIdAlumno());
            Integer totalClasesAl = clases.size();
            Integer totalRecibido = 0;
            SieniCurso cursoActual = new SieniCurso();
            Integer numPuntos = 0;
            Integer totalClases = 0;
            if (!clases.isEmpty()) {
//                if (alumnoActual.getIdAlumno().equals(86L)) {
//                    System.out.println("aja");
//                }
                HashMap<Long, List<SieniClase>> clasesAct = agruparPorCurso(clases);
                for (Long key : clasesAct.keySet()) {
                    cursoActual = new SieniCurso();
                    totalClases = 0;
                    totalRecibido = 0;
                    for (SieniClase claseActual : clasesAct.get(key)) {

                        numPuntos = sieniPntosContrlFacadeRemote.findByCountClase(claseActual.getIdClase(), alumnoActual.getIdAlumno());
//                        if (numPuntos > 0) {
//                            System.out.println("aja");
//                        }

                        SieniCatPuntos ptos = sieniCatPuntosFacadeRemote.findByClase(claseActual.getIdClase());
                        if (ptos != null && ptos.getCpNumPuntos() != null && ptos.getCpNumPuntos() >= 0) {
                            totalClases = ptos.getCpNumPuntos();
                        }

                        if (totalClases > 0 && totalClases <= numPuntos) {//mayor o igual al 100%
                            totalRecibido++;
                        }
                        cursoActual = claseActual.getIdCurso();
                    }
                    BigDecimal participacion = new BigDecimal((totalRecibido + 0.0)/(totalClasesAl + 0.0)*100.0).setScale(2, RoundingMode.HALF_UP);
                    elem = new RptParticipacionPojo(alumnoActual.getAlNombreCompleto(), cursoActual.getCrNombre(), totalClasesAl.toString(), totalRecibido.toString(), participacion.doubleValue() + " %");
                    this.getListDatos().add(elem);
                }
            }
        }

        this.setTotalTransacciones(Long.parseLong(this.getListDatos().size() + ""));
    }

    public void generarReporte() {
//        fill();
        String path = "resources/reportes/rtpParticipacion.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("desde", new FormatUtils().getFormatedDate(this.getDesde()));
        parameterMap.put("hasta", new FormatUtils().getFormatedDate(this.getHasta()));
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
        fill();
    }
}
