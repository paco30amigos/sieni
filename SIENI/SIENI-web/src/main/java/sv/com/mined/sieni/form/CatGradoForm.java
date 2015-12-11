/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.model.SieniGrado;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class CatGradoForm {

    private int indexMenu;
    private SieniGrado eliminar;
    private SieniGrado ver;

    // consulta de archivo
    private List<SieniGrado> list;
    //registro de archivo
    private SieniGrado nuevo;
    //modificacion de archivo
    private SieniGrado modifica;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasecatGrado()+ "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasecatGrado() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasecatGrado() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasecatGrado() + "ver.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public SieniGrado getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniGrado eliminar) {
        this.eliminar = eliminar;
    }

    public SieniGrado getVer() {
        return ver;
    }

    public void setVer(SieniGrado ver) {
        this.ver = ver;
    }

    public List<SieniGrado> getList() {
        return list;
    }

    public void setList(List<SieniGrado> list) {
        this.list = list;
    }

    public SieniGrado getNuevo() {
        return nuevo;
    }

    public void setNuevo(SieniGrado nuevo) {
        this.nuevo = nuevo;
    }

    public SieniGrado getModifica() {
        return modifica;
    }

    public void setModifica(SieniGrado modifica) {
        this.modifica = modifica;
    }

}
