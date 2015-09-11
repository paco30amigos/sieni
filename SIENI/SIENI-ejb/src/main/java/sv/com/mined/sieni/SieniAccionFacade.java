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
import sv.com.mined.sieni.model.SieniAccion;

/**
 *
 * @author francisco_medina
 */
@Stateless
public class SieniAccionFacade extends AbstractFacade<SieniAccion> implements sv.com.mined.sieni.SieniAccionFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniAccionFacade() {
        super(SieniAccion.class);
    }

    @Override
    public List<SieniAccion> findByTipoSuperComponente(Long tipoComponente) {
        Query q = em.createNamedQuery("SieniAccion.findByTipoSuperComponente");
        q.setParameter("idTipoSuperComponente", tipoComponente);
        return q.getResultList();
    }
}
