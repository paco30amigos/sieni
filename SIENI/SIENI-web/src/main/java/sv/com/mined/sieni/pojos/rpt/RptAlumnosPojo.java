/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import java.io.Serializable;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniGrado;

/**
 *
 * @author Laptop
 */
public class RptAlumnosPojo implements Serializable {

    private SieniAlumno alumnoEntity;
    private SieniGrado gradoActualEntity;
    private String alumno;
    private String fechaNacimiento;
    private String edad;
    private String direccion;
    private String telefono;
    private String gradoActual;

    public RptAlumnosPojo() {
    }

    public RptAlumnosPojo(SieniAlumno alumnoEntity, SieniGrado gradoActualEntity, String alumno, String fechaNacimiento, String edad, String direccion, String telefono, String gradoActual) {
        this.alumnoEntity = alumnoEntity;
        this.gradoActualEntity = gradoActualEntity;
        this.alumno = alumno;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.gradoActual = gradoActual;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
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

    public String getGradoActual() {
        return gradoActual;
    }

    public void setGradoActual(String gradoActual) {
        this.gradoActual = gradoActual;
    }

    public SieniAlumno getAlumnoEntity() {
        return alumnoEntity;
    }

    public void setAlumnoEntity(SieniAlumno alumnoEntity) {
        this.alumnoEntity = alumnoEntity;
    }

    public SieniGrado getGradoActualEntity() {
        return gradoActualEntity;
    }

    public void setGradoActualEntity(SieniGrado gradoActualEntity) {
        this.gradoActualEntity = gradoActualEntity;
    }

}