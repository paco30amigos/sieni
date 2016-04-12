/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniClaseFacadeRemote;
import sv.com.mined.sieni.SieniCursoFacadeRemote;
import sv.com.mined.sieni.form.RptClasesForm;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.pojos.rpt.RptClasesPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author INFORMATICA
 */
@SessionScoped
@ManagedBean(name = "rptClasesController")
public class RptClasesController extends RptClasesForm{
    
    @EJB
    SieniClaseFacadeRemote sieniClaseFacadeRemote;

    @EJB
    SieniCursoFacadeRemote sieniCursoFacadeRemote;

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setIdCurso(0);
        this.setTipoC(0);
        this.setEstadoC(0);
        this.setCurso(null);
        this.setTotalClases("0");
        this.setTipoRpt(0);
        this.setListDatos(new ArrayList<RptClasesPojo>());
        this.setListCursos(sieniCursoFacadeRemote.findActivos());
        fill();
    }

    public void fill() {
        RptClasesPojo elem = new RptClasesPojo();
        this.setCurso(null);
        this.setListDatos(new ArrayList<RptClasesPojo>());
        List<SieniClase> clases = new ArrayList<SieniClase>();
        for (SieniCurso actual : this.getListCursos()) {
            if(actual.getIdCurso().intValue() == this.getIdCurso()){
                this.setCurso(actual);
            }
        }
        clases = sieniClaseFacadeRemote.findClasesRpt(this.getCurso(),this.getTipoC(),this.getEstadoC());
        for (SieniClase actual : clases) {
            
            String horario = actual.getClHorario();
            String dias[] = horario.split(",");
            String horarioMod = "";
            
            for(int i=0; i<dias.length; i++){
                switch(dias[i]){
                    case "L": dias[i]="Lu";
                    horarioMod += dias[i] + ", ";
                        break;
                    case "M": dias[i]="Ma";
                    horarioMod += dias[i] + ", ";
                        break;
                    case "X": dias[i]="Mi";
                    horarioMod += dias[i] + ", ";
                        break;
                    case "J": dias[i]="Ju";
                    horarioMod += dias[i] + ", ";
                        break;
                    case "V": dias[i]="Vi";
                    horarioMod += dias[i] + ", ";
                        break;
                    case "S": dias[i]="Sa";
                    horarioMod += dias[i] + ", ";
                        break;
                    case "D": dias[i]="Do";
                    horarioMod += dias[i];
                        break;    
                }
            }
            
            elem = new RptClasesPojo(actual.getIdCurso(),actual.getClTema(),actual.getClTipo(),actual.getClHora(),horarioMod,actual.getClEstado());
            this.getListDatos().add(elem);
        }
        this.setTotalClases("" + this.getListDatos().size());
        
        
    }

    public void generarReporte() {
        fill();
        String path = "resources/reportes/rtpClases.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        if(this.getCurso() == null){
            parameterMap.put("cursoClase", "TODOS");
        }else{
            parameterMap.put("cursoClase",this.getCurso().getCrNombre());
        }
        switch (this.getTipo()) {
            case 0:
                parameterMap.put("tipoClase", "TODOS");
                break;
            case 1:
                parameterMap.put("tipoClase", "Clase en vivo");
                break;
            case 2:
                parameterMap.put("tipoClase", "Video clase");
                break;
            case 3:
                parameterMap.put("tipoClase", "Clase interactiva");
                break;
        }
        switch(this.getEstadoC()){
            case 0:
                parameterMap.put("estadoClase", "TODOS");
                break;
            case 1:
                parameterMap.put("estadoClase", "No Iniciada");
                break;
            case 2:
                parameterMap.put("estadoClase","Iniciada");
                break;
            case 3:
                parameterMap.put("estadoClase", "Terminada");
                break;
            case 4:
                parameterMap.put("estadoClase","Eliminada");
                break;
        }
        try {
            this.generateReport(path, "rtpClases" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
            registrarEnBitacora("Reporte", "Clases", 0L);
        } catch (JRException ex) {
            Logger.getLogger("error 1").log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger("error 2").log(Level.SEVERE, null, ex);
        }
    }

    public void refresh() {
        fill();
    }

}
