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
import sv.com.mined.sieni.model.SieniArchivo;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniArchivoFacade extends AbstractFacade<SieniArchivo> implements sv.com.mined.sieni.SieniArchivoFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniArchivoFacade() {
        super(SieniArchivo.class);
    }

    @Override
    public byte[] getArchivoLazy(Long idArchivo) {
        String estado = "I";
        Query q = em.createNamedQuery("SieniArchivo2.findArchivoLazy");
        q.setParameter("idArchivo", idArchivo);
        q.setParameter("estado", estado);        
//        em.refresh(q.getSingleResult());
        byte[] ret=(byte[])q.getSingleResult();
        return ret;
    }

    @Override
    public List<SieniArchivo> findAllNoInactivos() {
        String estado = "I";
        Query q = em.createNamedQuery("SieniArchivo.findAllNoInactivos");
        q.setParameter("estado", estado);
        List<SieniArchivo> ret = q.getResultList();
        for (SieniArchivo actual : ret) {
            em.refresh(actual);
        }
        return ret;
    }

    @Override
    public List<SieniArchivo> findByIdSuperComp(Long idSuperCompon) {
        String estado = "I";
        Query q = em.createNamedQuery("SieniArchivo.findByIdSuperComp");
        q.setParameter("idSuperCompon", idSuperCompon);
        q.setParameter("estado", estado);
        List<SieniArchivo> ret = q.getResultList();
        for (SieniArchivo actual : ret) {
            em.refresh(actual);
        }
        return ret;
    }

    @Override
    public List<SieniArchivo> findByTipoArchivo(String tipo) {
//        String estado = "I";
        Query q = em.createNamedQuery("SieniArchivo.findByArTipoActivo");
        q.setParameter("arTipo", new Character(tipo.charAt(0)));
//        q.setParameter("estado", estado);
        List<SieniArchivo> ret = q.getResultList();
        for (SieniArchivo actual : ret) {
            em.refresh(actual);
        }
        return ret;
    }

    @Override
    public List<SieniArchivo> merge(List<SieniArchivo> lista, List<SieniArchivo> eliminados) {
        for (SieniArchivo actual : lista) {
            if (actual.getIdArchivo() != null) {
                this.edit(actual);
            } else {
                this.create(actual);
            }
        }

        for (SieniArchivo actual : eliminados) {
            if (actual.getIdArchivo() != null) {
                actual.setArEstado("I");//eliminacion logica
                this.edit(actual);
            }
        }
        em.flush();

        return lista;
    }

    @Override
    public SieniArchivo merge(SieniArchivo dato) {

        if (dato.getIdArchivo() != null) {
            this.edit(dato);
        } else {
            this.create(dato);
        }
        return dato;
    }

    @Override
    public SieniArchivo findByNombre(String nombre) {
        SieniArchivo ret = null;
        Query q = em.createNamedQuery("SieniArchivo.findByNombre");
        q.setParameter("nombre", nombre);

        List<SieniArchivo> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            ret = res.get(0);
            em.refresh(ret);
        }
        return ret;
    }
}
