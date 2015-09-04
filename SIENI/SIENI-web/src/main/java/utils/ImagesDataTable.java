/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniArchivoFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.controller.GestionComponentesInteractivosController;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniArchivo;
import sv.com.mined.sieni.model.SieniDocente;

/**
 *
 * @author Laptop
 */
@ManagedBean
@ApplicationScoped
public class ImagesDataTable {

    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    @EJB
    private SieniArchivoFacadeRemote sieniArchivoFacadeRemote;

    public StreamedContent getImagenAlumno() {
        StreamedContent ret = null;
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            String imageID = (String) ((HttpServletRequest) context.getExternalContext().getRequest()).getParameter("imageID");
            Long idAlumno = Long.parseLong(imageID);
            SieniAlumno seleccionado = null;
            for (SieniAlumno actual : sieniAlumnoFacadeRemote.findAll()) {
                if (actual.getIdAlumno().equals(idAlumno)) {
                    seleccionado = actual;
                    break;
                }
            }
            if (seleccionado != null) {
                if (seleccionado.getAlFoto() != null) {
                    ret = new DefaultStreamedContent(new ByteArrayInputStream(seleccionado.getAlFoto()));
                }
            }
            return ret;
        }
    }

    public StreamedContent getImagenDocente() {
        StreamedContent ret = null;
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            String imageID = (String) ((HttpServletRequest) context.getExternalContext().getRequest()).getParameter("imageID");
            Long idDocente = Long.parseLong(imageID);
            SieniDocente seleccionado = null;
            for (SieniDocente actual : sieniDocenteFacadeRemote.findAll()) {
                if (actual.getIdDocente().equals(idDocente)) {
                    seleccionado = actual;
                    break;
                }
            }
            if (seleccionado != null) {
                if (seleccionado.getDcFoto() != null) {
                    ret = new DefaultStreamedContent(new ByteArrayInputStream(seleccionado.getDcFoto()));
                }
            }
            return ret;
        }
    }

    public StreamedContent getArchivo() {
        StreamedContent ret = null;
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            String fileId = (String) ((HttpServletRequest) context.getExternalContext().getRequest()).getParameter("fileId");
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            GestionComponentesInteractivosController controller = (GestionComponentesInteractivosController) req.getSession().getAttribute("gestionComponentesInteractivosController");
            Long idArchivo = Long.parseLong(fileId);
            byte[] archivo = sieniArchivoFacadeRemote.getArchivoLazy(idArchivo);
            SieniArchivo archivoEntity = new SieniArchivo();
            archivoEntity.setIdArchivo(idArchivo);
            archivoEntity = sieniArchivoFacadeRemote.find(idArchivo);
            InputStream input = new ByteArrayInputStream(archivo);
            if (archivoEntity.getArTipo().equals(new Character('A'))) {
                ret = new DefaultStreamedContent(input, "audio/mpeg");
            } else if (archivoEntity.getArTipo().equals(new Character('V'))) {
                ret = new DefaultStreamedContent(input, "video/mp4");
            } else {
                ret = new DefaultStreamedContent(input);
            }
            return ret;
        }
    }
}
