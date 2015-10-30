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
import sv.com.mined.sieni.model.SieniEvalRespItem;

/**
 *
 * @author ever
 */
@Stateless
public class SieniEvalRespItemFacade extends AbstractFacade<SieniEvalRespItem> implements sv.com.mined.sieni.SieniEvalRespItemFacadeRemote {
    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniEvalRespItemFacade() {
        super(SieniEvalRespItem.class);
    }
    
    @Override
    public List<SieniEvalRespItem> findByIdEvalItem(Long idEvaluacionItem) {
         
        Query q = em.createNamedQuery("SieniEvalRespItem.findByIdEvalItem");
        q.setParameter("idEvaluacionItem", idEvaluacionItem);
        List<SieniEvalRespItem> res = q.getResultList();
        return res;   
    }
    
    
}
