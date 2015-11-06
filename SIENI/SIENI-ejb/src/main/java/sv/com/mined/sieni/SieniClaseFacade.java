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
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniCurso;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniClaseFacade extends AbstractFacade<SieniClase> implements sv.com.mined.sieni.SieniClaseFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniClaseFacade() {
        super(SieniClase.class);
    }

    @Override
    public List<SieniClase> findAllNoInactivos() {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniClase.findAllNoInactivos");
        q.setParameter("estado", estado);
        return q.getResultList();
    }

    @Override
    public List<SieniClase> findClaseByTipo(Character tipoClase) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniClase.findClaseByTipo");
        q.setParameter("estado", estado);
        q.setParameter("tipoClase", tipoClase);
        return q.getResultList();
    }

    @Override
    public List<SieniClase> findClasesRpt(SieniCurso curso, Integer tipo, Integer estado) {

        Query q = em.createNamedQuery("SieniClase.findClasesRpt");
        if (curso == null) {// TODOS LOS CURSOS
            if (tipo.intValue() == 0 && estado.intValue() == 0) {
                q = em.createNamedQuery("SieniClase.findClasesRpt");
            } else if (tipo.intValue() != 0 && estado.intValue() == 0) {
                q = em.createNamedQuery("SieniClase.findClasesRptByTipo");
                switch (tipo) {
                    case 0:
                        q.setParameter("clTipo", 'X');
                        break;
                    case 1:
                        q.setParameter("clTipo", 'O');
                        break;
                    case 2:
                        q.setParameter("clTipo", 'V');
                        break;
                    case 3:
                        q.setParameter("clTipo", 'I');
                        break;
                }

            } else if (tipo.intValue() == 0 && estado.intValue() != 0) {
                q = em.createNamedQuery("SieniClase.findClasesRptByEstado");

            } else {
                q = em.createNamedQuery("SieniClase.findClasesRptByTipoEstado");
                switch (tipo) {
                    case 0:
                        q.setParameter("clTipo", 'X');
                        break;
                    case 1:
                        q.setParameter("clTipo", 'O');
                        break;
                    case 2:
                        q.setParameter("clTipo", 'V');
                        break;
                    case 3:
                        q.setParameter("clTipo", 'I');
                        break;
                }
                switch (estado) {
                    case 0:
                        q.setParameter("clEstado", 'X');
                        break;
                    case 1:
                        q.setParameter("clEstado", 'N');
                        break;
                    case 2:
                        q.setParameter("clEstado", 'A');
                        break;
                    case 3:
                        q.setParameter("clEstado", 'T');
                        break;
                }
            }
        } else {
            if (tipo.intValue() == 0 && estado.intValue() == 0) {
                q = em.createNamedQuery("SieniClase.findClasesRptByCurso");
                q.setParameter("idCurso", curso.getIdCurso());
            } else if (tipo.intValue() != 0 && estado.intValue() == 0) {
                q = em.createNamedQuery("SieniClase.findClasesRptByCursoTipo");
                q.setParameter("idCurso", curso.getIdCurso());
                switch (tipo) {
                    case 0:
                        q.setParameter("clTipo", 'X');
                        break;
                    case 1:
                        q.setParameter("clTipo", 'O');
                        break;
                    case 2:
                        q.setParameter("clTipo", 'V');
                        break;
                    case 3:
                        q.setParameter("clTipo", 'I');
                        break;
                }
            } else if (tipo.intValue() == 0 && estado.intValue() != 0) {
                q = em.createNamedQuery("SieniClase.findClasesRptByCursoEstado");
                q.setParameter("idCurso", curso.getIdCurso());
                switch (estado) {
                    case 0:
                        q.setParameter("clEstado", 'X');
                        break;
                    case 1:
                        q.setParameter("clEstado", 'N');
                        break;
                    case 2:
                        q.setParameter("clEstado", 'A');
                        break;
                    case 3:
                        q.setParameter("clEstado", 'T');
                        break;
                }
            } else {
                q = em.createNamedQuery("SieniClase.findClasesRptByCursoTipoEstado");
                q.setParameter("idCurso", curso.getIdCurso());
                switch (tipo) {
                    case 0:
                        q.setParameter("clTipo", 'X');
                        break;
                    case 1:
                        q.setParameter("clTipo", 'O');
                        break;
                    case 2:
                        q.setParameter("clTipo", 'V');
                        break;
                    case 3:
                        q.setParameter("clTipo", 'I');
                        break;
                }
                switch (estado) {
                    case 0:
                        q.setParameter("clEstado", 'X');
                        break;
                    case 1:
                        q.setParameter("clEstado", 'N');
                        break;
                    case 2:
                        q.setParameter("clEstado", 'A');
                        break;
                    case 3:
                        q.setParameter("clEstado", 'T');
                        break;
                }
            }
        }
        return q.getResultList();
    }

    @Override
    public List<SieniClase> findClaseByTipoAlumno(Character tipoClase, Long idAlumno) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniClase.findClaseByTipoAlumno");
//        List<SieniClase>  auc=em.createQuery("SELECT s FROM SieniClase s,SieniAlumno a join fetch a.sieniCursoAlumnoList ca where s.clEstado not in (:estado) and s.clTipo=:tipoClase and ca.idCurso.idCurso=s.idCurso.idCurso").setParameter("estado", estado).setParameter("tipoClase", tipoClase).getResultList();
//        em.refresh(em.createQuery("SELECT s FROM SieniClase s,SieniAlumno a join fetch a.sieniCursoAlumnoList ca where s.clEstado not in (:estado) and s.clTipo=:tipoClase and ca.idCurso=s.idCurso").setParameter("estado", estado).setParameter("tipoClase", tipoClase).getResultList().get(0).getIdCurso());
        q.setParameter("estado", estado);
        q.setParameter("tipoClase", tipoClase);
        q.setParameter("idAlumno", idAlumno);

        return q.getResultList();
    }

    @Override
    public List<SieniClase> findClaseByAlumno(Long idAlumno) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniClase.findClaseByAlumno");
//        List<SieniClase>  auc=em.createQuery("SELECT s FROM SieniClase s,SieniAlumno a join fetch a.sieniCursoAlumnoList ca where s.clEstado not in (:estado) and s.clTipo=:tipoClase and ca.idCurso.idCurso=s.idCurso.idCurso").setParameter("estado", estado).setParameter("tipoClase", tipoClase).getResultList();
//        em.refresh(em.createQuery("SELECT s FROM SieniClase s,SieniAlumno a join fetch a.sieniCursoAlumnoList ca where s.clEstado not in (:estado) and s.clTipo=:tipoClase and ca.idCurso=s.idCurso").setParameter("estado", estado).setParameter("tipoClase", tipoClase).getResultList().get(0).getIdCurso());
        q.setParameter("estado", estado);
        q.setParameter("idAlumno", idAlumno);

        return q.getResultList();
    }

    @Override
    public void merge(List<SieniClase> clases) {
        for (SieniClase actual : clases) {
            if (actual.getIdClase() != null) {
                this.edit(actual);
            } else {
                this.create(actual);
            }
        }
        em.flush();
    }
}
