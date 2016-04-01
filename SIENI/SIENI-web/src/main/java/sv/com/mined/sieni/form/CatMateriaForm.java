/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.model.SieniCatMateria;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class CatMateriaForm {

    private int indexMenu;
    private SieniCatMateria eliminar;
    private SieniCatMateria ver;

    // consulta de archivo
    private List<SieniCatMateria> list;
    private List<SieniCatMateria> listDatosFiltered;
    //registro de archivo
    private SieniCatMateria nuevo;
    //modificacion de archivo
    private SieniCatMateria modifica;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasecatMateria() + "index.xhtml");
                //sU.resetearFitrosTabla("consulta");
                break;
            case 1:
                sU.redirect(sU.getBasecatMateria() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasecatMateria() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasecatMateria() + "ver.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public SieniCatMateria getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniCatMateria eliminar) {
        this.eliminar = eliminar;
    }

    public SieniCatMateria getVer() {
        return ver;
    }

    public void setVer(SieniCatMateria ver) {
        this.ver = ver;
    }

    public List<SieniCatMateria> getList() {
        return list;
    }

    public void setList(List<SieniCatMateria> list) {
        this.list = list;
    }

    public SieniCatMateria getNuevo() {
        return nuevo;
    }

    public void setNuevo(SieniCatMateria nuevo) {
        this.nuevo = nuevo;
    }

    public SieniCatMateria getModifica() {
        return modifica;
    }

    public void setModifica(SieniCatMateria modifica) {
        this.modifica = modifica;
    }

    public List<SieniCatMateria> getListDatosFiltered() {
        return listDatosFiltered;
    }

    public void setListDatosFiltered(List<SieniCatMateria> listDatosFiltered) {
        this.listDatosFiltered = listDatosFiltered;
    }

}
