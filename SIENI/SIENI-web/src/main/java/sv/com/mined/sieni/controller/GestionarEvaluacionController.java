/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniCursoAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniCursoFacadeRemote;
import sv.com.mined.sieni.SieniEvalRespAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniEvalRespItemFacadeRemote;
import sv.com.mined.sieni.SieniEvaluacionFacadeRemote;
import sv.com.mined.sieni.SieniEvaluacionItemFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniMatriculaFacadeRemote;
import sv.com.mined.sieni.SieniNotaFacadeRemote;
import sv.com.mined.sieni.form.GestionarEvaluacionForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.model.SieniEvalRespAlumno;
import sv.com.mined.sieni.model.SieniEvalRespItem;
import sv.com.mined.sieni.model.SieniEvaluacion;
import sv.com.mined.sieni.model.SieniEvaluacionItem;
import sv.com.mined.sieni.model.SieniNota;
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
    private SieniEvalRespAlumnoFacadeRemote sieniEvalRespAlumnoFacadeRemote;

    @EJB
    private SieniNotaFacadeRemote sieniNotaFacadeRemote;

    Boolean disponible;

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

//        this.setCalificacion(Double.valueOf(0));
        this.getTipoPregunta().add(new TipoP(1, "Seleccion unica"));
        this.getTipoPregunta().add(new TipoP(2, "Seleccion multiple"));
        this.getTipoPregunta().add(new TipoP(3, "Falso/Verdadero"));
        this.getTipoPregunta().add(new TipoP(4, "Abierta"));
        this.setTipoEvaluacion("Digital");
        disponible = true;
        fill();
    }

    public boolean verificarAnioEscolar(Date fecha) {
        boolean ret = false;
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        //la fecha debe estar dentro del rango de fechas del periodo escolar
        //fecha>=fechaInicio&&fecha<=fechaFin
        if (!fecha.before(loginBean.getAnioEscolarActivo().getAeInicio()) && !fecha.after(loginBean.getAnioEscolarActivo().getAeFin())) {
            ret = true;
        } else {
            new ValidationPojo().printMsj("El registro no se puede modificar para el año escolar " + loginBean.getAnioEscolarActivo().getAeAnio().toString() + ", fecha: " + new FormatUtils().getFormatedDate(fecha), FacesMessage.SEVERITY_ERROR);
        }
        return ret;
    }

    public void initNuevo() {
        this.setCursoList(sieniCursoFacadeRemote.findByEstado('A'));
        this.setEvaluacionNuevo(new SieniEvaluacion());
        this.getEvaluacionNuevo().setEvTipo("Digital");
        this.setIndexMenu(1);
    }

    public void cancelaModifica(SieniEvaluacion modifica) {
        modifica = sieniEvaluacionFacadeRemote.find(modifica.getIdEvaluacion());
        this.setIndexMenu(0);
    }

    private void fill() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");

        if (loginBean.getTipoRol().charAt(0) == '0') {
            this.setEvaluacionList(sieniEvaluacionFacadeRemote.findByAlumno(loginBean.getAlumno().getIdAlumno()));
        } else {
            this.setEvaluacionList(sieniEvaluacionFacadeRemote.findActivos());
            this.setCursoList(sieniCursoFacadeRemote.findByEstado('A'));

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
    }

    public synchronized void guardar() {
        try {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            for (SieniCurso actual : this.getCursoList()) {
                if (actual.getIdCurso().equals(this.getIdCurso())) {
                    this.getEvaluacionNuevo().setIdCurso(actual);
                    this.getEvaluacionNuevo().setIdMateria(actual.getIdMateria());
                    break;
                }
            }
            this.getEvaluacionNuevo().setEvEstado('A');
            if (validarNuevo(this.getEvaluacionNuevo())) {//valida el guardado
                if (!this.getEvaluacionNuevo().getEvTipo().equals("Digital")) {
                    this.getEvaluacionNuevo().setEvFechaCierre(this.getEvaluacionNuevo().getEvFechaInicio());
                }
                this.getEvaluacionNuevo().setIdDocente(loginBean.getDocente().getIdDocente());
                this.setEvaluacionNuevo(sieniEvaluacionFacadeRemote.createAndReturn(this.getEvaluacionNuevo()));
                registrarEnBitacora("Crear", "Evaluacion", this.getEvaluacionNuevo().getIdEvaluacion());
                new ValidationPojo().printMsj("Evaluacion Creada Exitosamente", FacesMessage.SEVERITY_INFO);
                this.setCursoList(sieniCursoFacadeRemote.findByEstado('A'));
                this.setEvaluacionNuevo(new SieniEvaluacion());
                this.getEvaluacionNuevo().setEvTipo("Digital");
                fill();
            }
            this.setCursoList(sieniCursoFacadeRemote.findByEstado('A'));
            this.getEvaluacionNuevo().setEvTipo("Digital");
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public synchronized void guardarItem() {

        try {
            if (validaItems(this.getEvaluacionItemNuevo())) {
                if (this.getEvaluacionItemNuevo().getEiPonderacion() == 0.0) {
                    new ValidationPojo().printMsj("La ponderación de la pregunta no puede ser cero", FacesMessage.SEVERITY_ERROR);
                } else if ((this.getTotalPonderacion() + this.getEvaluacionItemNuevo().getEiPonderacion()) <= 100.0) {
                    this.getEvaluacionItemNuevo().setEiEstado('A');
                    this.getEvaluacionItemNuevo().setIdEvaluacion(this.getEvaluacionModifica());

                    HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                    LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
                    SieniEvaluacion existEvaluacion = new SieniEvaluacion();

                    sieniEvaluacionItemFacadeRemote.create(this.getEvaluacionItemNuevo());
                    registrarEnBitacora("Crear", "Evaluacion item", this.getEvaluacionItemNuevo().getIdEvaluacionItem());
                    new ValidationPojo().printMsj("Pregunta Creada Exitosamente", FacesMessage.SEVERITY_INFO);
//                    FacesMessage msg = new FacesMessage("Pregutna Creada Exitosamente");
//                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    this.setIndexMenu(4);
                    this.setEvaluacionItemNuevo(new SieniEvaluacionItem());

                    fillItemsEvaluacion();
                } else {
                    new ValidationPojo().printMsj("La ponderacion excede el 100% de la evaluacion, puede asignar un maximo de " + (100.0 - this.getTotalPonderacion()) + " puntos", FacesMessage.SEVERITY_ERROR);
                }
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }

    }

    public synchronized void guardarRespuesta() {
        try {
            Boolean guardarBoolean = true;

            this.getEvalRespItemNuevo().setErTipoInput("checkbox");
            if (this.getEvaluacionItemModifica().getEiTipoResp().equals("1")) {
                this.getEvalRespItemNuevo().setErTipoInput("radio");
            }
            if (this.getEvaluacionItemModifica().getEiTipoResp().equals("3")) {
                if (this.getEvalRespItemList().size() == 2) {
                    guardarBoolean = false;
                    new ValidationPojo().printMsj("Solo pueden haber para esta pregunta 2 respuestas tipo F/V", FacesMessage.SEVERITY_ERROR);
                }
                this.getEvalRespItemNuevo().setErTipoInput("radio");
            }
            if (this.getEvaluacionItemModifica().getEiTipoResp().equals("4")) {
                if (this.getEvalRespItemList().size() == 1) {
                    guardarBoolean = false;
                    new ValidationPojo().printMsj("Solo pueden haber para esta pregunta 1 respuestas", FacesMessage.SEVERITY_ERROR);
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
                new ValidationPojo().printMsj("Respuesta Creada Exitosamente", FacesMessage.SEVERITY_INFO);
                this.setIndexMenu(7);
                this.setEvalRespItemNuevo(new SieniEvalRespItem());

                fillItemsResp();
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }

    }

    public void gestionarItemsEvaluacion(SieniEvaluacion eval) {
        if (verificarAnioEscolar(eval.getEvFechaCierre())) {
            if (validarUsuarioModificaEvaluacion(eval)) {
                this.setEvaluacionModifica(eval);
                fillItemsEvaluacion();
                this.setIndexMenu(4);
            }
        }
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

    }

    public void fillItemsResp() {
        this.setEvalRespItemList(sieniEvalRespItemFacadeRemote.findByIdEvalItem(this.getEvaluacionItemModifica().getIdEvaluacionItem()));
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
        if (nuevo.getEvTipo() != null && nuevo.getEvTipo().equals("Digital")) {
            validaciones.add(new ValidationPojo(nuevo.getEvFechaInicio() == null, "Debe ingresar la fecha de de cierre", FacesMessage.SEVERITY_ERROR));
            validaciones.add(new ValidationPojo(nuevo.getEvFechaInicio() == null || (nuevo.getEvFechaInicio().getTime() >= nuevo.getEvFechaCierre().getTime()), "La fecha de inicio debe ser menor o igual a la de cierre", FacesMessage.SEVERITY_ERROR));
        }

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
        if (verificarAnioEscolar(modificado.getEvFechaCierre())) {
            if (validarUsuarioModificaEvaluacion(modificado)) {
                this.setCursoList(sieniCursoFacadeRemote.findByEstado('A'));
                this.setEvaluacionModifica(modificado);
                this.setIndexMenu(2);
            }
        }
    }

    public boolean validarUsuarioModificaEvaluacion(SieniEvaluacion modificado) {
        boolean ban = false;
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        //si es el que ingreso la evaluacion o el coordinador de la materia
        if (loginBean.getAlumno() != null) {
            ban = true;
        } else {
            if (modificado.getIdDocente() != null && modificado.getIdDocente().equals(loginBean.getDocente().getIdDocente())
                    || loginBean.getDocente().getIdDocente().equals(modificado.getIdMateria().getMaCoordinador())) {
                ban = true;
            } else {
                new ValidationPojo().printMsj("Unicamente el creador de la evaluación y el coordinador de la materia puede modificar la evaluacion", FacesMessage.SEVERITY_ERROR);
            }
        }
        return ban;

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
        if (validarUsuarioModificaEvaluacion(modificado)) {
            this.setEvaluacionModifica(modificado);
            this.setIndexMenu(3);
        }
    }

    public void mostrar(SieniEvaluacion modificado) {
        if (validarUsuarioModificaEvaluacion(modificado)) {
            this.setEvaluacionModifica(modificado);
            this.setIndexMenu(11);
        }
    }

    public void verEvaluacion(SieniEvaluacion modificado) {
        if (verificarAnioEscolar(modificado.getEvFechaCierre())) {
            if (validarUsuarioModificaEvaluacion(modificado)) {
                HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
                this.setEvaluacionItemResp(new SieniEvaluacion());
                this.setEvaluacionItemResp(sieniEvaluacionFacadeRemote.findEvalItemResp(modificado.getIdEvaluacion()));
                List<SieniNota> notas = new ArrayList<>();
                if (loginBean.getAlumno() != null) {
                    notas = sieniNotaFacadeRemote.findNotasAlumnoEv(loginBean.getAlumno().getIdAlumno(), modificado.getIdEvaluacion());
                }

                if (notas != null) {
                    this.setNumIntento(notas.size());
                    if (this.getNumIntento() < modificado.getEvIntentos()) {
                        this.setEvaluacionItemList(new ArrayList<SieniEvaluacionItem>());
                        this.setEvaluacionItemList(this.getEvaluacionItemResp().getSieniEvaluacionItemList());
                        if ("Si".equals(this.getEvaluacionItemResp().getEvPreguntasAleatorias())) {
                            Collections.shuffle(this.getEvaluacionItemList());
                        }
                        if ("Si".equals(this.getEvaluacionItemResp().getEvRespuestasAleatorias())) {
                            for (SieniEvaluacionItem evaluacionItem : this.getEvaluacionItemList()) {
//                evalRespItems=evaluacionItem.getSieniEvalRespItemList();
                                Collections.shuffle(evaluacionItem.getSieniEvalRespItemList());
                            }
                        }
                        this.setIndexMenu(10);
                    } else {
//                FacesMessage msg;
//            msg = new FacesMessage("La evaluacion no permite mas intentos");
//            FacesContext.getCurrentInstance().addMessage(, msg); 
                        new ValidationPojo().printMsj("La evaluacion no permite mas intentos", FacesMessage.SEVERITY_ERROR);
                    }
                } else {
                    this.setNumIntento(0);
                    this.setEvaluacionItemList(new ArrayList<SieniEvaluacionItem>());
                    this.setEvaluacionItemList(this.getEvaluacionItemResp().getSieniEvaluacionItemList());
//        List<SieniEvalRespItem> evalRespItems=new ArrayList<>();
                    for (SieniEvaluacionItem evaluacionItem : this.getEvaluacionItemList()) {
//                evalRespItems=evaluacionItem.getSieniEvalRespItemList();
                        Collections.shuffle(evaluacionItem.getSieniEvalRespItemList());
                    }

                    if ("Si".equals(this.getEvaluacionItemResp().getEvPreguntasAleatorias())) {
                        Collections.shuffle(this.getEvaluacionItemList());
                    }
                    if ("Si".equals(this.getEvaluacionItemResp().getEvRespuestasAleatorias())) {
                        for (SieniEvaluacionItem evaluacionItem : this.getEvaluacionItemList()) {
//                evalRespItems=evaluacionItem.getSieniEvalRespItemList();
                            Collections.shuffle(evaluacionItem.getSieniEvalRespItemList());
                        }
                    }
                    this.setIndexMenu(10);
                }
            }
        }

    }

    //metodos para modificacion de datos
    public void eliminar(SieniEvaluacion eliminado) {
        this.setEliminar(eliminado);
    }

    public synchronized void guardarModifica() {
        try {
            if (validarModifica(this.getEvaluacionModifica())) {//valida el guardado
                sieniEvaluacionFacadeRemote.edit(this.getEvaluacionModifica());
                registrarEnBitacora("Modificar", "Evaluacion", this.getEvaluacionModifica().getIdEvaluacion());
                new ValidationPojo().printMsj("Evaluacion Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
                fill();
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public synchronized void guardarModificaItem() {

        try {
//            if (validaItems(this.getEvaluacionItemModifica())) {
            sieniEvaluacionItemFacadeRemote.edit(this.getEvaluacionItemModifica());
            registrarEnBitacora("Modificar", "Evaluacion item", this.getEvaluacionItemModifica().getIdEvaluacionItem());
            new ValidationPojo().printMsj("Item Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
            fillItemsEvaluacion();
//            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public boolean validaItems(SieniEvaluacionItem items) {
        boolean valido = true;
        DateUtils du = new DateUtils();
        FormatUtils fu = new FormatUtils();

        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        validaciones.add(new ValidationPojo(this.getEvaluacionModifica().getEvTotalPreguntas() == null
                || this.getEvaluacionModifica().getEvTotalPreguntas() <= 0, "La evaluación no tiene un total de preguntas asignadas", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(this.getEvaluacionModifica().getEvTotalPreguntas() != null
                && this.getEvaluacionModifica().getEvTotalPreguntas() <= this.getEvaluacionItemList().size(), "Ya no se pueden asignar items a la evaluacion", FacesMessage.SEVERITY_ERROR));

        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public synchronized void guardarModificaResp() {
        try {

            sieniEvalRespItemFacadeRemote.edit(this.getEvalRespItemModifica());
            registrarEnBitacora("Modificar", "Evaluacion resp item", this.getEvalRespItemModifica().getIdEvalRespItem());
            new ValidationPojo().printMsj("Respuesta Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
            fillItemsResp();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public synchronized void guardarResAlumno(Boolean timeout) {

        try {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            final DataTable d = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("consultaForm:consulta");
            Boolean ultimaPagina = false;
            List<String[]> respuestas = new ArrayList<>();
            List<Long> idRespuestas = new ArrayList<>();
            int totalPag = this.getEvaluacionItemResp().getSieniEvaluacionItemList().size() / this.getEvaluacionItemResp().getEvPreguntasPagina().intValue();
            int reciduo = this.getEvaluacionItemResp().getSieniEvaluacionItemList().size() % this.getEvaluacionItemResp().getEvPreguntasPagina().intValue();
            int numPagina = d.getPage() + 1;

            int fin = numPagina * this.getEvaluacionItemResp().getEvPreguntasPagina().intValue();
            int inicio = fin - (this.getEvaluacionItemResp().getEvPreguntasPagina().intValue() - 1);

            if (reciduo != 0) {
                totalPag++;

                if (numPagina == totalPag) {
                    inicio = (numPagina - 1) * this.getEvaluacionItemResp().getEvPreguntasPagina().intValue() + 1;
                    fin = inicio + (reciduo - 1);
                    for (int i = inicio; i <= fin; i++) {
                        respuestas.add(req.getParameterValues("name" + (i)));
                        idRespuestas.add(Long.valueOf(req.getParameter("id" + (i))));
                    }

                    ultimaPagina = true;
                } else {

                    for (int i = inicio; i <= fin; i++) {
                        respuestas.add(req.getParameterValues("name" + (i)));
                        idRespuestas.add(Long.valueOf(req.getParameter("id" + (i))));
                    }
                }

            } else {
                if (numPagina == totalPag) {
                    ultimaPagina = true;
                }
                for (int i = inicio; i <= fin; i++) {
                    respuestas.add(req.getParameterValues("name" + (i)));
                    idRespuestas.add(Long.valueOf(req.getParameter("id" + (i))));
                }
            }

            this.setEvalRespAlumnoList(new ArrayList<SieniEvalRespAlumno>());

            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");

            for (int j = 0; j < (fin - inicio + 1); j++) {
                SieniEvalRespAlumno respAlumno = new SieniEvalRespAlumno();
                respAlumno.setRaRespuesta(getCadena(respuestas.get(j)));
                SieniAlumno alumno = new SieniAlumno();
                respAlumno.setIdAlumno(loginBean.getAlumno().getIdAlumno());
                //Long.parseLong(params.get(String.valueOf(j)))

                for (SieniEvaluacionItem evItem : this.getEvaluacionItemResp().getSieniEvaluacionItemList()) {
                    if (idRespuestas.get(j).equals(evItem.getIdEvaluacionItem())) {
                        respAlumno.setIdEvaluacionItem(evItem);
                        break;
                    }
                }
                respAlumno.setRaEstado('A');
                this.getEvalRespAlumnoList().add(respAlumno);

            }
            FacesMessage msg;
            sieniEvalRespAlumnoFacadeRemote.guardarRespuestasAlumno(this.getEvalRespAlumnoList(), this.getNumIntento());
            if (ultimaPagina || timeout) {
                Double nota = calcularNotas(loginBean.getAlumno(), this.getEvaluacionItemResp());
                updateNotasAnteriores();
                SieniNota sieniNota = new SieniNota();
                sieniNota.setIdAlumno(loginBean.getAlumno().getIdAlumno());
                sieniNota.setIdEvaluacion(this.getEvaluacionItemResp());
                sieniNota.setNtAnio(Calendar.getInstance().get(Calendar.YEAR));
                sieniNota.setNtEstado('A');
                sieniNota.setNtCalificacion(nota);
                sieniNota.setNtFechaIngreso(new Date());
//                sieniNota.setNtDocente(this.getEvaluacionItemResp().getIdMateria().getMaCoordinador());
                BigDecimal notaAux = new BigDecimal(sieniNota.getNtCalificacion());
                this.setCalificacion(notaAux.setScale(2, RoundingMode.HALF_UP).doubleValue());
                sieniNota.setNtCalificacion(this.getCalificacion());
                sieniNota.setNtTipoIngreso("A");
                sieniNotaFacadeRemote.create(sieniNota);
//            this.setNotaALumno(sieniNota);
                RequestContext context = RequestContext.getCurrentInstance();
                if ("Si".equals(this.getEvaluacionItemResp().getEvNotaFin())) {
                    context.execute("PF('dlgfinEvaVar').show();");
                    context.update("consultaForm:dlgfinEva");
                } else {
                    context.execute("PF('dlgfinVar').show();");
                    context.update("consultaForm:dlgfin");
                }

//            msg = new FacesMessage("Examen finalizado, su nota es: " + this.getCalificacion());
            } else {
                msg = new FacesMessage("Se guardaron las respuestas");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }

    }

    //pone las notas anteriores como inactivas
    public void updateNotasAnteriores() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        List<SieniNota> notas = sieniNotaFacadeRemote.findNotasAlumnoEv(loginBean.getAlumno().getIdAlumno(), this.getEvaluacionItemResp().getIdEvaluacion());
        for (SieniNota actual : notas) {
            actual.setNtEstado('I');
        }
        sieniNotaFacadeRemote.merge(notas);
    }

    public String getCadena(String[] cad) {
        StringBuilder st = new StringBuilder();
        boolean ban;
        int cont = 0;
        if (cad != null) {
            for (String actual : cad) {
                st.append(actual);
                ban = cont == cad.length - 1;
                if (!ban) {
                    st.append(",");
                }
                cont++;
            }
        }
        return st.toString();
    }

    public Boolean evaluacionDisponible(SieniEvaluacion evaluacion) {
        Date fechaActual = new Date();
        try {
            if (evaluacion.getEvFechaCierre() != null && evaluacion.getEvFechaInicio() != null) {
                if ((fechaActual.getTime() >= evaluacion.getEvFechaInicio().getTime() && fechaActual.getTime() <= evaluacion.getEvFechaCierre().getTime()) && !"Escrita".equals(evaluacion.getEvTipo())) {
                    disponible = true;
                } else {
                    disponible = false;
                }
            }
            if (!validaAlumno() && !"Escrita".equals(evaluacion.getEvTipo())) {
                disponible = true;
            }

        } catch (Exception e) {
            disponible = false;
        }

        return disponible;
    }

    public Double calcularNotas(SieniAlumno alumno, SieniEvaluacion evaluacion) {
        int numFalsasAlumno = 0;
        int numVerdaderasAlumno = 0;
        int numFalsas = 0;
        int numVerdaderas = 0;
        List<Double> notaPregunta = new ArrayList<>();
        Double nota = new Double("0.0");
        List<String> respuestas = new ArrayList<>();
        String resAux = "";
        String respuestalibreAlumno = null;
        String respuestLibre = null;
        this.setEvalRespAlumnoList(new ArrayList<SieniEvalRespAlumno>());
        this.setEvalRespAlumnoList(sieniEvalRespAlumnoFacadeRemote.findByAlumnoEv(alumno, evaluacion));

        for (int i = 0; i < this.getEvalRespAlumnoList().size(); i++) {
            for (SieniEvalRespItem res : this.getEvalRespAlumnoList().get(i).getIdEvaluacionItem().getSieniEvalRespItemList()) {
                resAux = resAux + (res.getErRespCorrecta()) + ",";
                if (this.getEvalRespAlumnoList().get(i).getIdEvaluacionItem().getEiTipoResp().equals("4")) {
                    respuestLibre = res.getErRespuesta();

                }
            }

            if (respuestLibre != null) {
                numFalsas = 0;
                numFalsasAlumno = 0;
                if (respuestLibre.equals(this.getEvalRespAlumnoList().get(i).getRaRespuesta())) {
                    numVerdaderas = 1;
                    numVerdaderasAlumno = 1;
                } else {
                    numVerdaderas = 1;
                    numVerdaderasAlumno = 0;
                }

            } else {
                numFalsas = contaPalabra("NO", resAux);
                numFalsasAlumno = contaPalabra("NO", this.getEvalRespAlumnoList().get(i).getRaRespuesta());
                numVerdaderas = contaPalabra("SI", resAux);
                numVerdaderasAlumno = contaPalabra("SI", this.getEvalRespAlumnoList().get(i).getRaRespuesta());
            }
            resAux = "";
            if (numFalsasAlumno > numVerdaderas) {
                notaPregunta.add(new Double("0.0"));
            } else {
                if (numFalsasAlumno > numVerdaderasAlumno) {
                    notaPregunta.add(new Double("0.0"));
                } else {
//                   Double diferencia=((numVerdaderasAlumno-numFalsasAlumno)/numVerdaderas);
                    notaPregunta.add(((float) (numVerdaderasAlumno - numFalsasAlumno) / numVerdaderas) * this.getEvalRespAlumnoList().get(i).getIdEvaluacionItem().getEiPonderacion() / 10.0);
                }
            }

        }

        for (Double notas : notaPregunta) {
            nota = nota + notas;
        }
        return nota;

    }

    public int contaPalabra(String palabra, String texto) {
        int contador = 0;
        while (texto.indexOf(palabra) > -1) {
            texto = texto.substring(texto.indexOf(palabra) + palabra.length(), texto.length());
            contador++;
        }

        return contador;
    }

    public void resetModificaForm() {
        this.setEvaluacionModifica(new SieniEvaluacion());
    }

    public boolean validarModifica(SieniEvaluacion nuevo) {
        boolean valido = true;
        DateUtils du = new DateUtils();
        FormatUtils fu = new FormatUtils();

        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        validaciones.add(new ValidationPojo(nuevo.getEvNombre().isEmpty(), "Debe ingresar el titulo de la evaluacion", FacesMessage.SEVERITY_ERROR));
        if (nuevo.getEvTipo() != null && nuevo.getEvTipo().equals("Digital")) {
            validaciones.add(new ValidationPojo(nuevo.getEvFechaInicio() == null, "Debe ingresar la fecha de de cierre", FacesMessage.SEVERITY_ERROR));
            validaciones.add(new ValidationPojo(nuevo.getEvFechaInicio() == null || (nuevo.getEvFechaInicio().getTime() >= nuevo.getEvFechaCierre().getTime()), "La fecha de inicio debe ser menor o igual a la de cierre", FacesMessage.SEVERITY_ERROR));
        }

        valido = !ValidationPojo.printErrores(validaciones);
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

    public synchronized void eliminarExpediente() {
        try {
            if (verificarAnioEscolar(this.getEliminar().getEvFechaCierre())) {
                if (validarUsuarioModificaEvaluacion(this.getEliminar())) {
                    registrarEnBitacora("Eliminar", "Evaluacion", this.getEliminar().getIdEvaluacion());
                    this.getEliminar().setEvEstado(new Character('I'));
                    sieniEvaluacionFacadeRemote.edit(this.getEliminar());
                    fill();
                    new ValidationPojo().printMsj("Registro eliminado exitosamente", FacesMessage.SEVERITY_INFO);
                }
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void actualizarTipoCurso(ValueChangeEvent a) {
        String cod = (String) a.getNewValue();
        this.setCursoList(sieniCursoFacadeRemote.findByEstado('A'));
    }

    public void onTimeout() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "BOOM", null));
    }

    public void handleClose(CloseEvent event) {
        setIndexMenu(0);
    }

    public void eliminaRespuesta(SieniEvalRespItem eliminar) {
        this.setEvalRespItemElimina(eliminar);

    }

    public void eliminarRespuesta() {
        try {
            this.getEvalRespItemElimina().setErEstado(new Character('I'));
            sieniEvalRespItemFacadeRemote.edit(this.getEvalRespItemElimina());
            registrarEnBitacora("Eliminar", "Respuesta de Evaluacion", this.getEvalRespItemElimina().getIdEvalRespItem());
            new ValidationPojo().printMsj("Registro eliminado exitosamente", FacesMessage.SEVERITY_INFO);
            fillItemsResp();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }
}
