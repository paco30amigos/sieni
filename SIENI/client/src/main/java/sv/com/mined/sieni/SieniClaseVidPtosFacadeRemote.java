/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniClaseVidPtos;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface SieniClaseVidPtosFacadeRemote {

    void create(SieniClaseVidPtos sieniClaseVidPtos);

    void edit(SieniClaseVidPtos sieniClaseVidPtos);

    void remove(SieniClaseVidPtos sieniClaseVidPtos);

    SieniClaseVidPtos find(Object id);

    List<SieniClaseVidPtos> findAll();

    List<SieniClaseVidPtos> findRange(int[] range);

    int count();
    
    public List<SieniClaseVidPtos> findByClase(Long idClase);
    
    public void merge(List<SieniClaseVidPtos> lista, List<SieniClaseVidPtos> eliminados);
}
