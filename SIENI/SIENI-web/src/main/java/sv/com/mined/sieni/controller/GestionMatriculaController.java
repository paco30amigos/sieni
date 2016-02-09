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
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniCursoAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniCursoFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMatriculaFacadeRemote;
import sv.com.mined.sieni.SieniSeccionFacadeRemote;
import sv.com.mined.sieni.form.GestionMatriculaForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniMatricula;
import sv.com.mined.sieni.model.SieniSeccion;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.FormatUtils;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "gestionMatriculaController")
public class GestionMatriculaController extends GestionMatriculaForm {

    @EJB
    private SieniMatriculaFacadeRemote sieniMatriculaFacadeRemote;
    @EJB
    private SieniGradoFacadeRemote sieniGradoFacadeRemote;
    @EJB
    private SieniSeccionFacadeRemote sieniSeccionFacadeRemote;
    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;
    @EJB
    private SieniCursoAlumnoFacadeRemote sieniCursoAlumnoFacadeRemote;
    @EJB
    private SieniCursoFacadeRemote sieniCursoFacadeRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);
    }

    @PostConstruct
    public void init() {
        this.setMatriculaNuevo(new SieniMatricula());
        this.setMatriculaModifica(new SieniMatricula());
        this.setMatriculaList(new ArrayList<SieniMatricula>());
        fill();
    }

    private List<SieniMatricula> setAlumnos(List<SieniMatricula> matriculas) {
        List<SieniMatricula> ret = new ArrayList<>();
        for (SieniMatricula actual : matriculas) {
            ret.add(setInfoAlumno(actual));
        }
        return ret;
    }

    public SieniMatricula setInfoAlumno(SieniMatricula matActual) {
        matActual.setAlumno(sieniAlumnoFacadeRemote.findAlumnoById(matActual.getIdAlumno()));
        return matActual;
    }
    
    public void cancelaModifica(SieniMatricula modifica) {
        modifica = sieniMatriculaFacadeRemote.find(modifica.getIdMatricula());
        this.setIndexMenu(0);
    }

    private void fill() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        this.setMatriculaList(setAlumnos(sieniMatriculaFacadeRemote.getMatriculasAnio(loginBean.getAnioEscolarActivo().getAeAnio())));
    }

    public void initNuevo() {
        this.setAlumnosList(sieniAlumnoFacadeRemote.findAlumnosNoMatriculados());
        //nuevo
        this.setGradosList(sieniGradoFacadeRemote.findAllNoInactivos());
        this.setSeccionesList(new ArrayList<SieniSeccion>());
        if (this.getGradosList() != null && !this.getGradosList().isEmpty()) {
            if (this.getGradosList().get(0).getSieniSeccionList() != null
                    && !this.getGradosList().get(0).getSieniSeccionList().isEmpty()) {
                this.setSeccionesList(this.getGradosList().get(0).getSieniSeccionList());
            }
        }
        this.setMatriculaNuevo(new SieniMatricula());
        this.setIndexMenu(1);
    }

    public synchronized void guardar() {
        try {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            for (SieniAlumno actual : this.getAlumnosList()) {
                if (actual.getIdAlumno().equals(this.getIdAlumno())) {
                    this.getMatriculaNuevo().setIdAlumno(actual.getIdAlumno());
                    break;
                }
            }
            for (SieniGrado actual : this.getGradosList()) {
                if (actual.getIdGrado().equals(this.getIdGrado())) {
                    this.getMatriculaNuevo().setIdGrado(actual);
                    break;
                }
            }
            for (SieniSeccion actual : this.getSeccionesList()) {
                if (actual.getIdSeccion().equals(this.getIdSeccion())) {
                    this.getMatriculaNuevo().setIdSeccion(actual);
                    break;
                }
            }

            this.getMatriculaNuevo().setMtAnio(loginBean.getAnioEscolarActivo().getAeAnio().toString());
            if (validarNuevo(this.getMatriculaNuevo())) {//valida el guardado
                SieniMatricula mat = sieniMatriculaFacadeRemote.findByIdAlumnoAnio(this.getMatriculaNuevo().getIdAlumno(), this.getMatriculaNuevo().getMtAnio());
                this.getMatriculaNuevo().setMtEstado('A');
                this.getMatriculaNuevo().setMtFechaIngreso(new Date());
                if (mat != null) {
                    this.getMatriculaNuevo().setIdMatricula(mat.getIdMatricula());
                    sieniMatriculaFacadeRemote.edit(this.getMatriculaNuevo());
                } else {
                    this.setMatriculaNuevo(sieniMatriculaFacadeRemote.createAndReturn(this.getMatriculaNuevo()));
                }
                registrarEnBitacora("Crear", "Matricula", this.getMatriculaNuevo().getIdMatricula());
                new ValidationPojo().printMsj("Matricula Creado Exitosamente", FacesMessage.SEVERITY_INFO);
                this.getMatriculaList().add(setInfoAlumno(this.getMatriculaNuevo()));
                this.setMatriculaNuevo(new SieniMatricula());
                //this.setIndexMenu(0);
            }
            //this.setMatriculaNuevo(new SieniMatricula());
//            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void quitarFormato(SieniMatricula actual) {

    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniMatricula nuevo) {
        boolean valido = true;
        List<ValidationPojo> validaciones = new ArrayList<>();
        SieniMatricula mat = sieniMatriculaFacadeRemote.findByIdAlumnoAnio(nuevo.getIdAlumno(), nuevo.getMtAnio());
        validaciones.add(new ValidationPojo((mat != null && mat.getMtEstado().equals(new Character('A'))), "El Alumno ya ha sido matriculado para ese año", FacesMessage.SEVERITY_ERROR));
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniMatricula modificado) {
        this.setAlumnosModificaList(sieniAlumnoFacadeRemote.findAlumnosNoMatriculados());
        //modifica
        this.setGradosModificaList(sieniGradoFacadeRemote.findAllNoInactivos());
        this.setSeccionesModificaList(new ArrayList<SieniSeccion>());
        if (this.getGradosModificaList() != null && !this.getGradosModificaList().isEmpty()) {
            if (this.getGradosModificaList().get(0).getSieniSeccionList() != null
                    && !this.getGradosModificaList().get(0).getSieniSeccionList().isEmpty()) {
                this.setSeccionesModificaList(this.getGradosModificaList().get(0).getSieniSeccionList());
            }
        }
        this.setMatriculaModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniMatricula eliminado) {
        this.setEliminar(eliminado);
    }

    public synchronized void guardarModifica() {
        try {
            for (SieniAlumno actual : this.getAlumnosModificaList()) {
                if (actual.getIdAlumno().equals(this.getMatriculaModifica().getIdAlumno())) {
                    this.getMatriculaModifica().setIdAlumno(actual.getIdAlumno());
                    break;
                }
            }
            for (SieniGrado actual : this.getGradosModificaList()) {
                if (actual.getIdGrado().equals(this.getMatriculaModifica().getIdGrado().getIdGrado())) {
                    this.getMatriculaModifica().setIdGrado(actual);
                    break;
                }
            }
            for (SieniSeccion actual : this.getSeccionesModificaList()) {
                if (actual.getIdSeccion().equals(this.getMatriculaModifica().getIdSeccion().getIdSeccion())) {
                    this.getMatriculaModifica().setIdSeccion(actual);
                    break;
                }
            }
            if (validarModifica(this.getMatriculaModifica())) {//valida el guardado
                sieniMatriculaFacadeRemote.edit(this.getMatriculaModifica());
                registrarEnBitacora("Modificar", "Matricula", this.getMatriculaModifica().getIdMatricula());
                new ValidationPojo().printMsj("Matricula Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
//                resetModificaForm();
//                this.setIndexMenu(0);
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void resetModificaForm() {
        this.setMatriculaModifica(new SieniMatricula());
    }

    public boolean validarModifica(SieniMatricula nuevo) {
        boolean valido = true;
//        List<ValidationPojo> validaciones = new ArrayList<>();
//        SieniMatricula mat = sieniMatriculaFacadeRemote.findByIdAlumnoAnio(nuevo.getIdAlumno(), nuevo.getMtAnio());
//        validaciones.add(new ValidationPojo(!(mat != null), "El Alumno ya ha sido matriculado para ese año", FacesMessage.SEVERITY_ERROR));
//        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public synchronized void eliminarMatricula() {
        try {
            registrarEnBitacora("Eliminar", "Matricula", this.getEliminar().getIdMatricula());
            this.getEliminar().setMtEstado('I');//I:eliminado,D:disponible,N:no disponible, (I eliminado logico)
            sieniMatriculaFacadeRemote.edit(this.getEliminar());
            this.getMatriculaList().remove(this.getEliminar());
//            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void getSeccionesGrado(ValueChangeEvent a) {
        Long idGrado = (Long) a.getNewValue();
        SieniGrado cod = new SieniGrado();
        for (SieniGrado actual : this.getGradosList()) {
            if (actual.getIdGrado().equals(idGrado)) {
                cod = actual;
                break;
            }
        }
        this.setSeccionesList(cod.getSieniSeccionList());
    }

    public void getSeccionesGradoModifica(ValueChangeEvent a) {
        Long idGrado = (Long) a.getNewValue();
        SieniGrado cod = new SieniGrado();
        for (SieniGrado actual : this.getGradosModificaList()) {
            if (actual.getIdGrado().equals(idGrado)) {
                cod = actual;
                break;
            }
        }
        this.setSeccionesModificaList(cod.getSieniSeccionList());
    }

    public void ver(SieniMatricula modificado) {
        this.setMatriculaModifica(modificado);
        this.setIndexMenu(3);
    }

    public void getCarnetAlumno(ValueChangeEvent a) {
        Long cod = new Long(a.getNewValue().toString());
        this.setAlumno(sieniAlumnoFacadeRemote.findAlumnoById(cod));
//        if (this.getMateriasList() != null && !this.getMateriasList().isEmpty()) {
//            this.setIdMateria(this.getMateriasList().get(0));
//            this.setEvaluacionesList(this.getIdMateria().getSieniEvaluacionList());
//        } else {
//            this.setEvaluacionesList(new ArrayList<SieniEvaluacion>());
//        }
    }
}
