/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniCursoAlumno;

/**
 *
 * @author bugtraq
 */
@Remote
public interface SieniCursoAlumnoFacadeRemote {

    void create(SieniCursoAlumno sieniCursoAlumno);

    void edit(SieniCursoAlumno sieniCursoAlumno);

    void remove(SieniCursoAlumno sieniCursoAlumno);

    SieniCursoAlumno find(Object id);

    List<SieniCursoAlumno> findAll();

    List<SieniCursoAlumno> findRange(int[] range);

    int count();
    
}
