/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.controller;

import java.util.List;

/**
 *
 * @author bugtraq
 */
public class CodigoEvento {

    private int cont;
    private List<CodigoComponente> componentes;

    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    public List<CodigoComponente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<CodigoComponente> componentes) {
        this.componentes = componentes;
    }
}
