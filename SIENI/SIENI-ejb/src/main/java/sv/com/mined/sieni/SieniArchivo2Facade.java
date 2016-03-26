/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.com.mined.sieni.model.SieniArchivo2;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniArchivo2Facade extends AbstractFacade<SieniArchivo2> implements sv.com.mined.sieni.SieniArchivo2FacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniArchivo2Facade() {
        super(SieniArchivo2.class);
    }
}
