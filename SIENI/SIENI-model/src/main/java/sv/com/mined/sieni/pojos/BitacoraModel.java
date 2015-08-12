/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos;

import java.util.Date;

/**
 *
 * @author Laptop
 */
public class BitacoraModel {

    private Long idBitacora;//id del objeto alterado
    private String transaccion;//id del objeto alterado
    private Date bitFechaHora;//fecha y hora de modificacion
    private String bitAccion;//eliminacion
    private String bitTabla;//alumno
    private Long bitIdUsuario;//profesor felipe
    private String bitTipoUsuario;//docente

    public Date getBitFechaHora() {
        return bitFechaHora;
    }

    public void setBitFechaHora(Date bitFechaHora) {
        this.bitFechaHora = bitFechaHora;
    }

    public String getBitAccion() {
        return bitAccion;
    }

    public void setBitAccion(String bitAccion) {
        this.bitAccion = bitAccion;
    }

    public String getBitTabla() {
        return bitTabla;
    }

    public void setBitTabla(String bitTabla) {
        this.bitTabla = bitTabla;
    }

    public Long getBitIdUsuario() {
        return bitIdUsuario;
    }

    public void setBitIdUsuario(Long bitIdUsuario) {
        this.bitIdUsuario = bitIdUsuario;
    }

    public String getBitTipoUsuario() {
        return bitTipoUsuario;
    }

    public void setBitTipoUsuario(String bitTipoUsuario) {
        this.bitTipoUsuario = bitTipoUsuario;
    }

    public Long getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Long idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

}
