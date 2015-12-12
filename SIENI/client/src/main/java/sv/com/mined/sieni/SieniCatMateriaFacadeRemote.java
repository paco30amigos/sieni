/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniCatMateria;

/**
 *
 * @author Alejandro
 */
@Remote
public interface SieniCatMateriaFacadeRemote {

    void create(SieniCatMateria sieniCatMateria);

    public SieniCatMateria createAndReturn(SieniCatMateria sieniCatMateria);

    void edit(SieniCatMateria sieniCatMateria);

    void remove(SieniCatMateria sieniCatMateria);

    SieniCatMateria find(Object id);

    List<SieniCatMateria> findAll();

    List<SieniCatMateria> findRange(int[] range);

    int count();

    public List<SieniCatMateria> findAllNoInactivos();

    public SieniCatMateria findByNombre(String nombre);

    public List<SieniCatMateria> findAllActivos();

}
