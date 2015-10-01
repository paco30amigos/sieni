/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.controller;

import java.util.List;

/**
 *
 * @author francisco_medina
 */
public class PantallaPojo {

    private Integer numPantalla;
    private List<ComponenteInteractivoPojo> componentes;

    public List<ComponenteInteractivoPojo> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<ComponenteInteractivoPojo> componentes) {
        this.componentes = componentes;
    }

    public Integer getNumPantalla() {
        return numPantalla;
    }

    public void setNumPantalla(Integer numPantalla) {
        this.numPantalla = numPantalla;
    }
}
