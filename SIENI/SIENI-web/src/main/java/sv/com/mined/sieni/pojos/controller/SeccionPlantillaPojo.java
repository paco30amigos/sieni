/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.controller;

import java.util.List;
import sv.com.mined.sieni.model.SieniElemPlantilla;

/**
 *
 * @author francisco_medina
 */
public class SeccionPlantillaPojo {

    private String nombre;
    private SieniElemPlantilla idElemPlantilla;
    private List<PantallaPojo> pantallas;
    private Integer pantallaActual;

    public List<PantallaPojo> getPantallas() {
        return pantallas;
    }

    public void setPantallas(List<PantallaPojo> pantallas) {
        this.pantallas = pantallas;
    }

    public SeccionPlantillaPojo() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public SieniElemPlantilla getIdElemPlantilla() {
        return idElemPlantilla;
    }

    public void setIdElemPlantilla(SieniElemPlantilla idElemPlantilla) {
        this.idElemPlantilla = idElemPlantilla;
    }

    public Integer getPantallaActual() {
        return pantallaActual;
    }

    public void setPantallaActual(Integer pantallaActual) {
        this.pantallaActual = pantallaActual;
    }

}
