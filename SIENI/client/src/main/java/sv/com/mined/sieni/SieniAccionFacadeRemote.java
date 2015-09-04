/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniAccion;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface SieniAccionFacadeRemote {

    void create(SieniAccion sieniAccion);

    void edit(SieniAccion sieniAccion);

    void remove(SieniAccion sieniAccion);

    SieniAccion find(Object id);

    List<SieniAccion> findAll();

    List<SieniAccion> findRange(int[] range);

    int count();
    
}
