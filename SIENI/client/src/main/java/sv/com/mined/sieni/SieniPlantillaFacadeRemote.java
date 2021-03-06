/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniPlantilla;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniPlantillaFacadeRemote {

    void create(SieniPlantilla sieniPlantilla);
    
    SieniPlantilla createAndReturn(SieniPlantilla sieniPlantilla);

    void edit(SieniPlantilla sieniPlantilla);

    void remove(SieniPlantilla sieniPlantilla);

    SieniPlantilla find(Object id);
    
    SieniPlantilla refresh(SieniPlantilla sieniPlantilla);
    
    SieniPlantilla findByIdPlantilla(Long id);

    List<SieniPlantilla> findAll();

    List<SieniPlantilla> findRange(int[] range);

    int count();
    
    public List<SieniPlantilla> findByMateria(Long idMateria);
    
    public List<SieniPlantilla> findAllNoInactivas();
}
