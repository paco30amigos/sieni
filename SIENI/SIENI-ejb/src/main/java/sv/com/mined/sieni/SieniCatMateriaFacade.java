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
import sv.com.mined.sieni.model.SieniCatMateria;

/**
 *
 * @author Alejandro
 */
@Stateless
public class SieniCatMateriaFacade extends AbstractFacade<SieniCatMateria> implements sv.com.mined.sieni.SieniCatMateriaFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniCatMateriaFacade() {
        super(SieniCatMateria.class);
    }

    @Override
    public List<SieniCatMateria> findAllNoInactivos() {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniCatMateria.findAllNoInactivos");
        q.setParameter("estado", estado);
        List<SieniCatMateria> res = q.getResultList();
        return res;
    }

    @Override
    public SieniCatMateria findByNombre(String nombre) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniCatMateria.findByCatNombre");
        q.setParameter("nombre", nombre);
        q.setParameter("estado", estado);
        List<SieniCatMateria> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<SieniCatMateria> findAllActivos() {
        Character estado = 'A';
        Query q = em.createNamedQuery("SieniCatMateria.findByEstado");
        q.setParameter("estado", estado);
        List<SieniCatMateria> res = q.getResultList();
        return res;
    }

}
