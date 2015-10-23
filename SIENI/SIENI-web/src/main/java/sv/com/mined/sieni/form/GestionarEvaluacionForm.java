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
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.model.SieniEvaluacion;
import utils.siteUrls;

/**
 *
 * @author Ever
 */
public class GestionarEvaluacionForm {

    private int indexMenu;
    private SieniEvaluacion eliminar;

    // consulta de alumnos
    private List<SieniEvaluacion> evaluacionList;
    //registro de alumnos
    private SieniEvaluacion evaluacionNuevo;
    private UploadedFile foto;
    private StreamedContent fotoUsable;
    private byte[] fotoArchivo;
    //modificacion de alumnos
    private SieniEvaluacion evaluacionModifica;
    private UploadedFile fotoModifica;
    private StreamedContent fotoUsableModifica;
    private byte[] fotoArchivoModifica;
    private Long idCurso;
    private List<SieniCurso> cursoList;

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
            if (fotoUsable != null) {
                return new DefaultStreamedContent();
            } else {
                return null;
            }
        } else {
            if (fotoUsable != null) {
                return fotoUsable;
            } else {
                return null;
            }

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

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public List<SieniCurso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<SieniCurso> cursoList) {
        this.cursoList = cursoList;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasegestionEvaluacion() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasegestionEvaluacion() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasegestionEvaluacion() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasegestionEvaluacion() + "ver.xhtml");
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

    public SieniEvaluacion getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniEvaluacion eliminar) {
        this.eliminar = eliminar;
    }

    public List<SieniEvaluacion> getEvaluacionList() {
        return evaluacionList;
    }

    public void setEvaluacionList(List<SieniEvaluacion> evaluacionList) {
        this.evaluacionList = evaluacionList;
    }

    public SieniEvaluacion getEvaluacionNuevo() {
        return evaluacionNuevo;
    }

    public void setEvaluacionNuevo(SieniEvaluacion evalucionNuevo) {
        this.evaluacionNuevo = evalucionNuevo;
    }

    public SieniEvaluacion getEvaluacionModifica() {
        return evaluacionModifica;
    }

    public void setEvaluacionModifica(SieniEvaluacion evalucionModifica) {
        this.evaluacionModifica = evalucionModifica;
    }

   
}
