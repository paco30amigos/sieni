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
import org.primefaces.model.DualListModel;
import sv.com.mined.sieni.SieniArchivoFacadeRemote;
import sv.com.mined.sieni.SieniCatPuntosFacadeRemote;
import sv.com.mined.sieni.SieniClaseFacadeRemote;
import sv.com.mined.sieni.SieniClaseSupCompFacadeRemote;
import sv.com.mined.sieni.SieniCompInteraccionFacadeRemote;
import sv.com.mined.sieni.SieniComponenteFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniElemPlantillaFacadeRemote;
import sv.com.mined.sieni.SieniInteEntrCompFacadeRemote;
import sv.com.mined.sieni.SieniPlantillaFacadeRemote;
import sv.com.mined.sieni.SieniPntosContrlFacadeRemote;
import sv.com.mined.sieni.SieniSuperComponFacadeRemote;
import sv.com.mined.sieni.form.GestionClaseInteracForm;
import sv.com.mined.sieni.model.SieniArchivo;
import sv.com.mined.sieni.model.SieniCatPuntos;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniClaseSupComp;
import sv.com.mined.sieni.model.SieniCompInteraccion;
import sv.com.mined.sieni.model.SieniComponente;
import sv.com.mined.sieni.model.SieniElemPlantilla;
import sv.com.mined.sieni.model.SieniEvento;
import sv.com.mined.sieni.model.SieniInteEntrComp;
import sv.com.mined.sieni.model.SieniPlantilla;
import sv.com.mined.sieni.model.SieniPntosContrl;
import sv.com.mined.sieni.model.SieniSuperCompon;
import sv.com.mined.sieni.model.SieniTipoElemPlantilla;
import sv.com.mined.sieni.pojos.controller.ComponenteInteractivoPojo;
import sv.com.mined.sieni.pojos.controller.FileStreamedPojo;
import sv.com.mined.sieni.pojos.controller.InteraccionMultiplePojo;
import sv.com.mined.sieni.pojos.controller.PantallaPojo;
import sv.com.mined.sieni.pojos.controller.SeccionPlantillaPojo;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.ControlInteractivoUtils;
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
    @EJB
    private SieniPntosContrlFacadeRemote sieniPntosContrlFacadeRemote;
    @EJB
    private SieniCatPuntosFacadeRemote sieniCatPuntosFacadeRemote;
    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;

    @PostConstruct
    public void init() {
        this.setPantallasEliminadas(new ArrayList<PantallaPojo>());
        this.setComponentesEliminados(new ArrayList<ComponenteInteractivoPojo>());
        this.setNuevoComponente(new SieniClaseSupComp());
        this.setEvn1(new ArrayList<SieniEvento>());
        this.setEvn2(new ArrayList<SieniEvento>());
        this.setCompDisponibles(new ArrayList<SieniSuperCompon>());
        this.setNuevaInterac(new SieniInteEntrComp());
        this.setNuevaInteracMult2(new DualListModel<SieniInteEntrComp>());
        this.getNuevaInteracMult2().setTarget(new ArrayList<SieniInteEntrComp>());
        this.getNuevaInteracMult2().setSource(new ArrayList<SieniInteEntrComp>());
        fill();
    }

    private void registrarEnBitacora(String accion, String tabla, Long id) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        loginBean.registrarTransaccion(accion, tabla, id);

    }

    private List<SieniClase> setDocente(List<SieniClase> matriculas) {
        List<SieniClase> ret = new ArrayList<>();
        for (SieniClase actual : matriculas) {
            ret.add(setInfoDocente(actual));
        }
        return ret;
    }

    public SieniClase setInfoDocente(SieniClase matActual) {
        matActual.getIdCurso().setDocente(sieniDocenteFacadeRemote.findByDocenteId(matActual.getIdCurso().getIdDocente()));
        return matActual;
    }

    public void cancelaModifica(SieniClase modifica) {
        modifica = sieniClaseFacadeRemote.find(modifica.getIdClase());
        this.setIndexMenu(0);
    }

    private void fill() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        List<SieniClase> listaClases = new ArrayList<>();
        //fill para alumnos
        if (loginBean.getTipoRol().equals("0")) {
//*******fill
            //clases interact
            listaClases = setDocente(sieniClaseFacadeRemote.findClaseByTipoAlumno('I', loginBean.getAlumno().getIdAlumno()));//clases interactivas
        } else {
            //*******fill
            //clases interact
            listaClases = setDocente(sieniClaseFacadeRemote.findClaseByTipo('I'));//clases interactivas
//            listaClases = setDocente(sieniClaseFacadeRemote.findClaseByTipo('I',loginBean.getDocente().getIdDocente()));//clases interactivas
        }
        updateEstadoClase(listaClases);
        this.setClaseList(listaClases);
    }

    //actualiza el estado de las clases si ya estan disponibles segun el horario
    private synchronized void updateEstadoClase(List<SieniClase> clases) {
        List<SieniClase> clasesIniciadas = new ArrayList<>();
        DateUtils du = new DateUtils();

        for (SieniClase actual : clases) {
            if (actual.getClEstado().equals(new Character('N'))
                    && du.horarioValido(actual.getClHorario(), actual.getClHora())
                    && actual.getClTipoPublicacion().equals(new Character('A'))) {
                actual.setClEstado('A');
                clasesIniciadas.add(actual);
            }
        }
        if (!clasesIniciadas.isEmpty()) {
            sieniClaseFacadeRemote.merge(clasesIniciadas);
        }
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
        SeccionPlantillaPojo nuevo;
        for (SieniClaseSupComp componActual : componentes) {
            for (SieniElemPlantilla elemPlantilla : elemsPlantilla) {
                if (componActual.getIdTipoElemPlantilla().getIdTipoElemPlantilla().equals(elemPlantilla.getIdTipoElemPlantilla().getIdTipoElemPlantilla())) {
                    if (!elemPlantillaAux.containsKey(elemPlantilla.getIdTipoElemPlantilla().getIdTipoElemPlantilla())) {
                        //agrega el componente a la lista, para ese elemento de plantilla
                        elemPlantillaAux.put(elemPlantilla.getIdTipoElemPlantilla().getIdTipoElemPlantilla(), new ArrayList<SieniClaseSupComp>());
                        //ingresa el componente actual
                        elemPlantillaAux.get(elemPlantilla.getIdTipoElemPlantilla().getIdTipoElemPlantilla()).add(componActual);
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
            //evita que las pantallas esten desordenadas
            int cont = 1;
            for (Integer actual : pantallasDiferentes) {
                nuevo = new PantallaPojo();
                nuevo.setNumPantalla(cont);
                nuevo.setComponentes(getComponentesInteractivos(pantallasAux.get(actual)));
                ret.add(nuevo);
                cont++;
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
        if (fillConfigura(clase)) {
            this.setInteracTotal(sieniInteEntrCompFacadeRemote.findByClase(this.getClaseConfig().getIdClase()));
            this.setComponentesPantallaActual(getComponActuales());
            this.setIndexMenu(4);
        }
    }

    public void configurarInterac(SieniClase ver) {
        this.setInteracEliminados(new ArrayList<SieniInteEntrComp>());
        this.setNuevaInteracMult1(new SieniInteEntrComp());
        this.setOpSelectMulti(0);
        this.setClaseConfig(ver);
        if (fillConfigura(ver)) {
            this.setInteracTotal(sieniInteEntrCompFacadeRemote.findByClase(this.getClaseConfig().getIdClase()));
            ControlInteractivoUtils ciu = new ControlInteractivoUtils();
            ciu.setSecciones(this.getSecciones());
            ciu.setTotalInteracc(this.getInteracTotal());
            this.setFuncionJS(ciu.getCodigoEventosEntreComp());
            this.setNuevaInterac(new SieniInteEntrComp());
            this.getNuevaInterac().setIeRetraso(0);
            updateInteractByTipoElemPlanPantalla(null);
            this.setIndexMenu(5);
        }
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

    private boolean fillConfigura(SieniClase clase) {
        boolean ret = true;
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

        /*seteo de componentes correcto*/
        List<SieniClaseSupComp> listaComp = sieniClaseSupCompFacadeRemote.findByClase(clase.getIdClase());
        List<SieniSuperCompon> totalSuperComponentes = sieniSuperComponFacadeRemote.findEstado('A');

        this.setComponentesInteractDisponibles(totalSuperComponentes);
        if (this.getClaseConfig().getIdPlantilla() != null) {
            List<SieniElemPlantilla> elemPlantilla = sieniElemPlantillaFacadeRemote.findByIdPlantilla(this.getClaseConfig().getIdPlantilla().getIdPlantilla());
            if (elemPlantilla != null && !elemPlantilla.isEmpty()) {
                this.setSecciones(getSeccionesByElemPlantilla(listaComp, elemPlantilla));

                //interacciones entre componentes by clase
                this.setInteEntrCompList(sieniInteEntrCompFacadeRemote.findByClase(clase.getIdClase()));
                //********información
                this.setPaginaActive(0);
                this.setIdElemenActive(0);
            } else {
                new ValidationPojo().printMsj("La plantilla no tiene ningun elemento", FacesMessage.SEVERITY_ERROR);
                ret = false;
            }
        } else {
            ret = false;
            new ValidationPojo().printMsj("No se ha ingresado una plantilla para la clase", FacesMessage.SEVERITY_ERROR);
        }
        this.setInteracEliminados(new ArrayList<SieniInteEntrComp>());
        return ret;
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
            nuevo.setInteracciones(sieniCompInteraccionFacadeRemote.findByIdSuperComp(actual.getFCompSuperCompon().getIdSuperCompon()));
            Character v = actual.getScVisible();
            nuevo.setVisible(!(v == null || v.equals(new Character('N'))));
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
        //establece el la plantilla cuando solo existe una
        if (modificado.getIdPlantilla() != null && modificado.getIdPlantilla().getIdPlantilla() == null && this.getPlantillaModificaList() != null && this.getPlantillaModificaList().size() == 1) {
            modificado.setIdPlantilla(this.getPlantillaModificaList().get(0));
        }
        this.setClaseModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniClase eliminado) {
        this.setEliminar(eliminado);
    }

    //metodos para modificacion de datos
    public void mostrar(SieniClase ver) {
        boolean correcto = true;
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        //fill para alumnos
        if (loginBean.getTipoRol().equals("0")) {
            DateUtils du = new DateUtils();
            if (du.horarioValido(ver.getClHorario(), ver.getClHora())) {
                if (!validarEstadoClase(ver)) {
                    correcto = false;
                }
            } else {
                if (!validarEstadoClase(ver)) {
                    correcto = false;
                }
//                new ValidationPojo().printMsj("La clase aun no esta disponible", FacesMessage.SEVERITY_ERROR);
            }
        }
        if (correcto) {
            this.setClaseConfig(ver);
            if (fillConfigura(ver)) {
                this.setIndexMenu(3);
                this.setInteracTotal(sieniInteEntrCompFacadeRemote.findByClase(this.getClaseConfig().getIdClase()));
                ControlInteractivoUtils ciu = new ControlInteractivoUtils();
                ciu.setSecciones(this.getSecciones());
                ciu.setTotalInteracc(this.getInteracTotal());
                this.setFuncionJS(ciu.getCodigoEventosEntreComp());
                updatePuntoCtrlSeleccionados(null);
            }
        }
    }

    public boolean validarEstadoClase(SieniClase claseActual) {
        boolean ret = true;
        if (claseActual.getClEstado() != null && claseActual.getClEstado().equals(new Character('N'))) {
            ret = false;
            new ValidationPojo().printMsj("La clase aun no esta disponible", FacesMessage.SEVERITY_ERROR);
        } else if (claseActual.getClEstado() != null && claseActual.getClEstado().equals(new Character('T'))) {
            ret = false;
            new ValidationPojo().printMsj("La clase ya ha terminado", FacesMessage.SEVERITY_ERROR);
        }
        return ret;
    }

    //solo cuando se desean ver los cambios sin llenar actualizar las interacciones
    public void mostrar2() {
        this.setIndexMenu(6);
        ControlInteractivoUtils ciu = new ControlInteractivoUtils();
        ciu.setSecciones(this.getSecciones());
        ciu.setTotalInteracc(this.getInteracTotal());
        this.setFuncionJS(ciu.getCodigoEventosEntreComp());
    }

    public synchronized void guardarModifica() {
        try {
            if (validarModifica(this.getClaseModifica())) {//valida el guardado
                sieniClaseFacadeRemote.edit(this.getClaseModifica());
                registrarEnBitacora("Modificar", "Clase interactiva", this.getClaseModifica().getIdArchivo());
                new ValidationPojo().printMsj("Clase modificada exitosamente", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    public boolean validarModifica(SieniClase nuevo) {
        boolean ban = true;
        List<ValidationPojo> validaciones = new ArrayList<ValidationPojo>();
        return ban;
    }

    public synchronized void eliminarClase() {
        try {
            registrarEnBitacora("Eliminar", "Clase interactiva", this.getEliminar().getIdClase());
            this.getEliminar().setClEstado('I');
            sieniClaseFacadeRemote.edit(this.getEliminar());
            new ValidationPojo().printMsj("Registro eliminado exitosamente", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }
//        fill();
    }

    public synchronized void guardarConfiguracion() {
        try {
            guardarConfiguracionComponentes();
            guardarPuntosControl();
            registrarEnBitacora("Modificar", "Clase interactiva - config componentes", this.getClaseConfig().getIdClase());
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }

    }

    public void guardarPuntosControl() {
        int contador = 0;
        for (SeccionPlantillaPojo sec : this.getSecciones()) {
            for (PantallaPojo pantalla : sec.getPantallas()) {
                contador++;
            }
        }
        SieniCatPuntos puntos = sieniCatPuntosFacadeRemote.findByClase(this.getClaseConfig().getIdClase());
        if (puntos != null && puntos.getIdCatPuntos() != null) {
            puntos.setCpNumPuntos(contador);
            sieniCatPuntosFacadeRemote.edit(puntos);
        } else {
            puntos = new SieniCatPuntos();
            puntos.setCpEstado('A');
            puntos.setIdClase(this.getClaseConfig());
            puntos.setCpNumPuntos(contador);
            sieniCatPuntosFacadeRemote.create(puntos);
        }
    }

    public void guardarConfiguracionComponentes() {
        List<SieniClaseSupComp> componentes = new ArrayList<>();
        List<SieniClaseSupComp> eliminados = new ArrayList<>();
        int cont;
        for (SeccionPlantillaPojo sec : this.getSecciones()) {
            for (PantallaPojo pantalla : sec.getPantallas()) {
                cont = 1;
                for (ComponenteInteractivoPojo comp : pantalla.getComponentes()) {
                    comp.getClaseSuperComp().setScVisible(comp.getVisible() ? 'S' : 'N');
                    comp.getClaseSuperComp().setScOrden(cont++);
                    componentes.add(comp.getClaseSuperComp());
                }
            }
        }
        for (ComponenteInteractivoPojo elimn : this.getComponentesEliminados()) {
            eliminados.add(elimn.getClaseSuperComp());
        }
        sieniClaseSupCompFacadeRemote.merge(componentes, eliminados);
        //elimina las interacciondes de los componentes relacionados que fueron eliminados
        sieniInteEntrCompFacadeRemote.merge(new ArrayList<SieniInteEntrComp>(), this.getInteracEliminados());
        fillConfigura(this.getClaseConfig());
        new ValidationPojo().printMsj("Configuración guardada exitosamente", FacesMessage.SEVERITY_INFO);
    }

    public synchronized void guardarConfiguracionInteracciones() {
        try {
            int cont = 1;
            for (SieniInteEntrComp actual : this.getInteracTotal()) {
                actual.setIeOrden(cont++);
            }
            sieniInteEntrCompFacadeRemote.merge(this.getInteracTotal(), this.getInteracEliminados());
            new ValidationPojo().printMsj("Configuración guardada exitosamente", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            new ValidationPojo().printMsj("Ocurrió un error:" + e, FacesMessage.SEVERITY_ERROR);
        }

    }

    public void agregarInteraccionesMultiples() {
        SeccionPlantillaPojo seccionActual = this.getSecciones().get(this.getIdElemenActive());
        Integer index = seccionActual.getPantallaActual();
        if (index == null) {
            index = 0;
        }
        SieniTipoElemPlantilla tipoPlant = this.getSecciones().get(this.getIdElemenActive()).getIdElemPlantilla().getIdTipoElemPlantilla();
        Integer nPantalla = seccionActual.getPantallas().get(index).getNumPantalla();
        for (InteraccionMultiplePojo actual : this.getListaMultiple()) {
            actual.getInteraccionEntreComps().setIeSupC1(this.getNuevaInteracMult1().getIeSupC1());
            actual.getInteraccionEntreComps().setIeEventoC1(this.getNuevaInteracMult1().getIeEventoC1());
            actual.getInteraccionEntreComps().setIeNPantalla(nPantalla);
            actual.getInteraccionEntreComps().setIdTipoElemPlantilla(tipoPlant);
            actual.getInteraccionEntreComps().setIdClase(this.getClaseConfig().getIdClase());
            actual.getInteraccionEntreComps().setIeEstado('A');
            this.getInteracTotal().add(actual.getInteraccionEntreComps());
        }
        //actualiza la lista de interacciones agregadas
        ControlInteractivoUtils ciu = new ControlInteractivoUtils();
        ciu.setSecciones(this.getSecciones());
        ciu.setTotalInteracc(this.getInteracTotal());
        this.setFuncionJS(ciu.getCodigoEventosEntreComp());
        List<SieniInteEntrComp> listaActual = getInteractByPantallaTipoElemPlantilla(this.getSecciones().get(this.getIdElemenActive()).getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla(), seccionActual.getPantallas().get(index).getNumPantalla());
        this.setInteracPantallaElemPlantillaActual(listaActual);
        this.setOpSelectMulti(0);
        updateInteractByTipoElemPlanPantalla(null);
        this.setIndexMenu(5);
    }

    public void agregarInteraccion() {
        SeccionPlantillaPojo seccionActual = this.getSecciones().get(this.getIdElemenActive());
        Integer index = seccionActual.getPantallaActual();
        if (index == null) {
            index = 0;
        }
        SieniTipoElemPlantilla tipoPlant = this.getSecciones().get(this.getIdElemenActive()).getIdElemPlantilla().getIdTipoElemPlantilla();
        Integer nPantalla = seccionActual.getPantallas().get(index).getNumPantalla();
        if (validaAddInteraccion(this.getNuevaInterac(), getInteractByPantallaTipoElemPlantilla(this.getSecciones().get(this.getIdElemenActive()).getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla(), seccionActual.getPantallas().get(index).getNumPantalla()))) {
            this.getNuevaInterac().setIdInteEntreComp(-Long.parseLong(new DateUtils().getTime()));
            this.getNuevaInterac().setIeNPantalla(nPantalla);
            this.getNuevaInterac().setIdTipoElemPlantilla(tipoPlant);
            this.getNuevaInterac().setIdClase(this.getClaseConfig().getIdClase());
            this.getNuevaInterac().setIeEstado('A');

            this.getInteracTotal().add(this.getNuevaInterac());
            ControlInteractivoUtils ciu = new ControlInteractivoUtils();
            ciu.setSecciones(this.getSecciones());
            ciu.setTotalInteracc(this.getInteracTotal());
            this.setFuncionJS(ciu.getCodigoEventosEntreComp());
            List<SieniInteEntrComp> listaActual = getInteractByPantallaTipoElemPlantilla(this.getSecciones().get(this.getIdElemenActive()).getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla(), seccionActual.getPantallas().get(index).getNumPantalla());
//        listaActual.add(this.getNuevaInterac());
            this.setInteracPantallaElemPlantillaActual(listaActual);
            this.setNuevaInterac(new SieniInteEntrComp());
            this.getNuevaInterac().setIeRetraso(0);
            this.setIndexMenu(5);
        }
    }

    public boolean validaAddInteraccion(SieniInteEntrComp nuevo, List<SieniInteEntrComp> listaInteracActual) {
        boolean valido;
        List<ValidationPojo> validaciones = new ArrayList<>();
        validaciones.add(new ValidationPojo(tieneInteraccion(nuevo, listaInteracActual), "Ya existe la interacción entre esos componentes y eventos", FacesMessage.SEVERITY_ERROR));
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    private boolean tieneInteraccion(SieniInteEntrComp nueva, List<SieniInteEntrComp> listaInteracActual) {
        boolean ret = false;
        if (listaInteracActual != null) {
            for (SieniInteEntrComp inteAc : listaInteracActual) {
                if (nueva.getIeSupC1().getIdSuperCompon().equals(inteAc.getIeSupC1().getIdSuperCompon())
                        && nueva.getIeSupC2().getIdSuperCompon().equals(inteAc.getIeSupC2().getIdSuperCompon())
                        && nueva.getIeEventoC1().getIdEvento().equals(inteAc.getIeEventoC1().getIdEvento())
                        && nueva.getIeEventoC2().getIdEvento().equals(inteAc.getIeEventoC2().getIdEvento())) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    public void refreshConfig() {
    }

    public void agregarComponentePantallaActual() {
        CopiaArchivos ca = new CopiaArchivos();
        ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
        SeccionPlantillaPojo seccionActual = this.getSecciones().get(this.getIdElemenActive());
        int index = this.getSecciones().get(this.getIdElemenActive()).getPantallaActual();
        if (validaAddComponente(this.getNuevoComponente(), seccionActual.getPantallas().get(index).getComponentes())) {
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
            seccionActual.getPantallas().get(index).getComponentes().addAll(a);
            this.setNuevoComponente(new SieniClaseSupComp());
            this.setComponentesPantallaActual(getComponActuales());
            mostrarTodos();
        }
    }

    public boolean validaAddComponente(SieniClaseSupComp nuevo, List<ComponenteInteractivoPojo> listaInteracActual) {
        boolean valido;
        List<ValidationPojo> validaciones = new ArrayList<>();
        validaciones.add(new ValidationPojo(tieneComponente(nuevo, listaInteracActual), "Ya existe el componente en esta pantalla", FacesMessage.SEVERITY_ERROR));
        valido = !ValidationPojo.printErrores(validaciones);
        return valido;
    }

    private boolean tieneComponente(SieniClaseSupComp nueva, List<ComponenteInteractivoPojo> listaInteracActual) {
        boolean ret = false;
        for (ComponenteInteractivoPojo inteAc : listaInteracActual) {
            if (nueva.getFCompSuperCompon().getIdSuperCompon().equals(inteAc.getClaseSuperComp().getFCompSuperCompon().getIdSuperCompon())) {
                ret = true;
            }
        }
        return ret;
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
            new ValidationPojo().printMsj("No se puede agregar más pantallas a este elemento de plantilla, máximo " + maxPantallas + " pantallas", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void eliminarInteraccion() {
        SieniInteEntrComp eliminado = this.getInteracEliminada();
        this.getInteracEliminados().add(eliminado);
        this.getInteracTotal().remove(eliminado);
        ControlInteractivoUtils ciu = new ControlInteractivoUtils();
        ciu.setSecciones(this.getSecciones());
        ciu.setTotalInteracc(this.getInteracTotal());
        this.setFuncionJS(ciu.getCodigoEventosEntreComp());

        SeccionPlantillaPojo seccionActual = this.getSecciones().get(this.getIdElemenActive());
        Integer index = seccionActual.getPantallaActual();

        if (index == null) {
            index = 0;
        }
        List<SieniInteEntrComp> listaActual = getInteractByPantallaTipoElemPlantilla(this.getSecciones().get(this.getIdElemenActive()).getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla(), seccionActual.getPantallas().get(index).getNumPantalla());
//        listaActual.remove(eliminado);
        this.setInteracPantallaElemPlantillaActual(listaActual);

        this.setIndexMenu(5);
    }

    public void setInteraccionEliminada(SieniInteEntrComp eliminado) {
        this.setInteracEliminada(eliminado);
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
            new ValidationPojo().printMsj("El elemento de plantilla debe tener almenos 1 página", FacesMessage.SEVERITY_ERROR);
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
        if (this.getInteracTotal() != null) {
            for (SieniInteEntrComp inte : this.getInteracTotal()) {
                if (inte.getIeSupC1().getIdSuperCompon().equals(this.getSuperCompEliminado().getSuperComp().getIdSuperCompon())
                        || inte.getIeSupC2().getIdSuperCompon().equals(this.getSuperCompEliminado().getSuperComp().getIdSuperCompon())) {
                    this.getInteracEliminados().add(inte);
                }
            }
        }
        this.setComponentesPantallaActual(getComponActuales());
    }

    public List<ComponenteInteractivoPojo> getComponActuales() {
        SeccionPlantillaPojo seccionActual = this.getSecciones().get(this.getIdElemenActive());
        Integer index = seccionActual.getPantallaActual();
        if (index == null) {
            index = 0;
        }
        Long idSeccionActual = this.getSecciones().get(this.getIdElemenActive()).getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla();
        Integer nPantalla = seccionActual.getPantallas().get(index).getNumPantalla();
        this.setIdTipoElempPlantilla(idSeccionActual);
        this.setNpantalla(nPantalla);
        for (ComponenteInteractivoPojo actual : seccionActual.getPantallas().get(index).getComponentes()) {
            actual.setMostrar(false);
        }
        return seccionActual.getPantallas().get(index).getComponentes();
    }

    public void resetMostrar() {
        SeccionPlantillaPojo seccionActual = this.getSecciones().get(this.getIdElemenActive());
        Integer index = seccionActual.getPantallaActual();
        if (index == null) {
            index = 0;
        }
        for (ComponenteInteractivoPojo actual : seccionActual.getPantallas().get(index).getComponentes()) {
            actual.setMostrar(false);
        }
    }

    public void ocultarTodos() {
        for (ComponenteInteractivoPojo actual : getComponentesPantallaActual()) {
            actual.setMostrar(true);
        }
    }

    public void mostrarTodos() {
        for (ComponenteInteractivoPojo actual : getComponentesPantallaActual()) {
            actual.setMostrar(false);
        }
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

        this.getNuevaInteracMult2().setSource(convertSuperCompon2ToInteractEntreCompon(getCompDisponibles()));
        this.getNuevaInteracMult2().setTarget(new ArrayList<SieniInteEntrComp>());
        //en cada refresco setea el primer componente de la lista como actual
        if (this.getCompDisponibles() != null && !this.getCompDisponibles().isEmpty()) {
            this.getNuevaInterac().setIeSupC1(this.getCompDisponibles().get(0));
            this.getNuevaInterac().setIeSupC2(this.getCompDisponibles().get(0));
        } else {
            this.getNuevaInterac().setIeSupC1(new SieniSuperCompon());
            this.getNuevaInterac().setIeSupC2(new SieniSuperCompon());
        }
        //si hay componentes disponibles
        if (this.getNuevaInterac().getIeSupC1() != null && this.getNuevaInterac().getIeSupC1().getIdSuperCompon() != null) {
            updateEv1(this.getNuevaInterac().getIeSupC1().getIdSuperCompon());
            updateEv2(this.getNuevaInterac().getIeSupC2().getIdSuperCompon());
        } else {
            this.setEvn1(new ArrayList<SieniEvento>());
            this.setEvn2(new ArrayList<SieniEvento>());
        }

        //inicializa los datos para las interacciones multiples
        if (this.getCompDisponibles() != null && !this.getCompDisponibles().isEmpty()) {
            this.getNuevaInteracMult1().setIeSupC1(this.getCompDisponibles().get(0));
            updateEv1Multi(this.getNuevaInteracMult1().getIeSupC1().getIdSuperCompon());
            if (this.getEvn1Multi() != null && !this.getEvn1Multi().isEmpty()) {
                this.getNuevaInteracMult1().setIeEventoC1(this.getEvn1Multi().get(0));
            }
        } else {
            this.getNuevaInteracMult1().setIeSupC1(new SieniSuperCompon());
            this.getNuevaInteracMult1().setIeEventoC1(new SieniEvento());
            this.setEvn1Multi(new ArrayList<SieniEvento>());
        }
    }

    public void updateEventosComps2Seleccionados(TabChangeEvent ev) {
        if (this.getOpSelectMulti() != null && this.getOpSelectMulti().equals(1)) {
            List<InteraccionMultiplePojo> listaSelec2 = new ArrayList<>();
            InteraccionMultiplePojo nuevo;
            for (SieniInteEntrComp actual : this.getNuevaInteracMult2().getTarget()) {
                nuevo = new InteraccionMultiplePojo();
                nuevo.setInteraccionEntreComps(actual);
                nuevo.setEventos(getEventoDiferenteBySuperCompon(sieniCompInteraccionFacadeRemote.findByIdSuperComp(actual.getIeSupC2().getIdSuperCompon())));
                listaSelec2.add(nuevo);
            }
            this.setListaMultiple(listaSelec2);
        }
//        this.getNuevaInteracMult1().getIeEventoC1();
        //set en formulario
    }

    public void updatePuntoCtrlSeleccionados(TabChangeEvent ev) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");

        if (loginBean.getTipoRol().equals("0")) {//alumno
            SeccionPlantillaPojo seccionActual = this.getSecciones().get(this.getIdElemenActive());
            Integer index = seccionActual.getPantallaActual();

            if (index == null) {
                index = 0;
            }
            //registra punto de control actual
            SieniPntosContrl nuevo;
            SieniTipoElemPlantilla tipoElem = this.getSecciones().get(this.getIdElemenActive()).getIdElemPlantilla().getIdTipoElemPlantilla();
            Integer nPantalla = seccionActual.getPantallas().get(index).getNumPantalla();
            //find punto de control by pantalla seccion alumno clase
            nuevo = sieniPntosContrlFacadeRemote.findPuntos(tipoElem.getIdTipoElemPlantilla(), nPantalla, this.getClaseConfig().getIdClase(), loginBean.getAlumno().getIdAlumno());
            if (nuevo.getIdPntosContrl() == null) {
                nuevo.setIdClase(this.getClaseConfig());
                nuevo.setIdAlumno(loginBean.getAlumno().getIdAlumno());
                nuevo.setIdTipoElemPlantilla(tipoElem);
                nuevo.setPcPantalla(nPantalla);
                nuevo.setPcEstado('V');
                sieniPntosContrlFacadeRemote.create(nuevo);
            } else {
                //cuando la pantalla ya existe pero ha sido eliminada se actualiza el estado
                nuevo.setPcEstado('V');
                sieniPntosContrlFacadeRemote.edit(nuevo);
            }

        }
        setIndexMenu(3);
    }

    public List<SieniInteEntrComp> convertSuperCompon2ToInteractEntreCompon(List<SieniSuperCompon> componentes) {
        List<SieniInteEntrComp> ret = new ArrayList<>();
        SieniInteEntrComp nuevo;
        Long inc = -Long.parseLong(new DateUtils().getTime());
        for (SieniSuperCompon actual : componentes) {
            nuevo = new SieniInteEntrComp();
            nuevo.setIdInteEntreComp(inc);
            nuevo.setIeSupC2(actual);
            nuevo.setIeRetraso(0);
            ret.add(nuevo);
            inc--;
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

    public void getEventosC1Multi(ValueChangeEvent ev) {
        SieniSuperCompon sup = (SieniSuperCompon) ev.getNewValue();
        if (sup != null) {
            updateEv1Multi(sup.getIdSuperCompon());
            if (this.getEvn1Multi() != null && !this.getEvn1Multi().isEmpty()) {
                this.getNuevaInteracMult1().setIeEventoC1(this.getEvn1Multi().get(0));
            } else {
                this.getNuevaInteracMult1().setIeEventoC1(new SieniEvento());
            }
        } else {
            this.setEvn1Multi(new ArrayList<SieniEvento>());
        }
    }

    public void getEventosC2(ValueChangeEvent ev) {
        SieniSuperCompon sup = (SieniSuperCompon) ev.getNewValue();
        if (sup != null) {
            updateEv2(sup.getIdSuperCompon());
        }
    }

    public void updateEv1Multi(Long idSuperCompon) {
        this.setEvn1Multi(getEventoDiferenteBySuperCompon(sieniCompInteraccionFacadeRemote.findByIdSuperComp(idSuperCompon)));
    }

    public void updateEv1(Long idSuperCompon) {
        this.setEvn1(getEventoDiferenteBySuperCompon(sieniCompInteraccionFacadeRemote.findByIdSuperComp(idSuperCompon)));
    }

    public void updateEv2(Long idSuperCompon) {
        this.setEvn2(getEventoDiferenteBySuperCompon(sieniCompInteraccionFacadeRemote.findByIdSuperComp(idSuperCompon)));
    }

    public void updateComponConfigura(TabChangeEvent ev) {
        setComponentesPantallaActual(getComponActuales());
    }
}
