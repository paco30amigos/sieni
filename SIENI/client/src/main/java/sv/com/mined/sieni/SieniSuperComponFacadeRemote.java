/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniSuperCompon;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniSuperComponFacadeRemote {

    void create(SieniSuperCompon sieniSuperCompon);
    
    SieniSuperCompon createAndReturn(SieniSuperCompon sieniSuperCompon);

    void edit(SieniSuperCompon sieniSuperCompon);

    void remove(SieniSuperCompon sieniSuperCompon);

    SieniSuperCompon find(Object id);

    List<SieniSuperCompon> findAll();

    List<SieniSuperCompon> findRange(int[] range);
    
    List<SieniSuperCompon> findAllNoInactivos();    

    int count();
    
    public List<SieniSuperCompon> findByClase(Long idClase);
    
    public List<SieniSuperCompon> findEstado(Character estado);
    
}
