/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import org.primefaces.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import sv.com.mined.sieni.SieniAlumnRolFacadeRemote;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniDocentRolFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.form.MntoUsuariosForm;
import sv.com.mined.sieni.model.SieniAlumnRol;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniDocentRol;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.pojos.UsuariosPojo;

/**
 *
 * @author Laptop
 */
@SessionScoped
@ManagedBean(name = "mntoUsuariosController")
public class MntoUsuariosController extends MntoUsuariosForm {

    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    @EJB
    private SieniDocentRolFacadeRemote sieniDocenteRolFacadeRemote;
    @EJB
    private SieniAlumnRolFacadeRemote sieniAlumnRolFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setUsuarioNuevo(new UsuariosPojo());
        this.setUsuarioModifica(new UsuariosPojo());
        this.setUsuariosList(new ArrayList<UsuariosPojo>());
        fill();
    }

    private void fill() {
        this.setUsuariosList(new ArrayList<UsuariosPojo>());
        List<SieniAlumnRol> alumnos = sieniAlumnRolFacadeRemote.findAll();
        List<SieniDocentRol> docentes = sieniDocenteRolFacadeRemote.findAll();
        this.getUsuariosList().addAll(getAlumnosUsuarioPojo(alumnos));
        this.getUsuariosList().addAll(getDocenteUsuarioPojo(docentes));
        this.setNombresDisponibles(getNombreUsuarioPojo(this.getUsuarioNuevo().getTipoUsuario()));
        this.setNombresDisponiblesModifica(getNombreUsuarioPojo(this.getUsuarioModifica().getTipoUsuario()));

    }

    private List<UsuariosPojo> getDocenteUsuarioPojo(List<SieniDocentRol> docentes) {
        List<UsuariosPojo> ret = new ArrayList<UsuariosPojo>();
        Integer tipo;
        UsuariosPojo aux;
        for (SieniDocentRol actual : docentes) {
            tipo = 1;
            aux = new UsuariosPojo();
            aux.setTipoUsuario(getTipoUsuario(tipo));
            aux.setCodTipoUsuario(tipo.toString());
            aux.setCodTipoPermiso(actual.getFRolDoc());
            aux.setDocenteRol(actual);
            aux.setNombre(actual.getIdDocente().getNombreCompleto());
            aux.setUsuario(actual.getIdDocente().getDcUsuario());
            aux.setEstado(getEstado(actual.getIdDocente().getDcEstado()));
            aux.setCodEstado(actual.getIdDocente().getDcEstado());
            aux.setTipoPermiso(getTipoUsuario(tipo));
            aux.setIdUsuario(actual.getIdDocente().getIdDocente());
            aux.setDocente(actual.getIdDocente());
            ret.add(aux);
        }
        return ret;
    }

    private List<UsuariosPojo> getAlumnosUsuarioPojo(List<SieniAlumnRol> alumnos) {
        List<UsuariosPojo> ret = new ArrayList<UsuariosPojo>();
        Integer tipo;
        UsuariosPojo aux;
        for (SieniAlumnRol actual : alumnos) {
            tipo = 0;
            aux = new UsuariosPojo();
            aux.setNombre(actual.getIdAlumno().getNombreCompleto());
            aux.setUsuario(actual.getIdAlumno().getAlUsuario());
            aux.setTipoUsuario(getTipoUsuario(tipo));
            aux.setCodTipoUsuario(tipo.toString());
            aux.setEstado(getEstado(actual.getIdAlumno().getAlEstado()));
            aux.setCodEstado(actual.getIdAlumno().getAlEstado());
            aux.setTipoPermiso(getTipoUsuario(tipo));
            aux.setCodTipoPermiso(actual.getFRol());
            aux.setIdUsuario(actual.getIdAlumno().getIdAlumno());
            aux.setAlumno(actual.getIdAlumno());
            aux.setAlumnoRol(actual);
            ret.add(aux);
        }
        return ret;
    }

    public String getTipoUsuario(int cod) {
        String ret = "";
        switch (cod) {
            case 0:
                ret = "Alumno";
                break;
            case 1:
                ret = "Docente";
                break;
            case 2:
                ret = "Administrador";
                break;
        }
        return ret;
    }

    public String getEstado(Character cod) {
        String ret = "";
        switch (cod) {
            case 'A':
                ret = "Activo";
                break;
            case 'I':
                ret = "Inactivo";
                break;
        }
        return ret;
    }

    public void getNombreUsuarios(ValueChangeEvent a) {
        String cod = a.getNewValue().toString();
        this.setNombresDisponibles(getNombreUsuarioPojo(cod));
    }

    private List<UsuariosPojo> getNombreUsuarioPojo(String cod) {
        List<UsuariosPojo> ret = new ArrayList<>();
        UsuariosPojo nuevo;
        if (cod == null || cod.equals("0")) {
            List<SieniAlumno> listaAlumnos = sieniAlumnoFacadeRemote.findAlumnoSinUsuario();
            for (SieniAlumno actual : listaAlumnos) {
                nuevo = new UsuariosPojo();
                nuevo.setIdUsuario(actual.getIdAlumno());
                nuevo.setNombre(actual.getNombreCompleto());
                nuevo.setAlumno(actual);
                ret.add(nuevo);
            }
        } else {
            List<SieniDocente> listaDocentes = sieniDocenteFacadeRemote.findDocentesSinUsuario();
            for (SieniDocente actual : listaDocentes) {
                nuevo = new UsuariosPojo();
                nuevo.setIdUsuario(actual.getIdDocente());
                nuevo.setNombre(actual.getNombreCompleto());
                nuevo.setDocente(actual);
                ret.add(nuevo);
            }
        }
        return ret;
    }

    public void guardar() {
        if (validarNuevo(this.getUsuarioNuevo())) {//valida el guardado

            if (this.getUsuarioNuevo().getCodTipoUsuario().equals("0")) {
                for (UsuariosPojo actual : this.getNombresDisponibles()) {
                    if (actual.getIdUsuario().equals(this.getUsuarioNuevo().getIdUsuario())) {
                        this.getUsuarioNuevo().setAlumno(actual.getAlumno());
                        break;
                    }
                }

                SieniAlumno alumnoEdit = this.getUsuarioNuevo().getAlumno();
                SieniAlumnRol alumnoRolNuevo = new SieniAlumnRol();
                alumnoEdit.setAlContrasenia(encriptarContrasenia(this.getUsuarioNuevo().getPass1()));
                alumnoEdit.setAlEstado(this.getUsuarioNuevo().getCodEstado());
                alumnoEdit.setAlUsuario(this.getUsuarioNuevo().getUsuario());

                alumnoRolNuevo.setIdAlumno(alumnoEdit);
                alumnoRolNuevo.setFRol(Long.parseLong(this.getUsuarioNuevo().getCodTipoUsuario()));
                //actualiza la contraseña y usuario
                sieniAlumnoFacadeRemote.edit(alumnoEdit);
                //crea el nuevo usuario
                sieniAlumnRolFacadeRemote.create(alumnoRolNuevo);
            } else {
                for (UsuariosPojo actual : this.getNombresDisponibles()) {
                    if (actual.getIdUsuario().equals(this.getUsuarioNuevo().getIdUsuario())) {
                        this.getUsuarioNuevo().setDocente(actual.getDocente());
                        break;
                    }
                }
                SieniDocente docenteEdit = this.getUsuarioNuevo().getDocente();
                SieniDocentRol docenteRolNuevo = new SieniDocentRol();
                docenteEdit.setDcContrasenia(this.getUsuarioNuevo().getPass1());
                docenteEdit.setDcEstado(this.getUsuarioNuevo().getCodEstado());
                docenteEdit.setDcUsuario(this.getUsuarioNuevo().getUsuario());

                docenteRolNuevo.setIdDocente(docenteEdit);
                docenteRolNuevo.setFRolDoc(Long.parseLong(this.getUsuarioNuevo().getCodTipoUsuario()));
                //actualiza la contraseña y usuario
                sieniDocenteFacadeRemote.edit(docenteEdit);
                //crea el nuevo usuario
                sieniDocenteRolFacadeRemote.create(docenteRolNuevo);
            }
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Usuario", this.getUsuarioNuevo().getIdUsuario(), new Character('D')));
            FacesMessage msg = new FacesMessage("Usuario Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setIndexMenu(0);
        }
        this.setUsuarioNuevo(new UsuariosPojo());
        fill();
    }

    public String encriptarContrasenia(String pass) {
        String passEncriptado = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            passEncriptado = Arrays.toString(Base64.encodeToByte((digest.digest(pass.getBytes("UTF-8"))),false));
        } catch (Exception e) {
        }
        return passEncriptado;
    }

    public void quitarFormato(SieniAlumno actual) {
        actual.setAlTelefonoEm1(actual.getAlTelefonoEm1().replaceAll("-", ""));
        actual.setAlTelefonoEm2(actual.getAlTelefonoEm2().replaceAll("-", ""));
    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(UsuariosPojo nuevo) {
        boolean ban = true;

        return ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(UsuariosPojo modificado) {
        this.setUsuarioModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(UsuariosPojo eliminado) {
        this.setEliminar(eliminado);
    }

    public void guardarModifica() {
        if (validarModifica(this.getUsuarioModifica())) {//valida el guardado

            if (this.getUsuarioModifica().getCodTipoUsuario().equals("0")) {
                //valor anterior
                SieniAlumno alumnoEdit = this.getUsuarioModifica().getAlumno();
                //valor anterior
                SieniAlumnRol alumnoRolNuevo = this.getUsuarioModifica().getAlumnoRol();

                if (this.getUsuarioModifica().getPass1() != null && !this.getUsuarioModifica().getPass1().isEmpty()) {
                    alumnoEdit.setAlContrasenia(encriptarContrasenia(this.getUsuarioModifica().getPass1()));
                }
                alumnoEdit.setAlEstado(this.getUsuarioModifica().getCodEstado());
                alumnoEdit.setAlUsuario(this.getUsuarioModifica().getUsuario());

                alumnoRolNuevo.setIdAlumno(alumnoEdit);
                alumnoRolNuevo.setFRol(Long.parseLong(this.getUsuarioModifica().getCodTipoUsuario()));
                //actualiza la contraseña y usuario
                sieniAlumnoFacadeRemote.edit(alumnoEdit);
                //crea el nuevo usuario 
                sieniAlumnRolFacadeRemote.edit(alumnoRolNuevo);
            } else {
                //valor anterior
                SieniDocente docenteEdit = this.getUsuarioModifica().getDocente();
                //valor anterior
                SieniDocentRol docenteRolNuevo = this.getUsuarioModifica().getDocenteRol();
                if (this.getUsuarioModifica().getPass1() != null && !this.getUsuarioModifica().getPass1().isEmpty()) {
                    docenteEdit.setDcContrasenia(this.getUsuarioModifica().getPass1());
                }
                docenteEdit.setDcEstado(this.getUsuarioModifica().getCodEstado());
                docenteEdit.setDcUsuario(this.getUsuarioModifica().getUsuario());
                docenteRolNuevo.setIdDocente(docenteEdit);
                docenteRolNuevo.setFRolDoc(Long.parseLong(this.getUsuarioModifica().getCodTipoUsuario()));
                //actualiza la contraseña y usuario
                sieniDocenteFacadeRemote.edit(docenteEdit);
                //crea el nuevo usuario
                sieniDocenteRolFacadeRemote.edit(docenteRolNuevo);
            }
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modificar", "Usuario", this.getUsuarioModifica().getIdUsuario(), new Character('D')));
            FacesMessage msg = new FacesMessage("Usuario Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            resetModificaForm();
            this.setIndexMenu(0);
        }
        fill();
    }

    public void resetModificaForm() {
        this.setUsuarioModifica(new UsuariosPojo());
    }

    public boolean validarModifica(UsuariosPojo nuevo) {
        boolean ban = true;

        return ban;
    }

    public void eliminarUsuario() {
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Usuario", this.getEliminar().getIdUsuario(), new Character('D')));
        if (this.getEliminar().getTipoUsuario().equals("Alumno")) {
            this.getEliminar().getAlumno().setAlEstado('I');
            sieniAlumnoFacadeRemote.edit(this.getEliminar().getAlumno());
            sieniAlumnRolFacadeRemote.remove(this.getEliminar().getAlumnoRol());
        } else {
            this.getEliminar().getAlumno().setAlEstado('I');
            sieniDocenteFacadeRemote.edit(this.getEliminar().getDocente());
            sieniDocenteRolFacadeRemote.remove(this.getEliminar().getDocenteRol());
        }
        fill();
    }
}
