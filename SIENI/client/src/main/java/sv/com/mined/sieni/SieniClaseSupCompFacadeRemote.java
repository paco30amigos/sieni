/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniClaseSupComp;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniClaseSupCompFacadeRemote {

    void create(SieniClaseSupComp sieniClaseSupComp);

    void edit(SieniClaseSupComp sieniClaseSupComp);

    void remove(SieniClaseSupComp sieniClaseSupComp);

    SieniClaseSupComp find(Object id);

    List<SieniClaseSupComp> findAll();

    List<SieniClaseSupComp> findRange(int[] range);

    int count();

    public List<SieniClaseSupComp> findByClase(Long idClase);

    public void merge(List<SieniClaseSupComp> lista, List<SieniClaseSupComp> eliminados);
}
