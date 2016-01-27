/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import sv.com.mined.sieni.model.SieniTipoElemPlantilla;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class CatTipoElemPlantillaForm {

    private int indexMenu;
    private SieniTipoElemPlantilla eliminar;
    private SieniTipoElemPlantilla ver;

    // consulta de archivo
    private List<SieniTipoElemPlantilla> list;
    private List<SieniTipoElemPlantilla> listDatosFiltered;
    //registro de archivo
    private SieniTipoElemPlantilla nuevo;
    //modificacion de archivo
    private SieniTipoElemPlantilla modifica;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasecatTipoElemPlantilla() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasecatTipoElemPlantilla() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasecatTipoElemPlantilla() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasecatTipoElemPlantilla() + "ver.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public SieniTipoElemPlantilla getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniTipoElemPlantilla eliminar) {
        this.eliminar = eliminar;
    }

    public SieniTipoElemPlantilla getVer() {
        return ver;
    }

    public void setVer(SieniTipoElemPlantilla ver) {
        this.ver = ver;
    }

    public List<SieniTipoElemPlantilla> getList() {
        return list;
    }

    public void setList(List<SieniTipoElemPlantilla> list) {
        this.list = list;
    }

    public SieniTipoElemPlantilla getNuevo() {
        return nuevo;
    }

    public void setNuevo(SieniTipoElemPlantilla nuevo) {
        this.nuevo = nuevo;
    }

    public SieniTipoElemPlantilla getModifica() {
        return modifica;
    }

    public void setModifica(SieniTipoElemPlantilla modifica) {
        this.modifica = modifica;
    }

    public List<SieniTipoElemPlantilla> getListDatosFiltered() {
        return listDatosFiltered;
    }

    public void setListDatosFiltered(List<SieniTipoElemPlantilla> listDatosFiltered) {
        this.listDatosFiltered = listDatosFiltered;
    }

}
