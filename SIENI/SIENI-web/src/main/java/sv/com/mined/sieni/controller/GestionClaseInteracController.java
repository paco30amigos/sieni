/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.TabChangeEvent;
import sv.com.mined.sieni.SieniArchivoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniClaseFacadeRemote;
import sv.com.mined.sieni.SieniClaseSupCompFacadeRemote;
import sv.com.mined.sieni.SieniCompInteraccionFacadeRemote;
import sv.com.mined.sieni.SieniComponenteFacadeRemote;
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
import sv.com.mined.sieni.model.SieniClaseSupComp;
import sv.com.mined.sieni.model.SieniCompInteraccion;
import sv.com.mined.sieni.model.SieniComponente;
import sv.com.mined.sieni.model.SieniElemPlantilla;
import sv.com.mined.sieni.model.SieniEvento;
import sv.com.mined.sieni.model.SieniInteEntrComp;
import sv.com.mined.sieni.model.SieniPlantilla;
import sv.com.mined.sieni.model.SieniSuperCompon;
import sv.com.mined.sieni.pojos.controller.CodigoComponente;
import sv.com.mined.sieni.pojos.controller.CodigoEvento;
import sv.com.mined.sieni.pojos.controller.ComponenteInteractivoPojo;
import sv.com.mined.sieni.pojos.controller.EventosPojo;
import sv.com.mined.sieni.pojos.controller.FileStreamedPojo;
import sv.com.mined.sieni.pojos.controller.InteraccionesCompPojo;
import sv.com.mined.sieni.pojos.controller.PantallaPojo;
import sv.com.mined.sieni.pojos.controller.SeccionPlantillaPojo;
import sv.com.mined.sieni.pojos.controller.SuperCompPojo;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.CopiaArchivos;
import utils.DateUtils;

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
    private SieniClaseSupCompFacadeRemote sieniClaseSupCompFacadeRemote;
    @EJB
    private SieniInteEntrCompFacadeRemote sieniInteEntrCompFacadeRemote;
    @EJB
    private SieniArchivoFacadeRemote sieniArchivoFacadeRemote;
    @EJB
    private SieniComponenteFacadeRemote sieniComponenteFacadeRemote;
    @EJB
    private SieniCompInteraccionFacadeRemote sieniCompInteraccionFacadeRemote;

    @PostConstruct
    public void init() {
        this.setPantallasEliminadas(new ArrayList<PantallaPojo>());
        this.setComponentesEliminados(new ArrayList<ComponenteInteractivoPojo>());
        this.setNuevoComponente(new SieniClaseSupComp());
        this.setEvn1(new ArrayList<SieniEvento>());
        this.setEvn2(new ArrayList<SieniEvento>());
        this.setCompDisponibles(new ArrayList<SieniSuperCompon>());
        this.setNuevaInterac(new SieniInteEntrComp());
        fill();
    }

    private void fill() {
        //*******fill
        //clases interact
        this.setClaseList(sieniClaseFacadeRemote.findClaseByTipo('I'));//clases interactivas
    }

    public String getNombreSeccion(Long idTipoElemPlantilla, List<SieniElemPlantilla> elemPlantilla) {
        String ret = "";
        for (SieniElemPlantilla actual : elemPlantilla) {
            if (idTipoElemPlantilla.equals(actual.getIdTipoElemPlantilla().getIdTipoElemPlantilla())) {
                ret = actual.getIdTipoElemPlantilla().getTeDescripcion();
            }
        }
        return ret;
    }

    public List<SeccionPlantillaPojo> getSeccionesByElemPlantilla(List<SieniClaseSupComp> componentes, List<SieniElemPlantilla> elemsPlantilla) {
        List<SeccionPlantillaPojo> ret = new ArrayList();
        HashMap<Long, List<SieniClaseSupComp>> elemPlantillaAux = new HashMap();
        List<Long> elemPlantillaDiferentes = new ArrayList<>();
        SeccionPlantillaPojo nuevo;
        for (SieniClaseSupComp componActual : componentes) {
            for (SieniElemPlantilla elemPlantilla : elemsPlantilla) {
                if (componActual.getIdTipoElemPlantilla().getIdTipoElemPlantilla().equals(elemPlantilla.getIdTipoElemPlantilla().getIdTipoElemPlantilla())) {
                    if (!elemPlantillaAux.containsKey(elemPlantilla.getIdTipoElemPlantilla().getIdTipoElemPlantilla())) {
                        //agrega el componente a la lista, para ese elemento de plantilla
                        elemPlantillaAux.put(elemPlantilla.getIdTipoElemPlantilla().getIdTipoElemPlantilla(), new ArrayList<SieniClaseSupComp>());
                        //ingresa el componente actual
                        elemPlantillaAux.get(elemPlantilla.getIdTipoElemPlantilla().getIdTipoElemPlantilla()).add(componActual);
                        // lo agrega a la lista de elementos diferentes
                        elemPlantillaDiferentes.add(elemPlantilla.getIdTipoElemPlantilla().getIdTipoElemPlantilla());
                    } else {
                        //si el elemento de plantilla ya estÃƒÆ’Ã‚Â¡ registrado, agrega a la lista el componente
                        elemPlantillaAux.get(elemPlantilla.getIdTipoElemPlantilla().getIdTipoElemPlantilla()).add(componActual);
                    }
                }
            }
        }

        for (SieniElemPlantilla actual : elemsPlantilla) {
            nuevo = new SeccionPlantillaPojo();
            nuevo.setIdElemPlantilla(actual);
            nuevo.setNombre(actual.getIdTipoElemPlantilla().getTeDescripcion());
            nuevo.setPantallas(getPantallas(elemPlantillaAux.get(actual.getIdTipoElemPlantilla().getIdTipoElemPlantilla())));
            ret.add(nuevo);
        }
        return ret;
    }

    public List<PantallaPojo> getPantallas(List<SieniClaseSupComp> componentes) {
        HashMap<Integer, List<SieniClaseSupComp>> pantallasAux = new HashMap();
        List<Integer> pantallasDiferentes = new ArrayList<>();
        List<PantallaPojo> ret = new ArrayList<>();
        PantallaPojo nuevo;

        if (componentes != null) {
            for (SieniClaseSupComp actual : componentes) {
                if (!pantallasAux.containsKey(actual.getScNPantalla())) {
                    //incializa el hashmap
                    pantallasAux.put(actual.getScNPantalla(), new ArrayList<SieniClaseSupComp>());
                    //ingresa el componente actual
                    pantallasAux.get(actual.getScNPantalla()).add(actual);
                    //agrega al listado de diferentes pantallas
                    pantallasDiferentes.add(actual.getScNPantalla());
                } else {
                    //si la pantalla ya estÃƒÆ’Ã‚Â¡ registrada, agrega a la lista el componente
                    pantallasAux.get(actual.getScNPantalla()).add(actual);
                }
            }
            for (Integer actual : pantallasDiferentes) {
                nuevo = new PantallaPojo();
                nuevo.setNumPantalla(actual);
                nuevo.setComponentes(getComponentesInteractivos(pantallasAux.get(actual)));
                ret.add(nuevo);
            }
        } else {//si no hay componentes, se agrega una pantalla en blanco
            nuevo = new PantallaPojo();
            nuevo.setNumPantalla(1);
            nuevo.setComponentes(new ArrayList<ComponenteInteractivoPojo>());
            ret.add(nuevo);
        }

        return ret;
    }

    public void configurar(SieniClase clase) {
        fillConfigura(clase);
        this.setIndexMenu(4);

//        this.setMaterias(sieniMateriaFacadeRemote.findAll());
    }

    public void configurarInterac(SieniClase ver) {
        this.setClaseConfig(ver);
        fillConfigura(ver);
        this.setFuncionJS(getCodigoEventosEntreComp());
        this.setCompDisponibles(getComponentsPantallaActual());
        if (this.getCompDisponibles() != null && !this.getCompDisponibles().isEmpty()) {
            updateEv1(this.getCompDisponibles().get(0).getIdSuperCompon());
            updateEv2(this.getCompDisponibles().get(0).getIdSuperCompon());
        } else {
            this.setEvn1(new ArrayList<SieniEvento>());
            this.setEvn2(new ArrayList<SieniEvento>());
        }
        this.setIndexMenu(5);
    }

    public List<SieniSuperCompon> getComponentsPantallaActual() {
        SeccionPlantillaPojo seccionActual = this.getSecciones().get(this.getIdElemenActive());
        Integer index = seccionActual.getPantallaActual();
        index = index != null ? index : 0;
        List<ComponenteInteractivoPojo> s = seccionActual.getPantallas().get(index).getComponentes();
        List<SieniSuperCompon> compons = getSuperCompDiferentesByTipoElemPlantillaPantalla(s);
        return compons;
    }

    public List<SieniSuperCompon> getSuperCompDiferentesByTipoElemPlantillaPantalla(List<ComponenteInteractivoPojo> s) {
        HashMap<Long, Long> diferentes = new HashMap<>();
        List<SieniSuperCompon> ret = new ArrayList<>();
        for (ComponenteInteractivoPojo actual : s) {
            if (!diferentes.containsKey(actual.getClaseSuperComp().getFCompSuperCompon().getIdSuperCompon())) {
                diferentes.put(actual.getClaseSuperComp().getFCompSuperCompon().getIdSuperCompon(), null);
                ret.add(actual.getSuperComp());
            }
        }
        return ret;
    }

    private void fillConfigura(SieniClase clase) {
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
//        this.getSecciones().get(0).getPantallas().get(0).setComponentes(null);

        /*seteo de componentes correcto*/
        List<SieniClaseSupComp> listaComp = sieniClaseSupCompFacadeRemote.findByClase(clase.getIdClase());
        List<SieniSuperCompon> totalSuperComponentes = sieniSuperComponFacadeRemote.findAllNoInactivos();

        this.setComponentesInteractDisponibles(totalSuperComponentes);
        this.setSecciones(getSeccionesByElemPlantilla(listaComp, this.getClaseConfig().getIdPlantilla().getSieniElemPlantillaList()));

        //interacciones entre componentes by clase
        this.setInteEntrCompList(sieniInteEntrCompFacadeRemote.findByClase(clase.getIdClase()));
        //********informaciÃƒÆ’Ã‚Â³n
        this.setPaginaActive(0);
        this.setIdElemenActive(0);
    }

    private List<ComponenteInteractivoPojo> getComponentesInteractivos(List<SieniClaseSupComp> listaComp) {
        CopiaArchivos ca = new CopiaArchivos();
        ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
        List<ComponenteInteractivoPojo> componentes = new ArrayList<>();
        for (SieniClaseSupComp actual : listaComp) {
            ComponenteInteractivoPojo nuevo = new ComponenteInteractivoPojo();
            nuevo.setSuperComp(actual.getFCompSuperCompon());
            nuevo.setData(new ArrayList());
            nuevo.setClaseSuperComp(actual);
            FileStreamedPojo dataNuevo;
            //si es archivo multimedia
            List<SieniComponente> superCompon = sieniComponenteFacadeRemote.findByIdSuperComp(nuevo.getSuperComp().getIdSuperCompon());
            List<SieniArchivo> archivos = sieniArchivoFacadeRemote.findByIdSuperComp(actual.getFCompSuperCompon().getIdSuperCompon());
            for (SieniComponente comp : superCompon) {
                dataNuevo = new FileStreamedPojo();
                dataNuevo.setComponente(comp);
                if (archivos != null && !archivos.isEmpty()) {
                    for (SieniArchivo arch : archivos) {
                        if (comp.getIdArchivo().equals(arch.getIdArchivo())) {
                            dataNuevo.setArchivoBD(arch);
                            if (arch.getArTipo().equals(new Character('I'))
                                    || arch.getArTipo().equals(new Character('V'))
                                    || arch.getArTipo().equals(new Character('A'))) {
                                ca.copyDataToResource(arch);

                            } else {
                                nuevo.setTexto(new String(ca.getData(arch)));
                            }
                            break;
                        }
                    }
                } else {
                    dataNuevo.setArchivoBD(new SieniArchivo());
                }
                nuevo.getData().add(dataNuevo);
            }
            componentes.add(nuevo);
        }
        return componentes;
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
        this.setClaseConfig(ver);
        fillConfigura(ver);
        this.setIndexMenu(3);
        this.setFuncionJS(getCodigoEventosEntreComp());
    }

    //solo cuando se desean ver los cambios sin llenar actualizar las interacciones
    public void mostrar2(SieniClase ver) {
        this.setClaseConfig(ver);
        this.setIndexMenu(3);
        this.setFuncionJS(getCodigoEventosEntreComp());
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
        List<SieniClaseSupComp> componentes = new ArrayList<>();
        List<SieniClaseSupComp> eliminados = new ArrayList<>();
        for (SeccionPlantillaPojo sec : this.getSecciones()) {
            for (PantallaPojo pantalla : sec.getPantallas()) {
                for (ComponenteInteractivoPojo comp : pantalla.getComponentes()) {
                    componentes.add(comp.getClaseSuperComp());
                }
            }
        }
        for (ComponenteInteractivoPojo elimn : this.getComponentesEliminados()) {
            eliminados.add(elimn.getClaseSuperComp());
        }
        sieniClaseSupCompFacadeRemote.merge(componentes, eliminados);
        fillConfigura(this.getClaseConfig());
        FacesMessage msg = new FacesMessage("ConfiguraciÃƒÆ’Ã‚Â³n guardada exitosamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void refreshConfig() {
    }

    public void agregarComponentePantallaActual() {
        CopiaArchivos ca = new CopiaArchivos();
        ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
        SeccionPlantillaPojo seccionActual = this.getSecciones().get(this.getIdElemenActive());
        int index = this.getSecciones().get(this.getIdElemenActive()).getPantallaActual();

        this.getNuevoComponente().setIdClase(this.getClaseConfig());
        this.getNuevoComponente().setIdTipoElemPlantilla(seccionActual.getIdElemPlantilla().getIdTipoElemPlantilla());
        this.getNuevoComponente().setIdClaseSupComp(-Long.parseLong(new DateUtils().getTime()));
        this.getNuevoComponente().setScEstado('A');
        this.getNuevoComponente().setScNPantalla(index + 1);
        this.getNuevoComponente().setScPosX(0);
        this.getNuevoComponente().setScPosY(0);

        List<SieniClaseSupComp> auxComp = new ArrayList<>();
        auxComp.add(this.getNuevoComponente());
        List<ComponenteInteractivoPojo> a = getComponentesInteractivos(auxComp);
        a.addAll(seccionActual.getPantallas().get(index).getComponentes());
        seccionActual.getPantallas().get(index).setComponentes(a);
        this.setNuevoComponente(new SieniClaseSupComp());
    }

    public void agregarPantalla() {
        Integer maxPantallas = 15;
        SeccionPlantillaPojo seccionActual = this.getSecciones().get(this.getIdElemenActive());
        PantallaPojo nuevaPantalla = new PantallaPojo();
        nuevaPantalla.setNumPantalla(seccionActual.getPantallas().size() + 1);
        if (seccionActual.getPantallas().size() < maxPantallas) {
            nuevaPantalla.setComponentes(new ArrayList<ComponenteInteractivoPojo>());
            seccionActual.getPantallas().add(nuevaPantalla);
        } else {
            FacesMessage msg = new FacesMessage("No se puede agregar mÃƒÆ’Ã‚Â¡s pantallas a este elemento de plantilla, mÃƒÆ’Ã‚Â¡ximo " + maxPantallas + " pantallas");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void eliminarPantallaActual() {
        SeccionPlantillaPojo seccionActual = this.getSecciones().get(this.getIdElemenActive());
        int index = seccionActual.getPantallaActual();
        if (seccionActual.getPantallas().size() > 1) {
            //eliminar componentes de la pantalla actual
            for (ComponenteInteractivoPojo elimn : seccionActual.getPantallas().get(index).getComponentes()) {
                this.getComponentesEliminados().add(elimn);
            }
            this.getPantallasEliminadas().add(seccionActual.getPantallas().get(index));
            seccionActual.getPantallas().remove(index);
            //recalcular el numero de pantallas despues de la eliminacion
            index = 1;
            for (PantallaPojo actual : seccionActual.getPantallas()) {
                actual.setNumPantalla(index);
                index++;
            }
            //establece la primera pantalla como seleccionada despues de la eliminacion
            seccionActual.setPantallaActual(0);
        } else {
            FacesMessage msg = new FacesMessage("El elemento de plantilla debe tener almenos 1 pÃƒÆ’Ã‚Â¡gina");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onDrop(DragDropEvent dragDropEvent) {
        String dargId = dragDropEvent.getDragId();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String left = params.get(dargId + "_left");
        String top = params.get(dargId + "_top");
        String height = params.get(dargId + "_height");
        String id = dargId.split("id_")[1];
        String idComponenteString = id.split("_")[0];
        String elemPlantillaString = id.split("_")[1];
        String pantallaString = id.split("_")[2];

        Long idSuperComponente = Long.parseLong(idComponenteString);
        Long idElemPlantilla = Long.parseLong(elemPlantillaString);
        Integer pantalla = Integer.parseInt(pantallaString);
        for (SeccionPlantillaPojo secc : this.getSecciones()) {
//            secc.getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla()
            if (idElemPlantilla.equals(secc.getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla()) && secc.getPantallas() != null) {
                for (PantallaPojo pant : secc.getPantallas()) {
                    if (pantalla.equals(pant.getNumPantalla()) && pant.getComponentes() != null) {
                        for (ComponenteInteractivoPojo comp : pant.getComponentes()) {
                            if (comp.getClaseSuperComp().getIdClaseSupComp().equals(idSuperComponente)) {
                                comp.getClaseSuperComp().setScPosX(new Double(Double.parseDouble(left)).intValue());
                                comp.getClaseSuperComp().setScPosY(new Double(Double.parseDouble(top)).intValue());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void eliminarSuperComponente(ComponenteInteractivoPojo componente) {
        this.setSuperCompEliminado(componente);
    }

    public void eliminarComponenteActual() {
        SeccionPlantillaPojo seccionActual = this.getSecciones().get(this.getIdElemenActive());
        int index = seccionActual.getPantallaActual();
        this.getComponentesEliminados().add(this.getSuperCompEliminado());
        seccionActual.getPantallas().get(index).getComponentes().remove(this.getSuperCompEliminado());
    }

    //interacciones seleccionadas
    public void updateInteractByTipoElemPlanPantalla(TabChangeEvent ev) {
        SeccionPlantillaPojo seccionActual = this.getSecciones().get(this.getIdElemenActive());
        Integer index = seccionActual.getPantallaActual();

        if (index == null) {
            index = 0;
        }
        List<SieniInteEntrComp> listaActual = getInteractByPantallaTipoElemPlantilla(this.getSecciones().get(this.getIdElemenActive()).getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla(), seccionActual.getPantallas().get(index).getNumPantalla());
        this.setInteracPantallaElemPlantillaActual(listaActual);
        this.setCompDisponibles(getComponentsPantallaActual());
        //en cada refresco setea el primer componente de la lista como actual
        if (this.getCompDisponibles() != null && !this.getCompDisponibles().isEmpty()) {
            this.getNuevaInterac().setIeSupC1(this.getCompDisponibles().get(0));
            this.getNuevaInterac().setIeSupC2(this.getCompDisponibles().get(0));
        }
        //si hay componentes disponibles
        if (this.getNuevaInterac().getIeSupC1() != null && this.getNuevaInterac().getIeSupC1().getIdSuperCompon() != null) {
            updateEv1(this.getNuevaInterac().getIeSupC1().getIdSuperCompon());
            updateEv2(this.getNuevaInterac().getIeSupC2().getIdSuperCompon());
        } else {
            this.setEvn1(new ArrayList<SieniEvento>());
            this.setEvn2(new ArrayList<SieniEvento>());
        }
    }

    public List<List<SieniInteEntrComp>> getInteracByTipoPlantilla(List<SieniInteEntrComp> listInteEntreComp) {
        List<List<SieniInteEntrComp>> ret = new ArrayList<>();
        HashMap<Long, List<SieniInteEntrComp>> dif = new HashMap<>();
        for (SieniInteEntrComp actual : listInteEntreComp) {
            if (!dif.containsKey(actual.getIdTipoElemPlantilla().getIdTipoElemPlantilla())) {
                dif.put(actual.getIdTipoElemPlantilla().getIdTipoElemPlantilla(), new ArrayList<SieniInteEntrComp>());
                dif.get(actual.getIdTipoElemPlantilla().getIdTipoElemPlantilla()).add(actual);
            } else {
                dif.get(actual.getIdTipoElemPlantilla().getIdTipoElemPlantilla()).add(actual);
            }
        }
        for (List<SieniInteEntrComp> actual : dif.values()) {
            ret.add(actual);
        }
        return ret;
    }

    public List<List<SieniInteEntrComp>> getInteracByPantalla(List<SieniInteEntrComp> listInteEntreComp) {
        List<List<SieniInteEntrComp>> ret = new ArrayList<>();
        HashMap<Integer, List<SieniInteEntrComp>> dif = new HashMap<>();
        for (SieniInteEntrComp actual : listInteEntreComp) {
            if (!dif.containsKey(actual.getIeNPantalla())) {
                dif.put(actual.getIeNPantalla(), new ArrayList<SieniInteEntrComp>());
                dif.get(actual.getIeNPantalla()).add(actual);
            } else {
                dif.get(actual.getIeNPantalla()).add(actual);
            }
        }
        for (List<SieniInteEntrComp> actual : dif.values()) {
            ret.add(actual);
        }
        return ret;
    }

    public List<SieniInteEntrComp> getInteractByPantallaTipoElemPlantilla(Long idTipoElemPlantilla, Integer nPantalla) {
        HashMap<Long, List<SieniInteEntrComp>> res1 = new HashMap<>();
        HashMap<Integer, List<SieniInteEntrComp>> res2 = new HashMap<>();
        for (SieniInteEntrComp tipoElemPlantilla : this.getInteracTotal()) {
            if (!res1.containsKey(tipoElemPlantilla.getIdTipoElemPlantilla().getIdTipoElemPlantilla())) {
                res1.put(tipoElemPlantilla.getIdTipoElemPlantilla().getIdTipoElemPlantilla(), new ArrayList<SieniInteEntrComp>());
                res1.get(tipoElemPlantilla.getIdTipoElemPlantilla().getIdTipoElemPlantilla()).add(tipoElemPlantilla);
            } else {
                res1.get(tipoElemPlantilla.getIdTipoElemPlantilla().getIdTipoElemPlantilla()).add(tipoElemPlantilla);
            }
        }
        if (res1.get(idTipoElemPlantilla) != null) {
            for (SieniInteEntrComp tipoElemPlantilla : res1.get(idTipoElemPlantilla)) {
                if (!res2.containsKey(tipoElemPlantilla.getIeNPantalla())) {
                    res2.put(tipoElemPlantilla.getIeNPantalla(), new ArrayList<SieniInteEntrComp>());
                    res2.get(tipoElemPlantilla.getIeNPantalla()).add(tipoElemPlantilla);
                } else {
                    res2.get(tipoElemPlantilla.getIeNPantalla()).add(tipoElemPlantilla);
                }
            }
        }
        return res2.get(nPantalla);
    }

    public String getCodigoEventosEntreComp() {
        List<SieniInteEntrComp> totalInteracc = sieniInteEntrCompFacadeRemote.findByClase(this.getClaseConfig().getIdClase());
        this.setInteracTotal(totalInteracc);
        List<CodigoEvento> listEv;
        String funcion = "";
        for (List<SieniInteEntrComp> tipoElemPlantilla : getInteracByTipoPlantilla(totalInteracc)) {
            for (List<SieniInteEntrComp> listInteEntreComp : getInteracByPantalla(tipoElemPlantilla)) {
                HashMap<String, Integer> eventosDiferentes = getEventosNumerados(listInteEntreComp);
                //agregar eventos que no son de interaccion entre componentes
                HashMap<Long, Integer> componentesDiferentes = getComponentesNumerados(listInteEntreComp);
                //agregar componentes que son parte de la pagina pero no estan en la interaccion entre componentes
                HashMap<Long, SuperCompPojo> l = agruparPorComp(listInteEntreComp);
                //agregar componentes que son parte de la pagina pero no estan en la interaccion entre componentes

                CodigoEvento ev = new CodigoEvento();
                CodigoComponente comp = new CodigoComponente();
                List<CodigoComponente> listComp = new ArrayList<>();
                List<EventosPojo> evDif = getEventosDiferentes(listInteEntreComp);
                //agregar eventos de componentes que son parte de la pagina pero no estan en la interaccion entre componentes
                Long idEventoActual;
                String codEvento;
                listEv = new ArrayList<>();
                for (EventosPojo actual : evDif) {
                    idEventoActual = actual.getIdEvento();
                    codEvento = actual.getEvento();
                    List<Long> compEv = getComponByEvento(codEvento, listInteEntreComp);
                    for (Long idCompActual : compEv) {
                        comp = new CodigoComponente();
                        comp.setIdSuperCompon(idCompActual);
                        comp.setInteracciones(l.get(idCompActual).getInteraccionesCompPojoByEvento(codEvento));
                        comp.setContEvento(eventosDiferentes.get(codEvento));
                        comp.setCont(componentesDiferentes.get(idCompActual));
                        comp.setEvento(codEvento);
                        comp.setComps2(getComps2(eventosDiferentes, componentesDiferentes, listInteEntreComp, idCompActual, idEventoActual));
                        listComp.add(comp);
                    }
                    ev.setComponentes(listComp);
                    ev.setCont(eventosDiferentes.get(codEvento));
                    listEv.add(ev);
                }
                funcion += crearFuncion(listEv, listInteEntreComp.get(0).getIdTipoElemPlantilla().getIdTipoElemPlantilla(), listInteEntreComp.get(0).getIeNPantalla());
            }
        }
        return funcion;
    }

    public List<Long> getComponByEvento(String idEvento, List<SieniInteEntrComp> listInteEntreComp) {
        List<Long> ret = new ArrayList<>();
        for (SieniInteEntrComp actual : listInteEntreComp) {
            if (actual.getIeEventoC1().getEvCodigo().equals(idEvento) && !ret.contains(actual.getIeSupC1().getIdSuperCompon())) {
                ret.add(actual.getIeSupC1().getIdSuperCompon());
            }
            if (actual.getIeEventoC2().getEvCodigo().equals(idEvento) && !ret.contains(actual.getIeSupC2().getIdSuperCompon())) {
                ret.add(actual.getIeSupC2().getIdSuperCompon());
            }
        }

        return ret;
    }

    //get comp2 de comp1 ev1
    public List<CodigoComponente> getComps2(HashMap<String, Integer> eventosDiferentes,
            HashMap<Long, Integer> componentesDiferentes,
            List<SieniInteEntrComp> listInteEntreComp,
            Long idComp1, Long idEvnt1) {
        List<CodigoComponente> listComp = new ArrayList<>();
        CodigoComponente comp;
        List<SieniInteEntrComp> ret = new ArrayList<>();
        for (SieniInteEntrComp actual : listInteEntreComp) {
            if (actual.getIeSupC1().getIdSuperCompon().equals(idComp1)
                    && actual.getIeEventoC1().getIdEvento().equals(idEvnt1)) {
                ret.add(actual);
            }
        }
        for (SieniInteEntrComp actual : ret) {
            comp = new CodigoComponente();
            comp.setIdSuperCompon(actual.getIeSupC2().getIdSuperCompon());
            comp.setContEvento(eventosDiferentes.get(actual.getIeEventoC2().getEvCodigo()));
            comp.setDelay(actual.getIeRetraso());
            comp.setCont(componentesDiferentes.get(actual.getIeSupC2().getIdSuperCompon()));
            listComp.add(comp);
        }
        return listComp;

    }

    public HashMap<String, Integer> getEventosNumerados(List<SieniInteEntrComp> interac) {
        HashMap<String, Integer> ret = new HashMap<>();
        int cont = 1;
        for (SieniInteEntrComp comp : interac) {
            if (!ret.containsKey(comp.getIeEventoC1().getEvCodigo())) {
                ret.put(comp.getIeEventoC1().getEvCodigo(), cont);
                cont++;
            } else if (!ret.containsKey(comp.getIeEventoC2().getEvCodigo())) {
                ret.put(comp.getIeEventoC2().getEvCodigo(), cont);
                cont++;
            }
        }
        return ret;
    }

    public List<EventosPojo> getEventosDiferentes(List<SieniInteEntrComp> interac) {
        List<String> res = new ArrayList<>();
        List<EventosPojo> ret = new ArrayList<>();
        EventosPojo nuevo;
        //TODO falta meter eventos que no son interacciones entre componentes
        for (SieniInteEntrComp comp : interac) {
            if (!res.contains(comp.getIeEventoC1().getEvCodigo())) {
                nuevo = new EventosPojo();
                nuevo.setEvento(comp.getIeEventoC1().getEvCodigo());
                nuevo.setIdEvento(comp.getIeEventoC1().getIdEvento());
                nuevo.setInterac(comp);
                res.add(comp.getIeEventoC1().getEvCodigo());
                ret.add(nuevo);
            }
            if (!res.contains(comp.getIeEventoC2().getEvCodigo())) {
                nuevo = new EventosPojo();
                nuevo.setEvento(comp.getIeEventoC2().getEvCodigo());
                nuevo.setIdEvento(comp.getIeEventoC2().getIdEvento());
                nuevo.setInterac(comp);
                res.add(comp.getIeEventoC2().getEvCodigo());
                ret.add(nuevo);
            }
        }
        return ret;
    }

    //agrega a una lista los eventos que no son parte de la interaccion entre componentes
    //pero que son parte de los componentes
    public List<SieniInteEntrComp> eventosNoInteracciones() {
        List<SieniInteEntrComp> ret = new ArrayList<>();

        return ret;
    }

    public HashMap<Long, Integer> getComponentesNumerados(List<SieniInteEntrComp> interac) {
        HashMap<Long, Integer> ret = new HashMap<>();
        int cont = 1;
        for (SieniInteEntrComp comp : interac) {
            if (!ret.containsKey(comp.getIeSupC1().getIdSuperCompon())) {
                ret.put(comp.getIeSupC1().getIdSuperCompon(), cont);
                cont++;
            }
            if (!ret.containsKey(comp.getIeSupC2().getIdSuperCompon())) {
                ret.put(comp.getIeSupC2().getIdSuperCompon(), cont);
                cont++;
            }
        }
        return ret;
    }

    public String crearFuncion(List<CodigoEvento> cods, Long idTipoElemPlantilla, Integer nPantalla) {
        String funcion = "";
        String intermedia = "";
        String eventos = "";
        boolean ultimoAccionCompActual;
        //codigoEvento->lista componentes
        //lista componentes->lista acciones
        int contAcciones = 1;
        //click,dblclick,contextmenu
        for (CodigoEvento evnto : cods) {
            //2
            for (CodigoComponente comp : evnto.getComponentes()) {
                String ubica = nPantalla + "_" + idTipoElemPlantilla + "_";
                String compon = ubica + comp.getIdSuperCompon();

                contAcciones = 1;
                if (comp.getInteracciones() != null && !comp.getInteracciones().isEmpty()) {
                    Integer inteCont = 1;
                    for (InteraccionesCompPojo inte : comp.getInteracciones()) {
                        //2 interacc
                        funcion += " function func_" + evnto.getCont() + "_" + compon + "_" + inteCont + "(){\n"
                                + "     jQuery(\".compon" + compon + "\").effect(\"" + inte.getInteraccion().getIdAccion().getEvCodigo() + "\"," + inte.getInteraccion().getInDuracion() + ", function(){\n"
                                + "         func_" + evnto.getCont() + "_" + compon + "_" + inteCont + "_intermedia();\n"
                                + "     });"
                                + "  };\n";
                        intermedia = "";
                        ultimoAccionCompActual = contAcciones >= comp.getInteracciones().size();
                        if (ultimoAccionCompActual) {
                            if (comp.getComps2() != null && !comp.getComps2().isEmpty()) {
                                //componentes vinculados al componente 1
                                for (CodigoComponente comp2 : comp.getComps2()) {
                                    intermedia += " setTimeout(\n"
                                            + "         function(){\n"
                                            + "             func_" + comp2.getContEvento() + "_" + ubica + comp2.getIdSuperCompon() + "_1();\n"
                                            + "         },\n"
                                            + "         " + (comp2.getDelay() != null ? comp2.getDelay() + "" : "0") + "\n"
                                            + "     );\n";
                                }
                            }
                        } else {//siguiente evento del componente actual
                            intermedia += "func_" + evnto.getCont() + "_" + compon + "_" + (inteCont + 1) + "();\n";
                        }
                        funcion += "function func_" + evnto.getCont() + "_" + compon + "_" + inteCont + "_intermedia(){\n"
                                + "     " + intermedia + "\n"
                                + "}\n";
                        contAcciones++;
                        inteCont++;
                    }
                    eventos += "jQuery(\".compon" + compon + "\").on(\"" + comp.getEvento() + "\", function () {\n"
                            + "                        func_" + evnto.getCont() + "_" + compon + "_1();\n"
                            + "                    });\n";
                } else {//funcion eliminada
                    funcion += " function func_" + evnto.getCont() + "_" + compon + "_1(){}\n";
                }
            }
        }
        funcion += "jQuery(document).ready(function () {\n"
                + eventos
                + "});";

        return funcion;
    }

    public HashMap<Long, SuperCompPojo> agruparPorComp(List<SieniInteEntrComp> intEntComp) {
        List<SieniCompInteraccion> interActual;
        HashMap<Long, SuperCompPojo> interaccionesBySuperComp = new HashMap<>();
        SuperCompPojo nuevo;
        int count = 1;
        for (SieniInteEntrComp comp : intEntComp) {
            if (!interaccionesBySuperComp.containsKey(comp.getIeSupC1().getIdSuperCompon())) {
                interActual = sieniCompInteraccionFacadeRemote.findByIdSuperComp(comp.getIeSupC1().getIdSuperCompon());
                nuevo = new SuperCompPojo();
                nuevo.setCount(count);
                nuevo.setEventos(agruparEvento(interActual));
                nuevo.setEventosEv(agruparEventoCod(interActual));
                nuevo.setSupComp(comp.getIeSupC1());
                interaccionesBySuperComp.put(comp.getIeSupC1().getIdSuperCompon(), nuevo);
                count++;
            }
            if (!interaccionesBySuperComp.containsKey(comp.getIeSupC2().getIdSuperCompon())) {
                interActual = sieniCompInteraccionFacadeRemote.findByIdSuperComp(comp.getIeSupC2().getIdSuperCompon());
                nuevo = new SuperCompPojo();
                nuevo.setCount(count);
                nuevo.setEventos(agruparEvento(interActual));
                nuevo.setEventosEv(agruparEventoCod(interActual));
                nuevo.setSupComp(comp.getIeSupC2());
                interaccionesBySuperComp.put(comp.getIeSupC2().getIdSuperCompon(), nuevo);
                count++;
            }
        }
        return interaccionesBySuperComp;
    }

    public HashMap<Long, List<InteraccionesCompPojo>> agruparEvento(List<SieniCompInteraccion> intEntComp) {
        HashMap<Long, List<InteraccionesCompPojo>> ret = new HashMap<>();
        InteraccionesCompPojo nuevo;
        int cont = 1;
        for (SieniCompInteraccion comp : intEntComp) {
            nuevo = new InteraccionesCompPojo();
            nuevo.setCont(cont);
            nuevo.setInteraccion(comp);
            if (!ret.containsKey(comp.getIdEvento().getIdEvento())) {
                ret.put(comp.getIdEvento().getIdEvento(), new ArrayList<InteraccionesCompPojo>());
                ret.get(comp.getIdEvento().getIdEvento()).add(nuevo);
            } else {
                ret.get(comp.getIdEvento().getIdEvento()).add(nuevo);
            }
            cont++;
        }
        return ret;
    }

    public HashMap<String, List<InteraccionesCompPojo>> agruparEventoCod(List<SieniCompInteraccion> intEntComp) {
        HashMap<String, List<InteraccionesCompPojo>> ret = new HashMap<>();
        InteraccionesCompPojo nuevo;
        int cont = 1;
        for (SieniCompInteraccion comp : intEntComp) {
            nuevo = new InteraccionesCompPojo();
            nuevo.setCont(cont);
            nuevo.setInteraccion(comp);
            if (!ret.containsKey(comp.getIdEvento().getEvCodigo())) {
                ret.put(comp.getIdEvento().getEvCodigo(), new ArrayList<InteraccionesCompPojo>());
                ret.get(comp.getIdEvento().getEvCodigo()).add(nuevo);
            } else {
                ret.get(comp.getIdEvento().getEvCodigo()).add(nuevo);
            }
            cont++;
        }
        return ret;
    }

    public List<SieniEvento> getEventoDiferenteBySuperCompon(List<SieniCompInteraccion> total) {
        List<SieniEvento> ret = new ArrayList<>();
        HashMap map = new HashMap();
        for (SieniCompInteraccion actual : total) {
            if (map.get(actual.getIdEvento().getEvCodigo()) == null) {
                map.put(actual.getIdEvento().getEvCodigo(), new Object());
                ret.add(actual.getIdEvento());
            }
        }
        return ret;
    }

    public void getEventosC1(ValueChangeEvent ev) {
        SieniSuperCompon sup = (SieniSuperCompon) ev.getNewValue();
        if (sup != null) {
            updateEv1(sup.getIdSuperCompon());
        }
    }

    public void getEventosC2(ValueChangeEvent ev) {
        SieniSuperCompon sup = (SieniSuperCompon) ev.getNewValue();
        if (sup != null) {
            updateEv2(sup.getIdSuperCompon());
        }
    }

    public void updateEv1(Long idSuperCompon) {
        this.setEvn1(getEventoDiferenteBySuperCompon(sieniCompInteraccionFacadeRemote.findByIdSuperComp(idSuperCompon)));
    }

    public void updateEv2(Long idSuperCompon) {
        this.setEvn2(getEventoDiferenteBySuperCompon(sieniCompInteraccionFacadeRemote.findByIdSuperComp(idSuperCompon)));
    }
}
