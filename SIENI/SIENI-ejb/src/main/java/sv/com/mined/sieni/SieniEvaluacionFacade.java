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
import sv.com.mined.sieni.model.SieniEvalRespItem;
import sv.com.mined.sieni.model.SieniEvaluacion;
import sv.com.mined.sieni.model.SieniEvaluacionItem;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniEvaluacionFacade extends AbstractFacade<SieniEvaluacion> implements sv.com.mined.sieni.SieniEvaluacionFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniEvaluacionFacade() {
        super(SieniEvaluacion.class);
    }

    @Override
    public List<SieniEvaluacion> findActivos() {
        Query q = em.createNamedQuery("SieniEvaluacion.findActivos");

        List<SieniEvaluacion> res = q.getResultList();
        return res;
    }

    @Override
    public List<SieniEvaluacion> findEvaluacionDesdeHasta(Date desde, Date hasta) {

        Query q = em.createNamedQuery("SieniEvaluacion.findByDesdeHasta");
        q.setParameter("desde", desde);
        q.setParameter("hasta", hasta);
        List<SieniEvaluacion> res = q.getResultList();

        return res;

    }

    @Override
    public List<SieniEvaluacion> findbyRendimientoRpt(Date desde, Date hasta, String grado, String seccion, String materia) {
        Query q = em.createNamedQuery("SieniEvaluacion.findbyRendimientoRpt");
        q.setParameter("desde", desde);
        q.setParameter("hasta", hasta);
        q.setParameter("grado", grado);
        q.setParameter("seccion", seccion);
        q.setParameter("materia", materia);
        return q.getResultList();
    }

    @Override
    public SieniEvaluacion findEvalItemResp(Long idEvaluacion) {
        Query q = em.createNamedQuery("SieniEvaluacion.findEvalItemResp");
        q.setParameter("idEvaluacion", idEvaluacion);
        SieniEvaluacion res = (SieniEvaluacion) q.getSingleResult();
//    for (SieniInteEntrComp actual : ret) {
        em.refresh(res);
//            em.refresh(res.getSieniEvaluacionItemList());
        for (SieniEvaluacionItem actual : res.getSieniEvaluacionItemList()) {
            em.refresh(actual);
            for (SieniEvalRespItem rep : actual.getSieniEvalRespItemList()) {
                em.refresh(rep);
            }
        }

//            em.refresh(res.getSieniEvaluacionItemList());
//        }
        em.flush();
        return res;
    }

    @Override
    public List<SieniEvaluacion> findByIdMateria(List<Long> listIdMateria) {
        Query q = em.createNamedQuery("SieniEvaluacion.findEvalItemResp");
        q.setParameter("listIdMateria", listIdMateria);
        List<SieniEvaluacion> res = q.getResultList();
        return res;
    }

    @Override
    public List<SieniEvaluacion> findIdMateria(Long idMateria) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniEvaluacion.findIdMateria");
        q.setParameter("idMateria", idMateria);
        q.setParameter("estado", estado);
        List<SieniEvaluacion> res = q.getResultList();
        return res;
    }

    @Override
    public List<SieniEvaluacion> findByMateria(Long idMateria) {
        Query q = em.createNamedQuery("SieniEvaluacion.findByMateria");
        q.setParameter("idMateria", idMateria);
        List<SieniEvaluacion> res = q.getResultList();
        return res;
    }

}
