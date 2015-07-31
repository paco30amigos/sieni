/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author bugtraq
 */
@ManagedBean
public class siteUrls {

    private final String gestionarAnioEscolar = "/faces/view/gestionAnioEscolar/index.xhtml";
    private final String gestionarAlumnos = "/faces/view/gestionarAlumnos/index.xhtml";
    private final String gestionarDocentes = "/faces/view/gestionarDocentes/index.xhtml";
    private final String gestionarCursos = "/faces/view/gestionCurso/index.xhtml";
    private final String programacionClases = "/faces/view/gestionCurso/gestionClase/programacionClase/index.xhtml";
    private final String gestionArchivosMultimedia = "/faces/view/gestionCurso/gestionArchivosMultimedia/index.xhtml";
    private final String gestionConsulta = "/faces/view/portalConsultas/gestionConsultas/index.xhtml";
    private final String gestionEvaluacion = "/faces/view/gestionCurso/gestionEvaluaciones/index.xhtml";
    private final String gestionNoticia = "/faces/view/portalNoticias/gestionNoticias/index.xhtml";
    private final String gestionMateria = "/faces/view/gestionCurso/gestionMaterias/index.xhtml";
    private final String gestionNota = "/faces/view/gestionCurso/gestionNotas/index.xhtml";
    private final String gestionUsuarios = "/faces/view/administracionSistema/gestionUsuarios/index.xhtml";
    private final String bitacora = "/faces/view/administracionSistema/gestionUsuarios/bitacora.xhtml";
    private final String gestionAlumnos = "/faces/view/gestionAnioEscolar/matricula/index.xhtml";
    private final String expedienteAlumnos = "/faces/view/gestionAlumnos/expediente/index.xhtml";
    private final String gestionDocentes = "/faces/view/gestionDocentes/mantenimientoDocentes/index.xhtml";
    private final String buzonNotificacion = "/faces/view/buzonNotificaciones/index.xhtml";
    private final String claseOnline = "/faces/view/gestionCurso/gestionClase/claseOnline/index.xhtml";
    private final String claseVideoAlmacenada = "/faces/view/gestionCurso/gestionClase/claseVideoAlmacenada/index.xhtml";
    private final String claseInteractiva = "/faces/view/gestionCurso/gestionClase/claseInteractiva/index.xhtml";
    private final String componenteInteractiva = "/faces/view/gestionCurso/gestionCompInteract/index.xhtml";

    private final String reporteMatricula = "/faces/view/rpt/reporteMatricula/index.xhtml";
    private final String reporteEstadisticoAvanceAlumno = "/faces/view/rpt/reporteEstadisticoAvanceAlumnos/index.xhtml";
    private final String reporteNotasAlumnoAnioEscolar = "/faces/view/rpt/reporteNotasAlumnoAnioEscolar/index.xhtml";
    private final String reporteEvaluaciones = "/faces/view/rpt/reporteEvaluaciones/index.xhtml";
    private final String reporteClases = "/faces/view/rpt/reporteClases/index.xhtml";
    private final String reporteDocentes = "/faces/view/rpt/reporteDocentes/index.xhtml";
    private final String reporteUsuarios = "/faces/view/rpt/reporteUsuarios/index.xhtml";
    private final String reporteParticipacionClases = "/faces/view/rpt/reporteParticipacionClases/index.xhtml";
    private final String reporteCursos = "/faces/view/rpt/reporteCursos/index.xhtml";
    private final String reporteAlumnos = "/faces/view/rpt/reporteAlumnos/index.xhtml";
    
    
    private final String ejerciciosResueltos="/faces/view/gestionCurso/gestionClase/ejerciciosResueltos/index.xhtml";

    
    private final String ejerciciosRes = "/faces/view/gestionCurso/gestionClase/ejerciciosRes/index.xhtml";
    private final String reporteRendimientoAlumno = "/faces/view/reportes/rendimientoAlumno/index.xhtml";

    
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

}
