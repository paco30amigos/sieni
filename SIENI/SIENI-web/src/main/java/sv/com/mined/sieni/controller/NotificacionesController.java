/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import sv.com.mined.sieni.SieniNotificacionFacadeRemote;
import sv.com.mined.sieni.form.NotificacionesForm;

/**
 *
 * @author INFORMATICA
 */

@ApplicationScoped
@ManagedBean(name = "notificacionesController")
public class NotificacionesController extends NotificacionesForm implements Serializable{
    
    @EJB
    private SieniNotificacionFacadeRemote sieniNotificacionFacadeRemote;
    
    
    @PostConstruct
    public void init() {
        this.setNumNoty(0);
    }
    
    private volatile int count;
 
    public int getCount() {
        return count;
    }
 
    public void setCount(int count) {
        this.count = count;
    }
     
    public void increment() {
        count++;
    }
    
    public void notificarPUSH() {
        String CHANNEL = "/notify";
        increment();
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish(CHANNEL, String.valueOf(count));
        
        //context.execute("agrandar();");
    }
    
    public void mensageFaces(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,"MENSAJE","SI SE"));
        context.execute("agrandar();");
    }
    
}
