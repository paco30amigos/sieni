/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
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
    private StreamedContent fotoUsable;
    private byte[] fotoArchivo;
    //modificacion de alumnos
    private SieniDocente alumnoModifica;
    private UploadedFile fotoModifica;
    private StreamedContent fotoUsableModifica;
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

    public StreamedContent getFotoUsable() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            return fotoUsable;
        }
    }

    public void setFotoUsable(StreamedContent fotoUsable) {
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

    public StreamedContent getFotoUsableModifica() {
        return fotoUsableModifica;
    }

    public void setFotoUsableModifica(StreamedContent fotoUsableModifica) {
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
