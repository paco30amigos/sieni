/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniAlumnRol;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniAlumnRolFacadeRemote {

    void create(SieniAlumnRol sieniAlumnRol);
    
    SieniAlumnRol createAndReturn(SieniAlumnRol sieniAlumnRol);

    void edit(SieniAlumnRol sieniAlumnRol);

    void remove(SieniAlumnRol sieniAlumnRol);

    SieniAlumnRol find(Object id);

    List<SieniAlumnRol> findAll();

    List<SieniAlumnRol> findRange(int[] range);

    int count();

    public List<SieniAlumnRol> findAllNoInactivos();
}
