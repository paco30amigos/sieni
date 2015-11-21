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
import sv.com.mined.sieni.SieniEvalRespAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniEvalRespItemFacadeRemote;
import sv.com.mined.sieni.SieniEvaluacionFacadeRemote;
import sv.com.mined.sieni.SieniEvaluacionItemFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniMatriculaFacadeRemote;
import sv.com.mined.sieni.SieniNotaFacadeRemote;
import sv.com.mined.sieni.form.GestionarEvaluacionForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.model.SieniEvalRespAlumno;
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

    @EJB
    private SieniEvalRespAlumnoFacadeRemote sieniEvalRespAlumnoFacadeRemote;

    @EJB
    private SieniNotaFacadeRemote sieniNotaFacadeRemote;

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

    public void guardar() {
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
                this.setEvaluacionNuevo(sieniEvaluacionFacadeRemote.createAndReturn(this.getEvaluacionNuevo()));
                registrarEnBitacora("Crear", "Evaluacion", this.getEvaluacionNuevo().getIdEvaluacion());
                FacesMessage msg = new FacesMessage("Evaluacion Creado Exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                this.setIndexMenu(0);
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
        this.setEvaluacionItemList(new ArrayList<SieniEvaluacionItem>());
        this.setEvaluacionItemList(sieniEvaluacionFacadeRemote.findEvalItemResp(modificado.getIdEvaluacion()).getSieniEvaluacionItemList());
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
            sieniEvaluacionItemFacadeRemote.edit(this.getEvaluacionItemModifica());
            registrarEnBitacora("Modificar", "Evaluacion item", this.getEvaluacionItemModifica().getIdEvaluacionItem());
            new ValidationPojo().printMsj("Item Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
            fillItemsEvaluacion();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void guardarModificaResp() {
        try {

            sieniEvalRespItemFacadeRemote.edit(this.getEvalRespItemModifica());
            registrarEnBitacora("Modificar", "Evaluacion resp item", this.getEvalRespItemModifica().getIdEvalRespItem());
            new ValidationPojo().printMsj("Respuesta Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
            fillItemsResp();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void guardarResAlumno() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        final DataTable d = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("consultaForm:consulta");
        Boolean ultimaPagina = false;
        List<String[]> respuestas = new ArrayList<>();
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
                }

                ultimaPagina = true;
            } else {

                for (int i = inicio; i <= fin; i++) {
                    respuestas.add(req.getParameterValues("name" + (i)));
                }
            }

        } else {
             if (numPagina == totalPag) {
                ultimaPagina = true;
            }
            for (int i = inicio; i <= fin; i++) {
                respuestas.add(req.getParameterValues("name" + (i)));
            }
        }

        this.setEvalRespAlumnoList(new ArrayList<SieniEvalRespAlumno>());

        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");

        for (int j = 0; j < (fin - inicio + 1); j++) {
            SieniEvalRespAlumno respAlumno = new SieniEvalRespAlumno();
            respAlumno.setRaRespuesta(String.join(",", respuestas.get(j)));
            SieniAlumno alumno = new SieniAlumno();
            respAlumno.setIdAlumno(loginBean.getAlumno());
            respAlumno.setIdEvaluacionItem(this.getEvaluacionItemResp().getSieniEvaluacionItemList().get(j));
            respAlumno.setRaEstado('A');
            this.getEvalRespAlumnoList().add(respAlumno);

        }
FacesMessage msg;
        sieniEvalRespAlumnoFacadeRemote.guardarRespuestasAlumno(this.getEvalRespAlumnoList());
        if (ultimaPagina) {
//            calcularNotas(loginBean.getAlumno(), this.getEvaluacionItemResp());
           msg = new FacesMessage("Examen finalizado, su nota es: "+calcularNotas(loginBean.getAlumno(), this.getEvaluacionItemResp()));
        }else{
         msg = new FacesMessage("Se guardaron las respuestas");
        }
       
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public Double calcularNotas(SieniAlumno alumno, SieniEvaluacion evaluacion) {
//        List<Integer> numFalsasAlumno= new ArrayList<Integer>();
//        List<Integer> numVerdaderasAlumno= new ArrayList<Integer>();
//        List<Integer> numFalsas= new ArrayList<Integer>();
//        List<Integer> numVerdaderas= new ArrayList<Integer>();
//        List<Double> notaPregunta=new ArrayList<>();
        int numFalsasAlumno = 0;
        int numVerdaderasAlumno = 0;
        int numFalsas = 0;
        int numVerdaderas = 0;
        List<Double> notaPregunta = new ArrayList<>();
        Double nota = new Double("0.0");
        List<String> respuestas = new ArrayList<>();
        String resAux = "";
        this.setEvalRespAlumnoList(new ArrayList<SieniEvalRespAlumno>());
        this.setEvalRespAlumnoList(sieniEvalRespAlumnoFacadeRemote.findByAlumnoEv(alumno, evaluacion));

//        for (SieniEvaluacionItem item : evaluacion.getSieniEvaluacionItemList()) {
//            for (SieniEvalRespItem res : item.getSieniEvalRespItemList()) {
//                resAux=resAux+(res.getErRespCorrecta())+",";
//            }
//            respuestas.add(resAux);
//            resAux = "";
//        }

        for (int i = 0; i < this.getEvaluacionItemList().size(); i++) {
            for (SieniEvalRespItem res : this.getEvalRespAlumnoList().get(i).getIdEvaluacionItem().getSieniEvalRespItemList()) {
                resAux=resAux+(res.getErRespCorrecta())+",";
            }
            
            numFalsas = contaPalabra("NO", resAux);
            numFalsasAlumno = contaPalabra("NO", this.getEvalRespAlumnoList().get(i).getRaRespuesta());
            numVerdaderas = contaPalabra("SI", resAux);
            numVerdaderasAlumno = contaPalabra("SI", this.getEvalRespAlumnoList().get(i).getRaRespuesta());
            resAux="";
            if (numFalsasAlumno > numVerdaderas) {
                notaPregunta.add(new Double("0.0"));
            } else {
                if (numFalsasAlumno > numVerdaderasAlumno) {
                    notaPregunta.add(new Double("0.0"));
                } else {
//                   Double diferencia=((numVerdaderasAlumno-numFalsasAlumno)/numVerdaderas);
                   notaPregunta.add(( (float) (numVerdaderasAlumno-numFalsasAlumno)/numVerdaderas)*this.getEvalRespAlumnoList().get(i).getIdEvaluacionItem().getEiPonderacion()/10.0);
                }
            }
            
            
        }
        
        for (Double notas : notaPregunta) {
            nota=nota+notas;
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
            this.getEliminar().setEvEstado(new Character('I'));
            sieniEvaluacionFacadeRemote.edit(this.getEliminar());
            fill();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }
}
