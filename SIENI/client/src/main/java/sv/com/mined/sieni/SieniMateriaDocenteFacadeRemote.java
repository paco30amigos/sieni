/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniMateriaDocente;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface SieniMateriaDocenteFacadeRemote {

    void create(SieniMateriaDocente sieniMateriaDocente);

    void edit(SieniMateriaDocente sieniMateriaDocente);

    void remove(SieniMateriaDocente sieniMateriaDocente);

    SieniMateriaDocente find(Object id);

    List<SieniMateriaDocente> findAll();

    List<SieniMateriaDocente> findRange(int[] range);

    int count();

    public List<SieniMateriaDocente> findByDocente(Long idDocente);

    public void merge(List<SieniMateriaDocente> lista, List<SieniMateriaDocente> eliminados);
}
