/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.model.SieniClase;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class GestionClasesOnlineForm {

    private List<SieniClase> clasesOnlineList;
    private List<String> listaUsuarios;
    private SieniClase claseActual;
    private int indexMenu;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBaseclaseOnline() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBaseclaseOnline() + "ver.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBaseclaseOnline() + "transmitir.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public List<String> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<String> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<SieniClase> getClasesOnlineList() {
        return clasesOnlineList;
    }

    public void setClasesOnlineList(List<SieniClase> clasesOnlineList) {
        this.clasesOnlineList = clasesOnlineList;
    }

    public SieniClase getClaseActual() {
        return claseActual;
    }

    public void setClaseActual(SieniClase claseActual) {
        this.claseActual = claseActual;
    }

}
