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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import sv.com.mined.sieni.model.SieniAlumno;
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
        Query q = em.createNativeQuery("select * from sieni_docente d left outer join sieni_docent_rol dr on(d.id_docente=dr.id_docente) where dr.id_docente_rol is null or (dr.id_docente_rol is not null and dr.sdr_estado ='I')", SieniDocente.class);
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

    public boolean docenteRegistrado(SieniDocente docente) {
        // es necesario instalar la siguiente extencion
        //CREATE EXTENSION unaccent;
        boolean ret = false;
        Character estado = 'I';
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SieniDocente> q = cb.createQuery(SieniDocente.class);
        Root<SieniDocente> c = q.from(SieniDocente.class);
        Predicate p = cb.conjunction();
        Path<String> primNombre = c.get("dcPrimNombre");
        ParameterExpression<String> paramPrimNombre = cb.parameter(String.class);
        Path<String> segNombre = c.get("dcSeguNombre");
        ParameterExpression<String> paramSegNombre = cb.parameter(String.class);
        Path<String> tercNombre = c.get("dcTercNombre");
        ParameterExpression<String> paramTercNombre = cb.parameter(String.class);
        Path<String> primApellido = c.get("dcPrimApe");
        ParameterExpression<String> paramPrimApellido = cb.parameter(String.class);
        Path<String> segApellido = c.get("dcSeguApe");
        ParameterExpression<String> paramSegApellido = cb.parameter(String.class);
        Path<String> tercApellido = c.get("dcTercApe");
        ParameterExpression<String> paramTercApellido = cb.parameter(String.class);
        Path<Character> pathEstado = c.get("dcEstado");
        ParameterExpression<Character> paramEstado = cb.parameter(Character.class);
        p = cb.and(p, cb.equal(cb.function("unaccent", String.class, cb.lower(primNombre)), cb.function("unaccent", String.class, paramPrimNombre)));
        p = cb.and(p, cb.equal(cb.function("unaccent", String.class, cb.lower(segNombre)), cb.function("unaccent", String.class, paramSegNombre)));
        p = cb.and(p, cb.equal(cb.function("unaccent", String.class, cb.lower(tercNombre)), cb.function("unaccent", String.class, paramTercNombre)));
        p = cb.and(p, cb.equal(cb.function("unaccent", String.class, cb.lower(primApellido)), cb.function("unaccent", String.class, paramPrimApellido)));
        p = cb.and(p, cb.equal(cb.function("unaccent", String.class, cb.lower(segApellido)), cb.function("unaccent", String.class, paramSegApellido)));
        p = cb.and(p, cb.equal(cb.function("unaccent", String.class, cb.lower(tercApellido)), cb.function("unaccent", String.class, paramTercApellido)));
        p = cb.and(p, cb.notEqual(pathEstado, paramEstado));
        q.select(c).where(p);
        TypedQuery<SieniDocente> query = em.createQuery(q);
        query.setParameter(paramPrimNombre, docente.getDcPrimNombre() != null ? docente.getDcPrimNombre().toLowerCase() : null);
        query.setParameter(paramSegNombre, docente.getDcSeguNombre() != null ? docente.getDcSeguNombre().toLowerCase() : null);
        query.setParameter(paramTercNombre, docente.getDcTercNombre() != null ? docente.getDcTercNombre().toLowerCase() : null);
        query.setParameter(paramPrimApellido, docente.getDcPrimApe() != null ? docente.getDcPrimApe().toLowerCase() : null);
        query.setParameter(paramSegApellido, docente.getDcSeguApe() != null ? docente.getDcSeguApe().toLowerCase() : null);
        query.setParameter(paramTercApellido, docente.getDcTercApe() != null ? docente.getDcTercApe().toLowerCase() : null);
        query.setParameter(paramEstado, estado);
        List<SieniDocente> results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            ret = true;
        }
        return ret;
    }

}
