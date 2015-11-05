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
import sv.com.mined.sieni.model.SieniMateria;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniMateriaFacade extends AbstractFacade<SieniMateria> implements sv.com.mined.sieni.SieniMateriaFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniMateriaFacade() {
        super(SieniMateria.class);
    }

    @Override
    public List<SieniMateria> findByAlumno(Long idAlumno) {
        Character estado = new Character('I');
        Query q = em.createNamedQuery("SieniMateria.findByAlumno");
        q.setParameter("idAlumno", idAlumno);
        q.setParameter("estado", estado);
        return q.getResultList();
    }

    @Override
    public List<SieniMateria> findAllNoInactivas() {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniMateria.findAllNoInactivas");
        q.setParameter("estado", estado);
        List<SieniMateria> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res;
        } else {
            return null;
        }
    }

    @Override
    public List<SieniMateria> findMateriasActivas() {
        Character estado = 'A';
        Query q = em.createNamedQuery("SieniMateria.findMateriasByEstado");
        q.setParameter("estado", estado);
        List<SieniMateria> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res;
        } else {
            return null;
        }
    }
}
