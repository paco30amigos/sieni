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
import sv.com.mined.sieni.model.SieniSuperCompon;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniSuperComponFacade extends AbstractFacade<SieniSuperCompon> implements sv.com.mined.sieni.SieniSuperComponFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniSuperComponFacade() {
        super(SieniSuperCompon.class);
    }

    @Override
    public List<SieniSuperCompon> findAllNoInactivos() {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniSuperCompon.findAllNoInactivos");
        q.setParameter("estado", estado);
        for(SieniSuperCompon actual:(List<SieniSuperCompon>)q.getResultList()){
            em.refresh(actual);
        }
        List<SieniSuperCompon> ret = q.getResultList();
        for (SieniSuperCompon actual : ret) {
            em.refresh(actual);
        }
        return ret;        
    }

    @Override
    public List<SieniSuperCompon> findByClase(Long idClase) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniSuperCompon.findByClase");
        q.setParameter("estado", estado);
        q.setParameter("idClase", idClase);
        List<SieniSuperCompon> ret = q.getResultList();
        for (SieniSuperCompon actual : ret) {
            em.refresh(actual);
        }
        return ret;        
    }

    public List<SieniSuperCompon> findEstado(Character estado) {
        Query q = em.createNamedQuery("SieniSuperCompon.findByEstado");
        q.setParameter("estado", estado);
        List<SieniSuperCompon> ret = q.getResultList();
        for (SieniSuperCompon actual : ret) {
            em.refresh(actual);
        }
        return ret;        
    }
}
