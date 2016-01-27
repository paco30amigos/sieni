/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.model.SieniElemPlantilla;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniPlantilla;
import sv.com.mined.sieni.model.SieniTipoElemPlantilla;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class GestionPlantillasForm {

    private int indexMenu;
    private SieniPlantilla eliminar;
    private SieniPlantilla ver;
    // consulta de plantilla
    private List<SieniPlantilla> plantillaList;
    private List<SieniPlantilla> listDatosFiltered;
    //registro de plantilla
    private SieniPlantilla plantillaNuevo;
    private List<SieniTipoElemPlantilla> tipoPlantilla;
    private List<SieniElemPlantilla> elemPlantillaSelected;
    private List<SieniElemPlantilla> elemPlantillaEliminados;
    private SieniTipoElemPlantilla nuevoElem;
    private SieniElemPlantilla elemPlantillaEliminado;
    private List<SieniMateria> materias;
    private SieniMateria materia;

    //modificacion de plantilla
    private SieniPlantilla plantillaModifica;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasegestionPlantilla() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasegestionPlantilla() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasegestionPlantilla() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasegestionPlantilla() + "ver.xhtml");
                break;
            case 4:
                sU.redirect(sU.getBasegestionPlantilla() + "configurar.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public SieniPlantilla getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniPlantilla eliminar) {
        this.eliminar = eliminar;
    }

    public SieniPlantilla getVer() {
        return ver;
    }

    public void setVer(SieniPlantilla ver) {
        this.ver = ver;
    }

    public List<SieniPlantilla> getPlantillaList() {
        return plantillaList;
    }

    public void setPlantillaList(List<SieniPlantilla> plantillaList) {
        this.plantillaList = plantillaList;
    }

    public SieniPlantilla getPlantillaNuevo() {
        return plantillaNuevo;
    }

    public void setPlantillaNuevo(SieniPlantilla plantillaNuevo) {
        this.plantillaNuevo = plantillaNuevo;
    }

    public List<SieniTipoElemPlantilla> getTipoPlantilla() {
        return tipoPlantilla;
    }

    public void setTipoPlantilla(List<SieniTipoElemPlantilla> tipoPlantilla) {
        this.tipoPlantilla = tipoPlantilla;
    }

    public SieniPlantilla getPlantillaModifica() {
        return plantillaModifica;
    }

    public void setPlantillaModifica(SieniPlantilla plantillaModifica) {
        this.plantillaModifica = plantillaModifica;
    }

    public List<SieniElemPlantilla> getElemPlantillaSelected() {
        return elemPlantillaSelected;
    }

    public void setElemPlantillaSelected(List<SieniElemPlantilla> elemPlantillaSelected) {
        this.elemPlantillaSelected = elemPlantillaSelected;
    }

    public List<SieniElemPlantilla> getElemPlantillaEliminados() {
        return elemPlantillaEliminados;
    }

    public void setElemPlantillaEliminados(List<SieniElemPlantilla> elemPlantillaEliminados) {
        this.elemPlantillaEliminados = elemPlantillaEliminados;
    }

    public SieniTipoElemPlantilla getNuevoElem() {
        return nuevoElem;
    }

    public void setNuevoElem(SieniTipoElemPlantilla nuevoElem) {
        this.nuevoElem = nuevoElem;
    }

    public SieniElemPlantilla getElemPlantillaEliminado() {
        return elemPlantillaEliminado;
    }

    public void setElemPlantillaEliminado(SieniElemPlantilla elemPlantillaEliminado) {
        this.elemPlantillaEliminado = elemPlantillaEliminado;
    }

    public List<SieniMateria> getMaterias() {
        return materias;
    }

    public void setMaterias(List<SieniMateria> materias) {
        this.materias = materias;
    }

    public SieniMateria getMateria() {
        return materia;
    }

    public void setMateria(SieniMateria materia) {
        this.materia = materia;
    }

    public List<SieniPlantilla> getListDatosFiltered() {
        return listDatosFiltered;
    }

    public void setListDatosFiltered(List<SieniPlantilla> listDatosFiltered) {
        this.listDatosFiltered = listDatosFiltered;
    }

}
