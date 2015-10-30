/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniEvalRespAlumno;

/**
 *
 * @author ever
 */
@Remote
public interface SieniEvalRespAlumnoFacadeRemote {

    void create(SieniEvalRespAlumno sieniEvalRespAlumno);

    void edit(SieniEvalRespAlumno sieniEvalRespAlumno);

    void remove(SieniEvalRespAlumno sieniEvalRespAlumno);

    SieniEvalRespAlumno find(Object id);

    List<SieniEvalRespAlumno> findAll();

    List<SieniEvalRespAlumno> findRange(int[] range);

    int count();
    
}
