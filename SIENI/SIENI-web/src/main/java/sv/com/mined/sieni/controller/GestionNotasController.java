/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniEvaluacionFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniNotaFacadeRemote;
import sv.com.mined.sieni.SieniNotasModHistFacadeRemote;
import sv.com.mined.sieni.form.GestionNotasForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniEvaluacion;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniNota;
import sv.com.mined.sieni.model.SieniNotasModHist;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.CopiaArchivos;
import utils.ExcelUtils;
import utils.FormatUtils;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "gestionNotaController")
public class GestionNotasController extends GestionNotasForm {

    @EJB
    private SieniNotaFacadeRemote sieniNotaFacadeRemote;

    @EJB
    private SieniMateriaFacadeRemote sieniMateriaFacadeRemote;

    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;

    @EJB
    private SieniEvaluacionFacadeRemote sieniEvaluacionFacadeRemote;

    @EJB
    private SieniNotasModHistFacadeRemote sieniNotasModHistFacadeRemote;

//docente->curso->materia->grado
//docente->materia->grado->poner notas
    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);
    }

    @PostConstruct
    public void init() {
        this.setNotaNuevo(new SieniNota());
        this.getNotaNuevo().setNtTipoIngreso("M");
        this.setNotaModifica(new SieniNota());
        this.setNotaList(new ArrayList<SieniNota>());
        this.setError(new SieniNota());
        this.getError().setErrores(new ArrayList<String>());
        this.setEvaluacionesList(new ArrayList<SieniEvaluacion>());
        this.setEvaluacionesModificaList(new ArrayList<SieniEvaluacion>());
        this.setMateriasList(new ArrayList());
        this.setMateriasModificaList(new ArrayList());
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

    private List<SieniNota> setAlumnos(List<SieniNota> notas) {
        List<SieniNota> ret = new ArrayList<>();
        for (SieniNota actual : notas) {
            ret.add(setInfoAlumno(actual));
        }
        return ret;
    }

    public SieniNota setInfoAlumno(SieniNota matActual) {
        matActual.setAlumno(sieniAlumnoFacadeRemote.findAlumnoById(matActual.getIdAlumno()));
        return matActual;
    }

    public void cancelaModifica(SieniNota modifica) {
        modifica = sieniNotaFacadeRemote.find(modifica.getIdNota());
        this.setIndexMenu(0);
    }

    private void fill() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        //fill para alumnos
        if (loginBean.getTipoRol().equals("0")) {
            this.setNotaList(setAlumnos(sieniNotaFacadeRemote.findByAlumno(loginBean.getAlumno().getIdAlumno())));
        } else {
            this.setNotaList(setAlumnos(sieniNotaFacadeRemote.findAllNoEliminadas()));
        }
    }

    public void nuevo() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        this.setAlumnosList(sieniAlumnoFacadeRemote.findAlumnosMatriculados(loginBean.getAnioEscolarActivo().getIdAnioEscolar(),loginBean.getAnioEscolarActivo().getAeAnio().toString()));
        if (this.getAlumnosList() != null && !this.getAlumnosList().isEmpty()) {
            this.setMateriasList(sieniMateriaFacadeRemote.findByAlumno(this.getAlumnosList().get(0).getIdAlumno()));
            if (this.getMateriasList() != null && !this.getMateriasList().isEmpty()) {
                this.setIdMateria(this.getMateriasList().get(0));
                this.setEvaluacionesList(sieniEvaluacionFacadeRemote.findIdMateria(this.getMateriasList().get(0).getIdMateria()));
            } else {
                this.setEvaluacionesList(new ArrayList<SieniEvaluacion>());
            }
        }
        this.setIndexMenu(1);
    }

    public synchronized void guardar() {
        try {
            FormatUtils fu = new FormatUtils();
            this.getNotaNuevo().setIdAlumno(this.getIdAlumno().getIdAlumno());
            this.getNotaNuevo().setIdEvaluacion(this.getIdEvaluacion());
            if (validarNuevo(this.getNotaNuevo())) {//valida el guardado
                HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
                this.getNotaNuevo().setNtEstado('A');
                this.getNotaNuevo().setNtFechaIngreso(new Date());
                this.getNotaNuevo().setNtAnio(fu.getFormatedAnioInt(new Date()));
                this.getNotaNuevo().setNtDocente(loginBean.getDocente().getIdDocente());
                registrarEnBitacora("Crear", "Nota", this.getNotaNuevo().getIdNota());
                BigDecimal nota = new BigDecimal(this.getNotaNuevo().getNtCalificacion());
                this.getNotaNuevo().setNtCalificacion(nota.setScale(2, RoundingMode.HALF_UP).doubleValue());
                this.setNotaNuevo(sieniNotaFacadeRemote.createAndReturn(this.getNotaNuevo()));
                new ValidationPojo().printMsj("Nota Creada Exitosamente", FacesMessage.SEVERITY_INFO);
                this.setNotaNuevo(setInfoAlumno(this.getNotaNuevo()));
                this.getNotaList().add(this.getNotaNuevo());
                this.setNotaNuevo(new SieniNota());
                this.getNotaNuevo().setNtTipoIngreso("M");
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void quitarFormato(SieniNota actual) {

    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniNota nuevo) {
        boolean valido;
        List<ValidationPojo> validaciones = new ArrayList<>();
        if (nuevo.getIdEvaluacion() != null && nuevo.getIdEvaluacion().getIdEvaluacion() != null) {
            validaciones.add(new ValidationPojo(sieniNotaFacadeRemote.findNotaRegistrada(nuevo), "La nota de la evaluación para ese alumno ya esta definida", FacesMessage.SEVERITY_ERROR));
        }
        validaciones.add(new ValidationPojo(!(this.getMateriasList() != null && !this.getMateriasList().isEmpty()), "El alumno no tiene materias disponibles", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(!(this.getEvaluacionesList() != null && !this.getEvaluacionesList().isEmpty()), "El alumno no tiene evaluaciones disponibles", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(this.getNotaNuevo() != null && !(this.getNotaNuevo().getNtCalificacion() >= 0.0 && this.getNotaNuevo().getNtCalificacion() <= 10.0), "Nota no valida", FacesMessage.SEVERITY_ERROR));
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniNota modificado) {
        if (verificarAnioEscolar(modificado.getNtFechaIngreso())) {
            //TODO verificar si el docente logueado es el coordinador de la materia
            //si es asi puede modificar todas las notas, sino solo las ingresadas por el
            if (validarUsuarioModificaNota(modificado)) {
                //llena los datos a utilizar en el formulario
                HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
                this.setAlumnosModificaList(sieniAlumnoFacadeRemote.findAlumnosMatriculados(loginBean.getAnioEscolarActivo().getIdAnioEscolar(),loginBean.getAnioEscolarActivo().getAeAnio().toString()));
                this.setMateriasModificaList(sieniMateriaFacadeRemote.findByAlumno(modificado.getIdAlumno()));
                if (this.getMateriasModificaList() != null && !this.getMateriasModificaList().isEmpty()) {
                    this.setEvaluacionesModificaList(sieniEvaluacionFacadeRemote.findByMateria(modificado.getIdEvaluacion().getIdMateria().getIdMateria()));
                }
                this.setIdMateriaModifica(modificado.getIdEvaluacion().getIdMateria());
                this.setNotaModifica(modificado);
                this.setIdAlumnoModifica(sieniAlumnoFacadeRemote.findAlumnoById(modificado.getIdAlumno()));
                this.setIdEvaluacionModifica(modificado.getIdEvaluacion());

                this.setIndexMenu(2);
            }
        }
    }

    //Solo la puede modificar:
    //si es docente, docente que la ingresó, o el coordinador de la materia
    //si es cualquier otro usuario (admin, director, subdirector)
    //los alumnos no pueden modificar
    private boolean validarUsuarioModificaNota(SieniNota modificado) {
        boolean ban = true;
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");

        if (!loginBean.getTipoUsuario().equals("A")) {
            switch (loginBean.getTipoRol()) {
                case "0":
                    //alumno no tiene permiso
                    ban = false;
                    new ValidationPojo().printMsj("Los alumnos no pueden modificar notas", FacesMessage.SEVERITY_ERROR);
                    break;
                case "1":
                    //si es el coordinador o el que ingresó la nota puede editarla
                    SieniMateria m = sieniMateriaFacadeRemote.find(modificado.getIdEvaluacion().getIdMateria().getIdMateria());
                    if (m.getMaCoordinador() != null && modificado.getNtDocente() != null && !(modificado.getNtDocente().equals(loginBean.getDocente().getIdDocente()) || m.getMaCoordinador().equals(loginBean.getDocente().getIdDocente()))) {
                        ban = false;
                    }
                    break;
                //si es cualquier usuario superior al docente, puede editar la nota
                case "2":
                case "3":
                    ban = true;
                    break;
            }
        } else {
            //los alumnos no pueden modificar notas aunque tengan permiso superiores a docentes
            new ValidationPojo().printMsj("Los alumnos no pueden modificar notas", FacesMessage.SEVERITY_ERROR);
            ban = false;
        }
        return ban;
    }

    public void ver(SieniNota modificado) {
        this.setNotaModifica(modificado);
        this.setIndexMenu(3);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniNota eliminado) {
        this.setEliminar(eliminado);
    }

    public synchronized void guardarModifica() {
        try {
            this.getNotaModifica().setIdAlumno(this.getIdAlumnoModifica().getIdAlumno());
            this.getNotaModifica().setIdEvaluacion(this.getIdEvaluacionModifica());
            if (validarModifica(this.getNotaModifica())) {//valida el guardado
                BigDecimal nota = new BigDecimal(this.getNotaModifica().getNtCalificacion());
                this.getNotaModifica().setNtCalificacion(nota.setScale(2, RoundingMode.HALF_UP).doubleValue());
                setNotaModHistorial("Modificacion", this.getNotaModifica());
                sieniNotaFacadeRemote.edit(this.getNotaModifica());
                registrarEnBitacora("Modificar", "Nota", this.getNotaModifica().getIdNota());
                new ValidationPojo().printMsj("Nota Modificada Exitosamente", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    private void setNotaModHistorial(String tipo, SieniNota nota) {
        try {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            SieniNota modifica = sieniNotaFacadeRemote.find(nota.getIdNota());
            SieniNotasModHist notaHist = new SieniNotasModHist();
            notaHist.setNtCalificacion(modifica.getNtCalificacion());
            notaHist.setNtFechaMod(new Date());
            notaHist.setNtTipoModificacion(tipo);
            notaHist.setIdNotas(modifica.getIdNota());
            notaHist.setNtAnio(loginBean.getAnioEscolarActivo().getAeAnio());
            notaHist.setNtDocente(loginBean.getDocente().getIdDocente());
            sieniNotasModHistFacadeRemote.create(notaHist);
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void resetModificaForm() {
        this.setNotaModifica(new SieniNota());
    }

    public void cargarDesdeExcel() {
        this.setListaNotasSubidas(new ArrayList<SieniNota>());
        this.setMateriaSubir(null);
        this.setEvaluacionSubir(null);
        
        this.setMateriasExcelList(sieniMateriaFacadeRemote.findAllNoInactivas());
        if (this.getMateriasExcelList() != null && !this.getMateriasExcelList().isEmpty()) {
            this.setIdMateria(this.getMateriasExcelList().get(0));
            this.setEvaluacionesExcelList(sieniEvaluacionFacadeRemote.findByMateria(this.getIdMateria().getIdMateria()));
        } else {
            this.setEvaluacionesExcelList(new ArrayList<SieniEvaluacion>());
        }
        this.setIndexMenu(4);
    }

    public boolean validarModifica(SieniNota nuevo) {
        boolean valido;
        List<ValidationPojo> validaciones = new ArrayList<>();
        SieniNota notaOriginal = sieniNotaFacadeRemote.find(nuevo.getIdNota());
        if (!notaOriginal.getIdAlumno().equals(nuevo.getIdAlumno())) {
            if (nuevo.getIdEvaluacion() != null && nuevo.getIdEvaluacion().getIdEvaluacion() != null) {
                validaciones.add(new ValidationPojo(sieniNotaFacadeRemote.findNotaRegistrada(nuevo), "La nota de la evaluación para ese alumno ya esta definida", FacesMessage.SEVERITY_ERROR));
            }
        }
        validaciones.add(new ValidationPojo(!(this.getMateriasModificaList() != null && !this.getMateriasModificaList().isEmpty()), "El alumno no tiene materias disponibles", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(!(this.getEvaluacionesModificaList() != null && !this.getEvaluacionesModificaList().isEmpty()), "El alumno no tiene evaluaciones disponibles", FacesMessage.SEVERITY_ERROR));
        validaciones.add(new ValidationPojo(this.getNotaModifica().getNtCalificacion() != null && !(this.getNotaModifica().getNtCalificacion() >= 0.0 && this.getNotaModifica().getNtCalificacion() <= 10.0), "Nota no valida", FacesMessage.SEVERITY_ERROR));
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    public synchronized void eliminarNota() {
        try {
            if (verificarAnioEscolar(this.getEliminar().getNtFechaIngreso())) {
                registrarEnBitacora("Eliminar", "Nota", this.getEliminar().getIdNota());
                this.getEliminar().setNtEstado('I');
                setNotaModHistorial("Eliminacion", this.getEliminar());
                sieniNotaFacadeRemote.edit(this.getEliminar());
                this.getNotaList().remove(this.getEliminar());
                new ValidationPojo().printMsj("Registro eliminado exitosamente", FacesMessage.SEVERITY_INFO);
                this.setIndexMenu(0);
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void getMateriasAlumno(ValueChangeEvent a) {
        SieniAlumno cod = (SieniAlumno) a.getNewValue();
        this.setMateriasList(sieniMateriaFacadeRemote.findByAlumno(cod.getIdAlumno()));
        if (this.getMateriasList() != null && !this.getMateriasList().isEmpty()) {
            this.setIdMateria(this.getMateriasList().get(0));
            this.setEvaluacionesList(sieniEvaluacionFacadeRemote.findIdMateria(this.getMateriasList().get(0).getIdMateria()));

        } else {
            this.setEvaluacionesList(new ArrayList<SieniEvaluacion>());
        }
    }

    public void getMateriasAlumnoModifica(ValueChangeEvent a) {
        SieniAlumno cod = (SieniAlumno) a.getNewValue();
        this.setMateriasModificaList(sieniMateriaFacadeRemote.findByAlumno(cod.getIdAlumno()));
        if (this.getMateriasModificaList() != null && !this.getMateriasModificaList().isEmpty()) {
            this.setIdMateriaModifica(this.getMateriasModificaList().get(0));
            this.setEvaluacionesList(sieniEvaluacionFacadeRemote.findIdMateria(this.getMateriasModificaList().get(0).getIdMateria()));
        } else {
            this.setEvaluacionesModificaList(new ArrayList<SieniEvaluacion>());
        }
    }

    public void getSeccionesGrado(ValueChangeEvent a) {
        SieniMateria cod = (SieniMateria) a.getNewValue();
        if (cod != null) {
            this.setEvaluacionesList(sieniEvaluacionFacadeRemote.findIdMateria(cod.getIdMateria()));
        }
    }

    public void getSeccionesGradoExcel(ValueChangeEvent a) {
        SieniMateria cod = (SieniMateria) a.getNewValue();
        if (cod != null) {
            this.setEvaluacionesExcelList(cod.getSieniEvaluacionList());
        }
    }

    public void getSeccionesGradoModifica(ValueChangeEvent a) {
        SieniMateria cod = (SieniMateria) a.getNewValue();
        if (cod != null) {
            this.setEvaluacionesModificaList(sieniEvaluacionFacadeRemote.findIdMateria(cod.getIdMateria()));
        }
    }

    public void getArchivoNuevo(FileUploadEvent event) {
        try {
            InputStream stream = event.getFile().getInputstream();
            ExcelUtils eu = new ExcelUtils(stream);
            eu.setSieniAlumnoFacadeRemote(sieniAlumnoFacadeRemote);
            List<SieniNota> notasActuales = eu.readNotasExcel(0);
            this.setListaNotasSubidas(notasActuales);
            eu.closeWorkbook();
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public synchronized void guardarNotasExcel() {
        try {
            boolean error = false;
            List<SieniNota> notas = new ArrayList<>();
            FormatUtils fu = new FormatUtils();
            Date fechaActual = new Date();
            if (this.getEvaluacionSubir() != null && this.getEvaluacionSubir().getIdEvaluacion() != null) {
                HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
                for (SieniNota actual : this.getListaNotasSubidas()) {
                    actual.setIdEvaluacion(this.getEvaluacionSubir());
                    actual.setNtEstado('A');
                    actual.setNtFechaIngreso(fechaActual);
                    actual.setNtTipoIngreso("E");
                    actual.setNtDocente(loginBean.getDocente().getIdDocente());
                    actual.setNtAnio(fu.getFormatedAnioInt(new Date()));
                    BigDecimal nota = new BigDecimal(actual.getNtCalificacion());
                    actual.setNtCalificacion(nota.setScale(2, RoundingMode.HALF_UP).doubleValue());
                    notas.add(actual);
                    if (actual.getErrores() != null && !actual.getErrores().isEmpty()) {
                        error = true;
                        break;
                    }
                }
                if (!error) {
                    for (int i = 0; i < notas.size(); i++) {
                        notas.get(i).setErrores(new ArrayList<String>());
                        if (sieniNotaFacadeRemote.findNotaRegistrada(notas.get(i))) {
                            notas.get(i).getErrores().add("La nota de la evaluación para ese alumno ya esta definida");
                            error = true;
                            List<ValidationPojo> errores = new ArrayList<>();
                            errores.add(new ValidationPojo(error, "Debe corregir los errores del archivo excel antes de guardar", FacesMessage.SEVERITY_ERROR));
                            ValidationPojo.printErrores(errores);
                        }
                    }
                    if (!error) {
                        sieniNotaFacadeRemote.merge(notas);
                        registrarEnBitacora("Crear", "Nota - excel", this.getEvaluacionSubir().getIdEvaluacion());
                        new ValidationPojo().printMsj("Notas creadas Exitosamente", FacesMessage.SEVERITY_INFO);
                    }
                } else {
                    List<ValidationPojo> errores = new ArrayList<>();
                    errores.add(new ValidationPojo(error, "Debe corregir los errores del archivo excel antes de guardar", FacesMessage.SEVERITY_ERROR));
                    ValidationPojo.printErrores(errores);
                }
            } else {
                List<ValidationPojo> errores = new ArrayList<>();
                errores.add(new ValidationPojo(true, "La materia no tiene evaluaciones disponibles", FacesMessage.SEVERITY_ERROR));
                ValidationPojo.printErrores(errores);
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void mostrarErrores(SieniNota nota) {
        this.setError(nota);
    }
    
    public void limpiarNotasExcel(){
        this.setListaNotasSubidas(new ArrayList<SieniNota>());
        this.setMateriaSubir(null);
        this.setEvaluacionSubir(null);
        
        this.setMateriasExcelList(sieniMateriaFacadeRemote.findAllNoInactivas());
        if (this.getMateriasExcelList() != null && !this.getMateriasExcelList().isEmpty()) {
            this.setIdMateria(this.getMateriasExcelList().get(0));
            this.setEvaluacionesExcelList(sieniEvaluacionFacadeRemote.findByMateria(this.getIdMateria().getIdMateria()));
        } else {
            this.setEvaluacionesExcelList(new ArrayList<SieniEvaluacion>());
        }
    }
    
    
    
    
    
    
    /* IMPORTAR EXCEL */
    private StreamedContent filePlantilla;
    
    public StreamedContent getFilePlantilla() {
        filePlantilla = null;
        String ruthPath = null;
        try{
            if (this.getEvaluacionSubir() != null && this.getEvaluacionSubir().getIdEvaluacion() != null) {
                // Se crea el libro
                XSSFWorkbook libro = new XSSFWorkbook();
                // Se crea una hoja dentro del libro
                XSSFSheet sheetD = libro.createSheet();
                //Obtener lista de alumnos del curso
                List<SieniAlumno> alumnosEval = sieniAlumnoFacadeRemote.findAlumnosInscritos(this.getEvaluacionSubir().getIdCurso().getIdCurso());
                //Leer datos y colocarlos en la hoja
                int f = 0;
                //Guardar datos en celda
                for(SieniAlumno alumno : alumnosEval){
                    // Se crea una fila dentro de la hoja
                    XSSFRow fila = sheetD.createRow(f);f++;
                    // Se crea las celdas dentro de la fila
                    XSSFCell celdaCarnet = fila.createCell((short) 0);
                    XSSFCell celdaAlumno = fila.createCell((short) 1);
                    XSSFCell celdaNota = fila.createCell((short) 2);
                    //Colocar valor en celda
                    celdaCarnet.setCellValue(alumno.getAlCarnet());
                    celdaAlumno.setCellValue(alumno.getNombreCompleto());
                    celdaNota.setCellValue((double)0.00);
                }
                //Encabezados desde plantilla
                InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/resources/templates/PlantillaAlumnosEval.xlsx");
                StreamedContent plantillaXLS = new DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Plantilla.xlsx");
                XSSFWorkbook plantilla = new XSSFWorkbook(plantillaXLS.getStream());
                XSSFSheet sheetP = plantilla.getSheetAt(0);
                
                //Filas que ocupa el encabezado de plantilla
                int encabezado = 3;
                //Quitar encabezado y desplazar Datos
                sheetD.shiftRows(0, sheetD.getLastRowNum(), encabezado);
                //Copiar contenido de plantilla a la hoja del reporte
                int inicio = 0;
                for (int row = 0; row < encabezado; row++) {
                    copyRow(sheetP, sheetD, row, inicio);
                    inicio++;
                }
                //Combinar las columnas al igual que la plantilla
                for (int m = 0; m < sheetP.getNumMergedRegions(); m++) {
                        CellRangeAddress cellRangeAddress = sheetP.getMergedRegion(m).copy();
                        sheetD.addMergedRegion(cellRangeAddress);
                }
                //Evaluacion
                XSSFCell celdaEval = sheetD.getRow(0).getCell(1);
                celdaEval.setCellValue(this.getEvaluacionSubir().getEvNombre());
                // Se salva el libro.
                FileOutputStream elFichero = new FileOutputStream("ListaAlumnos.xlsx");
                libro.write(elFichero);
                elFichero.close();
                //Leer libro para descarga
                FileInputStream file = new FileInputStream(new File("ListaAlumnos.xlsx"));
                filePlantilla = new DefaultStreamedContent(file, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "AlumnosEval.xlsx");
	
            }else{
                new ValidationPojo().printMsj("Seleccione una evaluacion", FacesMessage.SEVERITY_ERROR);
            }
        }catch(Exception exc){ new ValidationPojo().printMsj("Ocurrio un error al descargar plantilla ... consulte con el administrador" + ruthPath, FacesMessage.SEVERITY_ERROR); }
        return filePlantilla;
    }
    
    
    
    public static void copyRow(XSSFSheet worksheetSource, XSSFSheet worksheetDestination, int sourceRowNum, int destinationRowNum) {
        // Get the source / new row
        XSSFRow origen = worksheetSource.getRow(sourceRowNum);
        XSSFRow destino = worksheetDestination.createRow(destinationRowNum);
        
        // Loop through source columns to add to new row
        for (int i = 0; i < origen.getLastCellNum(); i++) {
            // Grab a copy of the old/new cell
            XSSFCell oldCell = origen.getCell(i);
            XSSFCell newCell = destino.createCell(i);
            // If the old cell is null jump to next cell
            if (oldCell == null) {
                newCell = null;
                continue;
            }
            
            //Ajustar tamaños columnas
            worksheetDestination.setColumnWidth(i, worksheetSource.getColumnWidth(i));
                       
            // Copy style from old cell and apply to new cell
            XSSFCellStyle newCellStyle = newCell.getSheet().getWorkbook().createCellStyle();
            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
            newCell.setCellStyle(newCellStyle);

            // If there is a cell comment, copy
            if (oldCell.getCellComment() != null) {
                newCell.setCellComment(oldCell.getCellComment());
            }
             

            // If there is a cell hyperlink, copy
            if (oldCell.getHyperlink() != null) {
                newCell.setHyperlink(oldCell.getHyperlink());
            }

            // Set the cell data type
            newCell.setCellType(oldCell.getCellType());
            // Set the cell data value
            switch (oldCell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    newCell.setCellValue(oldCell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    newCell.setCellValue(oldCell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_ERROR:
                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    newCell.setCellFormula(oldCell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    newCell.setCellValue(oldCell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    newCell.setCellValue(oldCell.getRichStringCellValue());
                    break;
            }
            
            
            
        }
    
    }
    
    
    
    
    
    
    
}
