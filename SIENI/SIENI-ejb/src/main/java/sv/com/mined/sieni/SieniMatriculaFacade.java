/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.com.mined.sieni.model.SieniMatricula;
import sv.com.mined.sieni.model.SieniMatricula;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniMatriculaFacade extends AbstractFacade<SieniMatricula> implements sv.com.mined.sieni.SieniMatriculaFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniMatriculaFacade() {
        super(SieniMatricula.class);
    }

    @Override
    public List<SieniMatricula> getMatriculasAnio(Integer anio) {
//        Character estado='I';
        Query q = em.createNamedQuery("SieniMatricula.findMatriculasByAnio");
        q.setParameter("anio", anio);
//        q.setParameter("estado", estado);
        return q.getResultList();
    }

    @Override
    public List<SieniMatricula> findAllNoInactivos() {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniMatricula.findAllNoInactivos");
        q.setParameter("estado", estado);
        return q.getResultList();
    }

    @Override
    public List<SieniMatricula> findAllNoInactivosRpt(Date desde, Date hasta) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniMatricula.findAllNoInactivos");
        q.setParameter("estado", estado);
        q.setParameter("desde", desde);
        q.setParameter("hasta", hasta);
        return q.getResultList();
    }

    @Override
    public List<SieniMatricula> findAlumnoRpt(Date desde, Date hasta, Long grado, Long seccion) {
        Character estado = 'I';
        int tipo = 0;
        if (grado != null) {
            tipo = 1;
            if (seccion != null) {
                tipo = 2;
            }
        }
        Query q;
        switch (tipo) {
            case 1:
                q = em.createNamedQuery("SieniMatricula.findAnioGrado");
                break;
            case 2:
                q = em.createNamedQuery("SieniMatricula.findAnioGradoSeccion");
                break;
            default:
                q = em.createNamedQuery("SieniMatricula.findAnio");
                break;
        }
        q.setParameter("estado", estado);
        q.setParameter("anioDesde", desde);
        q.setParameter("anioHasta", hasta);
        if (grado != null) {
            q.setParameter("grado", grado);
            if (seccion != null) {
                q.setParameter("seccion", seccion);
            }
        }

        List<SieniMatricula> res = q.getResultList();
        return res;
    }
}
