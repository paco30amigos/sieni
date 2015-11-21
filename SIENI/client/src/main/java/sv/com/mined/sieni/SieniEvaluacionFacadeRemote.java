/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.Date;
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
    
    SieniEvaluacion createAndReturn(SieniEvaluacion sieniEvaluacion);

    void edit(SieniEvaluacion sieniEvaluacion);

    void remove(SieniEvaluacion sieniEvaluacion);

    SieniEvaluacion find(Object id);

    List<SieniEvaluacion> findAll();
    
    List<SieniEvaluacion> findActivos();
    
    List<SieniEvaluacion> findEvaluacionDesdeHasta(Date desde, Date hasta);

    List<SieniEvaluacion> findRange(int[] range);

    SieniEvaluacion findEvalItemResp(Long idEvaluacion);
    
    int count();

    public List<SieniEvaluacion> findbyRendimientoRpt(Date desde, Date hasta, String grado, String seccion, String materia);
    
    public List<SieniEvaluacion> findByIdMateria(List<Long> listIdMateria);

    public List<String> findByTipo();
    
}
