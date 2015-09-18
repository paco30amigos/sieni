/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniCurso;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniCursoFacadeRemote {

    void create(SieniCurso sieniCurso);

    void edit(SieniCurso sieniCurso);

    void remove(SieniCurso sieniCurso);

    SieniCurso find(Object id);

    List<SieniCurso> findAll();

    List<SieniCurso> findRange(int[] range);
    
    List<SieniCurso> findByEstado(Character estado);

    int count();
    
}
