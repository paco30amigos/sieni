/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniDocentRDud;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniDocentRDudFacadeRemote {

    void create(SieniDocentRDud sieniDocentRDud);

    void edit(SieniDocentRDud sieniDocentRDud);

    void remove(SieniDocentRDud sieniDocentRDud);

    SieniDocentRDud find(Object id);

    List<SieniDocentRDud> findAll();

    List<SieniDocentRDud> findRange(int[] range);

    int count();
    
}
