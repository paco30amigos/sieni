package com.unicomer.opos.inhouse.gface.ejb.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.unicomer.inhouse.jndi.JNDIUnicomerGface;
import com.unicomer.opos.inhouse.gface.ejb.GfaceHdrDocEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceStRegEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceDocErrDtl;
import com.unicomer.opos.inhouse.gface.entity.GfaceErrRegCtg;
import com.unicomer.opos.inhouse.gface.entity.GfaceErrRegCtg_;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc_;
import com.unicomer.opos.inhouse.gface.entity.GfaceStReg;
import com.unicomer.opos.inhouse.gface.entity.GfaceStReg_;
import com.unicomer.opos.inhouse.services.ejbs.AbstractFacade;

/**
*
* @author nelsong_lopez
*
*/
@Transactional(value = TxType.REQUIRED)
@Stateless(name = "GfaceHdrDocEjbLocalImpl", mappedName = "ejb/GfaceHdrDocEjbLocalImpl")
@Remote(GfaceHdrDocEjbLocal.class)
public class GfaceHdrDocEjbLocalImpl extends AbstractFacade<GfaceHdrDoc> implements GfaceHdrDocEjbLocal {

    @PersistenceContext(unitName = "EAgfacePersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Resource(lookup = JNDIUnicomerGface.GfaceStRegEjbLocal)
    private GfaceStRegEjbLocal gfaceStRegEjbLocal;

    public GfaceHdrDocEjbLocalImpl() {
        super(GfaceHdrDoc.class);
    }

    @Override
    public List<GfaceHdrDoc> getHeaderByStatus(HashMap<String, Object> params) {
        List<GfaceHdrDoc> ret = new ArrayList<GfaceHdrDoc>();

        if (params != null && !params.isEmpty()) {
            BigInteger rgsStCode = (BigInteger) params.get("rgsStCode");
            BigInteger flgSt = (BigInteger) params.get("flgSt");

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GfaceHdrDoc> query = cb.createQuery(GfaceHdrDoc.class);
            Root<GfaceHdrDoc> from = query.from(GfaceHdrDoc.class);
            List<Predicate> predList = new LinkedList<Predicate>();
//            from.fetch(GfaceHdrDoc_.btchId);
//            from.fetch(GfaceHdrDoc_.sttRgsId);
//            from.fetch("gfaceDocErrDtlList",JoinType.LEFT);
            from.fetch("gfaceDtlDocList",JoinType.LEFT);
            from.fetch("gfaceAssocDocList",JoinType.LEFT);
            from.fetch("gfaceDocErrDtlList",JoinType.LEFT);
            
            query.orderBy(cb.asc(from.get(GfaceHdrDoc_.hdrId)));
            
            if (flgSt != null) {
                predList.add(cb.equal(from.get(GfaceHdrDoc_.flgSt), flgSt));
            }
            if (rgsStCode != null) {
                //verifica que el estatus sea el proporcionado
                Subquery<GfaceStReg> sq = query.subquery(GfaceStReg.class);
                Root<GfaceStReg> rsq = sq.from(GfaceStReg.class);
                sq.select(rsq).where(cb.equal(rsq.get(GfaceStReg_.rgsStCode), rgsStCode));
                predList.add(cb.in(from.get(GfaceHdrDoc_.sttRgsId)).value(sq));
            }

            query.select(from).distinct(true);
            if (!predList.isEmpty()) {
                query.where(predList.toArray(new Predicate[predList.size()]));
            }
            ret = em.createQuery(query).getResultList();
        }
        return ret;
    }
    
    @Override
    public List<GfaceHdrDoc> getHeaderByStatusDocTypeErrWrStr(HashMap<String, Object> params) {
        List<GfaceHdrDoc> ret = new ArrayList<GfaceHdrDoc>();

        if (params != null && !params.isEmpty()) {
            BigInteger rgsStCode = (BigInteger) params.get("rgsStCode");
            BigInteger flgSt = (BigInteger) params.get("flgSt");
            String docType = (String) params.get("docType");
            String errCode = (String) params.get("errCode");
            String warCode = (String) params.get("warCode");
            String store = (String) params.get("storeCode");

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GfaceHdrDoc> query = cb.createQuery(GfaceHdrDoc.class);
            Root<GfaceHdrDoc> from = query.from(GfaceHdrDoc.class);
            List<Predicate> predList = new LinkedList<Predicate>();
//            from.fetch(GfaceHdrDoc_.btchId);
//            from.fetch(GfaceHdrDoc_.sttRgsId);
//            from.fetch("gfaceDocErrDtlList",JoinType.LEFT);
            from.fetch("gfaceDtlDocList",JoinType.LEFT);
            from.fetch("gfaceAssocDocList",JoinType.LEFT);
            Fetch<GfaceHdrDoc,GfaceDocErrDtl> errFetch=from.fetch("gfaceDocErrDtlList",JoinType.LEFT);
            
            query.orderBy(cb.asc(from.get(GfaceHdrDoc_.hdrId)));
            
            if (flgSt != null) {
                predList.add(cb.equal(from.get(GfaceHdrDoc_.flgSt), flgSt));
            }
            if (rgsStCode != null) {
                //verifica que el estatus sea el proporcionado
                Subquery<GfaceStReg> sq = query.subquery(GfaceStReg.class);
                Root<GfaceStReg> rsq = sq.from(GfaceStReg.class);
                sq.select(rsq).where(cb.equal(rsq.get(GfaceStReg_.rgsStCode), rgsStCode));
                predList.add(cb.in(from.get(GfaceHdrDoc_.sttRgsId)).value(sq));
            }
            if (docType != null) {
            	predList.add(cb.equal(from.get(GfaceHdrDoc_.docType), docType));
            }
            if (errCode != null) {
            	
            	Fetch<GfaceDocErrDtl,GfaceErrRegCtg> errCat=errFetch.fetch("erregId",JoinType.LEFT);
            	Join<GfaceDocErrDtl,GfaceErrRegCtg> j=(Join)errCat;
            	j.get(GfaceErrRegCtg_.isWarning);
            	predList.add(cb.equal(j.get(GfaceErrRegCtg_.isWarning),"N"));                
            }
            if (warCode != null) {
            	//busca el codigo del error
            	Fetch<GfaceDocErrDtl,GfaceErrRegCtg> errCat=errFetch.fetch("erregId",JoinType.LEFT);
            	Join<GfaceDocErrDtl,GfaceErrRegCtg> j=(Join)errCat;
            	j.get(GfaceErrRegCtg_.isWarning);
            	predList.add(cb.equal(j.get(GfaceErrRegCtg_.isWarning),"Y"));
            }
            if (store != null) {
                //verifica que el estatus sea el proporcionado
                Subquery<GfaceStReg> sq = query.subquery(GfaceStReg.class);
                Root<GfaceStReg> rsq = sq.from(GfaceStReg.class);
                sq.select(rsq).where(cb.equal(rsq.get(GfaceStReg_.rgsStCode), rgsStCode));
                predList.add(cb.in(from.get(GfaceHdrDoc_.sttRgsId)).value(sq));
            }

            query.select(from).distinct(true);
            if (!predList.isEmpty()) {
                query.where(predList.toArray(new Predicate[predList.size()]));
            }
            ret = getEntityManager().createQuery(query).getResultList();
        }
        return ret;
    }

    @Override
    public boolean merge(HashMap<String, Object> params, boolean saveStatus) {
        boolean ban = true;
        List<GfaceHdrDoc> registros = null;
        try {
            if (params != null &&! params.isEmpty()) {
                registros = (List<GfaceHdrDoc>) params.get("registros");
                Date fecha=(Date)params.get("fecha");
                BigInteger usuario=(BigInteger)params.get("usuario"); 
                for (GfaceHdrDoc actual : registros) {
                    if (actual.getHdrId() == null) {
                        actual.setAuditUserIns(usuario);
                        actual.setAuditDateIns(fecha);
                    	create(actual);
                    } else {
                        edit(actual);
                        
                    }
                    //actualiza el status de la factura
                    if(saveStatus){
                    	gfaceStRegEjbLocal.edit(actual.getSttRgsId());
                    }
                    em.flush();
                }
            }
            em.flush();
        } catch (Exception e) {
            ban = false;
        }
        return ban;
    }
    
    public Long countErrors(){
    	Long ret=0L;
    	try{
    		Query q=em.createNamedQuery("GfaceHdrDoc.countHeaderError");
    		ret=(Long)q.getSingleResult();
    		
    	}catch(Exception e){    		
    	}
    	return ret;
    	
    }
    public Long countWarnigns(){
    	Long ret=0L;
    	try{
    		Query q=em.createNamedQuery("GfaceHdrDoc.countHeaderWarnings");
    		ret=(Long)q.getSingleResult();
    		
    	}catch(Exception e){    		
    	}
    	return ret;
    }
    public Long countReadyToSend(){
    	Long ret=0L;
    	try{
    		Query q=em.createNamedQuery("GfaceHdrDoc.countHeaderReadyToSend");
    		ret=(Long)q.getSingleResult();
    		
    	}catch(Exception e){    		
    	}
    	return ret;
    }

	@Override
	public GfaceHdrDoc find(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}
}
