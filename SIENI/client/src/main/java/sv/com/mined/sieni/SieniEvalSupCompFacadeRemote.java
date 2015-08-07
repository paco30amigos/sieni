/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniEvalSupComp;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniEvalSupCompFacadeRemote {

    void create(SieniEvalSupComp sieniEvalSupComp);

    void edit(SieniEvalSupComp sieniEvalSupComp);

    void remove(SieniEvalSupComp sieniEvalSupComp);

    SieniEvalSupComp find(Object id);

    List<SieniEvalSupComp> findAll();

    List<SieniEvalSupComp> findRange(int[] range);

    int count();
    
}
