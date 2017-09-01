/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import com.unicomer.opos.inhouse.ipr.entities.IprTrxLineItmProp;
import com.unicomer.opos.inhouse.services.ejbs.AbstractFacade;
import java.math.BigInteger;
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
@Stateless(name = "IprTrxLineItmPropEjbLocalImpl", mappedName = "ejb/IprTrxLineItmPropEjbLocalImpl")
@Remote(IprTrxLineItmPropEjbRemote.class)
public class IprTrxLineItmPropEjbLocalImpl extends AbstractFacade<IprTrxLineItmProp> implements IprTrxLineItmPropEjbRemote {

    @PersistenceContext(unitName = "IhIprPersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IprTrxLineItmPropEjbLocalImpl() {
        super(IprTrxLineItmProp.class);
    }

    @Override
    public IprTrxLineItmProp findByLineItemId(BigInteger lineItemId) {
        Query q = em.createNamedQuery("IprTrxLineItmProp.findByLineItemId");
        q.setParameter("lineItemId", lineItemId);
        List res = q.getResultList();
        IprTrxLineItmProp ret = null;
        if (res != null && !res.isEmpty()) {
            ret = (IprTrxLineItmProp) res.get(0);
        }
        return ret;
    }

}
