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
import sv.com.mined.sieni.model.SieniNotasModHist;
import sv.com.mined.sieni.pojos.rpt.BitacoraPojo;
import sv.com.mined.sieni.pojos.rpt.RptNotasModificadasPojo;

/**
 *
 * @author Laptop
 */
public class RptNotasModificadasForm extends ReportesController {

    private List<SieniNotasModHist> bitacoraList;
    private StreamedContent reporte;
    private Long totalTransacciones;
    private String formatoRpt;
    private Date desde;
    private Date hasta;

    private List<RptNotasModificadasPojo> listDatos;
    private List<RptNotasModificadasPojo> listDatosFiltered;
    private Integer tipoRpt;

    public List<SieniNotasModHist> getBitacoraList() {
        return bitacoraList;
    }

    public void setBitacoraList(List<SieniNotasModHist> bitacoraList) {
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

    public List<RptNotasModificadasPojo> getListDatos() {
        return listDatos;
    }

    public void setListDatos(List<RptNotasModificadasPojo> listDatos) {
        this.listDatos = listDatos;
    }

    public List<RptNotasModificadasPojo> getListDatosFiltered() {
        return listDatosFiltered;
    }

    public void setListDatosFiltered(List<RptNotasModificadasPojo> listDatosFiltered) {
        this.listDatosFiltered = listDatosFiltered;
    }

    public Integer getTipoRpt() {
        return tipoRpt;
    }

    public void setTipoRpt(Integer tipoRpt) {
        this.tipoRpt = tipoRpt;
    }

}
