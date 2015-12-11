/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.Date;
import java.util.HashMap;
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
    public SieniGrado getGradoActualAlumno(Long idAlumno, Date anioDesde, Date anioHasta) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniGrado.findGradoActualAlumno");
        q.setParameter("idAlumno", idAlumno);
        q.setParameter("anioDesde", anioDesde);
        q.setParameter("anioHasta", anioHasta);
        q.setParameter("estado", estado);
        List<SieniGrado> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<SieniGrado> findBy(SieniGrado grado) {
        List<SieniGrado> ret = null;
        String jpql = "select s from SieniGrado s";
        HashMap<String, Object> params = new HashMap<>();
        boolean where = false;
        String filter = " where s.grEstado=:estado";
        if (grado != null) {
            Character estado = 'I';
            if (grado.getGrNombre() != null) {
                filter += " and s.grNombre=:grNombre ";
                params.put("grNombre", grado.getGrNombre());
                where = true;
            }
            if (grado.getGrNumero() != null) {
                filter += where ? " and " : "";
                filter += " s.grNumero=:grNumero";
                params.put("grNumero", grado.getGrNumero());
            }
            Query q = em.createQuery(jpql + filter);
            q.setParameter("estado", estado);
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
    public SieniGrado findByIdGrado(Long idGrado) {
        Query q = em.createNamedQuery("SieniGrado.findByIdGrado");
        q.setParameter("idGrado", idGrado);
        List<SieniGrado> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<SieniGrado> findAllNoInactivos() {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniGrado.findAllNoInactivos");
        q.setParameter("estado", estado);
        List<SieniGrado> ret = q.getResultList();
        return ret;
    }
}
