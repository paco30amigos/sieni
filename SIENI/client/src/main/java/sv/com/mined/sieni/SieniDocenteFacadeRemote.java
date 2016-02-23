/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import sv.com.mined.sieni.model.SieniDocente;

/**
 *
 * @author Laptop
 */
@Remote
public interface SieniDocenteFacadeRemote {

    void create(SieniDocente sieniDocente);

    SieniDocente createAndReturn(SieniDocente sieniDocente);

    void edit(SieniDocente sieniDocente);

    void remove(SieniDocente sieniDocente);

    SieniDocente find(Object id);

    List<SieniDocente> findAll();

    List<SieniDocente> findRange(int[] range);

    int count();

    public List<SieniDocente> findDocentesSinUsuario();

    public SieniDocente findDocenteUsuario(String usuario, String pass);

    public List<SieniDocente> findDocentesActivos();

    public List<SieniDocente> findDocentesDesdeHasta(Date desde, Date hasta);

    public List<SieniDocente> findUsuariosRpt(Integer estadoUser);

    public SieniDocente findUsuario(String usuario);

    public SieniDocente findByDocenteId(Long idDocente);
    
    public boolean docenteRegistrado(SieniDocente docente);
    
    public SieniDocente findByNombreCompleto(String nombreCompleto);
}
