/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniTemaDuda;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniTemaDudaFacade extends AbstractFacade<SieniTemaDuda> implements sv.com.mined.sieni.SieniTemaDudaFacadeRemote {
    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniTemaDudaFacade() {
        super(SieniTemaDuda.class);
    }
    
    @Override
    public List<SieniTemaDuda> findConsultasActivas() {
        Query q = em.createNamedQuery("SieniTemaDuda.findConsultasActivas");
        return q.getResultList();
    }
    
    
    @Override
    public List<SieniTemaDuda> findConsultasActivasByDocente(SieniDocente docente) {
        Query q = em.createNamedQuery("SieniTemaDuda.findConsultasActivasByDocente");
        q.setParameter("idDocente", docente.getIdDocente());
        return q.getResultList();
    }
    
    @Override
    public List<SieniTemaDuda> findConsultasActivasByAlumno(SieniAlumno alumno) {
        Query q = em.createNamedQuery("SieniTemaDuda.findConsultasActivasByAlumno");
        q.setParameter("idAlumno", alumno.getIdAlumno());
        return q.getResultList();
    }
    
}
