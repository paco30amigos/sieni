/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author francisco_medina
 */
@ManagedBean
public class formatoPantallas {

    private String campo50 = "XX--50--XX";
    private String campo30 = "XX--30--XX";
    private String campo15 = "XX--15--XX";
    private String campo2 = "XX--2--XX";
    private String numeroDecimal = "99.99";
    private String numero3 = "999";
    private String numero2 = "9";
    private String anio = "9,999";
    private String fecha = "99/99/9999";
    private String fechaHora = "99/99/9999 99:99 Xm";
    private String campo4000 = "XX--4000--XX";
    private String campo100 = "XX--100--XX";
    private String campo200 = "XX--200--XX";
    private String campo300 = "XX--300--XX";
    private String telefono = "(999) 9999-9999";
    private String Carnet = "XX99999";
    private String campoInfinito = "XX----XX";

    public String getCampo50() {
        return campo50;
    }

    public String getCampo30() {
        return campo30;
    }

    public String getCampo15() {
        return campo15;
    }

    public String getNumeroDecimal() {
        return numeroDecimal;
    }

    public String getAnio() {
        return anio;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCampo4000() {
        return campo4000;
    }

    public String getCampo100() {
        return campo100;
    }

    public String getCampo200() {
        return campo200;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCampo300() {
        return campo300;
    }

    public String getCarnet() {
        return Carnet;
    }

    public String getNumero3() {
        return numero3;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public String getNumero2() {
        return numero2;
    }

    public String getCampo2() {
        return campo2;
    }

    public String getCampoInfinito() {
        return campoInfinito;
    }

}
