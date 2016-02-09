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
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.form.CatGradoForm;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "catGradoController")
public class CatGradoController extends CatGradoForm {

    @EJB
    private SieniGradoFacadeRemote sieniGradoRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);

    }

    @PostConstruct
    public void init() {
        this.setNuevo(new SieniGrado());
        this.setModifica(new SieniGrado());
        this.setList(new ArrayList<SieniGrado>());
        fill();
    }

    private void fill() {
        this.setList(sieniGradoRemote.findAllNoInactivos());
    }

    public void cancelaModifica(SieniGrado modifica) {
        modifica = sieniGradoRemote.find(modifica.getIdGrado());
        this.setIndexMenu(0);
    }
    public synchronized void guardar() {
        try {
            if (validarNuevo(this.getNuevo())) {//valida el guardado
                this.getNuevo().setGrEstado('A');
                
                this.setNuevo(sieniGradoRemote.createAndReturn(this.getNuevo()));
//                sieniGradoRemote.create(this.getNuevo());
                registrarEnBitacora("Guardar", "Grado", this.getNuevo().getIdGrado());
                new ValidationPojo().printMsj("Grado Creado Exitosamente", FacesMessage.SEVERITY_INFO);
                //agrega el nuevo archivo a la lista de la tabla actual para no hacer el fill
                this.getList().add(this.getNuevo());
                //limpia los datos para un registro nuevo
                this.setNuevo(new SieniGrado());
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniGrado nuevo) {
        boolean ban = true;
        List<ValidationPojo> validaciones = new ArrayList<>();
        validaciones.add(new ValidationPojo(sieniGradoRemote.findBy(nuevo) != null, "El grado ya existe", FacesMessage.SEVERITY_ERROR));
        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniGrado modificado) {
        this.setModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniGrado eliminado) {
        this.setEliminar(eliminado);
    }

    //metodos para modificacion de datos
    public void mostrar(SieniGrado ver) {
        this.setVer(ver);
        this.setIndexMenu(3);
    }

    public synchronized void guardarModifica() {
        try {
            if (validarModifica(this.getModifica())) {//valida el guardado

                sieniGradoRemote.edit(this.getModifica());
                registrarEnBitacora("Modificar", "Grado", this.getModifica().getIdGrado());
                new ValidationPojo().printMsj("Grado Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void resetModificaForm() {
        this.setModifica(new SieniGrado());
    }

    public void resetNuevoForm() {
        this.setNuevo(new SieniGrado());
    }

    public boolean validarModifica(SieniGrado nuevo) {
        boolean ban;
        List<ValidationPojo> validaciones = new ArrayList<>();
        SieniGrado archivoBD = sieniGradoRemote.find(nuevo.getIdGrado());
        if (!archivoBD.getGrNombre().equals(nuevo.getGrNombre())) {
            validaciones.add(new ValidationPojo(sieniGradoRemote.findBy(nuevo) != null, "El grado ya existe", FacesMessage.SEVERITY_ERROR));
        }
        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public synchronized void eliminarGrado() {
        try {
            registrarEnBitacora("Eliminar", "Grado", this.getEliminar().getIdGrado());
            this.getEliminar().setGrEstado('I');
            sieniGradoRemote.edit(this.getEliminar());
            //elimina el archivo de la lista de datos para no volver a hacer el fill
            this.getList().remove(this.getEliminar());
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }
}
