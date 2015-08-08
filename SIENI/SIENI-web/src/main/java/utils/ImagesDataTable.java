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
import sv.com.mined.sieni.model.SieniAlumno;

/**
 *
 * @author Laptop
 */
@ManagedBean
@ApplicationScoped
public class ImagesDataTable {

    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
//
//    public StreamedContent getImage(byte[] foto) {
//        StreamedContent ret = null;
//        if (foto != null) {
//            InputStream input = new ByteArrayInputStream(foto);
//            ret = new DefaultStreamedContent(input, "image/jpg");
//        }
//        return new DefaultStreamedContent(new ByteArrayInputStream(foto));
//    }

    public StreamedContent getImagenAlumno() {
        StreamedContent ret = null;
//        if (foto != null) {
//            InputStream input = new ByteArrayInputStream(foto);
//            ret = new DefaultStreamedContent(input, "image/jpg");
//        }
//        return ret;
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
}
