/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import sv.com.mined.sieni.model.SieniSuperCompon;

/**
 *
 * @author francisco_medina
 */
public class SuperCompPojo {

    private Integer count;
    private SieniSuperCompon supComp;
    private HashMap<Long, List<InteraccionesCompPojo>> eventos;//click, mostrar, etc-     
    private HashMap<String, List<InteraccionesCompPojo>> eventosEv;//click, mostrar, etc-     
    private Collection<List<InteraccionesCompPojo>> eventosCollection;
    private boolean visible;

    public List<InteraccionesCompPojo> getInteraccionesCompPojoByIdEvento(Long idEvento) {
        List<InteraccionesCompPojo> ret = null;
        if (eventos != null) {
            ret = eventos.get(idEvento);
        }
        return ret;
    }

    public List<InteraccionesCompPojo> getInteraccionesCompPojoByEvento(String evento) {
        List<InteraccionesCompPojo> ret = null;
        if (eventosEv != null) {
            ret = eventosEv.get(evento);
        }
        return ret;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public HashMap<Long, List<InteraccionesCompPojo>> getEventos() {
        return eventos;
    }

    public void setEventos(HashMap<Long, List<InteraccionesCompPojo>> eventos) {
        this.eventos = eventos;
    }

    public SieniSuperCompon getSupComp() {
        return supComp;
    }

    public void setSupComp(SieniSuperCompon supComp) {
        this.supComp = supComp;
    }

    public Collection<List<InteraccionesCompPojo>> getEventosCollection() {
        eventosCollection = eventos.values();
        return eventosCollection;
    }

    public HashMap<String, List<InteraccionesCompPojo>> getEventosEv() {
        return eventosEv;
    }

    public void setEventosEv(HashMap<String, List<InteraccionesCompPojo>> eventosEv) {
        this.eventosEv = eventosEv;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
