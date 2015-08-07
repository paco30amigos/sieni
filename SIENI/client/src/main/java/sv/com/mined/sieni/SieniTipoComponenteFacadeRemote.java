/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniTipoComponente;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniTipoComponenteFacadeRemote {

    void create(SieniTipoComponente sieniTipoComponente);

    void edit(SieniTipoComponente sieniTipoComponente);

    void remove(SieniTipoComponente sieniTipoComponente);

    SieniTipoComponente find(Object id);

    List<SieniTipoComponente> findAll();

    List<SieniTipoComponente> findRange(int[] range);

    int count();
    
}
