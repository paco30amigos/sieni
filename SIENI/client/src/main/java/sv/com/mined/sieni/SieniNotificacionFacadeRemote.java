/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.AlumnoRecibeNoti;
import sv.com.mined.sieni.model.DocRecibeNoti;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniNotificacion;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniNotificacionFacadeRemote {

    void create(SieniNotificacion sieniNotificacion);

    void edit(SieniNotificacion sieniNotificacion);

    void remove(SieniNotificacion sieniNotificacion);

    SieniNotificacion find(Object id);

    List<SieniNotificacion> findAll();

    List<SieniNotificacion> findRange(int[] range);

    int count();
    
    SieniNotificacion createAndReturn(SieniNotificacion notify);
    
    public List<DocRecibeNoti> findDocenteNotify(Integer iddocente);
    
    public List<AlumnoRecibeNoti> findAlumnoNotify(Integer idalumno);
}
