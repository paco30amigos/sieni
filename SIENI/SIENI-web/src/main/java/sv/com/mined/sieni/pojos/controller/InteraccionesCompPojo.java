/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.controller;

import java.util.List;
import sv.com.mined.sieni.model.SieniCompInteraccion;
import sv.com.mined.sieni.model.SieniInteEntrComp;
import sv.com.mined.sieni.model.SieniSuperCompon;

/**
 *
 * @author bugtraq
 */
public class InteraccionesCompPojo {

    private Integer cont;
    private SieniSuperCompon componente;
    private SieniCompInteraccion interaccion;

    public SieniSuperCompon getComponente() {
        return componente;
    }

    public void setComponente(SieniSuperCompon componente) {
        this.componente = componente;
    }

    public Integer getCont() {
        return cont;
    }

    public void setCont(Integer cont) {
        this.cont = cont;
    }

    public SieniCompInteraccion getInteraccion() {
        return interaccion;
    }

    public void setInteraccion(SieniCompInteraccion interaccion) {
        this.interaccion = interaccion;
    }

}
