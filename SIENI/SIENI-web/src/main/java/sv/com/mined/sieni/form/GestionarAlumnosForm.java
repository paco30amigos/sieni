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
import sv.com.mined.sieni.model.SieniArchivo;
import utils.siteUrls;

/**
 *
 * @author Laptop
 */
public class GestionarAlumnosForm {

    private int indexMenu;
    private SieniAlumno eliminar;

    // consulta de alumnos
    private List<SieniAlumno> alumnosList;
    private List<SieniAlumno> listDatosFiltered;
    //registro de alumnos
    private SieniAlumno alumnoNuevo;
    private UploadedFile foto;
    private SieniArchivo fotoUsable;
    private byte[] fotoArchivo;
    //modificacion de alumnos
    private SieniAlumno alumnoModifica;
    private UploadedFile fotoModifica;
    private SieniArchivo fotoUsableModifica;
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
                sU.redirect(sU.getBaseexpedienteAlumnos() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBaseexpedienteAlumnos() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBaseexpedienteAlumnos() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBaseexpedienteAlumnos() + "ver.xhtml");
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

    public SieniAlumno getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniAlumno eliminar) {
        this.eliminar = eliminar;
    }

    public List<SieniAlumno> getListDatosFiltered() {
        return listDatosFiltered;
    }

    public void setListDatosFiltered(List<SieniAlumno> listDatosFiltered) {
        this.listDatosFiltered = listDatosFiltered;
    }
}
