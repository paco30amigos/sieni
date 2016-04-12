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
import sv.com.mined.sieni.SieniArchivoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.form.GestionarAlumnosForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniArchivo;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.CopiaArchivos;
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
    @EJB
    private SieniArchivoFacadeRemote sieniArchivoFacadeRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);
    }

    @PostConstruct
    public void init() {
        this.setAlumnoNuevo(new SieniAlumno());
        this.setAlumnoModifica(new SieniAlumno());
        this.setAlumnosList(new ArrayList<SieniAlumno>());
        resetFotos();
        fill();
    }

    private void resetFotos() {
        this.setFotoUsable(new SieniArchivo());
        this.setFotoUsableModifica(new SieniArchivo());
        this.getFotoUsableModifica().setArTipo('F');
        this.getFotoUsableModifica().setArEstado("A");
        this.getFotoUsable().setArTipo('F');
        this.getFotoUsable().setArEstado("A");
    }

    private void fill() {
        this.setAlumnosList(sieniAlumnoFacadeRemote.findAlumnosNoInactivos());
    }

    public void initNuevo() {
        resetFotos();
        CopiaArchivos ca = new CopiaArchivos();
        this.getFotoUsable().setArRuta(ca.getFotoDefault());
        this.setIndexMenu(1);
    }

    public void cancelaModifica(SieniAlumno modifica) {
        modifica = sieniAlumnoFacadeRemote.find(modifica.getIdAlumno());
        this.setIndexMenu(0);
    }
    public synchronized void guardar() {
        try {
            CopiaArchivos ca = new CopiaArchivos();
            quitarFormato(this.getAlumnoNuevo());//quita el formato de los campos
            if (validarNuevo(this.getAlumnoNuevo())) {//valida el guardado
                if (!this.getFotoUsable().getArRuta().equals(ca.getFotoDefault())) {
                    Long fotoId = guardarFoto(this.getFotoUsable());
                    this.getAlumnoNuevo().setAlFoto(fotoId);
                }
                this.getAlumnoNuevo().setAlEstado('A');
                generarCarnet(this.getAlumnoNuevo());
                this.getAlumnoNuevo().setAlFechaIngreso(new Date());
                this.getAlumnoNuevo().setAlNombreCompleto(this.getAlumnoNuevo().getNombreCompleto());
                this.setAlumnoNuevo(sieniAlumnoFacadeRemote.createAndReturn(this.getAlumnoNuevo()));
                registrarEnBitacora("Crear", "Alumno", this.getAlumnoNuevo().getIdAlumno());
                this.getAlumnosList().add(this.getAlumnoNuevo());
                this.setAlumnoNuevo(new SieniAlumno());
                new ValidationPojo().printMsj("Expediente Creado Exitosamente", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void quitarFormato(SieniAlumno actual) {
        actual.setAlTelefonoEm1(actual.getAlTelefonoEm1().replaceAll("-", ""));
        actual.setAlTelefonoEm2(actual.getAlTelefonoEm2().replaceAll("-", ""));
        actual.setAlPrimApe(actual.getAlPrimApe().trim());
        actual.setAlSeguApe(actual.getAlSeguApe().trim());
        actual.setAlTercApe(actual.getAlTercApe().trim());
        actual.setAlPrimNombre(actual.getAlPrimNombre().trim());
        actual.setAlSeguNombre(actual.getAlSeguNombre().trim());
        actual.setAlTercNombre(actual.getAlTercNombre().trim());
    }

    public synchronized Long guardarFoto(SieniArchivo archivo) {
        SieniArchivo id = sieniArchivoFacadeRemote.merge(archivo);
        return id.getIdArchivo();
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
        validaciones.add(new ValidationPojo(nuevo.getAlPrimApe().isEmpty(), "Debe ingresar Primer Apellido", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(nuevo.getAlPrimNombre().isEmpty(), "Debe ingresar Primer Nombre", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(sieniAlumnoFacadeRemote.alumnoRegistrado(nuevo), "El Alumno ya esta registrado", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(nuevo.getAlFechaNacimiento().before(du.getFechaMinima()), "La fecha de nacimiento es menor que " + fu.getFormatedDate(du.getFechaMinima()), FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(nuevo.getAlFechaNacimiento().after(du.getFechaMaxima()), "La fecha de nacimiento es mayor que " + fu.getFormatedDate(du.getFechaMaxima()), FacesMessage.SEVERITY_ERROR));
        if (nuevo.getAlCorreo() != null && !nuevo.getAlCorreo().isEmpty()) {//si se ingreso un correo lo valida
            validaciones.add(new ValidationPojo(!ev.validate(nuevo.getAlCorreo()), "El correo electronico no válido", FacesMessage.SEVERITY_ERROR));
        }
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public void cancelar() {
    }

    public void getFotoNueva(FileUploadEvent event) {
        CopiaArchivos ca = new CopiaArchivos();
        ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
        this.getFotoUsable().setArArchivo(event.getFile().getContents());
        if (this.getFotoUsable().getArRuta() == null
                || this.getFotoUsable().getArRuta().equals(ca.getFotoDefault())) {
            this.getFotoUsable().setArRuta(null);
        } else {
            ca.deleteDataToResource(this.getFotoUsable());
            this.getFotoUsable().setArRuta(null);
        }
        this.setFotoUsable(ca.updateDataToResource(this.getFotoUsable()));
        
//        CopiaArchivos ca = new CopiaArchivos();
//        ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
//        this.getFotoUsable().setArArchivo(event.getFile().getContents());
//        if (this.getFotoUsable().getArRuta().equals(ca.getFotoDefault())) {
//            this.getFotoUsable().setArRuta(null);
//        } else {
//            ca.deleteDataToResource(this.getFotoUsable());
//            this.getFotoUsable().setArRuta(null);
//        }
//        this.setFotoUsable(ca.updateDataToResource(this.getFotoUsable()));
    }

    //metodos para modificacion de datos
    public void modificar(SieniAlumno modificado) {
        SieniArchivo foto = new SieniArchivo();
        CopiaArchivos ca = new CopiaArchivos();
        resetFotos();
        if (modificado.getAlFoto() != null) {
            this.setFotoUsableModifica(sieniArchivoFacadeRemote.find(modificado.getAlFoto()));
            ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
            ca.copyDataToResource(this.getFotoUsableModifica());
        } else {
            this.getFotoUsableModifica().setArRuta(ca.getFotoDefault());
        }
        this.setAlumnoModifica(modificado);
        this.setIndexMenu(2);
    }

    public void ver(SieniAlumno modificado) {
        SieniArchivo foto = new SieniArchivo();
        CopiaArchivos ca = new CopiaArchivos();
        resetFotos();
        if (modificado.getAlFoto() != null) {
            this.setFotoUsableModifica(sieniArchivoFacadeRemote.find(modificado.getAlFoto()));
            ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
            ca.copyDataToResource(this.getFotoUsableModifica());
        } else {
            this.getFotoUsableModifica().setArRuta(ca.getFotoDefault());
        }
        this.setAlumnoModifica(modificado);
        this.setIndexMenu(3);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniAlumno eliminado) {
        this.setEliminar(eliminado);
    }

    public void getFotoNuevaModifica(FileUploadEvent event) {
        CopiaArchivos ca = new CopiaArchivos();
        ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
        this.getFotoUsableModifica().setArArchivo(event.getFile().getContents());
        if (this.getFotoUsableModifica().getArRuta() == null
                || this.getFotoUsableModifica().getArRuta().equals(ca.getFotoDefault())) {
            this.getFotoUsableModifica().setArRuta(null);
        } else {
            ca.deleteDataToResource(this.getFotoUsableModifica());
            this.getFotoUsableModifica().setArRuta(null);
        }
        this.setFotoUsableModifica(ca.updateDataToResource(this.getFotoUsableModifica()));
    }

    public synchronized void guardarModifica() {
        try {
//        this.getAlumnoModifica().setAlFoto(this.getFotoArchivoModifica());
            quitarFormato(this.getAlumnoModifica());//quita el formato de los campos
            if (validarModifica(this.getAlumnoModifica())) {//valida el guardado
                Long fotoId = guardarFoto(this.getFotoUsableModifica());
                this.getAlumnoModifica().setAlFoto(fotoId);
                if (this.getAlumnoModifica().getAlCarnet() == null) {
                    generarCarnet(this.getAlumnoModifica());
                }
                this.getAlumnoModifica().setAlNombreCompleto(this.getAlumnoModifica().getNombreCompleto());
                sieniAlumnoFacadeRemote.edit(this.getAlumnoModifica());
                registrarEnBitacora("Modificar", "Alumno", this.getAlumnoModifica().getIdAlumno());
                new ValidationPojo().printMsj("Expediente Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
//            fill();
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
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
        validaciones.add(new ValidationPojo(this.getAlumnoModifica().getAlPrimApe().isEmpty(), "Debe ingresar Primer Apellido", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(this.getAlumnoModifica().getAlPrimNombre().isEmpty(), "Debe ingresar Primer Nombre", FacesMessage.SEVERITY_ERROR));
        SieniAlumno alOriginal = sieniAlumnoFacadeRemote.find(this.getAlumnoModifica().getIdAlumno());
        cambio = diferencia(alOriginal.getAlPrimApe(), nuevo.getAlPrimApe());
        cambio &= diferencia(alOriginal.getAlSeguApe(), nuevo.getAlSeguApe());
        cambio &= diferencia(alOriginal.getAlTercApe(), nuevo.getAlTercApe());
        cambio &= diferencia(alOriginal.getAlPrimNombre(), nuevo.getAlPrimNombre());
        cambio &= diferencia(alOriginal.getAlSeguNombre(), nuevo.getAlSeguNombre());
        cambio &= diferencia(alOriginal.getAlTercNombre(), nuevo.getAlTercNombre());
        if (!cambio) {
            validaciones.add(new ValidationPojo(sieniAlumnoFacadeRemote.alumnoRegistrado(nuevo), "El Alumno ya esta existe", FacesMessage.SEVERITY_ERROR));
        }
        validaciones.add(new ValidationPojo(this.getAlumnoModifica().getAlFechaNacimiento().before(du.getFechaMinima()), "La fecha de nacimiento es menor que " + fu.getFormatedDate(du.getFechaMinima()), FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(this.getAlumnoModifica().getAlFechaNacimiento().after(du.getFechaMaxima()), "La fecha de nacimiento es mayor que " + fu.getFormatedDate(du.getFechaMaxima()), FacesMessage.SEVERITY_ERROR));
        if (nuevo.getAlCorreo() != null && !nuevo.getAlCorreo().isEmpty()) {//si se ingreso un correo lo valida
            validaciones.add(new ValidationPojo(!ev.validate(nuevo.getAlCorreo()), "El correo electronico no válido", FacesMessage.SEVERITY_ERROR));
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

    public synchronized void eliminarExpediente() {
        try {
            registrarEnBitacora("Eliminar", "Alumno", this.getEliminar().getIdAlumno());
            this.getEliminar().setAlFechaBaja(new Date());
            this.getEliminar().setAlEstado(new Character('I'));
            sieniAlumnoFacadeRemote.edit(this.getEliminar());
            fill();
            new ValidationPojo().printMsj("Registro eliminado exitosamente", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public synchronized SieniAlumno generarCarnet(SieniAlumno alumno) {
        String carnet = "";
        String inicial = alumno.getAlPrimNombre().charAt(0) + "" + alumno.getAlPrimApe().charAt(0) + "";
        inicial = inicial.toUpperCase();
        String anioActual = (new FormatUtils().getFormatedAnio(new Date())).substring(2, 4);
        //inicial primer nombre+primer apellido+ anio de registro
        Integer codigo = sieniAlumnoFacadeRemote.findSiguienteCorrelat(inicial + anioActual);//siguiente codigo
        Integer tamaActual = codigo.toString().length();
        Integer logitudCodigoCarnet = 4;
        //agrega ceros al inicio del codigo
        StringBuffer codigoFormat = new StringBuffer();
        for (int i = 0; i < logitudCodigoCarnet; i++) {
            if (tamaActual + codigoFormat.length() < logitudCodigoCarnet) {
                codigoFormat.append("0");
            } else {
                break;
            }
        }
//        carnet="PP150001";
        carnet = inicial + anioActual + codigoFormat.toString() + codigo;
        alumno.setAlCodigoCarnet(inicial + anioActual);
        alumno.setAlCorrelatCarnet(codigo);
        alumno.setAlCarnet(carnet);
        return alumno;
    }
}
