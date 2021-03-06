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
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniMatriculaFacadeRemote;
import sv.com.mined.sieni.form.RptMatriculaForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniMatricula;
import sv.com.mined.sieni.pojos.rpt.RptMatriculasPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "rptMatriculaController")
public class RptMatriculaController extends RptMatriculaForm {

    @EJB
    private SieniMatriculaFacadeRemote sieniMatriculaFacadeRemote;

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;
    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;

    @PostConstruct
    public void init() {
        this.setTipoRpt(0);
        this.setAnio("2015");
        this.setDesde(new Date());
        this.setHasta(new Date());
        //fill();
    }

    public void fill() {
//        this.setMatriculaList(sieniMatriculaFacadeRemote.findAll());
        RptMatriculasPojo elem = new RptMatriculasPojo();

//        List<SieniMatricula> matriculas = sieniMatriculaFacadeRemote.findAllNoInactivos();
        List<SieniMatricula> matriculas = sieniMatriculaFacadeRemote.findAllNoInactivosRpt(this.getDesde(), this.getHasta());
        this.setListDatos(new ArrayList<RptMatriculasPojo>());
        for (SieniMatricula actual : matriculas) {
            SieniAlumno alumno = sieniAlumnoFacadeRemote.findAlumnoById(actual.getIdAlumno());
            elem = new RptMatriculasPojo(actual.getIdMatricula().toString(), actual.getMtAnio(), alumno.getAlCarnet(), alumno.getNombreCompleto(), actual.getIdGrado().getGrNombre(), actual.getIdSeccion().getScDescripcion());
            this.getListDatos().add(elem);
        }
        this.setTotalTransacciones(Long.parseLong(this.getListDatos().size() + ""));
    }

    public void generarReporte() {
//        String anio = this.getAnio().replaceAll(",", "");
//        Integer anioInt = Integer.parseInt(anio);
//        this.setMatriculaList(sieniMatriculaFacadeRemote.getMatriculasAnio(anioInt));
//        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Generar Reporte", "Reporte de Matricula", 1L, new Character('D')));
        //fill();
        String path = "resources/reportes/rtpMatriculas.jasper";
        Map parameterMap = new HashMap();
//        parameterMap.put("anio", this.getAnio());
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        parameterMap.put("desde", new FormatUtils().getFormatedDate(this.getDesde()));
        parameterMap.put("hasta", new FormatUtils().getFormatedDate(this.getHasta()));
        try {
            RptMatriculaController.generateReport(path, "rtpMatriculas" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
            registrarEnBitacora("Reporte", "Matricula", 0L);
        } catch (JRException ex) {
            Logger.getLogger("error 1").log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger("error 2").log(Level.SEVERE, null, ex);
        }
    }

    public void refresh() {
        String anio = this.getAnio().replaceAll(",", "");
        Integer anioInt = Integer.parseInt(anio);
        this.setMatriculaList(sieniMatriculaFacadeRemote.getMatriculasAnio(anioInt));
    }

}
