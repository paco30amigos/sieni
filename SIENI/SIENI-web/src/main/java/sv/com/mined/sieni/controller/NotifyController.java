/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import sv.com.mined.sieni.model.SieniNotificacion;


@SessionScoped
@ManagedBean(name = "notifyController")
public class NotifyController implements Serializable {

    private Integer numNotify = 3;
    private SieniNotificacion notify = new SieniNotificacion();
    private static List<SieniNotificacion> lista = new ArrayList();

    @PostConstruct
    public void init() {
        this.setNumNotify(3);
    }
    public Integer getNumNotify() {
        return numNotify;
    }

    public void setNumNotify(Integer numNotify) {
        this.numNotify = numNotify;
    }

    
    public SieniNotificacion getNotify() {
        return notify;
    }

    public void setNotify(SieniNotificacion notify) {
        this.notify = notify;
    }

    public static List<SieniNotificacion> getLista() {
        return lista;
    }

    public static void setLista(List<SieniNotificacion> lista) {
        NotifyController.lista = lista;
    }

    

    public void agregar() {
        this.lista.add(notify);
        notificarPUSH();
    }

    public void notificarPUSH() {

        String summary = "Nuevo Elemento";
        String detail = "Se agrego a la lista";
        String CHANNEL = "/notify";

        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish(CHANNEL, new FacesMessage(summary, detail));
    }
}