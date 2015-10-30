/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniEvaluacionItem;

/**
 *
 * @author ever
 */
@Remote
public interface SieniEvaluacionItemFacadeRemote {

    void create(SieniEvaluacionItem sieniEvaluacionItem);

    void edit(SieniEvaluacionItem sieniEvaluacionItem);

    void remove(SieniEvaluacionItem sieniEvaluacionItem);

    SieniEvaluacionItem find(Object id);

    List<SieniEvaluacionItem> findAll();

    List<SieniEvaluacionItem> findRange(int[] range);
    
     List<SieniEvaluacionItem> findByIdEvaluacion(Long idEvaluacion);

    int count();
    
}
