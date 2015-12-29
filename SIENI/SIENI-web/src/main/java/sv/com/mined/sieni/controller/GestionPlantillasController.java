/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import sv.com.mined.sieni.SieniElemPlantillaFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniPlantillaFacadeRemote;
import sv.com.mined.sieni.SieniTipoElemPlantillaFacadeRemote;
import sv.com.mined.sieni.form.GestionPlantillasForm;
import sv.com.mined.sieni.model.SieniElemPlantilla;
import sv.com.mined.sieni.model.SieniPlantilla;
import sv.com.mined.sieni.model.SieniTipoElemPlantilla;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.DateUtils;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "gestionPlantillasController")
public class GestionPlantillasController extends GestionPlantillasForm {

    @EJB
    private SieniPlantillaFacadeRemote sieniPlantillaFacadeRemote;
    @EJB
    private SieniElemPlantillaFacadeRemote sieniElemPlantillaFacadeRemote;
    @EJB
    private SieniTipoElemPlantillaFacadeRemote sieniTipoElemPlantillaFacadeRemote;
    @EJB
    private SieniMateriaFacadeRemote sieniMateriaFacadeRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);
    }

    @PostConstruct
    public void init() {
        this.setPlantillaModifica(new SieniPlantilla());
        this.setPlantillaNuevo(new SieniPlantilla());
        fill();
    }

    private void fill() {
        this.setPlantillaList(sieniPlantillaFacadeRemote.findAllNoInactivas());
        this.setMaterias(sieniMateriaFacadeRemote.findMateriasActivas());
    }

    public synchronized void guardar() {
        try {
            if (validarNuevo(this.getPlantillaNuevo())) {//valida el guardado
                this.setPlantillaNuevo(sieniPlantillaFacadeRemote.createAndReturn(this.getPlantillaNuevo()));
                registrarEnBitacora("Crear", "Plantilla", this.getPlantillaNuevo().getIdPlantilla());
                new ValidationPojo().printMsj("Plantilla Creada Exitosamente", FacesMessage.SEVERITY_INFO);
                this.getPlantillaList().add(this.getPlantillaNuevo());
                this.setPlantillaNuevo(new SieniPlantilla());
//                fill();
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void quitarFormato(SieniPlantilla actual) {

    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniPlantilla nuevo) {
        boolean ban = true;
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniPlantilla modificado) {
        this.setPlantillaModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniPlantilla eliminado) {
        this.setEliminar(eliminado);
    }

    //metodos para modificacion de datos
    public void mostrar(SieniPlantilla ver) {
        this.setVer(ver);
        this.setIndexMenu(3);
    }

    public synchronized void guardarModifica() {
        try {
            if (validarModifica(this.getPlantillaModifica())) {//valida el guardado
                sieniPlantillaFacadeRemote.edit(this.getPlantillaModifica());
                registrarEnBitacora("Modificar", "Plantilla", this.getPlantillaModifica().getIdPlantilla());
                new ValidationPojo().printMsj("Plantilla Modificada Exitosamente", FacesMessage.SEVERITY_INFO);
//                fill();
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void resetModificaForm() {
        this.setPlantillaModifica(new SieniPlantilla());
    }

    public void resetNuevoForm() {
        this.setPlantillaNuevo(new SieniPlantilla());

    }

    public boolean validarModifica(SieniPlantilla nuevo) {
        boolean ban = true;
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        return ban;
    }

    public synchronized void eliminarPlantilla() {
        try {
            registrarEnBitacora("Eliminar", "Plantilla", this.getEliminar().getIdPlantilla());
            this.getEliminar().setPlEstado('I');
            sieniPlantillaFacadeRemote.edit(this.getEliminar());
            this.getPlantillaList().remove(this.getEliminar());
//            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void configurar(SieniPlantilla plantilla) {
        this.setElemPlantillaSelected(null);
        fillElemPlantillaPlantilla(plantilla);
        this.setIndexMenu(4);
    }

    public void agregarElemPlantilla() {
        SieniElemPlantilla nuevo = new SieniElemPlantilla();
        nuevo.setIdPlantilla(this.getPlantillaModifica());
        nuevo.setIdTipoElemPlantilla(this.getNuevoElem());
        nuevo.setEpEstado('A');
        nuevo.setIdElemPlantilla(-Long.parseLong(new DateUtils().getTime()));
        this.getElemPlantillaSelected().add(nuevo);
        for (SieniElemPlantilla actual : this.getElemPlantillaSelected()) {
            for (int i = 0; i < getTipoPlantilla().size(); i++) {
                if (actual.getIdTipoElemPlantilla().getIdTipoElemPlantilla().equals(getTipoPlantilla().get(i).getIdTipoElemPlantilla())) {
                    getTipoPlantilla().remove(i);
                    break;
                }
            }
        }
    }

    public synchronized void guardarElemPlantilla() {
        try {
            this.setElemPlantillaSelected(sieniElemPlantillaFacadeRemote.merge(this.getElemPlantillaSelected(), this.getElemPlantillaEliminados()));
            new ValidationPojo().printMsj("Elementos de plantilla guardados exitosamente", FacesMessage.SEVERITY_INFO);
            fillElemPlantillaPlantilla(this.getPlantillaModifica());
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void eliminarElemPlantilla() {
        SieniElemPlantilla materia = this.getElemPlantillaEliminado();
        for (int i = 0; i < this.getElemPlantillaSelected().size(); i++) {
            if (this.getElemPlantillaSelected().get(i).getIdElemPlantilla().equals(materia.getIdElemPlantilla())) {
                this.getElemPlantillaEliminados().add(this.getElemPlantillaSelected().get(i));
                this.getTipoPlantilla().add(this.getElemPlantillaSelected().get(i).getIdTipoElemPlantilla());
                this.getElemPlantillaSelected().remove(i);
                break;
            }
        }
    }

    public void eliminarElemPlantilla_(SieniElemPlantilla materia) {
        this.setElemPlantillaEliminado(materia);
    }

    public void fillElemPlantillaPlantilla(SieniPlantilla plantilla) {
        plantilla = sieniPlantillaFacadeRemote.refresh(plantilla);
        this.setPlantillaModifica(plantilla);
        this.setTipoPlantilla(sieniTipoElemPlantillaFacadeRemote.findAllActivos());
        this.setNuevoElem(new SieniTipoElemPlantilla());
        this.setElemPlantillaEliminados(new ArrayList<SieniElemPlantilla>());
        if (this.getElemPlantillaSelected() == null) {
//            this.setElemPlantillaSelected(new ArrayList<SieniElemPlantilla>());
            this.setElemPlantillaSelected(this.getPlantillaModifica().getSieniElemPlantillaList());
        }
        for (SieniElemPlantilla actual : this.getElemPlantillaSelected()) {
            for (int i = 0; i < getTipoPlantilla().size(); i++) {
                if (actual.getIdTipoElemPlantilla().getIdTipoElemPlantilla().equals(getTipoPlantilla().get(i).getIdTipoElemPlantilla())) {
                    getTipoPlantilla().remove(i);
                }
            }
        }

    }
}
