/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniArchivoFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
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
        //pagina para streaming
        //http://www.ncolomer.net/2012/03/cometwebsocket-introducing-the-atmosphere-framework/
        
        StreamedContent ret = null;
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            String fileId = (String) ((HttpServletRequest) context.getExternalContext().getRequest()).getParameter("fileId");
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Long idArchivo = Long.parseLong(fileId);
            SieniArchivo archivoEntity = new SieniArchivo();
            archivoEntity.setIdArchivo(idArchivo);
            archivoEntity = sieniArchivoFacadeRemote.find(idArchivo);
            CopiaArchivos cpa = new CopiaArchivos();
            boolean crear = false, copiaExitosa = false;
            //si no existe el nombre del archivo se crea
            String rutaRelativa = archivoEntity.getArRuta() != null ? archivoEntity.getArRuta() : cpa.getRutaRelativa(archivoEntity.getArTipo());
            String ruta = cpa.getResourcesUrl() + cpa.getSeparador() + rutaRelativa;
            if (archivoEntity.getArRuta() != null) {
                File archivoCopia = new File(ruta);
                crear = !archivoCopia.exists();
            } else {
                crear = true;
            }

            if (crear) {//crea la copia para streaming eficiente
                try {
                    byte[] archivo = sieniArchivoFacadeRemote.getArchivoLazy(idArchivo);
                    File f = new File(ruta);
                    FileUtils.writeByteArrayToFile(f, archivo);
                    copiaExitosa = true;
                } catch (IOException ex) {
                    Logger.getLogger(ImagesDataTable.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                copiaExitosa = true;
            }

            if (copiaExitosa) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(new File(ruta));
                    if (archivoEntity.getArTipo().equals(new Character('A'))) {
                        ret = new DefaultStreamedContent(fis, "audio/mpeg");
                    } else if (archivoEntity.getArTipo().equals(new Character('V'))) {
                        ret = new DefaultStreamedContent(fis, "video/mp4");
                    } else {
                        ret = new DefaultStreamedContent(fis);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ImagesDataTable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

////            String driver = "org.postgresql.Driver";
////            String connectString = "jdbc:postgresql://localhost:5432/bd_sieni";
////            String user = "postgres";
////            String password = "postgres";
////            InputStream input = null;
////
////            java.sql.Blob data = null;
////            try {
////                Class.forName(driver);
////                Connection con = DriverManager.getConnection(connectString, user, password);
////                PreparedStatement stmt = con.prepareStatement("select * from sieni_archivo where id_archivo=" + idArchivo.toString());
////                ResultSet res = stmt.executeQuery();
////                con.setAutoCommit(false);
////                while (res.next()) {
////                    input = res.getBinaryStream("ar_archivo");
////                }
////                if (input != null) {
////                    try {
//////                        input = data.getBinaryStream();
////                        if (archivoEntity.getArTipo().equals(new Character('A'))) {
////                            ret = new DefaultStreamedContent(input, "audio/mpeg");
////                        } else if (archivoEntity.getArTipo().equals(new Character('V'))) {
////                            ret = new DefaultStreamedContent(input, "video/mp4");
////                        } else {
////                            ret = new DefaultStreamedContent(input);
////                        }
////                    } catch (Exception ex) {
////                        Logger.getLogger(ImagesDataTable.class.getName()).log(Level.SEVERE, null, ex);
////                    }
////                }
////                stmt.close();
////                con.close();
//            } catch (Exception e) {
//            }
            System.gc();
            return ret;
        }
    }
}
