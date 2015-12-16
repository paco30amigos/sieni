/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import java.io.Serializable;
import sv.com.mined.sieni.model.AlumnoRecibeNoti;
import sv.com.mined.sieni.model.DocRecibeNoti;
import sv.com.mined.sieni.model.SieniNotificacion;
/**
 *
 * @author INFORMATICA
 */
public class NotificacionesPojo implements Serializable {
    private AlumnoRecibeNoti alumnoNotifyEntity;
    private DocRecibeNoti docenteNotifyEntity;
    
    private String usuario;
    private SieniNotificacion notify;
    private Boolean visto;

    public NotificacionesPojo() {
    }

    public NotificacionesPojo(DocRecibeNoti docenteNotifyEntity,AlumnoRecibeNoti alumnoNotifyEntity,  String usuario, SieniNotificacion notify, Boolean visto) {
        this.alumnoNotifyEntity = alumnoNotifyEntity;
        this.docenteNotifyEntity = docenteNotifyEntity;
        this.usuario = usuario;
        this.notify = notify;
        this.visto = visto;
    }

    public AlumnoRecibeNoti getAlumnoNotifyEntity() {
        return alumnoNotifyEntity;
    }

    public DocRecibeNoti getDocenteNotifyEntity() {
        return docenteNotifyEntity;
    }

    public String getUsuario() {
        return usuario;
    }

    public SieniNotificacion getNotify() {
        return notify;
    }

    public Boolean getVisto() {
        return visto;
    }

    
    
    
}
