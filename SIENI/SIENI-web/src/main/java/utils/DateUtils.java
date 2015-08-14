/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Laptop
 */
@ManagedBean
public class DateUtils {

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
        fechaMinima = getFormatDate("01/01/1980");
        return fechaMinima;
    }

    public Date getFechaMaxima() {
        Calendar actual = Calendar.getInstance();
        int anio = actual.get(Calendar.YEAR) + 1;//siguiente anio
        fechaMaxima = getFormatDate("31/12/" + anio);
        return fechaMaxima;
    }

    public Date getFormatDate(String fecha) {
        SimpleDateFormat dt1 = new SimpleDateFormat(formatoFecha);
        Date ret = null;
        if (fecha != null) {
            try {
                ret = dt1.parse(fecha);
            } catch (ParseException ex) {
                Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

    public String getFormatedDate(Date fecha) {
        SimpleDateFormat dt1 = new SimpleDateFormat(formatoFecha);
        String ret = null;
        if (fecha != null) {
            try {
                ret = dt1.format(fecha);
            } catch (Exception ex) {
                Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

    public Date getFechaFinAnioActual() {
        Calendar actual = Calendar.getInstance();
        int anio = actual.get(Calendar.YEAR);//siguiente anio
        fechaFinAnioActual = getFormatDate("31/12/" + anio);
        return fechaFinAnioActual;
    }

    public void setFechaFinAnioActual(Date fechaFinAnioActual) {
        this.fechaFinAnioActual = fechaFinAnioActual;
    }
}
