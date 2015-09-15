/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import sv.com.mined.sieni.SieniArchivoFacadeRemote;
import sv.com.mined.sieni.model.SieniArchivo;

/**
 *
 * @author francisco_medina
 */
@ManagedBean(name = "copiaArchivos")
public class CopiaArchivos {

    @EJB
    private SieniArchivoFacadeRemote sieniArchivoFacadeRemote;

    public Properties getProperties() {
        try {
            Properties props = new Properties();
            props.load(CopiaArchivos.class.getClassLoader().getResourceAsStream("sieni.properties"));
            return props;
        } catch (IOException ex) {
            Logger.getLogger(CopiaArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getSeparador() {
//        return File.separator;
        return getProperties().getProperty("separador");
    }

    public String getResourcesUrl() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String ruta = servletContext.getRealPath("") + getRelativeResourcesUrl();
//        URL url = CopiaArchivos.class.getClassLoader().getResource("sieni.properties");
//        String ruta = url.getFile();
        ruta = formatUrl(ruta);
        int pos = (ruta.lastIndexOf("/") != -1) ? ruta.lastIndexOf("/") : ruta.lastIndexOf(File.separator);
        ruta = ruta.substring(0, pos);
        return ruta;
    }

    public String formatUrl(String url) {
        Character separador1 = '\\', separador2 = '/';
        url = url.replace(separador1, getSeparador().charAt(0));
        url = url.replace(separador2, getSeparador().charAt(0));
        return url;
    }

    public String getMultimediaBaseUrl() {
        String multimedia = getProperties().getProperty("resources.multimedia.base");
        return multimedia;
    }

    public String getRelativeResourcesUrl() {
        return getSeparador() + "resources" + getSeparador() + getMultimediaBaseUrl() + getSeparador();
    }

    public String getContexto() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        return servletContext.getContextPath();
    }

    public String getRutaRelativa(Character tipoArchivo) {
        CopiaArchivos cpa = new CopiaArchivos();
        String rutaRelativa = "";
        String extencion = "";
        Properties props = cpa.getProperties();
        String separador = cpa.getSeparador();
        Long numeroUnico = new Date().getTime();
        switch (tipoArchivo) {
            case 'V':
                extencion = ".mp4";
                rutaRelativa = props.getProperty("resources.multimedia.video") + separador + numeroUnico.toString() + extencion;
                break;
            case 'I':
                extencion = ".jpg";
                rutaRelativa = props.getProperty("resources.multimedia.imagen") + separador + numeroUnico.toString() + extencion;
                break;
            case 'A':
                extencion = ".mp3";
                rutaRelativa = props.getProperty("resources.multimedia.audio") + separador + numeroUnico.toString() + extencion;
                break;
        }
        return rutaRelativa;
    }

    //cuando el archivo esta creado en la BD pero no existe en la carpeta de recursos
    public boolean copyDataToResource(SieniArchivo archivoEntity) {
        CopiaArchivos cpa = new CopiaArchivos();
        Long idArchivo = archivoEntity.getIdArchivo();
        boolean crear = false, copiaExitosa = false;
        //si no existe el nombre del archivo se crea
        String rutaRelativa = archivoEntity.getArRuta() != null ? archivoEntity.getArRuta() : cpa.getRutaRelativa(archivoEntity.getArTipo());
        String ruta = cpa.getResourcesUrl() + cpa.getSeparador() + rutaRelativa;
        ruta = formatUrl(ruta);
        rutaRelativa = formatUrl(rutaRelativa);
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
                archivoEntity.setArRuta(rutaRelativa);
                sieniArchivoFacadeRemote.edit(archivoEntity);
                copiaExitosa = true;
            } catch (IOException ex) {
                Logger.getLogger(CopiaArchivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            copiaExitosa = true;
        }
        return copiaExitosa;
    }

    //cuando se quiere actualizar el archivo de un recurso
    public SieniArchivo updateDataToResource(SieniArchivo archivoEntity) {
        CopiaArchivos cpa = new CopiaArchivos();
        boolean crear = false;
        //si no existe el nombre del archivo se crea
        String rutaRelativa = archivoEntity.getArRuta() != null ? archivoEntity.getArRuta() : cpa.getRutaRelativa(archivoEntity.getArTipo());
        String ruta = cpa.getResourcesUrl() + cpa.getSeparador() + rutaRelativa;
        ruta = formatUrl(ruta);
        rutaRelativa = formatUrl(rutaRelativa);
        if (archivoEntity.getArRuta() != null) {
            File archivoCopia = new File(ruta);
            crear = !archivoCopia.exists();
        } else {
            crear = true;
        }
        if (crear) {//crea la copia para streaming eficiente
            try {
                byte[] archivo = archivoEntity.getArArchivo();
                File f = new File(ruta);
                FileUtils.writeByteArrayToFile(f, archivo);
                archivoEntity.setArRuta(rutaRelativa);

            } catch (IOException ex) {
                Logger.getLogger(CopiaArchivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return archivoEntity;
    }

    //Elimina el archivo de la carpeta de recursos
    public boolean deleteDataToResource(SieniArchivo archivoEntity) {
        CopiaArchivos cpa = new CopiaArchivos();
        boolean existe = false, eliminacionExitosa = false;
        //si no existe el nombre del archivo se crea
        String rutaRelativa = archivoEntity.getArRuta() != null ? archivoEntity.getArRuta() : cpa.getRutaRelativa(archivoEntity.getArTipo());
        String ruta = cpa.getResourcesUrl() + cpa.getSeparador() + rutaRelativa;
        ruta = formatUrl(ruta);
        rutaRelativa = formatUrl(rutaRelativa);
        if (archivoEntity.getArRuta() != null) {
            File archivoCopia = new File(ruta);
            existe = archivoCopia.exists();
        }

        if (existe) {//crea la copia para streaming eficiente
            try {
                File f = new File(ruta);
                f.delete();
                eliminacionExitosa = true;
            } catch (Exception ex) {
                Logger.getLogger(CopiaArchivos.class.getName()).log(Level.SEVERE, null, ex);
                eliminacionExitosa = false;
            }
        } else {
            eliminacionExitosa = true;
        }
        return eliminacionExitosa;
    }

    public void setSieniArchivoFacadeRemote(SieniArchivoFacadeRemote sieniArchivoFacadeRemote) {
        this.sieniArchivoFacadeRemote = sieniArchivoFacadeRemote;
    }
}
