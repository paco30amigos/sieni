/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import org.primefaces.model.UploadedFile;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniEvaluacion;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniNota;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class GestionNotasForm {

    private int indexMenu;
    private SieniNota eliminar;

    // consulta de nota
    private List<SieniNota> notaList;
    //registro de nota
    private SieniNota notaNuevo;
    private List<SieniAlumno> alumnosList;
    private List<SieniMateria> materiasList;
    private List<SieniEvaluacion> evaluacionesList;
    private SieniAlumno idAlumno;
    private SieniMateria idMateria;
    private SieniEvaluacion idEvaluacion;
    private SieniMateria materia;
    //modificacion de nota
    private SieniNota notaModifica;
    private List<SieniAlumno> alumnosModificaList;
    private List<SieniMateria> materiasModificaList;
    private List<SieniEvaluacion> evaluacionesModificaList;
    private SieniAlumno idAlumnoModifica;
    private SieniMateria idMateriaModifica;
    private SieniEvaluacion idEvaluacionModifica;
    private SieniMateria materiaModifica;

    //subida de excel
    private Integer tamanioMaxExcel = 2 * 1024 * 1024;
    private String formatoArchivo = "/(\\.|\\/)(xlsx|xls)$/";
    private List<SieniNota> listaNotasSubidas;
    private SieniMateria materiaSubir;
    private SieniEvaluacion evaluacionSubir;
    private UploadedFile archivoSubidoNuevo;
    private List<SieniMateria> materiasExcelList;
    private List<SieniEvaluacion> evaluacionesExcelList;
    private SieniNota error;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasegestionNota() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasegestionNota() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasegestionNota() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasegestionNota() + "ver.xhtml");
                break;
            case 4:
                sU.redirect(sU.getBasegestionNota() + "subir.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public SieniNota getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniNota eliminar) {
        this.eliminar = eliminar;
    }

    public List<SieniNota> getNotaList() {
        return notaList;
    }

    public void setNotaList(List<SieniNota> notaList) {
        this.notaList = notaList;
    }

    public SieniNota getNotaNuevo() {
        return notaNuevo;
    }

    public void setNotaNuevo(SieniNota notaNuevo) {
        this.notaNuevo = notaNuevo;
    }

    public List<SieniAlumno> getAlumnosList() {
        return alumnosList;
    }

    public void setAlumnosList(List<SieniAlumno> alumnosList) {
        this.alumnosList = alumnosList;
    }

    public List<SieniMateria> getMateriasList() {
        return materiasList;
    }

    public void setMateriasList(List<SieniMateria> materiasList) {
        this.materiasList = materiasList;
    }

    public List<SieniEvaluacion> getEvaluacionesList() {
        return evaluacionesList;
    }

    public void setEvaluacionesList(List<SieniEvaluacion> evaluacionesList) {
        this.evaluacionesList = evaluacionesList;
    }

    public SieniAlumno getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(SieniAlumno idAlumno) {
        this.idAlumno = idAlumno;
    }

    public SieniMateria getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(SieniMateria idMateria) {
        this.idMateria = idMateria;
    }

    public SieniEvaluacion getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(SieniEvaluacion idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public SieniMateria getMateria() {
        return materia;
    }

    public void setMateria(SieniMateria materia) {
        this.materia = materia;
    }

    public SieniNota getNotaModifica() {
        return notaModifica;
    }

    public void setNotaModifica(SieniNota notaModifica) {
        this.notaModifica = notaModifica;
    }

    public List<SieniAlumno> getAlumnosModificaList() {
        return alumnosModificaList;
    }

    public void setAlumnosModificaList(List<SieniAlumno> alumnosModificaList) {
        this.alumnosModificaList = alumnosModificaList;
    }

    public List<SieniMateria> getMateriasModificaList() {
        return materiasModificaList;
    }

    public void setMateriasModificaList(List<SieniMateria> materiasModificaList) {
        this.materiasModificaList = materiasModificaList;
    }

    public List<SieniEvaluacion> getEvaluacionesModificaList() {
        return evaluacionesModificaList;
    }

    public void setEvaluacionesModificaList(List<SieniEvaluacion> evaluacionesModificaList) {
        this.evaluacionesModificaList = evaluacionesModificaList;
    }

    public SieniAlumno getIdAlumnoModifica() {
        return idAlumnoModifica;
    }

    public void setIdAlumnoModifica(SieniAlumno idAlumnoModifica) {
        this.idAlumnoModifica = idAlumnoModifica;
    }

    public SieniMateria getIdMateriaModifica() {
        return idMateriaModifica;
    }

    public void setIdMateriaModifica(SieniMateria idMateriaModifica) {
        this.idMateriaModifica = idMateriaModifica;
    }

    public SieniEvaluacion getIdEvaluacionModifica() {
        return idEvaluacionModifica;
    }

    public void setIdEvaluacionModifica(SieniEvaluacion idEvaluacionModifica) {
        this.idEvaluacionModifica = idEvaluacionModifica;
    }

    public SieniMateria getMateriaModifica() {
        return materiaModifica;
    }

    public void setMateriaModifica(SieniMateria materiaModifica) {
        this.materiaModifica = materiaModifica;
    }

    public Integer getTamanioMaxExcel() {
        return tamanioMaxExcel;
    }

    public void setTamanioMaxExcel(Integer tamanioMaxExcel) {
        this.tamanioMaxExcel = tamanioMaxExcel;
    }

    public String getFormatoArchivo() {
        return formatoArchivo;
    }

    public void setFormatoArchivo(String formatoArchivo) {
        this.formatoArchivo = formatoArchivo;
    }

    public List<SieniNota> getListaNotasSubidas() {
        return listaNotasSubidas;
    }

    public void setListaNotasSubidas(List<SieniNota> listaNotasSubidas) {
        this.listaNotasSubidas = listaNotasSubidas;
    }

    public SieniMateria getMateriaSubir() {
        return materiaSubir;
    }

    public void setMateriaSubir(SieniMateria materiaSubir) {
        this.materiaSubir = materiaSubir;
    }

    public SieniEvaluacion getEvaluacionSubir() {
        return evaluacionSubir;
    }

    public void setEvaluacionSubir(SieniEvaluacion evaluacionSubir) {
        this.evaluacionSubir = evaluacionSubir;
    }

    public UploadedFile getArchivoSubidoNuevo() {
        return archivoSubidoNuevo;
    }

    public void setArchivoSubidoNuevo(UploadedFile archivoSubidoNuevo) {
        this.archivoSubidoNuevo = archivoSubidoNuevo;
    }

    public SieniNota getError() {
        return error;
    }

    public void setError(SieniNota error) {
        this.error = error;
    }

    public List<SieniMateria> getMateriasExcelList() {
        return materiasExcelList;
    }

    public void setMateriasExcelList(List<SieniMateria> materiasExcelList) {
        this.materiasExcelList = materiasExcelList;
    }

    public List<SieniEvaluacion> getEvaluacionesExcelList() {
        return evaluacionesExcelList;
    }

    public void setEvaluacionesExcelList(List<SieniEvaluacion> evaluacionesExcelList) {
        this.evaluacionesExcelList = evaluacionesExcelList;
    }

}
