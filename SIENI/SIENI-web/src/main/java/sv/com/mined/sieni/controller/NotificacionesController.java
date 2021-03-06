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
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniNoticiaFacadeRemote;
import sv.com.mined.sieni.SieniNotificacionFacadeRemote;
import sv.com.mined.sieni.SieniResolDudaFacadeRemote;
import sv.com.mined.sieni.SieniTemaDudaFacadeRemote;
import sv.com.mined.sieni.form.NotificacionesForm;
import sv.com.mined.sieni.model.AlumnoRecibeNoti;
import sv.com.mined.sieni.model.DocRecibeNoti;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniNoticia;
import sv.com.mined.sieni.model.SieniNotificacion;
import sv.com.mined.sieni.model.SieniTemaDuda;
import sv.com.mined.sieni.model.SieniResolDuda;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import sv.com.mined.sieni.pojos.rpt.NotificacionesPojo;

/**
 *
 * @author INFORMATICA
 */
@RequestScoped
@ManagedBean(name = "notificacionesController")
public class NotificacionesController extends NotificacionesForm {

    @EJB
    private SieniNotificacionFacadeRemote sieniNotificacionFacadeRemote;
    @EJB
    private SieniTemaDudaFacadeRemote sieniTemaDudaFacadeRemote;
    @EJB
    private SieniResolDudaFacadeRemote sieniResolDudaFacadeRemote;
    @EJB
    private SieniNoticiaFacadeRemote sieniNoticiaFacadeRemote;
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

    public void obtenerNotifyUsuario() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");

        NotificacionesPojo elem = new NotificacionesPojo();
        this.setListNotificaciones(new ArrayList<NotificacionesPojo>());

        List<DocRecibeNoti> notifyDocente = new ArrayList<DocRecibeNoti>();
        List<AlumnoRecibeNoti> notifyAlumno = new ArrayList<AlumnoRecibeNoti>();
        if (loginBean.getDocente() != null) {
            notifyDocente = sieniNotificacionFacadeRemote.findDocenteNotify(loginBean.getDocente().getIdDocente().intValue());
        } else if (loginBean.getAlumno() != null) {
            notifyAlumno = sieniNotificacionFacadeRemote.findAlumnoNotify(loginBean.getAlumno().getIdAlumno().intValue());
        }
        for (DocRecibeNoti actual : notifyDocente) {
            SieniDocente d = sieniDocenteFacadeRemote.findByDocenteId(actual.getDocRecibeNotiPK().getIdDocente());
            elem = new NotificacionesPojo(actual, null, d.getNombreCompleto(), actual.getIdNotificacion(), actual.getNotiVisto());
            this.getListNotificaciones().add(elem);
        }
        for (AlumnoRecibeNoti actual : notifyAlumno) {
            SieniAlumno alumno = sieniAlumnoFacadeRemote.findAlumnoById(actual.getAlumnoRecibeNotiPK().getIdAlumno());
            elem = new NotificacionesPojo(null, actual, alumno.getNombreCompleto(), actual.getIdNotificacion(), actual.getNotiVisto());
            this.getListNotificaciones().add(elem);
        }
        count = this.getListNotificaciones().size();
    }

    
    public void IrNotifyOrigen(SieniNotificacion notify) {
        try {
            FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
            switch (notify.getNfOrigen()) {
                case "sieni_tema_duda":
                    GestionarConsultasController consultasBean = (GestionarConsultasController) context.getApplication().getELResolver().getValue(context.getELContext(), null, "gestionarConsultasController");
                    notify.setNfEstado('I');
                    sieniNotificacionFacadeRemote.edit(notify);
                    SieniTemaDuda duda = sieniTemaDudaFacadeRemote.find(notify.getNfKey());
                    consultasBean.ver(duda);
                    break;
                case "sieni_resol_duda":
                    GestionarConsultasController respuestaBean = (GestionarConsultasController) context.getApplication().getELResolver().getValue(context.getELContext(), null, "gestionarConsultasController");
                    notify.setNfEstado('I');
                    sieniNotificacionFacadeRemote.edit(notify);
                    SieniResolDuda respuesta = sieniResolDudaFacadeRemote.find(notify.getNfKey());
                    respuestaBean.ver(respuesta.getIdTemaDuda());
                    break;
                case "sieni_noticia":
                    GestionarNoticiasController noticiasBean = (GestionarNoticiasController) context.getApplication().getELResolver().getValue(context.getELContext(), null, "gestionarNoticiasController");
                    notify.setNfEstado('I');
                    sieniNotificacionFacadeRemote.edit(notify);
                    SieniNoticia noticia = sieniNoticiaFacadeRemote.find(notify.getNfKey());
                    noticiasBean.ver(noticia);
                    break;
            }

        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }
    
    
    
    public void notificarPUSH(String mensaje) {
        try {
            String CHANNEL = "/notify";
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish(CHANNEL, new FacesMessage(mensaje));

        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }

    }
}
