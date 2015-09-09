/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniMatricula;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniSeccion;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class GestionMatriculaForm {

    private int indexMenu;
    private SieniMatricula eliminar;

    // consulta de matricula
    private List<SieniMatricula> matriculaList;
    //registro de matricula
    private SieniMatricula matriculaNuevo;
    private List<SieniAlumno> alumnosList;
    private List<SieniSeccion> seccionesList;
    private List<SieniGrado> gradosList;
    private Long idAlumno;
    private Long idSeccion;
    private Long idGrado;
    //modificacion de matricula
    private SieniMatricula matriculaModifica;
    private List<SieniAlumno> alumnosModificaList;
    private List<SieniSeccion> seccionesModificaList;
    private List<SieniGrado> gradosModificaList;
    private Long idAlumnoModifica;
    private Long idSeccionModifica;
    private Long idGradoModifica;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasegestionarAnioEscolar() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasegestionarAnioEscolar() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasegestionarAnioEscolar() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasegestionarAnioEscolar() + "ver.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public SieniMatricula getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniMatricula eliminar) {
        this.eliminar = eliminar;
    }

    public List<SieniMatricula> getMatriculaList() {
        return matriculaList;
    }

    public void setMatriculaList(List<SieniMatricula> matriculaList) {
        this.matriculaList = matriculaList;
    }

    public SieniMatricula getMatriculaNuevo() {
        return matriculaNuevo;
    }

    public void setMatriculaNuevo(SieniMatricula matriculaNuevo) {
        this.matriculaNuevo = matriculaNuevo;
    }

    public List<SieniAlumno> getAlumnosList() {
        return alumnosList;
    }

    public void setAlumnosList(List<SieniAlumno> alumnosList) {
        this.alumnosList = alumnosList;
    }

    public List<SieniSeccion> getSeccionesList() {
        return seccionesList;
    }

    public void setSeccionesList(List<SieniSeccion> seccionesList) {
        this.seccionesList = seccionesList;
    }

    public List<SieniGrado> getGradosList() {
        return gradosList;
    }

    public void setGradosList(List<SieniGrado> gradosList) {
        this.gradosList = gradosList;
    }

    public SieniMatricula getMatriculaModifica() {
        return matriculaModifica;
    }

    public void setMatriculaModifica(SieniMatricula matriculaModifica) {
        this.matriculaModifica = matriculaModifica;
    }

    public List<SieniAlumno> getAlumnosModificaList() {
        return alumnosModificaList;
    }

    public void setAlumnosModificaList(List<SieniAlumno> alumnosModificaList) {
        this.alumnosModificaList = alumnosModificaList;
    }

    public List<SieniSeccion> getSeccionesModificaList() {
        return seccionesModificaList;
    }

    public void setSeccionesModificaList(List<SieniSeccion> seccionesModificaList) {
        this.seccionesModificaList = seccionesModificaList;
    }

    public List<SieniGrado> getGradosModificaList() {
        return gradosModificaList;
    }

    public void setGradosModificaList(List<SieniGrado> gradosModificaList) {
        this.gradosModificaList = gradosModificaList;
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Long getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Long idSeccion) {
        this.idSeccion = idSeccion;
    }

    public Long getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(Long idGrado) {
        this.idGrado = idGrado;
    }

    public Long getIdAlumnoModifica() {
        return idAlumnoModifica;
    }

    public void setIdAlumnoModifica(Long idAlumnoModifica) {
        this.idAlumnoModifica = idAlumnoModifica;
    }

    public Long getIdSeccionModifica() {
        return idSeccionModifica;
    }

    public void setIdSeccionModifica(Long idSeccionModifica) {
        this.idSeccionModifica = idSeccionModifica;
    }

    public Long getIdGradoModifica() {
        return idGradoModifica;
    }

    public void setIdGradoModifica(Long idGradoModifica) {
        this.idGradoModifica = idGradoModifica;
    }

}
