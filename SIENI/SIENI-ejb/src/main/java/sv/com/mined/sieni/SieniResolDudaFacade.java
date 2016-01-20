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
import sv.com.mined.sieni.model.SieniResolDuda;
import sv.com.mined.sieni.model.SieniTemaDuda;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniResolDudaFacade extends AbstractFacade<SieniResolDuda> implements sv.com.mined.sieni.SieniResolDudaFacadeRemote {
    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniResolDudaFacade() {
        super(SieniResolDuda.class);
    }
    
    
    @Override
    public List<SieniResolDuda> findByConsulta(SieniTemaDuda consulta) {
        Query q = em.createNamedQuery("SieniResolDuda.findByConsulta");
        q.setParameter("idConsulta", consulta.getIdTemaDuda());
        return q.getResultList();
    }
}
