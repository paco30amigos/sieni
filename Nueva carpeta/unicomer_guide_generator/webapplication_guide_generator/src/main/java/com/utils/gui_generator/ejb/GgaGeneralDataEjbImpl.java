/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.ejb;

import com.utils.gui_generator.model.GgaGeneralData;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.utils.gui_generator.ejb.local.GgaGeneralDataEjbImplLocal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author francisco_medina
 */
@Stateless
public class GgaGeneralDataEjbImpl extends AbstractFacade<GgaGeneralData> implements GgaGeneralDataEjbImplLocal {

    @PersistenceContext(unitName = "gga_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GgaGeneralDataEjbImpl() {
        super(GgaGeneralData.class);
    }

    @Override
    public List<GgaGeneralData> getAllGeneralDataFetch() {
        List<GgaGeneralData> ret;
        Query q = em.createNamedQuery("GgaGeneralData.findAllFetch");
        ret = q.getResultList();
        for (GgaGeneralData actual : ret) {
            em.refresh(actual);
        }
        return ret;
    }

}
