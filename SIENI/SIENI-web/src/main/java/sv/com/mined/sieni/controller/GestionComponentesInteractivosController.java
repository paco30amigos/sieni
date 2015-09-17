/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
import utils.CopiaArchivos;

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
            sieniSuperComponFacadeRemote.create(this.getNuevo());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Guardar", "Componente interactivo", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
            FacesMessage msg = new FacesMessage("Componente Interactivo Creado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.setNuevo(new SieniSuperCompon());
            fill(); 
            this.setIndexMenu(0);
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

    public void configurar(SieniSuperCompon configura) {
        CopiaArchivos ca = new CopiaArchivos();
        ca.setSieniArchivoFacadeRemote(sieniArchivoFacadeRemote);
        this.setConfig(configura);
        if (configura.getScAlto() == null) {
            configura.setScAlto(240);
        }
        if (configura.getScAncho() == null) {
            configura.setScAncho(320);
        }
        this.getListaOrdenable().setSource(new ArrayList<FileStreamedPojo>());
        this.getListaOrdenable().setTarget(new ArrayList<FileStreamedPojo>());

        //archivos de cada componente
        List<SieniArchivo> archivos = sieniArchivoFacadeRemote.findByIdSuperComp(configura.getIdSuperCompon());
        for (SieniArchivo actual : archivos) {
            ca.copyDataToResource(actual);
        }
        this.setListaArchivosComponente(archivos);
        //componentes que integran al super componente
        List<SieniComponente> componentes = sieniComponenteFacadeRemote.findByIdSuperComp(configura.getIdSuperCompon());
        for (SieniComponente actual : componentes) {
            addArchivoExistente(actual);
        }
        List<SieniCompInteraccion> interaccion = sieniCompInteraccionFacadeRemote.findByIdSuperComp(configura.getIdSuperCompon());
        this.setListaInteraccion(interaccion);

        this.setEventos(this.sieniEventoFacadeRemote.findByTipoSuperComponente(this.getConfig().getIdTipoSuperCompon().getIdTipoSuperCompon()));
        this.setAcciones(this.sieniAccionFacadeRemote.findByTipoSuperComponente(this.getConfig().getIdTipoSuperCompon().getIdTipoSuperCompon()));
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
            FacesMessage msg = new FacesMessage("Componente Interactivo Modificado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            resetModificaForm();
            this.setIndexMenu(0);
        }
        fill();
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
        ca.copyDataToResource(sux.getArchivoBD());
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
        guardarConfiguracionComponente();
        guardarConfiguracionInteraccion();
        configurar(this.getConfig());
    }

    private void guardarConfiguracionInteraccion() {
        List<SieniComponente> componentes = sieniComponenteFacadeRemote.findByIdSuperComp(this.getConfig().getIdSuperCompon());
        //limpia los id temporales
        int orden = 1;
        if (componentes != null && !componentes.isEmpty()) {
            for (int i = 0; i < this.getListaInteraccion().size(); i++) {
                this.getListaInteraccion().get(i).setIdComponente(componentes.get(0));
                this.getListaInteraccion().get(i).setInOrden(orden);
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
        this.setNuevaInterac(new SieniCompInteraccion());
    }

    public void eliminarInteraccion(SieniCompInteraccion eliminado) {
        this.getListaInteraccionesElimn().add(eliminado);
        for (int i = 0; i < this.getListaInteraccion().size(); i++) {
            if (this.getListaInteraccion().get(i).getIdCompInteraccion().equals(eliminado.getIdCompInteraccion())) {
                this.getListaInteraccion().remove(i);
                break;
            }
        }
    }

    public void interaccEdit(RowEditEvent e) {
        SieniCompInteraccion editado = (SieniCompInteraccion) e.getObject();
        for (int i = 0; i < this.getListaInteraccion().size(); i++) {
            if (editado.getIdCompInteraccion().equals(this.getListaInteraccion().get(i).getIdCompInteraccion())) {
                this.getListaInteraccion().set(i, editado);
                break;
            }
        }
    }
}
