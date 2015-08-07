/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniAlumnRDud;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniAlumnRDudFacadeRemote {

    void create(SieniAlumnRDud sieniAlumnRDud);

    void edit(SieniAlumnRDud sieniAlumnRDud);

    void remove(SieniAlumnRDud sieniAlumnRDud);

    SieniAlumnRDud find(Object id);

    List<SieniAlumnRDud> findAll();

    List<SieniAlumnRDud> findRange(int[] range);

    int count();
    
}
