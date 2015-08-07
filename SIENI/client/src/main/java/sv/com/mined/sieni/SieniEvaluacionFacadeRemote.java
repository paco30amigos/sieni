/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniEvaluacion;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniEvaluacionFacadeRemote {

    void create(SieniEvaluacion sieniEvaluacion);

    void edit(SieniEvaluacion sieniEvaluacion);

    void remove(SieniEvaluacion sieniEvaluacion);

    SieniEvaluacion find(Object id);

    List<SieniEvaluacion> findAll();

    List<SieniEvaluacion> findRange(int[] range);

    int count();
    
}