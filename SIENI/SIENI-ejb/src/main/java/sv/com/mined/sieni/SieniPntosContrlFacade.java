/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.ArrayList;
import java.util.Date;
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
    public SieniPntosContrl findPuntos(Long idTipoElemPlantilla, Integer nPantalla, Long idClase, Long idAlumno) {
        List<SieniPntosContrl> req = new ArrayList<>();
        SieniPntosContrl ret = new SieniPntosContrl();
        Query q = em.createNamedQuery("SieniPntosContrl.findPtosByClaseAlumnPantallaSeccion");
        q.setParameter("idTipoElemPlantilla", idTipoElemPlantilla);
        q.setParameter("nPantalla", nPantalla);
        q.setParameter("idClase", idClase);
        q.setParameter("idAlumno", idAlumno);
        req = q.getResultList();
        if (req != null && !req.isEmpty()) {
            ret = req.get(0);
            em.refresh(ret);
        }
        return ret;
    }

    @Override
    public List<SieniAlumno> findByAlumno(Date desde, Date hasta) {
        Query q = em.createNamedQuery("SieniPntosContrl.findByAlumno");
        q.setParameter("desde", desde);
        q.setParameter("hasta", hasta);
        List<SieniAlumno> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res;
        } else {
            return new ArrayList<>();
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

    @Override
    public Integer findByCountClase(Long idClase,Long idAlumno) {
        Query q = em.createNamedQuery("SieniPntosContrl.findByCountClase");
        q.setParameter("idClase", idClase);
        q.setParameter("idAlumno", idAlumno);
        List<SieniPntosContrl> res = q.getResultList();
        Integer total = 0;
        if (res != null && !res.isEmpty()) {
            for (SieniPntosContrl actual : res) {
                total += actual.getPcPantalla();
            }
        }
        if (res != null) {
            return total;
        } else {
            return null;
        }
    }
}
