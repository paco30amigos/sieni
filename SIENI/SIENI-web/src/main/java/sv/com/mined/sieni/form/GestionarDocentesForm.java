/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import sv.com.mined.sieni.model.SieniArchivo;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniMateriaDocente;
import utils.siteUrls;

/**
 *
 * @author Laptop
 */
public class GestionarDocentesForm {

    private int indexMenu;
    private SieniDocente eliminar;

    // consulta de alumnos
    private List<SieniDocente> alumnosList;
    //registro de alumnos
    private SieniDocente alumnoNuevo;
    private UploadedFile foto;
    private SieniArchivo fotoUsable;
    private byte[] fotoArchivo;
    //modificacion de alumnos
    private SieniDocente alumnoModifica;
    private UploadedFile fotoModifica;
    private SieniArchivo fotoUsableModifica;
    private byte[] fotoArchivoModifica;

    //para gestion de materias
    private List<SieniMateriaDocente> materiasDocente;
    private List<SieniMateriaDocente> materiasDocenteEliminadas;
    private List<SieniMateria> materias;
    private SieniMateria materia;
    private SieniMateriaDocente materiaEliminada;

    public List<SieniDocente> getDocentesList() {
        return alumnosList;
    }

    public void setDocentesList(List<SieniDocente> alumnosList) {
        this.alumnosList = alumnosList;
    }

    public SieniDocente getDocenteNuevo() {
        return alumnoNuevo;
    }

    public void setDocenteNuevo(SieniDocente alumnoNuevo) {
        this.alumnoNuevo = alumnoNuevo;
    }

    public SieniDocente getDocenteModifica() {
        return alumnoModifica;
    }

    public void setDocenteModifica(SieniDocente alumnoModifica) {
        this.alumnoModifica = alumnoModifica;
    }

    public UploadedFile getFoto() {
        return foto;
    }

    public void setFoto(UploadedFile foto) {
        this.foto = foto;
    }

    public StreamedContent getImage(byte[] foto) {
        StreamedContent ret = null;
        if (foto != null) {
            InputStream input = new ByteArrayInputStream(foto);
            ret = new DefaultStreamedContent(input);
        }
        return ret;
    }

    public SieniArchivo getFotoUsable() {
        return fotoUsable;
    }

    public void setFotoUsable(SieniArchivo fotoUsable) {
        this.fotoUsable = fotoUsable;
    }

    public byte[] getFotoArchivo() {
        return fotoArchivo;
    }

    public void setFotoArchivo(byte[] fotoArchivo) {
        this.fotoArchivo = fotoArchivo;
    }

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasegestionDocentes() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasegestionDocentes() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasegestionDocentes() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasegestionDocentes() + "ver.xhtml");
                break;
            case 4:
                sU.redirect(sU.getBasegestionDocentes() + "gestionMaterias.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public UploadedFile getFotoModifica() {
        return fotoModifica;
    }

    public void setFotoModifica(UploadedFile fotoModifica) {
        this.fotoModifica = fotoModifica;
    }

    public SieniArchivo getFotoUsableModifica() {
        return fotoUsableModifica;
    }

    public void setFotoUsableModifica(SieniArchivo fotoUsableModifica) {
        this.fotoUsableModifica = fotoUsableModifica;
    }

    public byte[] getFotoArchivoModifica() {
        return fotoArchivoModifica;
    }

    public void setFotoArchivoModifica(byte[] fotoArchivoModifica) {
        this.fotoArchivoModifica = fotoArchivoModifica;
    }

    public SieniDocente getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniDocente eliminar) {
        this.eliminar = eliminar;
    }

    public List<SieniMateriaDocente> getMateriasDocente() {
        return materiasDocente;
    }

    public void setMateriasDocente(List<SieniMateriaDocente> materiasDocente) {
        this.materiasDocente = materiasDocente;
    }

    public List<SieniMateria> getMaterias() {
        return materias;
    }

    public void setMaterias(List<SieniMateria> materias) {
        this.materias = materias;
    }

    public SieniMateria getMateria() {
        return materia;
    }

    public void setMateria(SieniMateria materia) {
        this.materia = materia;
    }

    public List<SieniMateriaDocente> getMateriasDocenteEliminadas() {
        return materiasDocenteEliminadas;
    }

    public void setMateriasDocenteEliminadas(List<SieniMateriaDocente> materiasDocenteEliminadas) {
        this.materiasDocenteEliminadas = materiasDocenteEliminadas;
    }

    public SieniMateriaDocente getMateriaEliminada() {
        return materiaEliminada;
    }

    public void setMateriaEliminada(SieniMateriaDocente materiaEliminada) {
        this.materiaEliminada = materiaEliminada;
    }
}
