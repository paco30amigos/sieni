/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import sv.com.mined.sieni.model.SieniAccion;
import sv.com.mined.sieni.model.SieniArchivo;
import sv.com.mined.sieni.model.SieniCompInteraccion;
import sv.com.mined.sieni.model.SieniComponente;
import sv.com.mined.sieni.model.SieniEvento;
import sv.com.mined.sieni.model.SieniSuperCompon;
import sv.com.mined.sieni.model.SieniTipoSuperCompon;
import sv.com.mined.sieni.pojos.controller.FileStreamedPojo;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class GestionComponentesInteractivosForm {

    private int indexMenu;
    private SieniSuperCompon eliminar;
    private SieniSuperCompon ver;
    private StreamedContent archivoVer;

    // consulta de archivo
    private List<SieniSuperCompon> datosList;
    //registro de archivo
    private SieniSuperCompon nuevo;
    private List<SieniEvento> eventos;
    private List<SieniAccion> acciones;
    private List<SieniTipoSuperCompon> listaTipo;
    private Long tipoSuperCompon;
    private Long idArchivo;
    //modificacion de archivo
    private SieniSuperCompon modifica;
    private List<SieniEvento> eventosModifica;
    private List<SieniAccion> accionesModifica;
    private List<SieniTipoSuperCompon> listaTipoModifica;
    private Long tipoSuperComponModifica;
    private Long idArchivoModifica;

    //configuracion de componentes
    private SieniSuperCompon config;
    private UploadedFile archivoSubida;
//    private HashMap<String, FileStreamedPojo> map;
    private List<SieniArchivo> listaArchivos;
    private List<SieniArchivo> listaArchivosComponente;
    private List<SieniComponente> listaArchivosEliminados;
    private DualListModel<FileStreamedPojo> listaOrdenable;
    private List<SieniCompInteraccion> listaInteraccion;
    private List<SieniCompInteraccion> listaInteraccionesElimn;
    private SieniCompInteraccion nuevaInterac;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasecomponenteInteractiva() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasecomponenteInteractiva() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasecomponenteInteractiva() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasecomponenteInteractiva() + "ver.xhtml");
                break;
            case 4:
                sU.redirect(sU.getBasecomponenteInteractiva() + "configurar.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public SieniSuperCompon getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniSuperCompon eliminar) {
        this.eliminar = eliminar;
    }

    public SieniSuperCompon getVer() {
        return ver;
    }

    public void setVer(SieniSuperCompon ver) {
        this.ver = ver;
    }

    public StreamedContent getArchivoVer() {
        return archivoVer;
    }

    public void setArchivoVer(StreamedContent archivoVer) {
        this.archivoVer = archivoVer;
    }

    public List<SieniSuperCompon> getDatosList() {
        return datosList;
    }

    public void setDatosList(List<SieniSuperCompon> datosList) {
        this.datosList = datosList;
    }

    public SieniSuperCompon getNuevo() {
        return nuevo;
    }

    public void setNuevo(SieniSuperCompon nuevo) {
        this.nuevo = nuevo;
    }

    public List<SieniEvento> getEventos() {
        return eventos;
    }

    public void setEventos(List<SieniEvento> eventos) {
        this.eventos = eventos;
    }

    public List<SieniAccion> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<SieniAccion> acciones) {
        this.acciones = acciones;
    }

    public SieniSuperCompon getModifica() {
        return modifica;
    }

    public void setModifica(SieniSuperCompon modifica) {
        this.modifica = modifica;
    }

    public List<SieniEvento> getEventosModifica() {
        return eventosModifica;
    }

    public void setEventosModifica(List<SieniEvento> eventosModifica) {
        this.eventosModifica = eventosModifica;
    }

    public List<SieniAccion> getAccionesModifica() {
        return accionesModifica;
    }

    public void setAccionesModifica(List<SieniAccion> accionesModifica) {
        this.accionesModifica = accionesModifica;
    }

    public List<SieniTipoSuperCompon> getListaTipo() {
        return listaTipo;
    }

    public void setListaTipo(List<SieniTipoSuperCompon> listaTipo) {
        this.listaTipo = listaTipo;
    }

    public List<SieniTipoSuperCompon> getListaTipoModifica() {
        return listaTipoModifica;
    }

    public void setListaTipoModifica(List<SieniTipoSuperCompon> listaTipoModifica) {
        this.listaTipoModifica = listaTipoModifica;
    }

    public Long getTipoSuperCompon() {
        return tipoSuperCompon;
    }

    public void setTipoSuperCompon(Long tipoSuperCompon) {
        this.tipoSuperCompon = tipoSuperCompon;
    }

    public Long getTipoSuperComponModifica() {
        return tipoSuperComponModifica;
    }

    public void setTipoSuperComponModifica(Long tipoSuperComponModifica) {
        this.tipoSuperComponModifica = tipoSuperComponModifica;
    }

    public List<SieniArchivo> getListaArchivos() {
        return listaArchivos;
    }

    public void setListaArchivos(List<SieniArchivo> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public Long getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    public Long getIdArchivoModifica() {
        return idArchivoModifica;
    }

    public void setIdArchivoModifica(Long idArchivoModifica) {
        this.idArchivoModifica = idArchivoModifica;
    }

    public StreamedContent getFile(byte[] foto) {
        StreamedContent ret = null;
        if (foto != null) {
            InputStream input = new ByteArrayInputStream(foto);
            ret = new DefaultStreamedContent(input);
        }
        return ret;
    }

    public SieniSuperCompon getConfig() {
        return config;
    }

    public void setConfig(SieniSuperCompon config) {
        this.config = config;
    }

    public UploadedFile getArchivoSubida() {
        return archivoSubida;
    }

    public void setArchivoSubida(UploadedFile archivoSubida) {
        this.archivoSubida = archivoSubida;
    }

    public StreamedContent getImage(byte[] foto) {
        StreamedContent ret = null;
        if (foto != null) {
            InputStream input = new ByteArrayInputStream(foto);
            ret = new DefaultStreamedContent(input);
        }
        return ret;
    }

    public List<SieniComponente> getListaArchivosEliminados() {
        return listaArchivosEliminados;
    }

    public void setListaArchivosEliminados(List<SieniComponente> listaArchivosEliminados) {
        this.listaArchivosEliminados = listaArchivosEliminados;
    }

    public DualListModel<FileStreamedPojo> getListaOrdenable() {
        return listaOrdenable;
    }

    public void setListaOrdenable(DualListModel<FileStreamedPojo> listaOrdenable) {
        this.listaOrdenable = listaOrdenable;
    }

    public List<SieniArchivo> getListaArchivosComponente() {
        return listaArchivosComponente;
    }

    public void setListaArchivosComponente(List<SieniArchivo> listaArchivosComponente) {
        this.listaArchivosComponente = listaArchivosComponente;
    }

    public List<SieniCompInteraccion> getListaInteraccion() {
        return listaInteraccion;
    }

    public void setListaInteraccion(List<SieniCompInteraccion> listaInteraccion) {
        this.listaInteraccion = listaInteraccion;
    }

    public SieniCompInteraccion getNuevaInterac() {
        if (nuevaInterac == null) {
            nuevaInterac = new SieniCompInteraccion();
        }
        if (nuevaInterac.getIdAccion() == null) {
            nuevaInterac.setIdAccion(new SieniAccion());
        }
        if (nuevaInterac.getIdEvento() == null) {
            nuevaInterac.setIdEvento(new SieniEvento());
        }
        return nuevaInterac;
    }

    public void setNuevaInterac(SieniCompInteraccion nuevaInterac) {
        this.nuevaInterac = nuevaInterac;
    }

    public List<SieniCompInteraccion> getListaInteraccionesElimn() {
        return listaInteraccionesElimn;
    }

    public void setListaInteraccionesElimn(List<SieniCompInteraccion> listaInteraccionesElimn) {
        this.listaInteraccionesElimn = listaInteraccionesElimn;
    }

}
