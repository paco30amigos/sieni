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
import sv.com.mined.sieni.pojos.ComponenteArchivoPojo;

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
        Query q = em.createNamedQuery("SieniArchivo.findArchivoLazy");
        q.setParameter("idArchivo", idArchivo);
        return (byte[]) q.getSingleResult();
    }

    @Override
    public List<SieniArchivo> findByIdSuperComp(Long idSuperCompon) {
        Query q = em.createNamedQuery("SieniArchivo.findByIdSuperComp");
        q.setParameter("idSuperCompon", idSuperCompon);
        return q.getResultList();
    }

    @Override
    public List<SieniArchivo> findByTipoArchivo(String tipo) {
        Query q = em.createNamedQuery("SieniArchivo.findByArTipoActivo");
        q.setParameter("arTipo", new Character(tipo.charAt(0)));
        return q.getResultList();
    }
}
