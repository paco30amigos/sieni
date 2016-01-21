/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
    private String formatoHora = "hh:mm a";

    public String getDayOfWeek(Date fechaActual) {
        String ret = "";
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        fechaCalendario.setTime(fechaActual);
        int diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK);
        switch (diaSemana) {
            case 1:
                ret = "D";
                break;
            case 2:
                ret = "L";
                break;
            case 3:
                ret = "M";
                break;
            case 4:
                ret = "X";
                break;
            case 5:
                ret = "J";
                break;
            case 6:
                ret = "V";
                break;
            case 7:
                ret = "S";
                break;
        }
        return ret;
    }

    public String getNameDayOfWeek(Date fechaActual) {
        String ret = "";
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        fechaCalendario.setTime(fechaActual);
        String diaSemana = getDayOfWeek(fechaActual);
        ret = getNameDay(diaSemana);
        return ret;
    }

    public String getAllNamesOfWeek(String dias) {
        String ret = "";
        String[] diasSemana;
        boolean inicio = true;
        if (dias != null) {
            diasSemana = dias.split(",");
            for (String dia : diasSemana) {
                if (inicio) {
                    ret += getNameDay(dia);
                } else {
                    ret += ", " + getNameDay(dia);
                }
                inicio = false;
            }
        }
        return ret;
    }

    public List<String> getAllNamesOfWeekList(String dias) {
        List<String> ret = new ArrayList<>();
        String[] diasSemana;
        if (dias != null) {
            diasSemana = dias.split(",");
            ret.addAll(Arrays.asList(diasSemana));
        }
        return ret;
    }

    public String getNameDay(String diaSemana) {
        String ret = "";
        switch (diaSemana) {
            case "D":
                ret = "Domingo";
                break;
            case "L":
                ret = "Lunes";
                break;
            case "M":
                ret = "Martes";
                break;
            case "X":
                ret = "Miercoles";
                break;
            case "J":
                ret = "Jueves";
                break;
            case "V":
                ret = "Viernes";
                break;
            case "S":
                ret = "Sabado";
                break;
        }
        return ret;
    }

    public boolean horarioValido(String horario, Date horaProg) {
        DateUtils du = new DateUtils();
        boolean ban = false;
        String[] diasHorario = horario.split(",");
        Date hora = new Date();
        for (String actual : diasHorario) {
            if (actual.equals(du.getDayOfWeek(hora))) {
                ban = true;
            }
        }
        if (ban) {
            GregorianCalendar fechaCalendarioClase = new GregorianCalendar();
            fechaCalendarioClase.setTime(horaProg);
            GregorianCalendar fechaCalendarioActual = new GregorianCalendar();
            fechaCalendarioActual.setTime(hora);

            int horaClase = fechaCalendarioClase.get(GregorianCalendar.HOUR);
            int minClase = fechaCalendarioClase.get(GregorianCalendar.MINUTE);
            int segClase = fechaCalendarioClase.get(GregorianCalendar.SECOND);
            int horaActual = fechaCalendarioActual.get(GregorianCalendar.HOUR);
            int minActual = fechaCalendarioActual.get(GregorianCalendar.MINUTE);
            int segActual = fechaCalendarioActual.get(GregorianCalendar.SECOND);

            if (horaActual > horaClase) {
                ban = true;
            } else if (horaActual == horaClase) {
                if (minActual > minClase) {
                    ban = true;
                } else if (minActual == minClase) {
                    if (segActual > segClase) {
                        ban = true;
                    }
                }
            }
        }
        return ban;
    }

    public String getEdad(Date edad) {
        String ret = "";
        String anioNacimiento = format.getFormatedAnio(edad);
        String anioActual = format.getFormatedAnio(new Date());

        Integer anioNacimientoInt = Integer.parseInt(anioNacimiento);
        Integer anioActualInt = Integer.parseInt(anioActual);
        Integer anio = anioActualInt - anioNacimientoInt;

        String cumpleActual = format.getFormatedDate(edad, "dd/MM/") + anioActual;
        Date cumpleAniosActual = format.getFormatDate(cumpleActual);

        if (cumpleAniosActual.after(new Date())) {
            anio++;
        }
        ret = anio.toString();
        return ret;
    }

    public Date getFechaActual() {
        fechaActual = new Date();
        return fechaActual;
    }

    public Date getFechaMinima() {
        fechaMinima = format.getFormatDate("01/01/1980");
        return fechaMinima;
    }

    public Date getFechaMinimaDocente() {
        fechaMinima = format.getFormatDate("01/01/1900");
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

    public String getFormatoFecha(Date fecha) {
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        String fechaFormato = "";
        if (fecha != null) {
            fechaFormato = sdf.format(fecha);
        }
        return fechaFormato;
    }

    public String getFormatoHora(Date hora) {

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(formatoHora);
        String fechaFormato = sdf.format(hora);
        return fechaFormato;
    }

    public void setFechaFinAnioActual(Date fechaFinAnioActual) {
        this.fechaFinAnioActual = fechaFinAnioActual;
    }

    public String getTime() {
        Long time = new Date().getTime();
        return time.toString();
    }

    public Date addDate(Date fecha, Integer dias) {
        Date ret = null;
        if (fecha != null && dias != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(fecha); //
            c.add(Calendar.DATE, dias); // suma 30 dias de vencimiento
            ret = c.getTime();
        }
        return ret;
    }

    public Integer getDiasAntesVencimientoContra(Date fecha, Integer dias) {
        Date d2 = new Date(), d1 = addDate(fecha, dias);
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));

    }

}
