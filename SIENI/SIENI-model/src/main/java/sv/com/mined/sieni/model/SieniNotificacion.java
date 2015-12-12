/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author bugtraq
 */
@Entity
@Table(name = "sieni_notificacion")
@NamedQueries({
    @NamedQuery(name = "SieniNotificacion.findAll", query = "SELECT s FROM SieniNotificacion s"),
    @NamedQuery(name = "SieniNotificacion.findByIdNotificacion", query = "SELECT s FROM SieniNotificacion s WHERE s.idNotificacion = :idNotificacion"),
    @NamedQuery(name = "SieniNotificacion.findByNfMensaje", query = "SELECT s FROM SieniNotificacion s WHERE s.nfMensaje = :nfMensaje"),
    @NamedQuery(name = "SieniNotificacion.findByNfFechaIngreso", query = "SELECT s FROM SieniNotificacion s WHERE s.nfFechaIngreso = :nfFechaIngreso"),
    @NamedQuery(name = "SieniNotificacion.findByNfFechaFin", query = "SELECT s FROM SieniNotificacion s WHERE s.nfFechaFin = :nfFechaFin"),
    @NamedQuery(name = "SieniNotificacion.findByNfEstado", query = "SELECT s FROM SieniNotificacion s WHERE s.nfEstado = :nfEstado")
     })

public class SieniNotificacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_notificacion")
    @SequenceGenerator(name = "sec_sieni_notificacion", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_notificacion")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_notificacion")
    private Long idNotificacion;
    @Size(max = 4000)
    @Column(name = "nf_mensaje")
    private String nfMensaje;
    @Column(name = "nf_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date nfFechaIngreso;
    @Column(name = "nf_fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date nfFechaFin;
    @Column(name = "nf_estado")
    private Character nfEstado;

    
    @Transient
    private String fechaIngresoFiltrable;
    
    public SieniNotificacion() {
    }

    public SieniNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getNfMensaje() {
        return nfMensaje;
    }

    public void setNfMensaje(String nfMensaje) {
        this.nfMensaje = nfMensaje;
    }

    public Date getNfFechaIngreso() {
        return nfFechaIngreso;
    }

    public void setNfFechaIngreso(Date nfFechaIngreso) {
        this.nfFechaIngreso = nfFechaIngreso;
    }

    public Date getNfFechaFin() {
        return nfFechaFin;
    }

    public void setNfFechaFin(Date nfFechaFin) {
        this.nfFechaFin = nfFechaFin;
    }

    public Character getNfEstado() {
        return nfEstado;
    }

    public void setNfEstado(Character nfEstado) {
        this.nfEstado = nfEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotificacion != null ? idNotificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniNotificacion)) {
            return false;
        }
        SieniNotificacion other = (SieniNotificacion) object;
        if ((this.idNotificacion == null && other.idNotificacion != null) || (this.idNotificacion != null && !this.idNotificacion.equals(other.idNotificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniNotificacion[ idNotificacion=" + idNotificacion + " ]";
    }
    
    
    public String getFechaIngresoFiltrable() {
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
        if (this.nfFechaIngreso != null) {
            fechaIngresoFiltrable = dt1.format(this.nfFechaIngreso);
        }
        return fechaIngresoFiltrable;
    }
    
    
}
