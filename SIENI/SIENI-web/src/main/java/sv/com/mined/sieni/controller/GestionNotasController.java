/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sv.com.mined.sieni.SieniNotaFacadeRemote;
import sv.com.mined.sieni.form.GestionNotasForm;
import sv.com.mined.sieni.model.SieniNota;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "gestionNotaController")
public class GestionNotasController extends GestionNotasForm {

    @EJB
    private SieniNotaFacadeRemote sieniNotaFacadeRemote;

    @ManagedProperty(value = "#{bitacoraController}")
    private BitacoraController bitacoraController;

    @PostConstruct
    public void init() {
        this.setNotaNuevo(new SieniNota());
        this.setNotaModifica(new SieniNota());
        this.setNotaList(new ArrayList<SieniNota>());
        fill();
    }

    private void fill() {
        this.setNotaList(sieniNotaFacadeRemote.findAll());
    }

    public void guardar() {
        if (validarNuevo(this.getNotaNuevo())) {//valida el guardado
            sieniNotaFacadeRemote.create(this.getNotaNuevo());
            FacesMessage msg = new FacesMessage("Nota Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setIndexMenu(0);
        }
        this.setNotaNuevo(new SieniNota());
        fill();
    }

    public void quitarFormato(SieniNota actual) {

    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniNota nuevo) {
        boolean ban = true;

        return ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniNota modificado) {
        this.setNotaModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniNota eliminado) {
        this.setEliminar(eliminado);
    }

    public void guardarModifica() {
        if (validarModifica(this.getNotaModifica())) {//valida el guardado
            sieniNotaFacadeRemote.edit(this.getNotaModifica());
            FacesMessage msg = new FacesMessage("Nota Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            resetModificaForm();
            this.setIndexMenu(0);
        }
        fill();
    }

    public void resetModificaForm() {
        this.setNotaModifica(new SieniNota());
    }

    public boolean validarModifica(SieniNota nuevo) {
        boolean ban = true;

        return ban;
    }

    public void eliminarNota() {
        sieniNotaFacadeRemote.remove(this.getEliminar());
        fill();
    }

}
