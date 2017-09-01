package com.unicomer.opos.inhouse.gface.ejb.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.unicomer.opos.inhouse.gface.ejb.GfaceDtlDocEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceDtlDoc;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.services.ejbs.AbstractFacade;

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
@Stateless(name = "GfaceDtlDocEjbLocalImpl", mappedName = "ejb/GfaceDtlDocEjbLocalImpl")
@Remote(GfaceDtlDocEjbLocal.class)
public class GfaceDtlDocEjbLocalImpl extends AbstractFacade<GfaceDtlDoc> implements GfaceDtlDocEjbLocal {

    @PersistenceContext(unitName = "EAgfacePersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GfaceDtlDocEjbLocalImpl() {
        super(GfaceDtlDoc.class);
    }

    @Override
    public boolean merge(HashMap<String, Object> params) {
        boolean ban = true;
        try {
            if (params != null && !params.isEmpty()) {
                List<GfaceDtlDoc> registros = (List<GfaceDtlDoc>) params.get("registros");
                Date fecha = (Date) params.get("fecha");
                BigInteger usuario = (BigInteger) params.get("usuario");
                for (GfaceDtlDoc actual : registros) {
                    if (actual.getDtldcId() == null) {
                        actual.setAuditUserIns(usuario);
                        actual.setAuditDateIns(fecha);
                        create(actual);                        
                    } else {
                        edit(actual);
                    }
                    em.flush();
                }
            }
        } catch (Exception e) {
            ban = false;
        }
        return ban;
    }
    
}
