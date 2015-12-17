/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.com.mined.sieni.model.SieniCursoAlumno;

/**
 *
 * @author bugtraq
 */
@Stateless
public class SieniCursoAlumnoFacade extends AbstractFacade<SieniCursoAlumno> implements sv.com.mined.sieni.SieniCursoAlumnoFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniCursoAlumnoFacade() {
        super(SieniCursoAlumno.class);
    }

    @Override
    public SieniCursoAlumno findByIdCursoIdAlumno(Long idCurso, Long idAlumno) {

        try {
            Query q = em.createNamedQuery("SieniCursoAlumno.findByIdCursoIdAlumno");
            q.setParameter("idCurso", idCurso);
            q.setParameter("idAlumno", idAlumno);
            return (SieniCursoAlumno) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

}
