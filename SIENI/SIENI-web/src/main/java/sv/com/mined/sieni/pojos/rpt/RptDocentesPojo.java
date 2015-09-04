/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import sv.com.mined.sieni.model.SieniDocente;

/**
 *
 * @author ever
 */
public class RptDocentesPojo {

    private SieniDocente docenteEntity;
    private String docente;
    private String fechaNacimiento;
    private String edad;
    private String direccion;
    private String telefono;
    private String gradoResponsable;

    public RptDocentesPojo() {
    }

    public RptDocentesPojo(SieniDocente docenteEntity, String docente, String fechaNacimiento, String edad, String direccion, String telefono, String gradoResponsable) {
        this.docenteEntity = docenteEntity;
        this.docente = docente;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.gradoResponsable = gradoResponsable;
    }

    public SieniDocente getDocenteEntity() {
        return docenteEntity;
    }

    public void setDocenteEntity(SieniDocente docenteEntity) {
        this.docenteEntity = docenteEntity;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGradoResponsable() {
        return gradoResponsable;
    }

    public void setGradoResponsable(String gradoResponsable) {
        this.gradoResponsable = gradoResponsable;
    }

}
