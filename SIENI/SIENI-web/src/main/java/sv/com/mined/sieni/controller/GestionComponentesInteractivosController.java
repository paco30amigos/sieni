/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.lang.reflect.Array;
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
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.DualListModel;
import sv.com.mined.sieni.SieniAccionFacadeRemote;
import sv.com.mined.sieni.SieniArchivoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniComponenteFacadeRemote;
import sv.com.mined.sieni.SieniEventoFacadeRemote;
import sv.com.mined.sieni.SieniSuperComponFacadeRemote;
import sv.com.mined.sieni.SieniTipoSuperComponFacadeRemote;
import sv.com.mined.sieni.form.GestionComponentesInteractivosForm;
import sv.com.mined.sieni.model.SieniArchivo;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniComponente;
import sv.com.mined.sieni.model.SieniSuperCompon;
import sv.com.mined.sieni.model.SieniTipoComponente;
import sv.com.mined.sieni.model.SieniTipoSuperCompon;
import sv.com.mined.sieni.pojos.controller.FileStreamedPojo;

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

    @PostConstruct
    public void init() {
        this.setListaArchivosEliminados(new ArrayList<SieniComponente>());
        this.setNuevo(new SieniSuperCompon());
        this.setListaOrdenable(new DualListModel<FileStreamedPojo>());
        this.getListaOrdenable().setSource(new ArrayList<FileStreamedPojo>());
        this.getListaOrdenable().setTarget(new ArrayList<FileStreamedPojo>());
        fill();
    }

    private void fill() {
        this.setDatosList(sieniSuperComponFacadeRemote.findAll());
        this.setListaTipo(this.sieniTipoSuperComponFacadeRemote.findAll());
        this.setListaTipoModifica(this.sieniTipoSuperComponFacadeRemote.findAll());
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
        this.setListaArchivosComponente(archivos);
        //componentes que integran al super componente
        List<SieniComponente> componentes = sieniComponenteFacadeRemote.findByIdSuperComp(configura.getIdSuperCompon());
        for (SieniComponente actual : componentes) {
            addArchivoExistente(actual);
        }
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
        sieniSuperComponFacadeRemote.remove(this.getEliminar());
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
        configurar(this.getConfig());
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
}
