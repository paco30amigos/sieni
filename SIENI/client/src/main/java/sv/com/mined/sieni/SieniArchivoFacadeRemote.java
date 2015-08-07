/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniArchivo;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniArchivoFacadeRemote {

    void create(SieniArchivo sieniArchivo);

    void edit(SieniArchivo sieniArchivo);

    void remove(SieniArchivo sieniArchivo);

    SieniArchivo find(Object id);

    List<SieniArchivo> findAll();

    List<SieniArchivo> findRange(int[] range);

    int count();
    
}
