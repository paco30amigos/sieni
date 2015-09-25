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

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniAlumnoFacadeRemote {

    void create(SieniAlumno sieniAlumno);

    void edit(SieniAlumno sieniAlumno);

    void remove(SieniAlumno sieniAlumno);

    SieniAlumno find(Object id);

    List<SieniAlumno> findAll();

    List<SieniAlumno> findRange(int[] range);

    int count();

    public List<SieniAlumno> findAlumnoActivos();

    public List<SieniAlumno> findAlumnoSinUsuario();

    public List<SieniAlumno> findAlumnosNoMatriculados();

    public SieniAlumno findAlumnoUsuario(String usuario, String password);

    public List<SieniAlumno> findAlumnoRpt(Date desde, Date hasta, Long grado, Long seccion, Integer matriculadoAnioActual);

    public List<SieniAlumno> findUsuariosRpt();

    public boolean alumnoRegistrado(SieniAlumno alumno);

    public boolean alumnoRegistrados(List<SieniAlumno> alumnos);

    public Integer findSiguienteCorrelat(String inicial);

    public SieniAlumno findByNombreCompleto(String nombreCompleto);
    
//    public List<SieniAlumno> findByGrado(Long idGrado);
}
