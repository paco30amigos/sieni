/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniPntosContrl;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniPntosContrlFacade extends AbstractFacade<SieniPntosContrl> implements sv.com.mined.sieni.SieniPntosContrlFacadeRemote {
    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniPntosContrlFacade() {
        super(SieniPntosContrl.class);
    }
    @Override
    public SieniPntosContrl findPuntos(Long idTipoElemPlantilla,Integer nPantalla,Long idClase,Long idAlumno){
        List<SieniPntosContrl> req=new ArrayList<>();
        SieniPntosContrl ret=new SieniPntosContrl();
        Query q=em.createNamedQuery("SieniPntosContrl.findPtosByClaseAlumnPantallaSeccion");
        q.setParameter("idTipoElemPlantilla", idTipoElemPlantilla);
        q.setParameter("nPantalla", nPantalla);
        q.setParameter("idClase", idClase);
        q.setParameter("idAlumno", idAlumno);
        req=q.getResultList();
        if(req!=null&&!req.isEmpty()){
            ret=req.get(0);
            em.refresh(ret);
        }
        return ret;
    }

    @Override
    public List<SieniAlumno> findByAlumno() {
        Query q = em.createNamedQuery("SieniPntosContrl.findByAlumno");
        List<SieniAlumno> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res;
        } else {
            return null;
        }
    }

    @Override
    public List<SieniClase> findByClasesAlumnos(Long idAlumno) {
        Query q = em.createNamedQuery("SieniPntosContrl.findByClasesAlumnos");
        q.setParameter("idAlumno", idAlumno);
        List<SieniClase> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res;
        } else {
            return null;
        }
    }
}
