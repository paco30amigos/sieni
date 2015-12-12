/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Stateless;
import javax.management.Notification;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.com.mined.sieni.model.AlumnoRecibeNoti;
import sv.com.mined.sieni.model.DocRecibeNoti;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniNotificacion;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniNotificacionFacade extends AbstractFacade<SieniNotificacion> implements sv.com.mined.sieni.SieniNotificacionFacadeRemote {
    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniNotificacionFacade() {
        super(SieniNotificacion.class);
    }
    
    @Override
    public List<DocRecibeNoti> findDocenteNotify(Integer iddocente){
        Query q = em.createNamedQuery("DocRecibeNoti.findByIdDocente");
        q.setParameter("idDocente", iddocente);
        List<DocRecibeNoti> res = q.getResultList();
        return res;
    }
    
    @Override
    public List<AlumnoRecibeNoti> findAlumnoNotify(Integer idalumno){
        Query q = em.createNamedQuery("AlumnoRecibeNoti.findByIdAlumno");
        q.setParameter("idAlumno", idalumno);
        List<AlumnoRecibeNoti> res = q.getResultList();
        return res;
    }
    
}
