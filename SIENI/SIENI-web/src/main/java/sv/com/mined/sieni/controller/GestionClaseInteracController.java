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
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.DragDropEvent;
import sv.com.mined.sieni.SieniArchivoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniClaseFacadeRemote;
import sv.com.mined.sieni.SieniClaseSupCompFacadeRemote;
import sv.com.mined.sieni.SieniCompInteraccionFacadeRemote;
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
import sv.com.mined.sieni.model.SieniInteEntrComp;
import sv.com.mined.sieni.model.SieniPlantilla;
import sv.com.mined.sieni.model.SieniSuperCompon;
import sv.com.mined.sieni.pojos.controller.ComponenteInteractivoPojo;
import sv.com.mined.sieni.pojos.controller.FileStreamedPojo;
import sv.com.mined.sieni.pojos.controller.InteraccionEntrCompPojo;
import sv.com.mined.sieni.pojos.controller.PantallaPojo;
import sv.com.mined.sieni.pojos.controller.SeccionPlantillaPojo;
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

    @PostConstruct
    public void init() {
        this.setPantallasEliminadas(new ArrayList<PantallaPojo>());
        this.setComponentesEliminados(new ArrayList<ComponenteInteractivoPojo>());
        this.setNuevoComponente(new SieniClaseSupComp());
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
                        //si el elemento de plantilla ya está registrado, agrega a la lista el componente
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
                    //si la pantalla ya está registrada, agrega a la lista el componente
                    pantallasAux.get(actual.getScNPantalla()).add(actual);
                }
            }
            for (Integer actual : pantallasDiferentes) {
                nuevo = new PantallaPojo();
                nuevo.setNumPantalla(actual);
                nuevo.setComponentes(getComponentesInteractivos(componentes));
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

    public HashMap<Long, List<SieniCompInteraccion>> getInteraccionEntrComponList(List<SieniCompInteraccion> componentes) {
        HashMap<Long, List<SieniCompInteraccion>> ret = new HashMap();
        //para 1 componente
        //click --lista
        //click dere--lista
        if (componentes != null) {
            for (SieniCompInteraccion actual : componentes) {
                if (!ret.containsKey(actual.getIdEvento().getIdEvento())) {
                    //incializa el hashmap
                    ret.put(actual.getIdEvento().getIdEvento(), new ArrayList<SieniCompInteraccion>());
                    //ingresa el componente actual
                    ret.get(actual.getIdEvento().getIdEvento()).add(actual);
                } else {
                    //si la pantalla ya está registrada, agrega a la lista el componente
                    ret.get(actual.getIdEvento().getIdEvento()).add(actual);
                }
            }
        }

        //interacc orden 1-click- del super
        //interacc orden 2-click- del super
        //interacc orden 1-click- del super
        //interacc orden 3-click- del super
        //crear las funciones
        //crear relaciones
        return ret;
    }

    public void ordenarPorComponente1(SieniInteEntrComp superCompon) {
        List<InteraccionEntrCompPojo> lista=null;
        
        InteraccionEntrCompPojo nuevo=null;
        
        SieniInteEntrComp aA=null;
        SieniCompInteraccionFacadeRemote a = null;
        //verigicar q si existe e componente 1 y 2
        HashMap<Long, List<SieniCompInteraccion>> interaccionesC1 = getInteraccionEntrComponList(a.findByIdSuperComp(aA.getIeSupC1().getIdSuperCompon()));
        HashMap<Long, List<SieniCompInteraccion>> interaccionesC2 = getInteraccionEntrComponList(a.findByIdSuperComp(aA.getIeSupC2().getIdSuperCompon()));
        List<SieniCompInteraccion> eventosActuales1 = interaccionesC1.get(aA.getIeEventoC1().getIdEvento());
        List<SieniCompInteraccion> eventosActuales2 = interaccionesC2.get(aA.getIeEventoC2().getIdEvento());
        
        nuevo.setC1(aA.getIeSupC1());
        nuevo.setC2(aA.getIeSupC2());
        
        

//        aA.getIeSupC1().getSieniInteEntrCompList1()
        ordenarPorEventoComp1();
    }

    public void ordenarPorEventoComp1() {
        ordenarPorComponente2();
    }

    public void ordenarPorComponente2() {
        ordenarPorEventoComp2();
    }

    public void ordenarPorEventoComp2() {

    }

    public void configurar(SieniClase clase) {
        this.setClaseConfig(clase);
        if (clase.getClAlto() == null) {
            clase.setClAlto(600);
        }
        if (clase.getClAncho() == null) {//2225-2116- nelson villa
            clase.setClAncho(800); //tijera flecha, cremallera
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
        //********información
        this.setPaginaActive(0);
        this.setIdElemenActive(0);
        this.setIndexMenu(4);

//        this.setMaterias(sieniMateriaFacadeRemote.findAll());
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
            for (SieniComponente comp : actual.getFCompSuperCompon().getSieniComponenteList()) {
                dataNuevo = new FileStreamedPojo();
                dataNuevo.setComponente(comp);
                List<SieniArchivo> archivos = sieniArchivoFacadeRemote.findByIdSuperComp(actual.getFCompSuperCompon().getIdSuperCompon());
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
        this.getPaginaActive();
        this.getIdElemenActive();
        System.out.println();
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
            FacesMessage msg = new FacesMessage("No se puede agregar más pantallas a este elemento de plantilla, máximo " + maxPantallas + " pantallas");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void eliminarPantallaActual() {
        SeccionPlantillaPojo seccionActual = this.getSecciones().get(this.getIdElemenActive());
        int index = this.getPaginaActive();
        if (seccionActual.getPantallas().size() > 1) {
            this.getPantallasEliminadas().add(seccionActual.getPantallas().get(index));
            seccionActual.getPantallas().remove(index);
            //recalcular el numero de pantallas despues de la eliminacion
            index = 1;
            for (PantallaPojo actual : seccionActual.getPantallas()) {
                actual.setNumPantalla(index);
                index++;
            }
        } else {
            FacesMessage msg = new FacesMessage("El elemento de plantilla debe tener almenos 1 página");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onDrop(DragDropEvent dragDropEvent) {
        String dargId = dragDropEvent.getDragId();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String left = params.get(dargId + "_left");
        String top = params.get(dargId + "_top");
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

}
