/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniEvento;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface SieniEventoFacadeRemote {

    void create(SieniEvento sieniEvento);

    void edit(SieniEvento sieniEvento);

    void remove(SieniEvento sieniEvento);

    SieniEvento find(Object id);

    List<SieniEvento> findAll();

    List<SieniEvento> findRange(int[] range);

    int count();
    
}
