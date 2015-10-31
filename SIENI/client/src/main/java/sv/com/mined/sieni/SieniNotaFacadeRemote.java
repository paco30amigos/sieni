/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniNota;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniNotaFacadeRemote {

    void create(SieniNota sieniNota);

    void edit(SieniNota sieniNota);

    void remove(SieniNota sieniNota);

    SieniNota find(Object id);

    List<SieniNota> findAll();

    List<SieniNota> findRange(int[] range);

    int count();

    public void merge(List<SieniNota> notas);

    public List<SieniNota> getNotasRangoFecha(Date desde, Date hasta);
    
    public List<SieniNota> getNotasRpt(Date desde, Date hasta,Long grado,Long seccion);

    List<SieniNota> findAllNoEliminadas();

    public boolean findNotaRegistrada(SieniNota nota);
    
    public List<SieniNota> findByAlumno(Long idAlumno);

    public List<SieniNota> findByGradoSecMatRpt(Date desde, Date hasta, Long grado, Long seccion, Long materia);
}
