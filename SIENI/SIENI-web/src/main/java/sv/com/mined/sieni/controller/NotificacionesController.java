/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import sv.com.mined.sieni.SieniNotificacionFacadeRemote;
import sv.com.mined.sieni.form.NotificacionesForm;

/**
 *
 * @author INFORMATICA
 */
@SessionScoped
@ManagedBean(name = "notificacionesController")
public class NotificacionesController extends NotificacionesForm {
    
    @EJB
    private SieniNotificacionFacadeRemote sieniNotificacionFacadeRemote;
    
    
    @PostConstruct
    public void init() {
        
    }
    
    
    public void sendNotify(){  
        RequestContext context = RequestContext.getCurrentInstance();  
        context.addCallbackParam("numNotify", 3);  
        context.execute("sendNotify("+2+")");
    }
    
}
