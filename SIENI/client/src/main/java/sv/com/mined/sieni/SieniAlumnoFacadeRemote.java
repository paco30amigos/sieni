/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniAlumno;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniAlumnoFacadeRemote {

    void create(SieniAlumno sieniAlumno);

    void edit(SieniAlumno sieniAlumno);

    void remove(SieniAlumno sieniAlumno);

    SieniAlumno find(Object id);

    List<SieniAlumno> findAll();

    List<SieniAlumno> findRange(int[] range);

    int count();
    
}