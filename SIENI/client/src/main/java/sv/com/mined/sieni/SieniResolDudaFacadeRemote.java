/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniResolDuda;
import sv.com.mined.sieni.model.SieniTemaDuda;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniResolDudaFacadeRemote {

    void create(SieniResolDuda sieniResolDuda);

    void edit(SieniResolDuda sieniResolDuda);

    void remove(SieniResolDuda sieniResolDuda);

    SieniResolDuda find(Object id);

    List<SieniResolDuda> findAll();

    List<SieniResolDuda> findRange(int[] range);

    int count();
    
    
    List<SieniResolDuda> findByConsulta(SieniTemaDuda consulta);
}
