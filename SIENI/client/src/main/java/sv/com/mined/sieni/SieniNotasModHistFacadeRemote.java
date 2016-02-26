/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniNotasModHist;

/**
 *
 * @author bugtraq
 */
@Remote
public interface SieniNotasModHistFacadeRemote {

    void create(SieniNotasModHist sieniNotasModHist);

    void edit(SieniNotasModHist sieniNotasModHist);

    void remove(SieniNotasModHist sieniNotasModHist);

    SieniNotasModHist find(Object id);

    List<SieniNotasModHist> findAll();

    List<SieniNotasModHist> findRange(int[] range);

    int count();

    List<SieniNotasModHist> findByFecha(Date fi, Date ff);

}
