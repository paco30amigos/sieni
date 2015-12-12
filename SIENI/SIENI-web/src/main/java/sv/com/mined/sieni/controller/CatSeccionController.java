/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import sv.com.mined.sieni.SieniAnioEscolarFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniSeccionFacadeRemote;
import sv.com.mined.sieni.form.CatSeccionForm;
import sv.com.mined.sieni.model.SieniAnioEscolar;
import sv.com.mined.sieni.model.SieniSeccion;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;

/**
 *
 * @author INFORMATICA
 */
@SessionScoped
@ManagedBean(name = "catSeccionController")
public class CatSeccionController extends CatSeccionForm {
    
    @EJB
    private SieniSeccionFacadeRemote sieniSeccionRemote;
    @EJB
    private SieniAnioEscolarFacadeRemote sieniAnioEscolarRemote;
    @EJB
    private SieniGradoFacadeRemote sieniGradoRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);

    }

    @PostConstruct
    public void init() {
        this.setAnio(1);
        this.setAnioEscolar(new SieniAnioEscolar());
        this.setNuevo(new SieniSeccion());
        this.setModifica(new SieniSeccion());
        this.setList(new ArrayList<SieniSeccion>());
        this.setListGrados(sieniGradoRemote.findAllNoInactivos());
        fill();
    }

    private void fill() {
        this.setAnio(null);
        this.setList(new ArrayList<SieniSeccion>());
        
        this.setList(sieniSeccionRemote.findByAnioEscolar(this.getAnio()));
    }

    public synchronized void guardar() {
        try {
            if (validarNuevo(this.getNuevo())) {//valida el guardado
                this.getNuevo().setScCoordinador(BigInteger.valueOf(3));
                this.setNuevo(sieniSeccionRemote.createAndReturn(this.getNuevo()));
//                sieniGradoRemote.create(this.getNuevo());
                registrarEnBitacora("Guardar", "Grado", this.getNuevo().getIdSeccion());
                new ValidationPojo().printMsj("Seccion Creada Exitosamente", FacesMessage.SEVERITY_INFO);
                //agrega el nuevo archivo a la lista de la tabla actual para no hacer el fill
                this.getList().add(this.getNuevo());
                //limpia los datos para un registro nuevo
                this.setNuevo(new SieniSeccion());
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniSeccion nuevo) {
        boolean ban = true;
        List<ValidationPojo> validaciones = new ArrayList<>();
        validaciones.add(new ValidationPojo(sieniSeccionRemote.findBy(nuevo) != null, "La seccion ya existe", FacesMessage.SEVERITY_ERROR));
        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniSeccion modificado) {
        this.setModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniSeccion eliminado) {
        this.setEliminar(eliminado);
    }

    //metodos para modificacion de datos
    public void mostrar(SieniSeccion ver) {
        this.setVer(ver);
        this.setIndexMenu(3);
    }

    public synchronized void guardarModifica() {
        try {
            if (validarModifica(this.getModifica())) {//valida el guardado

                sieniSeccionRemote.edit(this.getModifica());
                registrarEnBitacora("Modificar", "Grado", this.getModifica().getIdSeccion());
                new ValidationPojo().printMsj("Seccion Modificada Exitosamente", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void resetModificaForm() {
        this.setModifica(new SieniSeccion());
    }

    public void resetNuevoForm() {
        this.setNuevo(new SieniSeccion());
    }

    public boolean validarModifica(SieniSeccion nuevo) {
        boolean ban;
        List<ValidationPojo> validaciones = new ArrayList<>();
        SieniSeccion archivoBD = sieniSeccionRemote.findByIdSeccion(nuevo.getIdSeccion());
        if (!archivoBD.getScDescripcion().equals(nuevo.getScDescripcion())) {
            validaciones.add(new ValidationPojo(sieniSeccionRemote.findBy(nuevo) != null, "La seccion ya existe", FacesMessage.SEVERITY_ERROR));
        }
        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public synchronized void eliminarSeccion() {
        try {
            registrarEnBitacora("Eliminar", "Seccion", this.getEliminar().getIdSeccion());
            //this.getEliminar().setGrEstado('I');
            sieniSeccionRemote.remove(this.getEliminar());
            //elimina el archivo de la lista de datos para no volver a hacer el fill
            this.getList().remove(this.getEliminar());
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }
}
