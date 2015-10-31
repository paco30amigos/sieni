/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.controller.ReportesController;
import sv.com.mined.sieni.model.SieniNotificacion;
import utils.FormatUtils;


/**
 *
 * @author INFORMATICA
 */
public class NotificacionesForm extends ReportesController{
    private int numNoty;
    private List<SieniNotificacion> listNotificaciones;

    public int getNumNoty() {
        return numNoty;
    }

    public void setNumNoty(int numNoty) {
        this.numNoty = numNoty;
    }

    public List<SieniNotificacion> getListNotificaciones() {
        return listNotificaciones;
    }

    public void setListNotificaciones(List<SieniNotificacion> listNotificaciones) {
        this.listNotificaciones = listNotificaciones;
    }

    
    
    
}
