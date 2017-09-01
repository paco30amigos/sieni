/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.unicomer.opos.inhouse.ipr.entities.IprTrxLineItm;
import com.unicomer.opos.inhouse.ipr.pojos.IprProduct;
import com.unicomer.opos.inhouse.services.ejbs.AbstractFacade;
import java.util.ArrayList;

/**
 *
 * @author francisco_medina
 */
@Stateless(name = "IprTrxLineItmEjbLocalImpl", mappedName = "ejb/IprTrxLineItmEjbLocalImpl")
@Remote(IprTrxLineItmEjbRemote.class)
public class IprTrxLineItmEjbLocalImpl extends AbstractFacade<IprTrxLineItm> implements IprTrxLineItmEjbRemote {

    @PersistenceContext(unitName = "IhIprPersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IprTrxLineItmEjbLocalImpl() {
        super(IprTrxLineItm.class);
    }

    @Override
    public void finishReadyProducts() {
        BigInteger infUpd = new BigInteger("2");
        BigInteger locUpd = new BigInteger("3");
        BigInteger finished = new BigInteger("4");
        Query q = em.createNamedQuery("IprTrxLineItm.findByStatusId");
        q.setParameter("statusId", infUpd);

        List<IprTrxLineItm> updateList = q.getResultList();
        q = em.createNamedQuery("IprTrxLineItm.findByStatusId");
        q.setParameter("statusId", locUpd);
        updateList.addAll(q.getResultList());

        for (IprTrxLineItm actual : updateList) {
            actual.setStatusId(finished);
        }

        if (updateList != null && !updateList.isEmpty()) {
            merge(updateList);
        }
    }

    @Override
    public List<IprTrxLineItm> findItemsByStatusAndTransac(List<BigInteger> status, BigInteger transactionId) {
        Query q = em.createNamedQuery("IprTrxLineItm.findItemsByStatusAndTransac");
        q.setParameter("statusId", status);
        q.setParameter("transactionId", transactionId);
        List<IprTrxLineItm> ret = q.getResultList();
        return ret;
    }

    @Override
    public List<IprProduct> findProductList(List<BigInteger> status, BigInteger transactionId, String locale) {
        Query q = em.createNativeQuery("select distinct ph.upc upc,b.brand_name brand, ph.model model,tli.quantity quantity,i.label color,sp.status_desc status,tli.line_item_id lineItemId,TLIP.ACCESO_ID itemPropId  from "
                + " IPR_TRX_LINE_ITM tli "
                + " join admihoth.OTH_PRODUCT_HIST ph on tli.prod_hist_id=ph.hist_prod_id "
                + " join ADMIHOTH.UN_BRAND b on ph.brand_id=b.brand_id "
                + " left join IPR_TRX_LINE_ITM_PROP TLIP on tli.line_item_id=tlip.line_item_id AND TLIP.IS_ACTIVE='Y' "
                + " left join admihoth.OTH_FIXED_DATA fd on fd.oth_fixed_data_id=TLIP.color_id "
                + " left join admihoth.OTH_I18N i on i.reference_id=fd.oth_fixed_data_id and i.locale=:locale "
                + " left join IPR_STATUS_PROD sp on TLIP.status_prod_id=sp.status_prod_id"
                + " where tli.status_id in(:statusId) "
                + " and tli.transaction_id=:transactionId order by tli.line_item_id", IprProduct.class);

        q.setParameter("statusId", status);
        q.setParameter("transactionId", transactionId);
        q.setParameter("locale", locale);
        List<IprProduct> ret = q.getResultList();
        return ret;
    }

    @Override
    public List<IprProduct> findProductByStatus(List<BigInteger> status, String locale) {
        Query q = em.createNativeQuery("select distinct ph.upc upc,b.brand_name brand, ph.model model,tli.quantity quantity,i.label color,sp.status_desc status,tli.line_item_id lineItemId,TLIP.ACCESO_ID itemPropId  from "
                + " IPR_TRX_LINE_ITM tli "
                + " join admihoth.OTH_PRODUCT_HIST ph on tli.prod_hist_id=ph.hist_prod_id "
                + " join ADMIHOTH.UN_BRAND b on ph.brand_id=b.brand_id "
                + " left join IPR_TRX_LINE_ITM_PROP TLIP on tli.line_item_id=tlip.line_item_id AND TLIP.IS_ACTIVE='Y' "
                + " left join admihoth.OTH_FIXED_DATA fd on fd.oth_fixed_data_id=TLIP.color_id "
                + " left join admihoth.OTH_I18N i on i.reference_id=fd.oth_fixed_data_id and i.locale=:locale "
                + " left join IPR_STATUS_PROD sp on TLIP.status_prod_id=sp.status_prod_id"
                + " where tli.status_id in(:statusId) "
                + " order by tli.line_item_id", IprProduct.class);

        q.setParameter("statusId", status);
        q.setParameter("locale", locale);
        List<IprProduct> ret = q.getResultList();
        return ret;
    }

    @Override
    public List<IprTrxLineItm> merge(List<IprTrxLineItm> items) {
        for (IprTrxLineItm actual : items) {
            edit(actual);
        }
        em.flush();
        return items;
    }

    @Override
    public List<IprTrxLineItm> findByLineItemId(List<BigInteger> lineItemIdList) {
        Query q = null;
        List<IprTrxLineItm> res;
        List<IprTrxLineItm> ret = new ArrayList<>();
        for (BigInteger actual : lineItemIdList) {
            q = em.createNamedQuery("IprTrxLineItm.findByLineItemId");
            q.setParameter("lineItemId", actual);
            res = q.getResultList();
            if (res != null) {
                ret.add(res.get(0));
            }
        }
        return ret;
    }

}
