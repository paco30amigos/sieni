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
import sv.com.mined.sieni.SieniClaseFacadeRemote;
import sv.com.mined.sieni.form.GestionClasesOnlineForm;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "gestionClasesOnlineController")
public class GestionClasesOnlineController extends GestionClasesOnlineForm {

    @EJB
    private SieniClaseFacadeRemote sieniClaseFacadeRemote;

    @PostConstruct
    public void init() {
        this.setListaUsuarios(new ArrayList<String>());
        this.setClaseActual(new SieniClase());
        fill();
    }

    public void fill() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        List<SieniClase> listaClases = new ArrayList<>();
        //fill para alumnos
        if (loginBean.getTipoRol().equals("0")) {
//*******fill
            //clases interact
            listaClases = sieniClaseFacadeRemote.findClaseByTipoAlumno('O', loginBean.getAlumno().getIdAlumno());//clases online
        } else {
            //*******fill
            //clases interact
            listaClases = sieniClaseFacadeRemote.findClaseByTipo('O');//clases online
        }
        updateEstadoClase(listaClases);
        this.setClasesOnlineList(listaClases);
    }

    private void updateEstadoClase(List<SieniClase> clases) {
        List<SieniClase> clasesIniciadas = new ArrayList<>();
        DateUtils du = new DateUtils();

        for (SieniClase actual : clases) {
            if (actual.getClEstado().equals(new Character('N'))
                    && du.horarioValido(actual.getClHorario(), actual.getClHora())) {
                actual.setClEstado('A');
                clasesIniciadas.add(actual);
            }
        }
        if (!clasesIniciadas.isEmpty()) {
            sieniClaseFacadeRemote.merge(clasesIniciadas);
        }
    }

    public void transmitirClase(SieniClase claseActual) {
        this.setClaseActual(claseActual);
        this.setIndexMenu(2);
    }

    public void recibirClase(SieniClase claseActual) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        //fill para alumnos
        if (loginBean.getTipoRol().equals("0")) {
            DateUtils du = new DateUtils();
            if (du.horarioValido(claseActual.getClHorario(), claseActual.getClHora())) {
                if (!validarEstadoClase(claseActual)) {
                    this.setClaseActual(claseActual);
                    this.setIndexMenu(1);
                }
            } else {
                new ValidationPojo().printMsj("La clase aun no esta disponible", FacesMessage.SEVERITY_ERROR);
            }
        } else {
            this.setClaseActual(claseActual);
            this.setIndexMenu(1);
        }
    }

    public boolean validarEstadoClase(SieniClase claseActual) {
        boolean ret = true;
        if (claseActual.getClEstado() != null && !claseActual.getClEstado().equals(new Character('N'))) {
            ret = false;
            new ValidationPojo().printMsj("La clase aun no esta disponible", FacesMessage.SEVERITY_ERROR);
        } else if (claseActual.getClEstado() != null && !claseActual.getClEstado().equals(new Character('T'))) {
            ret = false;
            new ValidationPojo().printMsj("La clase ya ha terminado", FacesMessage.SEVERITY_ERROR);
        }
        return ret;
    }

    public void actualizarUsuarios() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String usuarios = req.getParameterMap().get("form:usuarios")[0];//asumiendo que existe
        this.setListaUsuarios(new ArrayList<String>());
        if (usuarios != null && !usuarios.isEmpty()) {
            String usuariosConectados[] = usuarios.split(new FormatUtils().getSeparadorUsuarios());
            for (String actual : usuariosConectados) {
                if (actual != null) {
                    this.getListaUsuarios().add(actual);
                }
            }
        } else {
            this.getListaUsuarios().add("No hay Usuarios Conectados");
        }
    }

    public void iniciarClase() {
        DateUtils du = new DateUtils();
//        if (du.horarioValido(this.getClaseActual().getClHorario(), this.getClaseActual().getClHora())) {
//            this.getClaseActual().setClEstado(new Character('A'));
//            sieniClaseFacadeRemote.edit(this.getClaseActual());
//        } else {
//            FacesMessage msg = new FacesMessage("La clase no puede iniciar, revise el horario de la clase");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
    }

    public void finalizarClase() {
        this.getClaseActual().setClEstado(new Character('T'));
        sieniClaseFacadeRemote.edit(this.getClaseActual());
    }
}
