/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.com.mined.sieni.model.SieniBitacora;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniBitacoraFacade extends AbstractFacade<SieniBitacora> implements sv.com.mined.sieni.SieniBitacoraFacadeRemote {
    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniBitacoraFacade() {
        super(SieniBitacora.class);
    }
    
}