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
import sv.com.mined.sieni.SieniAlumnRolFacadeRemote;
import sv.com.mined.sieni.SieniDocentRolFacadeRemote;
import sv.com.mined.sieni.form.MntoUsuariosForm;
import sv.com.mined.sieni.model.SieniAlumnRol;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniDocentRol;
import sv.com.mined.sieni.pojos.UsuariosPojo;

/**
 *
 * @author Laptop
 */
@SessionScoped
@ManagedBean(name = "mntoUsuariosController")
public class MntoUsuariosController extends MntoUsuariosForm {

    @EJB
    private SieniDocentRolFacadeRemote sieniDocenteRolFacadeRemote;
    @EJB
    private SieniAlumnRolFacadeRemote sieniAlumnRolFacadeRemote;

    @PostConstruct
    public void init() {
        this.setUsuarioNuevo(new UsuariosPojo());
        this.setUsuarioModifica(new UsuariosPojo());
        this.setUsuariosList(new ArrayList<UsuariosPojo>());
        fill();
    }

    private void fill() {
        this.setUsuariosList(new ArrayList<UsuariosPojo>());
        List<SieniAlumnRol> alumnos = sieniAlumnRolFacadeRemote.findAll();
        List<SieniDocentRol> docentes = sieniDocenteRolFacadeRemote.findAll();
        UsuariosPojo aux = new UsuariosPojo();
        for (SieniAlumnRol actual : alumnos) {
            aux = new UsuariosPojo();
            aux.setNombre(actual.getIdAlumno().getNombreCompleto());
            aux.setUsuario(actual.getIdAlumno().getAlUsuario());
            aux.setTipoUsuario(getTipoUsuario(0));
            aux.setTipoPermiso(getTipoUsuario(0));
            aux.setCodTipoUsuario("0");
            aux.setCodTipoPermiso(actual.getSieniAlumnRolPK().getFRol());
            aux.setIdUsuario(actual.getIdAlumno().getIdAlumno());
            this.getUsuariosList().add(aux);
        }
        for (SieniDocentRol actual : docentes) {
            aux = new UsuariosPojo();
            aux.setNombre(actual.getIdDocente().getNombreCompleto());
            aux.setUsuario(actual.getIdDocente().getDcUsuario());
            aux.setTipoUsuario(getTipoUsuario(1));
            aux.setCodTipoUsuario("1");
            aux.setTipoPermiso(getTipoUsuario(1));
            aux.setCodTipoPermiso(actual.getSieniDocentRolPK().getFRolDoc());
            aux.setIdUsuario(actual.getIdDocente().getIdDocente());
            this.getUsuariosList().add(aux);
        }
    }

    public String getTipoUsuario(int cod) {
        String ret = "";
        switch (cod) {
            case 0:
                ret = "Alumno";
                break;
            case 1:
                ret = "Docente";
                break;
            case 2:
                ret = "Administrador";
                break;
        }
        return ret;
    }

    public void guardar() {
        if (validarNuevo(this.getUsuarioNuevo())) {//valida el guardado
//            sieniAlumnoFacadeRemote.create(this.getAlumnoNuevo());
            FacesMessage msg = new FacesMessage("Expediente Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setIndexMenu(0);
        }
        this.setUsuarioNuevo(new UsuariosPojo());
        fill();
    }

    public void quitarFormato(SieniAlumno actual) {
        actual.setAlTelefonoEm1(actual.getAlTelefonoEm1().replaceAll("-", ""));
        actual.setAlTelefonoEm2(actual.getAlTelefonoEm2().replaceAll("-", ""));
    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(UsuariosPojo nuevo) {
        boolean ban = true;

        return ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(UsuariosPojo modificado) {
        this.setUsuarioModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(UsuariosPojo eliminado) {
        this.setEliminar(eliminado);
    }

    public void guardarModifica() {
        if (validarModifica(this.getUsuarioModifica())) {//valida el guardado
//            sieniAlumnoFacadeRemote.edit(this.getAlumnoModifica());
            FacesMessage msg = new FacesMessage("Expediente Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            resetModificaForm();
            this.setIndexMenu(0);
        }
        fill();
    }

    public void resetModificaForm() {
        this.setUsuarioModifica(new UsuariosPojo());
    }

    public boolean validarModifica(UsuariosPojo nuevo) {
        boolean ban = true;

        return ban;
    }

    public void eliminarExpediente() {
//        sieniAlumnoFacadeRemote.remove(this.getEliminar());
        fill();
    }
}
