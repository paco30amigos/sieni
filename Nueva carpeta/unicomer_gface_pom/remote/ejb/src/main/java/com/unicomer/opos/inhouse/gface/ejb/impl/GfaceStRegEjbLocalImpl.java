package com.unicomer.opos.inhouse.gface.ejb.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.unicomer.opos.inhouse.gface.ejb.GfaceStRegEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceStReg;
import com.unicomer.opos.inhouse.services.ejbs.AbstractFacade;

/**
 * 
 * @author nelsong_lopez
 */
@Transactional(value = TxType.REQUIRED)
@Stateless(name = "GfaceStRegEjbLocalImpl", mappedName = "ejb/GfaceStRegEjbLocalImpl")
@Remote(GfaceStRegEjbLocal.class)
public class GfaceStRegEjbLocalImpl extends AbstractFacade<GfaceStReg> implements GfaceStRegEjbLocal {

    @PersistenceContext(unitName = "EAgfacePersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GfaceStRegEjbLocalImpl() {
        super(GfaceStReg.class);
    }

	
}
