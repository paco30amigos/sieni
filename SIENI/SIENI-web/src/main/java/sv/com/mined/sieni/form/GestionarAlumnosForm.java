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
import sv.com.mined.sieni.model.SieniAlumno;

/**
 *
 * @author Laptop
 */
public class GestionarAlumnosForm {

    private int indexMenu;
    private SieniAlumno eliminar;

    // consulta de alumnos
    private List<SieniAlumno> alumnosList;
    //registro de alumnos
    private SieniAlumno alumnoNuevo;
    private UploadedFile foto;
    private StreamedContent fotoUsable;
    private byte[] fotoArchivo;
    //modificacion de alumnos
    private SieniAlumno alumnoModifica;
    private UploadedFile fotoModifica;
    private StreamedContent fotoUsableModifica;
    private byte[] fotoArchivoModifica;

    public List<SieniAlumno> getAlumnosList() {
        return alumnosList;
    }

    public void setAlumnosList(List<SieniAlumno> alumnosList) {
        this.alumnosList = alumnosList;
    }

    public SieniAlumno getAlumnoNuevo() {
        return alumnoNuevo;
    }

    public void setAlumnoNuevo(SieniAlumno alumnoNuevo) {
        this.alumnoNuevo = alumnoNuevo;
    }

    public SieniAlumno getAlumnoModifica() {
        return alumnoModifica;
    }

    public void setAlumnoModifica(SieniAlumno alumnoModifica) {
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

    public SieniAlumno getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniAlumno eliminar) {
        this.eliminar = eliminar;
    }
}
