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
import sv.com.mined.sieni.SieniTipoElemPlantillaFacadeRemote;
import sv.com.mined.sieni.form.CatTipoElemPlantillaForm;
import sv.com.mined.sieni.model.SieniElemPlantilla;
import sv.com.mined.sieni.model.SieniTipoElemPlantilla;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "catTipoElementoPlantillaController")
public class CatTipoElementoPlantillaController extends CatTipoElemPlantillaForm {

    @EJB
    private SieniTipoElemPlantillaFacadeRemote sieniTipoElemPlantillaFacadeRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);

    }

    @PostConstruct
    public void init() {
        this.setNuevo(new SieniTipoElemPlantilla());
        this.setModifica(new SieniTipoElemPlantilla());
        this.setList(new ArrayList<SieniTipoElemPlantilla>());
        fill();
    }

    private void fill() {
        this.setList(sieniTipoElemPlantillaFacadeRemote.findAllNoInactivos());
    }
    
    public void cancelaModifica(SieniTipoElemPlantilla modifica) {
        modifica = sieniTipoElemPlantillaFacadeRemote.find(modifica.getIdTipoElemPlantilla());
        this.setIndexMenu(0);
    }

    public synchronized void guardar() {
        try {
            if (validarNuevo(this.getNuevo())) {//valida el guardado
                this.getNuevo().setTeEstado('A');
                
                this.setNuevo(sieniTipoElemPlantillaFacadeRemote.createAndReturn(this.getNuevo()));
//                sieniTipoElemPlantillaFacadeRemote.create(this.getNuevo());
                registrarEnBitacora("Guardar", "TipoElemPlantilla", this.getNuevo().getIdTipoElemPlantilla());
                new ValidationPojo().printMsj("TipoElemPlantilla Creado Exitosamente", FacesMessage.SEVERITY_INFO);
                //agrega el nuevo archivo a la lista de la tabla actual para no hacer el fill
                this.getList().add(this.getNuevo());
                //limpia los datos para un registro nuevo
                this.setNuevo(new SieniTipoElemPlantilla());
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniTipoElemPlantilla nuevo) {
        boolean ban = true;
        List<ValidationPojo> validaciones = new ArrayList<>();
        validaciones.add(new ValidationPojo(sieniTipoElemPlantillaFacadeRemote.findByNombre(nuevo.getTeDescripcion()) != null, "El tipo de elemento de plantilla ya existe", FacesMessage.SEVERITY_ERROR));
        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniTipoElemPlantilla modificado) {
        this.setModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniTipoElemPlantilla eliminado) {
        this.setEliminar(eliminado);
    }

    //metodos para modificacion de datos
    public void mostrar(SieniTipoElemPlantilla ver) {
        this.setVer(ver);
        this.setIndexMenu(3);
    }

    public synchronized void guardarModifica() {
        try {
            if (validarModifica(this.getModifica())) {//valida el guardado

                sieniTipoElemPlantillaFacadeRemote.edit(this.getModifica());
                registrarEnBitacora("Modificar", "TipoElemPlantilla", this.getModifica().getIdTipoElemPlantilla());
                new ValidationPojo().printMsj("TipoElemPlantilla Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void resetModificaForm() {
        this.setModifica(new SieniTipoElemPlantilla());
    }

    public void resetNuevoForm() {
        this.setNuevo(new SieniTipoElemPlantilla());
    }

    public boolean validarModifica(SieniTipoElemPlantilla nuevo) {
        boolean ban;
        List<ValidationPojo> validaciones = new ArrayList<>();
        SieniTipoElemPlantilla archivoBD = sieniTipoElemPlantillaFacadeRemote.find(nuevo.getIdTipoElemPlantilla());
        if (!archivoBD.getTeDescripcion().equals(nuevo.getTeDescripcion())) {
            validaciones.add(new ValidationPojo(sieniTipoElemPlantillaFacadeRemote.findByNombre(nuevo.getTeDescripcion()) != null, "El tipo de elemento de plantilla ya existe", FacesMessage.SEVERITY_ERROR));
        }
        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public synchronized void eliminarTipoElemPlantilla() {
        try {
            registrarEnBitacora("Eliminar", "TipoElemPlantilla", this.getEliminar().getIdTipoElemPlantilla());
            this.getEliminar().setTeEstado('I');
            sieniTipoElemPlantillaFacadeRemote.edit(this.getEliminar());
            //elimina el archivo de la lista de datos para no volver a hacer el fill
            this.getList().remove(this.getEliminar());
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }
}
