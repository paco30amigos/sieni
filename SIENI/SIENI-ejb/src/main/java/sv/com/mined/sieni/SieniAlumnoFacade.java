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
    public List<SieniAlumno> findAlumnoActivos() {
        Query q = em.createNamedQuery("SieniAlumno.findAlumnosActivos");
        return q.getResultList();
    }

    @Override
    public List<SieniAlumno> findAlumnoSinUsuario() {
        Query q = em.createNamedQuery("SieniAlumno.findAlumnosSinUsuario");
        return q.getResultList();
    }

    @Override
    public List<SieniAlumno> findAlumnosNoMatriculados() {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniAlumno.findAlumnosNoMatriculados");
        q.setParameter("estado", estado);
        return q.getResultList();
    }

    @Override
    public SieniAlumno findAlumnoUsuario(String usuario, String password) {
        Query q = em.createNamedQuery("SieniAlumno.findAlumnoUsuario");
        q.setParameter("usuario", usuario);
        q.setParameter("pass", password);
        List<SieniAlumno> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<SieniAlumno> findAlumnoRpt(String anio, Long grado, Long seccion) {
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
                q = em.createNamedQuery("SieniAlumno.findAnioGrado");
                break;
            case 2:
                q = em.createNamedQuery("SieniAlumno.findAnioGradoSeccion");
                break;
            default:
                q = em.createNamedQuery("SieniAlumno.findAnio");
                break;
        }
        q.setParameter("anio", anio);
        if (grado != null) {
            q.setParameter("grado", grado);
            if (seccion != null) {
                q.setParameter("seccion", seccion);
            }
        }

        List<SieniAlumno> res = q.getResultList();
        return res;
    }

}
