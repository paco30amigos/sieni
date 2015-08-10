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
import sv.com.mined.sieni.model.SieniDocente;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniDocenteFacade extends AbstractFacade<SieniDocente> implements sv.com.mined.sieni.SieniDocenteFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniDocenteFacade() {
        super(SieniDocente.class);
    }

    @Override
    public List<SieniDocente> findDocentesSinUsuario() {
        Query q = em.createNamedQuery("SieniDocente.findDocentesSinUsuario");
        return q.getResultList();
    }

}
