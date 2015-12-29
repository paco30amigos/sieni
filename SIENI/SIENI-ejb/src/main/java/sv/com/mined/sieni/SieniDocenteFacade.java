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
import sv.com.mined.sieni.model.SieniDocente;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniDocenteFacade extends AbstractFacade<SieniDocente> implements sv.com.mined.sieni.SieniDocenteFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniDocenteFacade() {
        super(SieniDocente.class);
    }

    @Override
    public List<SieniDocente> findDocentesSinUsuario() {
        Query q = em.createNativeQuery("select * from sieni_docente d left outer join sieni_docent_rol dr on(d.id_docente=dr.id_docente) where dr.id_docente_rol is null",SieniDocente.class);
        return q.getResultList();
    }

    @Override
    public SieniDocente findDocenteUsuario(String usuario, String pass) {
        Query q = em.createNamedQuery("SieniDocente.findDocenteUsuario");
        q.setParameter("usuario", usuario);
        q.setParameter("pass", pass);
        List<SieniDocente> res = q.getResultList();
        SieniDocente ret = null;
        if (res != null && !res.isEmpty()) {
            ret = res.get(0);
            em.refresh(ret);
            return ret;
        } else {
            return null;
        }
    }

    @Override
    public List<SieniDocente> findDocentesActivos() {

        Query q = em.createNamedQuery("SieniDocente.findDocenteActivo");
        List<SieniDocente> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res;
        } else {
            return null;
        }
    }

    @Override
    public List<SieniDocente> findDocentesDesdeHasta(Date desde, Date hasta) {

        Query q = em.createNamedQuery("SieniDocente.findByDesdeHasta");
        q.setParameter("desde", desde);
        q.setParameter("hasta", hasta);
        List<SieniDocente> res = q.getResultList();

        return res;

    }

    @Override
    public List<SieniDocente> findUsuariosRpt(Integer estadoUser) {
        Query q = em.createNamedQuery("SieniDocente.findRptUsuariosDocentes");;
        switch (estadoUser) {
            case 0: // TODOS
                q = em.createNamedQuery("SieniDocente.findRptUsuariosDocentes");
                break;
            case 1: // ACTIVOS
                q = em.createNamedQuery("SieniDocente.findRptUsuariosDocentesByEstado");
                q.setParameter("dcEstado", 'A');
                break;
            case 2: // INACTIVOS
                q = em.createNamedQuery("SieniDocente.findRptUsuariosDocentesByEstado");
                q.setParameter("dcEstado", 'I');
                break;
        }
        return q.getResultList();
    }

    @Override
    public SieniDocente findUsuario(String usuario) {
        Query q = em.createNamedQuery("SieniDocente.findByDcUsuario");
        q.setParameter("dcUsuario", usuario);
        List<SieniDocente> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SieniDocente findByDocenteId(Long idDocente) {
        Query q = em.createNamedQuery("SieniDocente.findByIdDocente");
        q.setParameter("idDocente", idDocente);
        List<SieniDocente> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }

}
