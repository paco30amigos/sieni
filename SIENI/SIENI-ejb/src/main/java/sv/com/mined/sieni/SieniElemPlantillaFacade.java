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
import sv.com.mined.sieni.model.SieniElemPlantilla;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniElemPlantillaFacade extends AbstractFacade<SieniElemPlantilla> implements sv.com.mined.sieni.SieniElemPlantillaFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniElemPlantillaFacade() {
        super(SieniElemPlantilla.class);
    }

    @Override
    public List<SieniElemPlantilla> findByIdPlantilla(Long idPlantilla) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniElemPlantilla.findByIdPlantilla");
        q.setParameter("estado", estado);
        q.setParameter("idPlantilla", idPlantilla);
        List<SieniElemPlantilla> ret = q.getResultList();
        for (SieniElemPlantilla actual : ret) {
            em.refresh(actual);
        }
        return ret;        
    }

    @Override
    public void merge(List<SieniElemPlantilla> lista, List<SieniElemPlantilla> eliminados) {
        int orden=1;
        for (SieniElemPlantilla actual : lista) {
            if (actual.getIdElemPlantilla() < 0) {
                actual.setIdElemPlantilla(null);
            }
            actual.setEpOrden(orden++);
            if (actual.getIdElemPlantilla() != null) {
                this.edit(actual);
            } else {
                this.create(actual);
            }
        }

        for (SieniElemPlantilla actual : eliminados) {
            if(actual.getIdElemPlantilla()<0){
                actual.setIdElemPlantilla(null);
            }
            if (actual.getIdElemPlantilla() != null) {
                actual.setEpEstado('I');//eliminacion logica
                this.edit(actual);
            }
        }
        em.flush();
    }
}
