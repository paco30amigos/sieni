/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniDocentRol;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniDocentRolFacadeRemote {

    void create(SieniDocentRol sieniDocentRol);

    void edit(SieniDocentRol sieniDocentRol);

    void remove(SieniDocentRol sieniDocentRol);

    SieniDocentRol find(Object id);

    List<SieniDocentRol> findAll();

    List<SieniDocentRol> findRange(int[] range);

    int count();
    
    public List<SieniDocentRol> findAllNoInactivos();
    
}
