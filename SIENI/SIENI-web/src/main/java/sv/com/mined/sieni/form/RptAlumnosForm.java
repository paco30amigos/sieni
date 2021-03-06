/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.Date;
import java.util.List;
import sv.com.mined.sieni.controller.*;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniSeccion;
import sv.com.mined.sieni.pojos.rpt.RptAlumnosPojo;

/**
 *
 * @author Laptop
 */
public class RptAlumnosForm extends ReportesController {

    private Date anioDesde;
    private Date anioHasta;
    private Long idGrado;
    private Long idSeccion;
    private String grado;
    private String seccion;
    private List<SieniGrado> gradosList;
    private List<SieniSeccion> seccionesList;
    private List<RptAlumnosPojo> listDatos;
    private String totalAlumnos;
    private Integer tipoRpt;
    private Integer matriculado;

    public Long getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(Long idGrado) {
        this.idGrado = idGrado;
    }

    public Long getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Long idSeccion) {
        this.idSeccion = idSeccion;
    }

    public List<SieniGrado> getGradosList() {
        return gradosList;
    }

    public void setGradosList(List<SieniGrado> gradosList) {
        this.gradosList = gradosList;
    }

    public List<SieniSeccion> getSeccionesList() {
        return seccionesList;
    }

    public void setSeccionesList(List<SieniSeccion> seccionesList) {
        this.seccionesList = seccionesList;
    }

    public List<RptAlumnosPojo> getListDatos() {
        return listDatos;
    }

    public void setListDatos(List<RptAlumnosPojo> listDatos) {
        this.listDatos = listDatos;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getTotalAlumnos() {
        return totalAlumnos;
    }

    public void setTotalAlumnos(String totalAlumnos) {
        this.totalAlumnos = totalAlumnos;
    }

    public Integer getTipoRpt() {
        return tipoRpt;
    }

    public void setTipoRpt(Integer tipoRpt) {
        this.tipoRpt = tipoRpt;
    }

    public Date getAnioDesde() {
        return anioDesde;
    }

    public void setAnioDesde(Date anioDesde) {
        this.anioDesde = anioDesde;
    }

    public Date getAnioHasta() {
        return anioHasta;
    }

    public void setAnioHasta(Date anioHasta) {
        this.anioHasta = anioHasta;
    }

    public Integer getMatriculado() {
        return matriculado;
    }

    public void setMatriculado(Integer matriculado) {
        this.matriculado = matriculado;
    }

}
