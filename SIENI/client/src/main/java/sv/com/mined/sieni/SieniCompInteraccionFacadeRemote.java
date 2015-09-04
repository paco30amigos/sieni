/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniCompInteraccion;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface SieniCompInteraccionFacadeRemote {

    void create(SieniCompInteraccion sieniCompInteraccion);

    void edit(SieniCompInteraccion sieniCompInteraccion);

    void remove(SieniCompInteraccion sieniCompInteraccion);

    SieniCompInteraccion find(Object id);

    List<SieniCompInteraccion> findAll();

    List<SieniCompInteraccion> findRange(int[] range);

    int count();
    
}
