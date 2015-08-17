/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Calendar;
import java.util.Date;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Laptop
 */
@ManagedBean
public class DateUtils {

    FormatUtils format = new FormatUtils();
    String formatoFecha = "dd/MM/yyyy";
    private Date fechaActual;
    private Date fechaFinAnioActual;
    private Date fechaMinima;
    private Date fechaMaxima;

    public Date getFechaActual() {
        fechaActual = new Date();
        return fechaActual;
    }

    public Date getFechaMinima() {
        fechaMinima = format.getFormatDate("01/01/1980");
        return fechaMinima;
    }

    public Date getFechaMaxima() {
        Calendar actual = Calendar.getInstance();
        int anio = actual.get(Calendar.YEAR) + 1;//siguiente anio
        fechaMaxima = format.getFormatDate("31/12/" + anio);
        return fechaMaxima;
    }

    public Date getFechaFinAnioActual() {
        Calendar actual = Calendar.getInstance();
        int anio = actual.get(Calendar.YEAR);//siguiente anio
        fechaFinAnioActual = format.getFormatDate("31/12/" + anio);
        return fechaFinAnioActual;
    }

    public void setFechaFinAnioActual(Date fechaFinAnioActual) {
        this.fechaFinAnioActual = fechaFinAnioActual;
    }
}
