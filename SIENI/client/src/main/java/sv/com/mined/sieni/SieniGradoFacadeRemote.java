/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniGrado;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniGradoFacadeRemote {

    void create(SieniGrado sieniGrado);

    SieniGrado createAndReturn(SieniGrado sieniGrado);

    void edit(SieniGrado sieniGrado);

    void remove(SieniGrado sieniGrado);

    SieniGrado find(Object id);

    List<SieniGrado> findAll();

    List<SieniGrado> findRange(int[] range);

    int count();

    public SieniGrado getGradoActualAlumno(Long idAlumno, Date anioDesde, Date anioHasta);

    public SieniGrado findByIdGrado(Long idGrado);
    
    public List<SieniGrado> findBy(SieniGrado grado);

    public List<SieniGrado> findAllNoInactivos();
}
