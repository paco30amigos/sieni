/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.com.mined.sieni.model.SieniDocente;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniDocenteFacade extends AbstractFacade<SieniDocente> implements sv.com.mined.sieni.SieniDocenteFacadeRemote {
    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniDocenteFacade() {
        super(SieniDocente.class);
    }
    
}
