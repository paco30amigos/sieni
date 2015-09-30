/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.DragDropEvent;
import sv.com.mined.sieni.SieniArchivoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniClaseFacadeRemote;
import sv.com.mined.sieni.SieniElemPlantillaFacadeRemote;
import sv.com.mined.sieni.SieniInteEntrCompFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniPlantillaFacadeRemote;
import sv.com.mined.sieni.SieniSuperComponFacadeRemote;
import sv.com.mined.sieni.SieniTipoElemPlantillaFacadeRemote;
import sv.com.mined.sieni.form.GestionClaseInteracForm;
import sv.com.mined.sieni.model.SieniArchivo;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniComponente;
import sv.com.mined.sieni.model.SieniElemPlantilla;
import sv.com.mined.sieni.model.SieniPlantilla;
import sv.com.mined.sieni.model.SieniSuperCompon;
import sv.com.mined.sieni.pojos.controller.ComponenteInteractivoPojo;
import sv.com.mined.sieni.pojos.controller.FileStreamedPojo;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.CopiaArchivos;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "gestionClaseInteracController")
public class GestionClaseInteracController extends GestionClaseInteracForm {

    @EJB
    private SieniPlantillaFacadeRemote sieniPlantillaFacadeRemote;
    @EJB
    private SieniElemPlantillaFacadeRemote sieniElemPlantillaFacadeRemote;
    @EJB
    private SieniTipoElemPlantillaFacadeRemote sieniTipoElemPlantillaFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;
    @EJB
    private SieniMateriaFacadeRemote sieniMateriaFacadeRemote;
    @EJB
    private SieniClaseFacadeRemote sieniClaseFacadeRemote;
    @EJB
    private SieniSuperComponFacadeRemote sieniSuperComponFacadeRemote;
    @EJB
    private SieniInteEntrCompFacadeRemote sieniInteEntrCompFacadeRemote;
    @EJB
    private SieniArchivoFacadeRemote sieniArchivoFacadeRemote;

    @PostConstruct
    public void init() {
        fill();
    }

    private void fill() {
        //*******fill
        //clases interact
        this.setClaseList(sieniClaseFacadeRemote.findClaseByTipo('I'));//clases interactivas
        this.setListaSuper(new ArrayList());
    }

    public void configurar(SieniClase clase) {
        this.setClaseConfig(clase);
        if (clase.getClAlto() == null) {
            clase.setClAlto(600);
        }
        if (clase.getClAncho() == null) {
            clase.setClAncho(800);
        }
        //******config
        //plantillas por materia
        this.setPlantillaList(sieniPlantillaFacadeRemote.findByMateria(clase.getIdCurso().getIdMateria().getIdMateria()));
        //super componentes by clase
        CopiaArchivos ca = new CopiaArchivos();
        ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
        this.setSuperCompList(sieniSuperComponFacadeRemote.findByClase(clase.getIdClase()));
        this.setListaSuper(new ArrayList<ComponenteInteractivoPojo>());
        for (SieniSuperCompon actual : this.getSuperCompList()) {
            ComponenteInteractivoPojo nuevo = new ComponenteInteractivoPojo();
            nuevo.setSuperComp(actual);
            nuevo.setData(new ArrayList());
            FileStreamedPojo dataNuevo;
            //si es archivo multimedia
            for (SieniComponente comp : actual.getSieniComponenteList()) {
                dataNuevo = new FileStreamedPojo();
                dataNuevo.setComponente(comp);
                List<SieniArchivo> archivos = sieniArchivoFacadeRemote.findByIdSuperComp(actual.getIdSuperCompon());
                if (archivos != null && !archivos.isEmpty()) {
                    for (SieniArchivo arch : archivos) {
                        dataNuevo.setArchivoBD(arch);
                        if (arch.getArTipo().equals(new Character('I'))
                                || arch.getArTipo().equals(new Character('V'))
                                || arch.getArTipo().equals(new Character('A'))) {
                            ca.copyDataToResource(arch);
                        } else {
                            nuevo.setTexto(new String(ca.getData(arch)));
                        }
                    }
                } else {
                    dataNuevo.setArchivoBD(new SieniArchivo());
                }
                nuevo.getData().add(dataNuevo);
            }
            this.getListaSuper().add(nuevo);

        }
        //interacciones entre componentes by clase
        this.setInteEntrCompList(sieniInteEntrCompFacadeRemote.findByClase(clase.getIdClase()));
        //********información
        this.setIndexMenu(4);
//        this.setMaterias(sieniMateriaFacadeRemote.findAll());
    }

    public void guardar() {
//        if (validarNuevo(this.getPlantillaNuevo())) {//valida el guardado
//            sieniPlantillaFacadeRemote.create(this.getPlantillaNuevo());
//            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
//            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Plantilla", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
//            FacesMessage msg = new FacesMessage("Plantilla Creado Exitosamente");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//            this.setPlantillaNuevo(new SieniPlantilla());
//            fill();
//        }
    }

    public void quitarFormato(SieniPlantilla actual) {

    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniPlantilla nuevo) {
        boolean ban = true;
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        ban = ValidationPojo.printErrores(validaciones);
        return !ban;
    }

    public void cancelar() {
    }

    //metodos para modificacion de datos
    public void modificar(SieniClase modificado) {
        this.setPlantillaModificaList(sieniPlantillaFacadeRemote.findByMateria(modificado.getIdCurso().getIdMateria().getIdMateria()));
        this.setClaseModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniClase eliminado) {
        this.setEliminar(eliminado);
    }

    //metodos para modificacion de datos
    public void mostrar(SieniClase ver) {
        this.setVer(ver);
        this.setIndexMenu(3);
    }

    public void guardarModifica() {

        if (validarModifica(this.getClaseModifica())) {//valida el guardado
            sieniClaseFacadeRemote.edit(this.getClaseModifica());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modifica", "Plantilla", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
            FacesMessage msg = new FacesMessage("Plantilla Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            fill();
        }
    }

    public void resetModificaForm() {
        this.setClaseModifica(new SieniClase());
    }

    public void resetNuevoForm() {
//        this.setPlantillaNuevo(new SieniPlantilla());

    }

    public boolean validarModifica(SieniClase nuevo) {
        boolean ban = true;
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        return ban;
    }

    public void eliminarClase() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Plantilla", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
        this.getEliminar().setClEstado('I');
        sieniClaseFacadeRemote.edit(this.getEliminar());
        fill();
    }

//    public void configurar(SieniPlantilla plantilla) {
//        fillElemPlantillaPlantilla(plantilla);
//        this.setIndexMenu(4);
//    }

    public void agregarElemPlantilla() {
//        SieniElemPlantilla nuevo = new SieniElemPlantilla();
//        nuevo.setIdPlantilla(this.getPlantillaModifica());
//        nuevo.setIdTipoElemPlantilla(this.getNuevoElem());
//        nuevo.setEpEstado('A');
//        nuevo.setIdElemPlantilla(-Long.parseLong(new DateUtils().getTime()));
//        this.getElemPlantillaSelected().add(nuevo);
//        for (SieniElemPlantilla actual : this.getElemPlantillaSelected()) {
//            for (int i = 0; i < getTipoPlantilla().size(); i++) {
//                if (actual.getIdTipoElemPlantilla().getIdTipoElemPlantilla().equals(getTipoPlantilla().get(i).getIdTipoElemPlantilla())) {
//                    getTipoPlantilla().remove(i);
//                }
//            }
//        }
    }

    public void guardarElemPlantilla() {
//        sieniElemPlantillaFacadeRemote.merge(this.getElemPlantillaSelected(), this.getElemPlantillaEliminados());
//        FacesMessage msg = new FacesMessage("Elementos de plantilla guardados exitosamente");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        fillElemPlantillaPlantilla(this.getPlantillaModifica());
    }

    public void eliminarElemPlantilla() {
//        SieniElemPlantilla materia = this.getElemPlantillaEliminado();
//        for (int i = 0; i < this.getElemPlantillaSelected().size(); i++) {
//            if (this.getElemPlantillaSelected().get(i).getIdElemPlantilla().equals(materia.getIdElemPlantilla())) {
//                this.getElemPlantillaEliminados().add(this.getElemPlantillaSelected().get(i));
//                this.getTipoPlantilla().add(this.getElemPlantillaSelected().get(i).getIdTipoElemPlantilla());
//                this.getElemPlantillaSelected().remove(i);
//                break;
//            }
//        }
    }

    public void eliminarElemPlantilla_(SieniElemPlantilla materia) {
//        this.setElemPlantillaEliminado(materia);
    }

    public void fillElemPlantillaPlantilla(SieniPlantilla plantilla) {
//        this.setPlantillaModifica(plantilla);
//        this.setTipoPlantilla(sieniTipoElemPlantillaFacadeRemote.findAll());
//        this.setNuevoElem(new SieniTipoElemPlantilla());
//        this.setElemPlantillaEliminados(new ArrayList<SieniElemPlantilla>());
//        this.setElemPlantillaSelected(this.getPlantillaModifica().getSieniElemPlantillaList());
//        if (this.getElemPlantillaSelected() == null) {
//            this.setElemPlantillaSelected(new ArrayList<SieniElemPlantilla>());
//        } else {
//            for (SieniElemPlantilla actual : this.getElemPlantillaSelected()) {
//                for (int i = 0; i < getTipoPlantilla().size(); i++) {
//                    if (actual.getIdTipoElemPlantilla().getIdTipoElemPlantilla().equals(getTipoPlantilla().get(i).getIdTipoElemPlantilla())) {
//                        getTipoPlantilla().remove(i);
//                    }
//                }
//            }
//        }
    }

    public void guardarConfiguracion() {
    }

    public void refreshConfig() {
    }

    public void onDrop(DragDropEvent dragDropEvent) {
        String dargId = dragDropEvent.getDragId();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String left = params.get(dargId + "_left");
        String top = params.get(dargId + "_top");
        String idComponenteString = dargId.split("id_")[1];
        Long idSuperComponente = Long.parseLong(idComponenteString);

        for (int i = 0; i < this.getListaSuper().size(); i++) {
            if (getListaSuper().get(i).getSuperComp().getIdSuperCompon().equals(idSuperComponente)) {
                getListaSuper().get(i).getSuperComp().setScPosX(new Double(Double.parseDouble(left)).intValue());
                getListaSuper().get(i).getSuperComp().setScPosY(new Double(Double.parseDouble(top)).intValue());
                break;
            }
        }
    }
}
