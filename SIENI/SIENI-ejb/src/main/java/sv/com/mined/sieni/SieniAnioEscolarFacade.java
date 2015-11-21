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
import sv.com.mined.sieni.model.SieniAnioEscolar;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniAnioEscolarFacade extends AbstractFacade<SieniAnioEscolar> implements SieniAnioEscolarFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniAnioEscolarFacade() {
        super(SieniAnioEscolar.class);
    }

    @Override
    public List<SieniAnioEscolar> findAllNoInactivos() {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniAnioEscolar.findAllNoInactivos");
        q.setParameter("estado", estado);
        List<SieniAnioEscolar> ret = q.getResultList();
        for (SieniAnioEscolar actual : ret) {
            em.refresh(actual);
        }
        return ret;
    }
    
    @Override
    public SieniAnioEscolar findActivo() {
        Character estado = 'A';
        Query q = em.createNamedQuery("SieniAnioEscolar.findByAeEstado");
        q.setParameter("estado", estado);
        List<SieniAnioEscolar> res = q.getResultList();
        SieniAnioEscolar ret=null;
        if(res!=null&&!res.isEmpty()){
            ret=res.get(0);
            em.refresh(ret);
        }        
        return ret;
    }
}
