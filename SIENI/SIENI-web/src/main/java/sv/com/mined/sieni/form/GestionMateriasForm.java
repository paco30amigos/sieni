/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.model.SieniAlumno;
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
    //registro de materias
    private SieniMateria materiaNuevo;
    private List<SieniAlumno> alumnosList;
     //modificacion de materia
    private SieniMateria materiaModifica;
    private List<SieniAlumno> alumnosModificaList;
    private Long idAlumnoModifica;
    
    public int getIndexMenu() {
        return indexMenu;
    }
    
    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasegestionAlumnos() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasegestionAlumnos() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasegestionAlumnos() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasegestionAlumnos() + "ver.xhtml");
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

    public List<SieniAlumno> getAlumnosModificaList() {
        return alumnosModificaList;
    }

    public void setAlumnosModificaList(List<SieniAlumno> alumnosModificaList) {
        this.alumnosModificaList = alumnosModificaList;
    }

    public Long getIdAlumnoModifica() {
        return idAlumnoModifica;
    }

    public void setIdAlumnoModifica(Long idAlumnoModifica) {
        this.idAlumnoModifica = idAlumnoModifica;
    }
    
}
