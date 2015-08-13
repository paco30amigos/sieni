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
import sv.com.mined.sieni.model.SieniAlumno;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniAlumnoFacade extends AbstractFacade<SieniAlumno> implements sv.com.mined.sieni.SieniAlumnoFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniAlumnoFacade() {
        super(SieniAlumno.class);
    }

    @Override
    public List<SieniAlumno> findAlumnoSinUsuario() {
        Query q = em.createNamedQuery("SieniAlumno.findAlumnosSinUsuario");
        return q.getResultList();
    }
    @Override
    public List<SieniAlumno> findAlumnosNoMatriculados(){
        Query q = em.createNamedQuery("SieniAlumno.findAlumnosNoMatriculados");
        return q.getResultList();
    }

}
