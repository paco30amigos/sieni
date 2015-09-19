/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.Date;
import java.util.List;
import sv.com.mined.sieni.controller.ReportesController;
import sv.com.mined.sieni.pojos.rpt.RptCursosPojo;

/**
 *
 * @author ever
 */
public class RptCursosForm extends ReportesController{
    private List<RptCursosPojo> listDatos;
     private Integer tipoRpt;
     private Date desde;
     private Date hasta;

    public List<RptCursosPojo> getListDatos() {
        return listDatos;
    }

    public void setListDatos(List<RptCursosPojo> listDatos) {
        this.listDatos = listDatos;
    }

    public Integer getTipoRpt() {
        return tipoRpt;
    }

    public void setTipoRpt(Integer tipoRpt) {
        this.tipoRpt = tipoRpt;
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
     
     
    
}
