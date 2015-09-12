///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package sv.com.mined.sieni.controller;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.annotation.PostConstruct;
//import javax.ejb.EJB;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//import javax.faces.context.FacesContext;
//import javax.faces.event.ValueChangeEvent;
//import javax.servlet.http.HttpServletRequest;
//import net.sf.jasperreports.engine.JRException;
//import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
//import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
//import sv.com.mined.sieni.SieniDocenteFacadeRemote;
//import sv.com.mined.sieni.SieniGradoFacadeRemote;
//import sv.com.mined.sieni.form.RptAlumnosForm;
//import sv.com.mined.sieni.form.RptDocentesForm;
//import sv.com.mined.sieni.model.SieniAlumno;
//import sv.com.mined.sieni.model.SieniBitacora;
//import sv.com.mined.sieni.model.SieniDocente;
//import sv.com.mined.sieni.model.SieniGrado;
//import sv.com.mined.sieni.model.SieniSeccion;
//import sv.com.mined.sieni.pojos.rpt.RptAlumnosPojo;
//import sv.com.mined.sieni.pojos.rpt.RptDocentesPojo;
//import utils.DateUtils;
//import utils.FormatUtils;
//
///**
// *
// * @author ever
// */
//@SessionScoped
//@ManagedBean(name = "rptDocentesController")
//public class RptDocentesController extends RptDocentesForm{
//    
//     @EJB
//    SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
//     
//     @EJB
//    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;
//     
//     @PostConstruct
//    public void init() {
//       this.setAnioEscolar("2015");
//        this.setTipoRpt(0);
//         this.setListDatos(new ArrayList<RptDocentesPojo>());
//        fill();
//    }
//    public void fill() {
//        RptDocentesPojo elem = new RptDocentesPojo();
//        
//        List<SieniDocente> docente= sieniDocenteFacadeRemote.findAll();
//        this.setListDatos(new ArrayList<RptDocentesPojo>());
//        for (SieniDocente actual : docente) {
////            SieniGrado grado=sieniGradoFacadeRemote.getGradoActualAlumno(actual.getIdAlumno(),new FormatUtils().getFormatedAnio(new Date()));
////           public RptDocentesPojo(SieniDocente docenteEntity, String docente, String fechaNacimiento, String edad, String direccion, String telefono, String gradoResponsable) {
//            elem = new RptDocentesPojo(actual, actual.getNombreCompleto(), actual.getFechaNacimientoFiltrable(),actual.getDcTelefonoEm2(),actual.getDcDireccion(), actual.getDcTelefonoEm1(), "5");
////        RptAlumnosPojo(actual, grado, actual.getNombreCompleto(), actual.getFechaNacimientoFiltrable(), new DateUtils().getEdad(actual.getAlFechaNacimiento()), actual.getAlDireccion(), new FormatUtils().getFormatedPhone(actual.getAlTelefonoEm1()), grado.getGrNombre());
//            this.getListDatos().add(elem);
//        }
//        
//       
//    }
//
//    public void generarReporte() {
//        fill();
//        List<SieniDocente> docente= sieniDocenteFacadeRemote.findAll();
//        String path = "resources/reportes/rtpDocentes.jasper";
//        Map parameterMap = new HashMap();
//        parameterMap.put("anio", this.getAnioEscolar());
////        parameterMap.put("grado", this.getGrado() != null ? this.getGrado() : "Todos");
////        parameterMap.put("seccion", this.getSeccion() != null ? this.getSeccion() : "Todos");
//        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
//
//        try {
//            RptDocentesController.generateReport(path, "rtpDocentes" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
//            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
//            sieniBitacoraFacadeRemote.create(new SieniBitacora(new Date(), "Generar Reporte", "Docente", loginBean.getIdUsuario(), loginBean.getTipoUsuario().charAt(0)));
//        } catch (JRException ex) {
//            Logger.getLogger("error 1").log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger("error 2").log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void refresh() {
//        fill();
//    }
//
////    public void getSeccionesGrado(ValueChangeEvent a) {
////        Long idGrado = (Long) a.getNewValue();
////        SieniGrado cod = new SieniGrado();
////        for (SieniGrado actual : this.getGradosList()) {
////            if (actual.getIdGrado().equals(idGrado)) {
////                cod = actual;
////                break;
////            }
////        }
////        this.setIdSeccion(0L);
////        this.setSeccionesList(cod.getSieniSeccionList());
////    }
////    
//}
