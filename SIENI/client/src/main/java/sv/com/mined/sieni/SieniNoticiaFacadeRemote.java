/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniNoticia;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniNoticiaFacadeRemote {

    void create(SieniNoticia sieniNoticia);
    
    SieniNoticia createAndReturn(SieniNoticia sieniNoticia);

    void edit(SieniNoticia sieniNoticia);

    void remove(SieniNoticia sieniNoticia);

    SieniNoticia find(Object id);

    List<SieniNoticia> findAll();

    List<SieniNoticia> findRange(int[] range);

    int count();
    
    public List<SieniNoticia> findNoticiasActivas();
    
}
