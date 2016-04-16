/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import java.io.Serializable;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniSeccion;

/**
 *
 * @author INFORMATICA
 */
public class RptEstadisticoAvancePojo implements Serializable {

    private SieniClase claseEntity;
    private SieniAlumno alumnoEntity;

    private String materia;
    private String clase;
    private String tipo;
    private Integer total;
    private Integer acumulado;
    private String porcentaje;

    public RptEstadisticoAvancePojo() {
    }

    public RptEstadisticoAvancePojo(SieniClase claseEntity, SieniAlumno alumnoEntity, String materia, String clase, String tipo, Integer total, Integer acumulado, String porcentaje) {
        this.claseEntity = claseEntity;
        this.alumnoEntity = alumnoEntity;
        this.clase = clase;
        this.materia = materia;
        this.tipo = tipo;
        this.total = total;
        this.acumulado = acumulado;
        this.porcentaje = porcentaje;
    }

    public SieniClase getClaseEntity() {
        return claseEntity;
    }

    public SieniAlumno getAlumnoEntity() {
        return alumnoEntity;
    }

    public String getClase() {
        return clase;
    }

    public String getMateria() {
        return materia;
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getAcumulado() {
        return acumulado;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

}
