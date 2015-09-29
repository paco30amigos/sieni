/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniInteEntrComp;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface SieniInteEntrCompFacadeRemote {

    void create(SieniInteEntrComp sieniInteEntrComp);

    void edit(SieniInteEntrComp sieniInteEntrComp);

    void remove(SieniInteEntrComp sieniInteEntrComp);

    SieniInteEntrComp find(Object id);

    List<SieniInteEntrComp> findAll();

    List<SieniInteEntrComp> findRange(int[] range);

    int count();

    public List<SieniInteEntrComp> findByClase(Long idClase);
}
