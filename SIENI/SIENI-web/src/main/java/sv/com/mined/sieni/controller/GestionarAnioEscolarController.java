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
import sv.com.mined.sieni.SieniAnioEscolarFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.form.GestionarAnioEscolarForm;
import sv.com.mined.sieni.model.SieniAnioEscolar;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author Laptop
 */
@SessionScoped
@ManagedBean(name = "gestionarAnioEscolarController")
public class GestionarAnioEscolarController extends GestionarAnioEscolarForm {

    @EJB
    private SieniAnioEscolarFacadeRemote sieniAnioEscolarFacadeRemote;

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);
    }

    @PostConstruct
    public void init() {
        this.setAnioEscolarNuevo(new SieniAnioEscolar());
        this.setAnioEscolarModifica(new SieniAnioEscolar());
        this.setAnioEscolarList(new ArrayList<SieniAnioEscolar>());
        this.getAnioEscolarNuevo().setAeEstado('A');
        fill();
    }

    public void cancelaModifica(SieniAnioEscolar modifica) {
        modifica = sieniAnioEscolarFacadeRemote.find(modifica.getIdAnioEscolar());
        this.setIndexMenu(0);
    }

    private void fill() {
        this.setAnioEscolarList(sieniAnioEscolarFacadeRemote.findAllNoInactivos());
    }

    public synchronized void guardar() {
        try {
            if (validarNuevo(this.getAnioEscolarNuevo())) {//valida el guardado            
                this.setAnioEscolarNuevo(sieniAnioEscolarFacadeRemote.createAndReturn(this.getAnioEscolarNuevo()));
                registrarEnBitacora("Crear", "Anio Escolar", this.getAnioEscolarNuevo().getIdAnioEscolar());
                FacesMessage msg = new FacesMessage("Año escolar Creado Exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
//                this.setIndexMenu(0);
                this.getAnioEscolarList().add(this.getAnioEscolarNuevo());
                this.setAnioEscolarNuevo(new SieniAnioEscolar());
            }
//            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void quitarFormato(SieniAnioEscolar actual) {

    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniAnioEscolar nuevo) {
        boolean valido = true;
        DateUtils du = new DateUtils();
        FormatUtils fu = new FormatUtils();
        SieniAnioEscolar anioActual = sieniAnioEscolarFacadeRemote.findActivo();
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        validaciones.add(new ValidationPojo(!nuevo.getAeInicio().before(nuevo.getAeFin()), "Rango de fechas no valido", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(anioActual != null && nuevo.getAeEstado().equals('A'), "Ya existe un año activo", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(anioActual != null && anioActual.getAeAnio().equals(nuevo.getAeAnio()) && anioActual.getAeInicio().equals(nuevo.getAeInicio())
                && anioActual.getAeFin().equals(nuevo.getAeFin()), "Ya existe ese año para ese rango de fechas", FacesMessage.SEVERITY_ERROR));
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniAnioEscolar modificado) {
        this.setAnioEscolarModifica(modificado);
        this.setIndexMenu(2);
    }

    public void ver(SieniAnioEscolar modificado) {
        this.setAnioEscolarModifica(modificado);
        this.setIndexMenu(3);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniAnioEscolar eliminado) {
        this.setEliminar(eliminado);
    }

    public synchronized void guardarModifica() {
        try {
            if (validarModifica(this.getAnioEscolarModifica())) {//valida el guardado
                sieniAnioEscolarFacadeRemote.edit(this.getAnioEscolarModifica());
                registrarEnBitacora("Modificar", "Anio Escolar", this.getAnioEscolarModifica().getIdAnioEscolar());
                FacesMessage msg = new FacesMessage("Año escolar Modificado Exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
//                resetModificaForm();
//                this.setIndexMenu(0);
            }
//            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void resetModificaForm() {
        this.setAnioEscolarModifica(new SieniAnioEscolar());
    }

    public boolean validarModifica(SieniAnioEscolar nuevo) {
        boolean valido = true;
        DateUtils du = new DateUtils();
        FormatUtils fu = new FormatUtils();
        SieniAnioEscolar anioActual = sieniAnioEscolarFacadeRemote.findActivo();
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        validaciones.add(new ValidationPojo(!nuevo.getAeInicio().before(nuevo.getAeFin()), "Rango de fechas no valido", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(anioActual != null && nuevo.getAeEstado().equals('A'), "Ya existe un año activo", FacesMessage.SEVERITY_ERROR));
        if (anioActual!=null &&!anioActual.getIdAnioEscolar().equals(nuevo.getIdAnioEscolar())) {
            validaciones.add(new ValidationPojo(anioActual != null && anioActual.getAeAnio().equals(nuevo.getAeAnio()) && anioActual.getAeInicio().equals(nuevo.getAeInicio())
                    && anioActual.getAeFin().equals(nuevo.getAeFin()), "Ya existe ese año para ese rango de fechas", FacesMessage.SEVERITY_ERROR));
        }
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public synchronized void eliminaraAnioEscolar() {
        try {
            registrarEnBitacora("Eliminar", "Anio Escolar", this.getEliminar().getIdAnioEscolar());
            this.getEliminar().setAeEstado('I');
            sieniAnioEscolarFacadeRemote.edit(this.getEliminar());
            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }
}
