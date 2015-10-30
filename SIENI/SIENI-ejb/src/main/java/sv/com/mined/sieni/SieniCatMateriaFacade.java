/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.com.mined.sieni.model.SieniCatMateria;

/**
 *
 * @author Alejandro
 */
@Stateless
public class SieniCatMateriaFacade extends AbstractFacade<SieniCatMateria> implements sv.com.mined.sieni.SieniCatMateriaFacadeRemote {
    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniCatMateriaFacade() {
        super(SieniCatMateria.class);
    }
    
}
