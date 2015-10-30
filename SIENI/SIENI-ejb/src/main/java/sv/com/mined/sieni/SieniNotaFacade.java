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
import sv.com.mined.sieni.model.SieniNota;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniNotaFacade extends AbstractFacade<SieniNota> implements sv.com.mined.sieni.SieniNotaFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniNotaFacade() {
        super(SieniNota.class);
    }

    @Override
    public void merge(List<SieniNota> notas) {
        for (SieniNota actual : notas) {
            if (actual.getIdNota() != null) {
                edit(actual);
            } else {
                create(actual);
            }
        }
        em.flush();
    }

    @Override
    public List<SieniNota> getNotasRangoFecha(Date desde, Date hasta) {
        Query q = em.createNamedQuery("SieniNota.getNotasRangoFecha");
        q.setParameter("desde", desde);
        q.setParameter("hasta", hasta);
        return q.getResultList();
    }

    @Override
    public List<SieniNota> findAllNoEliminadas() {
        Character estado = new Character('I');
        Query q = em.createNamedQuery("SieniNota.findAllNoEliminadas");
        q.setParameter("estado", estado);
        return q.getResultList();
    }

    @Override
    public boolean findNotaRegistrada(SieniNota nota) {
        Character estado = 'I';
        boolean ret = false;
        Query q = em.createNamedQuery("SieniNota.findNotaRegistrada");
        q.setParameter("estado", estado);
        q.setParameter("idAlumno", nota.getIdAlumno().getIdAlumno());
        q.setParameter("idEvaluacion", nota.getIdEvaluacion().getIdEvaluacion());
        List<SieniNota> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            ret = true;
        }
        return ret;
    }

    @Override
    public List<SieniNota> getNotasRpt(Date desde, Date hasta, Long grado, Long seccion) {
        List<SieniNota> ret;
        Query q = null;
        if (grado == null || grado.equals(0L)) {
            grado = null;
        }
        if (seccion == null || seccion.equals(0L)) {
            seccion = null;
        }

        if ((grado == null && seccion == null)) {
            q = em.createNamedQuery("SieniNota.findRango");
        }
        if (grado != null && seccion == null) {
            q = em.createNamedQuery("SieniNota.findRangoGrado");
            q.setParameter("grado", grado);
        }
        if (grado != null && seccion != null) {
            q = em.createNamedQuery("SieniNota.findRangoGradoSeccion");
            q.setParameter("grado", grado);
            q.setParameter("seccion", seccion);
        }
        //parametros obligatorios
        q.setParameter("desde", desde);
        q.setParameter("hasta", hasta);
        ret = q.getResultList();
        return ret;
    }
}
