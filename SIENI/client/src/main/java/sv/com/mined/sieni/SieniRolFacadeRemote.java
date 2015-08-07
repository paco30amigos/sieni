/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniRol;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniRolFacadeRemote {

    void create(SieniRol sieniRol);

    void edit(SieniRol sieniRol);

    void remove(SieniRol sieniRol);

    SieniRol find(Object id);

    List<SieniRol> findAll();

    List<SieniRol> findRange(int[] range);

    int count();
    
}
