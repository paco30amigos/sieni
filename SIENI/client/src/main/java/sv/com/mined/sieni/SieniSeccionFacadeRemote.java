/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniSeccion;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniSeccionFacadeRemote {

    void create(SieniSeccion sieniSeccion);

    void edit(SieniSeccion sieniSeccion);

    void remove(SieniSeccion sieniSeccion);

    SieniSeccion find(Object id);

    List<SieniSeccion> findAll();

    List<SieniSeccion> findRange(int[] range);

    int count();

    public SieniSeccion findByIdSeccion(Long idSeccion);
    
}
