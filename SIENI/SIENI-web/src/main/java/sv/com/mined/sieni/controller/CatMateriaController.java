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
import sv.com.mined.sieni.SieniCatMateriaFacadeRemote;
import sv.com.mined.sieni.form.CatMateriaForm;
import sv.com.mined.sieni.model.SieniCatMateria;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "catMateriaController")
public class CatMateriaController extends CatMateriaForm {

    @EJB
    private SieniCatMateriaFacadeRemote sieniCatMateriaFacadeRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);

    }

    @PostConstruct
    public void init() {
        this.setNuevo(new SieniCatMateria());
        this.setModifica(new SieniCatMateria());
        this.setList(new ArrayList<SieniCatMateria>());
        fill();
    }

    private void fill() {
        this.setList(sieniCatMateriaFacadeRemote.findAllNoInactivos());
    }
    
    public void resetFiltros(){
        this.setFiltroNombre(null);
        this.setFiltroEstado(null);
        this.setListDatosFiltered(null);
    }
    
    public void cancelaModifica(SieniCatMateria modifica) {
        modifica = sieniCatMateriaFacadeRemote.find(modifica.getIdCatMateria());
        this.setIndexMenu(0);
    }

    public synchronized void guardar() {
        try {
            if (validarNuevo(this.getNuevo())) {//valida el guardado
                this.setNuevo(sieniCatMateriaFacadeRemote.createAndReturn(this.getNuevo()));
                registrarEnBitacora("Guardar", "CatMateria", this.getNuevo().getIdCatMateria());
                new ValidationPojo().printMsj("Materia Creada Exitosamente", FacesMessage.SEVERITY_INFO);
                //agrega el nuevo archivo a la lista de la tabla actual para no hacer el fill
                this.getList().add(this.getNuevo());
                //limpia los datos para un registro nuevo
                this.setNuevo(new SieniCatMateria());
                
                resetFiltros();
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniCatMateria nuevo) {
        boolean ban = true;
        List<ValidationPojo> validaciones = new ArrayList<>();
        validaciones.add(new ValidationPojo(sieniCatMateriaFacadeRemote.findByNombre(nuevo.getCatNombre()) != null, "La materia ya existe", FacesMessage.SEVERITY_ERROR));
        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniCatMateria modificado) {
        this.setModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniCatMateria eliminado) {
        this.setEliminar(eliminado);
    }

    //metodos para modificacion de datos
    public void mostrar(SieniCatMateria ver) {
        this.setVer(ver);
        this.setIndexMenu(3);
    }

    public synchronized void guardarModifica() {
        try {
            if (validarModifica(this.getModifica())) {//valida el guardado
                sieniCatMateriaFacadeRemote.edit(this.getModifica());
                registrarEnBitacora("Modificar", "CatMateria", this.getModifica().getIdCatMateria());
                new ValidationPojo().printMsj("Materia Modificada Exitosamente", FacesMessage.SEVERITY_INFO);
                
                resetFiltros();
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void resetModificaForm() {
        this.setModifica(new SieniCatMateria());
    }

    public void resetNuevoForm() {
        this.setNuevo(new SieniCatMateria());
    }

    public boolean validarModifica(SieniCatMateria nuevo) {
        boolean ban;
        List<ValidationPojo> validaciones = new ArrayList<>();
        SieniCatMateria archivoBD = sieniCatMateriaFacadeRemote.find(nuevo.getIdCatMateria());
        if (!archivoBD.getCatNombre().equals(nuevo.getCatNombre())) {
            validaciones.add(new ValidationPojo(sieniCatMateriaFacadeRemote.findByNombre(nuevo.getCatNombre()) != null, "La materia ya existe", FacesMessage.SEVERITY_ERROR));
        }
        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public synchronized void eliminarCatMateria() {
        try {
            registrarEnBitacora("Eliminar", "CatMateria", this.getEliminar().getIdCatMateria());
            this.getEliminar().setCatEstado('I');
            sieniCatMateriaFacadeRemote.edit(this.getEliminar());
            //elimina el archivo de la lista de datos para no volver a hacer el fill
            this.getList().remove(this.getEliminar());
            
            resetFiltros();
            new ValidationPojo().printMsj("Registro eliminado exitosamente", FacesMessage.SEVERITY_INFO);
            this.setMsjEliminado(true);
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }
    public void printMensajeEliminado() {
        if (this.isMsjEliminado()) {
            this.setMsjEliminado(false);
            new ValidationPojo().printMsj("Registro eliminado exitosamente", FacesMessage.SEVERITY_INFO);
        }
    }
}
