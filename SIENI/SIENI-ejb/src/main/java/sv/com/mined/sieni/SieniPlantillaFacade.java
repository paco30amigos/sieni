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
import sv.com.mined.sieni.model.SieniPlantilla;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniPlantillaFacade extends AbstractFacade<SieniPlantilla> implements sv.com.mined.sieni.SieniPlantillaFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniPlantillaFacade() {
        super(SieniPlantilla.class);
    }

    @Override
    public List<SieniPlantilla> findByMateria(Long idMateria) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniPlantilla.findByMateria");
        q.setParameter("estado", estado);
        q.setParameter("idMateria", idMateria);
        List<SieniPlantilla> ret = q.getResultList();
        for (SieniPlantilla actual : ret) {
            em.refresh(actual);
        }
        return ret;
    }
    
    @Override
    public SieniPlantilla refresh(SieniPlantilla sieniPlantilla){
        em.flush();
        sieniPlantilla=find(sieniPlantilla.getIdPlantilla());
        em.refresh(sieniPlantilla);
        return sieniPlantilla;
    }

    @Override
    public List<SieniPlantilla> findAllNoInactivas() {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniPlantilla.findAllNoInactivas");
        q.setParameter("estado", estado);
        List<SieniPlantilla> ret = q.getResultList();
        for (SieniPlantilla actual : ret) {
            em.refresh(actual);
        }
        return ret;
    }
}
