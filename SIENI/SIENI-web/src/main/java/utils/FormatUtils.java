/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Laptop
 */
@ManagedBean
public class FormatUtils {

    private String formatoTelefono = "####-####";
    private String formatoNumero = "###,###,###.##";
    private String formatoFecha = "dd/MM/yyyy";
    private String formatoAnio = "yyyy";

    public String getFormatedAnio(Date fecha) {
        SimpleDateFormat dt1 = new SimpleDateFormat(formatoAnio);
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

    public Date getFormatDate(String fecha, String formatoFecha) {
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

    public String getFormatedDate(Date fecha, String formatoFecha) {
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

    public String getFormatedNumber(Double numero) {
        DecimalFormat formateador = new DecimalFormat(formatoNumero);
        return formateador.format(numero);
    }

    public String getFormatedPhone(String telefono) {
        String formato[] = formatoTelefono.split("-");
        String ret = "";
        int sigPatron = 0;
        int antPatron = 0;
        if (telefono != null && !telefono.isEmpty()) {
            for (String actual : formato) {
                sigPatron += actual.length();
                ret += telefono.substring(antPatron, sigPatron) + "-";
                antPatron += sigPatron;
            }
            ret = ret.substring(0, (ret.length() - 1));
        }
        return ret;
    }

    public String getFormatoTelefono() {
        return formatoTelefono;
    }

    public void setFormatoTelefono(String formatoTelefono) {
        this.formatoTelefono = formatoTelefono;
    }

    public String getFormatoNumero() {
        return formatoNumero;
    }

    public void setFormatoNumero(String formatoNumero) {
        this.formatoNumero = formatoNumero;
    }

    public String getFormatoFecha() {
        return formatoFecha;
    }

    public void setFormatoFecha(String formatoFecha) {
        this.formatoFecha = formatoFecha;
    }
}