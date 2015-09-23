/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.io.InputStream;
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
import org.primefaces.event.FileUploadEvent;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniCursoFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniMatriculaFacadeRemote;
import sv.com.mined.sieni.SieniNotaFacadeRemote;
import sv.com.mined.sieni.form.GestionNotasForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniEvaluacion;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniNota;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
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
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @EJB
    private SieniMateriaFacadeRemote sieniMateriaFacadeRemote;

    @EJB
    private SieniMatriculaFacadeRemote sieniMatriculaFacadeRemote;

    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;

    @EJB
    private SieniCursoFacadeRemote sieniCursoFacadeRemote;
//docente->curso->materia->grado
//docente->materia->grado->poner notas

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

    private void fill() {
        //findNotasByMateriasDocente
        this.setNotaList(sieniNotaFacadeRemote.findAllNoEliminadas());
        //findAlumnosByMateriasDocente
        this.setAlumnosList(sieniAlumnoFacadeRemote.findAll());
        this.setAlumnosModificaList(sieniAlumnoFacadeRemote.findAll());
        if (this.getAlumnosList() != null && !this.getAlumnosList().isEmpty()) {
            //findMateriasByAlumnoDocente
            this.setMateriasList(sieniMateriaFacadeRemote.findByAlumno(this.getAlumnosList().get(0).getIdAlumno()));
            if (this.getMateriasList() != null && !this.getMateriasList().isEmpty()) {
                this.setIdMateria(this.getMateriasList().get(0));
                this.setEvaluacionesList(this.getIdMateria().getSieniEvaluacionList());
            } else {
                this.setEvaluacionesList(new ArrayList<SieniEvaluacion>());
            }
        }
        if (this.getAlumnosModificaList() != null && !this.getAlumnosModificaList().isEmpty()) {
            this.setMateriasModificaList(sieniMateriaFacadeRemote.findByAlumno(this.getAlumnosModificaList().get(0).getIdAlumno()));
            if (this.getMateriasModificaList() != null && !this.getMateriasModificaList().isEmpty()) {
                this.setIdMateriaModifica(this.getMateriasModificaList().get(0));
                this.setEvaluacionesModificaList(this.getIdMateriaModifica().getSieniEvaluacionList());
            } else {
                this.setEvaluacionesModificaList(new ArrayList<SieniEvaluacion>());
            }
        }
    }

    public void guardar() {
        FormatUtils fu = new FormatUtils();
        this.getNotaNuevo().setIdAlumno(this.getIdAlumno());
        this.getNotaNuevo().setIdEvaluacion(this.getIdEvaluacion());
        if (validarNuevo(this.getNotaNuevo())) {//valida el guardado
            this.getNotaNuevo().setNtEstado(new Character('A'));
            this.getNotaNuevo().setNtAnio(fu.getFormatedAnioInt(new Date()));
            sieniNotaFacadeRemote.create(this.getNotaNuevo());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Nota", this.getNotaNuevo().getIdNota(), 'D'));
            FacesMessage msg = new FacesMessage("Nota Creada Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            init();
        }
    }

    public void quitarFormato(SieniNota actual) {

    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniNota nuevo) {
        boolean ban = true;
        //nota ya registrada
        return ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniNota modificado) {
        this.setNotaModifica(modificado);
        this.setIdAlumnoModifica(modificado.getIdAlumno());
        this.setIdEvaluacion(modificado.getIdEvaluacion());
        this.setMateria(modificado.getIdEvaluacion().getIdMateria());
        this.setMateriasModificaList(sieniMateriaFacadeRemote.findByAlumno(modificado.getIdAlumno().getIdAlumno()));
        if (this.getMateriasModificaList() != null && !this.getMateriasModificaList().isEmpty()) {
            this.setIdMateriaModifica(this.getMateriasModificaList().get(0));
            this.setEvaluacionesModificaList(this.getIdMateriaModifica().getSieniEvaluacionList());
        } else {
            this.setEvaluacionesModificaList(new ArrayList<SieniEvaluacion>());
        }
        this.setIndexMenu(2);
    }

    public void ver(SieniNota modificado) {
//        this.getNotaNuevo().setIdAlumno(this.getIdAlumno());
//        this.getNotaNuevo().setIdEvaluacion(this.getIdEvaluacion());
        this.setNotaModifica(modificado);
        this.setIndexMenu(3);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniNota eliminado) {
        this.setEliminar(eliminado);
    }

    public void guardarModifica() {
        this.getNotaModifica().setIdAlumno(this.getIdAlumnoModifica());
        this.setIdEvaluacion(this.getIdEvaluacionModifica());
        if (validarModifica(this.getNotaModifica())) {//valida el guardado
            sieniNotaFacadeRemote.edit(this.getNotaModifica());
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modificar", "Nota", this.getNotaNuevo().getIdNota(), 'D'));
            FacesMessage msg = new FacesMessage("Nota Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            resetModificaForm();
            init();
        }

    }

    public void resetModificaForm() {
        this.setNotaModifica(new SieniNota());
    }

    public boolean validarModifica(SieniNota nuevo) {
        boolean ban = true;

        return ban;
    }

    public void eliminarNota() {
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Nota", this.getNotaNuevo().getIdNota(), 'D'));
        this.getEliminar().setNtEstado('I');
        sieniNotaFacadeRemote.edit(this.getEliminar());
        fill();
        this.setIndexMenu(0);
    }

    public void getMateriasAlumno(ValueChangeEvent a) {
        SieniAlumno cod = (SieniAlumno) a.getNewValue();
        this.setMateriasList(sieniMateriaFacadeRemote.findByAlumno(cod.getIdAlumno()));
        if (this.getMateriasList() != null && !this.getMateriasList().isEmpty()) {
            this.setIdMateria(this.getMateriasList().get(0));
            this.setEvaluacionesList(this.getIdMateria().getSieniEvaluacionList());
        } else {
            this.setEvaluacionesList(new ArrayList<SieniEvaluacion>());
        }
    }

    public void getMateriasAlumnoModifica(ValueChangeEvent a) {
        SieniAlumno cod = (SieniAlumno) a.getNewValue();
        this.setMateriasModificaList(sieniMateriaFacadeRemote.findByAlumno(cod.getIdAlumno()));
        if (this.getMateriasModificaList() != null && !this.getMateriasModificaList().isEmpty()) {
            this.setIdMateriaModifica(this.getMateriasModificaList().get(0));
            this.setEvaluacionesModificaList(this.getIdMateriaModifica().getSieniEvaluacionList());
        } else {
            this.setEvaluacionesModificaList(new ArrayList<SieniEvaluacion>());
        }
    }

    public void getSeccionesGrado(ValueChangeEvent a) {
        SieniMateria cod = (SieniMateria) a.getNewValue();
        this.setEvaluacionesList(cod.getSieniEvaluacionList());
    }

    public void getSeccionesGradoModifica(ValueChangeEvent a) {
        SieniMateria cod = (SieniMateria) a.getNewValue();
        this.setEvaluacionesModificaList(cod.getSieniEvaluacionList());
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

        }
    }

    public void guardarNotasExcel() {
        boolean error = false;
        List<SieniNota> notas = new ArrayList<>();
        FormatUtils fu = new FormatUtils();
        for (SieniNota actual : this.getListaNotasSubidas()) {
            actual.setIdEvaluacion(this.getEvaluacionSubir());
            actual.setNtEstado(new Character('A'));
            actual.setNtTipoIngreso("E");
            actual.setNtAnio(fu.getFormatedAnioInt(new Date()));
            notas.add(actual);
            if (actual.getErrores() != null && !actual.getErrores().isEmpty()) {
                error = true;
                break;
            }
        }
        if (!error) {
//            for(SieniNota actual:notas){
//                if(!validarNuevo(actual)){
//                    
//                }
//            }
            sieniNotaFacadeRemote.merge(notas);
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Nota", this.getNotaNuevo().getIdNota(), 'D'));
            FacesMessage msg = new FacesMessage("Nota Creada Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            fill();
        } else {
            List<ValidationPojo> errores = new ArrayList<>();
            errores.add(new ValidationPojo(error, "Debe corregir los errores del archivo excel antes de guardar", FacesMessage.SEVERITY_ERROR));
            ValidationPojo.printErrores(errores);
        }
    }

    public void mostrarErrores(SieniNota nota) {
        this.setError(nota);
    }
    //    //algoritmo para crear listado de alumnos a buscar en base al nombre
//    public void buscarAlumnos(List<SieniAlumno> nombres) {
//        List<SieniAlumno> posiblilidades = new ArrayList<SieniAlumno>();
//        HashMap<String, List<SieniAlumno>> res = new HashMap<String, List<SieniAlumno>>();
//        for (SieniAlumno nombre : nombres) {
//            String cad = nombre.getNombreCompleto();
//            String partes[] = cad.split(" ");
//            SieniAlumno al;
//            List<String> partesUtiles = new ArrayList<String>();
//            for (String actual : partes) {
//                if (actual != null) {
//                    partesUtiles.add(actual);
//                }
//            }
//            int j = 0, max = 3;//3 nombres y 3 apellidos
//            if (partesUtiles.size() <= 3) {
//                max = partesUtiles.size() - 1;//evitar que se quede sin apellidos
//            }
//            for (int i = 1; i <= max; i++) {//nombres
//                al = new SieniAlumno();
//                j = partesUtiles.size() - i;
//                if (i >= 1) {
//                    al.setAlPrimNombre(partesUtiles.get(i - 1));
//                }
//                if (i >= 2) {
//                    al.setAlSeguNombre(partesUtiles.get(i - 1));
//                }
//                if (i >= 3) {
//                    al.setAlSeguNombre(partesUtiles.get(i - 1));
//                }
//                if (j >= 1) {
//                    al.setAlPrimApe(partesUtiles.get(i));
//                }
//                if (j >= 2) {
//                    al.setAlSeguApe(partesUtiles.get(i + 1));//cruz
//                }
//                if (j >= 3) {
//                    al.setAlTercApe(partesUtiles.get(i + 2));
//                }
//                posiblilidades.add(al);
//            }
//            res.put(cad, posiblilidades);
//        }
////        sieniAlumnoFacadeRemote.alumnoRegistrado(null)
//    }
}
