/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniAnioEscolar;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniAnioEscolarFacadeRemote {

    void create(SieniAnioEscolar sieniAnioEscolar);

    void edit(SieniAnioEscolar sieniAnioEscolar);

    void remove(SieniAnioEscolar sieniAnioEscolar);

    SieniAnioEscolar find(Object id);

    List<SieniAnioEscolar> findAll();

    List<SieniAnioEscolar> findRange(int[] range);

    int count();
    
    public List<SieniAnioEscolar> findAllNoInactivos();
}
