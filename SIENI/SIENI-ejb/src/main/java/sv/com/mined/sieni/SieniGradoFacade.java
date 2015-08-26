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
import sv.com.mined.sieni.model.SieniGrado;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniGradoFacade extends AbstractFacade<SieniGrado> implements sv.com.mined.sieni.SieniGradoFacadeRemote {
    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniGradoFacade() {
        super(SieniGrado.class);
    }
    @Override
    public SieniGrado getGradoActualAlumno(Long idAlumno, String anio){
        Query q = em.createNamedQuery("SieniGrado.findGradoActualAlumno");
        q.setParameter("idAlumno", idAlumno);
        q.setParameter("anio", anio);
        List<SieniGrado> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }
}
