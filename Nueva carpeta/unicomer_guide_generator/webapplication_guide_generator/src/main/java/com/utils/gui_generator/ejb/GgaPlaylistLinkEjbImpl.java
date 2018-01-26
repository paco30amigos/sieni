/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.ejb;

import com.utils.gui_generator.model.GgaPlaylistLink;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.utils.gui_generator.ejb.local.GgaPlaylistLinkEjbImplLocal;

/**
 *
 * @author francisco_medina
 */
@Stateless
public class GgaPlaylistLinkEjbImpl extends AbstractFacade<GgaPlaylistLink> implements GgaPlaylistLinkEjbImplLocal {

    @PersistenceContext(unitName = "gga_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GgaPlaylistLinkEjbImpl() {
        super(GgaPlaylistLink.class);
    }
    
}
