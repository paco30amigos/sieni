/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniMateria;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniMateriaFacadeRemote {

    void create(SieniMateria sieniMateria);
    
    SieniMateria createAndReturn(SieniMateria sieniMateria);

    void edit(SieniMateria sieniMateria);

    void remove(SieniMateria sieniMateria);

    SieniMateria find(Object id);

    List<SieniMateria> findAll();

    List<SieniMateria> findMateriasActivas();
    
    public List<SieniMateria> findMateriasActivasByGrado(Long idGrado);

    List<SieniMateria> findRange(int[] range);

    int count();

    public List<SieniMateria> findAllNoInactivas();

    public List<SieniMateria> findByAlumno(Long idAlumno);

    public List<SieniMateria> findByMaNombre(String maNombre);

    public SieniMateria findByIdMateria(Long idMateria);

}
