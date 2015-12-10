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
import sv.com.mined.sieni.model.SieniTipoElemPlantilla;

/**
 *
 * @author francisco_medina
 */
@Stateless
public class SieniTipoElemPlantillaFacade extends AbstractFacade<SieniTipoElemPlantilla> implements sv.com.mined.sieni.SieniTipoElemPlantillaFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniTipoElemPlantillaFacade() {
        super(SieniTipoElemPlantilla.class);
    }

    @Override
    public SieniTipoElemPlantilla findByNombre(String nombre) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniTipoElemPlantilla.findByNombre");
        q.setParameter("estado", estado);
        q.setParameter("nombre", nombre);
        List<SieniTipoElemPlantilla> res = q.getResultList();
        SieniTipoElemPlantilla ret = null;
        if (res != null && !res.isEmpty()) {
            ret = res.get(0);
        }

        return ret;
    }

    @Override
    public List<SieniTipoElemPlantilla> findAllNoInactivos() {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniTipoElemPlantilla.findAllNoInactivos");
        q.setParameter("estado", estado);
        List<SieniTipoElemPlantilla> ret = q.getResultList();

        return ret;

    }

    @Override
    public List<SieniTipoElemPlantilla> findAllActivos() {
        Character estado = 'A';
        Query q = em.createNamedQuery("SieniTipoElemPlantilla.findByEstado");
        q.setParameter("estado", estado);
        List<SieniTipoElemPlantilla> ret = q.getResultList();

        return ret;
    }
}
