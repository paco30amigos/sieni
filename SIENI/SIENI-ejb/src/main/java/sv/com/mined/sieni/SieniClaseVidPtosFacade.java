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
import sv.com.mined.sieni.model.SieniClaseVidPtos;

/**
 *
 * @author francisco_medina
 */
@Stateless
public class SieniClaseVidPtosFacade extends AbstractFacade<SieniClaseVidPtos> implements sv.com.mined.sieni.SieniClaseVidPtosFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniClaseVidPtosFacade() {
        super(SieniClaseVidPtos.class);
    }

    @Override
    public List<SieniClaseVidPtos> findByClase(Long idClase) {
        Character estado = 'I';
        Query q = em.createNamedQuery("SieniClaseVidPtos.findByClase");
        q.setParameter("estado", estado);
        q.setParameter("idClase", idClase);
        return q.getResultList();
    }

    @Override
    public void merge(List<SieniClaseVidPtos> lista, List<SieniClaseVidPtos> eliminados) {
        for (SieniClaseVidPtos actual : lista) {
            if (actual.getIdClaseVideoPtosAct() != null && actual.getIdClaseVideoPtosAct() < 0) {
                actual.setIdClaseVideoPtosAct(null);
            }
            if (actual.getIdClaseVideoPtosAct() != null) {
                this.edit(actual);
            } else {
                this.create(actual);
            }
        }

        for (SieniClaseVidPtos actual : eliminados) {
            if (actual.getIdClaseVideoPtosAct() != null && actual.getIdClaseVideoPtosAct() < 0) {
                actual.setIdClaseVideoPtosAct(null);
            }
            if (actual.getIdClaseVideoPtosAct() != null) {
                actual.setVpEstado('I');//eliminacion logica
                this.edit(actual);
            }
        }
        em.flush();
    }
}
