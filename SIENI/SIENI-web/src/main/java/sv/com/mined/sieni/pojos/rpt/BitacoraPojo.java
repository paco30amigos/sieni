/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import java.io.Serializable;

/**
 *
 * @author alejandro.gonzalez
 */
public class BitacoraPojo implements Serializable{
    
    private String accion;
    private String fecha;
    private String hora;
    private String fuente;
    private String responsable;

    public BitacoraPojo() {
    }

    public BitacoraPojo(String accion, String fecha, String hora, String fuente, String responsable) {
        this.accion = accion;
        this.fecha = fecha;
        this.hora = hora;
        this.fuente = fuente;
        this.responsable = responsable;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    
}
