/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.controller;

import sv.com.mined.sieni.model.SieniInteEntrComp;

/**
 *
 * @author francisco_medina
 */
public class EventosPojo {

    private String evento;
    private Long idEvento;
    private SieniInteEntrComp interac;

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public SieniInteEntrComp getInterac() {
        return interac;
    }

    public void setInterac(SieniInteEntrComp interac) {
        this.interac = interac;
    }
}
