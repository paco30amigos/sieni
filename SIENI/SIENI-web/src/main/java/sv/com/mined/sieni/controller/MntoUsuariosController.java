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
import javax.servlet.http.HttpServletRequest;
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
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.DateUtils;
import utils.EmailValidator;
import utils.FormatUtils;

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

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);
    }

    @PostConstruct
    public void init() {
        this.setUsuarioNuevo(new UsuariosPojo());
        this.setUsuarioModifica(new UsuariosPojo());
        this.setUsuarioModPass(new UsuariosPojo());
        this.setUsuariosList(new ArrayList<UsuariosPojo>());
        fill();
    }

    private void fill() {
        this.setUsuariosList(new ArrayList<UsuariosPojo>());
        List<SieniAlumnRol> alumnos = sieniAlumnRolFacadeRemote.findAllNoInactivos();
        List<SieniDocentRol> docentes = sieniDocenteRolFacadeRemote.findAllNoInactivos();
        this.getUsuariosList().addAll(getAlumnosUsuarioPojo(alumnos));
        this.getUsuariosList().addAll(getDocenteUsuarioPojo(docentes));
    }

    private List<UsuariosPojo> getDocenteUsuarioPojo(List<SieniDocentRol> docentes) {
        List<UsuariosPojo> ret = new ArrayList<UsuariosPojo>();
        Integer tipo;
        UsuariosPojo aux;
        StringBuffer sb = new StringBuffer();
        for (SieniDocentRol actual : docentes) {
            tipo = 1;
            sb = new StringBuffer();
            sb.append(actual.getFRolDoc());
            aux = new UsuariosPojo();
            aux.setTipoUsuario(getTipoUsuario(tipo));
            aux.setCodTipoUsuario(tipo.toString());
            aux.setCodTipoPermiso(actual.getFRolDoc());
            aux.setDocenteRol(actual);
            aux.setNombre(actual.getIdDocente().getNombreCompleto());
            aux.setUsuario(actual.getIdDocente().getDcUsuario());
            aux.setEstado(getEstado(actual.getIdDocente().getDcEstado()));
            aux.setCodEstado(actual.getIdDocente().getDcEstado());

            aux.setTipoPermiso(getTipoUsuario(Integer.parseInt(sb.toString())));
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
        StringBuffer sb = new StringBuffer();
        for (SieniAlumnRol actual : alumnos) {
            tipo = 0;
            sb = new StringBuffer();
            sb.append(actual.getFRol());
            aux = new UsuariosPojo();
            aux.setNombre(actual.getIdAlumno().getNombreCompleto());
            aux.setUsuario(actual.getIdAlumno().getAlUsuario());
            aux.setTipoUsuario(getTipoUsuario(tipo));
            aux.setCodTipoUsuario(tipo.toString());
            aux.setEstado(getEstado(actual.getIdAlumno().getAlEstado()));
            aux.setCodEstado(actual.getIdAlumno().getAlEstado());
            aux.setTipoPermiso(getTipoUsuario(Integer.parseInt(sb.toString())));
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
            case 3:
                ret = "Subdirector";
                break;
            case 4:
                ret = "Director";
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
            case 'D':
                ret = "Inactivo";
                break;
            case 'I':
                ret = "Eliminado";
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

    public synchronized void guardar() {
        try {
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
                    alumnoRolNuevo.setSarEstado('A');
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
                    docenteEdit.setDcContrasenia(encriptarContrasenia(this.getUsuarioNuevo().getPass1()));
                    docenteEdit.setDcEstado(this.getUsuarioNuevo().getCodEstado());
                    docenteEdit.setDcUsuario(this.getUsuarioNuevo().getUsuario());

                    docenteRolNuevo.setIdDocente(docenteEdit);
                    docenteRolNuevo.setFRolDoc(Long.parseLong(this.getUsuarioNuevo().getCodTipoUsuario()));
                    docenteRolNuevo.setSdrEstado('A');
                    //actualiza la contraseña y usuario
                    sieniDocenteFacadeRemote.edit(docenteEdit);
                    //crea el nuevo usuario
                    sieniDocenteRolFacadeRemote.create(docenteRolNuevo);
                }
                registrarEnBitacora("Crear", "Usuario", this.getUsuarioNuevo().getIdUsuario());
                new ValidationPojo().printMsj("Usuario Creado Exitosamente", FacesMessage.SEVERITY_INFO);
                this.setUsuarioNuevo(new UsuariosPojo());
                this.getUsuarioNuevo().setCodTipoUsuario("0");
                fill();
                this.setNombresDisponibles(getNombreUsuarioPojo(this.getUsuarioNuevo().getTipoUsuario()));
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public String encriptarContrasenia(String pass) {
        String passEncriptado = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            passEncriptado = Arrays.toString(Base64.encodeToByte((digest.digest(pass.getBytes("UTF-8"))), false));
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
        boolean valido = true;
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        //longitud de contrasenia
        validaciones.add(new ValidationPojo(nuevo.getPass1().length() < 8, "La contraseña debe ser de almenos 8 caracteres", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(sieniDocenteFacadeRemote.findUsuario(nuevo.getUsuario()) != null, "El usuario ya está registrado", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(sieniAlumnoFacadeRemote.findUsuario(nuevo.getUsuario()) != null, "El usuario ya está registrado", FacesMessage.SEVERITY_ERROR));
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public void cancelar() {
    }

    public void crear() {
        this.setNombresDisponibles(getNombreUsuarioPojo(this.getUsuarioNuevo().getTipoUsuario()));
        this.setIndexMenu(1);
    }

    //metodos para modificacion de datos
    public void modificar(UsuariosPojo modificado) {
        modificado.setPass1("");
        modificado.setPass2("");
        modificado.setPass0("");
        this.setUsuarioModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(UsuariosPojo eliminado) {
        this.setEliminar(eliminado);
    }

    public synchronized void guardarModifica() {
        try {
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
                    alumnoRolNuevo.setFRol(this.getUsuarioModifica().getCodTipoPermiso());
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
                        docenteEdit.setDcContrasenia(encriptarContrasenia(this.getUsuarioModifica().getPass1()));
                    }
                    docenteEdit.setDcEstado(this.getUsuarioModifica().getCodEstado());
                    docenteEdit.setDcUsuario(this.getUsuarioModifica().getUsuario());
                    docenteRolNuevo.setIdDocente(docenteEdit);
                    docenteRolNuevo.setFRolDoc(this.getUsuarioModifica().getCodTipoPermiso());
                    //actualiza la contraseña y usuario
                    sieniDocenteFacadeRemote.edit(docenteEdit);
                    //crea el nuevo usuario
                    sieniDocenteRolFacadeRemote.edit(docenteRolNuevo);
                }
                registrarEnBitacora("Modificar", "Usuario", this.getUsuarioModifica().getIdUsuario());
                new ValidationPojo().printMsj("Usuario Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
                fill();
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void resetModificaForm() {
        this.setUsuarioModifica(new UsuariosPojo());
    }

    public boolean validarModifica(UsuariosPojo nuevo) {
        boolean valido = true;
        DateUtils du = new DateUtils();
        FormatUtils fu = new FormatUtils();
        EmailValidator ev = new EmailValidator();
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        //longitud de contrasenia
        if (nuevo.getPass1() != null && !nuevo.getPass1().isEmpty()) {
            validaciones.add(new ValidationPojo(nuevo.getPass1().length() < 8, "La contraseña debe ser de almenos 8 caracteres", FacesMessage.SEVERITY_ERROR));
        }
        SieniDocente usuarioDoc = sieniDocenteFacadeRemote.findUsuario(nuevo.getUsuario());
        SieniAlumno usuarioAl = sieniAlumnoFacadeRemote.findUsuario(nuevo.getUsuario());
        validaciones.add(new ValidationPojo(usuarioDoc != null && !usuarioDoc.getIdDocente().equals(nuevo.getDocente().getIdDocente()), "El usuario ya está registrado", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(usuarioAl != null && !usuarioAl.getIdAlumno().equals(nuevo.getAlumno().getIdAlumno()), "El usuario ya está registrado", FacesMessage.SEVERITY_ERROR));
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public synchronized void eliminarUsuario() {
        try {
            registrarEnBitacora("Eliminar", "Usuario", this.getUsuarioNuevo().getIdUsuario());
            if (this.getEliminar().getTipoUsuario().equals("Alumno")) {
                this.getEliminar().getAlumno().setAlEstado('I');
                sieniAlumnoFacadeRemote.edit(this.getEliminar().getAlumno());
                this.getEliminar().getAlumnoRol().setSarEstado('I');
                sieniAlumnRolFacadeRemote.edit(this.getEliminar().getAlumnoRol());
//            sieniAlumnRolFacadeRemote.remove(this.getEliminar().getAlumnoRol());
            } else {
                this.getEliminar().getDocente().setDcEstado('I');
                sieniDocenteFacadeRemote.edit(this.getEliminar().getDocente());
                this.getEliminar().getDocenteRol().setSdrEstado('I');
                sieniDocenteRolFacadeRemote.edit(this.getEliminar().getDocenteRol());
//            sieniDocenteRolFacadeRemote.remove(this.getEliminar().getDocenteRol());
            }
            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void ver(UsuariosPojo modificado) {
        this.setUsuarioModifica(modificado);
        this.setIndexMenu(3);
    }

    public synchronized void guardarModPassword() {
        try {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");

            if (loginBean.getTipoUsuario().charAt(0) == 'A') {
                String antiguoPass = loginBean.getPassword();
                SieniAlumno alumnoEdit = loginBean.getAlumno();
                if (this.getUsuarioModPass().getPass0().equals(antiguoPass)) {
                    if (this.getUsuarioModPass().getPass1() != null && !this.getUsuarioModPass().getPass1().isEmpty()) {
                        alumnoEdit.setAlContrasenia(encriptarContrasenia(this.getUsuarioModPass().getPass1()));
                    }
                }
                sieniAlumnoFacadeRemote.edit(alumnoEdit);
            } else if (loginBean.getTipoUsuario().charAt(0) == 'D') {
                String antiguoPass = loginBean.getPassword();
                SieniDocente docenteEdit = loginBean.getDocente();
                if (this.getUsuarioModPass().getPass0().equals(antiguoPass)) {
                    if (this.getUsuarioModPass().getPass1() != null && !this.getUsuarioModPass().getPass1().isEmpty()) {
                        docenteEdit.setDcContrasenia(encriptarContrasenia(this.getUsuarioModPass().getPass1()));
                    }
                }
                sieniDocenteFacadeRemote.edit(docenteEdit);
            }
            registrarEnBitacora("Cambio Contraseña", "Usuario", loginBean.getIdUsuario());
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

}
