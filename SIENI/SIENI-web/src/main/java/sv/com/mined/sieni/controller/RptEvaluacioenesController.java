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
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniEvaluacionFacadeRemote;
import sv.com.mined.sieni.form.RptDocentesForm;
import sv.com.mined.sieni.form.RptEvaluacionesForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniEvaluacion;
import sv.com.mined.sieni.pojos.rpt.RptDocentesPojo;
import sv.com.mined.sieni.pojos.rpt.RptEvaluacionesPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author ever
 */
@SessionScoped
@ManagedBean(name = "rptEvaluacioenesController")
public class RptEvaluacioenesController extends RptEvaluacionesForm {

    @EJB
    SieniEvaluacionFacadeRemote sieniEvaluacionFacadeRemote;

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;
    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;

    @PostConstruct
    public void init() {

        this.setTipoRpt(0);
        this.setListDatos(new ArrayList<RptEvaluacionesPojo>());
//        fill();
    }

    public void fill() {
        RptEvaluacionesPojo elem = new RptEvaluacionesPojo();

//        List<SieniDocente> docente= sieniDocenteFacadeRemote.findAll();
        List<SieniEvaluacion> evaluacion = sieniEvaluacionFacadeRemote.findEvaluacionDesdeHasta(this.getDesde(), this.getHasta());

        this.setListDatos(new ArrayList<RptEvaluacionesPojo>());
        for (SieniEvaluacion actual : evaluacion) {
//            SieniGrado grado=sieniGradoFacadeRemote.getGradoActualAlumno(actual.getIdAlumno(),new FormatUtils().getFormatedAnio(new Date()));
//           public RptDocentesPojo(SieniDocente docenteEntity, String docente, String fechaNacimiento, String edad, String direccion, String telefono, String gradoResponsable) {
            SieniDocente d = sieniDocenteFacadeRemote.findByDocenteId(actual.getIdCurso().getIdDocente());
            elem = new RptEvaluacionesPojo(actual, d.getNombreCompleto(), actual.getIdCurso().getCrNombre(), actual.getEvNombre(), actual.getEvPonderacion().toString(), new DateUtils().getFormatoFecha(actual.getEvFechaInicio()), new DateUtils().getFormatoFecha(actual.getEvFechaCierre()));
//        RptAlumnosPojo(actual, grado, actual.getNombreCompleto(), actual.getFechaNacimientoFiltrable(), new DateUtils().getEdad(actual.getAlFechaNacimiento()), actual.getAlDireccion(), new FormatUtils().getFormatedPhone(actual.getAlTelefonoEm1()), grado.getGrNombre());
            this.getListDatos().add(elem);
        }

    }

    public void generarReporte() {
//        fill();
//        List<SieniDocente> docente= sieniDocenteFacadeRemote.findAll();
//         List<SieniDocente> docente2= sieniDocenteFacadeRemote.findDocentesDesdeHasta(this.getDesde(), this.getHasta());
        String path = "resources/reportes/rtpEvaluaciones.jasper";
        Map parameterMap = new HashMap();

//        parameterMap.put("grado", this.getGrado() != null ? this.getGrado() : "Todos");
//        parameterMap.put("seccion", this.getSeccion() != null ? this.getSeccion() : "Todos");
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        parameterMap.put("desde", new FormatUtils().getFormatedDate(this.getDesde()));
        parameterMap.put("hasta", new FormatUtils().getFormatedDate(this.getHasta()));

        try {
            RptEvaluacioenesController.generateReport(path, "rtpEvaluaciones" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
            registrarEnBitacora("Reporte", "Evaluaciones", 0L);
        } catch (JRException ex) {
            Logger.getLogger("error 1").log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger("error 2").log(Level.SEVERE, null, ex);
        }
    }

    public void refresh() {
        fill();
    }

//    public void getSeccionesGrado(ValueChangeEvent a) {
//        Long idGrado = (Long) a.getNewValue();
//        SieniGrado cod = new SieniGrado();
//        for (SieniGrado actual : this.getGradosList()) {
//            if (actual.getIdGrado().equals(idGrado)) {
//                cod = actual;
//                break;
//            }
//        }
//        this.setIdSeccion(0L);
//        this.setSeccionesList(cod.getSieniSeccionList());
//    }
//    
}
