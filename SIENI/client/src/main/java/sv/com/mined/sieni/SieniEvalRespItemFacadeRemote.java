/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniEvalRespItem;

/**
 *
 * @author ever
 */
@Remote
public interface SieniEvalRespItemFacadeRemote {

    void create(SieniEvalRespItem sieniEvalRespItem);

    void edit(SieniEvalRespItem sieniEvalRespItem);

    void remove(SieniEvalRespItem sieniEvalRespItem);

    SieniEvalRespItem find(Object id);

    List<SieniEvalRespItem> findAll();

    List<SieniEvalRespItem> findRange(int[] range);
    
    List<SieniEvalRespItem> findByIdEvalItem(Long idEvaluacionItem);

    int count();
    
}
