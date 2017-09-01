/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.unicomer.opos.inhouse.ipr.entities.IprLocationHist;
import com.unicomer.opos.inhouse.ipr.pojos.IprLocationDet;
import com.unicomer.opos.inhouse.ipr.pojos.IprResponsableInfo;
import com.unicomer.opos.inhouse.services.ejbs.AbstractFacade;

/**
 *
 * @author francisco_medina
 */
@Stateless(name = "IprLocationHistEjbLocalImpl", mappedName = "ejb/IprLocationHistEjbLocalImpl")
@Remote(IprLocationHistEjbRemote.class)
public class IprLocationHistEjbLocalImpl extends AbstractFacade<IprLocationHist> implements IprLocationHistEjbRemote {

    @PersistenceContext(unitName = "IhIprPersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IprLocationHistEjbLocalImpl() {
        super(IprLocationHist.class);
    }

    @Override
    public List<IprLocationDet> getLastLocationByTrxLineItm(BigInteger lineItemId) {
        List<IprLocationDet> ret = new ArrayList<IprLocationDet>();
        String sql = "SELECT a.LOC_HIST_ID locHistId,a.line_item_id lineItemId, "
                + " a.store_id storeId, "
                + " s.retail_store_name storeName, "
                + " l.transaction_id iprTrxId, "
                + "  (SELECT audit_date"
                + "  FROM admihoth.oth_auditory"
                + "  WHERE audit_id="
                + "    (SELECT MAX(audit_id)"
                + "    FROM admihoth.oth_auditory"
                + "    WHERE audit_id      =a.audit_id"
                + "    OR inicial_audit_id = a.audit_id"
                + "    )"
                + "  ) lastModDate,"
                + "  NULL storeResponsable,"
                + "  NULL storeRespCode,"
                + "  NULL responsableRole,"
                + "  NULL defaultResp,"
                + "  NULL storeRespId,"
                + "  s.store_ri_code storeRiCode "
                + " FROM admihipr.ipr_location_hist a "
                + " INNER JOIN admihoth.un_retail_store s "
                + " ON (a.store_id =s.store_id) "
                + " INNER JOIN admihipr.IPR_TRX_LINE_ITM l "
                + " ON (a.line_item_id   =l.line_item_id) "
                + " WHERE a.loc_hist_id IN (SELECT MAX(b.loc_hist_id) "
                + " FROM admihipr.ipr_location_hist b "
                + " WHERE b.line_item_id=:lineItemId )";
        Query q = em.createNativeQuery(sql, IprLocationDet.class);
        q.setParameter("lineItemId", lineItemId);
        ret = q.getResultList();
        return ret;
    }

    @Override
    public List<IprLocationDet> getAllLocationByTrxLineItm(BigInteger lineItemId, String locale) {
        List<IprLocationDet> ret = new ArrayList<IprLocationDet>();
        String sql = "SELECT a.LOC_HIST_ID locHistId, "
                + "  a.line_item_id lineItemId, "
                + "  a.store_id storeId, "
                + "  s.retail_store_name storeName, "
                + "  l.transaction_id iprTrxId,"
                + "  a.resp_type, "
                + "  NVL( "
                + "  CASE "
                + "    WHEN a.resp_type = 'E' "
                + "    THEN "
                + "      (SELECT e.emp_name FROM admihoth.oth_employee e WHERE a.emp_id=e.emp_id "
                + "      ) "
                + "    WHEN a.resp_type = 'W' "
                + "    THEN "
                + "      (SELECT w.wrhr_name "
                + "      FROM admihoth.oth_warehouse_resp w "
                + "      WHERE a.wrhr_id=w.wrhr_id "
                + "      ) "
                + "    WHEN a.resp_type = 'D' "
                + "    THEN "
                + "      (SELECT d.driver_name FROM admihoth.oth_driver d WHERE a.driver_id=d.driver_id "
                + "      ) "
                + "  END,'') storeResponsable, "
                + "  NVL( "
                + "  CASE "
                + "    WHEN a.resp_type = 'E' "
                + "    THEN "
                + "      (SELECT e.emp_id FROM admihoth.oth_employee e WHERE a.emp_id=e.emp_id "
                + "      ) "
                + "    WHEN a.resp_type = 'W' "
                + "    THEN "
                + "      (SELECT to_char(w.wrhr_code) "
                + "      FROM admihoth.oth_warehouse_resp w "
                + "      WHERE a.wrhr_id=w.wrhr_id "
                + "      ) "
                + "    WHEN a.resp_type = 'D' "
                + "    THEN "
                + "      (SELECT to_char(d.driver_code) FROM admihoth.oth_driver d WHERE a.driver_id=d.driver_id "
                + "      ) "
                + "  END,'') storeRespCode, "
                + "  NVL(     "
                + "                     CASE     "
                + "                       WHEN a.resp_type = 'E'     "
                + "                       THEN     "
                + "                         (a.emp_id "
                + "                         )     "
                + "                       WHEN a.resp_type = 'W'     "
                + "                       THEN     "
                + "                         (to_char(a.wrhr_id) "
                + "                         )     "
                + "                       WHEN a.resp_type = 'D'     "
                + "                       THEN     "
                + "                         (to_char(a.driver_id) "
                + "                         )     "
                + "                     END,'') storeRespId,"
                + "  (select i.label from admihoth.OTH_I18N i where i.reference_id=A.RESP_ROLE and i.locale=:locale) responsableRole, "
                + "  (SELECT audit_date "
                + "  FROM admihoth.oth_auditory "
                + "  WHERE audit_id= "
                + "    (SELECT MAX(audit_id) "
                + "    FROM admihoth.oth_auditory "
                + "    WHERE audit_id      =a.audit_id "
                + "    OR inicial_audit_id = a.audit_id "
                + "    ) "
                + "  ) lastModDate ,"
                + "  NULL defaultResp,"
                + "  s.store_ri_code storeRiCode "
                + " FROM admihipr.ipr_location_hist a "
                + " INNER JOIN admihoth.un_retail_store s "
                + " ON (a.store_id =s.store_id) "
                + " INNER JOIN admihipr.IPR_TRX_LINE_ITM l "
                + " ON (a.line_item_id  =l.line_item_id) "
                + " WHERE a.line_item_id=:lineItemId";
        Query q = em.createNativeQuery(sql, IprLocationDet.class);
        q.setParameter("lineItemId", lineItemId);
        q.setParameter("locale", locale);
        ret = q.getResultList();
        return ret;
    }

    @Override
    public List<IprResponsableInfo> getAllResponsableByStore(BigInteger storeId, String locale) {
        List<IprResponsableInfo> ret = new ArrayList<IprResponsableInfo>();
        String sql = "SELECT  "
                + "  a.location_id locationId, "
                + "  a.store_id storeId, "
                + "  s.retail_store_name storeName, "
                + "  a.resp_type, "
                + "  NVL( "
                + "  CASE "
                + "    WHEN a.resp_type = 'E' "
                + "    THEN (e.emp_name ) "
                + "    WHEN a.resp_type = 'W' "
                + "    THEN (w.wrhr_name ) "
                + "    WHEN a.resp_type = 'D' "
                + "    THEN (d.driver_name ) "
                + "  END,'') storeResponsable, "
                + "  NVL( "
                + "  CASE "
                + "    WHEN a.resp_type = 'E' "
                + "    THEN (e.emp_id ) "
                + "    WHEN a.resp_type = 'W' "
                + "    THEN (TO_CHAR(w.wrhr_code) ) "
                + "    WHEN a.resp_type = 'D' "
                + "    THEN (TO_CHAR(d.driver_code) ) "
                + "  END,'') storeRespCode, "
                + "  NVL( "
                + "  CASE "
                + "    WHEN a.resp_type = 'E' "
                + "    THEN (a.emp_id ) "
                + "    WHEN a.resp_type = 'W' "
                + "    THEN (TO_CHAR(a.wrhr_id) ) "
                + "    WHEN a.resp_type = 'D' "
                + "    THEN (TO_CHAR(a.driver_id) ) "
                + "  END,'') storeRespId, "
                + "  NVL( "
                + "  CASE "
                + "    WHEN a.resp_type = 'E' "
                + "    THEN (NULL ) "
                + "    WHEN a.resp_type = 'W' "
                + "    THEN (w.wrhr_observ ) "
                + "    WHEN a.resp_type = 'D' "
                + "    THEN (d.driver_observ ) "
                + "  END,'') observationResponsability, "
                + "  (SELECT i.label "
                + "  FROM admihoth.OTH_I18N i "
                + "  WHERE i.reference_id=A.RESP_ROLE "
                + "  AND i.locale        =:locale "
                + "  ) responsableRole, "
                + "  (SELECT audit_date "
                + "  FROM admihoth.oth_auditory "
                + "  WHERE audit_id= "
                + "    (SELECT MAX(audit_id) "
                + "    FROM admihoth.oth_auditory "
                + "    WHERE audit_id      =a.audit_id "
                + "    OR inicial_audit_id = a.audit_id "
                + "    ) "
                + "  ) lastModDate, "
                + "  A.IS_DEFAULT defaultResp,"
                + "  a.resp_role responsableRoleId, "
                + "  s.store_ri_code storeRiCode "
                + "FROM admihipr.IPR_LOCATION_RESP a "
                + "INNER JOIN admihoth.un_retail_store s "
                + "ON (a.store_id =s.store_id) "
                + "LEFT JOIN admihoth.oth_employee e "
                + "ON (A.EMP_ID=e.emp_id) "
                + "LEFT JOIN admihoth.oth_driver d "
                + "ON (d.driver_id=a.driver_id) "
                + "LEFT JOIN admihoth.oth_warehouse_resp w "
                + "ON (w.wrhr_id     =a.wrhr_id) "
                + "WHERE A.STORE_ID  =:storeId "
                + "AND (a.driver_id IS NOT NULL "
                + "OR a.wrhr_id     IS NOT NULL "
                + "OR a.emp_id      IS NOT NULL) "
                + "AND a.is_active   ='Y'";
        Query q = em.createNativeQuery(sql, IprResponsableInfo.class);
        q.setParameter("storeId", storeId);
        q.setParameter("locale", locale);
        ret = q.getResultList();
        return ret;
    }

    @Override
    public List<IprLocationDet> getFirstLocationByIprTrx(BigInteger iprTrxId) {
        List<IprLocationDet> ret = new ArrayList<IprLocationDet>();
        String sql = "SELECT a.loc_hist_id locHistId,a.line_item_id lineItemId, "
                + " a.store_id storeId, " + " s.retail_store_name storeName, "
                + " l.transaction_id iprTrxId, "
                + " NVL( "
                + " CASE "
                + " WHEN a.resp_type = 'E' "
                + " THEN "
                + " (SELECT e.emp_name FROM admihoth.oth_employee e WHERE a.emp_id=e.emp_id "
                + "       ) "
                + " WHEN a.resp_type = 'W' "
                + " THEN "
                + " (SELECT w.wrhr_name "
                + " FROM admihoth.oth_warehouse_resp w "
                + " WHERE a.wrhr_id=w.wrhr_id "
                + "       ) "
                + " WHEN a.resp_type = 'D' "
                + " THEN "
                + " (SELECT d.driver_name FROM admihoth.oth_driver d WHERE a.driver_id=d.driver_id "
                + " ) END,'') storeResponsable,"
                + " (SELECT audit_date"
                + "  FROM admihoth.oth_auditory"
                + "  WHERE audit_id=a.audit_id"
                + "  ) lastModDate,"
                + "  NULL storeRespCode,"
                + "  NULL responsableRole, "
                + "  NULL defaultResp,"
                + "  NULL storeRespId,"
                + "  s.store_ri_code storeRiCode "
                + " FROM admihipr.ipr_location_hist a "
                + " INNER JOIN admihoth.un_retail_store s "
                + " ON (a.store_id =s.store_id) "
                + " INNER JOIN admihipr.IPR_TRX_LINE_ITM l "
                + " ON (a.line_item_id   =l.line_item_id) "
                + " WHERE a.loc_hist_id IN (SELECT MIN(b.loc_hist_id) "
                + " FROM admihipr.ipr_location_hist b "
                + " INNER JOIN admihipr.IPR_TRX_LINE_ITM l2 "
                + " ON (b.line_item_id     =l2.line_item_id) "
                + " WHERE l2.transaction_id=:iprTransacId )";
        Query q = em.createNativeQuery(sql, IprLocationDet.class);
        q.setParameter("iprTransacId", iprTrxId);
        ret = q.getResultList();
        return ret;
    }

    @Override
    public IprLocationHist findById(BigInteger id) {
        IprLocationHist ret = null;
        Query q = em.createNamedQuery("IprLocationHist.findByLocHistId");
        q.setParameter("locHistId", id);
        List res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            ret = (IprLocationHist) res.get(0);
        }
        return ret;
    }

}
