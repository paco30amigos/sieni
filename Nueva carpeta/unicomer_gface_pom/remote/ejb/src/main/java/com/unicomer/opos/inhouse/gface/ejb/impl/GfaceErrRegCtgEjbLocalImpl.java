package com.unicomer.opos.inhouse.gface.ejb.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.unicomer.opos.inhouse.gface.ejb.GfaceErrRegCtgEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceErrRegCtg;
import com.unicomer.opos.inhouse.gface.entity.GfaceErrRegCtg_;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc_;
import com.unicomer.opos.inhouse.gface.entity.GfaceStReg;
import com.unicomer.opos.inhouse.gface.entity.GfaceStReg_;
import com.unicomer.opos.inhouse.services.ejbs.AbstractFacade;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
*
* @author nelsong_lopez
*
*/
@Transactional(value = TxType.REQUIRED)
@Stateless(name = "GfaceErrRegCtgEjbLocalImpl", mappedName = "ejb/GfaceErrRegCtgEjbLocalImpl")
@Remote(GfaceErrRegCtgEjbLocal.class)
public class GfaceErrRegCtgEjbLocalImpl extends AbstractFacade<GfaceErrRegCtg> implements GfaceErrRegCtgEjbLocal {

    @PersistenceContext(unitName = "EAgfacePersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GfaceErrRegCtgEjbLocalImpl() {
        super(GfaceErrRegCtg.class);
    }

    @Override
    public boolean merge(HashMap<String, Object> params) {
        boolean ban = true;
        try {
            if (params != null && !params.isEmpty()) {
                List<GfaceErrRegCtg> registros = (List<GfaceErrRegCtg>) params.get("registros");
                Date fecha = (Date) params.get("fecha");
                BigInteger usuario = (BigInteger) params.get("usuario");
                Boolean manualId = (Boolean) params.get("manualId");
                for (GfaceErrRegCtg actual : registros) {
                    if (actual.getErregId() == null) {
                        actual.setAuditUserIns(usuario);
                        actual.setAuditTimeIns(fecha);
                        actual.setEffectiveDate(fecha);
                       create(actual);
                    } else if(manualId!=null&&manualId){
                        actual.setAuditUserIns(usuario);
                        actual.setAuditTimeIns(fecha);
                        actual.setEffectiveDate(fecha);
                        create(actual);
                    }else{
                    	actual.setAuditUserUpd(usuario);
                        actual.setAuditTimeUpd(fecha);
                        edit(actual);
                    }
                    
                }
            }
            em.flush();
        } catch (Exception e) {
            ban = false;
        }
        return ban;
    }
    
    public HashMap<String,List<GfaceErrRegCtg>> getErroresAdvertencias(){
    	HashMap<String,List<GfaceErrRegCtg>> ret=new HashMap<String, List<GfaceErrRegCtg>>();
    	

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GfaceErrRegCtg> query = cb.createQuery(GfaceErrRegCtg.class);
            Root<GfaceErrRegCtg> from = query.from(GfaceErrRegCtg.class);
            List<Predicate> predList = new LinkedList<Predicate>();
            
            query.orderBy(cb.asc(from.get(GfaceErrRegCtg_.erregId)));
            //regla que no haya expirado            
            predList.add(cb.or(cb.isNull(from.get(GfaceErrRegCtg_.expiryDate)),cb.greaterThanOrEqualTo(from.get(GfaceErrRegCtg_.expiryDate), new Date())));
            if (!predList.isEmpty()) {
                query.where(predList.toArray(new Predicate[predList.size()]));
            }
            List<GfaceErrRegCtg> lista = em.createQuery(query).getResultList();
            if(lista!=null&&!lista.isEmpty()){
            	for(GfaceErrRegCtg actual:lista){
            		if(ret.get("Y")==null){
            			ret.put("Y",new ArrayList<GfaceErrRegCtg>());
            		}
            		if(ret.get("N")==null){
            			ret.put("N",new ArrayList<GfaceErrRegCtg>());
            		}
            		if("Y".equals(actual.getIsWarning())){
            			ret.get("Y").add(actual);
            		}else{
            			ret.get("N").add(actual);
            		}
            	}
            }
            
    	return ret;
    }
}
