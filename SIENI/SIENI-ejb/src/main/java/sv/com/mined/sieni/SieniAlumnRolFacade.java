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
import sv.com.mined.sieni.model.SieniAlumnRol;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniAlumnRolFacade extends AbstractFacade<SieniAlumnRol> implements sv.com.mined.sieni.SieniAlumnRolFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniAlumnRolFacade() {
        super(SieniAlumnRol.class);
    }

    @Override
    public List<SieniAlumnRol> findAllNoInactivos() {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniAlumnRol.findAllNoInactivos");
        q.setParameter("estado", estado);
        return q.getResultList();
    }

    @Override
    public List<SieniAlumnRol> findRolesAlumno(Long idAlumno) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniAlumnRol.findRolesAlumno");
        q.setParameter("estado", estado);
        q.setParameter("idAlumno", idAlumno);
        return q.getResultList();
    }

    @Override
    public SieniAlumnRol findByFIngreso(Long idAlumno) {
        Query q = em.createNamedQuery("SieniAlumnRol.findByFIngreso");
        q.setParameter("idAlumno", idAlumno);
        return (SieniAlumnRol) q.getSingleResult();
    }

    @Override
    public List<SieniAlumnRol> findUsuariosRpt(Integer estadoUser) {
        Query q = em.createNamedQuery("SieniAlumnRol.findAll");
        switch (estadoUser) {
            case 0: //TODOS
                q = em.createNamedQuery("SieniAlumnRol.findAll");
                break;
            case 1: //ACTIVOS
                q = em.createNamedQuery("SieniAlumnRol.findByEstado");
                q.setParameter("sarEstado", 'A');
                break;
            case 2: //INACTIVOS
                q = em.createNamedQuery("SieniAlumnRol.findByEstado");
                q.setParameter("sarEstado", 'I');
                break;
        }
        return q.getResultList();
    }
}
