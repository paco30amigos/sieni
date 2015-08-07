/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniMatricula;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniMatriculaFacadeRemote {

    void create(SieniMatricula sieniMatricula);

    void edit(SieniMatricula sieniMatricula);

    void remove(SieniMatricula sieniMatricula);

    SieniMatricula find(Object id);

    List<SieniMatricula> findAll();

    List<SieniMatricula> findRange(int[] range);

    int count();
    
}