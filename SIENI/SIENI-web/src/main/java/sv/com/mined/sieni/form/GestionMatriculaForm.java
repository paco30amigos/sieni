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
    //modificacion de matricula
    private SieniMatricula matriculaModifica;
    private List<SieniAlumno> alumnosModificaList;
    private List<SieniSeccion> seccionesModificaList;
    private List<SieniGrado> gradosModificaList;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
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

}
