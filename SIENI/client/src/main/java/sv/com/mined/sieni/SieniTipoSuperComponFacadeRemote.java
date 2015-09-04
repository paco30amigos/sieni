/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniTipoSuperCompon;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface SieniTipoSuperComponFacadeRemote {

    void create(SieniTipoSuperCompon sieniTipoSuperCompon);

    void edit(SieniTipoSuperCompon sieniTipoSuperCompon);

    void remove(SieniTipoSuperCompon sieniTipoSuperCompon);

    SieniTipoSuperCompon find(Object id);

    List<SieniTipoSuperCompon> findAll();

    List<SieniTipoSuperCompon> findRange(int[] range);

    int count();
    
}
