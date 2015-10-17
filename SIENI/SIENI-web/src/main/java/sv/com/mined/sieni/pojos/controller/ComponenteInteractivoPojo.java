/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.controller;

import java.util.List;
import sv.com.mined.sieni.model.SieniClaseSupComp;
import sv.com.mined.sieni.model.SieniCompInteraccion;
import sv.com.mined.sieni.model.SieniSuperCompon;

/**
 *
 * @author francisco_medina
 */
public class ComponenteInteractivoPojo {

    private List<FileStreamedPojo> data;
    private SieniSuperCompon superComp;
    private SieniClaseSupComp claseSuperComp;
    private String texto;
    private List<SieniCompInteraccion> interacciones;
    private boolean visible;
    private boolean mostrar;

    public List<FileStreamedPojo> getData() {
        return data;
    }

    public void setData(List<FileStreamedPojo> data) {
        this.data = data;
    }

    public SieniSuperCompon getSuperComp() {
        return superComp;
    }

    public void setSuperComp(SieniSuperCompon superComp) {
        this.superComp = superComp;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public SieniClaseSupComp getClaseSuperComp() {
        return claseSuperComp;
    }

    public void setClaseSuperComp(SieniClaseSupComp claseSuperComp) {
        this.claseSuperComp = claseSuperComp;
    }

    public List<SieniCompInteraccion> getInteracciones() {
        return interacciones;
    }

    public void setInteracciones(List<SieniCompInteraccion> interacciones) {
        this.interacciones = interacciones;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isMostrar() {
        return mostrar;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }
}
