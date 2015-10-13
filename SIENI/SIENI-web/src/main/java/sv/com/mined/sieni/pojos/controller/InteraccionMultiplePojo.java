/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.controller;

import java.util.List;
import sv.com.mined.sieni.model.SieniEvento;
import sv.com.mined.sieni.model.SieniInteEntrComp;

/**
 *
 * @author francisco_medina
 */
public class InteraccionMultiplePojo {

    private SieniInteEntrComp interaccionEntreComps;
    private List<SieniEvento> eventos;

    public SieniInteEntrComp getInteraccionEntreComps() {
        return interaccionEntreComps;
    }

    public void setInteraccionEntreComps(SieniInteEntrComp interaccionEntreComps) {
        this.interaccionEntreComps = interaccionEntreComps;
    }

    public List<SieniEvento> getEventos() {
        return eventos;
    }

    public void setEventos(List<SieniEvento> eventos) {
        this.eventos = eventos;
    }

}
