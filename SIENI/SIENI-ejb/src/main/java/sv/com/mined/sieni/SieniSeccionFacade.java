/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.com.mined.sieni.model.SieniSeccion;
import sv.com.mined.sieni.model.SieniAnioEscolar;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniSeccionFacade extends AbstractFacade<SieniSeccion> implements sv.com.mined.sieni.SieniSeccionFacadeRemote {
    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniSeccionFacade() {
        super(SieniSeccion.class);
    }

    @Override
    public SieniSeccion findByIdSeccion(Long idSeccion) {
        Query q = em.createNamedQuery("SieniSeccion.findByIdSeccion");
        q.setParameter("idSeccion", idSeccion);
        List<SieniSeccion> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public List<SieniSeccion> findBy(SieniSeccion seccion) {
        List<SieniSeccion> ret = null;
        String jpql = "select s from SieniSeccion s";
        HashMap<String, Object> params = new HashMap<>();
        boolean where = false;
        String filter = " where 1==1";
        if (seccion != null) {
            if (seccion.getScDescripcion()!= null) {
                filter += " and s.scDescripcion =:scDescripcion ";
                params.put("scDescripcion", seccion.getScDescripcion());
                where = true;
            }
            if (seccion.getIdGrado() != null) {
                filter += where ? " and " : "";
                filter += " s.idGrado =:idGrado";
                params.put("idGrado", seccion.getIdGrado());
            }
            Query q = em.createQuery(jpql + filter);
            for (String actual : params.keySet()) {
                q.setParameter(actual, params.get(actual));
            }
            ret = q.getResultList();
            if (ret == null || ret.isEmpty()) {
                ret = null;
            }
        }
        return ret;
    }

    

    @Override
    public List<SieniSeccion> findByAnioEscolar(SieniAnioEscolar anio) {
        Query q = em.createNamedQuery("SieniSeccion.findByAnioEscolar");
        q.setParameter("anio", anio.getIdAnioEscolar());
        List<SieniSeccion> ret = q.getResultList();
        return ret;
    }
    
}
