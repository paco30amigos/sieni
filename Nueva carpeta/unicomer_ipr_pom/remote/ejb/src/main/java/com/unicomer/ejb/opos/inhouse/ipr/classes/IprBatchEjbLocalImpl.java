/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import com.unicomer.opos.inhouse.ipr.entities.IprBatch;
import com.unicomer.opos.inhouse.services.ejbs.AbstractFacade;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author francisco_medina
 */
@Stateless(name = "IprBatchEjbLocalImpl", mappedName = "ejb/IprBatchEjbLocalImpl")
@Remote(IprBatchEjbRemote.class)
public class IprBatchEjbLocalImpl extends AbstractFacade<IprBatch> implements IprBatchEjbRemote {
    @PersistenceContext(unitName = "IhIprPersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IprBatchEjbLocalImpl() {
        super(IprBatch.class);
    }
    
}