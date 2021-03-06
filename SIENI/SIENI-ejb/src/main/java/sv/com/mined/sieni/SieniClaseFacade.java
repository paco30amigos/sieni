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
        List<SieniClase> ret = q.getResultList();
        for (SieniClase actual : ret) {
            em.refresh(actual);
        }
        return ret;
    }

    @Override
    public List<SieniClase> findByDocente(Long idDocente) {
        Query q = em.createNamedQuery("SieniClase.findByDocente");
        q.setParameter("idDocente", idDocente);
        List<SieniClase> ret = q.getResultList();
        for (SieniClase actual : ret) {
            em.refresh(actual);
        }
        return ret;
    }

    @Override
    public List<SieniClase> findClaseByTipo(Character tipoClase, Long idDocente) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniClase.findClaseByTipoDocente");
        q.setParameter("idDocente", idDocente);
        q.setParameter("tipoClase", tipoClase);
        List<SieniClase> ret = q.getResultList();
        for (SieniClase actual : ret) {
            em.refresh(actual);
        }
        return ret;
    }

    @Override
    public List<SieniClase> findClaseByTipo(Character tipoClase) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniClase.findClaseByTipo");
        q.setParameter("estado", estado);
        q.setParameter("tipoClase", tipoClase);
        List<SieniClase> ret = q.getResultList();
        for (SieniClase actual : ret) {
            em.refresh(actual);
        }
        return ret;
    }

    @Override
    public List<SieniClase> findClasesRpt(SieniCurso curso, Integer tipo, Integer estado) {

        Query q = null;
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
        List<SieniClase> ret = q.getResultList();
        //for (SieniClase actual : ret) {
        //    em.refresh(actual);
        //}
        return ret;
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

        List<SieniClase> ret = q.getResultList();
        for (SieniClase actual : ret) {
            em.refresh(actual);
        }
        return ret;
    }

    @Override
    public List<SieniClase> findClaseByAlumno(Long idAlumno) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniClase.findClaseByAlumno");
//        List<SieniClase>  auc=em.createQuery("SELECT s FROM SieniClase s,SieniAlumno a join fetch a.sieniCursoAlumnoList ca where s.clEstado not in (:estado) and s.clTipo=:tipoClase and ca.idCurso.idCurso=s.idCurso.idCurso").setParameter("estado", estado).setParameter("tipoClase", tipoClase).getResultList();
//        em.refresh(em.createQuery("SELECT s FROM SieniClase s,SieniAlumno a join fetch a.sieniCursoAlumnoList ca where s.clEstado not in (:estado) and s.clTipo=:tipoClase and ca.idCurso=s.idCurso").setParameter("estado", estado).setParameter("tipoClase", tipoClase).getResultList().get(0).getIdCurso());
        q.setParameter("estado", estado);
        q.setParameter("idAlumno", idAlumno);

        List<SieniClase> ret = q.getResultList();
        for (SieniClase actual : ret) {
            em.refresh(actual);
        }
        return ret;
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

    @Override
    public List<SieniClase> findRptAvance(Long idAlumno) {
        Query q = em.createNamedQuery("SieniClase.rptAvanceClases");
        q.setParameter("idAlumno", idAlumno);
        List<SieniClase> res = q.getResultList();
        return res;
    }

    @Override
    public Boolean findByHorarioExiste(String horario, Date clHora) {
        boolean ban = true;
        Query q = em.createNamedQuery("SieniClase.findByHorarioExiste2");
//        q.setParameter("horario", horario);
        q.setParameter("clHora", clHora);

        String dias[] = horario.split(",");
        List<SieniClase> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            for (String diaHorarioNuevo : dias) {
                for (SieniClase actual : res) {
                    String[] horarioActual;
                    if (actual.getClHorario() != null && !actual.getClHorario().isEmpty()) {
                        horarioActual = actual.getClHorario().split(",");
                        for (String actual2 : horarioActual) {
                            if (diaHorarioNuevo.equals(actual2)) {
                                ban = false;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return ban;
    }
}
