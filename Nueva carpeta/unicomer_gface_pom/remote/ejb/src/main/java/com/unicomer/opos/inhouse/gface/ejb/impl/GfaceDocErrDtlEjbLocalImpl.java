package com.unicomer.opos.inhouse.gface.ejb.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.unicomer.opos.inhouse.gface.ejb.GfaceDocErrDtlEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceDocErrDtl;
import com.unicomer.opos.inhouse.services.ejbs.AbstractFacade;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
*
* @author nelsong_lopez
*
*/
@Transactional(value = TxType.REQUIRED)
@Stateless(name = "GfaceDocErrDtlEjbLocalImpl", mappedName = "ejb/GfaceDocErrDtlEjbLocalImpl")
@Remote(GfaceDocErrDtlEjbLocal.class)
public class GfaceDocErrDtlEjbLocalImpl extends AbstractFacade<GfaceDocErrDtl> implements GfaceDocErrDtlEjbLocal {

    @PersistenceContext(unitName = "EAgfacePersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GfaceDocErrDtlEjbLocalImpl() {
        super(GfaceDocErrDtl.class);
    }

    @Override
    public boolean merge(HashMap<String, Object> params) {
        boolean ban = true;
        try {
            if (params != null && !params.isEmpty()) {
                List<GfaceDocErrDtl> registros = (List<GfaceDocErrDtl>) params.get("registros");
                Date fecha = (Date) params.get("fecha");
                BigInteger usuario = (BigInteger) params.get("usuario");
                Boolean manualId = (Boolean) params.get("manualId");
                for (GfaceDocErrDtl actual : registros) {                    
                    if (actual.getDcrdtId() == null) {
                        actual.setAuditUserIns(usuario);
                        actual.setAuditDateIns(fecha);
                        create(actual);
                    } else if(manualId!=null&&manualId){
                        actual.setAuditUserIns(usuario);
                        actual.setAuditDateIns(fecha);
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

	@Override
	public GfaceDocErrDtl find(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}
}
