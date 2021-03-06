/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.controller;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import org.primefaces.context.RequestContext;

/**
 *
 * @author francisco_medina
 */
public class ValidationPojo {

    //true cuando hay error y false cuando no hay
    private boolean valor;
    private String msg;
    private Severity Tipo;

    public ValidationPojo(boolean valor, String msg, Severity Tipo) {
        this.valor = valor;
        this.msg = msg;
        this.Tipo = Tipo;
    }

    public ValidationPojo() {
    }

    public boolean isValor() {
        return valor;
    }

    public void setValor(boolean valor) {
        this.valor = valor;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Severity getTipo() {
        return Tipo;
    }

    public void setTipo(Severity Tipo) {
        this.Tipo = Tipo;
    }

    public static boolean printErrores(List<ValidationPojo> errores) {
        boolean ret = false;
        for (ValidationPojo actual : errores) {
            if (actual.isValor()) {
                ret = true;
                actual.printMsj(actual.getMsg(), actual.getTipo());
            }
        }
        return ret;
    }

    public void printMsjSaved(String msj, Severity severity) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
//        flash.clear();
        flash.setKeepMessages(true);
//        flash.setRedirect(true);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage(severity, msj, " "));
        requestContext.execute("configMsg();");
    }

    public void printMsj(String msj, Severity severity) {
        FacesMessage msg = new FacesMessage(severity, msj, " ");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void printMsj(String msj, String descripcion, Severity severity) {
        FacesMessage msg = new FacesMessage(severity, msj, descripcion);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
