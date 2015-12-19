/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.model.SieniAnioEscolar;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniSeccion;
import utils.siteUrls;

/**
 *
 * @author INFORMATICA
 */
public class CatSeccionForm {
    private int indexMenu;
    private Integer idanio;
    private Integer idgrado;
    private SieniSeccion eliminar;
    private SieniSeccion ver;

    // consulta de archivo
    private SieniAnioEscolar anio;
    private List<SieniAnioEscolar> listAnios;
    private List<SieniGrado> listGrados;
    private List<SieniSeccion> list;
    //registro de archivo
    private SieniSeccion nuevo;
    //modificacion de archivo
    private SieniSeccion modifica;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasecatSeccion()+ "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasecatSeccion() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasecatSeccion() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasecatSeccion() + "ver.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public SieniSeccion getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniSeccion eliminar) {
        this.eliminar = eliminar;
    }

    public SieniSeccion getVer() {
        return ver;
    }

    public void setVer(SieniSeccion ver) {
        this.ver = ver;
    }

    public Integer getIdanio() {
        return idanio;
    }

    public void setIdanio(Integer idanio) {
        this.idanio = idanio;
    }

    public Integer getIdgrado() {
        return idgrado;
    }

    public void setIdgrado(Integer idgrado) {
        this.idgrado = idgrado;
    }
    
    
    

    public SieniAnioEscolar getAnio() {
        return anio;
    }

    public void setAnio(SieniAnioEscolar anio) {
        this.anio = anio;
    }

    

    public List<SieniGrado> getListGrados() {
        return listGrados;
    }

    public void setListGrados(List<SieniGrado> listGrados) {
        this.listGrados = listGrados;
    }

    
    public List<SieniAnioEscolar> getListAnios() {
        return listAnios;
    }

    public void setListAnios(List<SieniAnioEscolar> listAnios) {
        this.listAnios = listAnios;
    }

    
    public List<SieniSeccion> getList() {
        return list;
    }

    public void setList(List<SieniSeccion> list) {
        this.list = list;
    }

    public SieniSeccion getNuevo() {
        return nuevo;
    }

    public void setNuevo(SieniSeccion nuevo) {
        this.nuevo = nuevo;
    }

    public SieniSeccion getModifica() {
        return modifica;
    }

    public void setModifica(SieniSeccion modifica) {
        this.modifica = modifica;
    }

    
    
    
}
