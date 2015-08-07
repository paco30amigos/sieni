/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniEvalSoluc;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniEvalSolucFacadeRemote {

    void create(SieniEvalSoluc sieniEvalSoluc);

    void edit(SieniEvalSoluc sieniEvalSoluc);

    void remove(SieniEvalSoluc sieniEvalSoluc);

    SieniEvalSoluc find(Object id);

    List<SieniEvalSoluc> findAll();

    List<SieniEvalSoluc> findRange(int[] range);

    int count();
    
}
