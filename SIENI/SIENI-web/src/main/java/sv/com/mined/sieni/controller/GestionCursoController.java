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
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniCursoAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniCursoFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMateriaDocenteFacadeRemote;
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
    private SieniMateriaDocenteFacadeRemote sieniMateriaDocenteFacadeRemote;
    @EJB
    private SieniGradoFacadeRemote sieniGradoFacadeRemote;
    @EJB
    private SieniMateriaFacadeRemote sieniMateriaFacadeRemote;
    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    @EJB
    private SieniCursoAlumnoFacadeRemote sieniCursoAlumnoFacadeRemote;
    @EJB
    private SieniSeccionFacadeRemote sieniSeccionFacadeRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);

    }

    private List<SieniCurso> setDocente(List<SieniCurso> matriculas) {
        List<SieniCurso> ret = new ArrayList<>();
        for (SieniCurso actual : matriculas) {
            ret.add(setInfoDocente(actual));
        }
        return ret;
    }

    public SieniCurso setInfoDocente(SieniCurso matActual) {
        matActual.setDocente(sieniDocenteFacadeRemote.findByDocenteId(matActual.getIdDocente()));
        return matActual;
    }

    @PostConstruct
    public void init() {
        this.setCursoNuevo(new SieniCurso());
        this.setCursoModifica(new SieniCurso());
        this.setCursoList(new ArrayList<SieniCurso>());
        fill();
    }

    public void cancelaModifica(SieniCurso modifica) {
        modifica = sieniCursoFacadeRemote.find(modifica.getIdCurso());
        this.setIndexMenu(0);
    }

    private void fill() {
        this.setCursoList(setDocente(sieniCursoFacadeRemote.findByEstado('A')));

        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
//        this.setCursoList(setDocente(sieniCursoFacadeRemote.findByDocente(loginBean.getDocente().getIdDocente())));
        //nuevo        
    }

    public void initNuevo() {
        this.setGradoList(sieniGradoFacadeRemote.findAllNoInactivos());
//        this.setMateriaList(sieniMateriaFacadeRemote.findMateriasActivas());
//        this.setSeccionList(new ArrayList<SieniSeccion>());
        if (this.getGradoList() != null && !this.getGradoList().isEmpty()) {
            this.setIdGrado(this.getGradoList().get(0).getIdGrado());
            if (this.getGradoList().get(0).getSieniSeccionList() != null
                    && !this.getGradoList().get(0).getSieniSeccionList().isEmpty()) {
                this.setIdSeccion(this.getGradoList().get(0).getSieniSeccionList().get(0).getIdSeccion());
                List<SieniSeccion> sec=sieniSeccionFacadeRemote.findByGrado(this.getGradoList().get(0).getIdGrado());
                this.setSeccionList(sec);
            }
            this.setMateriaList(sieniMateriaFacadeRemote.findMateriasActivasByGrado(this.getGradoList().get(0).getIdGrado()));
            if (this.getMateriaList() != null && !this.getMateriaList().isEmpty()) {
                this.setIdMateria(this.getMateriaList().get(0).getIdMateria());
                this.setDocentesList(sieniMateriaDocenteFacadeRemote.findByMateria(this.getMateriaList().get(0).getIdMateria()));
            } else {
                this.setDocentesList(new ArrayList<SieniDocente>());
            }
        } else {
            this.setMateriaList(new ArrayList<SieniMateria>());
            this.setDocente(new ArrayList<SieniCurso>());
            this.setSeccionList(new ArrayList<SieniSeccion>());
        }
        this.setIndexMenu(1);
    }

    public synchronized void guardar() {
        try {
            for (SieniDocente actual : this.getDocentesList()) {
                if (actual.getIdDocente().equals(this.getIdDocente())) {
                    this.getCursoNuevo().setIdDocente(actual.getIdDocente());
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

                existCurso = sieniCursoFacadeRemote.finByDocGrSecMat(this.getCursoNuevo().getIdDocente(), this.getCursoNuevo().getIdGrado().getIdGrado(), this.getCursoNuevo().getIdSeccion().getIdSeccion(), this.getCursoNuevo().getIdMateria().getIdMateria(), this.getCursoNuevo().getCrNombre());
                if (existCurso != null) {
                    new ValidationPojo().printMsj("No se puede crear el curso, ya existe para esa materia", FacesMessage.SEVERITY_ERROR);

                } else {
                    this.setCursoNuevo(sieniCursoFacadeRemote.createAndReturn(this.getCursoNuevo()));
                    registrarEnBitacora("Crear", "Curso", this.getCursoNuevo().getIdCurso());
                    new ValidationPojo().printMsj("Curso Creado Exitosamente", FacesMessage.SEVERITY_INFO);
                    this.setCursoNuevo(setInfoDocente(this.getCursoNuevo()));
                    //agrega el curso a la tabla de busqueda
                    this.getCursoList().add(this.getCursoNuevo());
                    //limpia solo si es guardado exitosamente
                    this.setCursoNuevo(new SieniCurso());
                }
            }
//        fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void quitarFormato(SieniCurso actual) {

    }

    public void gestionarCursoAlumno(SieniCurso curso) {
        this.setAlumnoList(new ArrayList<SieniAlumno>());
        this.setAlumnoList(sieniAlumnoFacadeRemote.findAlumnosNoCursos(curso.getIdGrado().getIdGrado(), curso.getIdSeccion().getIdSeccion(), curso.getIdCurso()));
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
        this.setIdGradoModifica(modificado.getIdGrado().getIdGrado());
        this.setIdMateriaModifica(modificado.getIdMateria().getIdMateria());
        this.setIdDocenteModifica(modificado.getIdDocente());
        this.setGradoModificaList(sieniGradoFacadeRemote.findAllNoInactivos());
        this.setSeccionModificaList(new ArrayList<SieniSeccion>());
        if (this.getGradoModificaList() != null && !this.getGradoModificaList().isEmpty()) {
            for (SieniGrado actual : this.getGradoModificaList()) {
                if (actual.getIdGrado().equals(modificado.getIdGrado().getIdGrado())) {
                    List<SieniSeccion> sec=sieniSeccionFacadeRemote.findByGrado(actual.getIdGrado());
                    this.setSeccionModificaList(sec);
                    break;
                }
            }
            this.setMateriaModificaList(sieniMateriaFacadeRemote.findMateriasActivasByGrado(modificado.getIdGrado().getIdGrado()));
            this.setDocentesModificaList(sieniMateriaDocenteFacadeRemote.findByMateria(modificado.getIdMateria().getIdMateria()));
        } else {
            this.setSeccionModificaList(new ArrayList<SieniSeccion>());
            this.setMateriaModificaList(new ArrayList<SieniMateria>());
            this.setDocentesModificaList(new ArrayList<SieniDocente>());
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
    public void eliminarInscrito(SieniCurso curso, SieniAlumno alumno) {
//        for (SieniCursoAlumno cursos : alumno.getSieniCursoAlumnoList()) {
//            if(cursos.getIdCurso().getIdCurso().equals(curso.getIdCurso())){
        this.setEliminarInscrito(sieniCursoAlumnoFacadeRemote.findByIdCursoIdAlumno(curso.getIdCurso(), alumno.getIdAlumno()));
//            break;
//            }

//        }
    }

    public synchronized void guardarModifica() {
        try {
            for (SieniDocente actual : this.getDocentesModificaList()) {
                if (actual.getIdDocente().equals(this.getIdDocenteModifica())) {
                    this.getCursoModifica().setIdDocente(actual.getIdDocente());
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
                new ValidationPojo().printMsj("Curso Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
            }
//        fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
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
            new ValidationPojo().printMsj("Registro eliminado exitosamente", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public synchronized void eliminarInscripcionCurso() {
        try {
            registrarEnBitacora("Eliminar", "Inscripcion", this.getEliminarInscrito().getIdAlumno());
            this.getEliminarInscrito().setCraEstado('I');
            sieniCursoAlumnoFacadeRemote.edit(this.getEliminarInscrito());
            
//            sieniCursoAlumnoFacadeRemote.remove(this.getEliminarInscrito());
            new ValidationPojo().printMsj("Registro eliminado exitosamente", FacesMessage.SEVERITY_INFO);
            gestionarAlumnoInscrito(this.getEliminarInscrito().getIdCurso());
            
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
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
        this.setMateriaList(sieniMateriaFacadeRemote.findMateriasActivasByGrado(idGrado));
        List<SieniSeccion> sec=sieniSeccionFacadeRemote.findByGrado(idGrado);
        this.setSeccionList(sec);
        if (this.getMateriaList() != null && !this.getMateriaList().isEmpty()) {
            this.setDocentesList(sieniMateriaDocenteFacadeRemote.findByMateria(this.getMateriaList().get(0).getIdMateria()));
        } else {
            this.setDocentesList(new ArrayList<SieniDocente>());
        }
    }

    public void getDocenteMateria(ValueChangeEvent a) {
        Long idMateria = (Long) a.getNewValue();
        this.setDocentesList(sieniMateriaDocenteFacadeRemote.findByMateria(idMateria));
    }

    public void getDocenteMateriaModifica(ValueChangeEvent a) {
        Long idMateria = (Long) a.getNewValue();
        this.setDocentesModificaList(sieniMateriaDocenteFacadeRemote.findByMateria(idMateria));
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
        this.setMateriaModificaList(sieniMateriaFacadeRemote.findMateriasActivasByGrado(idGrado));
        List<SieniSeccion> sec=sieniSeccionFacadeRemote.findByGrado(idGrado);
        this.setSeccionModificaList(sec);
        if (this.getMateriaModificaList() != null && !this.getMateriaModificaList().isEmpty()) {
            this.setDocentesModificaList(sieniMateriaDocenteFacadeRemote.findByMateria(this.getMateriaModificaList().get(0).getIdMateria()));
        } else {
            this.setDocentesModificaList(new ArrayList<SieniDocente>());
        }
    }

    public synchronized void alumnosSeleccion() {
        String alumnos = "Se matricularon:\n";
        SieniCursoAlumno cursoAlumno = new SieniCursoAlumno();

        if (validarInscripcion()) {
            for (SieniAlumno alumno : this.getSelectedAlumnoList()) {
                cursoAlumno.setIdAlumno(alumno.getIdAlumno());
                cursoAlumno.setIdCurso(this.getCursoModifica());
                cursoAlumno.setCraEstado('A');
                sieniCursoAlumnoFacadeRemote.create(cursoAlumno);

                alumnos = alumnos + alumno.getAlNombreCompleto() + "\n";
            }
            refreshCursoAlumno();
            FacesMessage msg = new FacesMessage(alumnos);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public boolean validarInscripcion() {
        boolean ban = true;
        List<SieniAlumno> li = sieniAlumnoFacadeRemote.findAlumnosInscritos(this.getCursoModifica().getIdCurso());
        if (this.getSelectedAlumnoList().size() + li.size() > this.getCursoModifica().getCrCapacidad()) {
            ban = false;
            new ValidationPojo().printMsj("No se puede realizar la inscripcion, capacidad del curso:" + this.getCursoModifica().getCrCapacidad() + ", alumnos inscritos:" + li.size(), FacesMessage.SEVERITY_ERROR);
        }
        return ban;
    }

    public void refreshCursoAlumno() {
        this.setAlumnoList(sieniAlumnoFacadeRemote.findAlumnosNoCursos(this.getCursoModifica().getIdGrado().getIdGrado(), this.getCursoModifica().getIdSeccion().getIdSeccion(), this.getCursoModifica().getIdCurso()));
//     RequestContext.getCurrentInstance().update("consulta");

    }

    public void refreshInscritos() {
        this.setAlumnoInscritoList(sieniAlumnoFacadeRemote.findAlumnosInscritos(this.getCursoModifica().getIdCurso()));

    }

}
