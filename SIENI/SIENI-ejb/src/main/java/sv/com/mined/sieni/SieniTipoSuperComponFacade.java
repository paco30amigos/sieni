/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.com.mined.sieni.model.SieniTipoSuperCompon;

/**
 *
 * @author francisco_medina
 */
@Stateless
public class SieniTipoSuperComponFacade extends AbstractFacade<SieniTipoSuperCompon> implements sv.com.mined.sieni.SieniTipoSuperComponFacadeRemote {
    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniTipoSuperComponFacade() {
        super(SieniTipoSuperCompon.class);
    }
    
}
