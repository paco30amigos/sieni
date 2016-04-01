/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniCatMateria;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniMateria;
import utils.siteUrls;

/**
 *
 * @author Alejandro
 */
public class GestionMateriasForm {

    private int indexMenu;
    private SieniMateria eliminar;

    // consulta de materias
    private List<SieniMateria> materiaList;
    private List<SieniMateria> listDatosFiltered;
    //registro de materias
    private SieniMateria materiaNuevo;
    private List<SieniAlumno> alumnosList;
    private List<SieniDocente> docentesList;
    private List<SieniGrado> gradoList;
    private List<SieniCatMateria> catmateriaList;
    private Long idCatmateria;
    //modificacion de materia
    private SieniMateria materiaModifica;
    private List<SieniMateria> materiaModificaList;
    private Long idAlumnoModifica;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasegestionMateria() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasegestionMateria() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasegestionMateria() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasegestionMateria() + "ver.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public SieniMateria getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniMateria eliminar) {
        this.eliminar = eliminar;
    }

    public List<SieniMateria> getMateriaList() {
        return materiaList;
    }

    public void setMateriaList(List<SieniMateria> materiaList) {
        this.materiaList = materiaList;
    }

    public SieniMateria getMateriaNuevo() {
        return materiaNuevo;
    }

    public void setMateriaNuevo(SieniMateria materiaNuevo) {
        this.materiaNuevo = materiaNuevo;
    }

    public List<SieniAlumno> getAlumnosList() {
        return alumnosList;
    }

    public void setAlumnosList(List<SieniAlumno> alumnosList) {
        this.alumnosList = alumnosList;
    }

    public SieniMateria getMateriaModifica() {
        return materiaModifica;
    }

    public void setMateriaModifica(SieniMateria materiaModifica) {
        this.materiaModifica = materiaModifica;
    }

    public List<SieniMateria> getMateriaModificaList() {
        return materiaModificaList;
    }

    public void setMateriaModificaList(List<SieniMateria> materiaModificaList) {
        this.materiaModificaList = materiaModificaList;
    }

    public Long getIdAlumnoModifica() {
        return idAlumnoModifica;
    }

    public void setIdAlumnoModifica(Long idAlumnoModifica) {
        this.idAlumnoModifica = idAlumnoModifica;
    }

    public List<SieniDocente> getDocentesList() {
        return docentesList;
    }

    public void setDocentesList(List<SieniDocente> docentesList) {
        this.docentesList = docentesList;
    }

    public List<SieniGrado> getGradoList() {
        return gradoList;
    }

    public void setGradoList(List<SieniGrado> gradoList) {
        this.gradoList = gradoList;
    }

    public List<SieniCatMateria> getCatmateriaList() {
        return catmateriaList;
    }

    public void setCatmateriaList(List<SieniCatMateria> catmateriaList) {
        this.catmateriaList = catmateriaList;
    }

    public Long getIdCatmateria() {
        return idCatmateria;
    }

    public void setIdCatmateria(Long idCatmateria) {
        this.idCatmateria = idCatmateria;
    }

    public List<SieniMateria> getListDatosFiltered() {
        return listDatosFiltered;
    }

    public void setListDatosFiltered(List<SieniMateria> listDatosFiltered) {
        this.listDatosFiltered = listDatosFiltered;
    }

    public String getCoordinadorV(Long idDocente) {
        String ret = "";
        if (this.getDocentesList() != null && !this.getDocentesList().isEmpty()) {
            for (SieniDocente actual : this.getDocentesList()) {
                if (idDocente.equals(actual.getIdDocente())) {
                    ret = actual.getNombreCompleto();
                    break;
                }
            }
        }
        return ret;
    }

}
