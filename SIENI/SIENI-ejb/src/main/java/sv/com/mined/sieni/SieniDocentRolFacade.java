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
import sv.com.mined.sieni.model.SieniDocentRol;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniDocentRolFacade extends AbstractFacade<SieniDocentRol> implements sv.com.mined.sieni.SieniDocentRolFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniDocentRolFacade() {
        super(SieniDocentRol.class);
    }

    @Override
    public List<SieniDocentRol> findAllNoInactivos() {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniDocentRol.findAllNoInactivos");
        q.setParameter("estado", estado);
        return q.getResultList();
    }

    @Override
    public List<SieniDocentRol> findAdmins() {
        Character estado = 'A';
        Query q = em.createNamedQuery("SieniDocentRol.findAdmins");
        q.setParameter("estado", estado);
        q.setParameter("rol", new Long("2"));
        return q.getResultList();
    }

    @Override
    public List<SieniDocentRol> findRoles(Long idDocente) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniDocentRol.findRoles");
        q.setParameter("estado", estado);
        q.setParameter("idDocente", idDocente);
        return q.getResultList();
    }
    
    @Override
    public SieniDocentRol findByFIngreso(Long idDocente) {
        Query q = em.createNamedQuery("SieniDocentRol.findByFIngreso");
        q.setParameter("idDocente", idDocente);
        return (SieniDocentRol) q.getSingleResult();
    }

    @Override
    public List<SieniDocentRol> findUsuariosRpt(Integer estadoUser) {
        Query q = em.createNamedQuery("SieniDocentRol.findAll");;
        switch (estadoUser) {
            case 0: // TODOS
                q = em.createNamedQuery("SieniDocentRol.findAll");
                break;
            case 1: // ACTIVOS
                q = em.createNamedQuery("SieniDocentRol.findByEstado");
                q.setParameter("sdrEstado", 'A');
                break;
            case 2: // INACTIVOS
                q = em.createNamedQuery("SieniDocentRol.findByEstado");
                q.setParameter("sdrEstado", 'I');
                break;
        }
        return q.getResultList();
    }
}
