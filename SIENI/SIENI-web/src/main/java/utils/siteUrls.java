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

}