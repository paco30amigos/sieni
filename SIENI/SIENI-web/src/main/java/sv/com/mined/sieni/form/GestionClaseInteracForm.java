/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniInteEntrComp;
import sv.com.mined.sieni.model.SieniPlantilla;
import sv.com.mined.sieni.model.SieniSuperCompon;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class GestionClaseInteracForm {

    private int indexMenu;
    private SieniClase eliminar;
    private SieniClase ver;
    // consulta de plantilla
    private List<SieniClase> claseList;
    //registro de plantilla
    private SieniClase claseConfig;
    private List<SieniInteEntrComp> inteEntrCompList;
    private List<SieniSuperCompon> superCompList;
    private List<SieniPlantilla> plantillaList;

    //modificacion de plantilla
    private SieniClase claseModifica;
    private List<SieniPlantilla> plantillaModificaList;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBaseclaseInteractiva() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBaseclaseInteractiva() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBaseclaseInteractiva() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBaseclaseInteractiva() + "ver.xhtml");
                break;
            case 4:
                sU.redirect(sU.getBaseclaseInteractiva() + "configurar.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public SieniClase getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniClase eliminar) {
        this.eliminar = eliminar;
    }

    public SieniClase getVer() {
        return ver;
    }

    public void setVer(SieniClase ver) {
        this.ver = ver;
    }

    public List<SieniClase> getClaseList() {
        return claseList;
    }

    public void setClaseList(List<SieniClase> claseList) {
        this.claseList = claseList;
    }

    public List<SieniInteEntrComp> getInteEntrCompList() {
        return inteEntrCompList;
    }

    public void setInteEntrCompList(List<SieniInteEntrComp> inteEntrCompList) {
        this.inteEntrCompList = inteEntrCompList;
    }

    public List<SieniSuperCompon> getSuperCompList() {
        return superCompList;
    }

    public void setSuperCompList(List<SieniSuperCompon> superCompList) {
        this.superCompList = superCompList;
    }

    public SieniClase getClaseConfig() {
        return claseConfig;
    }

    public void setClaseConfig(SieniClase claseConfig) {
        this.claseConfig = claseConfig;
    }

    public List<SieniPlantilla> getPlantillaList() {
        return plantillaList;
    }

    public void setPlantillaList(List<SieniPlantilla> plantillaList) {
        this.plantillaList = plantillaList;
    }

    public SieniClase getClaseModifica() {
        return claseModifica;
    }

    public void setClaseModifica(SieniClase claseModifica) {
        this.claseModifica = claseModifica;
    }

    public List<SieniPlantilla> getPlantillaModificaList() {
        return plantillaModificaList;
    }

    public void setPlantillaModificaList(List<SieniPlantilla> plantillaModificaList) {
        this.plantillaModificaList = plantillaModificaList;
    }

}
