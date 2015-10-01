/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniClaseSupComp;
import sv.com.mined.sieni.model.SieniInteEntrComp;
import sv.com.mined.sieni.model.SieniPlantilla;
import sv.com.mined.sieni.model.SieniSuperCompon;
import sv.com.mined.sieni.pojos.controller.ComponenteInteractivoPojo;
import sv.com.mined.sieni.pojos.controller.PantallaPojo;
import sv.com.mined.sieni.pojos.controller.SeccionPlantillaPojo;
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
//    private List<SieniClaseSupComp> superCompList;
    private List<SieniPlantilla> plantillaList;
    private List<PantallaPojo> pantallasEliminadas;
    private List<ComponenteInteractivoPojo> componentesEliminados;
    private List<SieniSuperCompon> componentesInteractDisponibles;
    private SieniClaseSupComp nuevoComponente;

    private List<SeccionPlantillaPojo> secciones;

    private Integer paginaActive;
    private Integer idElemenActive;

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

//    public List<SieniClaseSupComp> getSuperCompList() {
//        return superCompList;
//    }
//
//    public void setSuperCompList(List<SieniClaseSupComp> superCompList) {
//        this.superCompList = superCompList;
//    }
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

    public List<SeccionPlantillaPojo> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<SeccionPlantillaPojo> secciones) {
        this.secciones = secciones;
    }

    public Integer getPaginaActive() {
        return paginaActive;
    }

    public void setPaginaActive(Integer paginaActive) {
        this.paginaActive = paginaActive;
    }

    public Integer getIdElemenActive() {
        return idElemenActive;
    }

    public void setIdElemenActive(Integer idElemenActive) {
        this.idElemenActive = idElemenActive;
    }

    public List<PantallaPojo> getPantallasEliminadas() {
        return pantallasEliminadas;
    }

    public void setPantallasEliminadas(List<PantallaPojo> pantallasEliminadas) {
        this.pantallasEliminadas = pantallasEliminadas;
    }

    public List<ComponenteInteractivoPojo> getComponentesEliminados() {
        return componentesEliminados;
    }

    public void setComponentesEliminados(List<ComponenteInteractivoPojo> componentesEliminados) {
        this.componentesEliminados = componentesEliminados;
    }

    public List<SieniSuperCompon> getComponentesInteractDisponibles() {
        return componentesInteractDisponibles;
    }

    public void setComponentesInteractDisponibles(List<SieniSuperCompon> componentesInteractDisponibles) {
        this.componentesInteractDisponibles = componentesInteractDisponibles;
    }

    public SieniClaseSupComp getNuevoComponente() {
        return nuevoComponente;
    }

    public void setNuevoComponente(SieniClaseSupComp nuevoComponente) {
        this.nuevoComponente = nuevoComponente;
    }

}
