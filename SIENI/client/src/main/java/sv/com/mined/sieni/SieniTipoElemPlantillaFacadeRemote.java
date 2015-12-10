/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniTipoElemPlantilla;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface SieniTipoElemPlantillaFacadeRemote {

    void create(SieniTipoElemPlantilla sieniTipoElemPlantilla);

    SieniTipoElemPlantilla createAndReturn(SieniTipoElemPlantilla sieniSuperCompon);

    void edit(SieniTipoElemPlantilla sieniTipoElemPlantilla);

    void remove(SieniTipoElemPlantilla sieniTipoElemPlantilla);

    SieniTipoElemPlantilla find(Object id);
    
    SieniTipoElemPlantilla findByNombre(String nombre);

    List<SieniTipoElemPlantilla> findAll();

    List<SieniTipoElemPlantilla> findRange(int[] range);

    int count();

    public List<SieniTipoElemPlantilla> findAllNoInactivos();
    
    public List<SieniTipoElemPlantilla> findAllActivos();

}
