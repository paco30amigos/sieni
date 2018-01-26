/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.ejb;

import com.utils.gui_generator.ejb.local.GgaGeneraldataLinkEjbImplLocal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.utils.gui_generator.model.GgaGeneraldataLink;

/**
 *
 * @author francisco_medina
 */
@Stateless
public class GgaGeneraldataLinkEjbImpl extends AbstractFacade<GgaGeneraldataLink> implements GgaGeneraldataLinkEjbImplLocal {

    @PersistenceContext(unitName = "gga_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GgaGeneraldataLinkEjbImpl() {
        super(GgaGeneraldataLink.class);
    }
    
}
