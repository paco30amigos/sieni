/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniComponente;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniComponenteFacadeRemote {

    void create(SieniComponente sieniComponente);

    void edit(SieniComponente sieniComponente);

    void remove(SieniComponente sieniComponente);

    SieniComponente find(Object id);

    List<SieniComponente> findAll();

    List<SieniComponente> findRange(int[] range);

    int count();
    
    public void merge(List<SieniComponente> comps,List<SieniComponente> eliminados);
    
    public List<SieniComponente> findByIdSuperComp(Long idSuperCompon);
}
