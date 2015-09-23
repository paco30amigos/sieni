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
import sv.com.mined.sieni.model.SieniMateriaDocente;

/**
 *
 * @author francisco_medina
 */
@Stateless
public class SieniMateriaDocenteFacade extends AbstractFacade<SieniMateriaDocente> implements sv.com.mined.sieni.SieniMateriaDocenteFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniMateriaDocenteFacade() {
        super(SieniMateriaDocente.class);
    }

    @Override
    public List<SieniMateriaDocente> findByDocente(Long idDocente) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniMateriaDocente.findByDocente");
        q.setParameter("idDocente", idDocente);
        q.setParameter("estado", estado);
        return q.getResultList();
    }

    @Override
    public void merge(List<SieniMateriaDocente> lista, List<SieniMateriaDocente> eliminados) {
        for (SieniMateriaDocente actual : lista) {
            if (actual.getIdMateriaDocente() < 0) {
                actual.setIdMateriaDocente(null);
            }
            if (actual.getIdMateriaDocente() != null) {
                this.edit(actual);
            } else {
                this.create(actual);
            }
        }

        for (SieniMateriaDocente actual : eliminados) {
            if (actual.getIdMateriaDocente() != null) {
                actual.setMdEstado('I');//eliminacion logica
                this.edit(actual);
            }
        }
        em.flush();
    }
}
