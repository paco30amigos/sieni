/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author alejandro.gonzalez
 */
public class BitacoraPojo implements Serializable{
    
    private String usuario;
    private String tipoPerfil;
    private String accion;
    private Date fecha;
    private Date hora;
    private String fuente;
    private String responsable;

    public BitacoraPojo() {
    }

    public BitacoraPojo(String usuario, String tipoPerfil, String accion, Date fecha, Date hora, String fuente, String responsable) {
        this.usuario = usuario;
        this.tipoPerfil = tipoPerfil;
        this.accion = accion;
        this.fecha = fecha;
        this.hora = hora;
        this.fuente = fuente;
        this.responsable = responsable;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }
    
    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
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
