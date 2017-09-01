/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import com.unicomer.opos.inhouse.ipr.entities.IprStatus;
import com.unicomer.opos.inhouse.services.ejbs.AbstractFacade;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author francisco_medina
 */
@Stateless(name = "IprStatusEjbLocalImpl", mappedName = "ejb/IprStatusEjbLocalImpl")
@Remote(IprStatusEjbRemote.class)
public class IprStatusEjbLocalImpl extends AbstractFacade<IprStatus> implements IprStatusEjbRemote {
    @PersistenceContext(unitName = "IhIprPersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IprStatusEjbLocalImpl() {
        super(IprStatus.class);
    }
    
}
