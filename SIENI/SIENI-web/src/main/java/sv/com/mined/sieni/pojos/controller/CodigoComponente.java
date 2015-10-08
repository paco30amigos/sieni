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
public class CodigoComponente {

    private Long idSuperCompon;
    private int cont;
    private List<InteraccionesCompPojo> interacciones;
    private List<CodigoComponente> comps2;
    private Integer delay;
    private String evento;
    private int contEvento;

    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    public List<InteraccionesCompPojo> getInteracciones() {
        return interacciones;
    }

    public void setInteracciones(List<InteraccionesCompPojo> interacciones) {
        this.interacciones = interacciones;
    }

    public List<CodigoComponente> getComps2() {
        return comps2;
    }

    public void setComps2(List<CodigoComponente> comps2) {
        this.comps2 = comps2;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getDelay() {
        return delay;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public int getContEvento() {
        return contEvento;
    }

    public void setContEvento(int contEvento) {
        this.contEvento = contEvento;
    }

    public Long getIdSuperCompon() {
        return idSuperCompon;
    }

    public void setIdSuperCompon(Long idSuperCompon) {
        this.idSuperCompon = idSuperCompon;
    }
}
