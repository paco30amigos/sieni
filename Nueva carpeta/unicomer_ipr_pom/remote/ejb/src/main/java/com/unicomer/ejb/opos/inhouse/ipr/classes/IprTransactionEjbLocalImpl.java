/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.unicomer.opos.inhouse.ipr.entities.IprTransaction;
import com.unicomer.opos.inhouse.ipr.entities.IprTransaction_;
import com.unicomer.opos.inhouse.ipr.entities.IprTrxLineItm;
import com.unicomer.opos.inhouse.ipr.entities.IprTrxLineItm_;
import com.unicomer.opos.inhouse.ipr.pojos.IprProductsResend;
import com.unicomer.opos.inhouse.services.ejbs.AbstractFacade;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder.In;

/**
 *
 * @author francisco_medina
 */
@Stateless(name = "IprTransactionEjbLocalImpl", mappedName = "ejb/IprTransactionEjbLocalImpl")
@Remote(IprTransactionEjbRemote.class)
public class IprTransactionEjbLocalImpl extends AbstractFacade<IprTransaction> implements IprTransactionEjbRemote {

    @PersistenceContext(unitName = "IhIprPersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IprTransactionEjbLocalImpl() {
        super(IprTransaction.class);
    }

    @Override
    public List<IprTransaction> findByFilters(HashMap<String, Object> params) {
        List<IprTransaction> ret = new ArrayList<>();
        IprTransaction a = new IprTransaction();
        String accNum = (String) params.get("accNum");
        BigInteger transactionId = (BigInteger) params.get("transactionId");
        String mainCtIde = (String) params.get("mainCtIde");
        String fsclCtIde = (String) params.get("fsclCtIde");
        Date dateMin = (Date) params.get("dateMin");
        Date dateMax = (Date) params.get("dateMax");
        List<BigInteger> statusId = (List<BigInteger>) params.get("statusId");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<IprTransaction> query = cb.createQuery(IprTransaction.class);
        Root<IprTransaction> from = query.from(IprTransaction.class);
        List<Predicate> predList = new LinkedList<Predicate>();
        query.orderBy(cb.desc(from.get(IprTransaction_.iprTransacId)));

        if (accNum != null && !accNum.isEmpty()) {
            predList.add(cb.equal(from.get(IprTransaction_.accNum), accNum));
        }
        if (transactionId != null) {
            predList.add(cb.equal(from.get(IprTransaction_.transactionId), transactionId));
        }
        if (fsclCtIde != null && !fsclCtIde.isEmpty()) {
            predList.add(cb.equal(from.get(IprTransaction_.fsclCtIde), fsclCtIde));
        }
        if (mainCtIde != null && !mainCtIde.isEmpty()) {
            predList.add(cb.equal(from.get(IprTransaction_.mainCtIde), mainCtIde));
        }
        if (statusId != null) {
            Fetch<IprTransaction, IprTrxLineItm> fetchIprTrxLine = from.fetch("iprTrxLineItmSet", JoinType.INNER);
            Join<IprTransaction, IprTrxLineItm> j = (Join) fetchIprTrxLine;
            Fetch<IprTrxLineItm, IprTransaction> fetchIprTransaction = j.fetch("transactionId", JoinType.INNER);
            In<BigInteger> in = cb.in(j.get(IprTrxLineItm_.statusId));
            for (BigInteger conditionColumnValue : statusId) {
                in.value(conditionColumnValue);
            }
            predList.add(in);
        }

        predList.add(cb.greaterThanOrEqualTo(from.get(IprTransaction_.transactionDate), dateMin));
        predList.add(cb.lessThanOrEqualTo(from.get(IprTransaction_.transactionDate), dateMax));

        query.distinct(true);
        if (!predList.isEmpty()) {
            query.where(predList.toArray(new Predicate[predList.size()]));
        }
        ret = em.createQuery(query).getResultList();

        return ret;
    }

    @Override
    public List<IprProductsResend> findProductSendByFilters(HashMap<String, Object> params) {
        List<IprProductsResend> ret = new ArrayList<>();
        String locale = (String) params.get("locale");
        String transactionId = (String) params.get("transactionId");
        Date dateMin = (Date) params.get("dateMin");
        Date dateMax = (Date) params.get("dateMax");
        BigInteger batchId = (BigInteger) params.get("batchId");
        BigInteger statusId = (BigInteger) params.get("statusId");

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String sql = "SELECT distinct BD.BATCH_DET_ID batchDetId, "
                + "  bd.batch_id batchNum, "
                + "  t.transaction_id transactionId, "
                + "  t.transaction_date dateTrx, "
                + "  TLI.UPC upc, "
                + "  b.brand_name brand, "
                + "  ph.model model, "
                + "  tli.quantity quantity, "
                + "  I.LABEL color, "
                + "  S.STATUS_DESC status, "
                + "  tli.line_item_id lineItemId "
                + "FROM admihipr.IPR_BATCH_DET bd "
                + "INNER JOIN admihipr.IPR_TRX_LINE_ITM tli "
                + "ON (bd.line_item_id=tli.line_item_id) "
                + "INNER JOIN admihipr.IPR_TRANSACTION t "
                + "ON (tli.transaction_id=t.ipr_transac_id) "
                + "INNER JOIN admihoth.oth_product_hist ph "
                + "ON TLI.PROD_HIST_ID=PH.HIST_PROD_ID "
                + "INNER JOIN admihoth.un_brand b "
                + "ON PH.BRAND_ID=b.brand_id "
                + "inner join IPR_STATUS s on (tli.status_id=s.status_id) "
                + "LEFT JOIN IPR_TRX_LINE_ITM_PROP tlip "
                + "ON (tlip.line_item_id=tli.line_item_id) "
                + "LEFT JOIN ADMIHOTH.OTH_I18N i "
                + "ON (i.reference_id=tlip.color_id) "
                + "AND I.LOCALE      =:locale "
                + "Where 1=1  ";
        if (transactionId != null) {
            sql += "and t.transaction_id=nvl(:transactionId,t.transaction_id) ";
        }
        sql += "and trunc(t.transaction_date)>=nvl(to_date(:dateMin,'DD/MM/YYYY'),trunc(T.TRANSACTION_DATE)) ";
        sql += "and trunc(t.transaction_date)<=nvl(to_date(:dateMax,'DD/MM/YYYY'),trunc(T.TRANSACTION_DATE)) ";
        if (batchId != null) {
            sql += "and BD.BATCH_ID=nvl(:batchId,BD.BATCH_ID) ";
        }
        if (statusId != null) {
            sql += "and s.status_id=nvl(:statusId,s.status_id) ";
        }
        sql += "ORDER BY BD.BATCH_DET_ID";

        Query q = em.createNativeQuery(sql, IprProductsResend.class);
        q.setParameter("locale", locale);
        if (transactionId != null) {
            q.setParameter("transactionId", transactionId);
        }

        q.setParameter("dateMin", df.format(dateMin));
        q.setParameter("dateMax", df.format(dateMax));
        if (batchId != null) {
            q.setParameter("batchId", batchId.toString());
        }
        if (statusId != null) {
            q.setParameter("statusId", statusId.toString());
        }
        ret = q.getResultList();
        return ret;
    }
}
