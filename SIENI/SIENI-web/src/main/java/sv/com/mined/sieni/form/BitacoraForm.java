/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.Date;
import java.util.List;
import org.primefaces.model.StreamedContent;
import sv.com.mined.sieni.controller.ReportesController;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.pojos.rpt.BitacoraPojo;

/**
 *
 * @author Laptop
 */
public class BitacoraForm extends ReportesController{

    private List<SieniBitacora> bitacoraList;
    private StreamedContent reporte;
    private Long totalTransacciones;
    private String formatoRpt;
    private Date desde;
    private Date hasta;
    
    private List<BitacoraPojo> listDatos;
    private Integer tipoRpt;

    public List<SieniBitacora> getBitacoraList() {
        return bitacoraList;
    }

    public void setBitacoraList(List<SieniBitacora> bitacoraList) {
        this.bitacoraList = bitacoraList;
    }

    public StreamedContent getReporte() {
        return reporte;
    }

    public void setReporte(StreamedContent reporte) {
        this.reporte = reporte;
    }

    public Long getTotalTransacciones() {
        return totalTransacciones;
    }

    public void setTotalTransacciones(Long totalTransacciones) {
        this.totalTransacciones = totalTransacciones;
    }

    public String getFormatoRpt() {
        return formatoRpt;
    }

    public void setFormatoRpt(String formatoRpt) {
        this.formatoRpt = formatoRpt;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public List<BitacoraPojo> getListDatos() {
        return listDatos;
    }

    public void setListDatos(List<BitacoraPojo> listDatos) {
        this.listDatos = listDatos;
    }

    public Integer getTipoRpt() {
        return tipoRpt;
    }

    public void setTipoRpt(Integer tipoRpt) {
        this.tipoRpt = tipoRpt;
    }

}
