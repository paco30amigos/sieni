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

    SieniAlumno createAndReturn(SieniAlumno sieniAlumno);

    void edit(SieniAlumno sieniAlumno);

    void remove(SieniAlumno sieniAlumno);

    SieniAlumno find(Object id);

    List<SieniAlumno> findAll();

    List<SieniAlumno> findRange(int[] range);

    int count();

    public List<SieniAlumno> findAlumnoActivos();

    public List<SieniAlumno> findAlumnosNoInactivos();

    public List<SieniAlumno> findAlumnoSinUsuario();

    public List<SieniAlumno> findAlumnosNoMatriculados();

    public List<SieniAlumno> findAlumnosMatriculados(Long idAnio,String anio);

    public SieniAlumno findAlumnoUsuario(String usuario, String password);

    public List<SieniAlumno> findAlumnoRpt(Date desde, Date hasta, Long grado, Long seccion, Integer matriculadoAnioActual);

    public List<SieniAlumno> findAlumnosGradoSeccionAnio(Long idGrado, Long idSeccion);

    public List<SieniAlumno> findUsuariosRpt(Integer estadoUser);

    public boolean alumnoRegistrado(SieniAlumno alumno);

    public boolean alumnoRegistrados(List<SieniAlumno> alumnos);

    public Integer findSiguienteCorrelat(String inicial);

    public SieniAlumno findByNombreCompleto(String nombreCompleto);

    public SieniAlumno findAlumnoById(Long id);

    public SieniAlumno findUsuario(String usuario);

    public List<SieniAlumno> findAlumnosNoCursos(Long idGrado,Long idSeccion, Long idCurso);

    public List<SieniAlumno> findAlumnosInscritos(Long idCurso);
//    public List<SieniAlumno> findByGrado(Long idGrado);

}
