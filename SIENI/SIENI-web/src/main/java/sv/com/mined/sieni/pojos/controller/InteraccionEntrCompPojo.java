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
 * @author francisco_medina
 */
public class InteraccionEntrCompPojo {
    private SieniSuperCompon c1;
    private SieniSuperCompon c2;
    
    
    private SieniCompInteraccion inicial;
    private List<SieniInteEntrComp> interacciones1;
    private List<SieniInteEntrComp> interacciones2;

    public SieniCompInteraccion getInicial() {
        return inicial;
    }

    public void setInicial(SieniCompInteraccion inicial) {
        this.inicial = inicial;
    }

    public List<SieniInteEntrComp> getInteracciones1() {
        return interacciones1;
    }

    public void setInteracciones1(List<SieniInteEntrComp> interacciones1) {
        this.interacciones1 = interacciones1;
    }

    public List<SieniInteEntrComp> getInteracciones2() {
        return interacciones2;
    }

    public void setInteracciones2(List<SieniInteEntrComp> interacciones2) {
        this.interacciones2 = interacciones2;
    }

    public SieniSuperCompon getC1() {
        return c1;
    }

    public void setC1(SieniSuperCompon c1) {
        this.c1 = c1;
    }

    public SieniSuperCompon getC2() {
        return c2;
    }

    public void setC2(SieniSuperCompon c2) {
        this.c2 = c2;
    }

}
