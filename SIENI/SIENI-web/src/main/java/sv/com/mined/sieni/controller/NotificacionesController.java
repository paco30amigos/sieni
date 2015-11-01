/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniNotificacionFacadeRemote;
import sv.com.mined.sieni.form.NotificacionesForm;
import sv.com.mined.sieni.model.SieniNoticia;
import sv.com.mined.sieni.model.SieniNotificacion;
import utils.DateUtils;

/**
 *
 * @author INFORMATICA
 */

@SessionScoped
@ManagedBean(name = "notificacionesController")
public class NotificacionesController extends NotificacionesForm {
    
    @EJB
    private SieniNotificacionFacadeRemote sieniNotificacionFacadeRemote;
    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    
    
    @PostConstruct
    public void init() {
        this.setNumNoty(0);
        this.setCount(0);
        obtenerNotifyUsuario();
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
    
    
    public void obtenerNotifyUsuario()
    {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        
        List<SieniNotificacion> notify = new ArrayList<SieniNotificacion>();
        if(loginBean.getDocente()!= null){
            notify = sieniNotificacionFacadeRemote.findDocenteNotify(loginBean.getDocente().getIdDocente().intValue());
        this.setListNotificaciones(notify);
        }else if(loginBean.getAlumno() != null){
            notify = sieniNotificacionFacadeRemote.findAlumnoNotify(loginBean.getAlumno().getIdAlumno().intValue());
        }
        this.setListNotificaciones(notify);
        this.setCount(this.getListNotificaciones().size());
    }
    
    
    
    
    public void notificarPUSH() {
        String CHANNEL = "/notifyNotice";
        increment();
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish(CHANNEL, String.valueOf(count));
        
        
    }
    
    public void mensageFaces(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,"MENSAJE","SI SE"));
        context.execute("agrandar();");
    }
    
    
    public void insertNotifyNoticia(SieniNoticia noticia){
        SieniNotificacion noty = new SieniNotificacion();
        noty.setNfEstado('A');
        noty.setNfFechaIngreso(new DateUtils().getFechaActual());
        noty.setNfFechaFin(new DateUtils().getFechaActual());
        noty.setNfMensaje("Nueva Noticia: " + noticia.getNcMensaje() + "Publicado por: " + noticia.getNcPublica());
        sieniNotificacionFacadeRemote.create(noty);
        obtenerNotifyUsuario();
        notificarPUSH();
    }
}
