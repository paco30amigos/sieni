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
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniCursoFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.form.RptCursosForm;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.pojos.rpt.RptCursosPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author ever
 */
@SessionScoped
@ManagedBean(name = "rptCursosController")
public class RptCursosController extends RptCursosForm {

    @EJB
    SieniCursoFacadeRemote sieniCursoFacadeRemote;

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;
    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;

    @PostConstruct
    public void init() {
        this.setTipoRpt(0);
        this.setListDatos(new ArrayList<RptCursosPojo>());
        this.setDesde(new Date());
        this.setHasta(new Date());
    }

    public void fill() {
        RptCursosPojo elem = new RptCursosPojo();

        List<SieniCurso> curso = sieniCursoFacadeRemote.findAll();
//        List<SieniCurso> docente2= sieniDocenteFacadeRemote.findDocentesDesdeHasta(this.getDesde(), this.getHasta());

        this.setListDatos(new ArrayList<RptCursosPojo>());
        for (SieniCurso actual : curso) {
//RptCursosPojo(SieniCurso cursoEntity, String nombre, String docente, String grado, String seccion, String Materia, String capacidad)
            SieniDocente d = sieniDocenteFacadeRemote.findByDocenteId(actual.getIdDocente());
            elem = new RptCursosPojo(actual, actual.getCrNombre(), d.getNombreCompleto(), actual.getIdGrado().getGrNombre(), actual.getIdSeccion().getScDescripcion(), actual.getIdMateria().getMaNombre(), actual.getCrCapacidad().toString());
//        RptAlumnosPojo(actual, grado, actual.getNombreCompleto(), actual.getFechaNacimientoFiltrable(), new DateUtils().getEdad(actual.getAlFechaNacimiento()), actual.getAlDireccion(), new FormatUtils().getFormatedPhone(actual.getAlTelefonoEm1()), grado.getGrNombre());
            this.getListDatos().add(elem);
        }

    }

    public void generarReporte() throws IOException {
        String path = "resources/reportes/rtpcursos.jasper";
        Map parameterMap = new HashMap();

        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        parameterMap.put("desde", new FormatUtils().getFormatedDate(this.getDesde()));
        parameterMap.put("hasta", new FormatUtils().getFormatedDate(this.getHasta()));

        try {
            RptCursosController.generateReport(path, "rtpCursos" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
            registrarEnBitacora("Reporte", "Cursos", 0L);
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
