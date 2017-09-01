package com.unicomer.opos.inhouse.gface.ejb.impl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.unicomer.opos.inhouse.gface.ejb.GfaceBtchFilesEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceBtchFiles;
import com.unicomer.opos.inhouse.services.ejbs.AbstractFacade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
*
* @author nelsong_lopez
*
*/
@Transactional(value = TxType.REQUIRED)
@Stateless(name = "GfaceBtchFilesEjbLocalImpl", mappedName = "ejb/GfaceBtchFilesEjbLocalImpl")
@Remote(GfaceBtchFilesEjbLocal.class)
public class GfaceBtchFilesEjbLocalImpl extends AbstractFacade<GfaceBtchFiles> implements GfaceBtchFilesEjbLocal {

    @PersistenceContext(unitName = "EAgfacePersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GfaceBtchFilesEjbLocalImpl() {
        super(GfaceBtchFiles.class);
    }

    @Override
    public BigInteger getSequenceNext(String sequenceName) {
        Query query
                = em.createNativeQuery("select " + sequenceName + ".nextval as num from dual");
        List res = query.getResultList();
        BigInteger ret = null;
        if (res != null && !res.isEmpty()) {
            ret = new BigInteger(res.get(0).toString());
        }
        return ret;
    }

    @Override
    public boolean merge(HashMap<String, Object> params) {
        boolean ban = true;
        try {
            if (params != null && !params.isEmpty()) {
                List<GfaceBtchFiles> registros = (List<GfaceBtchFiles>) params.get("registros");
                Date fecha = (Date) params.get("fecha");
                BigInteger usuario = (BigInteger) params.get("usuario");
                String batchId=(String)params.get("batchId");
                for (GfaceBtchFiles actual : registros) {
                    if (actual.getBtchId() == null) {
                        actual.setAuditUserIns(usuario);
                        actual.setAuditDateIns(fecha);
                        create(actual);
                    } else if(batchId!=null) {
                    	actual.setAuditUserIns(usuario);
                        actual.setAuditDateIns(fecha);
                        actual.setBtchId(batchId);
                        create(actual);
                    }else{
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
    
    public List<String> getFilesProcessed(){
    	List<String> ret=new ArrayList<String>();
    	Query q=em.createNamedQuery("GfaceBtchFiles.filesProcessed");
    	ret=(List<String>)q.getResultList();
    	return ret;
    }
}
