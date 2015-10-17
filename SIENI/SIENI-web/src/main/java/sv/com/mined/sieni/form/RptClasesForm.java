/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.controller.ReportesController;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.pojos.rpt.RptClasesPojo;

/**
 *
 * @author INFORMATICA
 */
public class RptClasesForm extends ReportesController {
    
    private Integer tipoC;
    private Integer estadoC;
    private Integer idCurso;
    
    private SieniCurso curso;
    private List<SieniCurso> listCursos;
    private List<RptClasesPojo> listDatos;
    private String totalClases;
    private Integer tipoRpt;

    public SieniCurso getCurso() {
        return curso;
    }

    public void setCurso(SieniCurso curso) {
        this.curso = curso;
    }

    

    
    
    public List<SieniCurso> getListCursos() {
        return listCursos;
    }

    public void setListCursos(List<SieniCurso> listCursos) {
        this.listCursos = listCursos;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    
    
    
    public Integer getTipoC() {
        return tipoC;
    }

    public void setTipoC(Integer tipoC) {
        this.tipoC = tipoC;
    }

    public Integer getEstadoC() {
        return estadoC;
    }

    public void setEstadoC(Integer estadoC) {
        this.estadoC = estadoC;
    }

    
    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public List<RptClasesPojo> getListDatos() {
        return listDatos;
    }

    public void setListDatos(List<RptClasesPojo> listDatos) {
        this.listDatos = listDatos;
    }

    public String getTotalClases() {
        return totalClases;
    }

    public void setTotalClases(String totalClases) {
        this.totalClases = totalClases;
    }

    public Integer getTipoRpt() {
        return tipoRpt;
    }

    public void setTipoRpt(Integer tipoRpt) {
        this.tipoRpt = tipoRpt;
    }
    
    
    
}
