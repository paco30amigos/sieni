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
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.model.SieniDocente;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniCursoFacade extends AbstractFacade<SieniCurso> implements sv.com.mined.sieni.SieniCursoFacadeRemote {
    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniCursoFacade() {
        super(SieniCurso.class);
    }

    @Override
    public List<SieniCurso> findByEstado(Character estado) {
    Query q=em.createNamedQuery("SieniCurso.findAByEstado");
        q.setParameter("estado", estado);
        
    List<SieniCurso> res = q.getResultList();   
    return res;
    }
    
    
    
}
