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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniCursoAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniCursoFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniSeccionFacadeRemote;
import sv.com.mined.sieni.form.GestionCursoForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.model.SieniCursoAlumno;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniSeccion;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "gestionCursoController")
public class GestionCursoController extends GestionCursoForm {

    @EJB
    private SieniCursoFacadeRemote sieniCursoFacadeRemote;

    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    @EJB
    private SieniGradoFacadeRemote sieniGradoFacadeRemote;   
    @EJB
    private SieniMateriaFacadeRemote sieniMateriaFacadeRemote;
    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    @EJB
    private SieniCursoAlumnoFacadeRemote sieniCursoAlumnoFacadeRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);

    }

    @PostConstruct
    public void init() {
        this.setCursoNuevo(new SieniCurso());
        this.setCursoModifica(new SieniCurso());
        this.setCursoList(new ArrayList<SieniCurso>());
        fill();
    }

    private void fill() {
        this.setCursoList(sieniCursoFacadeRemote.findByEstado('A'));
        //nuevo        
    }

    public void nuevo() {
        this.setDocentesList(sieniDocenteFacadeRemote.findDocentesActivos());
        this.setGradoList(sieniGradoFacadeRemote.findAll());
//        this.setMateriaList(sieniMateriaFacadeRemote.findMateriasActivas());
//        this.setSeccionList(new ArrayList<SieniSeccion>());
        if (this.getGradoList() != null && !this.getGradoList().isEmpty()) {
            if (this.getGradoList().get(0).getSieniSeccionList() != null
                    && !this.getGradoList().get(0).getSieniSeccionList().isEmpty()) {
                this.setSeccionList(this.getGradoList().get(0).getSieniSeccionList());
            }
            
            if (this.getGradoList().get(0).getSieniMateriaList() != null
                    && !this.getGradoList().get(0).getSieniMateriaList().isEmpty()) {
                this.setMateriaList(this.getGradoList().get(0).getSieniMateriaList());
//                this.setSeccionList(this.getGradoList().get(0).getSieniSeccionList());
            }
        }
        this.setIndexMenu(1);
    }

    public synchronized void guardar() {
        try {
            for (SieniDocente actual : this.getDocentesList()) {
                if (actual.getIdDocente().equals(this.getIdDocente())) {
                    this.getCursoNuevo().setIdDocente(actual);
                    break;
                }
            }
            for (SieniGrado actual : this.getGradoList()) {
                if (actual.getIdGrado().equals(this.getIdGrado())) {
                    this.getCursoNuevo().setIdGrado(actual);
                    break;
                }
            }
            for (SieniSeccion actual : this.getSeccionList()) {
                if (actual.getIdSeccion().equals(this.getIdSeccion())) {
                    this.getCursoNuevo().setIdSeccion(actual);
                    break;
                }
            }
            for (SieniMateria actual : this.getMateriaList()) {
                if (actual.getIdMateria().equals(this.getIdMateria())) {
                    this.getCursoNuevo().setIdMateria(actual);
                    break;
                }
            }

            this.getCursoNuevo().setCrEstado('A');
            if (validarNuevo(this.getCursoNuevo())) {//valida el guardado
                SieniCurso existCurso = new SieniCurso();
                existCurso = sieniCursoFacadeRemote.finByDocGrSecMat(this.getCursoNuevo().getIdDocente().getIdDocente(), this.getCursoNuevo().getIdGrado().getIdGrado(), this.getCursoNuevo().getIdSeccion().getIdSeccion(), this.getCursoNuevo().getIdMateria().getIdMateria(), this.getCursoNuevo().getCrNombre());
                if (existCurso != null) {
                    new ValidationPojo().printMsj("No se puede crear el curso, ya existe para esa materia", FacesMessage.SEVERITY_ERROR);

                } else {
                    this.setCursoNuevo(sieniCursoFacadeRemote.createAndReturn(this.getCursoNuevo()));
                    registrarEnBitacora("Crear", "Curso", this.getCursoNuevo().getIdCurso());
                    new ValidationPojo().printMsj("Curso Creado Exitosamente", FacesMessage.SEVERITY_INFO);
                    //agrega el curso a la tabla de busqueda
                    this.getCursoList().add(this.getCursoNuevo());
                    //limpia solo si es guardado exitosamente
                    this.setCursoNuevo(new SieniCurso());
                }
            }
//        fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void quitarFormato(SieniCurso actual) {

    }

    public void gestionarCursoAlumno(SieniCurso curso) {
        this.setAlumnoList(new ArrayList<SieniAlumno>());
        this.setAlumnoList(sieniAlumnoFacadeRemote.findAlumnosNoCursos(curso.getIdGrado().getIdGrado(),curso.getIdCurso()));
        this.setCursoModifica(curso);
        this.setIndexMenu(4);
    }
    
    public void gestionarAlumnoInscrito(SieniCurso curso) {
        this.setAlumnoInscritoList(new ArrayList<SieniAlumno>());
        this.setAlumnoInscritoList(sieniAlumnoFacadeRemote.findAlumnosInscritos(curso.getIdCurso()));
        this.setCursoModifica(curso);
        this.setIndexMenu(5);
    }
    
    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniCurso nuevo) {
        boolean ban = true;

        return ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniCurso modificado) {
        //modifica
        this.setDocentesModificaList(sieniDocenteFacadeRemote.findDocentesActivos());
        this.setGradoModificaList(sieniGradoFacadeRemote.findAll());
        this.setMateriaModificaList(sieniMateriaFacadeRemote.findMateriasActivas());
        this.setSeccionModificaList(new ArrayList<SieniSeccion>());
        if (this.getGradoModificaList() != null && !this.getGradoModificaList().isEmpty()) {
            if (this.getGradoModificaList().get(0).getSieniSeccionList() != null
                    && !this.getGradoModificaList().get(0).getSieniSeccionList().isEmpty()) {
                this.setSeccionModificaList(this.getGradoModificaList().get(0).getSieniSeccionList());
            }
        }
        this.setCursoModifica(modificado);
        this.setIndexMenu(2);
    }

    public void ver(SieniCurso modificado) {
        this.setCursoModifica(modificado);
        this.setIndexMenu(3);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniCurso eliminado) {
        this.setEliminar(eliminado);
    }
    
     //metodos para modificacion de datos
    public void eliminarInscrito(SieniCurso curso,SieniAlumno alumno) {
        for (SieniCursoAlumno cursos : alumno.getSieniCursoAlumnoList()) {
            if(cursos.getIdCurso().equals(curso)){
            this.setEliminarInscrito(cursos);
            break;
            }
                
        }
        
    }

    public synchronized void guardarModifica() {
        try {
            for (SieniDocente actual : this.getDocentesModificaList()) {
                if (actual.getIdDocente().equals(this.getIdDocenteModifica())) {
                    this.getCursoModifica().setIdDocente(actual);
                    break;
                }
            }
            for (SieniGrado actual : this.getGradoModificaList()) {
                if (actual.getIdGrado().equals(this.getIdGradoModifica())) {
                    this.getCursoModifica().setIdGrado(actual);
                    break;
                }
            }
            for (SieniSeccion actual : this.getSeccionModificaList()) {
                if (actual.getIdSeccion().equals(this.getIdSeccionModifica())) {
                    this.getCursoModifica().setIdSeccion(actual);
                    break;
                }
            }
            for (SieniMateria actual : this.getMateriaModificaList()) {
                if (actual.getIdMateria().equals(this.getIdMateriaModifica())) {
                    this.getCursoModifica().setIdMateria(actual);
                    break;
                }
            }
            if (validarModifica(this.getCursoModifica())) {//valida el guardado
                sieniCursoFacadeRemote.edit(this.getCursoModifica());
                registrarEnBitacora("Modificar", "Curso", this.getCursoModifica().getIdCurso());
                new ValidationPojo().printMsj("Curso Modificado Exitosamente" , FacesMessage.SEVERITY_INFO);
            }
//        fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void resetModificaForm() {
        this.setCursoModifica(new SieniCurso());
    }

    public boolean validarModifica(SieniCurso nuevo) {
        boolean ban = true;

        return ban;
    }

    public synchronized void eliminarCurso() {
        try {
            registrarEnBitacora("Eliminar", "Curso", this.getEliminar().getIdCurso());
            this.getEliminar().setCrEstado('I');
            sieniCursoFacadeRemote.edit(this.getEliminar());
            this.getCursoList().remove(this.getEliminar());
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public synchronized void eliminarInscripcionCurso() {
        try {
            registrarEnBitacora("Eliminar", "Inscripcion", this.getEliminarInscrito().getIdAlumno().getIdAlumno());
//            this.getEliminar().setCrEstado('I');
//            sieniCursoFacadeRemote.edit(this.getEliminar());
            sieniCursoAlumnoFacadeRemote.remove(this.getEliminarInscrito());
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void getSeccionesGrado(ValueChangeEvent a) {
        Long idGrado = (Long) a.getNewValue();
        SieniGrado cod = new SieniGrado();
        for (SieniGrado actual : this.getGradoList()) {
            if (actual.getIdGrado().equals(idGrado)) {
                cod = actual;
                break;
            }
        }
        this.setMateriaList(cod.getSieniMateriaList());
        this.setSeccionList(cod.getSieniSeccionList());
    }

    public void getSeccionesGradoModifica(ValueChangeEvent a) {
        Long idGrado = (Long) a.getNewValue();
        SieniGrado cod = new SieniGrado();
        for (SieniGrado actual : this.getGradoModificaList()) {
            if (actual.getIdGrado().equals(idGrado)) {
                cod = actual;
                break;
            }
        }
        this.setSeccionModificaList(cod.getSieniSeccionList());
    }
    
    public synchronized void alumnosSeleccion() {
        String alumnos="Se matricularon:\n";
        SieniCursoAlumno cursoAlumno=new SieniCursoAlumno();
        
        for (SieniAlumno alumno : this.getSelectedAlumnoList()) {
            cursoAlumno.setIdAlumno(alumno);
            cursoAlumno.setIdCurso(this.getCursoModifica());
            sieniCursoAlumnoFacadeRemote.create(cursoAlumno);
                    
            alumnos=alumnos+alumno.getAlNombreCompleto()+"\n";
        }
        refreshCursoAlumno();
        FacesMessage msg = new FacesMessage(alumnos);
                FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void refreshCursoAlumno(){
     this.setAlumnoList(sieniAlumnoFacadeRemote.findAlumnosNoCursos(this.getCursoModifica().getIdGrado().getIdGrado(),this.getCursoModifica().getIdCurso()));
//     RequestContext.getCurrentInstance().update("consulta");
    
    }
    
    public void refreshInscritos(){
          this.setAlumnoInscritoList(sieniAlumnoFacadeRemote.findAlumnosInscritos(this.getCursoModifica().getIdCurso()));
    
    }
 

}
