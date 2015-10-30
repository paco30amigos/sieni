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
import sv.com.mined.sieni.model.SieniEvaluacionItem;

/**
 *
 * @author ever
 */
@Stateless
public class SieniEvaluacionItemFacade extends AbstractFacade<SieniEvaluacionItem> implements sv.com.mined.sieni.SieniEvaluacionItemFacadeRemote {
   @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniEvaluacionItemFacade() {
        super(SieniEvaluacionItem.class);
    }

    @Override
    public List<SieniEvaluacionItem> findByIdEvaluacion(Long idEvaluacion) {
         
        Query q = em.createNamedQuery("SieniEvaluacionItem.findByIdEvaluacion");
        q.setParameter("idEvaluacion", idEvaluacion);
        List<SieniEvaluacionItem> res = q.getResultList();
        return res;   
    }
    
}
