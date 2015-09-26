/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniTemaDuda;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniTemaDudaFacadeRemote {

    void create(SieniTemaDuda sieniTemaDuda);

    void edit(SieniTemaDuda sieniTemaDuda);

    void remove(SieniTemaDuda sieniTemaDuda);

    SieniTemaDuda find(Object id);

    List<SieniTemaDuda> findAll();
    
    List<SieniTemaDuda> findConsultasActivas();

    List<SieniTemaDuda> findRange(int[] range);

    int count();
    
}
