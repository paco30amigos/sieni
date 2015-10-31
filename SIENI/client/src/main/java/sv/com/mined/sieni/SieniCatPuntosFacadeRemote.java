/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniCatPuntos;

/**
 *
 * @author bugtraq
 */
@Remote
public interface SieniCatPuntosFacadeRemote {

    void create(SieniCatPuntos sieniCatPuntos);

    void edit(SieniCatPuntos sieniCatPuntos);

    void remove(SieniCatPuntos sieniCatPuntos);

    SieniCatPuntos find(Object id);

    List<SieniCatPuntos> findAll();

    List<SieniCatPuntos> findRange(int[] range);

    int count();
    
    public SieniCatPuntos findByClase(Long idClase);
    
    
    public List<SieniCatPuntos> findRptAvance();
}
