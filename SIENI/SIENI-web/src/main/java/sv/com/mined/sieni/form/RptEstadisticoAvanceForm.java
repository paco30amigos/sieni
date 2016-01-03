/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.controller.ReportesController;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniSeccion;
import sv.com.mined.sieni.pojos.rpt.RptEstadisticoAvancePojo;

/**
 *
 * @author INFORMATICA
 */
public class RptEstadisticoAvanceForm extends ReportesController {
    
    private Integer idgrado;
    private Integer idseccion;
    private Integer idmateria;
    private Long idalumno;
    
    private SieniGrado grado;
    private SieniSeccion seccion;
    private SieniMateria materia;
    private SieniAlumno alumno;
    
    private List<SieniGrado> listGrados;
    private List<SieniSeccion> listSecciones;
    private List<SieniMateria> listMaterias;
    private List<SieniAlumno> listAlumnos;
    
    private List<RptEstadisticoAvancePojo> listDatos;
    private String totalAlumnos;
    private Integer tipoRpt;

    public Integer getIdgrado() {
        return idgrado;
    }

    public void setIdgrado(Integer idgrado) {
        this.idgrado = idgrado;
    }

    public Integer getIdseccion() {
        return idseccion;
    }

    public void setIdseccion(Integer idseccion) {
        this.idseccion = idseccion;
    }

    public Integer getIdmateria() {
        return idmateria;
    }

    public void setIdmateria(Integer idmateria) {
        this.idmateria = idmateria;
    }

    public Long getIdalumno() {
        return idalumno;
    }

    public void setIdalumno(Long idalumno) {
        this.idalumno = idalumno;
    }

    public SieniGrado getGrado() {
        return grado;
    }

    public void setGrado(SieniGrado grado) {
        this.grado = grado;
    }

    public SieniSeccion getSeccion() {
        return seccion;
    }

    public void setSeccion(SieniSeccion seccion) {
        this.seccion = seccion;
    }

    public SieniMateria getMateria() {
        return materia;
    }

    public void setMateria(SieniMateria materia) {
        this.materia = materia;
    }

    public SieniAlumno getAlumno() {
        return alumno;
    }

    public void setAlumno(SieniAlumno alumno) {
        this.alumno = alumno;
    }

    public List<SieniGrado> getListGrados() {
        return listGrados;
    }

    public void setListGrados(List<SieniGrado> listGrados) {
        this.listGrados = listGrados;
    }

    public List<SieniSeccion> getListSecciones() {
        return listSecciones;
    }

    public void setListSecciones(List<SieniSeccion> listSecciones) {
        this.listSecciones = listSecciones;
    }

    public List<SieniMateria> getListMaterias() {
        return listMaterias;
    }

    public void setListMaterias(List<SieniMateria> listMaterias) {
        this.listMaterias = listMaterias;
    }

    public List<SieniAlumno> getListAlumnos() {
        return listAlumnos;
    }

    public void setListAlumnos(List<SieniAlumno> listAlumnos) {
        this.listAlumnos = listAlumnos;
    }

    public List<RptEstadisticoAvancePojo> getListDatos() {
        return listDatos;
    }

    public void setListDatos(List<RptEstadisticoAvancePojo> listDatos) {
        this.listDatos = listDatos;
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

    
    
    
}
