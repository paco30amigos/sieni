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
import net.sf.jasperreports.engine.JRException;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.form.BitacoraForm;
import sv.com.mined.sieni.model.SieniBitacora;
import sv.com.mined.sieni.pojos.rpt.BitacoraPojo;
import utils.DateUtils;
import utils.FormatUtils;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "bitacoraController")
public class BitacoraController extends BitacoraForm {

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;
    
    @EJB
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;
    
    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;

    @PostConstruct
    public void init() {
        this.setTipoRpt(0);
        this.setDesde(new Date());
        this.setHasta(new Date());
        //fill();
    }

    public void fill() {
        BitacoraPojo elem = new BitacoraPojo();
        List<SieniBitacora> bitacoras = sieniBitacoraFacadeRemote.findByFecha(this.getDesde(), this.getHasta());
        this.setListDatos(new ArrayList<BitacoraPojo>());
        for (SieniBitacora actual : bitacoras) {
            String usuario;
            if(actual.getBitTipoUsuario()=='A'){
                usuario = sieniAlumnoFacadeRemote.findAlumnoById(actual.getBitIdUsuario()).getAlUsuario();
            }else {
                usuario = sieniDocenteFacadeRemote.findByDocenteId(actual.getBitIdUsuario()).getDcUsuario();
            }
            elem = new BitacoraPojo(usuario, actual.getBitTipoUsuario().toString(), actual.getBitAccion(), actual.getBitFechaHoraIngreso(), actual.getBitFechaHoraIngreso(), actual.getBitTabla(), actual.getBitIp());
            this.getListDatos().add(elem);
        }
        this.setTotalTransacciones(Long.parseLong(this.getListDatos().size() + ""));
    }

    public void generarReporte() {
        String path = "resources/reportes/rtpBitacora.jasper";
        Map parameterMap = new HashMap();
        parameterMap.put("fechaGeneracion", new FormatUtils().getFormatedDate(new DateUtils().getFechaActual()));
        parameterMap.put("desde", new FormatUtils().getFormatedDate(this.getDesde()));
        parameterMap.put("hasta", new FormatUtils().getFormatedDate(this.getHasta()));

        try {
            BitacoraController.generateReport(path, "rtpBitacora" + new Date().getTime(), this.getListDatos(), parameterMap, this.getTipoRpt());
        } catch (JRException ex) {
            Logger.getLogger("error 1").log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger("error 2").log(Level.SEVERE, null, ex);
        }
    }

    public void refresh() {
        Date desde = this.getDesde();
        Date hasta = this.getHasta();
        this.setBitacoraList(sieniBitacoraFacadeRemote.getBitacorasRangoFecha(desde, hasta));
    }

}
