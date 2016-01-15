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
import sv.com.mined.sieni.model.SieniEvaluacionItem;

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
     public int guardarRespuestasAlumno(List<SieniEvalRespAlumno> respAlumnos,int numIntento){
         try {
             SieniEvalRespAlumno sieniEvalRespAlumno=new SieniEvalRespAlumno();
             for (SieniEvalRespAlumno respAlumno : respAlumnos) {
                 sieniEvalRespAlumno=findByAlumnoItemEv(respAlumno.getIdAlumno(), respAlumno.getIdEvaluacionItem());
                 if(sieniEvalRespAlumno!=null){                     
                     if(numIntento>0){
                         remove(sieniEvalRespAlumno);
                         create(respAlumno);
                     }else{
                     sieniEvalRespAlumno.setRaRespuesta(respAlumno.getRaRespuesta());
                     edit(sieniEvalRespAlumno);
                     }
                 }else{
                 create(respAlumno);
                 }                  
            
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
    
    public SieniEvalRespAlumno findByAlumnoItemEv(Long idAlumno, SieniEvaluacionItem evaluacionItem) {
        try {
            Query q = em.createNamedQuery("SieniEvalRespAlumno.findByAlumnoItemEv");
        q.setParameter("idAlumno", idAlumno);
        q.setParameter("idEvaluacionItem", evaluacionItem.getIdEvaluacionItem());       
        return (SieniEvalRespAlumno) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    
}
