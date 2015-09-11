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
import sv.com.mined.sieni.model.SieniCompInteraccion;

/**
 *
 * @author francisco_medina
 */
@Stateless
public class SieniCompInteraccionFacade extends AbstractFacade<SieniCompInteraccion> implements sv.com.mined.sieni.SieniCompInteraccionFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniCompInteraccionFacade() {
        super(SieniCompInteraccion.class);
    }

    @Override
    public List<SieniCompInteraccion> findByIdSuperComp(Long idSuperCompon) {
        List<SieniCompInteraccion> ret = null;
        Query q = em.createNamedQuery("SieniCompInteraccion.findByIdSuperComp");
        q.setParameter("idSuperCompon", idSuperCompon);
        ret = q.getResultList();
        return ret;
    }

    public void merge(List<SieniCompInteraccion> lista, List<SieniCompInteraccion> eliminados) {
        for (SieniCompInteraccion actual : lista) {
            if (actual.getIdCompInteraccion() != null) {
                this.edit(actual);
            } else {
                this.create(actual);
            }
        }

        for (SieniCompInteraccion actual : eliminados) {
            if (actual.getIdCompInteraccion() != null) {
                this.remove(actual);
            }
        }
        em.flush();
    }
}
