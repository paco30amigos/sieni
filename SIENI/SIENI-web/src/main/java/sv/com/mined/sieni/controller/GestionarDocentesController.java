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
import sv.com.mined.sieni.SieniArchivoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniMateriaDocenteFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.form.GestionarDocentesForm;
import sv.com.mined.sieni.model.SieniArchivo;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniMateriaDocente;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.CopiaArchivos;
import utils.DateUtils;

/**
 *
 * @author Laptop
 */
@SessionScoped
@ManagedBean(name = "gestionarDocentesController")
public class GestionarDocentesController extends GestionarDocentesForm {

    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;
    @EJB
    private SieniMateriaDocenteFacadeRemote sieniMateriaDocenteFacadeRemote;
    @EJB
    private SieniMateriaFacadeRemote sieniMateriaFacadeRemote;
    @EJB
    private SieniArchivoFacadeRemote sieniArchivoFacadeRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);
    }

    @PostConstruct
    public void init() {
        this.setDocenteNuevo(new SieniDocente());
        this.setDocenteModifica(new SieniDocente());
        this.setDocentesList(new ArrayList<SieniDocente>());
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
        this.setDocentesList(sieniDocenteFacadeRemote.findDocentesActivos());
    }

    public void nuevo() {
        resetFotos();
        CopiaArchivos ca = new CopiaArchivos();
        this.getFotoUsable().setArRuta(ca.getFotoDefault());
        this.setIndexMenu(1);
    }

    public void guardar() {
        try {
            CopiaArchivos ca = new CopiaArchivos();
            quitarFormato(this.getDocenteNuevo());//quita el formato de los campos
            this.getDocenteNuevo().setDcEstado('A');
            if (validarNuevo(this.getDocenteNuevo())) {//valida el guardado
                if (this.getFotoUsable().getArRuta().equals(ca.getFotoDefault())) {
                    Long fotoId = guardarFoto(this.getFotoUsable());
                    this.getDocenteNuevo().setDcFoto(fotoId);
                }
                this.setDocenteNuevo(sieniDocenteFacadeRemote.createAndReturn(this.getDocenteNuevo()));
                registrarEnBitacora("Crear", "Docentes", this.getDocenteNuevo().getIdDocente());
                FacesMessage msg = new FacesMessage("Expediente Creado Exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
//            this.setIndexMenu(0);
                this.getDocentesList().add(this.getDocenteNuevo());
                this.setDocenteNuevo(new SieniDocente());
                resetFotos();
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
//        fill();
    }

    public void quitarFormato(SieniDocente actual) {
        actual.setDcTelefonoEm1(actual.getDcTelefonoEm1().replaceAll("-", ""));
        actual.setDcTelefonoEm2(actual.getDcTelefonoEm2().replaceAll("-", ""));
    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniDocente nuevo) {
        boolean ban = true;

        return ban;
    }

    public void cancelar() {
    }

    public void getFotoNueva(FileUploadEvent event) {
        CopiaArchivos ca = new CopiaArchivos();
        ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
        this.getFotoUsable().setArArchivo(event.getFile().getContents());
        if (this.getFotoUsable().getArRuta().equals(ca.getFotoDefault())) {
            this.getFotoUsable().setArRuta(null);
        } else {
            ca.deleteDataToResource(this.getFotoUsable());
            this.getFotoUsable().setArRuta(null);
        }
        this.setFotoUsable(ca.updateDataToResource(this.getFotoUsable()));
    }

    //metodos para modificacion de datos
    public void modificar(SieniDocente modificado) {
        SieniArchivo foto = new SieniArchivo();
        CopiaArchivos ca = new CopiaArchivos();
        resetFotos();
        if (modificado.getDcFoto() != null) {
            this.setFotoUsableModifica(sieniArchivoFacadeRemote.find(modificado.getDcFoto()));
            ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
            ca.copyDataToResource(this.getFotoUsableModifica());
        } else {
            this.getFotoUsableModifica().setArRuta(ca.getFotoDefault());
        }
        this.setDocenteModifica(modificado);
        this.setIndexMenu(2);
    }

    public void ver(SieniDocente modificado) {
        SieniArchivo foto = new SieniArchivo();
        CopiaArchivos ca = new CopiaArchivos();
        resetFotos();
        if (modificado.getDcFoto() != null) {
            this.setFotoUsableModifica(sieniArchivoFacadeRemote.find(modificado.getDcFoto()));
            ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
            ca.copyDataToResource(this.getFotoUsableModifica());
        } else {
            this.getFotoUsableModifica().setArRuta(ca.getFotoDefault());
        }
        this.setDocenteModifica(modificado);
        this.setIndexMenu(3);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniDocente eliminado) {
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

    public void guardarModifica() {
        try {
            quitarFormato(this.getDocenteModifica());//quita el formato de los campos
            if (validarModifica(this.getDocenteModifica())) {//valida el guardado
                Long fotoId = guardarFoto(this.getFotoUsableModifica());
                this.getDocenteModifica().setDcFoto(fotoId);
                sieniDocenteFacadeRemote.edit(this.getDocenteModifica());
                registrarEnBitacora("Modificar", "Docentes", this.getDocenteModifica().getIdDocente());
                FacesMessage msg = new FacesMessage("Expediente Modificado Exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                resetModificaForm();
                this.setIndexMenu(0);
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
//        fill();
    }

    public Long guardarFoto(SieniArchivo archivo) {
        SieniArchivo id = sieniArchivoFacadeRemote.merge(archivo);
        return id.getIdArchivo();
    }

    public void resetModificaForm() {
        this.setDocenteModifica(new SieniDocente());
        this.setFotoArchivoModifica(null);
        this.setFotoUsableModifica(null);
    }

    public boolean validarModifica(SieniDocente nuevo) {
        boolean ban = true;

        return ban;
    }

    public void eliminarExpediente() {
        try {
            registrarEnBitacora("Eliminar", "Docentes", this.getEliminar().getIdDocente());
            this.getEliminar().setDcEstado(new Character('I'));
            sieniDocenteFacadeRemote.edit(this.getEliminar());
            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }

    }

    public void gestionarMateriasDocente(SieniDocente docente) {
        this.setDocenteModifica(docente);
        this.setMaterias(sieniMateriaFacadeRemote.findAll());
        fillMateriasDocente();
        this.setMateria(new SieniMateria());
        for (int j = 0; j < this.getMaterias().size(); j++) {
            for (int i = 0; i < this.getMateriasDocente().size(); i++) {
                if (this.getMateriasDocente().get(i).getIdMateria().getIdMateria().equals(this.getMaterias().get(j).getIdMateria())) {
                    this.getMaterias().remove(j);
                }
            }
        }
        this.setMateriasDocenteEliminadas(new ArrayList<SieniMateriaDocente>());
        this.setIndexMenu(4);
    }

    public void agregarMateria() {
        SieniMateriaDocente nuevo = new SieniMateriaDocente();
        nuevo.setIdDocente(this.getDocenteModifica());
        nuevo.setIdMateria(this.getMateria());
        nuevo.setMdEstado('A');
        nuevo.setIdMateriaDocente(-Long.parseLong(new DateUtils().getTime()));
        this.getMateriasDocente().add(nuevo);
        for (int i = 0; i < this.getMaterias().size(); i++) {
            if (nuevo.getIdMateria().getIdMateria().equals(this.getMaterias().get(i).getIdMateria())) {
                this.getMaterias().remove(i);
                break;
            }
        }
//        sieniMateriaDocenteFacadeRemote.create(nuevo);
    }

    public void guardarMaterias() {
        try {
            registrarEnBitacora("Modificar", "Docente - Materias", this.getDocenteModifica().getIdDocente());
            sieniMateriaDocenteFacadeRemote.merge(this.getMateriasDocente(), this.getMateriasDocenteEliminadas());
            FacesMessage msg = new FacesMessage("Materias guardadas exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            fillMateriasDocente();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void eliminarMateria() {
        SieniMateriaDocente materia = this.getMateriaEliminada();
        for (int i = 0; i < this.getMateriasDocente().size(); i++) {
            if (this.getMateriasDocente().get(i).getIdMateriaDocente().equals(materia.getIdMateriaDocente())) {
                this.getMateriasDocenteEliminadas().add(this.getMateriasDocente().get(i));
                this.getMaterias().add(this.getMateriasDocente().get(i).getIdMateria());
                this.getMateriasDocente().remove(i);
                break;
            }
        }
    }

    public void eliminarMateria(SieniMateriaDocente materia) {
        this.setMateriaEliminada(materia);
    }

    public void fillMateriasDocente() {
        List<SieniMateriaDocente> mat = sieniMateriaDocenteFacadeRemote.findByDocente(this.getDocenteModifica().getIdDocente());
        this.setMateriasDocente(mat);
    }
}
