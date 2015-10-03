/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    @Override
    public List<SieniDocente> findDocentesSinUsuario() {
        Query q = em.createNamedQuery("SieniDocente.findDocentesSinUsuario");
        return q.getResultList();
    }

    @Override
    public SieniDocente findDocenteUsuario(String usuario, String pass) {
        Query q = em.createNamedQuery("SieniDocente.findDocenteUsuario");
        q.setParameter("usuario", usuario);
        q.setParameter("pass", pass);
        List<SieniDocente> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public List<SieniDocente> findDocentesActivos(){
    
    Query q=em.createNamedQuery("SieniDocente.findDocenteActivo");
    List<SieniDocente> res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            return res;
        } else {
            return null;
        }
    }
    
    @Override
    public List<SieniDocente> findDocentesDesdeHasta(Date desde,Date hasta){
    
        Query q=em.createNamedQuery("SieniDocente.findByDesdeHasta");
        q.setParameter("desde", desde);
         q.setParameter("hasta", hasta);
    List<SieniDocente> res = q.getResultList();
    
    return res;
        
    }

    @Override
    public List<SieniDocente> findUsuariosRpt(String estadoUser) {
        Query q;
        switch(estadoUser){
            case "T":
                q = em.createNamedQuery("SieniDocente.findRptUsuariosDocentes");
                break;
            default:
                q = em.createNamedQuery("SieniDocente.findRptUsuariosDocentesByEstado");
                q.setParameter("dcEstado", estadoUser);
                break;
        }
        return q.getResultList();
    }
}
