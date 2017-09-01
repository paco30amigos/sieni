/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import com.unicomer.opos.inhouse.ipr.entities.IprStatusProd;
import com.unicomer.opos.inhouse.services.ejbs.AbstractFacade;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author francisco_medina
 */
@Stateless(name = "IprStatusProdEjbLocalImpl", mappedName = "ejb/IprStatusProdEjbLocalImpl")
@Remote(IprStatusProdEjbRemote.class)
public class IprStatusProdEjbLocalImpl extends AbstractFacade<IprStatusProd> implements IprStatusProdEjbRemote {
    @PersistenceContext(unitName = "IhIprPersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IprStatusProdEjbLocalImpl() {
        super(IprStatusProd.class);
    }
    
    
    @Override
    public List<IprStatusProd> findAllOrdered(){
        Query q=em.createNamedQuery("IprStatusProd.findAllOrdered");
        return q.getResultList();
    }
    
    @Override
    public List<IprStatusProd> findActiveOrdered(){
        Query q=em.createNamedQuery("IprStatusProd.findActiveOrdered");
        return q.getResultList();
    }
}
