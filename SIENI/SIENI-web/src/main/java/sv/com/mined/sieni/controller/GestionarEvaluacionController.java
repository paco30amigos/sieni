/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniCursoFacadeRemote;
import sv.com.mined.sieni.SieniEvalRespItemFacadeRemote;
import sv.com.mined.sieni.SieniEvaluacionFacadeRemote;
import sv.com.mined.sieni.SieniEvaluacionItemFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniMatriculaFacadeRemote;
import sv.com.mined.sieni.form.GestionarEvaluacionForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.model.SieniEvalRespItem;
import sv.com.mined.sieni.model.SieniEvaluacion;
import sv.com.mined.sieni.model.SieniEvaluacionItem;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniMatricula;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author Ever
 */
@SessionScoped
@ManagedBean(name = "gestionarEvaluacionController")
public class GestionarEvaluacionController extends GestionarEvaluacionForm {

    @EJB
    private SieniEvaluacionFacadeRemote sieniEvaluacionFacadeRemote;
    @EJB
    private SieniEvaluacionItemFacadeRemote sieniEvaluacionItemFacadeRemote;
    @EJB
    private SieniEvalRespItemFacadeRemote sieniEvalRespItemFacadeRemote;
    @EJB
    private SieniCursoFacadeRemote sieniCursoFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;
    @EJB
    private SieniMatriculaFacadeRemote sieniMatriculaFacadeRemote;

    @EJB
    private SieniMateriaFacadeRemote sieniMateriaFacadeRemote;

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);
    }

    @PostConstruct
    public void init() {

        this.setEvaluacionNuevo(new SieniEvaluacion());

        this.setEvaluacionModifica(new SieniEvaluacion());
        this.setEvaluacionList(new ArrayList<SieniEvaluacion>());
        this.setEvaluacionItemNuevo(new SieniEvaluacionItem());
        this.setEvalRespItemNuevo(new SieniEvalRespItem());
        this.setTipoPregunta(new ArrayList<TipoP>());
        this.getTipoPregunta().add(new TipoP(1, "Seleccion unica"));
        this.getTipoPregunta().add(new TipoP(2, "Seleccion multiple"));
        this.getTipoPregunta().add(new TipoP(3, "Falso/Verdadero"));
        this.getTipoPregunta().add(new TipoP(4, "Abierta"));
//        this.getTipoPregunta().put("0", "Seleccion unica");
//        this.getTipoPregunta().put("1", "Seleccion multiple");
//        this.getTipoPregunta().put("2", "also/Verdadero");
//        this.getTipoPregunta().put("3", "Abierta");

        fill();
    }

    private void fill() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");

        if (loginBean.getTipoRol().charAt(0) == '0') {
            List<SieniMateria> sieniMateria = sieniMateriaFacadeRemote.findByAlumno(loginBean.getAlumno().getIdAlumno());
            SieniMatricula sieniMatricula = new SieniMatricula();//sieniMatriculaFacadeRemote
            for (SieniMateria materia : sieniMateria) {
                this.getEvaluacionList().addAll(materia.getSieniEvaluacionList());
            }
        } else {
            this.setEvaluacionList(sieniEvaluacionFacadeRemote.findActivos());
            this.setCursoList(sieniCursoFacadeRemote.findByEstado('A'));

//                List<SieniCursoAlumno> cursoAlumno=new ArrayList<>();
//                cursoAlumno= cursoAlumnoFacadeRemote.findByIdAlumno(loginBean.getAlumno().getIdAlumno());
//                for (SieniCursoAlumno cursoAlumno1 : cursoAlumno) {
//                    this.getEvaluacionList().addAll(cursoAlumno1.getIdCurso().getSieniEvaluacionList());
//                }
//                this.setEvaluacionList(cursoAlumnoFacadeRemote.findByIdAlumno(loginBean.getAlumno().getIdAlumno()));
        }
    }

    public Boolean validaAlumno() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        if (loginBean.getTipoRol().charAt(0) == '0') {
            return true;
        } else {
            return false;
        }
//        return null;

    }

    public void guardar() {
        String a = "";
        try {
            for (SieniCurso actual : this.getCursoList()) {
                if (actual.getIdCurso().equals(this.getIdCurso())) {
                    this.getEvaluacionNuevo().setIdCurso(actual);
                    this.getEvaluacionNuevo().setIdMateria(actual.getIdMateria());
                    break;
                }
            }

            this.getEvaluacionNuevo().setEvEstado('A');

            if (validarNuevo(this.getEvaluacionNuevo())) {//valida el guardado
                SieniEvaluacion existEvaluacion = new SieniEvaluacion();
//            existCurso=sieniEvaluacionFacadeRemote.finByDocGrSecMat(this.getCursoNuevo().getIdDocente().getIdDocente(), this.getCursoNuevo().getIdGrado().getIdGrado(), this.getCursoNuevo().getIdSeccion().getIdSeccion(), this.getCursoNuevo().getIdMateria().getIdMateria(),this.getCursoNuevo().getCrNombre());
//            if(existCurso!=null)
//            {
//            FacesMessage msg = new FacesMessage("No se puede crear el curso, ya existe para esa materia");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//           
//            }else{
                sieniEvaluacionFacadeRemote.create(this.getEvaluacionNuevo());
                registrarEnBitacora("Crear", "Evaluacion", this.getEvaluacionNuevo().getIdEvaluacion());
                FacesMessage msg = new FacesMessage("Evaluacion Creado Exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                this.setIndexMenu(0);
//            }

            }
            this.setEvaluacionNuevo(new SieniEvaluacion());
            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void guardarItem() {

        try {
            if ((this.getTotalPonderacion() + this.getEvaluacionItemNuevo().getEiPonderacion()) <= 100.0) {
                this.getEvaluacionItemNuevo().setEiEstado('A');
                this.getEvaluacionItemNuevo().setIdEvaluacion(this.getEvaluacionModifica());

                HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
                SieniEvaluacion existEvaluacion = new SieniEvaluacion();

                sieniEvaluacionItemFacadeRemote.create(this.getEvaluacionItemNuevo());
                registrarEnBitacora("Crear", "Evaluacion item", this.getEvaluacionItemNuevo().getIdEvaluacionItem());
                FacesMessage msg = new FacesMessage("Pregutna Creada Exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                this.setIndexMenu(4);
                this.setEvaluacionItemNuevo(new SieniEvaluacionItem());

                fillItemsEvaluacion();
            } else {
                FacesMessage msg = new FacesMessage("La ponderacion excede el 100% de la evaluacion, puede asignar un maximo de " + (100.0 - this.getTotalPonderacion()) + " puntos");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }

    }

    public void guardarRespuesta() {
        try {
            Boolean guardarBoolean = true;
//        if(this.getEvaluacionItemModifica().getEiTipoResp().equals("1") || this.getEvaluacionItemModifica().getEiTipoResp().equals("2"))

            this.getEvalRespItemNuevo().setErTipoInput("checkbox");
            if (this.getEvaluacionItemModifica().getEiTipoResp().equals("1")) {
                this.getEvalRespItemNuevo().setErTipoInput("radio");
            }
            if (this.getEvaluacionItemModifica().getEiTipoResp().equals("3")) {
                if (this.getEvalRespItemList().size() == 2) {
                    guardarBoolean = false;
                    FacesMessage msg = new FacesMessage("Solo pueden haber para esta pregunta 2 respuestas tipo F/V");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                this.getEvalRespItemNuevo().setErTipoInput("radio");
            }
            if (this.getEvaluacionItemModifica().getEiTipoResp().equals("4")) {
                if (this.getEvalRespItemList().size() == 1) {
                    guardarBoolean = false;
                    FacesMessage msg = new FacesMessage("Solo pueden haber para esta pregunta 1 respuestas");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                this.getEvalRespItemNuevo().setErTipoInput("text");
                this.getEvalRespItemNuevo().setErRespCorrecta("");
            }
            if (guardarBoolean) {
                this.getEvalRespItemNuevo().setErEstado('A');
                this.getEvalRespItemNuevo().setIdEvaluacionItem(this.getEvaluacionItemModifica());

                HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
                SieniEvaluacion existEvaluacion = new SieniEvaluacion();

                sieniEvalRespItemFacadeRemote.create(this.getEvalRespItemNuevo());
                registrarEnBitacora("Crear", "Evaluacion resp item", this.getEvalRespItemNuevo().getIdEvalRespItem());
                FacesMessage msg = new FacesMessage("Respuesta Creada Exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                this.setIndexMenu(7);
                this.setEvalRespItemNuevo(new SieniEvalRespItem());

                fillItemsResp();
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }

    }

    public void gestionarItemsEvaluacion(SieniEvaluacion eval) {
        this.setEvaluacionModifica(eval);

        fillItemsEvaluacion();
//        this.setMaterias(sieniMateriaFacadeRemote.findAll());
//        fillMateriasDocente();
//        this.setMateria(new SieniMateria());
//        for (int j = 0; j < this.getMaterias().size(); j++) {
//            for (int i = 0; i < this.getMateriasDocente().size(); i++) {
//                if (this.getMateriasDocente().get(i).getIdMateria().getIdMateria().equals(this.getMaterias().get(j).getIdMateria())) {
//                    this.getMaterias().remove(j);
//                }
//            }
//        }
//        this.setMateriasDocenteEliminadas(new ArrayList<SieniMateriaDocente>());
        this.setIndexMenu(4);
    }

    public void gestionarItemsResp(SieniEvaluacionItem evaluacionItem) {
        this.setEvaluacionItemModifica(evaluacionItem);

        fillItemsResp();
        this.setIndexMenu(7);
    }

    public void fillItemsEvaluacion() {
        this.setEvaluacionItemList(sieniEvaluacionItemFacadeRemote.findByIdEvaluacion(this.getEvaluacionModifica().getIdEvaluacion()));
        this.setTotalPonderacion(new Double("0"));
        for (SieniEvaluacionItem col : this.getEvaluacionItemList()) {
            this.setTotalPonderacion(this.getTotalPonderacion() + col.getEiPonderacion());
        }

//        List<SieniMateriaDocente> mat = sieniMateriaDocenteFacadeRemote.findByDocente(this.getDocenteModifica().getIdDocente());
//        this.setMateriasDocente(mat);
    }

    public void fillItemsResp() {
        this.setEvalRespItemList(sieniEvalRespItemFacadeRemote.findByIdEvalItem(this.getEvaluacionItemModifica().getIdEvaluacionItem()));
    }

    public void quitarFormato(SieniAlumno actual) {
//        actual.setAlTelefonoEm1(actual.getAlTelefonoEm1().replaceAll("-", ""));
//        actual.setAlTelefonoEm2(actual.getAlTelefonoEm2().replaceAll("-", ""));
//        actual.setAlPrimApe(actual.getAlPrimApe().trim());
//        actual.setAlSeguApe(actual.getAlSeguApe().trim());
//        actual.setAlTercApe(actual.getAlTercApe().trim());
//        actual.setAlPrimNombre(actual.getAlPrimNombre().trim());
//        actual.setAlSeguNombre(actual.getAlSeguNombre().trim());
//        actual.setAlTercNombre(actual.getAlTercNombre().trim());
    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniEvaluacion nuevo) {
        boolean valido = true;
        DateUtils du = new DateUtils();
        FormatUtils fu = new FormatUtils();

        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        validaciones.add(new ValidationPojo(nuevo.getEvNombre().isEmpty(), "Debe ingresar el titulo de la evaluacion", FacesMessage.SEVERITY_ERROR));
//        validaciones.add(new ValidationPojo(nuevo.getEvFechaInicio().after(nuevo.getEvFechaCierre()), "La fecha de inicio debe ser menor que la  de cierre",FacesMessage.SEVERITY_ERROR));
//        validaciones.add(new ValidationPojo(nuevo.getAlFechaNacimiento().after(du.getFechaMaxima()), "La fecha de nacimiento es mayor que " + fu.getFormatedDate(du.getFechaMaxima()), FacesMessage.SEVERITY_ERROR));
//        if (nuevo.getAlCorreo() != null && !nuevo.getAlCorreo().isEmpty()) {//si se ingreso un correo lo valida
//            validaciones.add(new ValidationPojo(!ev.validate(nuevo.getAlCorreo()), "El correo electronico no válido", FacesMessage.SEVERITY_ERROR));
//        }
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public void cancelar() {
    }

    public void getFotoNueva(FileUploadEvent event) {
        this.setFotoArchivo(event.getFile().getContents());
        this.setFotoUsable(getImage(event.getFile().getContents()));
    }

    //metodos para modificacion de datos
    public void modificar(SieniEvaluacion modificado) {
        this.setEvaluacionModifica(modificado);
        this.setIndexMenu(2);
    }

    public void modificarItem(SieniEvaluacionItem modificadoItem) {
        this.setEvaluacionItemModifica(modificadoItem);
        this.setIndexMenu(6);
    }

    public void modificarResp(SieniEvalRespItem evalRespItem) {
        this.setEvalRespItemModifica(evalRespItem);
        this.setIndexMenu(9);
    }

    public void ver(SieniEvaluacion modificado) {
        this.setEvaluacionModifica(modificado);
        this.setIndexMenu(3);
    }

    public void verEvaluacion(SieniEvaluacion modificado) {
        this.setEvaluacionItemResp(new SieniEvaluacion());
        this.setEvaluacionItemResp(sieniEvaluacionFacadeRemote.findEvalItemResp(modificado.getIdEvaluacion()));
        this.setIndexMenu(10);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniEvaluacion eliminado) {
        this.setEliminar(eliminado);
    }

    public void guardarModifica() {
        try {
            if (validarModifica(this.getEvaluacionModifica())) {//valida el guardado
                sieniEvaluacionFacadeRemote.edit(this.getEvaluacionModifica());
                registrarEnBitacora("Modificar", "Evaluacion", this.getEvaluacionModifica().getIdEvaluacion());
                new ValidationPojo().printMsj("Evaluacion Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
                fill();
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void guardarModificaItem() {

        try {
//        if (validarModifica(this.getEvaluacionModifica())) {//valida el guardado
            sieniEvaluacionItemFacadeRemote.edit(this.getEvaluacionItemModifica());
            registrarEnBitacora("Modificar", "Evaluacion item", this.getEvaluacionItemModifica().getIdEvaluacionItem());
            new ValidationPojo().printMsj("Item Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
            fillItemsEvaluacion();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
//        }
    }

    public void guardarModificaResp() {
        try {
//        if (validarModifica(this.getEvaluacionModifica())) {//valida el guardado
            sieniEvalRespItemFacadeRemote.edit(this.getEvalRespItemModifica());
            registrarEnBitacora("Modificar", "Evaluacion resp item", this.getEvalRespItemModifica().getIdEvalRespItem());
            new ValidationPojo().printMsj("Respuesta Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
            fillItemsResp();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
//        }
    }

    public void guardarResAlumno() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String inputs[] = req.getParameterValues("name1");
        Enumeration<String> totalinputs;
        totalinputs = req.getParameterNames();
        FacesMessage msg = new FacesMessage("Respuestas guardadas");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        final DataTable d = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("consultaForm:consulta");
        d.getPage();
    }

    public void resetModificaForm() {
        this.setEvaluacionModifica(new SieniEvaluacion());
    }

    public boolean validarModifica(SieniEvaluacion nuevo) {
        boolean valido = true;
//        DateUtils du = new DateUtils();
//        FormatUtils fu = new FormatUtils();
//        EmailValidator ev = new EmailValidator();
//        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
//        //alumno ya registrado
//        boolean cambio = true;
//        validaciones.add(new ValidationPojo(this.getAlumnoModifica().getAlPrimApe().isEmpty(), "Debe ingresar Primer Apellido", FacesMessage.SEVERITY_ERROR));
//        validaciones.add(new ValidationPojo(this.getAlumnoModifica().getAlPrimNombre().isEmpty(), "Debe ingresar Primer Nombre", FacesMessage.SEVERITY_ERROR));
//        SieniAlumno alOriginal = sieniAlumnoFacadeRemote.find(this.getAlumnoModifica().getIdAlumno());
//        cambio = diferencia(alOriginal.getAlPrimApe(), nuevo.getAlPrimApe());
//        cambio &= diferencia(alOriginal.getAlSeguApe(), nuevo.getAlSeguApe());
//        cambio &= diferencia(alOriginal.getAlTercApe(), nuevo.getAlTercApe());
//        cambio &= diferencia(alOriginal.getAlPrimNombre(), nuevo.getAlPrimNombre());
//        cambio &= diferencia(alOriginal.getAlSeguNombre(), nuevo.getAlSeguNombre());
//        cambio &= diferencia(alOriginal.getAlTercNombre(), nuevo.getAlTercNombre());
//        if (!cambio) {
//            validaciones.add(new ValidationPojo(sieniAlumnoFacadeRemote.alumnoRegistrado(nuevo), "El Alumno ya esta existe", FacesMessage.SEVERITY_ERROR));
//        }
//        validaciones.add(new ValidationPojo(this.getAlumnoModifica().getAlFechaNacimiento().before(du.getFechaMinima()), "La fecha de nacimiento es menor que " + fu.getFormatedDate(du.getFechaMinima()), FacesMessage.SEVERITY_ERROR));
//        validaciones.add(new ValidationPojo(this.getAlumnoModifica().getAlFechaNacimiento().after(du.getFechaMaxima()), "La fecha de nacimiento es mayor que " + fu.getFormatedDate(du.getFechaMaxima()), FacesMessage.SEVERITY_ERROR));
//        if (nuevo.getAlCorreo() != null && !nuevo.getAlCorreo().isEmpty()) {//si se ingreso un correo lo valida
//            validaciones.add(new ValidationPojo(!ev.validate(nuevo.getAlCorreo()), "El correo electronico no válido", FacesMessage.SEVERITY_ERROR));
//        }
//        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public boolean diferencia(String original, String modificado) {
        boolean ret = true;
        if (modificado != null && original != null) {
            if (!modificado.equals(original)) {
                ret = false;
            }
        } else {
            if (!((modificado == null && original != null) || modificado != null && original == null)) {
                ret = false;
            }
        }
        return ret;
    }

    public void eliminarExpediente() {
        try {
            registrarEnBitacora("Eliminar", "Evaluacion", this.getEliminar().getIdEvaluacion());
//        this.getEliminar().setsetAlFechaBaja(new Date());
            this.getEliminar().setEvEstado(new Character('I'));
            sieniEvaluacionFacadeRemote.edit(this.getEliminar());
            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }
}
