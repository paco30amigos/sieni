/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniElemPlantilla;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniElemPlantillaFacadeRemote {

    void create(SieniElemPlantilla sieniElemPlantilla);

    void edit(SieniElemPlantilla sieniElemPlantilla);

    void remove(SieniElemPlantilla sieniElemPlantilla);

    SieniElemPlantilla find(Object id);

    List<SieniElemPlantilla> findAll();

    List<SieniElemPlantilla> findRange(int[] range);

    int count();
    
}
