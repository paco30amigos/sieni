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
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniCursoFacadeRemote;
import sv.com.mined.sieni.SieniNoticiaFacadeRemote;
import sv.com.mined.sieni.form.GestionarNoticiasForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.model.SieniNoticia;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.DateUtils;
import utils.EmailValidator;
import utils.FormatUtils;

/**
 *
 * @author Laptop
 */
@SessionScoped
@ManagedBean(name = "gestionarNoticiasController")
public class GestionarNoticiasController extends GestionarNoticiasForm {

    @EJB
    private SieniNoticiaFacadeRemote sieniNoticiaFacadeRemote;
    @EJB
    private SieniCursoFacadeRemote sieniCursoFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);
    }

    @PostConstruct
    public void init() {
        this.setNoticiaNueva(new SieniNoticia());
        this.setNoticiaModifica(new SieniNoticia());
        this.setNoticiasList(new ArrayList<SieniNoticia>());
        fill();
    }

    private void fill() {
        this.setNoticiasList(sieniNoticiaFacadeRemote.findNoticiasActivas());
        this.setCursosList(sieniCursoFacadeRemote.findAll());
    }

    public void refresh() {
        fill();
    }

    public void getFotoNueva(FileUploadEvent event) {
        this.setFotoArchivo(event.getFile().getContents());
        this.setFotoUsable(getImage(event.getFile().getContents()));
    }

    //metodos para modificacion de datos
    public void modificar(SieniNoticia modificado) {
        this.setFotoArchivoModifica(modificado.getNcFoto());
        this.setFotoUsableModifica(getImage(modificado.getNcFoto()));
        this.setNoticiaModifica(modificado);
        if (modificado.getIdCurso() != null) {
            this.setIdCursoModifica(modificado.getIdCurso().getIdCurso());
        } else {
            this.setIdCursoModifica(null);
        }
        this.setIndexMenu(2);
    }

    public void ver(SieniNoticia modificado) {
        this.setFotoArchivoModifica(modificado.getNcFoto());
        this.setFotoUsableModifica(getImage(modificado.getNcFoto()));
        this.setNoticiaModifica(modificado);
        this.setIndexMenu(3);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniNoticia eliminado) {
        this.setEliminar(eliminado);
    }

    public void getFotoNuevaModifica(FileUploadEvent event) {
        this.setFotoArchivoModifica(event.getFile().getContents());
        this.setFotoUsableModifica(getImage(event.getFile().getContents()));
    }

    public synchronized void guardar() {
        try {
//        Character tipoUsuario = ;//hay que extraer el del usuario logueado
            for (SieniCurso actual : this.getCursosList()) {
                if (actual.getIdCurso().equals(this.getIdCurso())) {
                    this.getNoticiaNueva().setIdCurso(actual);
                    break;
                }
            }
            this.getNoticiaNueva().setNcFoto(this.getFotoArchivo());
            if (validarNuevo(this.getNoticiaNueva())) {//valida el guardado
                HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
                NotificacionesController notifyBean = (NotificacionesController) req.getSession().getAttribute("notificacionesController");
                this.getNoticiaNueva().setNcEstado('A');
                this.getNoticiaNueva().setNcPublica(loginBean.getUsuario());
                this.setNoticiaNueva(sieniNoticiaFacadeRemote.createAndReturn(this.getNoticiaNueva()));
                registrarEnBitacora("Crear", "Noticia", this.getNoticiaNueva().getIdNoticia());
                notifyBean.insertNotifyNoticia(this.getNoticiaNueva());

                this.setNoticiaNueva(new SieniNoticia());
                FacesMessage msg = new FacesMessage("Noticia Agregada Exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                fill();
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public boolean validarNuevo(SieniNoticia nuevo) {
        boolean valido = true;
        DateUtils du = new DateUtils();
        FormatUtils fu = new FormatUtils();
        EmailValidator ev = new EmailValidator();
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public synchronized void guardarModifica() {
        try {
            for (SieniCurso actual : this.getCursosList()) {
                if (actual.getIdCurso().equals(this.getIdCursoModifica())) {
                    this.getNoticiaModifica().setIdCurso(actual);
                    break;
                }
            }
            this.getNoticiaModifica().setNcFoto(this.getFotoArchivoModifica());
            if (validarModifica(this.getNoticiaModifica())) {//valida el guardado
                sieniNoticiaFacadeRemote.edit(this.getNoticiaModifica());
                registrarEnBitacora("Modificar", "Noticia", this.getNoticiaModifica().getIdNoticia());
                FacesMessage msg = new FacesMessage("Noticia Modificada Exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                fill();
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public boolean validarModifica(SieniNoticia nuevo) {
        boolean valido = true;
        DateUtils du = new DateUtils();
        FormatUtils fu = new FormatUtils();
        EmailValidator ev = new EmailValidator();
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        //alumno ya registrado
        boolean cambio = true;
        SieniNoticia alOriginal = sieniNoticiaFacadeRemote.find(this.getNoticiaModifica().getIdNoticia());
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public void resetModificaForm() {
        this.setNoticiaModifica(new SieniNoticia());
        this.setFotoArchivoModifica(null);
        this.setFotoUsableModifica(null);
        this.setIdCursoModifica(null);
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

    public synchronized void eliminarNoticia() {
        try {
            registrarEnBitacora("Eliminar", "Noticia", this.getEliminar().getIdNoticia());
            this.getEliminar().setNcEstado(new Character('I'));
            sieniNoticiaFacadeRemote.edit(this.getEliminar());
            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }
}
