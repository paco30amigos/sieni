/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.controller.ReportesController;
import sv.com.mined.sieni.model.SieniNotificacion;
import sv.com.mined.sieni.pojos.rpt.NotificacionesPojo;
import utils.FormatUtils;


/**
 *
 * @author INFORMATICA
 */
public class NotificacionesForm extends ReportesController{
    private int numNoty;
    private List<NotificacionesPojo> listNotificaciones;

    public int getNumNoty() {
        return numNoty;
    }

    public void setNumNoty(int numNoty) {
        this.numNoty = numNoty;
    }

    public List<NotificacionesPojo> getListNotificaciones() {
        return listNotificaciones;
    }

    public void setListNotificaciones(List<NotificacionesPojo> listNotificaciones) {
        this.listNotificaciones = listNotificaciones;
    }

    
    
    
}
