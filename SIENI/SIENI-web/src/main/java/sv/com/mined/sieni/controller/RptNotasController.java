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
import sv.com.mined.sieni.SieniNotaFacadeRemote;
import sv.com.mined.sieni.form.RptNotasForm;
import sv.com.mined.sieni.model.SieniBitacora;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "rptNotasController")
public class RptNotasController extends RptNotasForm {

    @EJB
    private SieniNotaFacadeRemote sieniNotasFacadeRemote;

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setFormatoRpt("PDF");
        fill();
    }

    private void fill() {
        this.setNotasList(sieniNotasFacadeRemote.findAll());
    }

    public void generarReporte() {
        Date desde = this.getDesde();
        Date hasta = this.getHasta();
        this.setNotasList(sieniNotasFacadeRemote.getNotasRangoFecha(desde, hasta));
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Generar Reporte", "Reporte de Notas", 1L, new Character('D')));
    }

    public void refresh() {
        Date desde = this.getDesde();
        Date hasta = this.getHasta();
        this.setNotasList(sieniNotasFacadeRemote.getNotasRangoFecha(desde, hasta));
    }

}
