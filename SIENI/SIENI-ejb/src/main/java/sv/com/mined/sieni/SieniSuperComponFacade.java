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
        return q.getResultList();
    }

    @Override
    public List<SieniSuperCompon> findByClase(Long idClase) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniSuperCompon.findByClase");
        q.setParameter("estado", estado);
        q.setParameter("idClase", idClase);
        return q.getResultList();
    }

}
