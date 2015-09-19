/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniClaseDocente;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface SieniClaseDocenteFacadeRemote {

    void create(SieniClaseDocente sieniClaseDocente);

    void edit(SieniClaseDocente sieniClaseDocente);

    void remove(SieniClaseDocente sieniClaseDocente);

    SieniClaseDocente find(Object id);

    List<SieniClaseDocente> findAll();

    List<SieniClaseDocente> findRange(int[] range);

    int count();
    
}
