/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.form.BitacoraForm;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "bitacoraController")
public class BitacoraController extends BitacoraForm {

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setFormatoRpt("PDF");
        fill();
    }

    private void fill() {
        this.setBitacoraList(sieniBitacoraFacadeRemote.findAll());
        this.setTotalTransacciones(new Long(this.getBitacoraList().size()));
    }

    public void generarReporte() {
        Date desde = this.getDesde();
        Date hasta = this.getHasta();
        this.setBitacoraList(sieniBitacoraFacadeRemote.getBitacorasRangoFecha(desde, hasta));
    }

    public void refresh() {
        Date desde = this.getDesde();
        Date hasta = this.getHasta();
        this.setBitacoraList(sieniBitacoraFacadeRemote.getBitacorasRangoFecha(desde, hasta));
    }

}
