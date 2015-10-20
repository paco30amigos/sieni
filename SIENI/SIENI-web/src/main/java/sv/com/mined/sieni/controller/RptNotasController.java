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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        Date desde = this.getDesde();
        Date hasta = this.getHasta();
        this.setNotasList(sieniNotasFacadeRemote.getNotasRangoFecha(desde, hasta));
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Generar Reporte", "Reporte de Notas", 1L, loginBean.getTipoUsuario().charAt(0), req.getRemoteAddr()));
    }

    public void refresh() {
        Date desde = this.getDesde();
        Date hasta = this.getHasta();
        this.setNotasList(sieniNotasFacadeRemote.getNotasRangoFecha(desde, hasta));
    }

}
