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
import sv.com.mined.sieni.model.SieniNoticia;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniNoticiaFacade extends AbstractFacade<SieniNoticia> implements sv.com.mined.sieni.SieniNoticiaFacadeRemote {
    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniNoticiaFacade() {
        super(SieniNoticia.class);
    }
    
    @Override
    public List<SieniNoticia> findNoticiasActivas() {
        Query q = em.createNamedQuery("SieniNoticia.findNoticiasActivas");
        return q.getResultList();
    }
    
}
