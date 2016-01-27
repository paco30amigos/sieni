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
import sv.com.mined.sieni.model.SieniNoticia;
import utils.siteUrls;

/**
 *
 * @author Laptop
 */
public class GestionarNoticiasForm {

    private int indexMenu;
    private SieniNoticia eliminar;

    // consulta de noticias
    private List<SieniNoticia> noticiasList;
    private List<SieniNoticia> listDatosFiltered;
    //registro de noticias
    private List<SieniCurso> cursosList;
    private Long idCurso;
    private SieniNoticia noticiaNueva;
    private UploadedFile foto;
    private StreamedContent fotoUsable;
    private byte[] fotoArchivo;
    //modificacion de noticias
    private SieniNoticia noticiaModifica;
    private Long idCursoModifica;
    private UploadedFile fotoModifica;
    private StreamedContent fotoUsableModifica;
    private byte[] fotoArchivoModifica;

    public SieniNoticia getNoticiaNueva() {
        return noticiaNueva;
    }

    public void setNoticiaNueva(SieniNoticia noticiaNueva) {
        this.noticiaNueva = noticiaNueva;
    }

    public SieniNoticia getNoticiaModifica() {
        return noticiaModifica;
    }

    public void setNoticiaModifica(SieniNoticia noticiaModifica) {
        this.noticiaModifica = noticiaModifica;
    }

    public List<SieniNoticia> getNoticiasList() {
        return noticiasList;
    }

    public void setNoticiasList(List<SieniNoticia> noticiasList) {
        this.noticiasList = noticiasList;
    }

    public List<SieniCurso> getCursosList() {
        return cursosList;
    }

    public void setCursosList(List<SieniCurso> cursosList) {
        this.cursosList = cursosList;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
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

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasegestionNoticia() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasegestionNoticia() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasegestionNoticia() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasegestionNoticia() + "ver.xhtml");
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

    public Long getIdCursoModifica() {
        return idCursoModifica;
    }

    public void setIdCursoModifica(Long idCursoModifica) {
        this.idCursoModifica = idCursoModifica;
    }

    public SieniNoticia getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniNoticia eliminar) {
        this.eliminar = eliminar;
    }

    public List<SieniNoticia> getListDatosFiltered() {
        return listDatosFiltered;
    }

    public void setListDatosFiltered(List<SieniNoticia> listDatosFiltered) {
        this.listDatosFiltered = listDatosFiltered;
    }
}
