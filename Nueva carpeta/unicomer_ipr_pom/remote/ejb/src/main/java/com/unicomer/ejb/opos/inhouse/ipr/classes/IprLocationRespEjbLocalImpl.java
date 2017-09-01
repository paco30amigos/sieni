/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import com.unicomer.opos.inhouse.ipr.entities.IprLocationResp;
import com.unicomer.opos.inhouse.security.entities.UnRetailStore;
import com.unicomer.opos.inhouse.services.ejbs.AbstractFacade;
import java.math.BigInteger;
import java.util.ArrayList;
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
@Stateless(name = "IprLocationRespEjbLocalImpl", mappedName = "ejb/IprLocationRespEjbLocalImpl")
@Remote(IprLocationRespEjbRemote.class)
public class IprLocationRespEjbLocalImpl extends AbstractFacade<IprLocationResp> implements IprLocationRespEjbRemote {

    @PersistenceContext(unitName = "IhIprPersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IprLocationRespEjbLocalImpl() {
        super(IprLocationResp.class);
    }

    @Override
    public List<UnRetailStore> findStoresConfigured(String countryCode) {
        Query q = em.createNamedQuery("IprLocationResp.findStoresConfigured");
        q.setParameter("countryCode", countryCode);
        return q.getResultList();
    }

    @Override
    public IprLocationResp findId(BigInteger id) {
        Query q = em.createNamedQuery("IprLocationResp.findByLocationId");
        q.setParameter("locationId", id);
        List res = q.getResultList();
        IprLocationResp ret = null;
        if (res != null && !res.isEmpty()) {
            ret = (IprLocationResp) res.get(0);
        }
        return ret;
    }

    @Override
    public boolean updateLocationResponsableDefault(String storeId, String respId, Character respType) {
        boolean ret = true;
        Query q = null;
        List<IprLocationResp> locToUpdate = new ArrayList<>();

        q = em.createNamedQuery("IprLocationResp.findByStoreId");
        q.setParameter("storeId", storeId);
        locToUpdate = q.getResultList();

        if (locToUpdate != null && !locToUpdate.isEmpty()) {
            switch (respType) {
                case 'W':
                    for (IprLocationResp actual : locToUpdate) {
                        if (actual.getIsDefault() != null && actual.getIsDefault().equals('Y') && (actual.getWrhrId() == null || !actual.getWrhrId().equals(new BigInteger(respId)))) {
                            actual.setIsDefault('N');
                            edit(actual);
                        }
                    }
                    break;
                case 'E':
                    for (IprLocationResp actual : locToUpdate) {
                        if (actual.getIsDefault() != null && actual.getIsDefault().equals('Y') && (actual.getEmpId() == null || !actual.getEmpId().equals(respId))) {
                            actual.setIsDefault('N');
                            edit(actual);
                        }
                    }
                    break;
                case 'D':
                    for (IprLocationResp actual : locToUpdate) {
                        if (actual.getIsDefault() != null && actual.getIsDefault().equals('Y') && (actual.getDriverId() == null || !actual.getDriverId().equals(new BigInteger(respId)))) {
                            actual.setIsDefault('N');
                            edit(actual);
                        }
                    }
                    break;
            }
        }
        return ret;
    }
}
