/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.com.mined.sieni.model.SieniEvalRespAlumno;

/**
 *
 * @author ever
 */
@Stateless
public class SieniEvalRespAlumnoFacade extends AbstractFacade<SieniEvalRespAlumno> implements sv.com.mined.sieni.SieniEvalRespAlumnoFacadeRemote {
   @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniEvalRespAlumnoFacade() {
        super(SieniEvalRespAlumno.class);
    }
    
}