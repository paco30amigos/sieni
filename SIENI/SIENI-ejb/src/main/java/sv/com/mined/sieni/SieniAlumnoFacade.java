/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public List<SieniAlumno> findAlumnoRpt(Date desde, Date hasta, Long grado, Long seccion, Integer matriculadoAnioActual) {
        Character estado = 'I';
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
                switch (matriculadoAnioActual) {
                    case 0:
                        q = em.createNamedQuery("SieniAlumno.findAnioGrado");
                        break;
                    case 1:
                        q = em.createNamedQuery("SieniAlumno.findAnioGradoMatriculadoActual");
                        break;
                    default:
                        q = em.createNamedQuery("SieniAlumno.findAnioGradoNoMatriculadoActual");
                        break;
                }
                break;
            case 2:
                switch (matriculadoAnioActual) {
                    case 0:
                        q = em.createNamedQuery("SieniAlumno.findAnioGradoSeccion");
                        break;
                    case 1:
                        q = em.createNamedQuery("SieniAlumno.findAnioGradoSeccionMatriculadoActual");
                        break;
                    default:
                        q = em.createNamedQuery("SieniAlumno.findAnioGradoSeccionNoMatriculadoActual");
                        break;
                }
                break;
            default:
                switch (matriculadoAnioActual) {
                    case 0:
                        q = em.createNamedQuery("SieniAlumno.findAnio");
                        break;
                    case 1:
                        q = em.createNamedQuery("SieniAlumno.findAnioMatriculadoActual");
                        break;
                    default:
                        q = em.createNamedQuery("SieniAlumno.findAnioNoMatriculadoActual");
                        break;
                }
                break;
        }
        q.setParameter("estado", estado);
        q.setParameter("anioDesde", desde);
        q.setParameter("anioHasta", hasta);
        if (grado != null && matriculadoAnioActual != 2) {
            q.setParameter("grado", grado);
            if (seccion != null && matriculadoAnioActual != 2) {
                q.setParameter("seccion", seccion);
            }
        }
        if (matriculadoAnioActual > 0) {
            q.setParameter("anio", getFormatedAnio(new Date()));
        }

        List<SieniAlumno> res = q.getResultList();
        return res;
    }

    public String getFormatedAnio(Date fecha) {
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy");
        String ret = null;
        if (fecha != null) {
            try {
                ret = dt1.format(fecha);
            } catch (Exception ex) {
                Logger.getLogger(SieniAlumnoFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

    @Override
    public List<SieniAlumno> findUsuariosRpt(Integer estadoUser) {
        Query q = em.createNamedQuery("SieniAlumno.findRptUsuariosAlumnos");
        switch(estadoUser){
            case 0: //TODOS
                q = em.createNamedQuery("SieniAlumno.findRptUsuariosAlumnos");
                break;
            case 1: //ACTIVOS
                q = em.createNamedQuery("SieniAlumno.findRptUsuariosAlumnosByEstado");
                q.setParameter("alEstado", 'A');
                break;
            case 2: //INACTIVOS
                q = em.createNamedQuery("SieniAlumno.findRptUsuariosAlumnosByEstado");
                q.setParameter("alEstado", 'I');
                break;
        }
        return q.getResultList();
    }

    @Override
    public boolean alumnoRegistrado(SieniAlumno alumno) {
        // es necesario instalar la siguiente extencion
        //CREATE EXTENSION unaccent;
        boolean ret = false;
        Character estado = 'I';
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SieniAlumno> q = cb.createQuery(SieniAlumno.class);
        Root<SieniAlumno> c = q.from(SieniAlumno.class);
        Predicate p = cb.conjunction();
        Path<String> primNombre = c.get("alPrimNombre");
        ParameterExpression<String> paramPrimNombre = cb.parameter(String.class);
        Path<String> segNombre = c.get("alSeguNombre");
        ParameterExpression<String> paramSegNombre = cb.parameter(String.class);
        Path<String> tercNombre = c.get("alTercNombre");
        ParameterExpression<String> paramTercNombre = cb.parameter(String.class);
        Path<String> primApellido = c.get("alPrimApe");
        ParameterExpression<String> paramPrimApellido = cb.parameter(String.class);
        Path<String> segApellido = c.get("alSeguApe");
        ParameterExpression<String> paramSegApellido = cb.parameter(String.class);
        Path<String> tercApellido = c.get("alTercApe");
        ParameterExpression<String> paramTercApellido = cb.parameter(String.class);
        Path<Character> pathEstado = c.get("alEstado");
        ParameterExpression<Character> paramEstado = cb.parameter(Character.class);
        p = cb.and(p, cb.equal(cb.function("unaccent", String.class, cb.lower(primNombre)), cb.function("unaccent", String.class, paramPrimNombre)));
        p = cb.and(p, cb.equal(cb.function("unaccent", String.class, cb.lower(segNombre)), cb.function("unaccent", String.class, paramSegNombre)));
        p = cb.and(p, cb.equal(cb.function("unaccent", String.class, cb.lower(tercNombre)), cb.function("unaccent", String.class, paramTercNombre)));
        p = cb.and(p, cb.equal(cb.function("unaccent", String.class, cb.lower(primApellido)), cb.function("unaccent", String.class, paramPrimApellido)));
        p = cb.and(p, cb.equal(cb.function("unaccent", String.class, cb.lower(segApellido)), cb.function("unaccent", String.class, paramSegApellido)));
        p = cb.and(p, cb.equal(cb.function("unaccent", String.class, cb.lower(tercApellido)), cb.function("unaccent", String.class, paramTercApellido)));
        p = cb.and(p, cb.notEqual(pathEstado, paramEstado));
        q.select(c).where(p);
        TypedQuery<SieniAlumno> query = em.createQuery(q);
        query.setParameter(paramPrimNombre, alumno.getAlPrimNombre() != null ? alumno.getAlPrimNombre().toLowerCase() : null);
        query.setParameter(paramSegNombre, alumno.getAlSeguNombre() != null ? alumno.getAlSeguNombre().toLowerCase() : null);
        query.setParameter(paramTercNombre, alumno.getAlTercNombre() != null ? alumno.getAlTercNombre().toLowerCase() : null);
        query.setParameter(paramPrimApellido, alumno.getAlPrimApe() != null ? alumno.getAlPrimApe().toLowerCase() : null);
        query.setParameter(paramSegApellido, alumno.getAlSeguApe() != null ? alumno.getAlSeguApe().toLowerCase() : null);
        query.setParameter(paramTercApellido, alumno.getAlTercApe() != null ? alumno.getAlTercApe().toLowerCase() : null);
        query.setParameter(paramEstado, estado);
        List<SieniAlumno> results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            ret = true;
        }
        return ret;
    }

    @Override
    public boolean alumnoRegistrados(List<SieniAlumno> alumnos) {
        boolean ret = false;
        for (SieniAlumno actual : alumnos) {

        }
        return ret;
    }

    @Override
    public Integer findSiguienteCorrelat(String inicial) {
        Integer ret = 0;
        Query q = em.createNamedQuery("SieniAlumno.findSiguienteCorrelat");
        q.setParameter("codigo", inicial);
        Integer resultado = (Integer) q.getSingleResult();
        if (resultado != null) {
            ret = resultado;
        }
        ret++;//siguiente correlativo
        return ret;
    }

    @Override
    public SieniAlumno findByNombreCompleto(String nombreCompleto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SieniAlumno> q = cb.createQuery(SieniAlumno.class);
        Root<SieniAlumno> c = q.from(SieniAlumno.class);
        Predicate p = cb.conjunction();
//        nombreCompleto="alberto francisco medina malcía ";
        Path<String> pathNombreCompleto = c.get("alNombreCompleto");
        ParameterExpression<String> nombre = cb.parameter(String.class);
        p = cb.and(p, cb.equal(cb.function("unaccent", String.class, cb.lower(pathNombreCompleto)), cb.function("unaccent", String.class, nombre)));
        q.select(c).where(p);
        TypedQuery<SieniAlumno> query = em.createQuery(q);
        query.setParameter(nombre, nombreCompleto != null ? nombreCompleto.toLowerCase() : null);
        List<SieniAlumno> res = query.getResultList();
        if (res != null && !res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SieniAlumno findAlumnoById(Integer id) {
        Query q = em.createNamedQuery("SieniAlumno.findAlumnoById");
        q.setParameter("id", id);
        SieniAlumno res = (SieniAlumno) q.getSingleResult();
        if (res != null) {
            return res;
        } else {
            return null;
        }
    }
    
    @Override
    public SieniAlumno findUsuario(String usuario) {
        Query q = em.createNamedQuery("SieniAlumno.findByAlUsuario");
        q.setParameter("alUsuario", usuario);
        List<SieniAlumno> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }
}
