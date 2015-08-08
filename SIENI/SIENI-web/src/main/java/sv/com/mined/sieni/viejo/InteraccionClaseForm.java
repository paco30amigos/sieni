/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.viejo;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import utils.Combo;

/**
 *
 * @author bugtraq
 */
@ManagedBean
public class InteraccionClaseForm {

    //listas
    private List<Combo> eventos;
    private List<Combo> acciones;
    private List<Combo> sigsComponente;
    private List<Combo> eventosSigComponente;
    //seleccionados
    private Integer duracion;
    private Integer tiEsperaActSigComp;
    private Combo evento;
    private Combo accion;
    private Combo sigComponente;
    private Combo eventoSigComponente;

    @PostConstruct
    public void init() {
        eventos = new ArrayList<>();
        eventos.add(new Combo("0", "click", null));
        eventos.add(new Combo("1", "doble click", null));
        eventos.add(new Combo("2", "mouse sobre", null));

        acciones = new ArrayList<>();
        acciones.add(new Combo("0", "Agitar", null));
        acciones.add(new Combo("1", "Mover", null));
        acciones.add(new Combo("2", "Reproducir", null));
        acciones.add(new Combo("3", "Pausar", null));
    }

    public List<Combo> getEventos() {
        return eventos;
    }

    public void setEventos(List<Combo> eventos) {
        this.eventos = eventos;
    }

    public List<Combo> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<Combo> acciones) {
        this.acciones = acciones;
    }

    public List<Combo> getSigsComponente() {
        return sigsComponente;
    }

    public void setSigsComponente(List<Combo> sigsComponente) {
        this.sigsComponente = sigsComponente;
    }

    public List<Combo> getEventosSigComponente() {
        return eventosSigComponente;
    }

    public void setEventosSigComponente(List<Combo> eventosSigComponente) {
        this.eventosSigComponente = eventosSigComponente;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Integer getTiEsperaActSigComp() {
        return tiEsperaActSigComp;
    }

    public void setTiEsperaActSigComp(Integer tiEsperaActSigComp) {
        this.tiEsperaActSigComp = tiEsperaActSigComp;
    }

    public Combo getEvento() {
        return evento;
    }

    public void setEvento(Combo evento) {
        this.evento = evento;
    }

    public Combo getAccion() {
        return accion;
    }

    public void setAccion(Combo accion) {
        this.accion = accion;
    }

    public Combo getSigComponente() {
        return sigComponente;
    }

    public void setSigComponente(Combo sigComponente) {
        this.sigComponente = sigComponente;
    }

    public Combo getEventoSigComponente() {
        return eventoSigComponente;
    }

    public void setEventoSigComponente(Combo eventoSigComponente) {
        this.eventoSigComponente = eventoSigComponente;
    }

}
