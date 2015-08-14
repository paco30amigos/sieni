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
import sv.com.mined.sieni.SieniMatriculaFacadeRemote;
import sv.com.mined.sieni.form.RptMatriculaForm;
import sv.com.mined.sieni.model.SieniBitacora;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "rptMatriculaController")
public class RptMatriculaController extends RptMatriculaForm {

    @EJB
    private SieniMatriculaFacadeRemote sieniMatriculaFacadeRemote;

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setFormatoRpt("PDF");
        this.setAnio("2,015");
        fill();
    }

    private void fill() {
        this.setMatriculaList(sieniMatriculaFacadeRemote.findAll());
    }

    public void generarReporte() {
        String anio = this.getAnio().replaceAll(",", "");
        Integer anioInt = Integer.parseInt(anio);
        this.setMatriculaList(sieniMatriculaFacadeRemote.getMatriculasAnio(anioInt));
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Generar Reporte", "Reporte de Matricula", 1L, new Character('D')));
    }

    public void refresh() {
        String anio = this.getAnio().replaceAll(",", "");
        Integer anioInt = Integer.parseInt(anio);
        this.setMatriculaList(sieniMatriculaFacadeRemote.getMatriculasAnio(anioInt));
    }

}
