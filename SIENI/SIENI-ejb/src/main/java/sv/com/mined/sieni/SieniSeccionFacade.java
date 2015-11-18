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
import sv.com.mined.sieni.model.SieniSeccion;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniSeccionFacade extends AbstractFacade<SieniSeccion> implements sv.com.mined.sieni.SieniSeccionFacadeRemote {
    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniSeccionFacade() {
        super(SieniSeccion.class);
    }

    @Override
    public SieniSeccion findByIdSeccion(Long idSeccion) {
        Query q = em.createNamedQuery("SieniSeccion.findByIdSeccion");
        q.setParameter("idSeccion", idSeccion);
        List<SieniSeccion> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }
    
}
