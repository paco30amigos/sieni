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
import sv.com.mined.sieni.model.SieniClaseSupComp;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniClaseSupCompFacade extends AbstractFacade<SieniClaseSupComp> implements sv.com.mined.sieni.SieniClaseSupCompFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniClaseSupCompFacade() {
        super(SieniClaseSupComp.class);
    }

    @Override
    public List<SieniClaseSupComp> findByClase(Long idClase) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniClaseSupComp.findByClase");
        q.setParameter("estado", estado);
        q.setParameter("idClase", idClase);
        return q.getResultList();
    }

    @Override
    public void merge(List<SieniClaseSupComp> lista, List<SieniClaseSupComp> eliminados) {
        for (SieniClaseSupComp actual : lista) {
            if (actual.getIdClaseSupComp() != null && actual.getIdClaseSupComp() < 0) {
                actual.setIdClaseSupComp(null);
            }
            if (actual.getIdClaseSupComp() != null) {
                this.edit(actual);
            } else {
                this.create(actual);
            }
        }

        for (SieniClaseSupComp actual : eliminados) {
            if (actual.getIdClaseSupComp() != null && actual.getIdClaseSupComp() < 0) {
                actual.setIdClaseSupComp(null);
            }
            if (actual.getIdClaseSupComp() != null) {
                actual.setScEstado('I');//eliminacion logica
                this.edit(actual);
            }
        }
        em.flush();
    }
}
