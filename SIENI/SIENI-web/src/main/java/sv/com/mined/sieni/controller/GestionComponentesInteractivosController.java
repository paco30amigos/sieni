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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DualListModel;
import sv.com.mined.sieni.SieniAccionFacadeRemote;
import sv.com.mined.sieni.SieniArchivoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniCompInteraccionFacadeRemote;
import sv.com.mined.sieni.SieniComponenteFacadeRemote;
import sv.com.mined.sieni.SieniEventoFacadeRemote;
import sv.com.mined.sieni.SieniSuperComponFacadeRemote;
import sv.com.mined.sieni.SieniTipoSuperComponFacadeRemote;
import sv.com.mined.sieni.form.GestionComponentesInteractivosForm;
import sv.com.mined.sieni.model.SieniAccion;
import sv.com.mined.sieni.model.SieniArchivo;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniCompInteraccion;
import sv.com.mined.sieni.model.SieniComponente;
import sv.com.mined.sieni.model.SieniEvento;
import sv.com.mined.sieni.model.SieniSuperCompon;
import sv.com.mined.sieni.model.SieniTipoComponente;
import sv.com.mined.sieni.model.SieniTipoSuperCompon;
import sv.com.mined.sieni.pojos.controller.FileStreamedPojo;
import sv.com.mined.sieni.pojos.controller.ValidationPojo;
import utils.CopiaArchivos;
import utils.DateUtils;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "gestionComponentesInteractivosController")
public class GestionComponentesInteractivosController extends GestionComponentesInteractivosForm {

    @EJB
    private SieniComponenteFacadeRemote sieniComponenteFacadeRemote;
    @EJB
    private SieniAccionFacadeRemote sieniAccionFacadeRemote;
    @EJB
    private SieniEventoFacadeRemote sieniEventoFacadeRemote;
    @EJB
    private SieniSuperComponFacadeRemote sieniSuperComponFacadeRemote;
    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;
    @EJB
    private SieniTipoSuperComponFacadeRemote sieniTipoSuperComponFacadeRemote;
    @EJB
    private SieniArchivoFacadeRemote sieniArchivoFacadeRemote;
    @EJB
    private SieniCompInteraccionFacadeRemote sieniCompInteraccionFacadeRemote;

    @PostConstruct
    public void init() {
        this.setListaArchivosEliminados(new ArrayList<SieniComponente>());
        this.setListaInteraccionesElimn(new ArrayList<SieniCompInteraccion>());
        this.setNuevo(new SieniSuperCompon());
        this.setListaOrdenable(new DualListModel<FileStreamedPojo>());
        this.getListaOrdenable().setSource(new ArrayList<FileStreamedPojo>());
        this.getListaOrdenable().setTarget(new ArrayList<FileStreamedPojo>());
        this.setListaEventosDiferentes(new ArrayList());
        this.setArchivoTexto(new SieniArchivo());
        this.setTexto("");
        this.setTextoAux("");
        fill();
    }

    private void fill() {
        this.setDatosList(sieniSuperComponFacadeRemote.findAllNoInactivos());
        this.setListaTipo(this.sieniTipoSuperComponFacadeRemote.findAll());
        this.setListaTipoModifica(this.sieniTipoSuperComponFacadeRemote.findAll());
        this.setNuevaInterac(new SieniCompInteraccion());
        this.getNuevaInterac().setIdAccion(new SieniAccion());
        this.getNuevaInterac().setIdEvento(new SieniEvento());
    }

    public void guardar() {
        for (SieniTipoSuperCompon actual : this.getListaTipo()) {
            if (this.getTipoSuperCompon().equals(actual.getIdTipoSuperCompon())) {
                this.getNuevo().setIdTipoSuperCompon(actual);
                break;
            }
        }
        if (validarNuevo(this.getNuevo())) {//valida el guardado
            this.getNuevo().setScEstado('A');
            sieniSuperComponFacadeRemote.create(this.getNuevo());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Componente interactivo", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
            new ValidationPojo().printMsj("Componente Interactivo Creado Exitosamente", FacesMessage.SEVERITY_INFO);
            this.setNuevo(new SieniSuperCompon());
            fill();
        }
    }

    public void quitarFormato(SieniSuperCompon actual) {

    }

    public void refresh() {
        fill();
    }

    public boolean validarNuevo(SieniSuperCompon nuevo) {
        boolean ban = true;

        return ban;
    }

    public void cancelar() {
    }

    public void refreshConfig() {
        actualizarEditor();
    }

    //metodos para modificacion de datos
    public void modificar(SieniSuperCompon modificado) {
        this.setModifica(modificado);
        this.setIndexMenu(2);
    }

    //metodos para modificacion de datos
    public void eliminar(SieniSuperCompon eliminado) {
        this.setEliminar(eliminado);
    }

    public void actualizarEditor() {
        this.setTextoAux(this.getTexto());
    }

    public void fillConfigura(SieniSuperCompon configura) {
        CopiaArchivos ca = new CopiaArchivos();
        ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
        this.setConfig(configura);
        if (configura.getScAlto() == null) {
            configura.setScAlto(240);
        }
        if (configura.getScAncho() == null) {
            configura.setScAncho(320);
        }
        if (this.getNuevaInterac().getInDuracion() == null) {
            this.getNuevaInterac().setInDuracion(1000);
        }
        this.getListaOrdenable().setSource(new ArrayList<FileStreamedPojo>());
        this.getListaOrdenable().setTarget(new ArrayList<FileStreamedPojo>());

        //archivos de cada componente
        List<SieniArchivo> archivos = sieniArchivoFacadeRemote.findByIdSuperComp(configura.getIdSuperCompon());
//        //si es texto
//        if (this.getConfig().getIdTipoSuperCompon().getIdTipoSuperCompon().equals(new Long("7"))) {
//            if (archivos == null) {
//                archivos = new ArrayList();
//            }
//            if (archivos.isEmpty()) {
//                SieniArchivo archivo = crearArchivo();
//                archivos.add(archivo);
//                getListaArchivos("T");//actualiza los archivos
//                this.setIdArchivo(archivo.getIdArchivo());
//                agregarArchivoUnico();
//                guardarConfiguracionComponente();
//            }
//        }
        for (SieniArchivo actual : archivos) {
            if (actual.getArTipo().equals(this.getArchivosCopiables()[0])
                    || actual.getArTipo().equals(getArchivosCopiables()[1])
                    || actual.getArTipo().equals(getArchivosCopiables()[2])) {//si es archivo copiable o multimedia
                ca.copyDataToResource(actual);
            } else {//si es de otro tipo (texto por ejemplo)
                this.setTexto(new String(ca.getData(actual)));
                this.setTextoAux(this.getTexto());
            }
        }
        this.setListaArchivosComponente(archivos);
        //componentes que integran al super componente
        List<SieniComponente> componentes = sieniComponenteFacadeRemote.findByIdSuperComp(configura.getIdSuperCompon());
        for (SieniComponente actual : componentes) {
            addArchivoExistente(actual);
        }
        //obtiene las interacciones de este super componente
        List<SieniCompInteraccion> interaccion = sieniCompInteraccionFacadeRemote.findByIdSuperComp(configura.getIdSuperCompon());
        this.setListaInteraccion(interaccion);

        //actualiza los eventos para la vista previa
        actualizarEventoSelec();
        //obtiene los eventos para probar en el ejemplo
        this.setListaEventosDiferentes(getEventosDiferentes(interaccion));
        //eventos para seleccionar
        this.setEventos(this.sieniEventoFacadeRemote.findByTipoSuperComponente(this.getConfig().getIdTipoSuperCompon().getIdTipoSuperCompon()));
        //acciones para seleccionar
        this.setAcciones(this.sieniAccionFacadeRemote.findByTipoSuperComponente(this.getConfig().getIdTipoSuperCompon().getIdTipoSuperCompon()));
    }

    public List<SieniEvento> getEventosDiferentes(List<SieniCompInteraccion> total) {
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

    public void configurar(SieniSuperCompon configura) {
        this.setTexto("");
        this.setTextoAux("");
        fillConfigura(configura);
        this.setIndexMenu(4);
    }

    //metodos para modificacion de datos
    public void mostrar(SieniSuperCompon ver) {
        this.setVer(ver);
        this.setIndexMenu(3);
    }

    public void guardarModifica() {
        for (SieniTipoSuperCompon actual : this.getListaTipoModifica()) {
            if (this.getTipoSuperComponModifica().equals(actual.getIdTipoSuperCompon())) {
                this.getModifica().setIdTipoSuperCompon(actual);
                break;
            }
        }
        if (validarModifica(this.getModifica())) {//valida el guardado
            sieniSuperComponFacadeRemote.edit(this.getModifica());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Modifica", "Componente interactivo", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
            new ValidationPojo().printMsj("Componente Interactivo Modificado Exitosamente", FacesMessage.SEVERITY_INFO);
            fill();
        }
    }

    public void resetModificaForm() {
        this.setModifica(new SieniSuperCompon());
    }

    public boolean validarModifica(SieniSuperCompon nuevo) {
        boolean ban = true;

        return ban;
    }

    public void eliminarArchivo() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
        sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Eliminar", "Componente interactivo", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
        this.getEliminar().setScEstado('I');
        sieniSuperComponFacadeRemote.edit(this.getEliminar());
        fill();
    }

    public void agregarArchivo() {
        Long id = this.getIdArchivo();
        SieniArchivo nuevo = null;
        for (SieniArchivo actual : this.getListaArchivos()) {
            if (actual.getIdArchivo().equals(id)) {
                nuevo = actual;
                break;
            }
        }
        if (nuevo != null) {
            SieniComponente aux = new SieniComponente();
            List<SieniArchivo> lista = new ArrayList<>();
            lista.add(nuevo);
            aux.setSieniArchivoList(lista);
            addArchivo(aux);
        }
    }

    public void agregarArchivoUnico() {
        Long id = this.getIdArchivo();
        SieniArchivo nuevo = null;
        for (SieniArchivo actual : this.getListaArchivos()) {
            if (actual.getIdArchivo().equals(id)) {
                nuevo = actual;
                break;
            }
        }
        if (nuevo != null) {
            SieniComponente aux = new SieniComponente();
            List<SieniArchivo> lista = new ArrayList<>();
            lista.add(nuevo);
            aux.setSieniArchivoList(lista);
            this.getListaOrdenable().setTarget(this.getListaOrdenable().getSource());
            this.getListaOrdenable().setSource(new ArrayList<FileStreamedPojo>());
            addArchivo(aux);
        }
    }

    public void addArchivo(SieniComponente actual) {
        FileStreamedPojo sux = new FileStreamedPojo();
        sux.setIndex(this.getListaOrdenable().getSource().size());
        sux.setArchivoBD(actual.getSieniArchivoList().get(0));//archivo unico
        sux.setComponente(actual);
        CopiaArchivos ca = new CopiaArchivos();
        ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
        if (sux.getArchivoBD().getArTipo().equals(this.getArchivosCopiables()[0])
                || sux.getArchivoBD().getArTipo().equals(this.getArchivosCopiables()[1])
                || sux.getArchivoBD().getArTipo().equals(this.getArchivosCopiables()[2])) {
            ca.copyDataToResource(sux.getArchivoBD());
        }
        this.getListaOrdenable().getSource().add(sux);
    }

    public void addArchivoExistente(SieniComponente actual) {
        FileStreamedPojo sux = new FileStreamedPojo();
        sux.setIndex(this.getListaOrdenable().getSource().size());
        sux.setArchivoBD(getArchivoComponente(actual.getIdArchivo()));//archivo unico
        sux.setComponente(actual);
        this.getListaOrdenable().getSource().add(sux);
    }

    public SieniArchivo getArchivoComponente(Long idArchivo) {
        SieniArchivo ret = null;
        for (SieniArchivo actual : this.getListaArchivosComponente()) {
            if (idArchivo.equals(actual.getIdArchivo())) {
                ret = actual;
                break;
            }
        }
        return ret;
    }

    public void addArchivoUnico(SieniArchivo actual) {
        FileStreamedPojo sux = new FileStreamedPojo();
        this.getListaOrdenable().setSource(new ArrayList<FileStreamedPojo>());
        sux.setIndex(this.getListaOrdenable().getSource().size());
        sux.setArchivoBD(actual);
        this.getListaOrdenable().getSource().add(sux);
    }

    public void guardarConfiguracion() {
        guardarTexto();
        guardarConfiguracionComponente();
        guardarConfiguracionInteraccion();
        new ValidationPojo().printMsj("Configuracion guardada Exitosamente", FacesMessage.SEVERITY_INFO);
        fillConfigura(this.getConfig());
    }

    private void guardarConfiguracionInteraccion() {
        List<SieniComponente> componentes = sieniComponenteFacadeRemote.findByIdSuperComp(this.getConfig().getIdSuperCompon());
        //limpia los id temporales
        int orden = 1;
        if (componentes != null && !componentes.isEmpty()) {
            for (int i = 0; i < this.getListaInteraccion().size(); i++) {
                this.getListaInteraccion().get(i).setIdComponente(componentes.get(0));
                this.getListaInteraccion().get(i).setInOrden(orden);
                this.getListaInteraccion().get(i).setInEstado(new Character(('A')));
                if (this.getListaInteraccion().get(i).getIdCompInteraccion() != null && this.getListaInteraccion().get(i).getIdCompInteraccion() < 0) {
                    this.getListaInteraccion().get(i).setIdCompInteraccion(null);
                }
                orden++;
            }
            for (int i = 0; i < this.getListaInteraccionesElimn().size(); i++) {
                if (this.getListaInteraccionesElimn().get(i).getIdCompInteraccion() != null && this.getListaInteraccionesElimn().get(i).getIdCompInteraccion() < 0) {
                    this.getListaInteraccionesElimn().get(i).setIdCompInteraccion(null);
                }
            }
            sieniCompInteraccionFacadeRemote.merge(this.getListaInteraccion(), this.getListaInteraccionesElimn());
        }
    }

    private void guardarConfiguracionComponente() {
        List<SieniComponente> lista = new ArrayList<>();
        List<SieniComponente> listaEliminados = new ArrayList<>();
        SieniComponente nuevo;
        Integer nuevoOrden = 0;
        for (FileStreamedPojo actual : this.getListaOrdenable().getSource()) {
            nuevo = actual.getComponente();
            nuevo.setIdSuperCompon(this.getConfig());
            nuevo.setIdTipoComponente(getTipoComponenteBySuperCompon(this.getConfig()));
            nuevo.setIdArchivo(actual.getArchivoBD().getIdArchivo());
            nuevo.setCpOrden(nuevoOrden);
            nuevo.setCpEstado('A');
            lista.add(nuevo);
            nuevoOrden++;
        }
        for (FileStreamedPojo actual : this.getListaOrdenable().getTarget()) {
            nuevo = actual.getComponente();
            nuevo.setIdSuperCompon(this.getConfig());
            nuevo.setIdTipoComponente(getTipoComponenteBySuperCompon(this.getConfig()));
            nuevo.setIdArchivo(actual.getArchivoBD().getIdArchivo());
            listaEliminados.add(nuevo);
        }
        sieniComponenteFacadeRemote.merge(lista, listaEliminados);
        sieniSuperComponFacadeRemote.edit(this.getConfig());
    }

    public SieniTipoComponente getTipoComponente(Integer tipo) {
        SieniTipoComponente ret = new SieniTipoComponente();
        switch (tipo) {
            case 1://imagen
                ret.setIdTipoComponente(1L);
                break;
            case 2://video
                ret.setIdTipoComponente(2L);
                break;
            case 3://audio
                ret.setIdTipoComponente(3L);
                break;
            case 4://texto
                ret.setIdTipoComponente(4L);
                break;
        }
        return ret;
    }

    public SieniTipoComponente getTipoComponenteBySuperCompon(SieniSuperCompon superCompon) {
        Long id = superCompon.getIdTipoSuperCompon().getIdTipoSuperCompon();
        SieniTipoComponente ret = new SieniTipoComponente();
        switch (id.intValue()) {
            case 1:
            case 2:
            case 4:
            case 6:
                ret = getTipoComponente(1);
                break;
            case 3:
                ret = getTipoComponente(2);
                break;
            case 5:
                ret = getTipoComponente(3);
                break;
            case 7:
                ret = getTipoComponente(4);
                break;
        }
        return ret;
    }

    public List<SieniArchivo> getListaArchivos(String tipo) {
        List<SieniArchivo> ret;
        ret = sieniArchivoFacadeRemote.findByTipoArchivo(tipo);
        this.setListaArchivos(ret);
        return ret;
    }

    public void agregarInteraccion() {
        this.getNuevaInterac().setIdCompInteraccion(-(new Date().getTime()));
        this.getListaInteraccion().add(this.getNuevaInterac());
        this.setListaEventosDiferentes(getEventosDiferentes(this.getListaInteraccion()));
        actualizarEventoSelec();
        this.setNuevaInterac(new SieniCompInteraccion());
        this.getNuevaInterac().setInDuracion(1000);
    }

    public void eliminarInteraccion(SieniCompInteraccion eliminado) {
        this.getListaInteraccionesElimn().add(eliminado);
        for (int i = 0; i < this.getListaInteraccion().size(); i++) {
            if (this.getListaInteraccion().get(i).getIdCompInteraccion().equals(eliminado.getIdCompInteraccion())) {
                this.getListaInteraccion().remove(i);
                break;
            }
        }
        this.setListaEventosDiferentes(getEventosDiferentes(this.getListaInteraccion()));
        actualizarEventoSelec();
    }

    public void interaccEdit(RowEditEvent e) {
        SieniCompInteraccion editado = (SieniCompInteraccion) e.getObject();
        for (int i = 0; i < this.getListaInteraccion().size(); i++) {
            if (editado.getIdCompInteraccion().equals(this.getListaInteraccion().get(i).getIdCompInteraccion())) {
                this.getListaInteraccion().set(i, editado);
                break;
            }
        }
        actualizarEventoSelec();
    }

    public void actualizarEfectos(ValueChangeEvent e) {
        String evento = (String) e.getNewValue();
        this.setEventoSelect(evento);
        actualizarEventoSelec();
    }

    public void actualizarEventoSelec() {

        List<SieniCompInteraccion> interaccion = this.getListaInteraccion();
        if (interaccion == null || interaccion.isEmpty()) {
            this.setEventoSelect("I");
        }
        String evento = this.getEventoSelect();
        if (!existeEvento(evento)) {
            this.setEventoSelect("I");
        }
        this.setListaInteraccionSelect(new ArrayList<SieniCompInteraccion>());
        for (SieniCompInteraccion actual : interaccion) {
            if (evento.equals(actual.getIdEvento().getEvCodigo())) {
                this.getListaInteraccionSelect().add(actual);
            }
        }
    }

    public boolean existeEvento(String evento) {
        boolean ret = false;
        List<SieniEvento> eventos = getEventosDiferentes(this.getListaInteraccion());
        this.setListaEventosDiferentes(eventos);
        for (SieniEvento actual : eventos) {
            if (actual.getEvCodigo().equals(evento)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    //guarda texto como archivo
    public List<SieniArchivo> guardarArchivoNoMultimedia(List<SieniArchivo> list) {
        list = sieniArchivoFacadeRemote.merge(list, new ArrayList());
        return list;
    }

    private void guardarTexto() {
        List<SieniArchivo> archivos = this.getListaArchivosComponente();
        //si es texto
        if (this.getConfig().getIdTipoSuperCompon().getIdTipoSuperCompon().equals(new Long("7"))) {
            if (archivos == null) {
                archivos = new ArrayList();
            }
            if (archivos.isEmpty()) {
                SieniArchivo archivo = crearArchivo();
                archivos.add(archivo);
                getListaArchivos("T");//actualiza los archivos
                this.setIdArchivo(archivo.getIdArchivo());
                agregarArchivoUnico();
            } else {
                archivos.get(0).setArArchivo(this.getTexto().getBytes());
                guardarArchivoNoMultimedia(archivos);
            }
        }
    }

    private SieniArchivo crearArchivo() {
        SieniArchivo ret = new SieniArchivo();
        List<SieniArchivo> list = new ArrayList<>();
        ret.setArNombre(new DateUtils().getTime());
        ret.setArTipo('T');
        ret.setArArchivo(this.getTexto().getBytes());
        ret.setArEstado("A");
        list.add(ret);
        list = sieniArchivoFacadeRemote.merge(list, new ArrayList<SieniArchivo>());
        return list.get(0);
    }
}
