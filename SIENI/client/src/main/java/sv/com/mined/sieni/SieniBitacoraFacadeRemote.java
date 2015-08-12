/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniBitacora;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniBitacoraFacadeRemote {

    void create(SieniBitacora sieniBitacora);

    void edit(SieniBitacora sieniBitacora);

    void remove(SieniBitacora sieniBitacora);

    SieniBitacora find(Object id);

    List<SieniBitacora> findAll();

    List<SieniBitacora> findRange(int[] range);

    int count();
    
}
