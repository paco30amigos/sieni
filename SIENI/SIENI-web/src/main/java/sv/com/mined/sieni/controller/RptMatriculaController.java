/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniMatriculaFacadeRemote;
import sv.com.mined.sieni.form.RptMatriculaForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniMatricula;
import sv.com.mined.sieni.pojos.rpt.RptMatriculasPojo;
import utils.DateUtils;
import utils.FormatUtils;

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
//        this.setMatriculaList(sieniMatriculaFacadeRemote.findAll());
        RptMatriculasPojo elem = new RptMatriculasPojo();
        
        List<SieniMatricula> matriculas = sieniMatriculaFacadeRemote.findAll();
        this.setListDatos(new ArrayList<RptMatriculasPojo>());
        
        for (SieniMatricula actual : matriculas) {
            elem = new RptMatriculasPojo(actual.getIdMatricula().toString(), actual.getMtAnio(), actual.getIdAlumno().toString(), null, actual.getIdGrado().toString(), actual.getIdSeccion().toString());
            
            this.getListDatos().add(elem);
        }
    }

    public void generarReporte() {
//        String anio = this.getAnio().replaceAll(",", "");
//        Integer anioInt = Integer.parseInt(anio);
//        this.setMatriculaList(sieniMatriculaFacadeRemote.getMatriculasAnio(anioInt));
//        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Generar Reporte", "Reporte de Matricula", 1L, new Character('D')));
        fill();
        String path = "resources/reportes/rtpMatriculas.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("anio", this.getAnio());
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        try {
            
        } catch (Exception e) {
            
        }
    }

    public void refresh() {
        String anio = this.getAnio().replaceAll(",", "");
        Integer anioInt = Integer.parseInt(anio);
        this.setMatriculaList(sieniMatriculaFacadeRemote.getMatriculasAnio(anioInt));
    }

}
