/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.viejo;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import sv.com.mined.sieni.SieniAnioEscolarFacadeRemote;
//import sv.com.mined.sieni.dao.SieniAlumnRDudFacade;

/**
 *
 * @author bugtraq
 */
@ManagedBean
public class AperturaAnioEscolarForm {

    @EJB
    private SieniAnioEscolarFacadeRemote a;
    private String anio;
    private Date finicio = new GregorianCalendar(2015, 0, 1).getTime();
    private Date ffin = new Date();
    private Integer estado;

    public String getAnio() {
        a.count();
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFinicio() {
        return finicio;
    }

    public void setFinicio(Date finicio) {
        this.finicio = finicio;
    }

    public Date getFfin() {
        return ffin;
    }

    public void setFfin(Date ffin) {
        this.ffin = ffin;
    }
}
