/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.com.mined.sieni.model.SieniNota;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniNotaFacade extends AbstractFacade<SieniNota> implements sv.com.mined.sieni.SieniNotaFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniNotaFacade() {
        super(SieniNota.class);
    }

    @Override
    public void merge(List<SieniNota> notas) {
        for (SieniNota actual : notas) {
            if (actual.getIdNota() != null) {
                edit(actual);
            } else {
                create(actual);
            }
        }
        em.flush();
    }

    @Override
    public List<SieniNota> getNotasRangoFecha(Date desde, Date hasta) {
        Query q = em.createNamedQuery("SieniNota.getNotasRangoFecha");
        q.setParameter("desde", desde);
        q.setParameter("hasta", hasta);
        return q.getResultList();
    }

    @Override
    public List<SieniNota> findAllNoEliminadas() {
        Character estado = new Character('I');
        Query q = em.createNamedQuery("SieniNota.findAllNoEliminadas");
        q.setParameter("estado", estado);
        return q.getResultList();
    }
}
