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
    private Long idAlumno;
    private Long idMateria;
    private Long idEvaluacion;
    private SieniMateria materia;
    //modificacion de nota
    private SieniNota notaModifica;
    private List<SieniAlumno> alumnosModificaList;
    private List<SieniMateria> materiasModificaList;
    private List<SieniEvaluacion> evaluacionesModificaList;
    private Long idAlumnoModifica;
    private Long idMateriaModifica;
    private Long idEvaluacionModifica;
    private SieniMateria materiaModifica;

    //subida de excel
    private Integer tamanioMaxExcel = 2 * 1024 * 1024;
    private String formatoArchivo = "/(\\.|\\/)(xlsx|xls)$/";
    private List<SieniNota> listaNotasSubidas;
    private SieniMateria materiaSubir;
    private SieniEvaluacion evaluacionSubir;
    private UploadedFile archivoSubidoNuevo;
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

    public SieniNota getNotaModifica() {
        return notaModifica;
    }

    public void setNotaModifica(SieniNota notaModifica) {
        this.notaModifica = notaModifica;
    }

    public List<SieniAlumno> getAlumnosList() {
        return alumnosList;
    }

    public void setAlumnosList(List<SieniAlumno> alumnosList) {
        this.alumnosList = alumnosList;
    }

    public List<SieniAlumno> getAlumnosModificaList() {
        return alumnosModificaList;
    }

    public void setAlumnosModificaList(List<SieniAlumno> alumnosModificaList) {
        this.alumnosModificaList = alumnosModificaList;
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

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public Long getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Long idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Long getIdAlumnoModifica() {
        return idAlumnoModifica;
    }

    public void setIdAlumnoModifica(Long idAlumnoModifica) {
        this.idAlumnoModifica = idAlumnoModifica;
    }

    public Long getIdMateriaModifica() {
        return idMateriaModifica;
    }

    public void setIdMateriaModifica(Long idMateriaModifica) {
        this.idMateriaModifica = idMateriaModifica;
    }

    public Long getIdEvaluacionModifica() {
        return idEvaluacionModifica;
    }

    public void setIdEvaluacionModifica(Long idEvaluacionModifica) {
        this.idEvaluacionModifica = idEvaluacionModifica;
    }

    public SieniMateria getMateria() {
        return materia;
    }

    public void setMateria(SieniMateria materia) {
        this.materia = materia;
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

    public List<SieniNota> getListaNotasSubidas() {
        return listaNotasSubidas;
    }

    public void setListaNotasSubidas(List<SieniNota> listaNotasSubidas) {
        this.listaNotasSubidas = listaNotasSubidas;
    }

    public SieniNota getError() {
        return error;
    }

    public void setError(SieniNota error) {
        this.error = error;
    }

}
