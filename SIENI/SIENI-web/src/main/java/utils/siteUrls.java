/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author bugtraq
 */
@ManagedBean
public class siteUrls {

    private final String gestionarAnioEscolar = "/faces/view/gestionAnioEscolar/MntoAnioEscolar/index.xhtml";
    private final String gestionarAlumnos = "/faces/view/gestionarAlumnos/index.xhtml";
    private final String gestionarDocentes = "/faces/view/gestionarDocentes/index.xhtml";
    private final String gestionarCursos = "/faces/view/gestionAnioEscolar/gestionCurso/index.xhtml";
    private final String programacionClases = "/faces/view/gestionAnioEscolar/gestionClase/programacionClase/index.xhtml";
    private final String gestionArchivosMultimedia = "/faces/view/administracionSistema/gestionArchivosMultimedia/index.xhtml";
    private final String gestionConsulta = "/faces/view/portalConsultas/gestionConsultas/index.xhtml";
    private final String gestionEvaluacion = "/faces/view/gestionAnioEscolar/gestionEvaluaciones/index.xhtml";
    private final String gestionNoticia = "/faces/view/portalNoticias/gestionNoticias/index.xhtml";
    private final String gestionMateria = "/faces/view/gestionAnioEscolar/gestionMaterias/index.xhtml";
    private final String gestionNota = "/faces/view/gestionAnioEscolar/gestionNotas/index.xhtml";
    private final String gestionUsuarios = "/faces/view/administracionSistema/gestionUsuarios/index.xhtml";
    private final String bitacora = "/faces/view/administracionSistema/bitacora/bitacora.xhtml";
    private final String gestionAlumnos = "/faces/view/gestionAnioEscolar/matricula/index.xhtml";
    private final String expedienteAlumnos = "/faces/view/gestionAlumnos/expediente/index.xhtml";
    private final String gestionDocentes = "/faces/view/gestionDocentes/mantenimientoDocentes/index.xhtml";
    private final String gestionAnioEscolar = "/faces/view/gestionAnioEscolar/MntoAnioEscolar/";
    private final String buzonNotificacion = "/faces/view/buzonNotificaciones/index.xhtml";
    private final String claseOnline = "/faces/view/gestionAnioEscolar/gestionClase/claseOnline/index.xhtml";
    private final String claseVideoAlmacenada = "/faces/view/gestionAnioEscolar/gestionClase/claseVideoAlmacenada/index.xhtml";
    private final String claseInteractiva = "/faces/view/gestionAnioEscolar/gestionClase/claseInteractiva/index.xhtml";
    private final String componenteInteractiva = "/faces/view/administracionSistema/gestionCompInteract/index.xhtml";
    private final String gestionPlantilla = "/faces/view/administracionSistema/gestionPlantillas/index.xhtml";
    private final String catTipoElemPlantilla = "/faces/view/administracionSistema/catalogos/tipoElemPlantilla/index.xhtml";
    private final String catSeccion = "/faces/view/administracionSistema/catalogos/seccion/index.xhtml";
    private final String catGrado = "/faces/view/administracionSistema/catalogos/grados/index.xhtml";
    private final String catMateria = "/faces/view/administracionSistema/catalogos/materias/index.xhtml";

    private final String reporteMatricula = "/faces/view/rpt/reporteMatricula/index.xhtml";
    private final String reporteEstadisticoAvanceAlumno = "/faces/view/rpt/reporteEstadisticoAvanceAlumnos/index.xhtml";
    private final String reporteNotasAlumnoAnioEscolar = "/faces/view/rpt/reporteNotasAlumnoAnioEscolar/index.xhtml";
    private final String boletaNota = "/faces/view/rpt/boletaNota/index.xhtml";
    private final String reporteNotasModificadas = "/faces/view/administracionSistema/reporteNotasModificadas/index.xhtml";
    private final String reporteEvaluaciones = "/faces/view/rpt/reporteEvaluaciones/index.xhtml";
    private final String reporteClases = "/faces/view/rpt/reporteClases/index.xhtml";
    private final String reporteDocentes = "/faces/view/rpt/reporteDocentes/index.xhtml";
    private final String reporteUsuarios = "/faces/view/rpt/reporteUsuarios/index.xhtml";
    private final String reporteParticipacionClases = "/faces/view/rpt/reporteParticipacionClases/index.xhtml";
    private final String reporteCursos = "/faces/view/rpt/reporteCursos/index.xhtml";
    private final String reporteAlumnos = "/faces/view/rpt/reporteAlumnos/index.xhtml";

    private final String ejerciciosResueltos = "/faces/view/gestionAnioEscolar/gestionClase/ejerciciosResueltos/index.xhtml";

    private final String ejerciciosRes = "/faces/view/gestionAnioEscolar/gestionClase/ejerciciosRes/index.xhtml";
    private final String reporteRendimientoAlumno = "/faces/view/rpt/rendimientoAlumno/index.xhtml";
    private final String resetPass = "/faces/resetPass.xhtml";
    private final String login = "/faces/login.xhtml";

    ////
    private final String basegestionarAnioEscolar = "/faces/view/gestionAnioEscolar/MntoAnioEscolar/";
    private final String basegestionarAlumnos = "/faces/view/gestionarAlumnos/";
    private final String basegestionarDocentes = "/faces/view/gestionarDocentes/";
    private final String basegestionarCursos = "/faces/view/gestionAnioEscolar/gestionCurso/";
    private final String baseprogramacionClases = "/faces/view/gestionAnioEscolar/gestionClase/programacionClase/";
    private final String basegestionArchivosMultimedia = "/faces/view/administracionSistema/gestionArchivosMultimedia/";
    private final String basegestionConsulta = "/faces/view/portalConsultas/gestionConsultas/";
    private final String basegestionEvaluacion = "/faces/view/gestionAnioEscolar/gestionEvaluaciones/";
    private final String basegestionNoticia = "/faces/view/portalNoticias/gestionNoticias/";
    private final String basegestionMateria = "/faces/view/gestionAnioEscolar/gestionMaterias/";
    private final String basegestionNota = "/faces/view/gestionAnioEscolar/gestionNotas/";
    private final String basegestionUsuarios = "/faces/view/administracionSistema/gestionUsuarios/";
    private final String basebitacora = "/faces/view/administracionSistema/bitacora/bitacora.xhtml";
    private final String basegestionAlumnos = "/faces/view/gestionAnioEscolar/matricula/";
    private final String baseexpedienteAlumnos = "/faces/view/gestionAlumnos/expediente/";
    private final String basegestionDocentes = "/faces/view/gestionDocentes/mantenimientoDocentes/";
    private final String basebuzonNotificacion = "/faces/view/buzonNotificaciones/";
    private final String baseclaseOnline = "/faces/view/gestionAnioEscolar/gestionClase/claseOnline/";
    private final String baseclaseVideoAlmacenada = "/faces/view/gestionAnioEscolar/gestionClase/claseVideoAlmacenada/";
    private final String baseclaseInteractiva = "/faces/view/gestionAnioEscolar/gestionClase/claseInteractiva/";
    private final String basecomponenteInteractiva = "/faces/view/administracionSistema/gestionCompInteract/";
    private final String basegestionPlantilla = "/faces/view/administracionSistema/gestionPlantillas/";

    private final String basereporteMatricula = "/faces/view/rpt/reporteMatricula/";
    private final String basereporteEstadisticoAvanceAlumno = "/faces/view/rpt/reporteEstadisticoAvanceAlumnos/";
    private final String basereporteNotasAlumnoAnioEscolar = "/faces/view/rpt/reporteNotasAlumnoAnioEscolar/";
    private final String baseboletaNota = "/faces/view/rpt/boletaNota/";
    private final String basereporteEvaluaciones = "/faces/view/rpt/reporteEvaluaciones/";
    private final String basereporteClases = "/faces/view/rpt/reporteClases/";
    private final String basereporteDocentes = "/faces/view/rpt/reporteDocentes/";
    private final String basereporteUsuarios = "/faces/view/rpt/reporteUsuarios/";
    private final String basereporteParticipacionClases = "/faces/view/rpt/reporteParticipacionClases/";
    private final String basereporteCursos = "/faces/view/rpt/reporteCursos/";
    private final String basereporteAlumnos = "/faces/view/rpt/reporteAlumnos/";

    private final String baseejerciciosResueltos = "/faces/view/gestionAnioEscolar/gestionClase/ejerciciosResueltos/";

    private final String baseejerciciosRes = "/faces/view/gestionAnioEscolar/gestionClase/ejerciciosRes/";
    private final String basereporteRendimientoAlumno = "/faces/view/rpt/rendimientoAlumno/";
    private final String basecatTipoElemPlantilla = "/faces/view/administracionSistema/catalogos/tipoElemPlantilla/";
    private final String basecatSeccion = "/faces/view/administracionSistema/catalogos/seccion/";
    private final String basecatGrado = "/faces/view/administracionSistema/catalogos/grados/";
    private final String basecatMateria = "/faces/view/administracionSistema/catalogos/materias/";

    public void redirect(String url) {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String contextPath = origRequest.getContextPath();
        try {
            Flash flash = context.getExternalContext().getFlash();
            flash.doPostPhaseActions(context);
            flash.setKeepMessages(true);
            flash.setRedirect(true);
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(contextPath + url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    
    public void resetearFitrosTabla(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }
    
    public String getEjerciciosResueltos() {
        return ejerciciosResueltos;
    }

    public String getGestionarAnioEscolar() {
        return gestionarAnioEscolar;
    }

    public String getGestionarAlumnos() {
        return gestionarAlumnos;
    }

    public String getGestionarDocentes() {
        return gestionarDocentes;
    }

    public String getGestionarCursos() {
        return gestionarCursos;
    }

    public String getProgramacionClases() {
        return programacionClases;
    }

    public String getGestionArchivosMultimedia() {
        return gestionArchivosMultimedia;
    }

    public String getGestionConsulta() {
        return gestionConsulta;
    }

    public String getGestionEvaluacion() {
        return gestionEvaluacion;
    }

    public String getGestionNoticia() {
        return gestionNoticia;
    }

    public String getGestionMateria() {
        return gestionMateria;
    }

    public String getGestionNota() {
        return gestionNota;
    }

    public String getGestionUsuarios() {
        return gestionUsuarios;
    }

    public String getBitacora() {
        return bitacora;
    }

    public String getGestionAlumnos() {
        return gestionAlumnos;
    }

    public String getGestionDocentes() {
        return gestionDocentes;
    }

    public String getExpedienteAlumnos() {
        return expedienteAlumnos;
    }

    public String getBuzonNotificacion() {
        return buzonNotificacion;
    }

    public String getReporteRendimientoAlumno() {
        return reporteRendimientoAlumno;
    }

    public String getReporteMatricula() {
        return reporteMatricula;
    }

    public String getReporteEstadisticoAvanceAlumno() {
        return reporteEstadisticoAvanceAlumno;
    }

    public String getReporteNotasAlumnoAnioEscolar() {
        return reporteNotasAlumnoAnioEscolar;
    }

    public String getReporteEvaluaciones() {
        return reporteEvaluaciones;
    }

    public String getEjerciciosRes() {
        return ejerciciosRes;
    }

    public String getReporteClases() {
        return reporteClases;
    }

    public String getReporteDocentes() {
        return reporteDocentes;
    }

    public String getReporteUsuarios() {
        return reporteUsuarios;
    }

    public String getReporteParticipacionClases() {
        return reporteParticipacionClases;
    }

    public String getReporteCursos() {
        return reporteCursos;
    }

    public String getReporteAlumnos() {
        return reporteAlumnos;
    }

    public String getClaseOnline() {
        return claseOnline;
    }

    public String getClaseVideoAlmacenada() {
        return claseVideoAlmacenada;
    }

    public String getClaseInteractiva() {
        return claseInteractiva;
    }

    public String getComponenteInteractiva() {
        return componenteInteractiva;
    }

    public String getBasegestionarAnioEscolar() {
        return basegestionarAnioEscolar;
    }

    public String getBasegestionarAlumnos() {
        return basegestionarAlumnos;
    }

    public String getBasegestionarDocentes() {
        return basegestionarDocentes;
    }

    public String getBasegestionarCursos() {
        return basegestionarCursos;
    }

    public String getBaseprogramacionClases() {
        return baseprogramacionClases;
    }

    public String getBasegestionArchivosMultimedia() {
        return basegestionArchivosMultimedia;
    }

    public String getBasegestionConsulta() {
        return basegestionConsulta;
    }

    public String getBasegestionEvaluacion() {
        return basegestionEvaluacion;
    }

    public String getBasegestionNoticia() {
        return basegestionNoticia;
    }

    public String getBasegestionMateria() {
        return basegestionMateria;
    }

    public String getBasegestionNota() {
        return basegestionNota;
    }

    public String getBasegestionUsuarios() {
        return basegestionUsuarios;
    }

    public String getBasebitacora() {
        return basebitacora;
    }

    public String getBasegestionAlumnos() {
        return basegestionAlumnos;
    }

    public String getBaseexpedienteAlumnos() {
        return baseexpedienteAlumnos;
    }

    public String getBasegestionDocentes() {
        return basegestionDocentes;
    }

    public String getBasebuzonNotificacion() {
        return basebuzonNotificacion;
    }

    public String getBaseclaseOnline() {
        return baseclaseOnline;
    }

    public String getBaseclaseVideoAlmacenada() {
        return baseclaseVideoAlmacenada;
    }

    public String getBaseclaseInteractiva() {
        return baseclaseInteractiva;
    }

    public String getBasecomponenteInteractiva() {
        return basecomponenteInteractiva;
    }

    public String getBasereporteMatricula() {
        return basereporteMatricula;
    }

    public String getBasereporteEstadisticoAvanceAlumno() {
        return basereporteEstadisticoAvanceAlumno;
    }

    public String getBasereporteNotasAlumnoAnioEscolar() {
        return basereporteNotasAlumnoAnioEscolar;
    }

    public String getBasereporteEvaluaciones() {
        return basereporteEvaluaciones;
    }

    public String getBasereporteClases() {
        return basereporteClases;
    }

    public String getBasereporteDocentes() {
        return basereporteDocentes;
    }

    public String getBasereporteUsuarios() {
        return basereporteUsuarios;
    }

    public String getBasereporteParticipacionClases() {
        return basereporteParticipacionClases;
    }

    public String getBasereporteCursos() {
        return basereporteCursos;
    }

    public String getBasereporteAlumnos() {
        return basereporteAlumnos;
    }

    public String getBaseejerciciosResueltos() {
        return baseejerciciosResueltos;
    }

    public String getBaseejerciciosRes() {
        return baseejerciciosRes;
    }

    public String getBasereporteRendimientoAlumno() {
        return basereporteRendimientoAlumno;
    }

    public String getGestionAnioEscolar() {
        return gestionAnioEscolar;
    }

    public String getGestionPlantilla() {
        return gestionPlantilla;
    }

    public String getBasegestionPlantilla() {
        return basegestionPlantilla;
    }

    public String getCatTipoElemPlantilla() {
        return catTipoElemPlantilla;
    }

    public String getCatSeccion() {
        return catSeccion;
    }

    public String getCatGrado() {
        return catGrado;
    }

    public String getCatMateria() {
        return catMateria;
    }

    public String getBasecatTipoElemPlantilla() {
        return basecatTipoElemPlantilla;
    }

    public String getBasecatSeccion() {
        return basecatSeccion;
    }

    public String getBasecatGrado() {
        return basecatGrado;
    }

    public String getBasecatMateria() {
        return basecatMateria;
    }

    public String getBoletaNota() {
        return boletaNota;
    }

    public String getBaseboletaNota() {
        return baseboletaNota;
    }

    public void getResetPass() {
        this.redirect(resetPass);
    }

    public void getReturnLogin() {
        this.redirect(login);
    }

    public String getReporteNotasModificadas() {
        return reporteNotasModificadas;
    }
    
    
    
    
    

}
