/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniSeccion;

/**
 *
 * @author francisco_medina
 */
public class GestionCursoForm {

    private int indexMenu;
    private SieniCurso eliminar;

    // consulta de curso
    private List<SieniCurso> cursoList;
    //registro de curso
    private SieniCurso cursoNuevo;
    private List<SieniDocente> docentesList;
    private List<SieniGrado> gradoList;
    private List<SieniSeccion> seccionList;
    private List<SieniMateria> materiaList;
    private Long idDocente;
    private Long idGrado;
    private Long idSeccion;
    private Long idMateria;
    //modificacion de curso
    private SieniCurso cursoModifica;
    private List<SieniDocente> docentesModificaList;
    private List<SieniGrado> gradoModificaList;
    private List<SieniSeccion> seccionModificaList;
    private List<SieniMateria> materiaModificaList;
    private Long idDocenteModifica;
    private Long idGradoModifica;
    private Long idSeccionModifica;
    private Long idMateriaModifica;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        this.indexMenu = indexMenu;
    }

    public SieniCurso getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniCurso eliminar) {
        this.eliminar = eliminar;
    }

    public List<SieniCurso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<SieniCurso> cursoList) {
        this.cursoList = cursoList;
    }

    public SieniCurso getCursoNuevo() {
        return cursoNuevo;
    }

    public void setCursoNuevo(SieniCurso cursoNuevo) {
        this.cursoNuevo = cursoNuevo;
    }

    public SieniCurso getCursoModifica() {
        return cursoModifica;
    }

    public void setCursoModifica(SieniCurso cursoModifica) {
        this.cursoModifica = cursoModifica;
    }

    public List<SieniDocente> getDocentesList() {
        return docentesList;
    }

    public void setDocentesList(List<SieniDocente> docentesList) {
        this.docentesList = docentesList;
    }

    public List<SieniSeccion> getSeccionList() {
        return seccionList;
    }

    public void setSeccionList(List<SieniSeccion> seccionList) {
        this.seccionList = seccionList;
    }

    public List<SieniMateria> getMateriaList() {
        return materiaList;
    }

    public void setMateriaList(List<SieniMateria> materiaList) {
        this.materiaList = materiaList;
    }

    public List<SieniDocente> getDocentesModificaList() {
        return docentesModificaList;
    }

    public void setDocentesModificaList(List<SieniDocente> docentesModificaList) {
        this.docentesModificaList = docentesModificaList;
    }

    public List<SieniSeccion> getSeccionModificaList() {
        return seccionModificaList;
    }

    public void setSeccionModificaList(List<SieniSeccion> seccionModificaList) {
        this.seccionModificaList = seccionModificaList;
    }

    public List<SieniMateria> getMateriaModificaList() {
        return materiaModificaList;
    }

    public void setMateriaModificaList(List<SieniMateria> materiaModificaList) {
        this.materiaModificaList = materiaModificaList;
    }

    public List<SieniGrado> getGradoList() {
        return gradoList;
    }

    public void setGradoList(List<SieniGrado> gradoList) {
        this.gradoList = gradoList;
    }

    public List<SieniGrado> getGradoModificaList() {
        return gradoModificaList;
    }

    public void setGradoModificaList(List<SieniGrado> gradoModificaList) {
        this.gradoModificaList = gradoModificaList;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public Long getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(Long idGrado) {
        this.idGrado = idGrado;
    }

    public Long getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Long idSeccion) {
        this.idSeccion = idSeccion;
    }

    public Long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public Long getIdDocenteModifica() {
        return idDocenteModifica;
    }

    public void setIdDocenteModifica(Long idDocenteModifica) {
        this.idDocenteModifica = idDocenteModifica;
    }

    public Long getIdGradoModifica() {
        return idGradoModifica;
    }

    public void setIdGradoModifica(Long idGradoModifica) {
        this.idGradoModifica = idGradoModifica;
    }

    public Long getIdSeccionModifica() {
        return idSeccionModifica;
    }

    public void setIdSeccionModifica(Long idSeccionModifica) {
        this.idSeccionModifica = idSeccionModifica;
    }

    public Long getIdMateriaModifica() {
        return idMateriaModifica;
    }

    public void setIdMateriaModifica(Long idMateriaModifica) {
        this.idMateriaModifica = idMateriaModifica;
    }
}
