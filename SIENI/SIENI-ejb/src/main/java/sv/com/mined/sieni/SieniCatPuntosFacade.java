/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.com.mined.sieni.model.SieniCatPuntos;

/**
 *
 * @author bugtraq
 */
@Stateless
public class SieniCatPuntosFacade extends AbstractFacade<SieniCatPuntos> implements sv.com.mined.sieni.SieniCatPuntosFacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniCatPuntosFacade() {
        super(SieniCatPuntos.class);
    }

    @Override
    public SieniCatPuntos findByClase(Long idClase) {
        SieniCatPuntos ret = null;
        Query q = em.createNamedQuery("SieniCatPuntos.findByClase");
        q.setParameter("idClase", idClase);
        List<SieniCatPuntos> datos=q.getResultList();
        if(datos!=null&&!datos.isEmpty()){
            ret=datos.get(0);
        }
        return ret;
    }
    
    
    
    
    @Override
    public List<SieniCatPuntos> findRptAvance() {
        Query q = em.createNamedQuery("SieniCatPuntos.rptAvanceClases");
        //q.setParameter("estado", estado);
        List<SieniCatPuntos> res = q.getResultList();
        return res;
    }

}
