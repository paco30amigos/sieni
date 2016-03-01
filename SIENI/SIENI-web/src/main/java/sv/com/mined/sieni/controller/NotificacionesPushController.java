/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import sv.com.mined.sieni.SieniNoticiaFacadeRemote;
import sv.com.mined.sieni.SieniTemaDudaFacadeRemote;
import sv.com.mined.sieni.form.NotificacionesForm;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;

/**
 *
 * @author INFORMATICA
 */
@ApplicationScoped
@ManagedBean(name = "notificacionesPushController")
public class NotificacionesPushController extends NotificacionesForm {

    @EJB
    private SieniTemaDudaFacadeRemote sieniTemaDudaFacadeRemote;
    @EJB
    private SieniNoticiaFacadeRemote sieniNoticiaFacadeRemote;

    @PostConstruct
    public void init() {

    }

    public void notificarPUSH() {
        try {
            String CHANNEL = "/notifyNotice";
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish(CHANNEL, new FacesMessage("Nueva Notificacion"));

        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }

    }
}
