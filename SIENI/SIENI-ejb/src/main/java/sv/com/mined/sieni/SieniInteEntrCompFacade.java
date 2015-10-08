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
import sv.com.mined.sieni.model.SieniInteEntrComp;

/**
 *
 * @author francisco_medina
 */
@Stateless
public class SieniInteEntrCompFacade extends AbstractFacade<SieniInteEntrComp> implements sv.com.mined.sieni.SieniInteEntrCompFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniInteEntrCompFacade() {
        super(SieniInteEntrComp.class);
    }

    @Override
    public List<SieniInteEntrComp> findByClase(Long idClase) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniInteEntrComp.findByClase");
        q.setParameter("estado", estado);
        q.setParameter("idClase", idClase);
        List<SieniInteEntrComp> ret = q.getResultList();
        for (SieniInteEntrComp actual : ret) {
            em.refresh(actual);
            em.refresh(actual.getIdTipoElemPlantilla());
            em.refresh(actual.getIeEventoC1());
            em.refresh(actual.getIeEventoC2());
            em.refresh(actual.getIeSupC1());
            em.refresh(actual.getIeSupC2());
        }
        em.flush();
        return ret;
    }
}
