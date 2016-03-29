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
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniNotaFacadeRemote;
import sv.com.mined.sieni.form.RptBoletaNotasForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniNota;
import sv.com.mined.sieni.model.SieniSeccion;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import sv.com.mined.sieni.pojos.rpt.RptBoletaNotasPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "rptBoletaNotasController")
public class RptBoletaNotasController extends RptBoletaNotasForm {

    @EJB
    private SieniNotaFacadeRemote sieniNotasFacadeRemote;
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
        this.setListaDatos(new ArrayList<RptBoletaNotasPojo>());
        this.setGrado(new SieniGrado());
        this.setSeccion(new SieniSeccion());
        this.setAlumnos(new ArrayList<SieniAlumno>());
        this.setAlumno(new SieniAlumno());
        this.setGradosList(sieniGradoFacadeRemote.findAllNoInactivos());
        this.setSeccionesList(new ArrayList<SieniSeccion>());
        if (this.getGradosList() != null && !this.getGradosList().isEmpty()) {
            if (this.getGradosList().get(0).getSieniSeccionList() != null
                    && !this.getGradosList().get(0).getSieniSeccionList().isEmpty()) {
                this.setSeccionesList(this.getGradosList().get(0).getSieniSeccionList());
                this.setSeccion(new SieniSeccion());
                this.setSeccion(this.getSeccionesList().get(0));
//                    String anio = "2015";
//                    if (getDesde() != null) {
//                        anio = new FormatUtils().getFormatedAnio(getDesde());
//                    } else if (getHasta() != null) {
//                        anio = new FormatUtils().getFormatedAnio(getDesde());
//                    }
                this.setAlumnos(sieniAlumnoFacadeRemote.findAlumnosGradoSeccionAnio(this.getSeccion().getIdGrado().getIdGrado(), this.getSeccion().getIdSeccion()));
            } else {
                this.setAlumnos(new ArrayList<SieniAlumno>());
            }
        }
//        fill();
    }

    private BigDecimal getPromedio(List<SieniNota> notas) {
        SieniNota ret = new SieniNota();
        BigDecimal promedio = BigDecimal.ZERO;
        for (SieniNota actual : notas) {
            BigDecimal nota = promedio.add(new BigDecimal(actual.getNtCalificacion()));
            ret.setNtCalificacion(nota.setScale(2, RoundingMode.HALF_UP).doubleValue());
            promedio = new BigDecimal(ret.getNtCalificacion());
        }
        if (!notas.isEmpty()) {
            return promedio.divide(new BigDecimal(notas.size()), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }
    }

    private HashMap<Long, List<SieniNota>> agruparPorMateria(List<SieniNota> notas) {
        HashMap<Long, List<SieniNota>> diferentes = new HashMap<>();
        for (SieniNota actual : notas) {
            if (!diferentes.containsKey(actual.getIdEvaluacion().getIdMateria().getIdMateria())) {
                diferentes.put(actual.getIdEvaluacion().getIdMateria().getIdMateria(), new ArrayList<SieniNota>());
            }
            diferentes.get(actual.getIdEvaluacion().getIdMateria().getIdMateria()).add(actual);
        }

        return diferentes;
    }

    private List<RptBoletaNotasPojo> agruparNotas(List<SieniNota> notas) {
        List<RptBoletaNotasPojo> ret = new ArrayList<>();
        HashMap<Long, List<SieniNota>> diferentes = agruparPorMateria(notas);
        List<SieniNota> primerTrimestre = new ArrayList<>();
        List<SieniNota> segundoTrimestre = new ArrayList<>();
        List<SieniNota> tercerTrimestre = new ArrayList<>();
        RptBoletaNotasPojo elem;
        this.setTotal(BigDecimal.ZERO);

        for (Long key : diferentes.keySet()) {
            List<SieniNota> notaMateria = diferentes.get(key);
            primerTrimestre = new ArrayList<SieniNota>();
            segundoTrimestre = new ArrayList<SieniNota>();
            tercerTrimestre = new ArrayList<SieniNota>();
            for (SieniNota actual : notaMateria) {
                if (actual.getIdEvaluacion().getEvFechaCierre().before(this.getTrimestre1())) {
                    primerTrimestre.add(actual);
                } else if (actual.getIdEvaluacion().getEvFechaCierre().before(this.getTrimestre2())) {
                    segundoTrimestre.add(actual);
                } else if (actual.getIdEvaluacion().getEvFechaCierre().before(this.getTrimestre3())) {
                    tercerTrimestre.add(actual);
                }
            }
            BigDecimal nota1 = getPromedio(primerTrimestre);
            BigDecimal nota2 = getPromedio(segundoTrimestre);
            BigDecimal nota3 = getPromedio(tercerTrimestre);
            BigDecimal promedio = nota1.add(nota2.add(nota3)).divide(new BigDecimal(3), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
            elem = new RptBoletaNotasPojo(notaMateria.get(0).getIdEvaluacion().getIdMateria().getMaNombre(), nota1.toString(), nota2.toString(), nota3.toString(), promedio.toString());
            ret.add(elem);
            this.setTotal(this.getTotal().add(promedio));
        }
        if (!diferentes.isEmpty()) {
            this.setTotal(this.getTotal().divide(new BigDecimal(diferentes.size()), RoundingMode.HALF_UP));
        } else {
            this.setTotal(BigDecimal.ZERO);
        }

        return ret;
    }

    private void fill() {
        if (this.getAlumno().getIdAlumno() != null && this.getSeccion().getIdSeccion() != null && this.getGrado().getIdGrado() != null && getDesde() != null && getHasta() != null) {
            Date desde = this.getDesde();
            Date hasta = this.getHasta();
//            this.setGradosList(sieniGradoFacadeRemote.findAllNoInactivos());
//            this.setSeccionesList(new ArrayList<SieniSeccion>());
//            if (this.getGradosList() != null && !this.getGradosList().isEmpty()) {
//                if (this.getGradosList().get(0).getSieniSeccionList() != null
//                        && !this.getGradosList().get(0).getSieniSeccionList().isEmpty()) {
//                    this.setSeccionesList(this.getGradosList().get(0).getSieniSeccionList());
//                    this.setSeccion(this.getSeccionesList().get(0));
//                    String anio = "2015";
//                    if (getDesde() != null) {
//                        anio = new FormatUtils().getFormatedAnio(getDesde());
//                    } else if (getHasta() != null) {
//                        anio = new FormatUtils().getFormatedAnio(getDesde());
//                    }
//                    this.setAlumnos(sieniAlumnoFacadeRemote.findAlumnosGradoSeccionAnio(this.getSeccion().getIdGrado().getIdGrado(), this.getSeccion().getIdSeccion(), anio));
//                } else {
//                    this.setAlumnos(new ArrayList<SieniAlumno>());
//                }
//            }
            FormatUtils fu = new FormatUtils();
            String anioActual = fu.getFormatedAnio(desde);
            this.setTrimestre1(fu.getFormatDate("30/04/" + anioActual));//abril
            this.setTrimestre2(fu.getFormatDate("31/08/" + anioActual));//agosto
            this.setTrimestre3(fu.getFormatDate("30/11/" + anioActual));//noviembre
            this.setListaDatos(new ArrayList<RptBoletaNotasPojo>());
            for (RptBoletaNotasPojo nota : agruparNotas(sieniNotasFacadeRemote.getBoletaNotasRpt(desde, hasta, this.getGrado().getIdGrado(), this.getSeccion().getIdSeccion(), this.getAlumno().getIdAlumno()))) {
                this.getListaDatos().add(nota);
            }
            this.setTotalTransacciones(this.getListaDatos().size());
        } else {
            new ValidationPojo().printMsj("Todos los campos deben ser ingresados", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void generarReporte() {

        String txtGrado = "", txtSeccion = "";
        if (this.getGrado() != null && this.getGrado().getIdGrado() != null && !this.getGrado().getIdGrado().equals(0L)) {
            for (SieniGrado actual : this.getGradosList()) {
                if (actual.getIdGrado().equals(this.getGrado().getIdGrado())) {
                    txtGrado = actual.getGrNombre();
                    break;
                }
            }
        } else {
            txtGrado = "Todos";
        }

        if (this.getSeccion() != null && this.getSeccion().getIdSeccion() != null && !this.getSeccion().getIdSeccion().equals(0L)) {
            txtSeccion = this.getSeccion().getScDescripcion();
        } else {
            txtSeccion = "Todos";
        }
        String path = "resources/reportes/boletaNotas.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("desde", new FormatUtils().getFormatedDate(this.getDesde()));
        parameterMap.put("hasta", new FormatUtils().getFormatedDate(this.getHasta()));
        parameterMap.put("grado", txtGrado);
        parameterMap.put("seccion", txtSeccion);
        SieniAlumno alumno = sieniAlumnoFacadeRemote.findAlumnoById(this.getAlumno().getIdAlumno());
        parameterMap.put("alumno", alumno != null ? alumno.getNombreCompleto() : "");
        parameterMap.put("carnet", alumno != null ? alumno.getAlCarnet() : "");
        parameterMap.put("promedioTotal", this.getTotal().setScale(2, RoundingMode.HALF_UP).toString());
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));

        try {
            RptBoletaNotasController.generateReport(path, "boletaNotas" + new Date().getTime(), this.getListaDatos(), parameterMap, this.getTipoRpt());
            registrarEnBitacora("Reporte", "Boleta de notas", 0L);
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
        this.setSeccion(new SieniSeccion());
        this.setSeccionesList(cod.getSieniSeccionList());
        if (!this.getSeccionesList().isEmpty()) {
            this.setSeccion(this.getSeccionesList().get(0));
            if (this.getSeccion() != null) {
//                String anio = "2015";
//                if (getDesde() != null) {
//                    anio = new FormatUtils().getFormatedAnio(getDesde());
//                } else if (getHasta() != null) {
//                    anio = new FormatUtils().getFormatedAnio(getDesde());
//                }
                this.setAlumnos(sieniAlumnoFacadeRemote.findAlumnosGradoSeccionAnio(this.getSeccion().getIdGrado().getIdGrado(), this.getSeccion().getIdSeccion()));
            }
        } else {
            this.setAlumnos(new ArrayList<SieniAlumno>());
        }
    }

    public void getAlumnosSecciones(ValueChangeEvent a) {
        Long idSeccion = (Long) a.getNewValue();
        SieniSeccion selec = null;
        for (SieniSeccion actual : this.getSeccionesList()) {
            if (actual.getIdSeccion().equals(idSeccion)) {
                selec = actual;
                this.setSeccion(selec);
                break;
            }
        }
        if (selec != null) {
//            String anio = "2015";
//            if (getDesde() != null) {
//                anio = new FormatUtils().getFormatedAnio(getDesde());
//            } else if (getHasta() != null) {
//                anio = new FormatUtils().getFormatedAnio(getDesde());
//            }
            this.setAlumnos(sieniAlumnoFacadeRemote.findAlumnosGradoSeccionAnio(selec.getIdGrado().getIdGrado(), selec.getIdSeccion()));
        }
    }

}
