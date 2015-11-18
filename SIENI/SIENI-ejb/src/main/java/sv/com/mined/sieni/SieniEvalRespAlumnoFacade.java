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
import sv.com.mined.sieni.model.SieniEvalRespAlumno;
import sv.com.mined.sieni.model.SieniEvaluacion;

/**
 *
 * @author ever
 */
@Stateless
public class SieniEvalRespAlumnoFacade extends AbstractFacade<SieniEvalRespAlumno> implements sv.com.mined.sieni.SieniEvalRespAlumnoFacadeRemote {
   @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniEvalRespAlumnoFacade() {
        super(SieniEvalRespAlumno.class);
    }
    
   @Override
     public int guardarRespuestasAlumno(List<SieniEvalRespAlumno> respAlumnos){
         try {
             for (SieniEvalRespAlumno respAlumno : respAlumnos) {
            create(respAlumno);
        }
             return 1;
         } catch (Exception e) {
             return 0;
         }
         
       
    
    }

    @Override
    public List<SieniEvalRespAlumno> findByAlumnoEv(SieniAlumno alumno, SieniEvaluacion evaluacion) {
        Query q = em.createNamedQuery("SieniEvalRespAlumno.findByAlumnoEv");
        q.setParameter("idAlumno", alumno.getIdAlumno());
        q.setParameter("idEvaluacion", evaluacion.getIdEvaluacion());       
        return q.getResultList();
    }

    
}
