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
import sv.com.mined.sieni.model.SieniArchivo;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class GestionArchivoMultimediaForm {

    private int indexMenu;
    private SieniArchivo eliminar;
    private SieniArchivo ver;
    private StreamedContent archivoVer;
    private final String formatosVideo = "/(\\.|\\/)(mp4|wmv|mp4)$/";
    private final String formatosAudio = "/(\\.|\\/)(mp3)$/";
    private final String formatosImagen = "/(\\.|\\/)(jpg)$/";

    // consulta de archivo
    private List<SieniArchivo> archivoList;
    //registro de archivo
    private SieniArchivo archivoNuevo;
    private StreamedContent archivoData;
    private byte[] archivoUsable;
    private UploadedFile archivoSubidoNuevo;
    private String formatoArchivo;
    //modificacion de archivo
    private SieniArchivo archivoModifica;
    private StreamedContent archivoModificaData;
    private byte[] archivoUsableModifica;
    private UploadedFile archivoSubidoModifica;
    private String formatoArchivoModifica;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasegestionArchivosMultimedia()+ "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasegestionArchivosMultimedia() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasegestionArchivosMultimedia() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasegestionArchivosMultimedia() + "ver.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public SieniArchivo getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniArchivo eliminar) {
        this.eliminar = eliminar;
    }

    public List<SieniArchivo> getArchivoList() {
        return archivoList;
    }

    public void setArchivoList(List<SieniArchivo> archivoList) {
        this.archivoList = archivoList;
    }

    public SieniArchivo getArchivoNuevo() {
        return archivoNuevo;
    }

    public void setArchivoNuevo(SieniArchivo archivoNuevo) {
        this.archivoNuevo = archivoNuevo;
    }

    public StreamedContent getArchivoData() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            return archivoData;
        }
    }

    public void setArchivoData(StreamedContent archivoData) {
        this.archivoData = archivoData;
    }

    public SieniArchivo getArchivoModifica() {
        return archivoModifica;
    }

    public void setArchivoModifica(SieniArchivo archivoModifica) {
        this.archivoModifica = archivoModifica;
    }

    public StreamedContent getArchivoModificaData() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            return archivoModificaData;
        }
    }

    public void setArchivoModificaData(StreamedContent archivoModificaData) {
        this.archivoModificaData = archivoModificaData;
    }

    public byte[] getArchivoUsable() {
        return archivoUsable;
    }

    public void setArchivoUsable(byte[] archivoUsable) {
        this.archivoUsable = archivoUsable;
    }

    public byte[] getArchivoUsableModifica() {
        return archivoUsableModifica;
    }

    public void setArchivoUsableModifica(byte[] archivoUsableModifica) {
        this.archivoUsableModifica = archivoUsableModifica;
    }

    public StreamedContent getArchivo(byte[] foto) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            StreamedContent ret = null;
            if (foto != null) {
                InputStream input = new ByteArrayInputStream(foto);
                ret = new DefaultStreamedContent(input);
            }
            return ret;
        }
    }

    public UploadedFile getArchivoSubidoNuevo() {
        return archivoSubidoNuevo;
    }

    public void setArchivoSubidoNuevo(UploadedFile archivoSubidoNuevo) {
        this.archivoSubidoNuevo = archivoSubidoNuevo;
    }

    public UploadedFile getArchivoSubidoModifica() {
        return archivoSubidoModifica;
    }

    public void setArchivoSubidoModifica(UploadedFile archivoSubidoModifica) {
        this.archivoSubidoModifica = archivoSubidoModifica;
    }

    public String getFormatosVideo() {
        return formatosVideo;
    }

    public String getFormatosAudio() {
        return formatosAudio;
    }

    public String getFormatosImagen() {
        return formatosImagen;
    }

    public String getFormatoArchivo() {
        return formatoArchivo;
    }

    public void setFormatoArchivo(String formatoArchivo) {
        this.formatoArchivo = formatoArchivo;
    }

    public String getFormatoArchivoModifica() {
        return formatoArchivoModifica;
    }

    public void setFormatoArchivoModifica(String formatoArchivoModifica) {
        this.formatoArchivoModifica = formatoArchivoModifica;
    }

    public SieniArchivo getVer() {
        return ver;
    }

    public void setVer(SieniArchivo ver) {
        this.ver = ver;
    }

    public StreamedContent getArchivoVer() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            if (this.getVer().getArArchivo() != null) {
                InputStream input = new ByteArrayInputStream(this.getVer().getArArchivo());
                if (ver.getArTipo().equals(new Character('A'))) {
                    archivoVer = new DefaultStreamedContent(input, "audio/mpeg");
                } else if (ver.getArTipo().equals(new Character('V'))) {
                    archivoVer = new DefaultStreamedContent(input, "video/mp4"); 
                } else {
                    archivoVer = new DefaultStreamedContent(input);
                }
            }
            return archivoVer;
        }
    }

    public void setArchivoVer(StreamedContent archivoVer) {
        this.archivoVer = archivoVer;
    }

}
