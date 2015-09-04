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
import sv.com.mined.sieni.model.SieniComponente;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniComponenteFacade extends AbstractFacade<SieniComponente> implements sv.com.mined.sieni.SieniComponenteFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniComponenteFacade() {
        super(SieniComponente.class);
    }

    @Override
    public void merge(List<SieniComponente> comps, List<SieniComponente> eliminados) {
        for (SieniComponente actual : comps) {
            if (actual.getIdComponente() != null) {
                this.edit(actual);
            } else {
                this.create(actual);
            }
        }

        for (SieniComponente actual : eliminados) {
            if (actual.getIdComponente() != null) {
                this.remove(actual);
            }
        }
        em.flush();
    }

    @Override
    public List<SieniComponente> findByIdSuperComp(Long idSuperCompon){
         Query q = em.createNamedQuery("SieniComponente.findByIdSuperComp");
        q.setParameter("idSuperCompon", idSuperCompon);
        return q.getResultList(); 
    }
}
