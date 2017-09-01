package com.unicomer.opos.inhouse.gface.ejb.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.unicomer.opos.inhouse.gface.ejb.GfaceAssocDocEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceStRegEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceAssocDoc;
import com.unicomer.opos.inhouse.gface.entity.GfaceDtlDoc;
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
@Stateless(name = "GfaceAssocDocEjbLocalImpl", mappedName = "ejb/GfaceAssocDocEjbLocalImpl")
@Remote(GfaceAssocDocEjbLocal.class)
public class GfaceAssocDocEjbLocalImpl extends AbstractFacade<GfaceAssocDoc> implements GfaceAssocDocEjbLocal {

    @PersistenceContext(unitName = "EAgfacePersistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GfaceAssocDocEjbLocalImpl() {
        super(GfaceAssocDoc.class);
    }

    @Override
    public boolean merge(HashMap<String, Object> params) {
        boolean ban = true;
        try {
            if (params != null && !params.isEmpty()) {
                List<GfaceAssocDoc> registros = (List<GfaceAssocDoc>) params.get("registros");
                Date fecha = (Date) params.get("fecha");
                BigInteger usuario = (BigInteger) params.get("usuario");
                for (GfaceAssocDoc actual : registros) {
                    if (actual.getAsdocId() == null) {
                        actual.setAuditUserIns(usuario);
                        actual.setAuditDateIns(fecha);
                        actual=em.merge(actual);
                    } else {                        
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
}
