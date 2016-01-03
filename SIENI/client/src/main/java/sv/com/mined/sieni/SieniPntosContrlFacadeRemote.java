/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniPntosContrl;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniPntosContrlFacadeRemote {

    void create(SieniPntosContrl sieniPntosContrl);

    void edit(SieniPntosContrl sieniPntosContrl);

    void remove(SieniPntosContrl sieniPntosContrl);

    SieniPntosContrl find(Object id);

    List<SieniPntosContrl> findAll();

    List<SieniPntosContrl> findRange(int[] range);

    int count();
    
    public SieniPntosContrl findPuntos(Long idTipoElemPlantilla,Integer nPantalla,Long idClase,Long idAlumno);

    public List<SieniAlumno> findByAlumno(Date desde, Date hasta);
    
    public List<SieniClase> findByClasesAlumnos(Long actual);
    
    public Integer findByCountClase(Long idClase,Long idAlumno);
}
