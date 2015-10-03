/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.controller.*;
import sv.com.mined.sieni.pojos.rpt.RptUsuariosPojo;

/**
 *
 * @author PD08004
 */
public class RptUsuariosForm extends ReportesController{
    
    private Integer tipoUser;
    private String estadoUser;
    private List<RptUsuariosPojo> listDatos;
    private String totalUsuarios;
    private Integer tipoRpt;

    public Integer getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(Integer tipoUser) {
        this.tipoUser = tipoUser;
    }

    public List<RptUsuariosPojo> getListDatos() {
        return listDatos;
    }

    public void setListDatos(List<RptUsuariosPojo> listDatos) {
        this.listDatos = listDatos;
    }

    public String getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(String totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }

    public Integer getTipoRpt() {
        return tipoRpt;
    }

    public void setTipoRpt(Integer tipoRpt) {
        this.tipoRpt = tipoRpt;
    }

    public String getEstadoUser() {
        return estadoUser;
    }

    public void setEstadoUser(String estadoUser) {
        this.estadoUser = estadoUser;
    }
    
    
}
