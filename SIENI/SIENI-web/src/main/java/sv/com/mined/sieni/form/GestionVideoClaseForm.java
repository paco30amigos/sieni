/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import org.primefaces.model.DualListModel;
import sv.com.mined.sieni.model.SieniArchivo;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniClaseSupComp;
import sv.com.mined.sieni.model.SieniClaseVidPtos;
import sv.com.mined.sieni.model.SieniEvento;
import sv.com.mined.sieni.model.SieniInteEntrComp;
import sv.com.mined.sieni.model.SieniPlantilla;
import sv.com.mined.sieni.model.SieniSuperCompon;
import sv.com.mined.sieni.model.SieniTipoElemPlantilla;
import sv.com.mined.sieni.pojos.controller.ComponenteInteractivoPojo;
import sv.com.mined.sieni.pojos.controller.InteraccionMultiplePojo;
import sv.com.mined.sieni.pojos.controller.PantallaPojo;
import sv.com.mined.sieni.pojos.controller.SeccionPlantillaPojo;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class GestionVideoClaseForm {

    private int indexMenu;
    private SieniClase eliminar;
    private SieniClase ver;
    // consulta de plantilla
    private List<SieniClase> claseList;
    private List<SieniClase> listDatosFiltered;
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
    private ComponenteInteractivoPojo superCompEliminado;

    //modificacion de plantilla
    private SieniClase claseModifica;
    private List<SieniPlantilla> plantillaModificaList;

    private String funcionJS;
    private List<SieniInteEntrComp> interacPantallaElemPlantillaActual;
    private List<SieniInteEntrComp> interacTotal;
    private List<SieniInteEntrComp> interacEliminados;
    private SieniInteEntrComp nuevaInterac;
    private List<SieniEvento> evn1;
    private List<SieniEvento> evn1Multi;
    private List<SieniEvento> evn2;
    private List<SieniSuperCompon> compDisponibles;
    private SieniInteEntrComp interacEliminada;
    private SieniInteEntrComp nuevaInteracMult1;
//    private List<SieniInteEntrComp> nuevaInteracMult2;
    private DualListModel<SieniInteEntrComp> nuevaInteracMult2;
    private List<InteraccionMultiplePojo> listaMultiple;
    private Integer opSelectMulti;
    private SieniArchivo video;
    private List<SieniArchivo> videosList;
    private Integer duracion;
    private List<SieniClaseVidPtos> puntosVid;
    private List<SieniClaseVidPtos> puntosVidEliminados;
    private SieniClaseVidPtos nuevoPunto;
    private SieniClaseVidPtos puntoEliminado;
    private List<SieniTipoElemPlantilla> listaTipoElemPlantilla;
    private Integer npantalla;
    private Long idTipoElempPlantilla;
    private List<ComponenteInteractivoPojo> componentesPantallaActual;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBaseclaseVideoAlmacenada() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBaseclaseVideoAlmacenada() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBaseclaseVideoAlmacenada() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBaseclaseVideoAlmacenada() + "ver.xhtml");
                break;
            case 4:
                sU.redirect(sU.getBaseclaseVideoAlmacenada() + "configurar.xhtml");
                break;
            case 5:
                sU.redirect(sU.getBaseclaseVideoAlmacenada() + "configurarInterac.xhtml");
                break;
            case 6:
                sU.redirect(sU.getBaseclaseVideoAlmacenada() + "configurarVideoTime.xhtml");
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

    public ComponenteInteractivoPojo getSuperCompEliminado() {
        return superCompEliminado;
    }

    public void setSuperCompEliminado(ComponenteInteractivoPojo superCompEliminado) {
        this.superCompEliminado = superCompEliminado;
    }

    public String getFuncionJS() {
        return funcionJS;
    }

    public void setFuncionJS(String funcionJS) {
        this.funcionJS = funcionJS;
    }

    public List<SieniInteEntrComp> getInteracPantallaElemPlantillaActual() {
        return interacPantallaElemPlantillaActual;
    }

    public void setInteracPantallaElemPlantillaActual(List<SieniInteEntrComp> interacPantallaElemPlantillaActual) {
        this.interacPantallaElemPlantillaActual = interacPantallaElemPlantillaActual;
    }

    public List<SieniInteEntrComp> getInteracTotal() {
        return interacTotal;
    }

    public void setInteracTotal(List<SieniInteEntrComp> interacTotal) {
        this.interacTotal = interacTotal;
    }

    public List<SieniInteEntrComp> getInteracEliminados() {
        return interacEliminados;
    }

    public void setInteracEliminados(List<SieniInteEntrComp> interacEliminados) {
        this.interacEliminados = interacEliminados;
    }

    public SieniInteEntrComp getNuevaInterac() {
        return nuevaInterac;
    }

    public void setNuevaInterac(SieniInteEntrComp nuevaInterac) {
        this.nuevaInterac = nuevaInterac;
    }

    public List<SieniEvento> getEvn1() {
        return evn1;
    }

    public void setEvn1(List<SieniEvento> evn1) {
        this.evn1 = evn1;
    }

    public List<SieniEvento> getEvn2() {
        return evn2;
    }

    public void setEvn2(List<SieniEvento> evn2) {
        this.evn2 = evn2;
    }

    public List<SieniSuperCompon> getCompDisponibles() {
        return compDisponibles;
    }

    public void setCompDisponibles(List<SieniSuperCompon> compDisponibles) {
        this.compDisponibles = compDisponibles;
    }

    public SieniInteEntrComp getInteracEliminada() {
        return interacEliminada;
    }

    public void setInteracEliminada(SieniInteEntrComp interacEliminada) {
        this.interacEliminada = interacEliminada;
    }

    public SieniInteEntrComp getNuevaInteracMult1() {
        return nuevaInteracMult1;
    }

    public void setNuevaInteracMult1(SieniInteEntrComp nuevaInteracMult1) {
        this.nuevaInteracMult1 = nuevaInteracMult1;
    }

    public DualListModel<SieniInteEntrComp> getNuevaInteracMult2() {
        return nuevaInteracMult2;
    }

    public void setNuevaInteracMult2(DualListModel<SieniInteEntrComp> nuevaInteracMult2) {
        this.nuevaInteracMult2 = nuevaInteracMult2;
    }

    public List<SieniEvento> getEvn1Multi() {
        return evn1Multi;
    }

    public void setEvn1Multi(List<SieniEvento> evn1Multi) {
        this.evn1Multi = evn1Multi;
    }

    public List<InteraccionMultiplePojo> getListaMultiple() {
        return listaMultiple;
    }

    public void setListaMultiple(List<InteraccionMultiplePojo> listaMultiple) {
        this.listaMultiple = listaMultiple;
    }

    public Integer getOpSelectMulti() {
        return opSelectMulti;
    }

    public void setOpSelectMulti(Integer opSelectMulti) {
        this.opSelectMulti = opSelectMulti;
    }

    public SieniArchivo getVideo() {
        return video;
    }

    public void setVideo(SieniArchivo video) {
        this.video = video;
    }

    public List<SieniArchivo> getVideosList() {
        return videosList;
    }

    public void setVideosList(List<SieniArchivo> videosList) {
        this.videosList = videosList;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public List<SieniClaseVidPtos> getPuntosVid() {
        return puntosVid;
    }

    public void setPuntosVid(List<SieniClaseVidPtos> puntosVid) {
        this.puntosVid = puntosVid;
    }

    public SieniClaseVidPtos getNuevoPunto() {
        return nuevoPunto;
    }

    public void setNuevoPunto(SieniClaseVidPtos nuevoPunto) {
        this.nuevoPunto = nuevoPunto;
    }

    public List<SieniTipoElemPlantilla> getListaTipoElemPlantilla() {
        return listaTipoElemPlantilla;
    }

    public void setListaTipoElemPlantilla(List<SieniTipoElemPlantilla> listaTipoElemPlantilla) {
        this.listaTipoElemPlantilla = listaTipoElemPlantilla;
    }

    public List<SieniClaseVidPtos> getPuntosVidEliminados() {
        return puntosVidEliminados;
    }

    public void setPuntosVidEliminados(List<SieniClaseVidPtos> puntosVidEliminados) {
        this.puntosVidEliminados = puntosVidEliminados;
    }

    public SieniClaseVidPtos getPuntoEliminado() {
        return puntoEliminado;
    }

    public void setPuntoEliminado(SieniClaseVidPtos puntoEliminado) {
        this.puntoEliminado = puntoEliminado;
    }

    public Integer getNpantalla() {
        return npantalla;
    }

    public void setNpantalla(Integer npantalla) {
        this.npantalla = npantalla;
    }

    public Long getIdTipoElempPlantilla() {
        return idTipoElempPlantilla;
    }

    public void setIdTipoElempPlantilla(Long idTipoElempPlantilla) {
        this.idTipoElempPlantilla = idTipoElempPlantilla;
    }

    public List<ComponenteInteractivoPojo> getComponentesPantallaActual() {
        return componentesPantallaActual;
    }

    public void setComponentesPantallaActual(List<ComponenteInteractivoPojo> componentesPantallaActual) {
        this.componentesPantallaActual = componentesPantallaActual;
    }

    public List<SieniClase> getListDatosFiltered() {
        return listDatosFiltered;
    }

    public void setListDatosFiltered(List<SieniClase> listDatosFiltered) {
        this.listDatosFiltered = listDatosFiltered;
    }

}
