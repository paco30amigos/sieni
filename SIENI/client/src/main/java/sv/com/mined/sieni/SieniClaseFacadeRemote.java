/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniClase;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniClaseFacadeRemote {

    void create(SieniClase sieniClase);

    void edit(SieniClase sieniClase);

    void remove(SieniClase sieniClase);

    SieniClase find(Object id);

    List<SieniClase> findAll();

    List<SieniClase> findRange(int[] range);

    int count();
    
}