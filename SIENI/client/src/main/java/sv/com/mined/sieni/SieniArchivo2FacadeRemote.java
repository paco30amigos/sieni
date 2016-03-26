/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniArchivo2;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniArchivo2FacadeRemote {

    void create(SieniArchivo2 sieniArchivo);

    SieniArchivo2 createAndReturn(SieniArchivo2 sieniArchivo);

    void edit(SieniArchivo2 sieniArchivo);

    void remove(SieniArchivo2 sieniArchivo);

    SieniArchivo2 find(Object id);

    List<SieniArchivo2> findAll();

    List<SieniArchivo2> findRange(int[] range);

    int count();

}
