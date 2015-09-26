/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniTemaDuda;
import utils.siteUrls;

/**
 *
 * @author INFORMATICA
 */
public class GestionarConsultasForm {
    private int indexMenu;
    private SieniTemaDuda eliminar;

    // consulta de noticias
    private List<SieniTemaDuda> consultasList;
    //registro de noticias
    private List<SieniDocente> docentesList;
    private Long idDocente;
    private SieniTemaDuda consultaNueva;
    //modificacion de noticias
    private SieniTemaDuda consultaModifica;
    private Long idDocenteModifica;
    
    
    public int getIndexMenu() {
        return indexMenu;
    }

    
    
    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasegestionConsulta()+ "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasegestionConsulta() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasegestionConsulta() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasegestionConsulta() + "ver.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public SieniTemaDuda getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniTemaDuda eliminar) {
        this.eliminar = eliminar;
    }

    public List<SieniTemaDuda> getConsultasList() {
        return consultasList;
    }

    public void setConsultasList(List<SieniTemaDuda> consultasList) {
        this.consultasList = consultasList;
    }

    public List<SieniDocente> getDocentesList() {
        return docentesList;
    }

    public void setDocentesList(List<SieniDocente> docentesList) {
        this.docentesList = docentesList;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public SieniTemaDuda getConsultaNueva() {
        return consultaNueva;
    }

    public void setConsultaNueva(SieniTemaDuda consultaNueva) {
        this.consultaNueva = consultaNueva;
    }

    public SieniTemaDuda getConsultaModifica() {
        return consultaModifica;
    }

    public void setConsultaModifica(SieniTemaDuda consultaModifica) {
        this.consultaModifica = consultaModifica;
    }

    public Long getIdDocenteModifica() {
        return idDocenteModifica;
    }

    public void setIdDocenteModifica(Long idDocenteModifica) {
        this.idDocenteModifica = idDocenteModifica;
    }
    
    
    

}
