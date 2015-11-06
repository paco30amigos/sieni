/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMatriculaFacadeRemote;
import sv.com.mined.sieni.form.RptAlumnosForm;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniMatricula;
import sv.com.mined.sieni.model.SieniSeccion;
import sv.com.mined.sieni.pojos.rpt.RptAlumnosPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author Laptop
 */
@SessionScoped
@ManagedBean(name = "rptAlumnosController")
public class RptAlumnosController extends RptAlumnosForm {

    @EJB
    SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;

    @EJB
    SieniGradoFacadeRemote sieniGradoFacadeRemote;

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @EJB
    private SieniMatriculaFacadeRemote sieniMatriculaFacadeRemote;

    @PostConstruct
    public void init() {
        this.setAnioDesde(new Date());
        this.setAnioHasta(new Date());
        this.setTotalAlumnos("0");
        this.setTipoRpt(0);
        this.setMatriculado(0);
        this.setListDatos(new ArrayList<RptAlumnosPojo>());
        fill();
    }

    public void fill() {
        RptAlumnosPojo elem = new RptAlumnosPojo();
        if (this.getIdGrado() != null && this.getIdGrado().equals(0L)) {
            this.setIdGrado(null);
        }
        if (this.getIdSeccion() != null && this.getIdSeccion().equals(0L)) {
            this.setIdSeccion(null);
        }
        List<SieniAlumno> alumnos = sieniAlumnoFacadeRemote.findAlumnoRpt(this.getAnioDesde(), this.getAnioHasta(), this.getIdGrado(), this.getIdSeccion(), this.getMatriculado());

//        List<SieniMatricula> alumnos = sieniMatriculaFacadeRemote.findAlumnoRpt(this.getAnioDesde(), this.getAnioHasta(), this.getIdGrado(), this.getIdSeccion());
        this.setListDatos(new ArrayList<RptAlumnosPojo>());
        String grado = "";
        String seccion = "";
        String gradoSeccion = "";
        for (SieniAlumno actual : alumnos) {
            if (actual.getMatriculaActual() != null) {
                grado = actual.getMatriculaActual().getIdGrado().getGrNombre();
                seccion = actual.getMatriculaActual().getIdSeccion().getScDescripcion();
                gradoSeccion = grado + seccion;
            } else {
                gradoSeccion = "N.M.";
            }
            elem = new RptAlumnosPojo(
                    actual.getAlCarnet(),
                    actual.getNombreCompleto(),
                    actual.getFechaNacimientoFiltrable(),
                    new DateUtils().getEdad(actual.getAlFechaNacimiento()),
                    actual.getAlDireccion(),
                    new FormatUtils().getFormatedPhone(actual.getAlTelefonoEm1()),
                    gradoSeccion);
            this.getListDatos().add(elem);
        }
        this.setTotalAlumnos("" + this.getListDatos().size());
        this.setGradosList(sieniGradoFacadeRemote.findAll());
        this.setSeccionesList(new ArrayList<SieniSeccion>());
        if (this.getGradosList() != null && !this.getGradosList().isEmpty()) {
            if (this.getGradosList().get(0).getSieniSeccionList() != null
                    && !this.getGradosList().get(0).getSieniSeccionList().isEmpty()) {
                this.setSeccionesList(this.getGradosList().get(0).getSieniSeccionList());
            }
        }
    }

    public void generarReporte() {
        fill();
        String txtMatriculado = "";
        Integer matriculado = this.getMatriculado();
        switch (matriculado) {
            case 0:
                txtMatriculado = "Todos";
                break;
            case 1:
                txtMatriculado = "Si";
                break;
            case 2:
                txtMatriculado = "No";
                break;
        }
        String path = "resources/reportes/rtpAlumnos.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("desde", new FormatUtils().getFormatedDate(this.getAnioDesde()));
        parameterMap.put("hasta", new FormatUtils().getFormatedDate(this.getAnioHasta()));
        parameterMap.put("grado", this.getGrado() != null ? this.getGrado() : "Último matriculado");
        parameterMap.put("seccion", this.getSeccion() != null ? this.getSeccion() : "Última matriculada");
        parameterMap.put("matriculado", txtMatriculado);
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));

        try {
            RptAlumnosController.generateReport(path, "rtpAlumnos" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
            registrarEnBitacora("Reporte", "Alumnos", 0L);
        } catch (JRException ex) {
            Logger.getLogger("error 1").log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger("error 2").log(Level.SEVERE, null, ex);
        }
    }

    public void refresh() {
        fill();
    }

    public void getSeccionesGrado(ValueChangeEvent a) {
        Long idGrado = (Long) a.getNewValue();
        SieniGrado cod = new SieniGrado();
        for (SieniGrado actual : this.getGradosList()) {
            if (actual.getIdGrado().equals(idGrado)) {
                cod = actual;
                break;
            }
        }
        this.setIdSeccion(0L);
        this.setSeccionesList(cod.getSieniSeccionList());
    }
}
