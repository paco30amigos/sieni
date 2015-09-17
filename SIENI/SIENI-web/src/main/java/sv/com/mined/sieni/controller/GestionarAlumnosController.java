/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.form.GestionarAlumnosForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.DateUtils;
import utils.EmailValidator;
import utils.FormatUtils;

/**
 *
 * @author Laptop
 */
@SessionScoped
@ManagedBean(name = "gestionarAlumnosController")
public class GestionarAlumnosController extends GestionarAlumnosForm {

    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @PostConstruct
    public void init() {
        this.setAlumnoNuevo(new SieniAlumno());
        this.setAlumnoModifica(new SieniAlumno());
        this.setAlumnosList(new ArrayList<SieniAlumno>());
        fill();
    }

    private void fill() {
        this.setAlumnosList(sieniAlumnoFacadeRemote.findAlumnoActivos());
    }

    public void guardar() {
//        Character tipoUsuario = ;//hay que extraer el del usuario logueado
        this.getAlumnoNuevo().setAlFoto(this.getFotoArchivo());
        quitarFormato(this.getAlumnoNuevo());//quita el formato de los campos
        if (validarNuevo(this.getAlumnoNuevo())) {//valida el guardado
            this.getAlumnoNuevo().setAlEstado('A');
            sieniAlumnoFacadeRemote.create(this.getAlumnoNuevo());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Alumno", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
            this.setAlumnoNuevo(new SieniAlumno());
            FacesMessage msg = new FacesMessage("Expediente Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            fill();
        }
    }

    public void quitarFormato(SieniAlumno actual) {
        actual.setAlTelefonoEm1(actual.getAlTelefonoEm1().replaceAll("-", ""));
        actual.setAlTelefonoEm2(actual.getAlTelefonoEm2().replaceAll("-", ""));
    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniAlumno nuevo) {
        boolean valido = true;
        DateUtils du = new DateUtils();
        FormatUtils fu = new FormatUtils();
        EmailValidator ev = new EmailValidator();
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        //longitud de contrasenia
//        validaciones.add(new ValidationPojo(nuevo.getAlContrasenia().length() < 8, "La contraseña debe ser de almenos 8 caracteres", 0));
        //alumno ya registrado
        validaciones.add(new ValidationPojo(sieniAlumnoFacadeRemote.alumnoRegistrado(nuevo), "El Alumno ya esta registrado", FacesMessage.SEVERITY_WARN));
        validaciones.add(new ValidationPojo(this.getAlumnoNuevo().getAlFechaNacimiento().before(du.getFechaMinima()), "La fecha de nacimiento es menor que " + fu.getFormatedDate(du.getFechaMinima()), FacesMessage.SEVERITY_WARN));
        validaciones.add(new ValidationPojo(this.getAlumnoNuevo().getAlFechaNacimiento().after(du.getFechaMaxima()), "La fecha de nacimiento es mayor que " + fu.getFormatedDate(du.getFechaMaxima()), FacesMessage.SEVERITY_WARN));
        if (this.getAlumnoNuevo().getAlCorreo() != null) {//si se ingreso un correo lo valida
            validaciones.add(new ValidationPojo(ev.validate(this.getAlumnoNuevo().getAlCorreo()), "El correo electronico no válido", FacesMessage.SEVERITY_WARN));
        }
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public void cancelar() {
    }

    public void getFotoNueva(FileUploadEvent event) {
        this.setFotoArchivo(event.getFile().getContents());
        this.setFotoUsable(getImage(event.getFile().getContents()));
    }

    //metodos para modificacion de datos
    public void modificar(SieniAlumno modificado) {
        this.setFotoArchivoModifica(modificado.getAlFoto());
        this.setFotoUsableModifica(getImage(modificado.getAlFoto()));
        this.setAlumnoModifica(modificado);
        this.setIndexMenu(2);
    }

    public void ver(SieniAlumno modificado) {
        this.setFotoArchivoModifica(modificado.getAlFoto());
        this.setFotoUsableModifica(getImage(modificado.getAlFoto()));
        this.setAlumnoModifica(modificado);
        this.setIndexMenu(3);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniAlumno eliminado) {
        this.setEliminar(eliminado);
    }

    public void getFotoNuevaModifica(FileUploadEvent event) {
        this.setFotoArchivoModifica(event.getFile().getContents());
        this.setFotoUsableModifica(getImage(event.getFile().getContents()));
    }

    public void guardarModifica() {
        this.getAlumnoModifica().setAlFoto(this.getFotoArchivoModifica());
        quitarFormato(this.getAlumnoModifica());//quita el formato de los campos
        if (validarModifica(this.getAlumnoModifica())) {//valida el guardado
            sieniAlumnoFacadeRemote.edit(this.getAlumnoModifica());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modificar", "Alumno", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
            FacesMessage msg = new FacesMessage("Expediente Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            fill();
        }
    }

    public void resetModificaForm() {
        this.setAlumnoModifica(new SieniAlumno());
        this.setFotoArchivoModifica(null);
        this.setFotoUsableModifica(null);
    }

    public boolean validarModifica(SieniAlumno nuevo) {
        boolean valido = true;
        DateUtils du = new DateUtils();
        FormatUtils fu = new FormatUtils();
        EmailValidator ev = new EmailValidator();
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        //alumno ya registrado
        boolean cambio = true;
        SieniAlumno alOriginal = sieniAlumnoFacadeRemote.find(this.getAlumnoModifica().getIdAlumno());
        cambio = diferencia(alOriginal.getAlPrimApe(), nuevo.getAlPrimApe());
        cambio &= diferencia(alOriginal.getAlSeguApe(), nuevo.getAlSeguApe());
        cambio &= diferencia(alOriginal.getAlTercApe(), nuevo.getAlTercApe());
        cambio &= diferencia(alOriginal.getAlPrimNombre(), nuevo.getAlPrimNombre());
        cambio &= diferencia(alOriginal.getAlSeguNombre(), nuevo.getAlSeguNombre());
        cambio &= diferencia(alOriginal.getAlTercNombre(), nuevo.getAlTercNombre());
        if (!cambio) {
            validaciones.add(new ValidationPojo(sieniAlumnoFacadeRemote.alumnoRegistrado(nuevo), "El Alumno ya esta existe", FacesMessage.SEVERITY_WARN));
        }
        validaciones.add(new ValidationPojo(this.getAlumnoModifica().getAlFechaNacimiento().before(du.getFechaMinima()), "La fecha de nacimiento es menor que " + fu.getFormatedDate(du.getFechaMinima()), FacesMessage.SEVERITY_WARN));
        validaciones.add(new ValidationPojo(this.getAlumnoModifica().getAlFechaNacimiento().after(du.getFechaMaxima()), "La fecha de nacimiento es mayor que " + fu.getFormatedDate(du.getFechaMaxima()), FacesMessage.SEVERITY_WARN));
        if (this.getAlumnoNuevo().getAlCorreo() != null) {//si se ingreso un correo lo valida
            validaciones.add(new ValidationPojo(ev.validate(this.getAlumnoModifica().getAlCorreo()), "El correo electronico no válido", FacesMessage.SEVERITY_WARN));
        }
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public boolean diferencia(String original, String modificado) {
        boolean ret = true;
        if (modificado != null && original != null) {
            if (!modificado.equals(original)) {
                ret = false;
            }
        } else {
            if (!((modificado == null && original != null) || modificado != null && original == null)) {
                ret = false;
            }
        }
        return ret;
    }

    public void eliminarExpediente() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Alumno", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
        this.getEliminar().setAlEstado(new Character('I'));
        sieniAlumnoFacadeRemote.edit(this.getEliminar());
        fill();
    }
}
